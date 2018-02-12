package me.chriskyle.library.toolkit.log;

public interface LogStrategy {

    void log(int priority, String tag, String message);
}
