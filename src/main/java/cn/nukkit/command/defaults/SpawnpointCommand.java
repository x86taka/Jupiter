package cn.nukkit.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;

/**
 * Created on 2015/12/13 by xtypr.
 * Package cn.nukkit.command.defaults in project Nukkit .
 */
public class SpawnpointCommand extends VanillaCommand {
    public SpawnpointCommand(String name) {
        super(name, "%nukkit.command.spawnpoint.description", "%commands.spawnpoint.usage");
        this.setPermission("nukkit.command.spawnpoint");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{});
        this.commandParameters.put("player", new CommandParameter[]{
                new CommandParameter("player", CommandParameter.ARG_TYPE_TARGET, false),
                new CommandParameter("spawnPos", CommandParameter.ARG_TYPE_BLOCK_POS, false),
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }

        Player target;
        if (args.length == 0) {
            if (sender instanceof Player) {
                target = (Player) sender;
                target.setSpawn(new Position((int) target.x + 0.5, target.y, (int) target.z + 0.5, target.getLevel()));
                Command.broadcastCommandMessage(sender, new TranslationContainer("commands.spawnpoint.success", new String[]{
                        target.getName(),
                        String.valueOf((int) (target.x)),
                        String.valueOf((int) (target.y)),
                        String.valueOf((int) (target.z))
                }));
                return true;
            } else {
                sender.sendMessage(new TranslationContainer("commands.generic.ingame"));
                return true;
            }
        } else {
            if (args.length != 4) {
                sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
                return true;
            }

            target = sender.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.player.notFound"));
                return true;
            }

            int x;
            int y;
            int z;
            try {
                x = Integer.parseInt(args[1]);
                y = Integer.parseInt(args[2]);
                z = Integer.parseInt(args[3]);
            } catch (NumberFormatException e1) {
                sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
                return true;
            }
            if (y < 0) y = 0;
            if (y > 256) y = 256;

            target.setSpawn(new Position(x + 0.5, y, z + 0.5, target.getLevel()));
            Command.broadcastCommandMessage(sender, new TranslationContainer("commands.spawnpoint.success", new String[]{
                    target.getName(),
                    String.valueOf(x),
                    String.valueOf(y),
                    String.valueOf(z)
            }));
            return true;
        }
    }
}
