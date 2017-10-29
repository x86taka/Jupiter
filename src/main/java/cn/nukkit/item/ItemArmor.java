package cn.nukkit.item;

import java.awt.Color;

import cn.nukkit.Player;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
abstract public class ItemArmor extends Item {

    public static final int TIER_LEATHER = 1;
    public static final int TIER_IRON = 2;
    public static final int TIER_CHAIN = 3;
    public static final int TIER_GOLD = 4;
    public static final int TIER_DIAMOND = 5;

    public ItemArmor(int id) {
        super(id);
    }

    public ItemArmor(int id, Integer meta) {
        super(id, meta);
    }

    public ItemArmor(int id, Integer meta, int count) {
        super(id, meta, count);
    }

    public ItemArmor(int id, Integer meta, int count, String name) {
        super(id, meta, count, name);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean isArmor() {
        return true;
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        if (this.isHelmet() && player.getInventory().getHelmet().isNull()) {
            if (player.getInventory().setHelmet(this))
                player.getInventory().clear(player.getInventory().getHeldItemIndex());
        } else if (this.isChestplate() && player.getInventory().getChestplate().isNull()) {
            if (player.getInventory().setChestplate(this))
                player.getInventory().clear(player.getInventory().getHeldItemIndex());
        } else if (this.isLeggings() && player.getInventory().getLeggings().isNull()) {
            if (player.getInventory().setHelmet(this))
                player.getInventory().clear(player.getInventory().getHeldItemIndex());
        } else if (this.isBoots() && player.getInventory().getBoots().isNull()) {
            if (player.getInventory().setBoots(this))
                player.getInventory().clear(player.getInventory().getHeldItemIndex());
        }

        return this.getCount() == 0;
    }

    @Override
    public int getEnchantAbility() {
        switch (this.getTier()) {
            case TIER_CHAIN:
                return 12;
            case TIER_LEATHER:
                return 15;
            case TIER_DIAMOND:
                return 10;
            case TIER_GOLD:
                return 25;
            case TIER_IRON:
                return 9;
        }

        return 0;
    }

    public void setCustomColor(Color color){
        CompoundTag tag;
        if(this.hasCompoundTag()){
            tag = this.getNamedTag();
        }else{
            tag = new CompoundTag();
        }
        tag.putInt("customColor", color.getRGB());
        this.setCompoundTag(tag);
    }

    public int getCustomColor(){
        if(!this.hasCompoundTag()) return 0;
        CompoundTag tag = this.getNamedTag();
        if(tag.contains("customColor")){
            return tag.getInt("customColor");
        }
        return 0;
    }

    public void clearCustomColor(){
        if(!this.hasCompoundTag()) return;
        CompoundTag tag = this.getNamedTag();
        if(tag.contains("customColor")){
            tag.remove("customColor");
        }
        this.setCompoundTag(tag);
    }
}
