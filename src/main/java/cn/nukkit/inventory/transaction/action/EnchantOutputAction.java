package cn.nukkit.inventory.transaction.action;

import cn.nukkit.Player;
import cn.nukkit.item.Item;

public class EnchantOutputAction extends InventoryAction{

    public EnchantOutputAction(Item sourceItem, Item targetItem) {
        super(sourceItem, targetItem);
    }

    @Override
    public boolean isValid(Player source) {
        return true;
    }

    @Override
    public boolean execute(Player source) {
        if (this.targetItem.getId() == 351) {
            source.setExperience(source.getExperience(), source.getExperienceLevel() - this.targetItem.getCount());
        }
        return true;
    }

    @Override
    public void onExecuteSuccess(Player source) {
    }

    @Override
    public void onExecuteFail(Player source) {
    }

}
