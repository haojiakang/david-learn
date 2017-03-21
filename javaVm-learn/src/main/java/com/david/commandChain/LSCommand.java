package com.david.commandChain;

/**
 * Created by haojk on 1/25/17.
 */
public class LSCommand extends Command {

    @Override
    public String execute(CommandVo vo) {
        //返回链表的首节点
        CommandName firstNode = super.buildChain(AbstractLS.class).get(0);
        return firstNode.handleMessage(vo);
    }
}
