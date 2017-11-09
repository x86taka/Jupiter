package cn.nukkit.utils;

/**
 * @author Megapix96
 */
public class Link {

    public static final int ACTION_ADD = 0;
    public static final int ACTION_RIDE = 1;
    public static final int ACTION_REMOVE = 2;

    public long from;
    public long to;
    public int action;
    public int unknownByte;

    public Link(long from, long to, int action, int unknownByte) {
        this.from = from;
        this.to = to;
        this.action = action;
        this.unknownByte = unknownByte;
    }
}
