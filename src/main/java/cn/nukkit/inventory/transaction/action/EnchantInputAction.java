package cn.nukkit.inventory.transaction.action;

import cn.nukkit.Player;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;

public class EnchantInputAction extends InventoryAction{

    public Inventory inventory;

    public EnchantInputAction(Inventory inventory, Item sourceItem, Item targetItem) {
        super(sourceItem, targetItem);
        this.inventory = inventory;
    }

    @Override
    public boolean isValid(Player source) {
        Item check = inventory.getItem(0);

        return check.equalsExact(this.sourceItem);
    }

    @Override
    public boolean execute(Player source) {
        return this.inventory.setItem(0, this.targetItem, false);
    }

    @Override
    public void onExecuteSuccess(Player source) {
    }

    @Override
    public void onExecuteFail(Player source) {
    }

    public void setSourceItem(Item item) {
        this.sourceItem = item;
    }

}