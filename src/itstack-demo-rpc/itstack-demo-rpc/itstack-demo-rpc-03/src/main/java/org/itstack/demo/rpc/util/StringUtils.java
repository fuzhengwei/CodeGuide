package org.itstack.demo.rpc.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/7
 */
public class StringUtils {

    /**
     * The empty String {@code ""}.
     *
     * @since 2.0
     */
    public static final String EMPTY = "";

    // Empty checks
    //-----------------------------------------------------------------------

    /**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     * <p/>
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     * <p/>
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().</p>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * <p>Checks if a CharSequence is not empty ("") and not null.</p>
     * <p/>
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not empty and not null
     * @since 3.0 Changed signature from isNotEmpty(String) to isNotEmpty(CharSequence)
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !StringUtils.isEmpty(cs);
    }

    /**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     * <p/>
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     * <p/>
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     * not empty and not null and not whitespace
     * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !StringUtils.isBlank(cs);
    }

    /**
     * <pre>
     * StringUtils.trim(null)          = null
     * StringUtils.trim("")            = ""
     * StringUtils.trim("     ")       = ""
     * StringUtils.trim("abc")         = "abc"
     * StringUtils.trim("    abc    ") = "abc"
     * </pre>
     *
     * @param str the String to be trimmed, may be null
     * @return the trimmed string, {@code null} if null String input
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * <pre>
     * StringUtils.trimToNull(null)          = null
     * StringUtils.trimToNull("")            = null
     * StringUtils.trimToNull("     ")       = null
     * StringUtils.trimToNull("abc")         = "abc"
     * StringUtils.trimToNull("    abc    ") = "abc"
     * </pre>
     *
     * @param str the String to be trimmed, may be null
     * @return the trimmed String,
     * {@code null} if only chars &lt;= 32, empty or null String input
     * @since 2.0
     */
    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * <pre>
     * StringUtils.trimToEmpty(null)          = ""
     * StringUtils.trimToEmpty("")            = ""
     * StringUtils.trimToEmpty("     ")       = ""
     * StringUtils.trimToEmpty("abc")         = "abc"
     * StringUtils.trimToEmpty("    abc    ") = "abc"
     * </pre>
     *
     * @param str the String to be trimmed, may be null
     * @return the trimmed String, or an empty String if {@code null} input
     * @since 2.0
     */
    public static String trimToEmpty(String str) {
        return str == null ? EMPTY : str.trim();
    }


    /**
     * Converts a <code>byte[]</code> to a String using the specified character encoding.
     *
     * @param bytes       the byte array to read from
     * @param charsetName the encoding to use, if null then use the platform default
     * @return a new String
     * @throws UnsupportedEncodingException If the named charset is not supported
     * @throws NullPointerException         if the input is null
     * @since 3.1
     */
    public static String toString(byte[] bytes, String charsetName) throws UnsupportedEncodingException {
        return charsetName == null ? new String(bytes) : new String(bytes, charsetName);
    }

    // Defaults
    //-----------------------------------------------------------------------

    /**
     * <p>Returns either the passed in String,
     * or if the String is {@code null}, an empty String ("").</p>
     * <p/>
     * <pre>
     * StringUtils.defaultString(null)  = ""
     * StringUtils.defaultString("")    = ""
     * StringUtils.defaultString("bat") = "bat"
     * </pre>
     *
     * @param str the String to check, may be null
     * @return the passed in String, or the empty String if it
     * was {@code null}
     * @see String#valueOf(Object)
     */
    public static String defaultString(final String str) {
        return str == null ? EMPTY : str;
    }

    /**
     * 按分隔符分隔的数组，包含空值<br/>
     * 例如 "1,2,,3," 返回 [1,2,,3,] 5个值
     *
     * @param src       原始值
     * @param separator 分隔符
     * @return 字符串数组
     */
    public static String[] split(String src, String separator) {
        if (isEmpty(separator)) {
            return new String[]{src};
        }
        if (isEmpty(src)) {
            return new String[0];
        }
        return src.split(separator, -1);
    }

    /**
     * 按逗号或者分号分隔的数组，排除空字符<br/>
     * 例如 " 1,2 ,, 3 , " 返回 [1,2,3] 3个值<br/>
     * " 1;2 ;; 3 ; " 返回 [1,2,3] 3个值<br/>
     *
     * @param src 原始值
     * @return 字符串数组
     */
    public static String[] splitWithCommaOrSemicolon(String src) {
        if (isEmpty(src)) {
            return new String[0];
        }
        String[] ss = split(src.replace(',', ';'), ";");
        List<String> list = new ArrayList<String>();
        for (String s : ss) {
            if (!isBlank(s)) {
                list.add(s.trim());
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * 连接字符串数组
     *
     * @param strings   字符串数组
     * @param separator 分隔符
     * @return 按分隔符分隔的字符串
     */
    public static String join(String[] strings, String separator) {
        if (strings == null || strings.length == 0) {
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            if (isNotBlank(string)) {
                sb.append(string).append(separator);
            }
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - separator.length()) : StringUtils.EMPTY;
    }

    /**
     * 默认逗号分隔的字符串
     *
     * @param strings 字符串数组
     * @return 按分隔符分隔的字符串
     */
    public static String joinWithComma(String... strings) {
        return join(strings, ",");
    }

    /**
     * 自定义的key是否合法
     *
     * @param paramkey 参数key
     * @return 是否合法
     */
    public static boolean isValidParamKey(String paramkey) {
        char c = paramkey.charAt(0);
        return c != Constants.HIDE_KEY_PREFIX && c != Constants.INTERNAL_KEY_PREFIX;
    }

    /**
     * 自定义的key是否内置key
     *
     * @param paramkey 参数key
     * @return 是否合法
     */
    public static boolean isInternalParamKey(String paramkey) {
        char c = paramkey.charAt(0);
        return c == Constants.INTERNAL_KEY_PREFIX;
    }

    /**
     * 对象转string
     *
     * @param o 对象
     * @return 不为null执行toString方法
     */
    public static String toString(Object o) {
        return o != null ? o.toString() : null;
    }
}
