package net.byte2data.consept.concurrency.concept.synchronization.raceconditionincriticalsection;

public class ThreadSafe {

    private class SharedResource{

        String memberVariable = "x";

        public void add(String y){
            memberVariable=memberVariable.concat(y);
            System.out.println(memberVariable);
        }

        public void addValue(int val1, int val2){
            //MemberVariables are not thread safe
            add("y");

            //Local Object References are Thread Safe
            ReferenceObject referenceObject = new ReferenceObject();
            referenceObject.setValue("1");
            System.out.println("local object reference: " + referenceObject.getValue());
            setSmth(referenceObject,"2");

            //Local Variables are Thread Safe
            int var=99;
            var +=var;
            var+=1;
            System.out.println("local variable: " + ++var);
        }

        public void setSmth(ReferenceObject ro, String x){
            ro.setValue(x);
        }

        public void getValue(){
            System.out.format("");
        }

    }

    private class MyThread implements Runnable{
        private SharedResource resource;
        public MyThread(SharedResource rs){
            this.resource=rs;
        }
        public void run(){
            resource.addValue(1,1);
        }
    }

    private void executer() throws InterruptedException{
        SharedResource[] rsArray = new SharedResource[10];

        SharedResource rs = new SharedResource();
        MyThread myThread = new MyThread(rs);
        for(int index = 0; index<10; index++) {
            new Thread(myThread).start();
            //If Object Member Variables are not shared then it is thread safe as shown below
            //rsArray[index]=new SharedResource();
            //new Thread(new MyThread(rsArray[index])).start();
        }
        //Yahu arkadaş neden aşağıdakinin çalışmasına izin vermiyor???
        /*
        for(int index = 0; index<10; index++)
            th1.start();
        */
        Thread.sleep(1000);
        rs.getValue();
    }

    public static void main(String... args) throws InterruptedException{
        new ThreadSafe().executer();
    }

}
