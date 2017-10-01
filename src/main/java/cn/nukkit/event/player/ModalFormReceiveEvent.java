package cn.nukkit.event.server;

import cn.nukkit.event.HandlerList;
import cn.nukkit.window.WindowBase;

public class ModalFormReceiveEvent extends ServerEvent {

    private static final HandlerList handlers = new HandlerList();

    private int formId;
    private WindowBase window;

    public ModalFormReceiveEvent(int formId, WindowBase window) {
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
