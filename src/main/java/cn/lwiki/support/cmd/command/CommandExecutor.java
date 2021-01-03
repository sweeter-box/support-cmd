package cn.lwiki.support.cmd.command;


import cn.lwiki.support.cmd.command.listener.CommandListener;

/**
 * @author sweeter
 * @date 2021/1/3
 * 命令执行入口
 */
public interface CommandExecutor {
    /**
     *
     * @param command 命令符
     * @param timeout 超时时间 单位/毫秒
     * @param commandListener 回调
     * @return 返回结果
     */
    Integer execute(Command command, Long timeout, CommandListener commandListener);

    Integer execute(Command command, CommandListener commandListener);

    Integer execute(Command command, Long timeout);

    Integer execute(Command command);
}
