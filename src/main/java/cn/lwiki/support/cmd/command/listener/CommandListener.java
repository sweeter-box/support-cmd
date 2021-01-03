package cn.lwiki.support.cmd.command.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author sweeter
 * @date 2021/1/3
 */
@FunctionalInterface
public interface CommandListener {

    Logger log = LoggerFactory.getLogger(CommandListener.class);
    /**
     * 命令执行后的监听处理 处理命令输出结果
     * @param listenerParam 监听入参
     * @return 处理后的字符串
     */
    void handle(ListenerParam listenerParam) throws Exception;

    default void printlnLog(InputStream inputStream) {
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line;
            while((line = reader.readLine()) != null) {
                log.info("打印cmd执行日志:{}", line);
            }
        }catch(Exception e){
            e.fillInStackTrace();
        }
    }
}
