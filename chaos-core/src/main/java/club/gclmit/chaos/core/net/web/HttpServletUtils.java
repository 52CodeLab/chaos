package club.gclmit.chaos.core.net.web;

import club.gclmit.chaos.core.lang.Assert;
import club.gclmit.chaos.core.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * HttpServlet Request/Response 工具类
 * </p>
 *
 * @author gclm
 */
public class HttpServletUtils {

    /**
     * Http 魔法值
     */
    public static final String UNKNOWN = "UNKNOWN";

    /**
     * localhost 魔法值
     */
    public static final String LOCALHOST = "0:0:0:0:0:0:0:1";

    /**
     * 默认 host
     */
    public static final String DEFAULT_HOST = "127.0.0.1";

    /**
     * 上传内容类型
     */
    private static final String UPLOAD_CONTENT_TYPE = "multipart/form-data";

    /**
     * 默认请求内容类型
     */
    private static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";

    /**
     * 获取客户端 ip
     *
     * @param request http request instance
     * @return ip address
     */
    public static String getClientIp(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return LOCALHOST.equals(ip) ? DEFAULT_HOST : ip;
    }

    /**
     * get all request header
     *
     * @param request http request instance
     * @return map
     */
    public static Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        Map<String, String> headers = new HashMap();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String headerName = enumeration.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        return headers;
    }

    /**
     * get all response header
     *
     * @param response http response instance
     * @return map
     */
    public static Map<String, String> getResponseHeaders(HttpServletResponse response) {
        Assert.notNull(response, "response instance is null.");
        Map<String, String> headers = new HashMap();
        Iterator<String> iterator = response.getHeaderNames().iterator();
        while (iterator.hasNext()) {
            String headerName = iterator.next();
            String headerValue = response.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        return headers;
    }

    /**
     * get request header by name
     *
     * @param request    http request instance
     * @param headerName header name
     * @return header value
     */
    public static String getHeader(HttpServletRequest request, String headerName) {
        Assert.notNull(request, "request instance is null.");
        Assert.notNull(headerName, "request header name is null.");
        return request.getHeader(headerName);
    }

    /**
     * get request path param
     *
     * @param request http request instance
     * @return path param
     */
    public static Map getPathParams(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        Map map = new HashMap();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }

    /**
     * get request uri
     *
     * @param request http request instance
     * @return request uri
     */
    public static String getUri(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        return request.getRequestURI();
    }

    /**
     * 获取客户端请求方式
     * eq:
     * - ajax
     * - form
     * - websocket
     *
     * @author gclm
     * @param request http request instance
     * @return java.lang.String
     */
    public static String getRequestType(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        return getHeader(request, "X-Requested-With");
    }

    /**
     * 获取Session Id
     *
     * @author gclm
     * @param request http request instance
     * @return java.lang.String
     */
    public static String getSessionId(HttpServletRequest request) {
        return request.getSession().getId();
    }

    /**
     * 获取用户代理
     *
     * @author gclm
     * @param request http request instance
     * @return java.lang.String
     */
    public static String getUserAgent(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        return getHeader(request, "User-Agent");
    }

    /**
     * <p>
     * 是否是文件上传类型
     * </p>
     *
     * @author gclm
     * @param request http request instance
     * @return boolean
     */
    public static boolean isFileUpload(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        return getContentType(request).startsWith(UPLOAD_CONTENT_TYPE);
    }

    /**
     * <p>
     * 获取请求内容
     * </p>
     *
     * @author gclm
     * @param request http request instance
     * @return boolean
     */
    public static String getContentType(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        return StringUtils.isEmpty(request.getContentType()) ? DEFAULT_CONTENT_TYPE : request.getContentType();
    }


    /**
     * 获取 requestBody 内容
     *
     * @author gclm
     * @param request http request instance
     * @return java.lang.String
     * @throws IOException 获取HttpServletRequest Body 异常
     */
    public static String getRequestBody(HttpServletRequest request) throws IOException {
        Assert.notNull(request, "request instance is null.");
        RequestWrapper requestWrapper;
        if (request instanceof RequestWrapper) {
            requestWrapper = (RequestWrapper) request;
        } else {
            requestWrapper = new RequestWrapper(request);
        }
        return StringUtils.isNotBlank(requestWrapper.getBody()) ? requestWrapper.getBody() : request.getQueryString();
    }

    /**
     * 获取 ResponseBody 内容
     *
     * @author gclm
     * @param response HttpServletResponse
     * @return java.lang.String
     */
    public static String getResponseBody(HttpServletResponse response) {
        Assert.notNull(response, "response instance is null.");
        ResponseWrapper responseWrapper;
        if (response instanceof ResponseWrapper) {
            responseWrapper = (ResponseWrapper) response;
        } else {
            responseWrapper = new ResponseWrapper(response);
        }
        return responseWrapper.getBody();
    }
}
