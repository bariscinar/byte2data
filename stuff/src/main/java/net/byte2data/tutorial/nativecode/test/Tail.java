package net.byte2data.tutorial.nativecode.test;

import net.byte2data.consept.concurrency.GetDetail;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * based on http://stackoverflow.com/a/559146/1313040
 * also based on http://stackoverflow.com/a/2922031/1313040
 */
public class Tail implements Runnable {

    private long filePointer;
    private File file;
    private static volatile boolean keepRunning = true;

    public static void main(String[] args) {
        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                keepRunning = false;
                try {
                    mainThread.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Tail.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        File log = new File("/home/barisci/Desktop/rsc_log");
        Tail tail = new Tail(log);
        new Thread(tail).start();

    }

    private Tail(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        long fileLength=0;
        long UPDATE_INTERVAL = 5000;
        String line=null;
        try {
            RandomAccessFile fileToTail = new RandomAccessFile(file, "r");
            while (keepRunning) {
                fileLength = file.length();
                GetDetail.getThreadDetails("file length:"+fileLength);
                GetDetail.getThreadDetails("file pointer:"+ filePointer);
                if (fileLength < filePointer) {
                    GetDetail.getThreadDetails("Log file must have been rotated or deleted; reopen the file and reset the file pointer");
                    filePointer = fileLength;
                } else if (fileLength > filePointer) {
                    GetDetail.getThreadDetails("File must have had something added to it!");
                    fileToTail = new RandomAccessFile(file, "r");
                    fileToTail.seek(filePointer);
                    line =  fileToTail.readLine();
                    while (line != null) {
                        this.appendLine(line);
                    }
                    filePointer = fileToTail.getFilePointer();
                    fileToTail.close();
                }else{
                    GetDetail.getThreadDetails("File is not changed!");
                }
            }
            Thread.sleep(UPDATE_INTERVAL);
        } catch (Exception e) {
            GetDetail.getThreadDetails(e.getMessage());
        }
        // dispose();
    }

    private void appendMessage(String line) {
        System.out.println(line.trim());
    }

    private void appendLine(String line) {
        System.out.println(line.trim());
    }
}
