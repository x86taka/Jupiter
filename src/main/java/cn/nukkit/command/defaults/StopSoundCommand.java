package cn.nukkit.command.defaults;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.network.protocol.StopSoundPacket;
import cn.nukkit.utils.TextFormat;

public class StopSoundCommand extends VanillaCommand {

    public StopSoundCommand(String name) {
        super(name, "%nukkit.command.stopsound.description", "%commands.stopsound.usage");
        this.setPermission("nukkit.command.stopsound");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParameter.ARG_TYPE_TARGET, false),
                new CommandParameter("sound", false, CommandParameter.ARG_TYPE_STRING)
        });
    }

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {

        if (!this.testPermission(sender)) {
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
            return true;
        }

        Player[] players = new Player[]{};

        if (args[0].equals("@e") || args[0].equals("@a")) {
            players = sender.getServer().getOnlinePlayers().values().toArray(new Player[0]);

        } else if (args[0].equals("@p") || args[0].equals("@s")) {
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(new TranslationContainer("commands.generic.ingame"));
                return true;
            } else {//TODO: CommandBlockCommandSender
                players = new Player[]{(Player) sender};
            }

        } else if (args[0].equals("@r")) {
            List<Player> list = Arrays.asList(sender.getServer().getOnlinePlayers().values().toArray(new Player[0]));
            if (list.size() <= 0) {
                sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.player.notFound"));
                return true;
            }
            Collections.shuffle(list);
            players = new Player[]{list.get(0)};

        } else {
            if (sender.getServer().getPlayer(args[0]) == null) {
                sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.player.notFound"));
                return true;
            }
            players = new Player[]{sender.getServer().getPlayer(args[0])};

        }

        String sound = args[1];
        
        Arrays.asList(players).forEach(p -> {
            StopSoundPacket pk = new StopSoundPacket();
            pk.name = sound;
            pk.stopAll = false;
            p.dataPacket(pk);
        });

		return false;
	}

}
