package cn.lwiki.support.cmd.command;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * @author sweeter
 * @date 2021/1/3
 * 常量类
 */
public final class CmdConstant {
    private static Logger log = LoggerFactory.getLogger(CmdConstant.class);

    private CmdConstant() {

    }

    /**
     * 默认超时时间
     */
    public final static Long DEFAULT_TIMEOUT = 100L;

    public static boolean isWin = false;

    public static boolean isLinux = false;

    public static Integer defaultIoThreads = 1;

    public static final String WIN_CMD = "cmd";

    public static final String WIN_CMD_EXE = "cmd.exe";

    /**
     * 在Windows上运行此命令，cmd，/ c =在此运行后终止
     */
    public static final String WIN_CMD_C = "/c";

    public static final String LINUX_CMD = "/usr/bin/env";

    static {
        String env = System.getProperty("os.name");
        log.debug("current operate system :{}", env);
        if (StringUtils.isNotBlank(env)) {
            String os = env.toLowerCase();
            if (os.contains("win")) {
                isWin = true;
            } else if (os.contains("linux") || os.contains("mac")) {
                isLinux = true;
            }
        }
        defaultIoThreads = Runtime.getRuntime().availableProcessors();
        if (log.isDebugEnabled()) {
            log.debug("isWindows : '{}'  or isLinux:'{}' defaultIoThreads:'{}'", isWin, isLinux, defaultIoThreads);
        }
    }

    /**
     * 开头命令
     * @return List<String>
     */
    public static List<String> startCommand() {
        if (isWin) {
            return Lists.newArrayList(WIN_CMD_EXE, WIN_CMD_C);
        }
        if (isLinux) {
            return Lists.newArrayList(LINUX_CMD);
        }
        return Collections.emptyList();
    }
}
