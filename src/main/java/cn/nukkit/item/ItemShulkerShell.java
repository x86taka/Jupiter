package cn.nukkit.item;

/**
 * @author Megapix96
 */
public class ItemShulkerShell extends ItemTool {

    public ItemShulkerShell() {
        this(0, 1);
    }

    public ItemShulkerShell(Integer meta) {
        this(meta, 1);
    }

    public ItemShulkerShell(Integer meta, int count) {
        super(SHULKER_SHELL, meta, count, "Shulker Shell");
    }
}
