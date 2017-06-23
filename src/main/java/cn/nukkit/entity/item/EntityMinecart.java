/*
まだ使えないのでお願いします。
*/

package cn.nukkit.entity.item;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockRail;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;

/**
 *
 * @author itsu
 *
 */

public class EntityMinecart extends EntityVehicle{

	public static final int NETWORK_ID = 84;

	public static final int TYPE_NORMAL = 1;
	public static final int TYPE_CHEST = 2;
	public static final int TYPE_HOPPER = 3;
	public static final int TYPE_TNT = 4;

	public static final int STATE_INITIAL = 0;
	public static final int STATE_ON_RAIL = 1;
	public static final int STATE_OFF_RAIL = 2;

	public boolean isMoving = false;
	public float moveSpeed = 0.4f;

	private int state = EntityMinecart.STATE_INITIAL;
	private int direction = -1;
	public Vector3 moveVector[] = new Vector3[0];


	@Override
    public float getHeight() {
        return 0.7f;
    }

    @Override
    public float getWidth() {
        return 0.98f;
    }

    @Override
    protected float getDrag() {
        return 0.1f;
    }

    @Override
    protected float getGravity() {
        return 0.5f;
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    public EntityMinecart(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public void initEntity(){
    	this.setMaxHealth(1);
    	this.setHealth(this.getHealth());
    	this.moveVector[Entity.SIDE_NORTH] = new Vector3(-1, 0, 0);
    	this.moveVector[Entity.SIDE_SOUTH] = new Vector3(1, 0, 0);
    	this.moveVector[Entity.SIDE_EAST] = new Vector3(0, 0, -1);
    	this.moveVector[Entity.SIDE_WEST] = new Vector3(0, 0, 1);
    	super.initEntity();
    }

    public String getName(){
    	return "Minecart";
    }

    public int getType(){
    	return TYPE_NORMAL;
    }

    @Override
    public boolean onUpdate(int currentTick){

    	if (this.closed) {
            return false;
        }

    	int tickDiff = currentTick - this.lastUpdate;

    	if(tickDiff <= 1){
			return false;
		}

    	this.lastUpdate = currentTick;

    	this.timing.startTiming();

    	boolean hasUpdate = false;

    	if(this.isAlive()){
    		Entity p;
    		p = this.linkedEntity;
    		if(p instanceof Player){
    			if(this.state == EntityMinecart.STATE_INITIAL){
    				checkIfOnRail();
    			}else if(this.state == EntityMinecart.STATE_ON_RAIL){
    				hasUpdate = this.forwordOnRail(p);
    				this.updateMovement();
    			}
    		}
    	}

    	this.timing.stopTiming();

		return hasUpdate || !this.onGround || Math.abs(this.motionX) > 0.00001 || Math.abs(this.motionY) > 0.00001 || Math.abs(this.motionZ) > 0.00001 ;
    }

    private void checkIfOnRail(){

    	for(int y = -1; y != 2 && this.state == EntityMinecart.STATE_INITIAL; y++){
    		Vector3 positionToCheck = this.temporalVector.setComponents(this.x,this.y, this.z);
    		Block block = this.level.getBlock(positionToCheck);
    		if(this.isRail(block)){
    			Vector3 minecartPosition = positionToCheck.floor().add(0.5, 0, 0.5);
    			this.setPosition(minecartPosition);
    			this.state = EntityMinecart.STATE_ON_RAIL;
    		}
    	}

    	if(this.state != EntityMinecart.STATE_ON_RAIL){
    		this.state = EntityMinecart.STATE_OFF_RAIL;
    	}
    }

	/**
	* @param rail
	*/
    private boolean isRail(Block rail){

    	ArrayList<Integer> list = new ArrayList<Integer>();

    	list.add(rail.getId());
    	list.add(Block.RAIL);
    	list.add(Block.ACTIVATOR_RAIL);
    	list.add(Block.DETECTOR_RAIL);
    	list.add(Block.POWERED_RAIL);

    	if(list.contains(rail.getId()))return true;
    	else if(list.contains(Block.RAIL))return true;
    	else if(list.contains(Block.ACTIVATOR_RAIL))return true;
    	else if(list.contains(Block.DETECTOR_RAIL))return true;
    	else if(list.contains(Block.POWERED_RAIL))return true;

    	return false;
    }

    private Block getCurrentRail(){

    	Block block = this.getLevel().getBlock(this);

    	if(isRail(block)){
    		return block;
    	}

    	Vector3 down = this.temporalVector.setComponents(this.x, this.y - 1, this.z);

    	block = this.getLevel().getBlock(down);

    	if(isRail(block)){
    		return block;
    	}

    	return null;
    }

    private boolean forwordOnRail(Entity p){

    	int candidateDirection;

    	if(this.direction == -1){
    		candidateDirection = p.getDirection();
    	}else{
    		candidateDirection = this.direction;
    	}

    	Block rail = getCurrentRail();

    	if(rail != null){

    		int railType = rail.getDamage();
    		int nextDirection = this.getDirectionToMove(railType, candidateDirection);

    		if(nextDirection != -1){

    			this.direction = nextDirection;
    			boolean moved = checkForVertical(railType, nextDirection);

    			if(!moved){
    				return moveIfRail();
    			}else{
    				return true;
    			}

    		}else{
    			this.direction = -1;
    		}

    	}else{
    		this.state = EntityMinecart.STATE_INITIAL;
    	}

    	return false;
    }

    private int getDirectionToMove(int rt, int cd){

    	switch(rt){

    		case BlockRail.STRAIGHT_NORTH_SOUTH:
    		case BlockRail.SLOPED_ASCENDING_NORTH:
    		case BlockRail.SLOPED_ASCENDING_SOUTH:
    			switch(cd){
    				case Entity.SIDE_NORTH:
    				case Entity.SIDE_SOUTH:
    					return cd;
    			}
    			break;

    		case BlockRail.STRAIGHT_EAST_WEST:
			case BlockRail.SLOPED_ASCENDING_EAST:
			case BlockRail.SLOPED_ASCENDING_WEST:
				switch(cd){
				case Entity.SIDE_WEST:
				case Entity.SIDE_EAST:
					return cd;
			}
			break;

			case BlockRail.CURVED_SOUTH_EAST:
				switch(cd){
					case Entity.SIDE_SOUTH:
					case Entity.SIDE_EAST:
						return cd;
					case Entity.SIDE_NORTH:
						return this.checkForTurn(cd, Entity.SIDE_EAST);
					case Entity.SIDE_WEST:
						return this.checkForTurn(cd, Entity.SIDE_SOUTH);
				}
				break;

			case BlockRail.CURVED_SOUTH_WEST:
				switch(cd){
					case Entity.SIDE_SOUTH:
					case Entity.SIDE_WEST:
						return cd;
					case Entity.SIDE_NORTH:
						return this.checkForTurn(cd, Entity.SIDE_WEST);
					case Entity.SIDE_EAST:
						return this.checkForTurn(cd, Entity.SIDE_SOUTH);
				}
				break;

			case BlockRail.CURVED_NORTH_EAST:
				switch(cd){
					case Entity.SIDE_NORTH:
					case Entity.SIDE_EAST:
						return cd;
					case Entity.SIDE_SOUTH:
						return this.checkForTurn(cd, Entity.SIDE_EAST);
					case Entity.SIDE_WEST:
						return this.checkForTurn(cd, Entity.SIDE_NORTH);
				}
				break;

			case BlockRail.CURVED_NORTH_WEST:
				switch(cd){
					case Entity.SIDE_NORTH:
					case Entity.SIDE_WEST:
						return cd;
					case Entity.SIDE_SOUTH:
						return this.checkForTurn(cd, Entity.SIDE_WEST);
					case Entity.SIDE_EAST:
						return this.checkForTurn(cd, Entity.SIDE_NORTH);
				}
				break;
    	}
    	return -1;
    }

    private int checkForTurn(int cd, int nd){
    	switch(cd){

	    	case Entity.SIDE_NORTH:
				double diff = this.x - this.getFloorX();
				if(diff != 0 && diff <= .5){
					double dx = (this.getFloorX() + .5) - this.x;
					this.move(dx, 0, 0);
					return nd;
				}
				break;

	    	case Entity.SIDE_SOUTH:
				diff = this.x - this.getFloorX();
				if(diff != 0 && diff >= .5){
					double dx = (this.getFloorX() + .5) - this.x;
					this.move(dx, 0, 0);
					return nd;
				}
				break;

	    	case Entity.SIDE_EAST:
				diff = this.z - this.getFloorZ();
				if(diff != 0 && diff <= .5){
					double dx = (this.getFloorZ() + .5) - this.z;
					this.move(0, 0, dx);
					return nd;
				}
				break;

	    	case Entity.SIDE_WEST:
				diff = this.z - this.getFloorZ();
				if(diff != 0 && diff >= .5){
					double dx = (this.getFloorZ() + .5) - this.z;
					this.move(0, 0, dx);
					return nd;
				}
				break;
    	}

    	return cd;
    }

    public boolean checkForVertical(int rt, int cd){

    	switch(rt){

	    	case BlockRail.SLOPED_ASCENDING_NORTH:

				switch(cd){

			    	case Entity.SIDE_NORTH:
						double diff = this.x - this.getFloorX();
						if(diff != 0 && diff <= .5){
							double dx = (this.getFloorX() - .1) - this.x;
							this.move(dx, 1, 0);
							return true;
						}
						break;

			    	case Entity.SIDE_SOUTH:
						diff = this.x - this.getFloorX();
						if(diff != 0 && diff >= .5){
							double dx = (this.getFloorX() + .1) - this.x;
							this.move(dx, -1, 0);
							return true;
						}
						break;
		    	}
		    	break;

	    	case BlockRail.SLOPED_ASCENDING_SOUTH:

				switch(cd){

			    	case Entity.SIDE_NORTH:
						double diff = this.x - this.getFloorX();
						if(diff != 0 && diff <= .5){
							double dx = (this.getFloorX() + .1) - this.x;
							this.move(dx, 1, 0);
							return true;
						}
						break;

			    	case Entity.SIDE_SOUTH:
						diff = this.x - this.getFloorX();
						if(diff != 0 && diff >= .5){
							double dx = (this.getFloorX() - .1) - this.x;
							this.move(dx, -1, 0);
							return true;
						}
						break;
				}
				break;

	    	case BlockRail.SLOPED_ASCENDING_EAST:

				switch(cd){

			    	case Entity.SIDE_EAST:
						double diff = this.z - this.getFloorZ();
						if(diff != 0 && diff <= .5){
							double dz = (this.getFloorZ() - .1) - this.z;
							this.move(0, 1, dz);
							return true;
						}
						break;

			    	case Entity.SIDE_WEST:
						diff = this.z - this.getFloorZ();
						if(diff != 0 && diff >= .5){
							double dz = (this.getFloorZ() + .1) - this.z;
							this.move(0, -1, dz);
							return true;
						}
						break;
				}
				break;

	    	case BlockRail.SLOPED_ASCENDING_WEST:

				switch(cd){

			    	case Entity.SIDE_EAST:
						double diff = this.z - this.getFloorZ();
						if(diff != 0 && diff <= .5){
							double dz = (this.getFloorZ() - .1) - this.z;
							this.move(0, -1, dz);
							return true;
						}
						break;

			    	case Entity.SIDE_WEST:
						diff = this.z - this.getFloorZ();
						if(diff != 0 && diff >= .5){
							double dz = (this.getFloorZ() + .1) - this.z;
							this.move(0, 1, dz);
							return true;
						}
						break;
				}
				break;
    	}
    	return false;
    }

    private boolean moveIfRail(){

    	Vector3 nextMoveVector = this.moveVector[this.direction];
    	nextMoveVector = nextMoveVector.multiply(this.moveSpeed);
    	Vector3 newVector = this.add(nextMoveVector.x, nextMoveVector.y, nextMoveVector.z);
    	Block possibleRail = getCurrentRail();

    	ArrayList<Integer> list = new ArrayList<Integer>();
    	list.add(Block.RAIL);
    	list.add(Block.ACTIVATOR_RAIL);
    	list.add(Block.DETECTOR_RAIL);
    	list.add(Block.POWERED_RAIL);

    	if(list.contains(possibleRail.getId())){
    		moveUsingVector(newVector);
    		return true;
    	}
    	else if(list.contains(Block.RAIL)){
    		moveUsingVector(newVector);
    		return true;
    	}
    	else if(list.contains(Block.ACTIVATOR_RAIL)){
    		moveUsingVector(newVector);
    		return true;
    	}
    	else if(list.contains(Block.DETECTOR_RAIL)){
    		moveUsingVector(newVector);
    		return true;
    	}
    	else if(list.contains(Block.POWERED_RAIL)){
    		moveUsingVector(newVector);
    		return true;
    	}

    	return false;
    }

    private void moveUsingVector(Vector3 dp){

    	double dx = dp.x - this.x;
    	double dy = dp.y - this.y;
    	double dz = dp.z - this.z;

    	this.move(dx, dy, dz);
    }

    @SuppressWarnings("null")
	public Vector3 getNearestRail(){

    	int minX = NukkitMath.floorFloat((float) this.boundingBox.minX);
    	int minY = NukkitMath.floorFloat((float) this.boundingBox.minY);
    	int minZ = NukkitMath.floorFloat((float) this.boundingBox.minZ);

    	int maxX = NukkitMath.ceilFloat((float) this.boundingBox.maxX);
    	int maxY = NukkitMath.ceilFloat((float) this.boundingBox.maxY);
    	int maxZ = NukkitMath.ceilFloat((float) this.boundingBox.maxZ);

    	Block rails[] = null;

    	for(int z = minZ; z <= maxZ; ++z){
			for(int x = minX; x <= maxX; ++x){
				for(int y = minY; y <= maxY; ++y){
					Block block = this.level.getBlock(this.temporalVector.setComponents(x, y, z));

					boolean b = false;

			    	ArrayList<Integer> list = new ArrayList<Integer>();
			    	list.add(block.getId());
			    	list.add(Block.RAIL);
			    	list.add(Block.ACTIVATOR_RAIL);
			    	list.add(Block.DETECTOR_RAIL);
			    	list.add(Block.POWERED_RAIL);

			    	if(list.contains(block.getId())){
			    		b = true;
			    	}
			    	else if(list.contains(Block.RAIL)){
			    		b = true;
			    	}
			    	else if(list.contains(Block.ACTIVATOR_RAIL)){
			    		b = true;
			    	}
			    	else if(list.contains(Block.DETECTOR_RAIL)){
			    		b = true;
			    	}
			    	else if(list.contains(Block.POWERED_RAIL)){
			    		b = true;
			    	}
			    	if(b)rails[0] = block;
				}
			}
		}

    	int minDistance = Integer.MAX_VALUE;
    	Vector3 nearestRail = null;

    	for(Vector3 rail : rails){
    		double dis = this.distance(rail);
    		if(dis < minDistance){
    			nearestRail = rail;
    			minDistance = (int) dis;
    		}
    	}

    	return nearestRail;
    }

    @Override
    public void spawnTo(Player player) {
        AddEntityPacket pk = new AddEntityPacket();
        pk.entityUniqueId = this.getId();
        pk.entityRuntimeId = this.getId();
        pk.type = EntityMinecart.NETWORK_ID;
        pk.x = (float) this.x;
        pk.y = (float) this.y;
        pk.z = (float) this.z;
        pk.speedX = 0;
        pk.speedY = 0;
        pk.speedZ = 0;
        pk.yaw = 0;
        pk.pitch = 0;
        pk.metadata = this.dataProperties;
        player.dataPacket(pk);

        super.spawnTo(player);
    }
    
    

}
