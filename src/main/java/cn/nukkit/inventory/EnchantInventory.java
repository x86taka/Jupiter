package cn.nukkit.inventory;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class EnchantInventory extends ContainerInventory {

    public EnchantInventory(Position position) {
        super(null, InventoryType.ENCHANT_TABLE);
        this.holder = new FakeBlockMenu(this, position);
    }

    @Override
    public FakeBlockMenu getHolder() {
        return (FakeBlockMenu) this.holder;
    }

    @Override
    public void onClose(Player who) {
        super.onClose(who);
        Item item = this.getItem(0);
        if (item.getId() != 0) {
            if (who.getInventory().canAddItem(item)) {
                who.getInventory().addItem(item);
            } else {
                FakeBlockMenu block = this.getHolder();
                block.getLevel().dropItem(block, item);
            }
        }
        item = this.getItem(1);
        if (item.getId() != 0) {
            if (who.getInventory().canAddItem(item)) {
                who.getInventory().addItem(item);
            } else {
                FakeBlockMenu block = this.getHolder();
                block.getLevel().dropItem(block, item);
            }
        }
    }
}
