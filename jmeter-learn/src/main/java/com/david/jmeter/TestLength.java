package com.david.jmeter;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.logging.Logger;

/**
 * Jmeter自定义脚本测试类
 * Created by haojk on 5/19/16.
 */
public class TestLength extends AbstractJavaSamplerClient {

    /**
     * 输出到Jmeter控制台的日志类
     * 需要引用Jmeter lib目录下的logkit-2.0.jar
     */
    private Logger logger = Logger.getLogger(TestLength.class.getSimpleName());

    /**
     * 运行结果
     */
    private SampleResult results;

    /**
     * Jmeter控制台输入的参数
     */
    private String testStr;

    /**
     * 初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行
     * 类似于LoadRunner中的init方法
     * @param arg0
     */
    @Override
    public void setupTest(JavaSamplerContext arg0){
        logger.info("execute setup test...");
        results = new SampleResult();
        testStr = arg0.getParameter("testStr", "");
        if(testStr != null && testStr.length() > 0){
            results.setSamplerData(testStr);
        }
    }

    /**
     * 设置传入的参数，可以设置多个，已设置的参数会显示到Jmeter的参数列表中
     * @return
     */
    @Override
    public Arguments getDefaultParameters(){
        logger.info("execute getDefaultParameters");
        Arguments params = new Arguments();
        /*
        定义一个参数，显示到Jmeter的参数列表中
        第一个参数为参数默认的显示名称
        第二个参数为默认值
         */
        params.addArgument("testStr", "");
        return params;
    }

    /**
     * 测试执行的循环体，根据线程数和循环次数的不同可执行多次，类似于LoadRunner中的Action方法
     * @param arg0
     * @return
     */
    public SampleResult runTest(JavaSamplerContext arg0) {
        logger.info("execute runTest...");
        //定义一个事务，表示这是事务的起点，类似于LoadRunner的lr.start_transaction
//        results.sampleStart();
        //定义一个事务，表示这是事务的终点，类似于LoadRunner的lr.end_transaction
//        results.sampleEnd();
        if(testStr.length() < 5){
            logger.info("fail... the length of testStr is less than 5");
            //用于设置运行结果的成功或失败，如果是"false"则表示失败，否则表示成功
            results.setSuccessful(false);
        } else {
            logger.info("Success...");
            results.setSuccessful(true);
        }
        return results;
    }

    /**
     * 结束方法，实际运行时每个县城仅执行一次，在测试方法运行结束后执行
     * 类似于LoadRunner中的end方法
     * @param arg0
     */
    @Override
    public void teardownTest(JavaSamplerContext arg0){

    }

}
