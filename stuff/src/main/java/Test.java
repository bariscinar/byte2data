import java.util.ArrayList;
import java.util.List;

public class Test {

    private static final String DELIMITER = "/";

    private static final String REPLACEMENT = "|||";


    private static String replaceCharacter(String param, String var1, String var2){
        if ( (param != null) && (!param.isEmpty()) ) {
            System.out.println("TrafficAndProfitibilityPlace.replaceCharacter - param:" + param);
            System.out.println("TrafficAndProfitibilityPlace.replaceCharacter - var1:" + var1);
            System.out.println("TrafficAndProfitibilityPlace.replaceCharacter - var2:" + var2);
            System.out.println("TrafficAndProfitibilityPlace.replaceCharacter - CHANGED:" + param.replace(var1, var2));
            return (param.replace(var1, var2));
        }
        return null;
    }

    public static void main(String... args){
        System.out.println(replaceCharacter("MY/Destination - YOUR/Destination", DELIMITER, REPLACEMENT));

        System.out.println(replaceCharacter("MY|||Destination - YOUR|||Destination", REPLACEMENT, DELIMITER));

    }

}
