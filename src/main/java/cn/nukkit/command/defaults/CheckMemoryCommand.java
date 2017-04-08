package cn.nukkit.command.defaults;

import java.util.ArrayList;

import cn.nukkit.MemoryChecker;
import cn.nukkit.command.CommandSender;

public class CheckMemoryCommand extends VanillaCommand{
	
	public CheckMemoryCommand(String name) {
        super(name,
                "Javaのメモリ使用量の確認",
                "/checkmemory"
        );
        this.setPermission("nukkit.command.checkmemory");
        this.commandParameters.clear();
    }

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (!this.testPermission(sender)) {
            return true;
        }
		ArrayList<String> list = new ArrayList<String>();
		list = MemoryChecker.getMemoryInfo();
		for(int i=0;list.size()>i;i++){
			sender.sendMessage(list.get(i));
		}
		return true;
	}

}
