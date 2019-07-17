package net.byte2data.consept.datastructures.linkedlist.doubly;

public class DoublyLinkedList {

    private ListNode rootNode;
    private ListNode currentNode;

    public DoublyLinkedList(){
        this.rootNode=new ListNode(null,null,null);
        this.currentNode = rootNode;
    }

    public void addTaTail(ListData data){
        ListNode tailNode = new ListNode(data,currentNode,null);
        while(null!=currentNode.getNextNode()){
            currentNode=currentNode.getNextNode();
        }
        currentNode.setNextNode(tailNode);
    }
}
