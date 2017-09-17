package cn.nukkit.inventory;

import cn.nukkit.Player;
import cn.nukkit.blockentity.BlockEntityDispenser;
import cn.nukkit.level.Level;
import cn.nukkit.network.protocol.BlockEventPacket;

/**
 * @author Megapix96
 */
public class DispenserInventory extends ContainerInventory {

    public DispenserInventory(BlockEntityDispenser dispenser) {
        super(dispenser, InventoryType.DISPENSER);
    }

    @Override
    public BlockEntityDispenser getHolder() {
        return (BlockEntityDispenser) this.holder;
    }

    @Override
    public void onOpen(Player who) {
        super.onOpen(who);

        if (this.getViewers().size() == 1) {
            BlockEventPacket pk = new BlockEventPacket();
            pk.x = (int) this.getHolder().getX();
            pk.y = (int) this.getHolder().getY();
            pk.z = (int) this.getHolder().getZ();
            pk.case1 = 1;
            pk.case2 = 2;

            Level level = this.getHolder().getLevel();
            if (level != null) {
                level.addChunkPacket((int) this.getHolder().getX() >> 4, (int) this.getHolder().getZ() >> 4, pk);
            }
        }
    }

    @Override
    public void onClose(Player who) {
        if (this.getViewers().size() == 1) {
            BlockEventPacket pk = new BlockEventPacket();
            pk.x = (int) this.getHolder().getX();
            pk.y = (int) this.getHolder().getY();
            pk.z = (int) this.getHolder().getZ();
            pk.case1 = 1;
            pk.case2 = 0;

            Level level = this.getHolder().getLevel();
            if (level != null) {
                level.addChunkPacket((int) this.getHolder().getX() >> 4, (int) this.getHolder().getZ() >> 4, pk);
            }
        }

        super.onClose(who);
    }
}
