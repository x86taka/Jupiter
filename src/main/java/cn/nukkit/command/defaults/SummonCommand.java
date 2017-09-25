package cn.nukkit.command.defaults;

import java.util.Random;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.entity.Entity;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.level.Position;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;

/**
 * Created on 2017/09/19 by Megapix96
 * Package cn.nukkit.command.defaults in project Jupiter .
 */
public class SummonCommand extends VanillaCommand {

    public SummonCommand(String name) {
        super(name, "%nukkit.command.summon.description", "%commands.summon.usage");
        this.setPermission("nukkit.command.summon");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("entityType", false, CommandParameter.ENUM_TYPE_ENTITY_LIST),
                new CommandParameter("spawnPos", CommandParameter.ARG_TYPE_BLOCK_POS, false)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(new TranslationContainer("commands.generic.ingame"));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
            return false;
        }

        Position pos = new Position(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), ((Player) sender).getLevel());

        CompoundTag nbt = new CompoundTag()
                .putList(new ListTag<DoubleTag>("Pos")
                        .add(new DoubleTag("", pos.getX() + 0.5))
                        .add(new DoubleTag("", pos.getY()))
                        .add(new DoubleTag("", pos.getZ() + 0.5)))
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0)))
                .putList(new ListTag<FloatTag>("Rotation")
                        .add(new FloatTag("", new Random().nextFloat() * 360))
                        .add(new FloatTag("", 0)));

        Entity entity = Entity.createEntity(args[0], pos.getLevel().getChunk((int) pos.getX() >> 4, (int) pos.getZ() >> 4, true), nbt);

        entity.spawnToAll();
        return true;
    }
}
