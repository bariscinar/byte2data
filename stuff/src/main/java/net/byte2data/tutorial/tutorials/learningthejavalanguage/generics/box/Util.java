package net.byte2data.tutorial.tutorials.learningthejavalanguage.generics.box;

/**
 * Created by barisci on 26.09.2017.
 */
public class Util {

    public static <K,V> boolean compare(Pair<K,V> pair1, Pair<K,V> pair2){
        return pair1.getValue().equals(pair2.getValue())&&pair1.getKey().equals(pair2.getKey());
    }

    static class Pair<K,V>{
        private K key;
        private V value;

        public Pair(K k, V v){
            this.key=k;
            this.value=v;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
