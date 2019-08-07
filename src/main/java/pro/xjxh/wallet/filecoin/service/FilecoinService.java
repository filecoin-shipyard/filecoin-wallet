package pro.xjxh.wallet.filecoin.service;

import pro.xjxh.wallet.filecoin.vo.res.KeyInfo;
import pro.xjxh.wallet.filecoin.vo.res.MessageStatusRes;

import java.math.BigDecimal;

/**
 * Filecoin 钱包客户端接口
 * @author yangjian
 */
public interface FilecoinService {

	/**
	 * 创建一个新钱包
	 * @return
	 */
	KeyInfo newAddress();

	/**
	 * 发送一笔转账交易
	 * @return 返回交易凭据哈希
	 */
	String sendTransaction(String from, String to, BigDecimal value, BigDecimal gasPrice, Integer gasLimit);

	/**
	 * 根据交易哈希查询某一笔交易转账交易详情
	 * @param cid
	 * @return
	 */
	MessageStatusRes.Message getTransactionByTxHash(String cid);

	/**
	 * 查询钱包账户余额
	 * @param address
	 * @return
	 */
	BigDecimal getBalance(String address);

}
