package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;

public class PlayerUseFishingRodEvent extends PlayerEvent implements Cancellable {

	public final static int ACTION_START_FISHING = 0;
	public final static int ACTION_STOP_FISHING = 1;

    private static final HandlerList handlers = new HandlerList();
    private int action;

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerUseFishingRodEvent(Player player, int action) {
        this.player = player;
        this.action = action;
    }

    public int getAction() {
        return this.action;
    }

}
