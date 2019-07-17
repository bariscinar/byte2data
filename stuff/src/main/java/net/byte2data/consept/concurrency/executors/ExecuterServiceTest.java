package net.byte2data.consept.concurrency.executors;

import net.byte2data.consept.concurrency.GetDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


public class ExecuterServiceTest {
    private class Resource{
        private int capacityOfTheList;
        private int minimumSizeOfTheList;
        private volatile int value;
        private AtomicInteger increasedValue;
        private List<Integer> linkedList;

        Resource(int capacity){
            this.capacityOfTheList=capacity;
            this.value=0;
            this.minimumSizeOfTheList=0;
            this.increasedValue = new AtomicInteger(0);
            linkedList = new ArrayList<>();
        }

        synchronized void put(int val){
            while(linkedList.size()==capacityOfTheList) {
                try{
                    GetDetail.getThreadDetails("waiting to put: " + val);
                    wait();
                }catch (InterruptedException ie){
                    GetDetail.getThreadDetails(ie.getMessage());
                }
            }
            GetDetail.getThreadDetails("put: " + val);
            linkedList.add(val);
            notify();
        }

        synchronized int get(){
            int temp=-1;
            while (linkedList.size()==minimumSizeOfTheList){
                try{
                    GetDetail.getThreadDetails("waiting to get...");
                    wait();
                }catch (InterruptedException ie){
                    GetDetail.getThreadDetails(ie.getMessage());
                }
            }
            temp= linkedList.remove(minimumSizeOfTheList);
            GetDetail.getThreadDetails("get: " + temp);
            notify();
            return temp;
        }

        synchronized int increament(){
            return increasedValue.addAndGet(1);
            //return this.value++;
        }

    }
    private class Producer implements Runnable{
        private Resource resource;
        Producer(Resource rs){
            this.resource=rs;
        }
        public void run(){
            resource.put(resource.increament());
        }
    }
    private class Consumer implements Runnable{
        private Resource resource;
        Consumer(Resource rs){
            this.resource=rs;
        }
        public void run(){
            resource.get();
        }
    }

    void runner () throws InterruptedException{

        ExecutorService executorService = Executors.newFixedThreadPool(300);
        Resource rs = new Resource(10);
        Producer pr = new Producer(rs);
        Consumer cs = new Consumer(rs);

        for(int index=0; index<100; index++){
            executorService.execute(pr);
            if(index%20==0)
                executorService.execute(cs);
        }

        Thread.sleep(5000);

        for(int index=0; index<100; index++){
            executorService.execute(cs);
        }

        //Future futureRunnableSubmit = executorService.submit(wr);
        /*
        Future futureCallableSubmit = executorService.submit(new Callable() {
            @Override
            public Object call() throws Exception {
                return "Callable Object";
            }
        });

        GetDetail.getThreadDetails("futureRunnableSubmit is done=" + futureRunnableSubmit.isDone());
        GetDetail.getThreadDetails("futureCallableSubmit is done=" + futureCallableSubmit.isDone());
        try{
            GetDetail.getThreadDetails("futureRunnableSubmit.get()->" + futureRunnableSubmit.get().toString());
            GetDetail.getThreadDetails("futureCallableSubmit.get()->" + futureCallableSubmit.get().toString());
        }catch (Exception ex){
            GetDetail.getThreadDetails("Exception: " + ex);
        }

        GetDetail.getThreadDetails("AFTER 2 SECS...");
        GetDetail.getThreadDetails("futureRunnableSubmit is done=" + futureRunnableSubmit.isDone());
        GetDetail.getThreadDetails("futureCallableSubmit is done=" + futureCallableSubmit.isDone());
        try{
            GetDetail.getThreadDetails("futureRunnableSubmit.get()->" + futureRunnableSubmit.get().toString());
            GetDetail.getThreadDetails("futureCallableSubmit.get()->" + futureCallableSubmit.get().toString());
        }catch (Exception ex){
            GetDetail.getThreadDetails("Exception: " + ex);
        }
        */
        Thread.sleep(2000);
        executorService.shutdown();
    }
    public static void main(String... args) throws InterruptedException{
        ExecuterServiceTest executerServiceTest = new ExecuterServiceTest();
        executerServiceTest.runner();

    }
}
