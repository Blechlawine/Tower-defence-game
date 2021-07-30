package de.marc.towerDefenceGame.utils;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Logger {

    private final PrintStream outStream, errStream;

    public Logger() {
        this.outStream = System.out;
        this.errStream = System.err;
    }

    public void info(String message) {
        this.outStream.println(this.makeMessage(message));
    }

    public void err(String message) {
        this.errStream.println(this.makeMessage(message));
    }

    private String makeMessage(String message) {
        LocalDateTime time = LocalDateTime.now();
        String timeStr = "[" + time.format(DateTimeFormatter.ofPattern("kk':'mm':'ss':'n")) + "]";
        return timeStr + ": " + message;
    }

    public void debug(Object... props) {
        List<String> messages = new ArrayList<String>();
        for (Object prop : props) {
            messages.add(String.valueOf(prop));
        }
        String message = String.join(", ", messages);
        this.info(message);
    }
}
