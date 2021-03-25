package de.marc.towerDefenceGame.utils;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Logger {

    private final PrintStream outStream, errStream;

    public Logger() {
        this.outStream = System.out;
        this.errStream = System.err;
    }

    public void info(String message) {
        this.outStream.println(message);
    }

    public void err(String message) {
        this.errStream.println(message);
    }

    public void debug(Object... props) {
        List<String> messages = new ArrayList<String>();
        for (Object prop : props) {
            messages.add(String.valueOf(prop));
        }
        String message = String.join(", ", messages);
        this.info(message.toString());
    }
}
