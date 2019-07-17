package net.byte2data.consept.concurrency.concept.immuttableobjects;

public class Sample {
    private class Resource{
        private String immutableVariable;
        public Resource(String var){
            this.immutableVariable=var;
        }
        public String getVar(){
            return this.immutableVariable;
        }

        public Resource setVarImmutable(String newVar){
            return new Resource((immutableVariable + newVar));
        }

        public String setVar(String newVar){
            immutableVariable= immutableVariable + newVar;
            return immutableVariable;
        }
    }

    public class Calculator {
        private Resource current = null;

        public Resource getCurrent() {
            return current;
        }

        public void setCurrent(Resource newResource){
            this.current=newResource;
        }

        public void add(String newVal){
            this.current=this.current.setVarImmutable(newVal);
        }
    }

    private class Executer implements Runnable{
        private Resource localResource;
        public Executer(Resource rs){
            this.localResource=rs;
        }
        public void run(){
            localResource.setVarImmutable("X");
            //localResource.setVar("X");
            System.out.println(localResource.getVar());
        }
    }
    private void executer(){
        Resource rs = new Resource("X");
        for(int index=0; index<10; index++){
            new Thread(new Executer(rs)).start();
        }
    }
    public static void main(String... args){
        new Sample().executer();
    }
}
