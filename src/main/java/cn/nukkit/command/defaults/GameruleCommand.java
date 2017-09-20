package cn.nukkit.command.defaults;

import java.util.Arrays;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.lang.TranslationContainer;

/**
 * Created on 2017/9/20 by Megapix96.
 * Package cn.nukkit.command.defaults in project Jupiter .
 */
public class GameruleCommand extends VanillaCommand {

    public GameruleCommand(String name) {
        super(name, "%nukkit.command.gamerule.description", "%commands.gamerule.usage");
        this.setPermission("nukkit.command.give");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("rule", true, CommandParameter.GAMERULE_LIST)
        });
        this.commandParameters.put("byBoolean", new CommandParameter[]{
                new CommandParameter("rule", false, CommandParameter.GAMERULE_LIST),
                new CommandParameter("value", CommandParameter.ARG_TYPE_BOOL, false)
        });
        this.commandParameters.put("byInt", new CommandParameter[]{
                new CommandParameter("rule", false, CommandParameter.GAMERULE_LIST),
                new CommandParameter("value", CommandParameter.ARG_TYPE_INT, false)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (args.length == 0) {
            String commands = "";
            StringBuffer commandsBuffer = new StringBuffer();
            for (Player player : sender.getServer().getOnlinePlayers().values()) {
                if (player.isOnline() && (!(sender instanceof Player) || ((Player) sender).canSee(player))) {
                    commandsBuffer.append(player.getDisplayName() + ", ");
                }
            }

            commands = commandsBuffer.substring(0, commandsBuffer.length() - 2);
            sender.sendMessage(commands);

            return true;
        } else if (args.length == 1) {
            if (Arrays.asList(CommandParameter.GAMERULE_LIST).contains(args[0])) {
                //TODO: convert to camelCase
            } else {
                sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
                return true;
            }
        } else if (args.length == 2) {
            //TODO: convert to camelCase & setRule
        }

        sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
        return true;
    }
}
