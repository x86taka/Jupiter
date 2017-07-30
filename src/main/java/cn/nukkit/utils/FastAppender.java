package cn.nukkit.utils;

public class FastAppender {

    public static String get(Object... objects){
        StringBuffer stringBuffer = new StringBuffer();
        for (Object obj : objects){
            stringBuffer.append(obj);
        }
        return stringBuffer.toString();
    }
}
