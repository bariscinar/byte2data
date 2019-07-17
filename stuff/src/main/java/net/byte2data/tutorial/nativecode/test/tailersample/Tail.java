package net.byte2data.tutorial.nativecode.test.tailersample;


import java.io.*;

/**
 * Implements console-based log file tailing, or more specifically, tail following:
 * it is somewhat equivalent to the unix command "tail -f"
 */
public class Tail implements LogFileTailerListener {
    /**
     * The log file logFileTailer
     */
    private LogFileTailer logFileTailer;
    private long interval;
    private boolean startAtBeginning;

    /**
     * Creates a new Tail instance to follow the specified file
     */
    public Tail(String filename, long interval, boolean startAtBeginning) {
        this.interval=interval;
        this.startAtBeginning=startAtBeginning;
        this.logFileTailer = new LogFileTailer(new File(filename), interval, startAtBeginning);
        logFileTailer.addLogFileTailerListener(this);
        logFileTailer.start();
    }

    /**
     * A new line has been added to the tailed log file
     *
     * @param line   The new line that has been added to the tailed log file
     */
    public void newLogFileLine(String line){
        System.out.println( line );
    }

    /**
     * Command-line launcher
     */
    public static void main( String[] args ) {
        String logFile1 = "/home/barisci/Desktop/rsc_log_1";
        Tail tail1 = new Tail(logFile1,5000L,false);
        String logFile2 = "/home/barisci/Desktop/rsc_log_2";
        Tail tail2 = new Tail(logFile2,5000L,false);

    }

    @Override
    public String toString() {
        return "Tail{" +
                "logFileTailer=" + logFileTailer.getName() +
                ", interval=" + interval +
                ", startAtBeginning=" + startAtBeginning +
                '}';
    }
}
