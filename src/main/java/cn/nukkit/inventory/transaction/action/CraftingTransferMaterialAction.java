package cn.nukkit.inventory.transaction.action;

import cn.nukkit.Player;
import cn.nukkit.inventory.transaction.CraftingTransaction;
import cn.nukkit.inventory.transaction.InventoryTransaction;
import cn.nukkit.item.Item;

public class CraftingTransferMaterialAction extends InventoryAction {

	public int slot;

	public CraftingTransferMaterialAction(Item sourceItem, Item targetItem, int slot) {
		super(sourceItem, targetItem);
		this.slot = slot;
	}

	public void onAddToTransaction(InventoryTransaction transaction) {
		if (transaction instanceof CraftingTransaction) {
			((CraftingTransaction) transaction).setInput(this.slot, this.targetItem);
		}
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
}
