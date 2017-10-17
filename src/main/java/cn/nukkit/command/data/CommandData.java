package cn.nukkit.command.data;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class CommandData implements Cloneable {

    public String[] aliases = new String[0];
    public String description = "description";
    public Object2ObjectMap<String, CommandOverload> overloads = new Object2ObjectOpenHashMap<>();
    public String permission = "any";

    @Override
    public CommandData clone() {
        try {
            return (CommandData) super.clone();
        } catch (Exception e) {
            return new CommandData();
        }
    }
}
