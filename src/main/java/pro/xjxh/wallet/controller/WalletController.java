package pro.xjxh.wallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
