package net.byte2data.tutorial.tutorials.essentialjavaclasses.exceptions;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by barisci on 19.09.2017.
 */


public class ListOfNumbers {
    private List<Integer> instanceIntArray;
    private int instanceIntArraySize;

    private static BufferedReader br;
    private static String retVal;

    public ListOfNumbers(int arraySize, int randomSupplier){
        this.instanceIntArraySize=arraySize;
        instanceIntArray = new ArrayList<>(instanceIntArraySize);
        for(int index=0; index<arraySize; index++){
            instanceIntArray.add((int)(randomSupplier*Math.random()));
        }

    }

    public static void main(String... args) throws IOException{
        System.out.println(readFirstLineFromFile("/home/barisci/Desktop/conversation2.txt"));
    }

    public static String readFirstLineFromFile(String path) throws IOException{
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            System.out.println("Executing...");
            System.out.println(br.readLine());
            System.out.println(br.read(new char[200],30,30));
        }
        /*
        catch (Exception ex){
            System.out.println("Catched ex: " + ex);
            Logger logger = Logger.getLogger("fails");
            for(StackTraceElement element : ex.getStackTrace()){
                System.err.println(element.getFileName() + ":" + element.getLineNumber() + " >> " + element.getMethodName() + "()");
                //logger.log(Level.WARNING,element.getMethodName());
            }
        }finally {
            System.out.println("Finally block is being executed");
            if(null!=br){
                System.out.println("BufferedReader is not null");
                //br.close();
            }else{
                System.out.println("BufferedReader is null");
            }
        }
        */
        return null;
    }

    public void writeList(){
        PrintWriter out = null;
        try{
            out = new PrintWriter(new FileWriter("test.txt"));
            for(int i : instanceIntArray){
                out.println(i);
            }
        }catch (IOException|ArrayIndexOutOfBoundsException firstEx){
            System.out.println("first: " + firstEx);
        }catch (Exception otherEx){
            System.out.println("other: " + otherEx);
        }finally{
            //if out object is not initialized by null obviously, statement below throws an exception
            if(out!=null)
                out.close();
            else
                System.out.println("print writer is not opened");
        }
    }



    public static String readFirstLineFromFile2(String path) throws IOException{
        try{
            br = new BufferedReader(new FileReader(path));
            retVal=br.readLine();
        }catch (Exception ex){
            System.out.println(ex);
        }finally {
            if(null!=br)
                br.close();
        }
        return retVal;
    }



    public static void writeToFileZipFileContents(String zipFileName, String outputFileName) throws IOException {

        java.nio.charset.Charset charset = java.nio.charset.StandardCharsets.US_ASCII;
        java.nio.file.Path outputFilePath = java.nio.file.Paths.get(outputFileName);

        // Open zip file and create output file with
        // try-with-resources statement

        try (java.util.zip.ZipFile zf = new java.util.zip.ZipFile(zipFileName); BufferedWriter writer = java.nio.file.Files.newBufferedWriter(outputFilePath, charset)) {
            // Enumerate each entry
            for (java.util.Enumeration entries = zf.entries(); entries.hasMoreElements(); ) {
                // Get the entry name and write it to the output file
                String newLine = System.getProperty("line.separator");
                String zipEntryName = ((java.util.zip.ZipEntry)entries.nextElement()).getName() + newLine;
                writer.write(zipEntryName, 0, zipEntryName.length());
            }
        }
    }

    public static void viewTable(Connection con) throws SQLException {

        String query = "select COF_NAME, SUP_ID, PRICE, SALES, TOTAL from COFFEES";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String coffeeName = rs.getString("COF_NAME");
                int supplierID = rs.getInt("SUP_ID");
                float price = rs.getFloat("PRICE");
                int sales = rs.getInt("SALES");
                int total = rs.getInt("TOTAL");

                System.out.println(coffeeName + ", " + supplierID + ", " +
                        price + ", " + sales + ", " + total);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
