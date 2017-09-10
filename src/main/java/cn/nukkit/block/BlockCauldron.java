package cn.nukkit.block;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityCauldron;
import cn.nukkit.event.player.PlayerBucketEmptyEvent;
import cn.nukkit.event.player.PlayerBucketFillEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemArmor;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemBucket;
import cn.nukkit.item.ItemCauldron;
import cn.nukkit.item.ItemDye;
import cn.nukkit.item.ItemGlassBottle;
import cn.nukkit.item.ItemPotion;
import cn.nukkit.item.ItemTool;
import cn.nukkit.level.sound.ExplodeSound;
import cn.nukkit.level.sound.SplashSound;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.Tag;
import cn.nukkit.utils.BlockColor;

/**
 * author: CreeperFace
 * Nukkit Project
 */
public class BlockCauldron extends BlockSolid {

    public BlockCauldron() {
        super(0);
    }

    public BlockCauldron(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return CAULDRON_BLOCK;
    }

    public String getName() {
        return "Cauldron Block";
    }

    @Override
    public BlockColor getColor(){
    	return BlockColor.BLACK_BLOCK_COLOR;
    }

    @Override
    public double getResistance() {
        return 10;
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    public boolean isFull() {
        return this.meta == 0x06;
    }

    public boolean isEmpty() {
        return this.meta == 0x00;
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        BlockEntity be = this.level.getBlockEntity(this);

        if (!(be instanceof BlockEntityCauldron)) {
            return false;
        }

        BlockEntityCauldron cauldron = (BlockEntityCauldron) be;

        switch (item.getId()) {
            case Item.BUCKET:
                if (item.getDamage() == 0) {//empty bucket
                    if (!isFull() || cauldron.isCustomColor() || cauldron.hasPotion()) {
                        break;
                    }

                    ItemBucket bucket = (ItemBucket) item.clone();
                    bucket.setDamage(8);//water bucket

                    PlayerBucketFillEvent ev = new PlayerBucketFillEvent(player, this, null, item, bucket);
                    this.level.getServer().getPluginManager().callEvent(ev);
                    if (!ev.isCancelled()) {
                        if (player.isSurvival()) {
                            player.getInventory().setItemInHand(ev.getItem());
                        }
                        this.meta = 0;//empty
                        this.level.setBlock(this, this, true);
                        cauldron.clearCustomColor();
                        this.getLevel().addSound(new SplashSound(this.add(0.5, 1, 0.5)));
                    }
                } else if (item.getDamage() == 8) {//water bucket

                    if (isFull() && !cauldron.isCustomColor() && !cauldron.hasPotion()) {
                        break;
                    }

                    ItemBucket bucket = (ItemBucket) item.clone();
                    bucket.setDamage(0);//empty bucket

                    PlayerBucketEmptyEvent ev = new PlayerBucketEmptyEvent(player, this, null, item, bucket);
                    this.level.getServer().getPluginManager().callEvent(ev);
                    if (!ev.isCancelled()) {
                        if (player.isSurvival()) {
                            player.getInventory().setItemInHand(ev.getItem());
                        }
                        if (cauldron.hasPotion()) {
                            this.meta = 0;
                            cauldron.setPotionId(0xffff);
                            cauldron.setSplashPotion(false);
                            cauldron.clearCustomColor();
                            this.level.setBlock(this, this, true);
                            this.level.addSound(new ExplodeSound(this.add(0.5, 0, 0.5)));
                        } else {
                            this.meta = 6;
                            cauldron.clearCustomColor();
                            this.level.setBlock(this, this, true);
                            this.level.addSound(new SplashSound(this.add(0.5, 1, 0.5)));
                        }
                    }
                }
                break;
            case Item.DYE:
                if (this.meta <= 0 || cauldron.hasPotion()) {
                    break;
                }
                cauldron.setCustomColor(((ItemDye) item).getDyeColor().getColor());
                this.level.setBlock(this, this, true);

                if (player.isSurvival()) {
                    if (item.getCount() == 1) {
                        player.getInventory().setItemInHand(new ItemBlock(new BlockAir()));
                    } else if (item.getCount() > 1) {
                        item.setCount(item.getCount() - 1);
                        player.getInventory().setItemInHand(item);
                    }
                }
                break;
            case Item.LEATHER_CAP:
            case Item.LEATHER_TUNIC:
            case Item.LEATHER_PANTS:
            case Item.LEATHER_BOOTS:
                if (cauldron.hasPotion() || isEmpty()) {
                    break;
                }

                --this.meta;
                if (this.meta < 0x00) {
                    this.meta = 0x00;
                }

                if (!(cauldron.isCustomColor())) {
                    ((ItemArmor) item).clearCustomColor();
                    if (this.meta == 0) {
                        cauldron.setPotionId(0xffff);
                        cauldron.setSplashPotion(false);
                    }
                    this.level.setBlock(this, this, true);
                    break;
                }

                ((ItemArmor) item).setCustomColor(cauldron.getCustomColor());
                player.getInventory().setItemInHand(item);

                if (this.meta == 0) {
                    cauldron.setPotionId(0xffff);
                    cauldron.setSplashPotion(false);
                    cauldron.clearCustomColor();
                }
                this.level.setBlock(this, this, true);
                break;
            case Item.POTION:
                if (cauldron.hasPotion() && cauldron.getPotionId() != item.getDamage() || !(isEmpty()) && !(cauldron.hasPotion())) {
                    this.meta = 0;
                    cauldron.setPotionId(0xffff);
                    cauldron.setSplashPotion(false);
                    cauldron.clearCustomColor();
                    this.level.setBlock(this, this, true);
                    this.level.addSound(new ExplodeSound(this.add(0.5, 0, 0.5)));

                    if (player.isSurvival()) {
                        if (item.getCount() == 1) {
                            player.getInventory().setItemInHand(new ItemGlassBottle());
                        } else if (item.getCount() > 1) {
                            item.setCount(item.getCount() - 1);
                            player.getInventory().setItemInHand(item);
    
                            Item bottle = new ItemGlassBottle();
                            if (player.getInventory().canAddItem(bottle)) {
                                player.getInventory().addItem(bottle);
                            } else {
                                player.getLevel().dropItem(player.add(0, 1.3, 0), bottle, player.getDirectionVector().multiply(0.4));
                            }
                        }
                    }
                    break;
                }

                this.meta = this.meta + 2;
                if (this.meta > 0x06)
                    this.meta = 0x06;

                if (item.getDamage() != ItemPotion.NO_EFFECTS) {
                    cauldron.setPotionId(item.getDamage());
                }
                this.level.setBlock(this, this, true);

                if (player.isSurvival()) {
                    if (item.getCount() == 1) {
                        player.getInventory().setItemInHand(new ItemGlassBottle());
                    } else if (item.getCount() > 1) {
                        item.setCount(item.getCount() - 1);
                        player.getInventory().setItemInHand(item);
    
                        Item bottle = new ItemGlassBottle();
                        if (player.getInventory().canAddItem(bottle)) {
                            player.getInventory().addItem(bottle);
                        } else {
                            player.getLevel().dropItem(player.add(0, 1.3, 0), bottle, player.getDirectionVector().multiply(0.4));
                        }
                    }
                }

                this.level.addSound(new SplashSound(this.add(0.5, 0.5, 0.5)));
                break;
            case Item.GLASS_BOTTLE:
                if (isEmpty()) {
                    break;
                }

                this.meta = this.meta - 2;
                if (this.meta < 0x00)
                    this.meta = 0x00;

                if (player.isSurvival()) {
                    if (item.getCount() == 1) {
                        player.getInventory().setItemInHand(new ItemPotion(cauldron.getPotionId()));
                    } else if (item.getCount() > 1) {
                        item.setCount(item.getCount() - 1);
                        player.getInventory().setItemInHand(item);
    
                        Item potion = new ItemPotion(cauldron.getPotionId());
                        if (player.getInventory().canAddItem(potion)) {
                            player.getInventory().addItem(potion);
                        } else {
                            player.getLevel().dropItem(player.add(0, 1.3, 0), potion, player.getDirectionVector().multiply(0.4));
                        }
                    }
                }

                if (this.meta == 0) {
                    cauldron.setPotionId(0xffff);
                    cauldron.setSplashPotion(false);
                    cauldron.clearCustomColor();
                }
                this.level.setBlock(this, this, true);

                this.level.addSound(new SplashSound(this.add(0.5, 0.5, 0.5)));
                break;
            default:
                return true;
        }

        this.level.updateComparatorOutputLevel(this);
        return true;
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        CompoundTag nbt = new CompoundTag("")
                .putString("id", BlockEntity.CHEST)
                .putInt("x", (int) this.x)
                .putInt("y", (int) this.y)
                .putInt("z", (int) this.z)
                .putShort("PotionId", 0xffff)
                .putByte("SplashPotion", 0);

        if (item.hasCustomBlockData()) {
            Map<String, Tag> customData = item.getCustomBlockData().getTags();
            for (Map.Entry<String, Tag> tag : customData.entrySet()) {
                nbt.put(tag.getKey(), tag.getValue());
            }
        }

        new BlockEntityCauldron(this.level.getChunk((int) this.x >> 4, (int) this.z >> 4), nbt);
        this.getLevel().setBlock(block, this, true, true);
        return true;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.getTier() >= ItemTool.TIER_WOODEN) {
            return new Item[]{new ItemCauldron()};
        }

        return new Item[0];
    }

    @Override
    public Item toItem() {
        return new ItemCauldron();
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride() {
        return this.meta;
    }
}
