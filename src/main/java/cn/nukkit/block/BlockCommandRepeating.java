package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.item.Item;

/**
 * @author Megapix96
 */
public class BlockCommandRepeating extends BlockCommand {

    public BlockCommandRepeating() {
        this(0);
    }

    public BlockCommandRepeating(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Repeating Command Block";
    }

    @Override
    public int getId() {
        return REPEATING_COMMAND_BLOCK;
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        //TODO
        return false;
    }
}
