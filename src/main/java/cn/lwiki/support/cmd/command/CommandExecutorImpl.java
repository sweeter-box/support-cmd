package cn.lwiki.support.cmd.command;


import cn.lwiki.support.cmd.base.NamedThreadFactory;
import cn.lwiki.support.cmd.command.listener.CommandListener;
import cn.lwiki.support.cmd.command.listener.DefaultLinuxCommandListener;
import cn.lwiki.support.cmd.command.listener.DefaultWinCommandListener;
import cn.lwiki.support.cmd.command.listener.ListenerParam;
import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.*;

/**
 * @author sweeter
 * @date 2021/1/3
 */
public class CommandExecutorImpl implements CommandExecutor {

    private Logger log = LoggerFactory.getLogger(CommandExecutorImpl.class);

    private static final ExecutorService executorService =
            new ThreadPoolExecutor(1, CmdConstant.defaultIoThreads * 2, 3L, TimeUnit.SECONDS,
                    new SynchronousQueue<>(),new NamedThreadFactory("CommandExecutor-"));
    @Override
    public Integer execute(Command command,Long timeout, CommandListener commandListener) {
        //返回参数
        Integer result = null;
        Stopwatch stopwatch = Stopwatch.createStarted();
        Future<Integer> executeFuture = null;
        ProcessBuilder processBuilder = new ProcessBuilder(command.get());
        Process process = null;
        if (StringUtils.isNotBlank(command.directory())) {
            //设置命令执行目录
            processBuilder.directory(new File(command.directory()));
        }else {
            processBuilder.directory(null);
        }
        try {
             process = processBuilder.start();
            log.info("执行命令行:\n{}",command.getLine());
            InputStream processInputStream = process.getInputStream();
            OutputStream processOutputStream = process.getOutputStream();
            InputStream processErrorStream = process.getErrorStream();
            //执行监听
            commandListener.handle(new ListenerParam(processInputStream,processOutputStream,processErrorStream));

            final Process p = process;
            Callable<Integer> call = () -> {
                p.waitFor();
                return p.exitValue();
            };
            executeFuture = executorService.submit(call);
            int executeCode = executeFuture.get(timeout, TimeUnit.MILLISECONDS);
            log.info("状态码(0:means success):{}", executeCode);
            result = executeCode;
        } catch (Exception e) {
            log.error("执行命令出错：{}", e.getMessage());
        }finally {
            if (executeFuture != null) {
                try {
                    executeFuture.cancel(Boolean.TRUE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (process != null) {
                process.destroy();
                processBuilder = null;
            }
            if (log.isInfoEnabled()) {
                log.info("command run {} seconds, {} milliseconds", stopwatch.elapsed(TimeUnit.SECONDS), stopwatch.elapsed(TimeUnit.MILLISECONDS));
            }
        }
        return result;
    }

    @Override
    public Integer execute(Command command, CommandListener commandListener) {
       return this.execute(command, CmdConstant.DEFAULT_TIMEOUT, commandListener);
    }

    @Override
    public Integer execute(Command command, Long timeout) {
        if (CmdConstant.isLinux) {
            return this.execute(command, timeout, new DefaultLinuxCommandListener());
        }else {
            return this.execute(command, timeout, new DefaultWinCommandListener());
        }
    }

    @Override
    public Integer execute(Command command) {
        return this.execute(command, CmdConstant.DEFAULT_TIMEOUT);
    }
}
