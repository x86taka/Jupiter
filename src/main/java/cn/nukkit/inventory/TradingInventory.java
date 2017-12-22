package cn.nukkit.inventory;

import java.io.IOException;
import java.nio.ByteOrder;

import cn.nukkit.Player;
import cn.nukkit.entity.passive.EntityVillager;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.UpdateTradePacket;

/**
 * author: tedo0627, hiroki19990625
 */

public class TradingInventory extends BaseInventory {

    public TradingInventory(InventoryHolder holder) {
        super(holder, InventoryType.TRADING);
    }

    @Override
    public EntityVillager getHolder() {
        return (EntityVillager) this.holder;
    }

    @Override
    public void onOpen(Player who) {
        CompoundTag nbt = this.getHolder().getOffers();
        if (nbt != null) {
            super.onOpen(who);

            UpdateTradePacket pk1 = new UpdateTradePacket();
            pk1.windowId = (byte) who.getWindowId(this);
            pk1.windowType = 15;
            pk1.unknownVarInt1 = 0;
            pk1.unknownVarInt2 = 0;
            pk1.isWilling = false;
            pk1.trader = this.getHolder().getId();
            pk1.player = who.getId();
            pk1.displayName = this.getHolder().getName();
            try {
                pk1.offers = NBTIO.write(nbt, ByteOrder.LITTLE_ENDIAN, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            who.dataPacket(pk1);
        } else {
            super.onClose(who);
        }
    }

    @Override
    public void onClose(Player who) {
        for (int i = 0; i < 2; ++i) {
            Item item = this.getItem(i);
            if (who.getInventory().canAddItem(item)) {
                who.getInventory().addItem(item);
            } else {
                who.dropItem(item);
            }
            this.clear(i);
        }

        super.onClose(who);
    }

}
