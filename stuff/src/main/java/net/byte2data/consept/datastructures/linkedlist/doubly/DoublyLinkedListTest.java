package net.byte2data.consept.datastructures.linkedlist.doubly;

public class DoublyLinkedListTest {

    private class Node{

        private String data;
        private Node nextNode;
        private Node previousNode;

        public Node(String data, Node previous, Node next){
            this.data=data;
            this.previousNode=previous;
            this.nextNode=next;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public Node getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node nextNode) {
            this.nextNode = nextNode;
        }

        public Node getPreviousNode() {
            return previousNode;
        }

        public void setPreviousNode(Node previousNode) {
            this.previousNode = previousNode;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data='" + data + '\'' +
                    ", nextNode=" + nextNode +
                    ", previousNode=" + previousNode +
                    '}';
        }
    }

    private Node headNode;
    private Node currentNode;

    public DoublyLinkedListTest(){
        headNode=new Node(null,null,null);
        currentNode=headNode;
    }

    public void addToTail(String data){
        Node nextNode = new Node(data,null,null);
        forwardingThroughNodes();
        currentNode.setNextNode(nextNode);
        nextNode.setPreviousNode(currentNode);

    }

    public void removeFromTail(String data){
            //Başlangıc NODE'una gitmek için bunu yapıyoruz ama direk HEAD de kullanılabilir.
            currentNode=headNode;
            while (null!=currentNode.getNextNode()){
                if((null!=currentNode.getData()) && (currentNode.getData().equals(data))){
                    System.out.println("Data Found -> " + currentNode.getData());
                    currentNode.getPreviousNode().setNextNode(currentNode.getNextNode());
                    if(null!=currentNode.getNextNode()) {
                        currentNode.getNextNode().setPreviousNode(currentNode.getPreviousNode());
                    }
                    System.out.println("Node Removed!");
                    break;
                }
                currentNode = currentNode.getNextNode();
            }
    }

    public void addAsFirst(String data){
        Node firstNode = new Node(data,null,null);
        firstNode.setNextNode(headNode.getNextNode());
        headNode.getNextNode().setPreviousNode(firstNode);
        headNode.setNextNode(firstNode);
        firstNode.setPreviousNode(headNode);
    }
    public void addAsLast(String data){
        Node lastNode = new Node(data,null,null);
        forwardingThroughNodes().setNextNode(lastNode);
        lastNode.setPreviousNode(forwardingThroughNodes());
    }

    private Node forwardingThroughNodes(){
        while(null!=currentNode.getNextNode()){
                System.out.println("iterating: " + currentNode.getData());
                currentNode=currentNode.getNextNode();
            }
            System.out.println("final node: " + currentNode.getData());
            return currentNode;
    }

    public static void main(String... args){
        DoublyLinkedListTest otherLinkedList = new DoublyLinkedListTest();
        otherLinkedList.addToTail("1");
        otherLinkedList.addToTail("2");
        otherLinkedList.addToTail("3");
        otherLinkedList.addToTail("4");
        otherLinkedList.addToTail("5");
        otherLinkedList.addToTail("6");
        otherLinkedList.addToTail("7");

        otherLinkedList.removeFromTail("4");

        otherLinkedList.addToTail("8");

        otherLinkedList.addAsFirst("0");
        otherLinkedList.addAsLast("9");

    }

}
