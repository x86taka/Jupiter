package cn.nukkit.block;

/**
 * @author Megapix96
 */
public class BlockChorusFlower extends BlockTransparent {

    protected BlockChorusFlower(int meta) {
        super(meta);
    }

    //TODO

    @Override
    public int getId() {
        return CHORUS_FLOWER;
    }

    @Override
    public String getName() {
        return "Chorus Flower";
    }
}
