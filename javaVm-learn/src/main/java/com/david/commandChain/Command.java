package com.david.commandChain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haojk on 1/25/17.
 */
public abstract class Command {

    public abstract String execute(CommandVo vo);

    //建立链表
    protected final List<? extends CommandName> buildChain(Class<? extends CommandName> abstractClass) {
        //取出所有的命令名下的子类
        List<Class> classes = ClassUtils.getSonClass(abstractClass);
        //存放命令的实例,并建立链表关系
        List<CommandName> commandNameList = new ArrayList<>();
        for (Class c : classes) {
            CommandName commandName = null;
            try {
                //产生实例
                commandName = (CommandName) Class.forName(c.getName()).newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //建立链表
            if (commandNameList.size() > 0) {
                commandNameList.get(commandNameList.size() - 1).setNextOperator(commandName);
            }
            commandNameList.add(commandName);
        }
        return commandNameList;
    }
}
