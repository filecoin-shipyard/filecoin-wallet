package pro.xjxh.wallet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.xjxh.wallet.filecoin.vo.res.KeyInfo;
import pro.xjxh.wallet.filecoin.service.impl.FilecoinServiceImpl;
import pro.xjxh.wallet.filecoin.vo.res.MessageStatusRes;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilecoinWalletApplicationTests {

	private static Logger logger = LoggerFactory.getLogger(FilecoinWalletApplicationTests.class);

	@Autowired
	private FilecoinServiceImpl filecoinService;

	/**
	 * 创建新地址
	 */
	@Test
	public void newAddress()
	{
		KeyInfo keyInfo = filecoinService.newAddress();
		logger.info("keyInfo: {}", keyInfo);
	}

	/**
	 * 发送交易
	 */
	@Test
	public void sendTransaction()
	{
		String from = "t16cgjiwgypve4uup27uk4xgppgd3i4nsldpid6ii";
		String to = "t1esjjrygs7adcfbjnodbpdjzulzobznnln4tmsxq";
		BigDecimal value = BigDecimal.valueOf(123.456);
		BigDecimal gasPrice = BigDecimal.valueOf(0.001);
		Integer gasLimit = 300;
		String cid = filecoinService.sendTransaction(from, to, value, gasPrice, gasLimit);
		logger.info("CID: {}", cid);
	}

	/**
	 * 查询交易状态
	 */
	@Test
	public void getTransactionStatus()
	{
		String cid = "zDPWYqFCtwpgqBEth4wFK53D8Sm9UGxhrL1tueb4RrgFDQLoKC1P";
		MessageStatusRes.Message message = filecoinService.getTransactionByTxHash(cid);
		logger.info("交易信息： {}", message);
		if (message.isSuccess()) {
			logger.info("交易确认成功.");
		} else {
			logger.warn("交易待确认");
		}
	}

	/**
	 * 查询钱包余额
	 */
	@Test
	public void getBalance()
	{
		String address = "t1esjjrygs7adcfbjnodbpdjzulzobznnln4tmsxq";
		BigDecimal balance = filecoinService.getBalance(address);
		logger.info("钱包余额：{}", balance);
	}
}
