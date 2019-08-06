package pro.xjxh.filecoin.wallet.api;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;
import java.util.Map;

/**
 * @author yangjian
 */
public interface RpcService {

    @GET("/api/chain/ls")
    Call<List<Map<String, Object>>> getChainInfo();

}
