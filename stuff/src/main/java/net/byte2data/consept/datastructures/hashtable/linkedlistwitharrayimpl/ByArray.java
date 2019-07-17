package net.byte2data.consept.datastructures.hashtable.linkedlistwitharrayimpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

@SuppressWarnings("unchecked")
public class ByArray {

    private static class KeyValueData{

        private int key;
        private String value;
        private KeyValueData(int k, String v){
            this.key=k;
            this.value=v;
        }

        @Override
        public String toString() {
            return "KeyValueData{" +
                    "key=" + key +
                    ", value='" + value + '\'' +
                    '}';
        }

        private String getDataValue(int key) throws NoSuchFieldError{
            if(this.key==key)
                return this.value;
            throw new NoSuchFieldError("Value cannot be found with key:"+key);
        }

        private String getValue(){
            return this.value;
        }

        private int getKey(){
            return this.key;
        }
    }

    private static int sizeOfGeneratedItem;
    private static KeyValueData[] generatedKeyValue;
    private static LinkedList<KeyValueData>[] linkedListArrayHashTable;

    static {
        sizeOfGeneratedItem = 100;
        generatedKeyValue = new KeyValueData[sizeOfGeneratedItem];

        for(int index = 0; index< sizeOfGeneratedItem; index++)
            generatedKeyValue[index] = new KeyValueData(new Random().nextInt(1000), "value-".concat(String.valueOf(index)));

        ArrayList<LinkedList<KeyValueData>> linkedListArrayList=new ArrayList<LinkedList<KeyValueData>>();
        linkedListArrayHashTable =new LinkedList[12];

        for(int index=0; index<linkedListArrayHashTable.length; index++)
            System.out.println(linkedListArrayHashTable[index]);
    }

    public void putData2HashTable(){
        int hash;
        int modIndex;

        for(KeyValueData keyValueData : generatedKeyValue){
            hash = String.valueOf(keyValueData.getKey()).hashCode();
            if(hash<0)
                hash*=-1;

            modIndex = hash % linkedListArrayHashTable.length;
            System.out.println("key: " + keyValueData.key + " - hash:"+hash + " - mod:"+ modIndex);
            if(null==linkedListArrayHashTable[modIndex]){
                linkedListArrayHashTable[modIndex]=new LinkedList<KeyValueData>();
            }
            linkedListArrayHashTable[modIndex].add(keyValueData);

        }
    }

    public void getDataFromHashTable(){
        KeyValueData temp;
        for(int index=0; index<linkedListArrayHashTable.length; index++){
            System.out.println("++++");
            System.out.println("for index: " + index);
            while(null!=(temp=linkedListArrayHashTable[index].poll())){
                System.out.println(temp.toString());
                //System.out.println("--");
            }
        }
        System.out.println("++++");
    }

    public static void main(String... args){
        //System.out.println("ByArray array size:" +  ByArray.sizeOfGeneratedItem);
        //System.out.println("ByArray generated key/value:" +  ByArray.generatedKeyValue);
        //System.out.println("ByArray hash table:" +  ByArray.linkedListArrayHashTable);

        ByArray byArray = new ByArray();
        System.out.println("ByArray instance array size:" +  byArray.sizeOfGeneratedItem);
        System.out.println("ByArray instance generated key/value size:" +  byArray.generatedKeyValue.length);
        System.out.println("ByArray instance hash table size:" +  byArray.linkedListArrayHashTable.length);

        byArray.putData2HashTable();
        byArray.getDataFromHashTable();

    }

}
