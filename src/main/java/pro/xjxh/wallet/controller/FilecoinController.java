package pro.xjxh.wallet.controller;

import org.rockyang.filecoin.vo.res.KeyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.xjxh.wallet.service.FilecoinService;
import pro.xjxh.wallet.vo.AddressVo;

import java.math.BigDecimal;

/**
 * @author yangjian
 */
@RestController
@RequestMapping("/filecoin")
public class FilecoinController {

	@Autowired
	private FilecoinService filecoinService;


	@GetMapping("/newAddress")
	public AddressVo newAddress()
	{
		KeyInfo keyInfo = filecoinService.newAddress();
		AddressVo addressVo = new AddressVo();
		addressVo.setAddress(keyInfo.getAddress());
		addressVo.setPrivateKey(keyInfo.getPrivateKey());
		return addressVo;
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
