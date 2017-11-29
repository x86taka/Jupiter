package cn.nukkit.inventory.transaction;

import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.inventory.transaction.action.EnchantInputAction;
import cn.nukkit.inventory.transaction.action.EnchantMaterialAction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.item.Item;

public class EnchantTransaction extends SimpleInventoryTransaction{

    public EnchantTransaction(Player source, List<InventoryAction> actions) {
        super(source, actions);

        InventoryAction check = null;
        for (InventoryAction action : actions) {
            if (check == null) {
                check = action;
            } else {
                if (check instanceof EnchantInputAction && action instanceof EnchantInputAction) {
                    ((EnchantInputAction) check).setSourceItem(action.getSourceItem());
                    this.actions.remove(action);
                } else if (check instanceof EnchantMaterialAction && action instanceof EnchantMaterialAction) {
                    ((EnchantMaterialAction) check).setSourceItem(action.getSourceItem());
                    this.actions.remove(action);
                } else {
                    check = action;
                }
            }
        }
    }

    @Override
    protected boolean matchItems(List<Item> needItems, List<Item> haveItems) {
        return true;
    }

}
