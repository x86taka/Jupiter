package cn.nukkit.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.utils.TextFormat;

public class MuteCommand extends VanillaCommand {

    public MuteCommand(String name) {
        super(name, "自分をミュート状態にします。", "/mute");
        this.setPermission("nukkit.command.mute");
        this.commandParameters.clear();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {

        if (!this.testPermission(sender)) {
            return true;
        }

        if(sender instanceof ConsoleCommandSender){
        	sender.sendMessage(TextFormat.RED + "コンソールはミュートできません。");
        	return true;
        }

        Player p = (Player)sender;

        if(p.mute){
        	p.mute = false;
        	p.sendImportantMessage(p.getName() + "が" + TextFormat.GREEN + "ミュートを解除しました。");
        	return true;
        }else{
        	p.mute = true;
        	p.sendImportantMessage(p.getName() + "が" + TextFormat.RED + "ミュートを有効にしました。");
        	return true;
        }
    }

}
