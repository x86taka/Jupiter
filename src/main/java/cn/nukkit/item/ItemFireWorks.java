package cn.nukkit.item;

public class ItemFireWorks extends Item{

    public ItemFireWorks() {
        this(0, 1);
    }

    public ItemFireWorks(Integer meta) {
        this(meta, 1);
    }

    public ItemFireWorks(Integer meta, int count) {
        super(FIREWORKS, 0, count, "FireWorks");
    }
}
