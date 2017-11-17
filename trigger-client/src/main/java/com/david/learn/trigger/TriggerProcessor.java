package com.david.learn.trigger;

import cn.sina.api.commons.cache.MemcacheClient;
import cn.sina.api.data.model.FirehoseMessagePBUtil;
import cn.sina.api.data.protobuf.FirehoseMessageWrap;
import com.weibo.api.harmonia.threads.decorate.build.DecoratedExecutorBuilder;
import com.weibo.api.harmonia.threads.decorate.decorators.ShareMdcFeatureDecoratorAssembler;
import com.weibo.api.motan.core.DefaultThreadFactory;
import com.weibo.trigger.common.bean.MessageBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiakang on 2017/9/13.
 */
public class TriggerProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TriggerProcessor.class);
    private static final String COMMONMESSAGE_TRIGGER_THREAD_POOL = "CommonmessageTriggerThreadPool";

    private ExecutorService workerPoolFromCommonmessageTrigger;
    protected int commonMessageTriggerThreadCount;
    protected MemcacheClient commonMessageTriggerClient;
    protected String commonMessageTriggerMessageTopic;

    public void setCommonMessageTriggerThreadCount(int commonMessageTriggerThreadCount) {
        this.commonMessageTriggerThreadCount = commonMessageTriggerThreadCount;
    }

    public void setCommonMessageTriggerClient(MemcacheClient commonMessageTriggerClient) {
        this.commonMessageTriggerClient = commonMessageTriggerClient;
    }

    public void setCommonMessageTriggerMessageTopic(String commonMessageTriggerMessageTopic) {
        this.commonMessageTriggerMessageTopic = commonMessageTriggerMessageTopic;
    }

    public static class CustomCallerRunsPolicy extends ThreadPoolExecutor.CallerRunsPolicy {
        public CustomCallerRunsPolicy() {
        }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            TriggerProcessor.logger.warn("thread pool is full,use the submit thread execute task. thread pool info:" + e.toString());
            super.rejectedExecution(r, e);
        }
    }

    @PostConstruct
    public void init() throws IOException {
        logger.info("============= Start init MessagesTriggerProcessor");

        try {

            // 读CommonMessage线程池
            DefaultThreadFactory defaultThreadFactoryCommonMessage = new DefaultThreadFactory(COMMONMESSAGE_TRIGGER_THREAD_POOL, true);
            ThreadPoolExecutor workerPoolBaseCommonMessage =
                    new ThreadPoolExecutor(this.commonMessageTriggerThreadCount, this.commonMessageTriggerThreadCount, 60000L,
                            TimeUnit.MILLISECONDS, new ArrayBlockingQueue(this.commonMessageTriggerThreadCount),
                            defaultThreadFactoryCommonMessage, new TriggerProcessor.CustomCallerRunsPolicy());
            this.workerPoolFromCommonmessageTrigger =
                    (new DecoratedExecutorBuilder()).setBaseExecutorService(workerPoolBaseCommonMessage)
                            .appendMoreAssembler(new ShareMdcFeatureDecoratorAssembler()).build();

        } catch (RuntimeException e) {
            logger.error("init MessagesTriggerProcessor workerPool error", e);
            throw e;
        }

        startReading();
    }

    private void startReading() {
        logger.info("============= Create read trigger Thread");

        for (int i = 0; i < commonMessageTriggerThreadCount; i++) {
            this.workerPoolFromCommonmessageTrigger.submit(() -> {
                try {
                    readFromTrigger(commonMessageTriggerClient);
                } catch (Exception e) {
                    logger.error("createReadThread Error: when read trigger. key:" + commonMessageTriggerMessageTopic, e);
                }
            });
        }
    }

    /**
     * 循环从trigger读取数据
     */
    protected void readFromTrigger(MemcacheClient triggerClient) {
        String triggerTopic = commonMessageTriggerMessageTopic;
//        waitForInit();

        logger.info("========Start trigger reader! topic:{}", triggerTopic);
        MessageBatch messageBatch;
        while (true) {
            try {
                while ((messageBatch = (MessageBatch) triggerClient.get(triggerTopic)) != null) {
//                    List<String> messages = messageBatch.getResultJson();
//                    for (String message : messages) {
//                        System.out.println(message);
//                    }
//
                    List<byte[]> pbMessagesFromTrigger = messageBatch.getFirehoseMessageFromTrigger();
                    handleMsq(pbMessagesFromTrigger);
                }
            } catch (Exception e) {
                logger.error("readFromTrigger Error: when read trigger. key:" + triggerTopic, e);
            }
        }
    }

    /**
     * trigger读到的消息的处理
     *
     * @param pbMessages
     */
    protected void handleMsq(List<byte[]> pbMessages) {
        if (pbMessages == null || pbMessages.isEmpty()) {
            logger.info("pbMessages is null, so return.");
            return;
        }

        for (byte[] pbMessage : pbMessages) {
            try {
                FirehoseMessageWrap.FirehoseMessage firehoseMessage = FirehoseMessagePBUtil.parseFrom(pbMessage);
                if (firehoseMessage == null) {
                    logger.warn("firehoseMessage is null. so return");
                    continue;
                }
                String firehoseMessageType = firehoseMessage.getType();
                logger.info("TMD.receive message, type={}. firehoseMessage:{}", firehoseMessageType, firehoseMessage);
            } catch (Exception e) {
                logger.error("MessagesTriggerProcessor handleMsq error. ", e);
            }
        }
    }
}
