package net.byte2data.tutorial.nativecode.test.tailersample;
import net.byte2data.consept.concurrency.GetDetail;

import java.io.*;

import java.util.*;

/**
 * A log file tailer is designed to monitor a log file and send notifications
 * when new lines are added to the log file. This class has a notification
 * strategy similar to a SAX parser: implement the LogFileTailerListener interface,
 * create a LogFileTailer to tail your log file, addToTail yourself as a listener, and
 * start the LogFileTailer. It is your job to interpret the results, build meaningful
 * sets of data, etc. This tailer simply fires notifications containing new log file lines,
 * one at a time.
 */
public class LogFileTailer extends Thread {
    /**
     * How frequently to check for file changes; defaults to 5 seconds
     */
    private long sampleInterval = 5000;

    /**
     * The log file to tail
     */
    private File logfile;

    /**
     * Defines whether the log file tailer should include the entire contents
     * of the exising log file or tail from the end of the file when the tailer starts
     */
    private boolean startAtBeginning = false;

    /**
     * Is the tailer currently tailing?
     */
    private boolean tailing = false;

    /**
     * Set of listeners
     */
    private Set listeners = new HashSet();

    /**
     * Creates a new log file tailer that tails an existing file and checks the file for
     * updates every 5000ms
     */
    public LogFileTailer(File file) {
        this.logfile = file;
    }

    /**
     * Creates a new log file tailer
     *
     * @param file         The file to tail
     * @param sampleInterval    How often to check for updates to the log file (default = 5000ms)
     * @param startAtBeginning   Should the tailer simply tail or should it process the entire
     *               file and continue tailing (true) or simply start tailing from the
     *               end of the file
     */
    public LogFileTailer(File file, long sampleInterval, boolean startAtBeginning) {
        this(file);
        this.sampleInterval = sampleInterval;
        this.startAtBeginning=startAtBeginning;
    }

    public void addLogFileTailerListener(LogFileTailerListener logFileTailerListener) {
        GetDetail.getThreadDetails("New listener added:"+logFileTailerListener.toString());
        this.listeners.add(logFileTailerListener);
    }

    public void removeLogFileTailerListener(LogFileTailerListener logFileTailerListener) {
        this.listeners.remove(logFileTailerListener);
    }

    protected void fireNewLogFileLine(String line) {
        Iterator i = this.listeners.iterator();
        while (i.hasNext()){
            LogFileTailerListener logFileTailerListener = (LogFileTailerListener)i.next();
            logFileTailerListener.newLogFileLine( line );
        }
    }

    public void stopTailing() {
        this.tailing = false;
    }

    public void run() {
        GetDetail.getThreadDetails("LogFileTailer is starting...:");
        long filePointer = 0;
        long fileLength = 0;
        String line = null;
        if(this.startAtBeginning){
            filePointer = 0;
        }else{
            filePointer = this.logfile.length();
        }
        try{
            this.tailing = true;
            RandomAccessFile fileToTail = new RandomAccessFile( logfile, "r" );
            while(this.tailing){
                try{
                    fileLength = this.logfile.length();
                    GetDetail.getThreadDetails("File length:"+fileLength);
                    GetDetail.getThreadDetails("File pointer:"+ filePointer);
                    if( fileLength < filePointer ) {
                        GetDetail.getThreadDetails("Log file must have been rotated or deleted; reopen the file and reset the file pointer");
                        filePointer = 0;
                    }else if( fileLength > filePointer ) {
                        GetDetail.getThreadDetails("File must have had something added to it!");
                        //fileToTail = new RandomAccessFile( logfile, "r" );
                        fileToTail.seek(filePointer);
                        GetDetail.getThreadDetails("line:"+line);
                        while(null!= (line = fileToTail.readLine())) {
                            this.fireNewLogFileLine(line);
                        }
                        filePointer = fileToTail.getFilePointer();
                        fileToTail.close();
                    }else{
                        GetDetail.getThreadDetails("File is not changed!");
                    }
                    Thread.sleep( this.sampleInterval );
                }
                catch( Exception e ){
                    GetDetail.getThreadDetails(e.getMessage());
                }
            }

// Close the file that we are tailing
            fileToTail.close();
        }
        catch( Exception e )
        {
            GetDetail.getThreadDetails(e.getMessage());
        }
    }
}
