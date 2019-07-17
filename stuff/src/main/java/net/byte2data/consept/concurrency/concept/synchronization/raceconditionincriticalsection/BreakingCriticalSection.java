package net.byte2data.consept.concurrency.concept.synchronization.raceconditionincriticalsection;

public class BreakingCriticalSection {
    private class SharedResource{
        private int instanceVariable1=0, instanceVariable2=0;
        private final Integer lock1=0, lock2=0;
        public void addValue(int val1, int val2){

            this.instanceVariable1+=val1;
            this.instanceVariable2+=val2;

            //Local Variables are thread Safe
            int var=99;
            var +=var;
            var+=1;
            System.out.println("local variable: " + ++var);

            //Bu iki adamı ayrı ayrı synchronized yapmak daha doğruymuş,
            //Critical Section'ın alanını azaltmak Thread Contention'u azaltıyormuş.
            //Synchronized alan ne kadar küçük olursa ve ne kadar hızlı sürede biterse evet bekleme yapan adam azalır.
            //Kısacası Thread Contention azalır.
            /*
            synchronized (this) {
                this.instanceVariable1 += val1;
                this.instanceVariable2 += val2;
            }
            */

            //Onun yolu aşağıdaki gibi
            //synchronized (lock1){
                //this.instanceVariable1+=val1;
            //}
            //synchronized (lock2){
                //this.instanceVariable2+=val2;
            //}

            //Diğer yol da methodun tamamını sychronized yapmak ama o maliyeti arttırıyormuş.

        }
        public void getValue(){
            System.out.format("var1:%s - var2:%s",instanceVariable1,instanceVariable2);
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
        SharedResource rs = new SharedResource();
        MyThread myThread = new MyThread(rs);
        Thread th1 = new Thread(myThread);
        for(int index = 1; index<=10000; index++)
            new Thread(myThread).start();
        //Yahu arkadaş neden aşağıdakinin çalışmasına izin vermiyor???
        /*
        for(int index = 0; index<10; index++)
            th1.start();
        */
        Thread.sleep(10000);
        rs.getValue();
    }
    public static void main(String... args) throws InterruptedException{
        new BreakingCriticalSection().executer();
    }
}
