package net.byte2data.consept.datastructures.linkedlist.doubly;

class ListNode{

    private ListData data;
    private ListNode previousNode;
    private ListNode nextNode;

    ListNode(ListData listData, ListNode previousNode, ListNode nextNode){
        this.data=listData;
        this.previousNode=previousNode;
        this.nextNode=nextNode;
    }

    public ListNode getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(ListNode previousNode) {
        this.previousNode = previousNode;
    }

    public ListNode getNextNode() {
        return nextNode;
    }

    public void setNextNode(ListNode nextNode) {
        this.nextNode = nextNode;
    }
}