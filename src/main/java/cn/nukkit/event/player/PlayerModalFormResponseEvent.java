package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.window.FormWindow;

public class PlayerModalFormResponseEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private int formId;
    private FormWindow window;

    public PlayerModalFormResponseEvent(Player player, int formId, FormWindow window) {
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

    public FormWindow getWindow(){
        return this.window;
    }

}
