package cn.nukkit.event.block;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;

/**
 * @author Itsu
 * @author Jupiter Project
 * 
 * <br>
 * <br>このイベントは看板を編集し終わった後に、その編集ウィンドウが閉じた際に呼ばれます。
 * <br>SignChangeEventの完全なる派生で、SignChangeEventの場合は文字を打つごとに呼ばれます。
 * <br>それを改善したのがこのイベントです。
 */
public class PreSignChangeEvent extends BlockEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Player player;

    private String[] lines = new String[4];

    public PreSignChangeEvent(Block block, Player player, String[] lines) {
        super(block);
        this.player = player;
        this.lines = lines;
    }

    public Player getPlayer() {
        return player;
    }

    public String[] getLines() {
        return lines;
    }

    public String getLine(int index) {
        return this.lines[index];
    }

    public void setLine(int index, String line) {
        this.lines[index] = line;
    }
}