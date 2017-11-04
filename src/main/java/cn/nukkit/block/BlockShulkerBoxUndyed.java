package cn.nukkit.block;

public class BlockShulkerBoxUndyed extends BlockSolid{

    public BlockShulkerBoxUndyed() {
        this(0);
    }

    public BlockShulkerBoxUndyed(int meta) {
        super(meta);
    }

	@Override
	public String getName() {
		return "Undyed Shulker Box";
	}

	@Override
	public int getId() {
		return UNDYED_SHULKER_BOX;
	}
}
