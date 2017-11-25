package cn.nukkit.inventory;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;

public class BeaconInventory extends ContainerInventory {

    public BeaconInventory(Position position) {
        super(null, InventoryType.BEACON);
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
    }

}
