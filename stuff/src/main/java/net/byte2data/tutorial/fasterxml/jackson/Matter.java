package net.byte2data.tutorial.fasterxml.jackson;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Matter {
    private ObjectMapper objectMapper;
    public Matter(){
        objectMapper = new ObjectMapper();
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static class Car{
        private String brandName;
        private int doorCount;

        public Car(){
            this("naNome",0);
        }

        public Car(String brand, int door){
            this.brandName=brand;
            this.doorCount=door;
        }

        public String getBrandName() {
            return brandName;
        }

        public int getDoorCount() {
            return doorCount;
        }
    }

    private void readFromString(String jsonString){
        try {
            Car car = objectMapper.readValue(jsonString, Car.class);
            System.out.println("car brand = " + car.getBrandName());
            System.out.println("car doors = " + car.getDoorCount());
            Map<String,Object> jsonMap = objectMapper.readValue(jsonString, new TypeReference<Map<String,Object>>(){});
            for(Object object : jsonMap.values()){
                System.out.println(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readFromStringArray(String jsonStringArray){
        try {
            Car[] cars = objectMapper.readValue(jsonStringArray, Car[].class);
            List<Car> carList = objectMapper.readValue(jsonStringArray, new TypeReference<List<Car>>(){});
            for(Car car : carList){
                System.out.println("car brand = " + car.getBrandName());
                System.out.println("car doors = " + car.getDoorCount());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readFromRader(Reader jsonReader){
        try {
            Car car = objectMapper.readValue(jsonReader, Car.class);
            System.out.println("car brand = " + car.getDoorCount());
            System.out.println("car doors = " + car.getBrandName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readFromFile(String jsonFile){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(jsonFile).getFile());
            Car car = objectMapper.readValue(file, Car.class);
            System.out.println("car doors = " + car.getDoorCount() + " - car brand = " + car.getBrandName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readFromURL(String jsonURL){
        try {
            URL url = new URL(jsonURL);
            Car[] cars = objectMapper.readValue(url, Car[].class);
            for(Car car : cars){
                System.out.println("car doors = " + car.getDoorCount() + " - car brand = " + car.getBrandName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readFromInputStream(String jsonFile){
        try {
            InputStream input = new FileInputStream(jsonFile);
            Car[] cars = objectMapper.readValue(input, Car[].class);
            System.out.println("readFromInputStream cars:"+cars.length);
            for(Car car : cars){
                System.out.println("car doors = " + car.getDoorCount() + " - car brand = " + car.getBrandName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readFromByteArray(String json){
        try {
            byte[] input = json.getBytes();
            Car car = objectMapper.readValue(input, Car.class);
            System.out.println("car doors = " + car.getDoorCount() + " - car brand = " + car.getBrandName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String... agrs){
        String stringJson = "{ \"brandName\" : \"Mercedes\", \"doorCount\" : 5 }";
        String stringArrayJson = "[{\"brandName\":\"Mercedes\",\"doorCount\":5},{\"brandName\":\"BMW\",\"doorCount\":5}]";

        Reader readerJson = new StringReader(stringJson);

        String resourceFileJson = "json/car.json";
        String urlJson = "file:src/car.json";
        String fileJson = "src/car.json";

        System.out.println("Reading from string...");
        new Matter().readFromString(stringJson);

        System.out.println("Reading from string array...");
        new Matter().readFromStringArray(stringArrayJson);

        System.out.println("Reading from reader...");
        new Matter().readFromRader(readerJson);

        System.out.println("Reading from file...");
        new Matter().readFromFile(resourceFileJson);

        System.out.println("Reading from url...");
        new Matter().readFromURL(urlJson);

        System.out.println("Reading from input stream...");
        new Matter().readFromInputStream(fileJson);

        System.out.println("Reading from byte array...");
        new Matter().readFromByteArray(stringJson);


    }
}
