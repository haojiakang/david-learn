package com.david.commandChain;

/**
 * Created by haojk on 1/25/17.
 */
public class LS_L extends AbstractLS {

    @Override
    protected String getOperatorParam() {
        return super.L_PARAM;
    }

    @Override
    protected String echo(CommandVo vo) {
        return FileManager.ls_l(vo.formatData());
    }
}
