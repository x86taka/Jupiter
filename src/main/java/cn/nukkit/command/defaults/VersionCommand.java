package cn.nukkit.command.defaults;

import java.util.List;

import cn.nukkit.command.CommandSender;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginDescription;
import cn.nukkit.utils.TextFormat;

/**
 * Created on 2015/11/12 by xtypr.
 * Package cn.nukkit.command.defaults in project Nukkit .
 */
public class VersionCommand extends VanillaCommand {

    public VersionCommand(String name) {
        super(name,
                "%nukkit.command.version.description",
                "%nukkit.command.version.usage",
                new String[]{"ver", "about"}
        );
        this.setPermission("nukkit.command.version");
        this.commandParameters.clear();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(new TranslationContainer("nukkit.server.info.extended", new String[]{
                    sender.getServer().getName(),
                    sender.getServer().getNukkitVersion(),
                    sender.getServer().getCodename(),
                    sender.getServer().getApiVersion(),
                    sender.getServer().getVersion(),
                    String.valueOf(ProtocolInfo.CURRENT_PROTOCOL)
            }) + "使用しているJupiterのバージョンは" + sender.getServer().getJupiterVersion() + "です。");
        } else {
            String pluginName = "";
            for (String arg : args) pluginName += arg + " ";
            pluginName = pluginName.trim();
            final boolean[] found = {false};
            final Plugin[] exactPlugin = {sender.getServer().getPluginManager().getPlugin(pluginName)};

            if (exactPlugin[0] == null) {
                pluginName = pluginName.toLowerCase();
                final String finalPluginName = pluginName;
                sender.getServer().getPluginManager().getPlugins().forEach((s, p) -> {
                    if (s.toLowerCase().contains(finalPluginName)) {
                        exactPlugin[0] = p;
                        found[0] = true;
                    }
                });
            } else {
                found[0] = true;
            }

            if (found[0]) {
                PluginDescription desc = exactPlugin[0].getDescription();
                sender.sendMessage(TextFormat.DARK_GREEN + desc.getName() + TextFormat.WHITE + " version " + TextFormat.DARK_GREEN + desc.getVersion());
                if (desc.getDescription() != null) {
                    sender.sendMessage(desc.getDescription());
                }
                if (desc.getWebsite() != null) {
                    sender.sendMessage("Website: " + desc.getWebsite());
                }
                List<String> authors = desc.getAuthors();
                final String[] authorsString = {""};
                authors.forEach((s) -> authorsString[0] += s);
                if (authors.size() == 1) {
                    sender.sendMessage("Author: " + authorsString[0]);
                } else if (authors.size() >= 2) {
                    sender.sendMessage("Authors: " + authorsString[0]);
                }
            } else {
                sender.sendMessage(new TranslationContainer("nukkit.command.version.noSuchPlugin"));
            }
        }
        return true;
    }
}
