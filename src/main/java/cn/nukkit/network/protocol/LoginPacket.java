package cn.nukkit.network.protocol;

/**
 * Created by on 15-10-13.
 */
public class LoginPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.LOGIN_PACKET;

    public int protocol;
    
    //デバッグ用
	private String data = "";

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.protocol = this.getInt();
        this.setBuffer(this.getByteArray(), 0);
        /* デバッグ用
         * Json出力
        try {
			Utils.writeFile("./logindata/data.json", data);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		*/
    }

    @Override
    public void encode() {

    }

    public int getProtocol() {
        return protocol;
    }
}
