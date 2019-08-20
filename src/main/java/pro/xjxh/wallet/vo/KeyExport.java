package pro.xjxh.wallet.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangjian
 */
public final class KeyExport {

	@JSONField(name = "KeyInfo")
	private List<KeyInfo> keyInfos = new ArrayList<>();

	public static final class KeyInfo {

		private String privateKey;
		private String curve;

		public KeyInfo(String privateKey, String curve)
		{
			this.privateKey = privateKey;
			this.curve = curve;
		}

		public String getPrivateKey() {
			return privateKey;
		}

		public void setPrivateKey(String privateKey) {
			this.privateKey = privateKey;
		}

		public String getCurve() {
			return curve;
		}

		public void setCurve(String curve) {
			this.curve = curve;
		}

	}

	public List<KeyInfo> getKeyInfos() {
		return keyInfos;
	}

	public void setKeyInfos(List<KeyInfo> keyInfos) {
		this.keyInfos = keyInfos;
	}

	public void addKeyInfo(KeyInfo keyInfo)
	{
		this.keyInfos.add(keyInfo);
	}
}
