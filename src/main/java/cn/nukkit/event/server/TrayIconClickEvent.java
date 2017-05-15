package cn.nukkit.event.server;

import java.awt.TrayIcon;

import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;

/**
 * @author Itsu
 * JupiterDevelopmentTeam
 */

public class TrayIconClickEvent extends ServerEvent implements Cancellable{
	
    private static final HandlerList handlers = new HandlerList();
    private final TrayIcon icon;
    
    public TrayIconClickEvent(TrayIcon icon){
    	this.icon = icon;
    }
    
    public static HandlerList getHandlers() {
        return handlers;
    }
    
    public TrayIcon getTrayIcon(){
    	return this.icon;
    }

}
