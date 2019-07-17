package net.byte2data.consept.concurrency;

public enum ThreadEnum {
    PRODUCER(10,1),CONSUMER(100,10);
    ThreadEnum(int priority, int count){
        this.thradPriority=priority;
        this.threadCount=count;
    }
    private int threadCount;
    private int thradPriority;

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public int getThradPriority() {
        return thradPriority;
    }

    public void setThradPriority(int thradPriority) {
        this.thradPriority = thradPriority;
    }

    public static void main(String... args){
        ThreadEnum defaultThread = ThreadEnum.CONSUMER;
        for(ThreadEnum item : ThreadEnum.values()){

        }
    }
}
