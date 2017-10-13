package cn.nukkit.command.defaults;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.level.Location;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.utils.TextFormat;

/**
 * Created on 2015/11/12 by Pub4Game and milkice.
 * Package cn.nukkit.command.defaults in project Nukkit .
 */
public class TeleportCommand extends VanillaCommand {
    public TeleportCommand(String name) {
        super(name, "%nukkit.command.tp.description", "%commands.tp.usage");
        this.setPermission("nukkit.command.teleport");
        this.commandParameters.clear();
        this.commandParameters.put("->Player", new CommandParameter[]{
                new CommandParameter("destination", CommandParameter.ARG_TYPE_TARGET, false),
        });
        this.commandParameters.put("Player->Player", new CommandParameter[]{
                new CommandParameter("victim", CommandParameter.ARG_TYPE_TARGET, false),
                new CommandParameter("destination", CommandParameter.ARG_TYPE_TARGET, false),
        });
        this.commandParameters.put("Player->Pos", new CommandParameter[]{
                new CommandParameter("victim", CommandParameter.ARG_TYPE_TARGET, false),
                new CommandParameter("destination", CommandParameter.ARG_TYPE_BLOCK_POS, false),
                /*new CommandParameter("y-rot", CommandParameter.ARG_TYPE_INT, true), //TODO
                new CommandParameter("x-rot", CommandParameter.ARG_TYPE_INT, true)*/
        });
        this.commandParameters.put("->Pos", new CommandParameter[]{
                new CommandParameter("destination", CommandParameter.ARG_TYPE_BLOCK_POS, false),
                /*new CommandParameter("y-rot", CommandParameter.ARG_TYPE_INT, true), //TODO
                new CommandParameter("x-rot", CommandParameter.ARG_TYPE_INT, true)*/
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length < 1 || args.length > 6) {
            sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
            return true;
        }
        CommandSender target;
        CommandSender origin = sender;
        if (args.length == 1 || args.length == 3) {
            if (sender instanceof Player) {
                target = sender;
            } else {
                sender.sendMessage(new TranslationContainer("commands.generic.ingame"));
                return true;
            }
            if (args.length == 1) {
                target = sender.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(TextFormat.RED + "Can't find player " + args[0]);
                    return true;
                }
            }
        } else {
            target = sender.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(TextFormat.RED + "Can't find player " + args[0]);
                return true;
            }
            if (args.length == 2) {
                origin = target;
                target = sender.getServer().getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(TextFormat.RED + "Can't find player " + args[1]);
                    return true;
                }
            }
        }
        if (args.length < 3) {
            ((Player) origin).teleport((Player) target, PlayerTeleportEvent.TeleportCause.COMMAND);
            Command.broadcastCommandMessage(sender, new TranslationContainer("commands.tp.success", new String[]{origin.getName(), target.getName()}));
            return true;
        } else if (((Player) target).getLevel() != null) {
        	
            int pos;
            
            if (args.length == 4 || args.length == 6) {
                pos = 1;
            } else {
                pos = 0;
            }
            
            double x;
            double y;
            double z;
            double yaw;
            double pitch;
        	
        	int times = 0;
            
            try {
            	
            	times = pos++;
            	
            	if(args[times].contains("~")){
            		x = this.parseArgs("x", args[times], target);
            	}else{
                    x = Double.parseDouble(args[times]);
            	}
            	
            	times = pos++;
            	
            	if(args[times].contains("~")){
            		y = this.parseArgs("y", args[times], target);
            	}else{
                    y = Double.parseDouble(args[times]);
            	}
            	
            	times = pos++;
            	
            	if(args[times].contains("~")){
            		z = this.parseArgs("z", args[times], target);
            	}else{
                    z = Double.parseDouble(args[times]);
            	}
            	
                yaw = ((Player) target).getYaw();
                pitch = ((Player) target).getPitch();
            } catch (NumberFormatException e1) {
                sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
                return true;
            }
            
            if (y < 0) y = 0;
            if (y > 256) y = 256;
            
            if (args.length == 6 || (args.length == 5 && pos == 3)) {
            	
            	times = pos++;
            	
            	if(args[times].contains("~")){
            		yaw = this.parseArgs("yaw", args[times], target);
            	}else{
                    yaw = Double.parseDouble(args[times]);
            	}
            	
            	times = pos++;
            	
            	if(args[times].contains("~")){
            		pitch = this.parseArgs("pitch", args[times], target);
            	}else{
            		pitch = Double.parseDouble(args[times]);
            	}
            	
            }
            
            ((Player) target).teleport(new Location(x, y, z, yaw, pitch, ((Player) target).getLevel()), PlayerTeleportEvent.TeleportCause.COMMAND);
            Command.broadcastCommandMessage(sender, new TranslationContainer("commands.tp.success.coordinates", new String[]{target.getName(), String.valueOf(NukkitMath.round(x, 2)), String.valueOf(NukkitMath.round(y, 2)), String.valueOf(NukkitMath.round(z, 2))}));
            
            return true;
        }
        sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
        return true;
    }
    
    public double parseArgs(String type, String str, CommandSender sender){
    	
    	double result = 0;
    	
    	switch(type){
    		case "x":
        		str = str.replaceAll("~", String.valueOf(((Player) sender).getX()));
        		break;
        		
    		case "y":
    			str = str.replaceAll("~", String.valueOf(((Player) sender).getY()));
        		break;
        		
    		case "z":
    			str = str.replaceAll("~", String.valueOf(((Player) sender).getZ()));
        		break;
        		
    		case "yaw":
    			str = str.replaceAll("~", String.valueOf(((Player) sender).getYaw()));
        		break;
        		
    		case "pitch":
    			str = str.replaceAll("~", String.valueOf(((Player) sender).getPitch()));
        		break;
    	}

    	try {    	
        	ScriptEngineManager manager = new ScriptEngineManager();
			result = (double) manager.getEngineByName("JavaScript").eval(str);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
    	
		return result;
    	
    }
}
