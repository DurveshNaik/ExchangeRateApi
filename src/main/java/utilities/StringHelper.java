package utilities;

public class StringHelper {
    public static Boolean isNotEmptyOrNull(String str){
        if (str != null && str.length() != 0)
            return true;
        return  false;
    }
}
