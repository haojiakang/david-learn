package com.david.commandChain;

/**
 * Created by haojk on 1/25/17.
 */
public class LS extends AbstractLS {

    //参数为空
    @Override
    protected String getOperatorParam() {
        return super.DEFAULT_PARAM;
    }

    //最简单的ls命令
    protected String echo(CommandVo vo) {
        return FileManager.ls(vo.formatData());
    }
}
