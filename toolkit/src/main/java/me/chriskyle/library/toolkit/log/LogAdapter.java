package me.chriskyle.library.toolkit.log;

public interface LogAdapter {

    boolean isLoggable(int priority, String tag);

    void log(int priority, String tag, String message);
}