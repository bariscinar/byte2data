package net.byte2data.consept.datastructures.tree.binary;

public class Sample {



    private BinaryTree rootBinaryTree;

    public Sample(){
        rootBinaryTree = new BinaryTree(null);
    }

    public void addNode(BinaryTree parentNode, Data data, Course course){
        BinaryTree aTree = new BinaryTree(data);
        if (course.getStatement().equals("sag"))
            parentNode.setRightNode(aTree);
        else
            parentNode.setLeftNode(aTree);

    }

    public void test(){

    }

    private class Data{
        private int key;
        private String value;

        public Data(int key, String value){
            this.key=key;
            this.value=value;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "key=" + key +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    private class BinaryTree{
        private Data data;
        private BinaryTree leftNode;
        private BinaryTree rightNode;

        public BinaryTree(Data data){
            this.data=data;
            this.leftNode=null;
            this.rightNode=null;
        }

        public BinaryTree getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(BinaryTree leftNode) {
            this.leftNode = leftNode;
        }

        public BinaryTree getRightNode() {
            return rightNode;
        }

        public void setRightNode(BinaryTree rightNode) {
            this.rightNode = rightNode;
        }
    }

    private enum Course{
        RIGHT("sag"), LEFT("sol");

        private String statement;

        Course(String statement){
            this.statement=statement;
        }

        public String getStatement() {
            return statement;
        }
    }
}
