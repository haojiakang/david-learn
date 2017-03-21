package com.david.commandChain;

/**
 * Created by haojk on 1/25/17.
 */
public class DF_K extends AbstractDF {

    @Override
    protected String getOperatorParam() {
        return super.K_PARAM;
    }

    @Override
    protected String echo(CommandVo vo) {
        return DiskManager.df_k();
    }
}
