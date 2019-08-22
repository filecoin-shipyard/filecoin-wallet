package pro.xjxh.wallet.controller.api;

import com.alibaba.fastjson.JSON;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.rockyang.filecoin.vo.res.KeyInfo;
import org.rockyang.filecoin.vo.res.MessageStatusRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

	/**
	 * create a new wallet
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/newAddress")
	public BizVo newAddress() throws IOException
	{
		KeyInfo keyInfo = filecoinService.newAddress();
		AddressVo addressVo = new AddressVo(keyInfo.getAddress());
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

	@PostMapping("/transaction/send")
	public BizVo sendTransaction(
			@RequestParam String from,
			@RequestParam String to,
			@RequestParam BigDecimal value,
			@RequestParam(required = false) BigDecimal gasPrice,
			@RequestParam(required = false) Integer gasLimit
	)
	{
		// check is the payment address is imported to the client node
		KeyInfo keyInfo = filecoinService.exportWallet(from);
		if (keyInfo == null) {
			return BizVo.fail("请先导入钱包");
		}
		String cid = filecoinService.sendTransaction(from, to, value, gasPrice, gasLimit);
		return BizVo.success((Object) cid);
	}

	/**
	 * import wallet by privateKey
	 * @return
	 */
	@PostMapping("/wallet/import/privateKey")
	public BizVo importWalletPrivateKey(String privateKey)
	{
		String address = filecoinService.importWallet(privateKey);
		BigDecimal balance = filecoinService.getBalance(address);
		AddressVo addressVo = new AddressVo(address);
		addressVo.setBalance(balance);
		return BizVo.success(addressVo);
	}

	/**
	 * import wallet by wallet file
	 * @return
	 */
	@PostMapping("/wallet/import/file")
	public BizVo importWalletFile(MultipartFile file) throws IOException
	{
		byte[] bytes = file.getBytes();
		String text = new String(bytes);
		KeyExport keyExport = JSON.parseObject(text, KeyExport.class);
		return importWalletPrivateKey(keyExport.getKeyInfos().get(0).getPrivateKey());
	}

	/**
	 * query balance of the specified wallet by address
	 * @param address
	 * @return
	 */
	@PostMapping("/wallet/balance")
	public BizVo getBalance(String address)
	{
		return BizVo.success(filecoinService.getBalance(address));
	}

	/**
	 * query message status by cid
	 * @param cid
	 * @return
	 */
	@PostMapping("/message/get")
	public BizVo queryMessage(String cid)
	{
		MessageStatusRes message = filecoinService.getMessageStatus(cid);
		return BizVo.success(message);
	}

	/**
	 * request Mock FIL token from filecoin dev network
	 * @param address
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/faucet")
	public BizVo faucet(String address) throws IOException
	{
		OkHttpClient client = new OkHttpClient();
		FormBody.Builder formBody = new FormBody.Builder();
		formBody.add("target", address);
		Request request = new Request.Builder()
				.url("http://user.kittyhawk.wtf:9797/tap")
				.post(formBody.build())
				.build();
		Response response;
		response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			return BizVo.success(response.body().string());
		}
		return BizVo.fail("获取测试代币失败，" + response.message());
	}
}
