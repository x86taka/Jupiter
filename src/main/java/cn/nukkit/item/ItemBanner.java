package cn.nukkit.item;

import cn.nukkit.block.Block;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;

/**
 * @author tedo0627
 */
public class ItemBanner extends Item {

    public static final String ButtomStripe = "bs";
    public static final String TopStripe = "ts";
    public static final String LeftStripe = "ls";
    public static final String RightStripe = "rs";
    public static final String CenterStripe = "cs";
    public static final String MiddleStripe = "ms";
    public static final String DownRightStripe = "drs";
    public static final String DownLeftStripe = "dls";
    public static final String SmallStripes = "ss";
    public static final String DiagonalCross = "cr";
    public static final String SquareCross = "sc";
    public static final String LeftOfDiagonal = "ld";
    public static final String RightOfUpsideDownDiagonal = "rud";
    public static final String LeftOfUpsideSownDiagonal = "lud";
    public static final String RightOfDiagonal = "rd";
    public static final String VerticalHalfLeft = "vh";
    public static final String VerticalHalfRight = "vhr";
    public static final String HorizontalHalfTop = "hh";
    public static final String HorizontalHalfBottom = "hhb";
    public static final String BottomLeftCorner = "bl";
    public static final String BottomRightCorner = "br";
    public static final String TopLeftCorner = "tl";
    public static final String TopRightcorner = "tr";
    public static final String BottomTriangle = "bt";
    public static final String TopTriangle = "tt";
    public static final String BottomTriangleSawtooth = "tts";
    public static final String MiddleCircle = "mc";
    public static final String MiddleRhombus = "bo";
    public static final String CurlyBorder = "cbo";
    public static final String Brick = "bri";
    public static final String Gradient = "gra";
    public static final String GradientUpsideDown = "gru";
    public static final String Creeper = "cre";
    public static final String Skull = "sku";
    public static final String Flower = "flo";
    public static final String Mojang = "moj";

    public ItemBanner() {
        this(0, 1);
    }

    public ItemBanner(Integer meta) {
        this(meta, 1);
    }

    public ItemBanner(Integer meta, int count) {
        super(BANNER, meta, count, "Banner");
        this.block = Block.get(Block.STANDING_BANNER);
    }

    @Override
    public int getMaxStackSize() {
        return 16;
    }

    public ItemBanner addPatterns(int color, String pattern) {
        CompoundTag nbt;
        if (this.hasCompoundTag()) {
            nbt = this.getNamedTag();
        } else {
            nbt = new CompoundTag();
        }
        ListTag<CompoundTag> tag;
        if (nbt.contains("Patterns")) {
            tag = nbt.getList("Patterns", CompoundTag.class);
        } else {
            tag = new ListTag<CompoundTag>("Patterns");
        }
        tag.add(new CompoundTag().putInt("Color", color).putString("Pattern", pattern));
        nbt.putList(tag);
        this.setNamedTag(nbt);
        return this;
    }

    public ItemBanner addPatterns(CompoundTag pattern) {
        CompoundTag nbt;
        if (this.hasCompoundTag()) {
            nbt = this.getNamedTag();
        } else {
            nbt = new CompoundTag();
        }
        ListTag<CompoundTag> tag;
        if (nbt.contains("Patterns")) {
            tag = nbt.getList("Patterns", CompoundTag.class);
        } else {
            tag = new ListTag<CompoundTag>("Patterns");
        }
        tag.add(pattern);
        nbt.putList(tag);
        this.setNamedTag(nbt);
        return this;
    }
}
