package cn.nukkit.blockentity;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.potion.Effect;

public class BlockEntityBeacon extends BlockEntitySpawnable {

    private static final int POWER_LEVEL_MAX = 4;

    private long currentTick = 0;

    public BlockEntityBeacon(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);

        if (!namedTag.contains("Lock")) {
            namedTag.putString("Lock", "");
        }

        if (!namedTag.contains("Levels")) {
            namedTag.putInt("Levels", 0);
        }

        if (!namedTag.contains("Primary")) {
            namedTag.putInt("Primary", 0);
        }

        if (!namedTag.contains("Secondary")) {
            namedTag.putInt("Secondary", 0);
        }

        scheduleUpdate();
    }

    @Override
    public boolean isBlockEntityValid() {
        return this.level.getBlockIdAt((int) this.x, (int) this.y, (int) this.z) == Block.BEACON;
    }

    @Override
    public CompoundTag getSpawnCompound() {
        return new CompoundTag()
                .putString("id", BlockEntity.BEACON)
                .putInt("x", (int) this.x)
                .putInt("y", (int) this.y)
                .putInt("z", (int) this.z)
                .putString("Lock", this.namedTag.getString("Lock"))
                .putInt("Levels", this.namedTag.getInt("Levels"))
                .putInt("Primary", this.namedTag.getInt("Primary"))
                .putInt("Secondary", this.namedTag.getInt("Secondary"));
    }

    @Override
    public boolean onUpdate() {
        //Only check every 100 ticks
        if (currentTick++ % 100 != 0) {
            return true;
        }
        if (this.level.getBlockIdAt(this.getFloorX(), this.getFloorY() + 1, this.getFloorZ()) != 0) {
            return true;
        }

        int level = this.calculatePowerLevel();
        int id = 0;

        if (level > 0) {
            if (this.getPrimary() != 0) {
                id = this.getPrimary();
                double range = (level + 1) * 10;
                Effect effect = Effect.getEffect(id);
                effect.setDuration(10 * 30);
                effect.setAmplifier(0);
                for (Player player : this.level.getPlayers().values()) {
                    if (this.distance(player) <= range) {
                        player.addEffect(effect);
                    }
                }
            }
            if (this.getSecondary() != 0) {
                if (this.getSecondary() == id) {
                    double range = (level + 1) * 10;
                    Effect effect = Effect.getEffect(id);
                    effect.setDuration(10 * 30);
                    effect.setAmplifier(1);
                    for (Player player : this.level.getPlayers().values()) {
                        if (this.distance(player) <= range) {
                            player.addEffect(effect);
                        }
                    }
                } else {
                    id = this.getSecondary();
                    double range = (level + 1) * 10;
                    Effect effect = Effect.getEffect(id);
                    effect.setDuration(10 * 30);
                    effect.setAmplifier(0);
                    for (Player player : this.level.getPlayers().values()) {
                        if (this.distance(player) <= range) {
                            player.addEffect(effect);
                        }
                    }
                }
            }
        }

        this.namedTag.putInt("Levels", level);

        return true;
    }

    public int getPrimary() {
        return this.namedTag.getInt("Primary");
    }

    public void setPrimary(int primary) {
        this.namedTag.putInt("Primary", primary);
    }

    public int getSecondary() {
        return this.namedTag.getInt("Secondary");
    }

    public void setSecondary(int secondary) {
        this.namedTag.putInt("Secondary", secondary);
    }

    private int calculatePowerLevel() {
        int tileX = getFloorX();
        int tileY = getFloorY();
        int tileZ = getFloorZ();

        //The power level that we're testing for
        for (int powerLevel = 1; powerLevel <= POWER_LEVEL_MAX; powerLevel++) {
            int queryY = tileY - powerLevel; //Layer below the beacon block

            for (int queryX = tileX - powerLevel; queryX <= tileX + powerLevel; queryX++) {
                for (int queryZ = tileZ - powerLevel; queryZ <= tileZ + powerLevel; queryZ++) {

                    int testBlockId = level.getBlockIdAt(queryX, queryY, queryZ);
                    if (
                            testBlockId != Block.IRON_BLOCK &&
                                    testBlockId != Block.GOLD_BLOCK &&
                                    testBlockId != Block.EMERALD_BLOCK &&
                                    testBlockId != Block.DIAMOND_BLOCK
                            ) {
                        return powerLevel - 1;
                    }

                }
            }
        }

        return POWER_LEVEL_MAX;
    }
}
