package pro.xjxh.wallet.vo;

/**
 * @author yangjian
 */
public class AddressVo {
	// 钱包地址
	private String address;
	// 钱包私钥
	private String privateKey;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
}
