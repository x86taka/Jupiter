package cn.nukkit.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;

public class TransferCommand extends VanillaCommand{

	

	public TransferCommand(String name) {
		super(name, "%nukkit.command.transfer.description", "/transfer [ip] [port]");
		this.setPermission("nukkit.command.transfer");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("ip", CommandParameter.ARG_TYPE_TARGET, false),
                new CommandParameter("port")
        });
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		
		if (!this.testPermission(sender)) {
            return true;
        }
		if(sender instanceof ConsoleCommandSender){
			sender.sendMessage(TextFormat.RED + "ゲーム内で実行してください。");
			return false;
		}
		
		Player p = (Player)sender;
		
		try{if(args[0] != null){}}
		catch(ArrayIndexOutOfBoundsException e){
			p.sendMessage(TextFormat.RED + "ipを入力してください。");
			return false;
		}
		try{if(args[1] != null){}}
		catch(ArrayIndexOutOfBoundsException e){
			p.sendMessage(TextFormat.RED + "ポートを入力してください。");
			return false;
		}
		try{
			Integer.parseInt(args[1]);
		}catch(NumberFormatException e){
			p.sendMessage(TextFormat.RED + "ポートには数値を入力してください。");
			return false;
		}
		
		p.transferServer(args[0], Integer.parseInt(args[1]));
		
		return true;
	}

}
