package pro.xjxh.filecoin.wallet.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.xjxh.filecoin.wallet.api.exception.ApiError;
import pro.xjxh.filecoin.wallet.api.exception.ApiException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * @author yangjian
 */
@Service
public class ApiService {

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create());

    private static Retrofit retrofit;

    private static RpcService rpcService;

    @Value("${rpc.logDebug}")
    private Boolean logDebug;
    @Value("${rpc.baseUrl}")
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
        rpcService =  retrofit.create(RpcService.class);
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

    public List<Map<String, Object>> getChainInfo()
    {
        return executeSync(rpcService.getChainInfo());
    }
}
