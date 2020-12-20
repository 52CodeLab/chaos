package club.gclmit.chaos.web.result;

import club.gclmit.chaos.core.util.DateUtils;
import club.gclmit.chaos.core.util.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据封装
 *
 * @author gclm
 */
@Getter
@Setter
@ApiModel(value = "通用消息响应", description = "通用消息响应")
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String TIMESTAMP =  String.valueOf(DateUtils.getMilliTimestamp());

    /**
     * 响应状态码
     */
    @ApiModelProperty(value = "响应状态码", required = true)
    private Integer code;

    /**
     * 响应提示
     */
    @ApiModelProperty(value = "响应提示消息", required = true)
    private String message;

    /**
     * 响应时间戳
     */
    @ApiModelProperty(value = "响应时间戳", required = true)
    private String timestamp = TIMESTAMP;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据", required = false)
    private Object data;


    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result result(boolean flag) {
        if (flag) {
            return ok();
        }
        return fail("");
    }

    public static Result result(ApiCode apiCode, Object data) {
        return result(apiCode, null, data);
    }

    public static Result result(ApiCode apiCode, String msg, Object data) {
        String message = apiCode.getMessage();
        if (StringUtils.isNotBlank(msg)) {
            message = msg;
        }
        return new Result(apiCode.getCode(), message, data);
    }

    public static Result ok() {
        return ok(null);
    }

    public static Result ok(Object data) {
        return result(ApiCode.OK, data);
    }

    public static Result ok(String message, Object data) {
        return result(ApiCode.OK, message, data);
    }

    public static Result ok(Integer code, String message, Object data) {
        return new Result(code, message, data);
    }

    public static Result okMap(String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return ok(map);
    }

    public static Result fail() {
        return fail(ApiCode.FAIL);
    }

    public static Result fail(String message) {
        return result(ApiCode.FAIL, message, null);
    }

    public static Result fail(ApiCode apiCode) {
        return result(apiCode, null);
    }

    public static Result fail(ApiCode apiCode, Object data) {
        if (ApiCode.OK == apiCode) {
            throw new RuntimeException("失败结果状态码不能为：" + apiCode.getCode());
        }
        return result(apiCode, data);
    }

    public static Result fail(Integer code, String message) {
        return new Result(code, message, null);
    }

    public static Result fail(String message, Object data) {
        return new Result(ApiCode.FAIL.getCode(), message, data);
    }

    public static Result fail(Integer code, String message, Object data) {
        return new Result(code, message, data);
    }

    public static Result failMap(String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return result(ApiCode.FAIL, map);
    }
}
