package org.blankjee.cloudfast.common.util;

import cn.hutool.core.util.IdUtil;
import io.netty.util.internal.StringUtil;
import org.thymeleaf.util.ArrayUtils;
import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author blankjee
 * @Date 2020/7/6 17:08
 */
public class CommonUtil {
    /**
     * 唯一单号生成
     * @return
     */
    public static long genId() {
        return IdUtil.getSnowflake(0, 0).nextId();
    }

    /**
     * 匹配规则
     * @param keywords
     * @param orderData
     * @return
     */
    public static boolean checkKeywordsTrue(String keywords, String orderData) {
        if (StringUtils.isEmpty(keywords)) {
            return true;
        }
        if (StringUtils.isEmpty(orderData)) {
            return false;
        }
        String[] keywordStr = keywords.split(",");
        if (ArrayUtils.contains(keywordStr, orderData)) {
            return true;
        }
        Pattern r = Pattern.compile(keywords);
        Matcher matcher = r.matcher(orderData);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
