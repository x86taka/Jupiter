package cn.nukkit.inventory.transaction;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.inventory.BigCraftingGrid;
import cn.nukkit.inventory.CraftingManager;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.network.protocol.types.ContainerIds;

public class CraftingTransaction extends SimpleInventoryTransaction {

	public int gridSize;

	public ArrayList<ArrayList<Item>> inputs = new ArrayList<>();
	public ArrayList<ArrayList<Item>> secondaryOutputs = new ArrayList<>();

	public Item primaryOutput;

	public CraftingManager recipe = null;

	public CraftingTransaction(Player source, List<InventoryAction> actions) {
		super(source, actions);

		this.gridSize = (source.getCraftingGrid() instanceof BigCraftingGrid) ? 3 : 2;
		Item item = Item.get(0);
		for (int i = 0; i < this.gridSize; i++) {
			ArrayList<Item> list = new ArrayList<>();
			for (int l = 0; l < this.gridSize; l++) {
				list.add(item);
			}
			this.inputs.add(list);
		}
		for (int i = 0; i < this.gridSize; i++) {
			ArrayList<Item> list = new ArrayList<>();
			for (int l = 0; l < this.gridSize; l++) {
				list.add(item);
			}
			this.secondaryOutputs.add(list);
		}
	}

	public void setInput(int index, Item item) {
		int y = index / this.gridSize;
		int x = index % this.gridSize;
		if (this.inputs.contains(y) && this.inputs.get(y).contains(x)) {
			this.inputs.get(y).set(index, item.clone());
		}
	}

	public ArrayList<ArrayList<Item>> getInputMap() {
		return this.inputs;
	}

	public Item getPrimaryOutput() {
		return this.primaryOutput;
	}

	public void setPrimaryOutput(Item item) {
		if (this.primaryOutput == null) {
			this.primaryOutput = item.clone();
		}
	}

	public CraftingManager getRecipe() {
		return this.recipe;
	}

	public ArrayList<ArrayList<Item>> reindexInputs() {
		int xOffset = this.gridSize;
		int yOffset = this.gridSize;

		int height = 0;
		int width = 0;

		for (int y = 0; y < this.inputs.size(); y++) {
			ArrayList<Item> row = this.inputs.get(y);
			for (int x = 0; x < row.size(); x++) {
				Item item = row.get(x);
				if (item != null) {
					xOffset = Math.min(x, xOffset);
					yOffset = Math.min(y, yOffset);

					height = Math.max(y + 1 - yOffset, height);
					width = Math.max(x + 1 - xOffset, width);
				}
			}
		}

		if (height == 0 || width == 0) {
			return new ArrayList<>();
		}

		Item air = Item.get(0);
		ArrayList<ArrayList<Item>> reindexed = new ArrayList<>();
		for (int i = 0; i < height; i++) {
			ArrayList<Item> list = new ArrayList<>();
			for (int l = 0; l < width; l++) {
				list.add(air);
			}
			reindexed.add(list);
		}

		for (int y = 0; y < this.inputs.size(); y++) {
			ArrayList<Item> row = this.inputs.get(y);
			for (int x = 0; x < row.size(); x++) {
				reindexed.get(y).set(x, this.inputs.get(y + yOffset).get(x + xOffset));
			}
		}
		return reindexed;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	public void sendInventories() {
		ContainerClosePacket pk = new ContainerClosePacket();
		pk.windowId = ContainerIds.NONE;
		this.source.dataPacket(pk);
	}
}
