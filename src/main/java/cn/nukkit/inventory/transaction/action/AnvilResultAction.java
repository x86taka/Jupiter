package cn.nukkit.inventory.transaction.action;

import cn.nukkit.Player;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;

public class AnvilResultAction extends InventoryAction {

    private Inventory inventory;

    public AnvilResultAction(Inventory inventory, Item sourceItem, Item targetItem) {
        super(sourceItem, targetItem);
        this.inventory = inventory;
    }

    @Override
    public boolean isValid(Player source) {
    	return true;
    }

    @Override
    public boolean execute(Player source) {
        return true;
    }

    @Override
    public void onExecuteSuccess(Player source) {
    }

    @Override
    public void onExecuteFail(Player source) {
    }

    public Inventory getInventory() {
        return this.inventory;
    }

}
