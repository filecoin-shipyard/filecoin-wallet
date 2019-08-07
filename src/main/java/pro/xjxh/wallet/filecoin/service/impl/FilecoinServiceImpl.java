package pro.xjxh.wallet.filecoin.service.impl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.xjxh.wallet.exception.ApiError;
import pro.xjxh.wallet.exception.ApiException;
import pro.xjxh.wallet.filecoin.rpc.FilecoinRpcService;
import pro.xjxh.wallet.filecoin.service.FilecoinService;
import pro.xjxh.wallet.filecoin.vo.res.KeyInfo;
import pro.xjxh.wallet.filecoin.vo.res.MessageStatusRes;
import pro.xjxh.wallet.filecoin.vo.res.SendMessageRes;
import pro.xjxh.wallet.filecoin.vo.res.WalletExportRes;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Filecoin 钱包服务实现
 * @author yangjian
 */
@Service
public class FilecoinServiceImpl implements FilecoinService {

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create());

    private static Retrofit retrofit;

    private static FilecoinRpcService rpcService;

    @Value("${rpc.logDebug}")
    private Boolean logDebug;
    @Value("${filecoin.rpc.baseUrl}")
    private String baseUrl;

    @PostConstruct
    public void init()
    {
        // open debug log model
        if (logDebug) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(loggingInterceptor);
        }

        builder.baseUrl(baseUrl);
        builder.client(httpClient.build());
        builder.addConverterFactory(JacksonConverterFactory.create());
        retrofit = builder.build();
        rpcService =  retrofit.create(FilecoinRpcService.class);
    }

    /**
     * 执行远程 API 同步调用
     * @param call
     * @param <T>
     * @return
     */
    public static <T> T executeSync(Call<T> call)
    {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                ApiError apiError = getApiError(response);
                throw new ApiException(apiError);
            }
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * get api error message
     * @param response
     * @return
     * @throws IOException
     * @throws ApiException
     */
    private static ApiError getApiError(Response<?> response) throws IOException, ApiException
    {
        return (ApiError) retrofit.responseBodyConverter(ApiError.class, new Annotation[0]).convert(response.errorBody());
    }

    @Override
    public KeyInfo newAddress()
    {
        Map<String, String> map = executeSync(rpcService.newAddress());
        String address = map.get("Address");
        WalletExportRes res = executeSync(rpcService.exportWallet(address));
        KeyInfo keyInfo = res.getKeyInfo().get(0);
        keyInfo.setAddress(address);
        return res.getKeyInfo().get(0);
    }

    @Override
    public String sendTransaction(String from, String to, BigDecimal value, BigDecimal gasPrice, Integer gasLimit)
    {
        SendMessageRes res = executeSync(rpcService.sendMessage(to, from, value, gasPrice, gasLimit));
        return res.getCid().getRoot();
    }

    @Override
    public MessageStatusRes.Message getTransactionByTxHash(String cid)
    {
        MessageStatusRes res = executeSync(rpcService.getMessageStatus(cid));
        MessageStatusRes.Message message = null;
        if (res.isOnChain()) {
            message = res.getChainMsg().getMessage().getMeteredMessage().getMessage();
            message.setSuccess(true);
        } else {
            message = res.getPoolMsg().getMeteredMessage().getMessage();
            message.setSuccess(false);
        }
        return message;
    }

    @Override
    public BigDecimal getBalance(String address)
    {
        return executeSync(rpcService.getBalance(address));
    }
}
