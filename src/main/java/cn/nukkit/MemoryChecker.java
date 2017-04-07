package cn.nukkit;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cn.nukkit.utils.TextFormat;

public class MemoryChecker {
	public static ArrayList<String> getMemoryInfo() {
		ArrayList<String> result = new ArrayList<String>();
	    DecimalFormat f1 = new DecimalFormat("#,###KB");
	    DecimalFormat f2 = new DecimalFormat("##.#");
	    long free = Runtime.getRuntime().freeMemory() / 1024;
	    long total = Runtime.getRuntime().totalMemory() / 1024;
	    long max = Runtime.getRuntime().maxMemory() / 1024;
	    long used = total - free;
	    double ratio = (used * 100 / (double)total);
	    result.add(TextFormat.AQUA + "--------------------------");
	    result.add(TextFormat.YELLOW + "Java メモリ情報");
	    result.add("合計: " + TextFormat.GREEN + f1.format(total));
	    result.add("使用量: " + TextFormat.GREEN + f1.format(used) + " (" + f2.format(ratio) + "%)");
	    result.add("使用可能最大: " + TextFormat.GREEN + f1.format(max));
	    result.add(TextFormat.AQUA + "--------------------------");
	    return result;
	}
}
