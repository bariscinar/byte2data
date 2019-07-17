package net.byte2data.tutorial.generics.typeerasure;

public class TypeErasure {
    public class Node<T>{
        public T tType;
        Node(T type){
            this.tType = type;
        }
        void setType(T type){
            System.out.println("Node.setType called for: "+type);
            this.tType=type;
        }
        T getType(){
            return this.tType;
        }
    }

    public class ExtendenNode extends Node<Integer>{
        ExtendenNode(int var){
            super(var);
        }
        void setType(int data){
            System.out.println("ExtendenNode.setType called for: "+data);
            super.setType(data);
        }
    }

    public void check(){
        ExtendenNode extendenNode = new ExtendenNode(1);
        Node stringNode = extendenNode;
        stringNode.setType("test");
        int x = extendenNode.tType;

    }

    public static void main(String... args){
        TypeErasure typeErasure = new TypeErasure();
        typeErasure.check();
    }
}
