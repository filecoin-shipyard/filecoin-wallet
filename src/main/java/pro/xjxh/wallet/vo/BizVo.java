package pro.xjxh.wallet.vo;

import com.alibaba.fastjson.JSON;
import pro.xjxh.wallet.enums.BizErrorCode;

/**
 * http 请求返回统一 VO
 * @author yangjian
 */
public class BizVo {

    /**
     * 错误代码,成功返回 000, 否则返回其他
     */
    private String code;

    /**
     * 返回提示信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 附带信息
     */
    private Object extra;

    /**
     * 总记录数
     */
    private Long count = 0L;

    /**
     * 页码
     */
    private Integer page = 0;

    /**
     * 每页记录数
     */
    private Integer pageSize = 0;

    public BizVo() {
    }

    /**
     * 构造方法
     * @param bizErrorCode 错误代码
     */
    public BizVo(BizErrorCode bizErrorCode) {
        this.code = bizErrorCode.getCode();
        this.message = bizErrorCode.getMessage();
    }

    public BizVo(String code, String message) {
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    /**
     * 创建一个成功的 VO
     * @return 返回 BizVo 实例
     */
    public static BizVo success() {
        return new BizVo(BizErrorCode.SUCCESS);
    }
    public static BizVo success(Object data) {
        BizVo instance = instance(BizErrorCode.SUCCESS);
        instance.setData(data);
        return instance;
    }
    public static BizVo success(String message) {
        return new BizVo(BizErrorCode.SUCCESS.getCode(), message);
    }

    /**
     * 创建一个失败的 VO
     * @return 返回 BizVo 实例
     */
    public static BizVo fail() {
        return new BizVo(BizErrorCode.FAIL);
    }
    public static BizVo fail(String message) {
        return new BizVo(BizErrorCode.FAIL.getCode(), message);
    }

    /**
     * 创建 BizVo 实例
     * @return 返回 BizVo 实例
     */
    public static BizVo instance() {
        return new BizVo();
    }

    public static BizVo instance(String code, String message) {
        return new BizVo(code, message);
    }

    public static BizVo instance(BizErrorCode bizErrorCode) {
        return new BizVo(bizErrorCode.getCode(), bizErrorCode.getMessage());
    }

    /**
     * 判断是否成功
     * @return 返回判断结果
     */
    public boolean isSuccess(){
        return BizErrorCode.SUCCESS.getCode() == this.code;
    }

    /**
     * 设置操作结果为 success
     */
    public void setSuccess() {
        this.code = BizErrorCode.SUCCESS.getCode();
    }

    /**
     * 设置操作结果为 fail
     */
    public void setFail() {
        this.code = BizErrorCode.FAIL.getCode();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
