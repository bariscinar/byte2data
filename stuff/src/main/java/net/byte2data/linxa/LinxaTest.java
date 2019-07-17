package net.byte2data.linxa;

import com.asimalp.commons.server.sql.Pool;
import com.asimalp.commons.server.util.Chrono;
import com.asimalp.commons.shared.model.Currency;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.linxa.connect.server.CustomObjectInputStreamProvider;
import com.linxa.connect.server.route.ReverseNameLookup;
import com.linxa.connect.server.routing.tree.PrefixTree;
import com.linxa.connect.server.routing.tree.PrefixTreeNode;
import net.byte2data.linxa.pojo.Address;
import net.byte2data.linxa.pojo.Employe;
import oracle.jdbc.proxy.annotation.Pre;
import org.mapdb.elsa.ElsaMaker;
import org.mapdb.elsa.ElsaSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.*;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class LinxaTest {
    /*
    private static Employe[] employees = new Employe[10];
    private static LoadingCache<Integer, Employe> loadingCache =
            CacheBuilder
            .newBuilder()
            .softValues()
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(
                new CacheLoader<Integer, Employe>() {
                    @Override
                    public Employe load(Integer key) throws Exception {
                        //Make expensive call if the "employee" cannot be found in the cache!
                        return getEmployeeById(key);
                        //return null;
                    }
                });

    private static Cache<Integer, Employe> cache =
            CacheBuilder
            .newBuilder()
            //.softValues()
            //.maximumSize(2)
            .expireAfterWrite(5, TimeUnit.SECONDS).build();

    static {
        for(int index = 0; index<10; index++){
            employees[index] = new Employe(index, "Name-"+index,
                    new Address("Sokak-"+index,
                    "Cadde-"+index,
                    "Mahalle-"+index,
                    index,
                    index),
                    (double)index*index);
        }
    }

    private static Employe getEmployeeById(Integer key){
        System.out.print("Making expensive call due to not found employee in the cache -> " + key + " -> ");
        return employees[key];
    }

    private static String present(Date now){
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss SSS Z").format(now);
    }

    public static long getDateDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);

    }
    */
    public static void main(String... args){
        /*
        try{
            System.out.println("putting id 2...");
            loadingCache.put(employees[2].getEmployeeId(), employees[2]);
            cache.put(employees[2].getEmployeeId(), employees[2]);
            System.out.println("putting id 3...");
            loadingCache.put(employees[3].getEmployeeId(), employees[3]);
            cache.put(employees[3].getEmployeeId(), employees[3]);

            System.out.println("From LoadingCache 2: " + loadingCache.get(2));
            System.out.println("From LoadingCache 3: " + loadingCache.get(3));
            System.out.println("From Cache 2: " + cache.getIfPresent(2));
            System.out.println("From Cache 3: " + cache.getIfPresent(3));

            System.out.println("From LoadingCache 4: " + loadingCache.get(4));
            System.out.println("From Cache 4: " + cache.getIfPresent(4));
            Thread.sleep(5000);
            System.out.println("From LoadingCache 5: " + loadingCache.get(5));
            System.out.println("From Cache 5: " + cache.getIfPresent(5));
            Thread.sleep(5000);
            System.out.println("From LoadingCache 6: " + loadingCache.get(6));
            System.out.println("From Cache 6: " + cache.getIfPresent(6));
        }catch (ExecutionException |InterruptedException ex){
            System.out.println("ex: " + ex);
        }
        */
        Pool p = new Pool("Test", "jdbc:mysql://192.168.0.43:3306/connect?autoReconnect=true&useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "l1nx@3!", new CustomObjectInputStreamProvider());

        try {

            Kryo kryo = new Kryo();
            kryo.setReferences(true);

            Object start = Chrono.start();
            long completed = 0;

            //KRYO ATTRIBUTES

            /*
            kryo.setRegistrationRequired(false);
            kryo.register(PrefixTree.class);
            kryo.register(PrefixTreeNode.class);
            kryo.register(HashSet.class);
            kryo.register(Date.class);
            kryo.register(Integer.class);
            kryo.register(Currency.class);
            kryo.register(ReverseNameLookup.class);
            kryo.register(ArrayList.class);
            kryo.register(com.linxa.connect.shared.model.Target.class);
            kryo.register(com.asimalp.commons.shared.model.SafeDate.class);
            */
            /*
            //Employee TEST
            //kryo.register(Employe[].class);
            //kryo.register(Employe.class);
            //kryo.register(Address.class);
            */

            /*
            //Default Constructor Issue
            */
            kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));

            /*
            //Serialization Impl
            */
            //kryo.setDefaultSerializer( JavaSerializer.class );

            //LINXA REAL OBJECT TEST
            System.out.println("Getting from DataSource...");
            PrefixTree mainObject = p.o().r(8190233);
            completed = Chrono.stop(start);
            System.out.println(mainObject.getBuildDate());
            System.out.println("Getting from DataSource completed in " + completed/1000 + " msec.");

            //Employee TEST - Fill Data
            //Employe[] mainObject = employees;


            ObjectOutputStream objectOutputStreamJavaSer = new ObjectOutputStream(new FileOutputStream("/tmp/java.ser"));
            Output objectOutputStreamKryoSer= new Output(new FileOutputStream("/tmp/kryo.ser"));

            /*
            System.out.println("----------------------------");
            System.out.println("JAVA Serialization starting...");
            start = Chrono.start(start);
            objectOutputStreamJavaSer.writeObject(mainObject);
            completed = Chrono.stop(start);
            objectOutputStreamJavaSer.close();
            System.out.println("JAVA Serialization completed in " + completed/1000 + " sec.");
               */


            System.out.println("----------------------------");
            System.out.println("KRYO Serialization starting...");
            start = Chrono.start(start);
            //kryo.writeClassAndObject(objectOutputStreamKryoSer, mainObject);
            kryo.writeObject(objectOutputStreamKryoSer, mainObject);
            completed = Chrono.stop(start);
            objectOutputStreamKryoSer.close();
            System.out.println("KRYO Serialization completed in " + completed/1000 + " sec.");




            /*
            System.out.println("----------------------------");
            System.out.println("JAVA Deserialization starting...");
            start = Chrono.start(start);
            ObjectInputStream objectInputStreamJavaSer = new ObjectInputStream(new FileInputStream("/tmp/java.ser"));

            //LINXA PREFIXTREE
            PrefixTree objectFromJava = (PrefixTree) objectInputStreamJavaSer.readObject();

            //EMPLOYEE
            //Employe[] objectFromJava = (Employe[]) objectInputStreamJavaSer.readObject();

            completed = Chrono.stop(start);
            System.out.println("JAVA Deserialization completed in " + completed/1000 + " sec.");
            System.out.println("JAVA Deserialized Object Size: " + objectFromJava.getAll().size());
            System.out.println(objectFromJava.toString());
            //for(int index=0; index< objectFromJava.length; index++)
            //    System.out.println(objectFromJava[index].toString());
            */
            System.out.println("----------------------------");
            System.out.println("KRYO Deserialization starting...");
            start = Chrono.start(start);
            Input objectInputStreamKryoSer = new Input(new FileInputStream("/tmp/kryo.ser"));

            //LINXA PREFIXTREE
            //PrefixTree objectFromKryo = (PrefixTree) kryo.readClassAndObject(objectInputStreamKryoSer);
            PrefixTree objectFromKryo = (PrefixTree) kryo.readObject(objectInputStreamKryoSer, PrefixTree.class);

            //EMPOYEE
            //Employe[] objectFromKryo = (Employe[]) kryo.readClassAndObject(objectInputStreamKryoSer);
            //Employe[] objectFromKryo = (Employe[]) kryo.readObject(objectInputStreamKryoSer, Employe[].class);

            completed = Chrono.stop(start);
            System.out.println("KRYO Deserialization completed in " + completed/1000 + " sec.");

            //LINXA
            System.out.println("KRYO Deserialized Object Size: " + objectFromKryo.getAll().size());

            System.out.println(objectFromKryo.toString());

            //EMPLOYEE
            /*
            for(int index=0; index< objectFromKryo.length; index++)
                System.out.println(objectFromKryo[index].toString());
            */

            System.out.println("----------------------------");


            /*
            for(int index=0; index<100001; index++){
                Chrono.start();
            }
            for (Field field : Chrono.class.getDeclaredFields()){
                System.out.println("field name: " + field.getName());
                System.out.println("field type: " + field.getType().getName());
                System.out.println("field generic string: " + field.toGenericString());
                field.setAccessible(true);
                Object value = field.get(Chrono.class);
                System.out.println(value);
            }
             */



        //}catch (IOException | InterruptedException | ClassNotFoundException e){
        }catch (Exception e){
            System.out.println("Exception: " + e);
        } finally {
            p.kill();

        }



    }









}
