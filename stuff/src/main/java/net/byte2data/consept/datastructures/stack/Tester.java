package net.byte2data.consept.datastructures.stack;

import java.util.Random;

public class Tester {

    public static void main(String... args) throws Exception{
        Stack[] stackArray = new Stack[10000];

        for(int index=0; index<=9999; index++){
            String name = "Stack-"+index;
            if(index>0 && index%333==0){
                System.out.println(index);
                stackArray[index] = stackArray[index-1];
            }else{
                stackArray[index] = new Stack((new Random().nextInt(5))+1 , name);
            }

        }
        int random = 0;
        while(random++>-1){
            int val = new Random().nextInt(Integer.MAX_VALUE);
            if(val == random){
                System.out.println("WOOUW - " + random);
            }
            try{
                /*
                if(null!=stackArray[val]){
                    System.out.println(stackArray[val].toString());
                }
                */
            }catch (Exception ex){

            }

        }
        System.out.println("random:"+random);
        Thread.sleep(999999999);
    }

}
