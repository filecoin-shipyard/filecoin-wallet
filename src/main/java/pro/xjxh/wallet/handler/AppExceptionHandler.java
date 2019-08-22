package pro.xjxh.wallet.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pro.xjxh.wallet.enums.BizErrorCode;
import pro.xjxh.wallet.vo.BizVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.SocketTimeoutException;


/**
 * Exception handler
 * @author yangjian
 */
@ControllerAdvice
public class AppExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BizVo handle(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException
    {

        // TODO logger here
        logger.error("Exception for URL: {}", request.getRequestURI());
        logger.error("======> {}", e);
        String message = BizErrorCode.FAIL.getMessage();
        if (e instanceof SocketTimeoutException) {
            message = "请求超时";
        }

        //ajax request
        if (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return BizVo.fail(message);
        } else {
            response.sendRedirect("/error");
            return null;
        }

    }
}
