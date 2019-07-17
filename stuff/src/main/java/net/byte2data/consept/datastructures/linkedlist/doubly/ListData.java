package net.byte2data.consept.datastructures.linkedlist.doubly;

class ListData{
    int key;
    String data;
    ListData(int key, String data){
        this.key=key;
        this.data=data;
    }

    String getData(int key){
        if(this.key==key)
            return this.data;
        throw new NoSuchFieldError("Value not found");
    }
}
