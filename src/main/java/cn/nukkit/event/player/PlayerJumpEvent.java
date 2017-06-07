package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.level.Location;

public class PlayerJumpEvent extends PlayerEvent implements Cancellable{
	
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected Location loc;

    public PlayerJumpEvent(Player player, Location loc){
        this.player = player;
        this.loc = loc;
    }
    
    public Location getLocation(){
    	return this.loc;
    }
    
    

}
