package cn.lwiki.support.cmd.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author sweeter
 * @date 2021/1/3
 */
public class Command {
    /**
     * 命令符
     */
    private List<String> command;
    /**
     * 命令执行的根目录
     */
    private String directory;

    private Command(Builder builder) {
        this.command = builder.commands;
        this.directory = builder.directory;
    }

    /**
     * 命令构建
     */
    public static final class Builder {
        private List<String> commands = new ArrayList<>();

        private String directory;

        private static final String BLANK = " ";

        public Builder directory(String directory) {
            this.directory = directory;
            return this;
        }
        public Builder add(String command) {
            commands.add(command);
            return this;
        }

        public Builder add(String ...command) {
            commands.addAll(Arrays.asList(command));
            return this;
        }

        public Builder add(List<String> command) {
            commands.addAll(command);
            return this;
        }

        public Builder add(String key,String value) {
            commands.addAll(Arrays.asList(key, value));
            return this;
        }

        public Builder addLine(String ...command){
            commands.add(String.join(System.getProperty("line.separator"), command));
            return this;
        }

        public Command build() {
            if (Objects.isNull(commands) || commands.size() == 0) {
                throw new IllegalArgumentException("command cannot be empty");
            }
            return new Command(this);
        }
    }

    public List<String> get() {
        return this.command;
    }

    public String getLine() {
        return String.join(" ", this.command);
    }

    public String directory() {
        return this.directory;
    }
}
