package com.david.commandChain;

/**
 * Created by haojk on 1/25/17.
 */
public class LS_A extends AbstractLS {


    @Override
    protected String getOperatorParam() {
        return super.A_PARAM;
    }

    @Override
    protected String echo(CommandVo vo) {
        return FileManager.ls_a(vo.formatData());
    }
}
