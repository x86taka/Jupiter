package cn.nukkit.utils;

public class FastAppender {
	private static StringBuffer stringBuffer = new StringBuffer();

    public static String get(Object... objects){
    	stringBuffer.setLength(0);
    	for (Object obj : objects){
    		stringBuffer.append(obj);
    	}
		return stringBuffer.toString();
    }
}
