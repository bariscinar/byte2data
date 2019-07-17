package net.byte2data.consept.serialization.usageoftransient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AReferenceObject implements Serializable{

    private String instanceVar;

    AReferenceObject(String item){
        this.instanceVar=item;
    }

    @Override
    public String toString() {
        return "AReferenceObject{" +
                "instanceVar='" + instanceVar + '\'' +
                '}';
    }

    public static void main(String... args) throws IOException{
        AReferenceObject aReferenceObject = new AReferenceObject("test");
        FileOutputStream fileOutputStream = new FileOutputStream("/tmp/test.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(aReferenceObject);
    }
}
