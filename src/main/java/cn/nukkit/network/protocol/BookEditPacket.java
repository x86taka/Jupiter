package cn.nukkit.network.protocol;

public class BookEditPacket extends DataPacket {

    //本を編集するためのパケットです

	public static final byte TYPE_REPLACE_PAGE = 0;
	public static final byte TYPE_ADD_PAGE = 1;
	public static final byte TYPE_DELETE_PAGE = 2;
	public static final byte TYPE_SWAP_PAGES = 3;
	public static final byte TYPE_SIGN_BOOK = 4;

    public byte type;
    public int inventorySlot;
    public int pageNumber;
    public int secondaryPageNumber;
    public String content1 = "";
    public String content2 = "";
    public String title = "";
    public String author = "";

    @Override
    public byte pid() {
        return ProtocolInfo.BOOK_EDIT_PACKET;
    }

    @Override
    public void decode() {
        this.type = (byte) getByte();
        this.inventorySlot = this.getByte();

        switch (type) {
            case TYPE_REPLACE_PAGE:
            case TYPE_ADD_PAGE:
                this.pageNumber = this.getByte();
                this.content1 = this.getString();
                this.content2 = this.getString();
                break;
            case TYPE_DELETE_PAGE:
                this.pageNumber = this.getByte();
                break;
            case TYPE_SWAP_PAGES:
                this.pageNumber = this.getByte();
                this.secondaryPageNumber = this.getByte();
                break;
            case TYPE_SIGN_BOOK:
                this.title = this.getString();
                this.author = this.getString();
                break;
        }
    }

    @Override
    public void encode() {
        this.reset();
        this.putByte(this.type);
        this.putByte((byte) this.inventorySlot);

        switch (this.type) {
            case TYPE_REPLACE_PAGE:
            case TYPE_ADD_PAGE:
                this.putByte((byte) this.pageNumber);
                this.putString(this.content1);
                this.putString(this.content2);
                break;
            case TYPE_DELETE_PAGE:
                this.putByte((byte) this.pageNumber);
                break;
            case TYPE_SWAP_PAGES:
                this.putByte((byte) this.pageNumber);
                this.putByte((byte) this.secondaryPageNumber);
                break;
            case TYPE_SIGN_BOOK:
                this.putString(this.title);
                this.putString(this.author);
                break;
        }
    }

}
