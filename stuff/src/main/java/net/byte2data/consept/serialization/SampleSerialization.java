package net.byte2data.consept.serialization;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Parent implements Serializable {
    int parentVersion = 10;
}

class contain implements Serializable{
    int containVersion = 11;
}

public class SampleSerialization extends Parent implements Serializable {
    int version = 66;
    contain con = new contain();

    public int getVersion() {
        return version;
    }
    public static void main(String args[]) throws IOException {
        FileOutputStream fos = new FileOutputStream("/tmp/temp.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        SampleSerialization st = new SampleSerialization();
        oos.writeObject(st);
        oos.flush();
        oos.close();
    }
}
