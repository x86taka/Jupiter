package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.window.WindowBase;

public class PlayerModalFormReceiveEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private int formId;
    private WindowBase window;

    public PlayerModalFormReceiveEvent(Player player, int formId, WindowBase window) {
        this.player = player;
        this.formId = formId;
        this.window = window;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }
    
    public int getFormId(){
    	return this.formId;
    }
    
    public WindowBase getWindow(){
    	return this.window;
    }

}
