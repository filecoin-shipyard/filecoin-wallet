package pro.xjxh.wallet.service.impl;

import org.rockyang.filecoin.rpc.Filecoin;
import org.rockyang.filecoin.vo.res.KeyInfo;
import org.rockyang.filecoin.vo.res.MessageStatusRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.xjxh.wallet.service.FilecoinService;

import java.math.BigDecimal;

/**
 * @author yangjian
 */
@Service
public class FilecoinServiceImpl implements FilecoinService {

	@Autowired
	private Filecoin filecoin;

	@Override
	public KeyInfo newAddress()
	{
		String address = filecoin.newAddress();
		return filecoin.walletExport(address);
	}

	@Override
	public String sendTransaction(String from, String to, BigDecimal value, BigDecimal gasPrice, Integer gasLimit)
	{
		if (gasPrice == null) {
			gasPrice = BigDecimal.valueOf(0.001);
		}
		if (gasLimit == null) {
			gasLimit = 300;
		}
		return filecoin.sendTransaction(from, to, value, gasPrice, gasLimit);
	}

	@Override
	public MessageStatusRes.Message getTransaction(String cid)
	{
		return filecoin.getTransaction(cid);
	}

	@Override
	public BigDecimal getBalance(String address)
	{
		return filecoin.getBalance(address);
	}
}
