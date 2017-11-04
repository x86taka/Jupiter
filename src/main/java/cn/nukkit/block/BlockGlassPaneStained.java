package cn.nukkit.block;

public class BlockGlassPaneStained extends BlockGlassPane {

    public BlockGlassPaneStained() {
        this(0);
    }

    public BlockGlassPaneStained(int meta) {
        super(0);
    }

    @Override
    public String getName() {
        return "Glass Pane Stained";
    }

    @Override
    public int getId() {
        return STAINED_GLASS_PANE;
    }
}
