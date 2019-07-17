package net.byte2data.consept.designpatterns.structural.templatemethod.coffee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BlackOrMilkyCoffee extends ACaffeineBeverage{

    public void brew(){
        System.out.println("black coffee - brew");
    }

    public void addContiments(){
        System.out.println("black coffee - addToTail some milk!");
    }

    @Override
    public boolean customerRequestsCondiment() {
        System.out.println("asking...");
        String coice = customerChoice();
        return ((coice!=null)&&(coice.toLowerCase().equals("y")));
    }

    private String customerChoice(){
        String choice = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try{
            choice = in.readLine();
        }catch (IOException ioEx){
            System.out.println("ex: " + ioEx);
        }
        System.out.println("choice: " + choice);
        return choice;
    }
}
