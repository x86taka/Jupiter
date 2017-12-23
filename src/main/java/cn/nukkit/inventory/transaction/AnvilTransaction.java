package cn.nukkit.inventory.transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.transaction.action.AnvilInputAction;
import cn.nukkit.inventory.transaction.action.AnvilMaterialAction;
import cn.nukkit.inventory.transaction.action.AnvilResultAction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBookEnchanted;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.MainLogger;

public class AnvilTransaction  implements InventoryTransaction {

    private long creationTime;
    private boolean hasExecuted;

    private Player source;

    private Set<Inventory> inventories = new HashSet<>();

    private Set<InventoryAction> actions = new HashSet<>();

    private Item result;
    private Item useInput;
    private Item useMaterial;

    private Inventory inventory;

    public AnvilTransaction(Player source, List<InventoryAction> actions) {
        creationTime = System.currentTimeMillis();
        this.source = source;

        for (InventoryAction action : actions) {
            if (action instanceof AnvilResultAction) {
                if (this.result == null) {
                    this.result = action.getSourceItem();
                    this.inventory = ((AnvilResultAction) action).getInventory();
                }
            } else if (action instanceof AnvilInputAction) {
                if (this.useInput == null) {
                    this.useInput = action.getSourceItem();
                }
                continue;
            } else if (action instanceof AnvilMaterialAction) {
                if (this.useMaterial == null) {
                    this.useMaterial = action.getTargetItem();
                }
                continue;
            }
            this.addAction(action);
        }
    }

    public Player getSource() {
        return source;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public Set<Inventory> getInventories() {
        return inventories;
    }

    @Override
    public Set<InventoryAction> getActions() {
        return actions;
    }

    public void addAction(InventoryAction action) {
        if (this.actions.contains(action)) {
            return;
        }

        if (action instanceof SlotChangeAction) {
            this.inventories.add(((SlotChangeAction) action).getInventory());
        }

        this.actions.add(action);
    }

    protected boolean matchItems(List<Item> needItems, List<Item> haveItems) {
        for (InventoryAction action : this.actions) {
            if (action.getTargetItem().getId() != Item.AIR) {
                needItems.add(action.getTargetItem());
            }

            if (!action.isValid(this.source)) {
                return false;
            }

            if (action.getSourceItem().getId() != Item.AIR) {
                haveItems.add(action.getSourceItem());
            }
        }

        for (Item needItem : new ArrayList<>(needItems)) {
            for (Item haveItem : new ArrayList<>(haveItems)) {
                if (needItem.equals(haveItem)) {
                    int amount = Math.min(haveItem.getCount(), needItem.getCount());
                    needItem.setCount(needItem.getCount() - amount);
                    haveItem.setCount(haveItem.getCount() - amount);
                    if (haveItem.getCount() == 0) {
                        haveItems.remove(haveItem);
                    }
                    if (needItem.getCount() == 0) {
                        needItems.remove(needItem);
                        break;
                    }
                }
            }
        }

        return haveItems.isEmpty() && needItems.isEmpty();
    }

    /**
     * Iterates over SlotChangeActions in this transaction and compacts any which refer to the same inventorySlot in the same
     * inventory so they can be correctly handled.
     * <p>
     * Under normal circumstances, the same inventorySlot would never be changed more than once in a single transaction. However,
     * due to the way things like the crafting grid are "implemented" in MCPE 1.2 (a.k.a. hacked-in), we may get
     * multiple inventorySlot changes referring to the same inventorySlot in a single transaction. These multiples are not even guaranteed
     * to be in the correct order (inventorySlot splitting in the crafting grid for example, causes the actions to be sent in the
     * wrong order), so this method also tries to chain them into order.
     *
     * @return bool
     */
    protected boolean squashDuplicateSlotChanges() {
        Map<Integer, List<SlotChangeAction>> slotChanges = new HashMap<>();

        for (InventoryAction action : this.actions) {
            if (action instanceof SlotChangeAction) {
                int hash = Objects.hash(((SlotChangeAction) action).getInventory(), ((SlotChangeAction) action).getSlot());

                List<SlotChangeAction> list = slotChanges.get(hash);
                if (list == null) {
                    list = new ArrayList<>();
                }

                list.add((SlotChangeAction) action);

                slotChanges.put(hash, list);
            }
        }

        for (Entry<Integer, List<SlotChangeAction>> entry : new ArrayList<>(slotChanges.entrySet())) {
            int hash = entry.getKey();
            List<SlotChangeAction> list = entry.getValue();

            if (list.size() == 1) { //No need to compact inventorySlot changes if there is only one on this inventorySlot
                slotChanges.remove(hash);
                continue;
            }

            List<SlotChangeAction> originalList = new ArrayList<>(list);

            SlotChangeAction originalAction = null;
            Item lastTargetItem = null;

            for (int i = 0; i < list.size(); i++) {
                SlotChangeAction action = list.get(i);

                if (action.isValid(this.source)) {
                    originalAction = action;
                    lastTargetItem = action.getTargetItem();
                    list.remove(i);
                    break;
                }
            }

            if (originalAction == null) {
                return false; //Couldn't find any actions that had a source-item matching the current inventory inventorySlot
            }

            int sortedThisLoop;

            do {
                sortedThisLoop = 0;
                for (int i = 0; i < list.size(); i++) {
                    SlotChangeAction action = list.get(i);

                    Item actionSource = action.getSourceItem();
                    if (actionSource.equalsExact(lastTargetItem)) {
                        lastTargetItem = action.getTargetItem();
                        list.remove(i);
                        sortedThisLoop++;
                    } else if (actionSource.equals(lastTargetItem)) {
                        lastTargetItem.count -= actionSource.count;
                        list.remove(i);
                        if (lastTargetItem.count == 0) sortedThisLoop++;
                    }
                }
            } while (sortedThisLoop > 0);

            if (list.size() > 0) { //couldn't chain all the actions together
                MainLogger.getLogger().debug("Failed to compact " + originalList.size() + " actions for " + this.source.getName());
                return false;
            }

            for (SlotChangeAction action : originalList) {
                this.actions.remove(action);
            }

            this.addAction(new SlotChangeAction(originalAction.getInventory(), originalAction.getSlot(), originalAction.getSourceItem(), lastTargetItem));

            MainLogger.getLogger().debug("Successfully compacted " + originalList.size() + " actions for " + this.source.getName());
        }

        return true;
    }

    public boolean canExecute() {
        this.squashDuplicateSlotChanges();

        List<Item> haveItems = new ArrayList<>();
        List<Item> needItems = new ArrayList<>();
        return matchItems(needItems, haveItems) && this.actions.size() > 0 && haveItems.size() == 0 && needItems.size() == 0;
    }

    /**
     * @return bool
     */
    public boolean execute() {
        if (this.hasExecuted() || !this.canExecute()) {
            return false;
        }

        InventoryTransactionEvent ev = new InventoryTransactionEvent(this);
        Server.getInstance().getPluginManager().callEvent(ev);
        if (ev.isCancelled()) {
            this.handleFailed();
            return false;
        }

        for (InventoryAction action : this.actions) {
            if (!action.onPreExecute(this.source)) {
                this.handleFailed();
                return false;
            }
        }

        for (InventoryAction action : this.actions) {
            if (action.execute(this.source)) {
                action.onExecuteSuccess(this.source);
            } else {
                action.onExecuteFail(this.source);
            }
        }

        this.inventory.setItem(0, Item.get(0), false);
        if (this.useMaterial != null) {
            Item item = this.inventory.getItem(1);
            if (item.getCount() - this.useMaterial.getCount() < 1) {
                item = Item.get(0);
            } else {
                item = item.setCount(item.getCount() - this.useMaterial.getCount());
            }
            this.inventory.setItem(1, item, false);
        }

        int cost = this.useInput.getRepairCost();
        if (!this.useInput.getCustomName().equals(this.result.getCustomName())) {
            cost++;
        }
        if (this.useMaterial != null) {
            cost += this.useMaterial.getRepairCost();
            if (this.useMaterial instanceof ItemBookEnchanted) {
                for (Enchantment enchant : this.result.getEnchantments()) {
                    Enchantment inputEnchant = this.useInput.getEnchantment(enchant.getId());
                    if (inputEnchant == null) {
                        cost += enchant.getRepairCost(true);
                    } else if (enchant.getLevel() != inputEnchant.getLevel()) {
                        Enchantment check = Enchantment.get(enchant.getId()).setLevel(enchant.getLevel() - inputEnchant.getLevel());
                        cost += check.getRepairCost(true);
                    }
                }
            } else if (this.useMaterial.isTool()) {
                int ench = 0;
                for (Enchantment enchant : this.result.getEnchantments()) {
                    ench += enchant.getRepairCost(false);
                }
                for (Enchantment enchant : this.useInput.getEnchantments()) {
                    ench -= enchant.getRepairCost(false);
                }
                cost += ench;
            } else {
            	cost += this.useMaterial.getCount();
            }
        }
        this.source.setExperience(this.source.getExperience(), this.source.getExperienceLevel() - cost);

        this.hasExecuted = true;
        return true;
    }

    protected void handleFailed() {
        for (InventoryAction action : this.actions) {
            action.onExecuteFail(this.source);
        }
    }

    public boolean hasExecuted() {
        return this.hasExecuted;
    }

    public int a(int b) {
        switch (b) {
            case 1:
                return 1;

            case 2:
                return 3;

            case 3:
                return 6;

            case 4:
                return 10;

            case 5:
                return 15;

            default:
                return 0;
        }
    }
}
