package pro.xjxh.wallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * wallet ui controller
 * @author yangjian
 */
@Controller
public class WalletController {

	@GetMapping({"", "/"})
	public String index(HttpServletRequest request)
	{
		request.setAttribute("title", "Gamma 钱包");
		return "index";
	}

	/**
	 * 下载钱包 keystore file
	 * @param address
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/wallet/download/{address}")
	public void downloadWallet(@PathVariable("address") String address, HttpServletResponse response) throws Exception {

		File destination = new File("/tmp/", address+".json");
		InputStream fis = new BufferedInputStream(new FileInputStream(destination));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		// 清空response
		response.reset();
		// 设置response的Header
		response.addHeader("Content-Disposition", "attachment;filename=" + destination.getName());
		response.addHeader("Content-Length", "" + destination.length());
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		toClient.write(buffer);
		toClient.flush();
		toClient.close();
	}

	/**
	 * FIL 转账
	 * @param request
	 * @return
	 */
	@GetMapping("/transfer")
	public String transfer(HttpServletRequest request)
	{
		request.setAttribute("title", "Gamma Wallet - 发送 FIL");
		return "transfer";
	}

	/**
	 * 导入钱包
	 * @return
	 */
	@GetMapping("/wallet/import")
	public String walletImport(HttpServletRequest request)
	{
		request.setAttribute("title", "导入钱包");
		return "import-wallet";
	}

	@GetMapping("/wallet/balance")
	public String balance(HttpServletRequest request)
	{
		request.setAttribute("title", "查询余额");
		return "balance";
	}

	/**
	 * query message by cid
	 * @return
	 */
	@GetMapping("/message/query")
	public String queryMessage(HttpServletRequest request)
	{
		request.setAttribute("title", "交易查询");
		return "message-query";
	}

	@GetMapping("/faucet")
	public String faucet(HttpServletRequest request)
	{
		request.setAttribute("title", "免费获取测试代币");
		return "faucet";
	}
}
