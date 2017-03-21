package com.david.commandChain;

/**
 * Created by haojk on 1/25/17.
 */
public class DF extends AbstractDF {


    @Override
    protected String getOperatorParam() {
        return super.DEFAULT_PARAM;
    }

    @Override
    protected String echo(CommandVo vo) {
        return DiskManager.df();
    }
}
