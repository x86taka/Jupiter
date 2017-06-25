package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityBed;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBed;
import cn.nukkit.level.Level;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.DyeColor;
import cn.nukkit.utils.TextFormat;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class BlockBed extends BlockTransparent {

    public BlockBed() {
        this(0);
    }

    public BlockBed(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return BED_BLOCK;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public double getResistance() {
        return 1;
    }

    @Override
    public double getHardness() {
        return 0.2;
    }

    @Override
    public String getName() {
    	return getDyeColor().getName() + " Bed";
    }

    @Override
    protected AxisAlignedBB recalculateBoundingBox() {
        return new AxisAlignedBB(
                this.x,
                this.y,
                this.z,
                this.x + 1,
                this.y + 0.5625,
                this.z + 1
        );
    }

    @Override
    public boolean onActivate(Item item) {
        return this.onActivate(item, null);
    }

    @Override
    public boolean onActivate(Item item, Player player) {
    	/*ネザーだったら...爆発させたい
    	if(this.getLevel().getDimension() == Level.DIMENSION_NETHER){
    		
    	}
    	*/
        int time = this.getLevel().getTime() % Level.TIME_FULL;

        boolean isNight = (time >= Level.TIME_NIGHT && time < Level.TIME_SUNRISE);

        if (player != null && !isNight) {
            player.sendMessage(TextFormat.GRAY + "夜の間しか眠ることはできません");
            return true;
        }

        Block blockNorth = this.getSide(BlockFace.NORTH);
        Block blockSouth = this.getSide(BlockFace.SOUTH);
        Block blockEast = this.getSide(BlockFace.EAST);
        Block blockWest = this.getSide(BlockFace.WEST);

        Block b;
        if ((this.meta & 0x08) == 0x08) {
            b = this;
        } else {
            if (blockNorth.getId() == this.getId() && (blockNorth.meta & 0x08) == 0x08) {
                b = blockNorth;
            } else if (blockSouth.getId() == this.getId() && (blockSouth.meta & 0x08) == 0x08) {
                b = blockSouth;
            } else if (blockEast.getId() == this.getId() && (blockEast.meta & 0x08) == 0x08) {
                b = blockEast;
            } else if (blockWest.getId() == this.getId() && (blockWest.meta & 0x08) == 0x08) {
                b = blockWest;
            } else {
                if (player != null) {
                    player.sendMessage(TextFormat.GRAY + "このベッドは未完成です");
                }

                return true;
            }
        }

        if (player != null && !player.sleepOn(b)) {
            player.sendMessage(TextFormat.GRAY + "このベッドは既に使われています");
        }


        return true;
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        return this.place(item, block, target, face, fx, fy, fz, null);
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        Block down = this.down();
        if (!down.isTransparent()) {
            int[] faces = {3, 4, 2, 5};
            int d = player != null ? player.getDirection().getHorizontalIndex() : 0;
            Block next = this.getSide(BlockFace.fromIndex(faces[((d + 3) % 4)]));
            Block downNext = this.getSide(BlockFace.DOWN);

            if (next.canBeReplaced() && !downNext.isTransparent()) {
                int meta = ((d + 3) % 4) & 0x03;
                this.getLevel().setBlock(block, Block.get(this.getId(), meta), true, true);
                this.getLevel().setBlock(next, Block.get(this.getId(), meta | 0x08), true, true);

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onBreak(Item item) {
        Block blockNorth = this.getSide(BlockFace.NORTH);
        Block blockSouth = this.getSide(BlockFace.SOUTH);
        Block blockEast = this.getSide(BlockFace.EAST);
        Block blockWest = this.getSide(BlockFace.WEST);
        
        int[] faces = {3, 4, 2, 5, 2, 5, 3, 4};
        Block next = null;
        try{
	        if ((this.meta & 0x08) == 0x08) { //This is the Top part of bed
	        	next = this.getSide(BlockFace.fromIndex(faces[this.meta]));
	        	if(next.getId() == this.getId() && (next.meta | 0x08) == this.meta){
	        		this.getLevel().setBlock(next, new BlockAir(), true, true);
	        	}
			}else{
				next = this.getSide(BlockFace.fromIndex(faces[this.meta]));
				if(next.getId() == this.getId() && next.meta == (next.meta | 0x08)){
		       		this.getLevel().setBlock(next, Block.get(Block.AIR), true, true);
				}
			}
        }catch(ArrayIndexOutOfBoundsException e){
        	next = this.getSide(BlockFace.fromIndex(faces[0]));
        	this.getLevel().setBlock(next, Block.get(Block.AIR), true, true);
        	return true;
        }
        this.getLevel().setBlock(next, Block.get(Block.AIR), true, true);
        return true;
    }

    private void createBlockEntity(Vector3 pos, int color) {
        CompoundTag nbt = BlockEntity.getDefaultCompound(pos, BlockEntity.BED);
        nbt.putByte("color", color);

        new BlockEntityBed(this.level.getChunk(pos.getFloorX() >> 4, pos.getFloorZ() >> 4), nbt);
    }

    @Override
    public Item toItem() {
        return new ItemBed(this.meta);
    }
    
    @Override
    public Item[] getDrops(Item item){
    	return new Item[]{this.toItem()};
    }
    
    public DyeColor getDyeColor() {
        return DyeColor.getByBlockColorData(meta);
    }
    

}
