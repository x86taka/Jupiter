package cn.nukkit.event.block;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.item.Item;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class BlockBreakEvent extends BlockEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected final Player player;

    protected final Item item;

    protected boolean instaBreak = false;
    protected Item[] blockDrops = new Item[0];

    protected boolean fastBreak = false;

    protected int dropExp;

    public BlockBreakEvent(Player player, Block block, Item item) {
        this(player, block, item, false, false);
    }

    public BlockBreakEvent(Player player, Block block, Item item, boolean instaBreak) {
        this(player, block, item, instaBreak, false);
    }

    public BlockBreakEvent(Player player, Block block, Item item, boolean instaBreak, boolean fastBreak) {
        this(player, block, item, instaBreak, fastBreak, 0);
    }

    public BlockBreakEvent(Player player, Block block, Item item, boolean instaBreak, boolean fastBreak, int dropExp) {
        super(block);
        this.item = item;
        this.player = player;
        this.instaBreak = instaBreak;
        this.blockDrops = player.isSurvival() ? block.getDrops(item) : new Item[0];
        this.fastBreak = fastBreak;
        this.dropExp = dropExp;
    }

    public Player getPlayer() {
        return player;
    }

    public Item getItem() {
        return item;
    }

    public boolean getInstaBreak() {
        return this.instaBreak;
    }

    public Item[] getDrops() {
        return blockDrops;
    }

    public void setDrops(Item[] drops) {
        this.blockDrops = drops;
    }

    public void setInstaBreak(boolean instaBreak) {
        this.instaBreak = instaBreak;
    }

    public boolean isFastBreak() {
        return this.fastBreak;
    }

    public int getDropExp() {
        return dropExp;
    }

    public void setDropExp(int dropExp) {
        this.dropExp = dropExp;
    }
}
