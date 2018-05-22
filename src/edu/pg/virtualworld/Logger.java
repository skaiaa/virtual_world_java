package edu.pg.virtualworld;

import javax.swing.*;

class Logger {
    private JTextArea textArea;
    private JScrollPane logsScrollPane;

    Logger(JTextArea textArea, JScrollPane logsScrollPane) {
        this.textArea = textArea;
        this.logsScrollPane = logsScrollPane;
    }

    void log(String log) {
        textArea.append(log + "\n");
        logsScrollPane.getVerticalScrollBar().setValue(logsScrollPane.getVerticalScrollBar().getMaximum());
    }
}
