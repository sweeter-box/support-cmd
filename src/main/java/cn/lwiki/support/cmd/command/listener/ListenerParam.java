package cn.lwiki.support.cmd.command.listener;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author sweeter
 * @date 2021/1/3
 */
public class ListenerParam {

    private InputStream processInputStream;

    private OutputStream processOutputStream;

    private InputStream processErrorStream;

    public ListenerParam(InputStream processInputStream, OutputStream processOutputStream, InputStream processErrorStream) {
        this.processInputStream = processInputStream;
        this.processOutputStream = processOutputStream;
        this.processErrorStream = processErrorStream;
    }

    public OutputStream getProcessOutputStream() {
        return processOutputStream;
    }

    public void setProcessOutputStream(OutputStream processOutputStream) {
        this.processOutputStream = processOutputStream;
    }

    public InputStream getProcessInputStream() {
        return processInputStream;
    }

    public void setProcessInputStream(InputStream processInputStream) {
        this.processInputStream = processInputStream;
    }

    public InputStream getProcessErrorStream() {
        return processErrorStream;
    }

    public void setProcessErrorStream(InputStream processErrorStream) {
        this.processErrorStream = processErrorStream;
    }
}
