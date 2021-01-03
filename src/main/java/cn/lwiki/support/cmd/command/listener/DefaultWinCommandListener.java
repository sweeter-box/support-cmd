package cn.lwiki.support.cmd.command.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sweeter
 * @date 2021/1/3
 */
public class DefaultWinCommandListener implements CommandListener {

    private Logger log = LoggerFactory.getLogger(DefaultWinCommandListener.class);

    @Override
    public void handle(ListenerParam listenerParam) throws IOException {
        printlnLog(listenerParam.getProcessErrorStream());
        converter(listenerParam.getProcessInputStream());
    }

    private void converter(InputStream inputStream) throws IOException{
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            List<String> resultList = reader.lines().collect(Collectors.toList());
            log.info("执行cmd返回信息:{}", String.join(System.getProperty("line.separator"), resultList));
        }catch(Exception e){
            throw e;
        }
    }
}
