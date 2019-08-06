package pro.xjxh.filecoin.wallet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.xjxh.filecoin.wallet.api.ApiService;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilecoinWalletApplicationTests {

	@Autowired
	private ApiService apiService;

	@Test
	public void contextLoads()
	{
		List<Map<String, Object>> chainInfo = apiService.getChainInfo();
		System.out.println(chainInfo);
	}

}
