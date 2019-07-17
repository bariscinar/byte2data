package net.byte2data.consept.concurrency.executors.implementations.forkjoin.recursiveaction;

import net.byte2data.consept.concurrency.GetDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MyRecursiveAction extends RecursiveAction {

    private final int threshold;
    private int load;

    MyRecursiveAction(int load){
        this.threshold=20;
        this.load=load;
    }

    void doSmt(int x){
        for(int index=0; index<x; index++){
            GetDetail.getThreadDetails("DoSmth:"+index);
        }
    }

    @Override
    protected void compute() {
        if(load<=threshold){
            GetDetail.getThreadDetails("doSmth");
            doSmt(load);
            return;
        }else{
            List<MyRecursiveAction> myRecursiveActions = new ArrayList<>();
            myRecursiveActions.addAll(createSamples());
            for(RecursiveAction action : myRecursiveActions){
                GetDetail.getThreadDetails("Action Forking");
                action.fork();
            }
        }

    }

    private List<MyRecursiveAction> createSamples(){
        List<MyRecursiveAction> sampleList = new ArrayList<>();
        MyRecursiveAction sample1 = new MyRecursiveAction(load/2);
        MyRecursiveAction sample2 = new MyRecursiveAction(load/2);
        sampleList.add(sample1);
        sampleList.add(sample2);
        return sampleList;
    }

    public static void main(String... args){
        MyRecursiveAction myRecursiveAction = new MyRecursiveAction(100);
        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        forkJoinPool.invoke(myRecursiveAction);
    }
}
