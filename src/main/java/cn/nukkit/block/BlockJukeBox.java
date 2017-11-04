package cn.nukkit.block;

public class BlockJukeBox extends BlockSolid{

    public BlockJukeBox() {
        this(0);
    }

    public BlockJukeBox(int meta) {
        super(meta);
    }

	@Override
	public String getName() {
		return "JukeBox";
	}

	@Override
	public int getId() {
		return JUKEBOX;
	}
}
