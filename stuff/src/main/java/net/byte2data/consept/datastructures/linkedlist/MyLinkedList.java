package net.byte2data.consept.datastructures.linkedlist;

public class MyLinkedList<T>{

    private static class StoredItem {
        private int key;
        private String value;
        public StoredItem(int k, String v){
            this.key=k;
            this.value=v;
        }
        public String getValue(int enteredKey){
            if(enteredKey==this.key)
                return this.value;
            throw new RuntimeException("wrong key");
        }

        @Override
        public String toString() {
            return "StoredItem{" +
                    "key=" + key +
                    ", value='" + value + '\'' +
                    '}';
        }

        public boolean equals(Object obj) {
            if(obj instanceof MyLinkedList.StoredItem){
                StoredItem compareItem = (StoredItem) obj;
                return compareItem.key==this.key;
            }
            return false;
        }
    }

    private T data;

    private MyLinkedList<T> nextNode;
    private MyLinkedList<T> previousNode;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private MyLinkedList<T> getNextNode() {
        return nextNode;
    }

    private void setNextNode(MyLinkedList<T> nextNode) {
        this.nextNode = nextNode;
    }

    private MyLinkedList<T> getPreviousNode() {
        return previousNode;
    }

    private void setPreviousNode(MyLinkedList<T> previousNode) {
        this.previousNode = previousNode;
    }

    public MyLinkedList(T data){
        this.data = data;
        this.nextNode = null;
        this.previousNode=null;
    }

    public void addToTail(T data){
        MyLinkedList<T> nextNode = new MyLinkedList<>(data);
        //MyLinkedList<T> previousNode = null;
        MyLinkedList<T> currentNode = this;
        while (null!=currentNode.getNextNode()) {
            currentNode = currentNode.getNextNode();
            //previousNode = currentNode.previousNode;
        }
        currentNode.setNextNode(nextNode);
        //currentNode.previousNode=previousNode;
        nextNode.setPreviousNode(currentNode);
    }

    public void removeFromTail(T data){
        MyLinkedList<T> currentNode = this;
        //Listenin başına git
        while (null!=currentNode.getPreviousNode()){
            currentNode=currentNode.getPreviousNode();
        }
        System.out.println("Listenin başı ->" + currentNode.getData().toString());
        //Listenin başından itibaren verilen datayı ara;
        while(null!=currentNode.getNextNode()){
            if(currentNode.getData().equals(data)){
                System.out.println("data found");
                currentNode.getNextNode().setPreviousNode(currentNode.getPreviousNode());
                currentNode.getPreviousNode().setNextNode(currentNode.getNextNode());
                break;
            }
            currentNode=currentNode.getNextNode();
        }
    }



    private static class TestLinkedList {

        public static void main(String... args){

            MyLinkedList<MyLinkedList.StoredItem> storedItemNode = new MyLinkedList<>(new MyLinkedList.StoredItem(1,""));
            storedItemNode.addToTail(new StoredItem(2,"iki"));
            storedItemNode.addToTail(new StoredItem(3,"üç"));
            storedItemNode.addToTail(new StoredItem(4,"dort"));
            storedItemNode.addToTail(new StoredItem(5,"bes"));
            storedItemNode.addToTail(new StoredItem(6,"alti"));
            storedItemNode.addToTail(new StoredItem(7,"yedi"));
            storedItemNode.addToTail(new StoredItem(8,"sekiz"));
            storedItemNode.addToTail(new StoredItem(9,"dokuz"));
            storedItemNode.addToTail(new StoredItem(0,"sifir"));

            storedItemNode.removeFromTail(new StoredItem(6,"KIRKBES"));

        }

    }


}