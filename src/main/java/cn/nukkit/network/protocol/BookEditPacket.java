package cn.nukkit.network.protocol;

/**
 * author: PikyCZ
 * CoreX Project
 */

public class BookEditPacket extends DataPacket {

    //本を編集するためのパケットです

    public static final int TYPE_REPLACE_PAGE = 0;
    public static final int TYPE_ADD_PAGE = 1;
    public static final int TYPE_DELETE_PAGE = 2;
    public static final int TYPE_SWAP_PAGES = 3;
    public static final int TYPE_SIGN_BOOK = 4;

    //typeには 0~5までの数値が入ります
    public int type;

    /** @var int */
    public int inventorySlot;
    /** @var int */
    public int pageNumber;
    /** @var int */
    public int secondaryPageNumber;

	/** @var string */
    public String content1;
	/** @var string */
    public String content2;

	/** @var string */
    public String title;
    
	/** @var string */
    public String author;



    @Override
    public byte pid() {
        return ProtocolInfo.BOOK_EDIT_PACKET;
    }

    @Override
    public void decode() {
        this.type = this.getByte();
        this.inventorySlot = this.getByte();

        switch (this.type) {
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
        this.putByte((byte) this.type);
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
