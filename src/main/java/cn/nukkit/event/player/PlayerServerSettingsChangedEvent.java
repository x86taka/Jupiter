package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.window.ServerSettingsWindow;

public class PlayerServerSettingsChangedEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private int formId;
    private ServerSettingsWindow window;

    public PlayerServerSettingsChangedEvent(Player player, int formId, ServerSettingsWindow window) {
        this.player = player;
        this.formId = formId;
        this.window = window;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }

    public int getFormId() {
        return this.formId;
    }

    public ServerSettingsWindow getWindow() {
        return this.window;
    }

}
