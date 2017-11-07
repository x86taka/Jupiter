package cn.nukkit.item;

public class ItemFireWorksCharge extends Item{

    public ItemFireWorksCharge() {
        this(0, 1);
    }

    public ItemFireWorksCharge(Integer meta) {
        this(meta, 1);
    }

    public ItemFireWorksCharge(Integer meta, int count) {
        super(FIREWORKS_CHARGE, 0, count, "FireWorks Charge");
    }
}
