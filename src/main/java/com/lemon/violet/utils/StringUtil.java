package com.lemon.violet.utils;

import org.springframework.util.StringUtils;

public class StringUtil {
    /**
     * 拼接url 比如入参 http baidu.com page1
     * 结果: http://baidu.com/page1
     * <p>
     * 入参 http://  baidu.com/   page1
     * 结果: http://baidu.com/page1
     * <p>
     * 入参 http://  baidu.com   page1
     * 结果: http://baidu.com/page1
     *
     * @param protocol
     * @param domain
     * @param source
     * @return
     */
    public static String getUrl(String protocol, String domain, String source) {
        if (!StringUtils.hasText(protocol) || !StringUtils.hasText(domain) || !StringUtils.hasText(domain)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(protocol)
                .append(protocol.endsWith("://") ? domain : "://" + domain)
                .append(domain.endsWith("/") ? source : "/" + source);
        return sb.toString();
    }
}
