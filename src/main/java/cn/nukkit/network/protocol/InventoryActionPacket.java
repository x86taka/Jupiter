package cn.nukkit.network.protocol;

import cn.nukkit.item.Item;

public class InventoryActionPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.INVENTORY_ACTION_PACKET;

    public final static int ACTION_GIVE_ITEM = 0;
    public final static int ACTION_ENCHANT_ITEM = 2;

    public long actionId;
    public Item item;
    public int enchantmentId = 0;
    public int enchantmentLevel = 0;

    @Override
    public void decode() {
    	this.actionId = this.getUnsignedVarInt();
    	this.item = this.getSlot();
    	this.enchantmentId = this.getVarInt();
    	this.enchantmentLevel = this.getVarInt();
    }

    @Override
    public void encode() {
        this.reset();
        this.putUnsignedVarInt(this.actionId);
        this.putSlot(this.item);
        this.putVarInt(this.enchantmentId);
        this.putVarInt(this.enchantmentLevel);
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
