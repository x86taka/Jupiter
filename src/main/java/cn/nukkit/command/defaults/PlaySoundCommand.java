package cn.nukkit.command.defaults;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.utils.TextFormat;

public class PlaySoundCommand extends VanillaCommand {

    private float volume = 400f;
    private float pitch = 1f;

    public PlaySoundCommand(String name) {
        super(name, "%nukkit.command.playsound.description", "%commands.playsound.usage");
        this.setPermission("nukkit.command.playsound");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("sound", CommandParameter.ARG_TYPE_STRING, false),
                new CommandParameter("player", false, CommandParameter.ARG_TYPE_TARGET),
                new CommandParameter("volume", CommandParameter.ARG_TYPE_INT, true),
                new CommandParameter("pitch", CommandParameter.ARG_TYPE_INT, true),
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

        if (args[1].equals("@e") || args[1].equals("@a")) {
            players = sender.getServer().getOnlinePlayers().values().toArray(new Player[0]);

        } else if (args[1].equals("@p") || args[1].equals("@s")) {
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(new TranslationContainer("commands.generic.ingame"));
                return true;
            } else {//TODO: CommandBlockCommandSender
                players = new Player[]{(Player) sender};
            }

        } else if (args[1].equals("@r")) {
            List<Player> list = Arrays.asList(sender.getServer().getOnlinePlayers().values().toArray(new Player[0]));
            if (list.size() <= 0) {
                sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.player.notFound"));
                return true;
            }
            Collections.shuffle(list);
            players = new Player[]{list.get(0)};

        } else {
            if (sender.getServer().getPlayer(args[1]) == null) {
                sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.player.notFound"));
                return true;
            }
            players = new Player[]{sender.getServer().getPlayer(args[1])};

        }

        String sound = args[0];

        if(args[2] != null) {
            try {
                volume = Float.parseFloat(args[2]);
            } catch(NumberFormatException e) {
                sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
                return false;
            }
        }

        if(args[3] != null) {
            try {
                pitch = Float.parseFloat(args[3]);
            } catch(NumberFormatException e) {
                sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
                return false;
            }
        }

        Arrays.asList(players).forEach(p -> {
            PlaySoundPacket pk = new PlaySoundPacket();
            pk.name = sound;
            pk.volume = volume;
            pk.pitch = pitch;
            pk.x = (int) p.getX();
            pk.y = (int) p.getY();
            pk.z = (int) p.getZ();
            p.dataPacket(pk);
        });

        return true;
    }

}
