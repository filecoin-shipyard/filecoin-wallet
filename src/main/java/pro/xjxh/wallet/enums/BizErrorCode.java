package pro.xjxh.wallet.enums;

/**
 * BizVo 错误代码
 * @author yangjian
 * @since  2018/7/25
 */
public enum BizErrorCode {

    SUCCESS("000", "操作成功."),
    FAIL("001", "操作失败."),
    PARAM_ERROR("002", "参数错误."),
    UNKONW_ERROR("101", "系统开了小差."),
    NO_RECORDS("102", "查无记录."),
    UNSURPPORT_OPT("103", "暂时不支持此操作.");


    private String code;

    private String message;

    BizErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
