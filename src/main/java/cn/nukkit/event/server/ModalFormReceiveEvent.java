package cn.nukkit.event.server;

import cn.nukkit.event.HandlerList;
import cn.nukkit.window.WindowBase;

public class ModalFormReceiveEvent extends ServerEvent {

    private static final HandlerList handlers = new HandlerList();

    private int formId;
    private String data;
    private WindowBase window;

    public ModalFormReceiveEvent(int formId, String data2, WindowBase window) {
        this.formId = formId;
        this.data = data2;
        this.window = window;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }
    
    public String getData(){
    	return this.data;
    }
    
    public int getFormId(){
    	return this.formId;
    }
    
    public WindowBase getWindow(){
    	return this.window;
    }

}
