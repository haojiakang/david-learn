package com.david.commandChain;

/**
 * Created by haojk on 1/25/17.
 */
public class DF_G extends AbstractDF {

    @Override
    protected String getOperatorParam() {
        return super.G_PARAM;
    }

    @Override
    protected String echo(CommandVo vo) {
        return DiskManager.df_g();
    }
}
