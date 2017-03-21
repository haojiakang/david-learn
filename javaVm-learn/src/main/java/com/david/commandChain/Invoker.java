package com.david.commandChain;

/**
 * Created by haojk on 1/25/17.
 */
public class Invoker {

    //执行命令
    public String exec(String _commandStr) {
        //定义返回值
        String result = "";
        //首先解析命令
        CommandVo vo = new CommandVo(_commandStr);
        //检查是否支持该命令
        if (CommandEnum.getNames().contains(vo.getCommandName())) {
            //产生命令对象
            String className = CommandEnum.valueOf(vo.getCommandName()).getValue();
            Command command;
            try {
                command = (Command) Class.forName(className).newInstance();
                result = command.execute(vo);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            result = "无法执行命令,请检查命令格式!";
        }
        return result;
    }
}
