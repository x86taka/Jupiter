package cn.nukkit.inventory;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public enum InventoryType implements InventoryNetworkId{

    CHEST(27, "Chest", WINDOW_CONTAINER),
    ENDER_CHEST(27, "Ender Chest", WINDOW_CONTAINER),
    SHULKER_BOX(27, "Shulker Box", WINDOW_CONTAINER), 
    DOUBLE_CHEST(27 + 27, "Double Chest", WINDOW_CONTAINER),
    PLAYER(40, "Player", WINDOW_CONTAINER), //36 CONTAINER, 4 ARMOR
    FURNACE(3, "Furnace", WINDOW_FURNACE),
    CRAFTING(5, "Crafting", WINDOW_WORKBENCH), //4 CRAFTING slots, 1 RESULT
    WORKBENCH(10, "Crafting", WINDOW_WORKBENCH), //9 CRAFTING slots, 1 RESULT
    BREWING_STAND(4, "Brewing", WINDOW_BREWING_STAND), //1 INPUT, 3 POTION
    ANVIL(3, "Anvil", WINDOW_ANVIL), //2 INPUT, 1 OUTPUT
    ENCHANT_TABLE(2, "Enchant", WINDOW_ENCHANTMENT), //1 INPUT/OUTPUT, 1 LAPIS
    DISPENSER(9, "Dispenser", WINDOW_DISPENSER), //9 CONTAINER
    DROPPER(9, "Dropper", WINDOW_DROPPER), //9 CONTAINER
    HOPPER(5, "Hopper", WINDOW_HOPPER), //5 CONTAINER
    MINECART_CHEST(27, "Minecart Chest", WINDOW_MINECART_CHEST), // 29 CONTAINER
    MINECART_HOPPER(5, "Minecart Hopper", WINDOW_MINECART_HOPPER), // 5 CONTAINER
    HORSE(2, "Horse", WINDOW_HORSE), // 2 INPUT(サドル, 馬鎧)
    //TODO: 15 Structure Editor?
    TRADING(3, "Trading", WINDOW_TRADING), // 2 INPUT, 1 OUTPUT
	COMMAND_BLOCK(0, "Command Block", WINDOW_COMMAND_BLOCK);

    private final int size;
    private final String title;
    private final int typeId;

    InventoryType(int defaultSize, String defaultBlockEntity, int typeId) {
        this.size = defaultSize;
        this.title = defaultBlockEntity;
        this.typeId = typeId;
    }

    public int getDefaultSize() {
        return size;
    }

    public String getDefaultTitle() {
        return title;
    }

    public int getNetworkType() {
        return typeId;
    }
}
