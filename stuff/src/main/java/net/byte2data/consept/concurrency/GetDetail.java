package net.byte2data.consept.concurrency;

public final class GetDetail {
    public final static void getThreadDetails(String message) {
        Thread currentThread = Thread.currentThread();
        long threadId = currentThread.getId();
        String thredName = currentThread.getName();
        int threadPriority = currentThread.getPriority();
        System.out.format("%s|%s - %s%n", thredName, threadId, message);
    }

    public final static void getThreadDetails(String message, long id, String name) {
        System.out.format("%s|%s - %s%n", name, id, message);
    }
}
