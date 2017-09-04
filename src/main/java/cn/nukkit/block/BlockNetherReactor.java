package cn.nukkit.block;

/**
 * @author Megapix96
 */
public class BlockNetherReactor extends BlockSolid {

    public BlockNetherReactor() {
        this(0);
    }

    public BlockNetherReactor(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return NETHER_REACTOR;
    }

    @Override
    public String getName() {
        return "Nether Reactor";
    }
}
