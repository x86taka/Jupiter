package cn.nukkit.block;

/**
 * @author Megapix96
 */
public class BlockCommandChain extends BlockCommand {

    public BlockCommandChain() {
        this(0);
    }

    public BlockCommandChain(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Chain Command Block";
    }

    @Override
    public int getId() {
        return CHAIN_COMMAND_BLOCK;
    }

    @Override
    public int getMode() {
    	return 2;
    }
}
