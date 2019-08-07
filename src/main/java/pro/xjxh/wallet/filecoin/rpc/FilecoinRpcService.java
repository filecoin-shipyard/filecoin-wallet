package pro.xjxh.wallet.filecoin.rpc;

import pro.xjxh.wallet.filecoin.vo.res.MessageStatusRes;
import pro.xjxh.wallet.filecoin.vo.res.SendMessageRes;
import pro.xjxh.wallet.filecoin.vo.res.WalletExportRes;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Filecoin 钱包 RPC 服务接口
 * @author yangjian
 */
public interface FilecoinRpcService {

	/**
	 * 创建新钱包地址
	 * @return
	 */
	@GET("/api/address/new")
	Call<Map<String, String>> newAddress();

	/**
	 * 导出钱包私钥
	 * @param address
	 * @return
	 */
	@GET("/api/wallet/export")
	Call<WalletExportRes> exportWallet(@Query("arg") String address);

	/**
	 * 发送转账交易
	 * @param target
	 * @param from
	 * @param value
	 * @param gasPrice
	 * @param gasLimit
	 * @return
	 */
	@GET("/api/message/send/{target}")
	Call<SendMessageRes> sendMessage(@Path("target") String target,
	                                 @Query("from") String from,
	                                 @Query("value") BigDecimal value,
	                                 @Query("gas-price") BigDecimal gasPrice,
	                                 @Query("gas-limit") Integer gasLimit);

	/**
	 * 查看交易状态
	 * @param cid
	 * @return
	 */
	@GET("/api/message/status")
	Call<MessageStatusRes> getMessageStatus(@Query("arg") String cid);

	/**
	 * 查询钱包余额
	 * @param address
	 * @return
	 */
	@GET("/api/wallet/balance")
	Call<BigDecimal> getBalance(@Query("arg") String address);






}
