package pro.xjxh.wallet.controller.api;

import com.alibaba.fastjson.JSON;
import org.rockyang.filecoin.vo.res.KeyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.xjxh.wallet.service.FilecoinService;
import pro.xjxh.wallet.vo.AddressVo;
import pro.xjxh.wallet.vo.BizVo;
import pro.xjxh.wallet.vo.KeyExport;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

/**
 * Filecoin wallet api
 * @author yangjian
 */
@RestController
@RequestMapping("/api/filecoin")
public class FilecoinController {

	@Autowired
	private FilecoinService filecoinService;


	@GetMapping("/newAddress")
	public BizVo newAddress() throws IOException
	{
		KeyInfo keyInfo = filecoinService.newAddress();
		AddressVo addressVo = new AddressVo();
		addressVo.setAddress(keyInfo.getAddress());
		addressVo.setPrivateKey(keyInfo.getPrivateKey());
		// write wallet file to tmp file
		String walletFile = "/tmp/" + keyInfo.getAddress() + ".json";
		OutputStream os = new FileOutputStream(walletFile);
		KeyExport keyExport = new KeyExport();
		KeyExport.KeyInfo key = new KeyExport.KeyInfo(keyInfo.getPrivateKey(), keyInfo.getCurve());
		keyExport.addKeyInfo(key);
		JSON.writeJSONString(os, keyExport);
		return BizVo.success(addressVo);
	}

	@GetMapping("/balance")
	public BigDecimal getBalance(String address)
	{
		return filecoinService.getBalance(address);
	}

	@PostMapping("/transaction/send")
	public String sendTransaction(
			@RequestParam String from,
			@RequestParam String to,
			@RequestParam BigDecimal value,
			@RequestParam(required = false) BigDecimal gasPrice,
			@RequestParam(required = false) Integer gasLimit
	)
	{
		return filecoinService.sendTransaction(from, to, value, gasPrice, gasLimit);
	}
}
