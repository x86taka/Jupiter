package cn.nukkit.command.defaults;

import java.io.File;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.plugin.PluginMaker;
import cn.nukkit.utils.TextFormat;

public class MakePluginCommand extends VanillaCommand{

    static CommandSender sender;
    int collect = 0;

    public MakePluginCommand(String name) {
        super(name, "指定されたフォルダをプラグインにします。", "/makeplugin [ファイル名(フルネーム)] or list");
        this.setPermission("nukkit.command.makeplugin");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("Filename", CommandParameter.ARG_TYPE_TARGET, false)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {

        if (!this.testPermission(sender)) {
            return true;
        }

        try{if(args[0] != null){}}
        catch(ArrayIndexOutOfBoundsException e){
            sender.sendMessage(TextFormat.RED + "ファイル名を入力してください。(またはlist)");
            return false;
        }

        if(args[0].equals("list")){
            File dir = new File(sender.getServer().getDataPath() + "makeOrder/");
            File[] files = dir.listFiles();
            sender.sendMessage(TextFormat.AQUA + "オーダーフォルダ一覧の表示:");
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                sender.sendMessage((i + 1) + ": " + TextFormat.GREEN +  file.getName());
            }
            return true;
        }

        sender.sendMessage(TextFormat.AQUA + "プラグインを作成しています...");
        if(new PluginMaker().Make(args[0]))
            sender.sendMessage(TextFormat.GREEN + "成功");
        else
            sender.sendMessage(TextFormat.RED + "失敗");

        return true;
    }
}