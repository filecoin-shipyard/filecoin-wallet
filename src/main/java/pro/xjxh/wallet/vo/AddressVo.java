package pro.xjxh.wallet.vo;

import java.math.BigDecimal;

/**
 * wallet address vo
 * @author yangjian
 */
public class AddressVo {

	private String address;
	private String privateKey;
	private BigDecimal balance;

	public AddressVo(String address) {
		this.address = address;
	}

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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
