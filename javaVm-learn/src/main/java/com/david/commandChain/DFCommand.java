package com.david.commandChain;

/**
 * Created by haojk on 1/25/17.
 */
public class DFCommand extends Command {

    @Override
    public String execute(CommandVo vo) {
        return super.buildChain(AbstractDF.class).get(0).handleMessage(vo);
    }
}
