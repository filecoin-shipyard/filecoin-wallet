package pro.xjxh.wallet.service;

import org.rockyang.filecoin.vo.res.KeyInfo;
import org.rockyang.filecoin.vo.res.MessageStatusRes;

import java.math.BigDecimal;

/**
 * @author yangjian
 */
public interface FilecoinService {

	/**
	 * 创建一个新的钱包地址
	 * @return
	 */
	KeyInfo newAddress();

	/**
	 * 发起一笔转账交易
	 * @param from
	 * @param to
	 * @param value
	 * @param gasPrice
	 * @param gasLimit
	 * @return
	 */
	String sendTransaction(String from, String to, BigDecimal value, BigDecimal gasPrice, Integer gasLimit);

	/**
	 * 查询交易状态
	 * @param cid
	 * @return
	 */
	MessageStatusRes.Message getTransaction(String cid);

	/**
	 * 查询钱包地址
	 * @param address
	 * @return
	 */
	BigDecimal getBalance(String address);
}
