package edu.pg.virtualworld;

import javax.swing.*;

public class Logger {
    private JTextArea textArea;
    private JScrollPane logsScrollPane;
    public Logger(JTextArea textArea,JScrollPane  logsScrollPane){
        this.textArea=textArea;
        this.logsScrollPane= logsScrollPane;
    }
    public void log(String log){
        textArea.append(log+"\n");
        logsScrollPane.getVerticalScrollBar().setValue(logsScrollPane.getVerticalScrollBar().getMaximum());
    }
}
