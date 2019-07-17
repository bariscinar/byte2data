package net.byte2data.tutorial.generics;

import java.util.*;

public class Drills {
    private static class Array2Collection{
        /*
        A method that takes an array of objects and a collection
        and puts all objects in the array into the collection.
        Generic methods allow type parameters to be used to express
        dependencies among the types of one or more arguments to a method and/or its return type.
        If there isn't such a dependency, a generic method should not be used.
        */
        private static  <T> void arrayToCollectionProcess(T[] tArray, Collection<T> tCollection){
            for(T item : tArray){
                tCollection.add(item);
            }
            tCollection.addAll(Arrays.asList(tArray));
        }

        private static  <T> void arrayToCollectionProcess2(T[] tArray, Collection<? super T> tCollection){
            for(T item : tArray){
                tCollection.add(item);
            }
            tCollection.addAll(Arrays.asList(tArray));
        }

        private static  <T, E extends T> void arrayToCollectionProcess3(E[] tArray, Collection<T> tCollection){
            for(T item : tArray){
                tCollection.add(item);
            }
            tCollection.addAll(Arrays.asList(tArray));
        }


        public static void main(String... args){
            Double[] doubles = {1.1,2.2,3.3,4.4};
            Integer[] integers = {1,2,3,4};
            Number[] numbers = {1.9,2,3.9,4};

            Collection<Number> numberCollection = new ArrayList<>();

            arrayToCollectionProcess(doubles,numberCollection);
            arrayToCollectionProcess2(doubles,numberCollection);
            arrayToCollectionProcess3(doubles,numberCollection);


        }
    }



    public static class Inventory {
        /**
         * Adds a new Assembly to the inventory database.
         * The assembly is given the name name, and
         * consists of a set parts specified by parts.
         * All elements of the collection parts
         * must support the Part interface.
         **/

        private static Map partMap = new HashMap();

        public interface Part {
        }
        public interface Assembly {
            // Returns a collection of Parts
            Collection getParts();
        }
        public static void addAssembly(String name, Collection parts) {
            partMap.put(name,parts);
        }
        public static Assembly getAssembly(String name) {
            return new Assembly() {
                @Override
                public Collection getParts() {
                    return partMap.values();
                }
            };
        }

        public class Blade implements Part {
        }
        public class Guillotine implements Part {
        }
        public static void main(String... args){
            Part aPart = new Part() {

            };
            Part bPart = new Part() {

            };
            Part cPart = new Part() {

            };
            Part dPart = new Part() {

            };

            Collection partList = new ArrayList<>();
            partList.add(aPart);
            partList.add(bPart);

            Inventory.addAssembly("aAssempbly", partList);
            Collection assemblyParts = Inventory.getAssembly("").getParts();
        }


    }


}
