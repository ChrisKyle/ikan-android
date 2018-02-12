package me.chriskyle.library.toolkit.log;

public interface FormatStrategy {

    void log(int priority, String tag, String message);
}
