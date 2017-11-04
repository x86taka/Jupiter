package cn.nukkit.block;

public class BlockBone extends BlockSolid{

    public BlockBone() {
        this(0);
    }

    public BlockBone(int meta) {
        super(meta);
    }

	@Override
	public String getName() {
		return "Bone Block";
	}

	@Override
	public int getId() {
		return BONE_BLOCK;
	}
}
