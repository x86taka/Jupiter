package cn.nukkit.network.protocol;

import cn.nukkit.inventory.InventoryNetworkId;

public class UpdateTradePacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.UPDATE_TRADE_PACKET;

    public byte windowId;
    public byte windowType = InventoryNetworkId.WINDOW_TRADING;
    public int unknownVarInt1;
    public int unknownVarInt2;
    public boolean isWilling;
    public long trader;
    public long player;
    public String displayName;
    public byte[] offers;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.putByte(this.windowId);
        this.putByte(this.windowType);
        this.putVarInt(this.unknownVarInt1);
        this.putVarInt(this.unknownVarInt2);
        this.putBoolean(this.isWilling);
        this.putVarLong(this.player);
        this.putVarLong(this.trader);
        this.putString(this.displayName);
        this.put(this.offers);
    }

}
