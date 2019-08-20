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
	public String index()
	{
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

	@GetMapping("/transfer")
	public String transfer(HttpServletRequest request)
	{
		request.setAttribute("title", "Gamma Wallet - 发送 FIL");
		return "transfer";
	}
}
