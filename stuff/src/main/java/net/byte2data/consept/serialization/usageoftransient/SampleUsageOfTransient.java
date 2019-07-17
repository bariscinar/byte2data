package net.byte2data.consept.serialization.usageoftransient;

import net.byte2data.consept.concurrency.concept.immuttableobjects.Sample;

import java.io.*;

public class SampleUsageOfTransient implements Serializable{

    private transient static String a="a";
    private transient static String b;
    private transient static String c;
    private transient static String d;
    private transient static String e;
    private transient static String f;

    static {
        b="b";
    }

    SampleUsageOfTransient(){
        c="c";
    }

    public static void setD(){
        d="d";
    }

    public void setE(){
        e="e";
    }

    @Override
    public String toString() {
        return "SampleUsageOfTransient{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                ", d='" + d + '\'' +
                ", e='" + e + '\'' +
                ", f='" + f + '\'' +
                '}';
    }

    public static void main(String... args) throws IOException, ClassNotFoundException, InterruptedException{
        SampleUsageOfTransient serialized = new SampleUsageOfTransient();

        SampleUsageOfTransient.setD();
        serialized.setE();

        FileOutputStream fileOutputStream = new FileOutputStream("/tmp/a.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(serialized);

        Thread.sleep(1000);

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("/tmp/a.ser"));
        SampleUsageOfTransient deserialized = (SampleUsageOfTransient) objectInputStream.readObject();

        System.out.println(deserialized);

    }

}
