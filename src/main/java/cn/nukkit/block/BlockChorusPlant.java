package cn.nukkit.block;

/**
 * @author Megapix96
 */
public class BlockChorusPlant extends BlockTransparent {

    protected BlockChorusPlant(int meta) {
        super(meta);
    }

    //TODO

    @Override
    public int getId() {
        return CHORUS_PLANT;
    }

    @Override
    public String getName() {
        return "Chorus Plant";
    }
}
