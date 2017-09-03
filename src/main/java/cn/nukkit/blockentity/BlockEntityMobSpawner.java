package cn.nukkit.blockentity;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockMobSpawner;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.format.Chunk;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;

/**
 * @author CreeperFace
 */
public class BlockEntityMobSpawner extends BlockEntitySpawnable {

    public BlockEntityMobSpawner(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);

        if (!nbt.contains("EntityId")) {
            nbt.putInt("EntityId", 0);
        }

        if (!nbt.contains("SpawnCount")) {
            nbt.putInt("SpawnCount", 4);
        }

        if (!nbt.contains("SpawnRange")) {
            nbt.putInt("SpawnRange", 4);
        }

        if (!nbt.contains("MinSpawnDelay")) {
            nbt.putInt("MinSpawnDelay", 200);
        }

        if (!nbt.contains("MaxSpawnDelay")) {
            nbt.putInt("MaxSpawnDelay", 799);
        }

        if (!nbt.contains("Delay")) {
            nbt.putInt("Delay", NukkitMath.randomRange(new NukkitRandom(), nbt.getInt("MinSpawnDelay"), nbt.getInt("MaxSpawnDelay")));
        }

        if (this.getNetworkId() > 0) {
            this.scheduleUpdate();
        }
    }

    @Override
    public boolean isBlockEntityValid() {
        return this.getLevelBlock() instanceof BlockMobSpawner;
    }

    public void setNetworkId(int id) {
        this.namedTag.putInt("EntityId", id);
        this.scheduleUpdate();
        this.spawnToAll();
    }

    public int getNetworkId() {
        return this.namedTag.getInt("EntityId");
    }

    public boolean canUpdate() {
        if (this.getNetworkId() == 0) {
            return false;
        }

        int count = 0;
        boolean hasPlayer = false;
        AxisAlignedBB boundingBox = new AxisAlignedBB(x - 8.5, y - 4.5, z - 8.5, x + 8.5, y + 4.5, z + 8.5);
        for (Entity entity : this.level.getNearbyEntities(boundingBox)) {
            if (entity.getNetworkId() == this.getNetworkId()) {
                ++count;
            }

            if (entity instanceof Player) {
                hasPlayer = true;
            }
        }

        if (hasPlayer && count <= 6) {
            return true;
        }

        return false;
    }

    @Override
    public boolean onUpdate() {
        if (this.closed) {
            return false;
        }

        this.timing.startTiming();

        if (!(this.chunk instanceof Chunk)) {
            return false;
        }

        if (!(this.canUpdate())) {
            return false;
        }

        if (this.namedTag.getInt("Delay") <= 0) {
            int success = 0;
            NukkitRandom random = new NukkitRandom();
            for (int i = 0; i < this.namedTag.getInt("SpawnCount"); ++i) {
                Vector3 pos = this.add(
                        this.getRandomSpawn(), 
                        NukkitMath.randomRange(random, -1, 1), 
                        this.getRandomSpawn());
                Block target = this.getLevel().getBlock(pos);
                Block ground = target.down();
                if (target.getId() == Block.AIR && ground.isSolid()) {
                    ++success;
                    CompoundTag nbt = new CompoundTag()
                            .putList(new ListTag<DoubleTag>("Pos")
                                    .add(new DoubleTag("", pos.x))
                                    .add(new DoubleTag("", pos.y))
                                    .add(new DoubleTag("", pos.z)))
                            .putList(new ListTag<DoubleTag>("Motion")
                                    .add(new DoubleTag("", 0))
                                    .add(new DoubleTag("", 0))
                                    .add(new DoubleTag("", 0)))
                            .putList(new ListTag<FloatTag>("Rotation")
                                    .add(new FloatTag("", (float) Math.random() * 360))
                                    .add(new FloatTag("", (float) 0)));

                    Entity entity = Entity.createEntity(this.getNetworkId(), this.chunk, nbt);
                    entity.spawnToAll();
                }
            }

            if (success > 0) {
                this.namedTag.putInt("Delay", NukkitMath.randomRange(random, this.namedTag.getInt("MinSpawnDelay"), this.namedTag.getInt("MaxSpawnDelay")));
            }

        } else {
            this.namedTag.putInt("Delay", this.namedTag.getInt("Delay") - 1);
        }

        this.timing.stopTiming();

        return true;
    }

    public double getRandomSpawn() {
        return (Math.random() * (this.namedTag.getInt("SpawnRange") * 2)) - this.namedTag.getInt("SpawnRange");
    }

    @Override
    public CompoundTag getSpawnCompound() {
        CompoundTag c = new CompoundTag()
                .putString("id", BlockEntity.MOB_SPAWNER)
                .putInt("x", (int) this.x)
                .putInt("y", (int) this.y)
                .putInt("z", (int) this.z)
                .putInt("EntityId", this.getNetworkId());

        return c;
    }
}
