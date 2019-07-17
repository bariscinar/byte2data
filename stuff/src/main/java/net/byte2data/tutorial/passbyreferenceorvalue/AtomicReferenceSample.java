package net.byte2data.tutorial.passbyreferenceorvalue;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceSample {

    private static void changeString(String parameter){
        System.out.println("Before Processing parameter:"+parameter);
        parameter=parameter.concat(" inserted!");
        System.out.println("After Processing parameter:"+parameter);
    }

    private static void changeString(AtomicReference<String> parameter){
        System.out.println("Before Processing parameter:"+parameter.get());
        parameter.set(parameter.get().concat(" inserted!"));
        System.out.println("After Processing parameter:"+parameter.get());
    }

    public static void main(String... args){
        String param =  "initial";
        AtomicReference<String> stringAtomicReference = new AtomicReference<>();
        stringAtomicReference.set(param);
        System.out.println("1-param:"+param);
        changeString(param);
        System.out.println("2-param:"+param);
        System.out.println("3-stringAtomicReference:"+stringAtomicReference.get());
        changeString(stringAtomicReference);
        System.out.println("4-param:"+param);
        System.out.println("5-stringAtomicReference:"+stringAtomicReference.get());
    }

}
