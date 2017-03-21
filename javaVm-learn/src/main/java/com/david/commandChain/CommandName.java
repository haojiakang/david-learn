package com.david.commandChain;

/**
 * Created by haojk on 1/25/17.
 */
public abstract class CommandName {

    private CommandName nextOperator;

    public final String handleMessage(CommandVo vo) {
        //处理结果
        String result;
        //判断是否是自己处理的参数
        if (vo.getParam().size() == 0 || vo.getParam().contains(this.getOperatorParam())) {
            result = this.echo(vo);
        } else {
            if (this.nextOperator != null) {
                result = this.nextOperator.handleMessage(vo);
            } else {
                result = "命令无法解析";
            }
        }
        return result;
    }

    //每个处理者都要处理一个后缀参数
    protected abstract String getOperatorParam();

    //每个处理者都必须实现处理任务
    protected abstract String echo(CommandVo vo);

    public void setNextOperator(CommandName nextOperator) {
        this.nextOperator = nextOperator;
    }
}
