package top.winkin.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串处理类
 *
 * @author zhaoge
 */
public class StringUtil {
    private static final Logger log = LoggerFactory.getLogger(StringUtil.class);
    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return boolean
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^\\w+[\\w\\.\\-_]*@[\\w\\.]+\\.[a-zA-Z]{2,4}$");

    /**
     * 取指定小数位的浮点数,不够小数位数时补零
     *
     * @param floStr
     * @return
     */
    public static String paseFloat(String floStr, int location) {
        if (floStr == null)
            return "";
        int index = floStr.indexOf(".");
        // 如果没有小数点.则加一个小数点.
        if (index == -1) {
            floStr = floStr + ".";
        }
        index = floStr.indexOf(".");
        int leave = floStr.length() - index;
        // 如果小于指定位数则在后面补零
        for (; leave <= location; leave++) {
            floStr = floStr + "0";
        }
        return floStr.substring(0, index + location + 1);
    }

    /**
     * @param intStr
     * @return
     */
    public static int truncateInt(String intStr) {
        int len = intStr.length();

        int result = 0;
        for (int i = 0; i < len; i++) {
            char c = intStr.charAt(i);
            if (c >= '0' && c <= '9') {
                result = result * 10;
                result += c - '0';
            } else {
                break;
            }
        }

        return result;
    }

    /**
     * 把字符型数字转换成整型.
     *
     * @param str 字符型数字
     * @return int 返回整型值。如果不能转换则返回默认值defaultValue.
     */
    public static int getInt(String str, int defaultValue) {
        if (str == null)
            return defaultValue;
        if (isInt(str)) {
            return Integer.parseInt(str);
        } else {
            return defaultValue;
        }
    }

    /**
     * 对姓名加密 隐藏姓氏
     *
     * @param name
     * @return
     */
    public static String encodeName(String name) {
        if (isBlank(name)) {
            return name;
        }
        if (name.length() == 1) {
            return name;
        }
        if (name.length() == 2) {
            return "*" + name.substring(1, 2);
        }
        if (name.length() == 3) {
            return "*" + name.substring(1, 3);
        }
        if (name.length() == 4) {
            return "**" + name.substring(2, 4);
        }
        return "**" + name.substring(2, name.length());
    }

    /**
     * 对身份证号进行部分隐藏
     *
     * @param idCard
     * @return
     */
    public static String encodeIdCard(String idCard) {
        if (StringUtil.isBlank(idCard)) {
            return idCard;
        }
        if (idCard.length() == 18) {
            return idCard.substring(0, 3) + "***********" + idCard.substring(14);
        }
        return idCard.substring(0, 3) + "********" + idCard.substring(11);
    }

    /**
     * 取整数，默认值-1
     */
    public static int getInt(String str) {
        return getInt(str, -1);
    }

    /**
     * 判断一个字符串是否为Int类型
     */
    public static boolean isInt(String str) {

        if (isNum(str)) {
            if (str.length() < 10) {
                return true;
            } else {
                try {
                    Integer.parseInt(str);
                    return true;
                } catch (Exception e) {
                }
            }

        }
        return false;
    }

    /**
     * 判断一个字符串是否空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断指定的字符串是否是空串
     */
    public static boolean isBlank(String str) {
        if (isEmpty(str))
            return true;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 针对字符串为NULL的处理
     */
    public static String notNull(String str) {
        if (str == null) {
            str = "";
        }
        return str;
    }

    /**
     * 判断2个字符串是否相等
     */
    public static boolean isequals(String str1, String str2) {
        return str1.equals(str2);
    }

    /**
     * 在长数字前补零
     *
     * @param num    数字
     * @param length 输出位数
     */
    public static String addzero(long num, int length) {
        String str = "";
        if (num < Math.pow(10, length - 1)) {
            for (int i = 0; i < (length - (num + "").length()); i++) {
                str += "0";
            }
        }
        str = str + num;
        return str;
    }

    /**
     * 在数字前补零
     *
     * @param num    数字
     * @param length 输出位数
     */
    public static String addzero(int num, int length) {
        String str = "";
        if (num < Math.pow(10, length - 1)) {
            for (int i = 0; i < (length - (num + "").length()); i++) {
                str += "0";
            }
        }
        str = str + num;
        return str;
    }

    /**
     * 使HTML的标签失去作用*
     *
     * @param input 被操作的字符串
     * @return String
     */
    public static final String escapeHTMLTagOld(String input) {
        if (input == null)
            return "";
        input = input.trim().replaceAll("&", "&amp;");
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");
        input = input.replaceAll("\r\n", "<br>");
        input = input.replaceAll("'", "&#39;");
        input = input.replaceAll("\"", "&quot;");
        input = input.replaceAll("\\\\", "&#92;");
        return input;

    }

    /**
     * 对表单提交的数据进行处理，有专门针对textarea的数据进行处理的逻辑,对空格进行保留. 如果不是textarea提交的数据(根据是否有"<br>
     * "和"&nbsp;"进行判断)
     *
     * @param input
     */
    public static final String escapeHTMLTag(String input) {
        if (input == null)
            return "";

        if (input.indexOf("<br>") == -1 && input.indexOf("&nbsp;") == -1) {
            return escapeHTMLTagOld(input);
        }

        int len = input.length();
        StringBuilder strBuilder = new StringBuilder();
        int pos = 0;
        while (pos < len) {
            char c = input.charAt(pos);

            switch (c) {
                case '<': {
                    if ((pos + 4) < len
                            && "<br>".equals(input.substring(pos, pos + 4))) {
                        strBuilder.append("<br>");
                        pos += 4;

                    } else {
                        strBuilder.append("&lt;");
                        pos++;
                    }
                    break;
                }
                case '>':
                    strBuilder.append("&gt;");
                    pos++;
                    break;
                case '&': {
                    if ((pos + 6) < len
                            && "&nbsp;".equals(input.substring(pos, pos + 6))) {
                        strBuilder.append("&nbsp;");
                        pos += 6;

                    } else {
                        strBuilder.append("&amp;");
                        pos++;
                    }
                    break;

                }
                case '\'':
                    strBuilder.append("&#39;");
                    pos++;
                    break;
                case '\"':
                    strBuilder.append("&quot;");
                    pos++;
                    break;
                case '\\':
                    strBuilder.append("&#92;");
                    pos++;
                    break;
                default:
                    strBuilder.append(c);
                    pos++;
                    break;
            }

        }

        return strBuilder.toString();

    }

    /**
     * 还原html标签
     *
     * @param input
     * @return
     */
    public static final String unEscapeHTMLTag(String input) {
        if (input == null)
            return "";
        input = input.trim().replaceAll("&amp;", "&");
        input = input.replaceAll("&lt;", "<");
        input = input.replaceAll("&gt;", ">");
        input = input.replaceAll("<br>", "\n");
        input = input.replaceAll("&#39;", "'");
        input = input.replaceAll("&quot;", "\"");
        input = input.replaceAll("&#92;", "\\\\");
        return input;
    }

    /**
     * 把数组合成字符串
     *
     * @param str
     * @param seperator
     * @return
     */
    public static String toString(String[] str, String seperator) {
        if (str == null || str.length == 0)
            return "";
        StringBuffer buf = new StringBuffer();
        for (int i = 0, n = str.length; i < n; i++) {
            if (i != 0)
                buf.append(seperator);
            buf.append(str[i]);
        }
        return buf.toString();
    }

    /**
     * 把字符串分隔成数组
     *
     * @param str       字符 如： 1/2/3/4/5
     * @param seperator 分隔符号 如: /
     * @return String[] 字符串树组 如: {1,2,3,4,5}
     */
    public static String[] split(String str, String seperator) {
        StringTokenizer token = new StringTokenizer(str, seperator);
        int count = token.countTokens();
        String[] ret = new String[count];
        for (int i = 0; i < count; i++) {
            ret[i] = token.nextToken();
        }
        return ret;
    }

    /**
     * 按指定的分隔符分隔数据，有N个分隔符则返回一个N+1的数组
     *
     * @param str
     * @param seperator
     * @return
     */
    public static String[] splitHaveEmpty(String str, String seperator) {
        // 分隔符前后增加一个空白字符
        str = str.replaceAll(seperator, " " + seperator + " ");
        return str.split(seperator);
    }

    /**
     * @param len    需要显示的长度(<font color="red">注意：长度是以byte为单位的，一个汉字是2个byte</font>)
     * @param symbol 用于表示省略的信息的字符，如“...”,“>>>”等。
     * @return 返回处理后的字符串
     */
    public static String getSub(String str, int len, String symbol) {
        if (str == null)
            return "";
        try {
            int counterOfDoubleByte = 0;
            byte[] b = str.getBytes("gbk");
            if (b.length <= len)
                return str;
            for (int i = 0; i < len; i++) {
                if (b[i] < 0)
                    counterOfDoubleByte++; // 通过判断字符的类型来进行截取
            }
            if (counterOfDoubleByte % 2 == 0)
                str = new String(b, 0, len, "gbk") + symbol;
            else
                str = new String(b, 0, len - 1, "gbk") + symbol;
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        return str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    /**
     * 按字节获取字符串的长度,一个汉字占二个字节
     *
     * @param str
     * @return
     */
    public static int getLen(String str) {
        try {
            byte[] b = str.getBytes("gbk");
            return b.length;
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    public static String getSub(String str, int len) {
        return getSub(str, len, "");
    }

    /**
     * 只取某一字符串的前几个字符，后面以...表示
     */
    public static String getAbc(String str, int len) {
        return getAbc(str, len, "...");
    }

    /**
     * 截取多少长度前的一断字符串
     */
    public static String getAbc(String str, int len, String symbol) {
        if (str == null)
            return null;
        if (len < 0)
            return "";
        if (str.length() <= len) {
            return str;
        } else {
            return str.substring(0, len).concat(symbol);
        }
    }

    /**
     * 截取某字符串中两个字符串之间的一段字符串 eg:StringUtil.subBetween("yabczyabcz", "y", "z") =
     * "abc"
     */
    public static String subBetween(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }

    /**
     * 截取某字符串中最后出现指定字符串之后的一段字符串 StringUtil.subAfterLast("abcba", "b") = "a"
     */
    public static String subAfterLast(String str, String separator) {
        if (str == null || str.length() == 0) {
            return str;
        }
        if (separator == null || separator.length() == 0) {
            return "";
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1 || pos == (str.length() - separator.length())) {
            return "";
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 截取某字符串中最后出现指定字符串之前的一段字符串 StringUtil.subBeforeLast("abcba", "b") = "abc"
     */
    public static String subBeforeLast(String str, String separator) {
        if (str == null || separator == null || str.length() == 0
                || separator.length() == 0) {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 截取某字符串中指定字符串之后的一段字符串 StringUtil.subAfter("abcba", "b") = "cba"
     */
    public static String subAfter(String str, String separator) {
        if (str == null || str.length() == 0) {
            return str;
        }
        if (separator == null) {
            return "";
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return "";
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 截取某字符串中指定字符串之前的一段字符串 StringUtil.subBefore("abcbd", "b") = "a"
     */
    public static String subBefore(String str, String separator) {
        if (str == null || separator == null || str.length() == 0) {
            return str;
        }
        if (separator.length() == 0) {
            return "";
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 判断两个字符串中是否含有相同的元素
     */
    public static boolean containsNone(String str, String invalidChars) {
        if (str == null || invalidChars == null) {
            return true;
        }
        return containsNone(str, invalidChars.toCharArray());
    }

    /**
     * 判断字符串中是否含有字符数组中的元素
     */
    public static boolean containsNone(String str, char[] invalidChars) {
        if (str == null || invalidChars == null) {
            return true;
        }
        int strSize = str.length();
        int validSize = invalidChars.length;
        for (int i = 0; i < strSize; i++) {
            char ch = str.charAt(i);
            for (int j = 0; j < validSize; j++) {
                if (invalidChars[j] == ch) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断字符串中是否包含指定字符串
     */
    public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        return (str.indexOf(searchStr) >= 0);
    }

    public static boolean isEmail(String email) {
        if (isBlank(email)) {
            return false;
        }
        if (email.length() > 100) {
            return false;
        }
        if (email.indexOf('@') == -1) {
            return false;
        }
        log.info("check email: " + email);

        return EMAIL_PATTERN.matcher(email).matches();
    }


    /**
     * 转换html特殊字符为html码
     *
     * @param str
     * @return
     */
    public static String htmlSpecialChars(String str) {
        try {
            if (str.trim() == null) {
                return "";
            }
            StringBuffer sb = new StringBuffer();
            char ch = ' ';
            for (int i = 0; i < str.length(); i++) {
                ch = str.charAt(i);
                if (ch == '&') {
                    sb.append("&amp;");
                } else if (ch == '<') {
                    sb.append("&lt;");
                } else if (ch == '>') {
                    sb.append("&gt;");
                } else if (ch == '"') {
                    sb.append("&quot;");
                } else if (ch == '\'') {
                    sb.append("&#039;");
                } else if (ch == '(') {
                    sb.append("&#040;");
                } else if (ch == ')') {
                    sb.append("&#041;");
                } else if (ch == '@') {
                    sb.append("&#064;");
                } else {
                    sb.append(ch);
                }
            }
            if (sb.toString().replaceAll("&nbsp;", "").replaceAll("　", "")
                    .trim().length() == 0) {
                return "";
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 生成小标题的正则表达式替换,mark是大标题的镭点标识 小标题的锚点标识按mark加出现的序号的方式生成，如第一个出现的小标题为：
     * mark1,第个出现的为mark2，如此类推。 小标题示例:
     * <ol>
     * <li><span class="menuId">3.1</span><a href="#">历史著名运动员</a></li>
     * <li><span class="menuId">3.2</span><a href="#">2004年奥运会著名运动员</a></li>
     * <li><span class="menuId">3.3</span><a href="#">其他运动员</a></li>
     * <li><span class="menuId">3.4</span><a href="#">其他著名人物</a></li>
     * </ol>
     * 返回一个字符串数组，序号1为解析后的数据，序号2为小题标数据
     *
     * @param input       需要解析的原始数据
     * @param mark        大标题的锚点标识
     * @param bigProIndex 大标题的索引序号
     * @return
     */
    public static String[] findSpecData(String input, String mark,
                                        String bigProIndex) {
        // 用来存放处理解析后的文本数据
        StringBuffer sb = new StringBuffer();
        // 用来生成小项的锚点
        StringBuffer smallPro = new StringBuffer("<ol>").append("\n");
        // 用来存放小项的标号
        int index = 1;

        String regex = "(<div class=s_title>)(.*?)(</div>)";
        Matcher testM = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
                .matcher(input);
        while (testM.find()) {
            testM.appendReplacement(sb, "<div class=\"s_title\"><a name=\""
                    + mark + index + "\"></a>$2$3");
            // 小标题名称
            String smallName = testM.group(2);
            smallPro.append("<li><span class=\"menuId\" >").append(bigProIndex)
                    .append(".").append(index).append("</span><a href=\"#")
                    .append(mark).append(index).append("\">").append(smallName)
                    .append("</a></li>").append("\n");
            // 索引号自加
            index++;
        }
        // 如果存在小标题，
        if (index != 1) {
            // 组装小标题
            smallPro.append("</ol>");
            // 生成带小标题锚点的数据
            testM.appendTail(sb);
            return new String[]{sb.toString(), smallPro.toString()};
        }
        return null;
    }

    /**
     * 随机生成几个不同的数
     *
     * @param lenth
     * @param num
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static int[] random5(int lenth, int num) {

        Random rd = new Random();

        HashSet set = new HashSet();
        while (true) {
            int i = rd.nextInt(lenth);
            set.add(new Integer(i));
            if (set.size() == num) {
                break;
            }
        }
        Iterator iter = set.iterator();
        int jj[] = new int[num];
        int i = 0;
        while (iter.hasNext()) {

            jj[i] = ((Integer) iter.next()).intValue();
            ++i;
        }
        return jj;
    }

    /**
     * 随机生成固定长度的字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 去掉超链接和
     * <p/>
     * </P>
     * ,文章摘要使用
     *
     * @param str
     * @return
     */
    public static String filtHref(String str) {
        if (str == null)
            return "";
        String regex = "<[a|A] href=\".*?>(.*?)</[a|A]>";
        Pattern pattern = null;
        pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String ss = matcher.group(1);
            str = str.replaceAll("<[a|A] href=\".*?>" + ss + "</[a|A]>", ss);
        }

        regex = "<[p|P] [^>]*?>(.*?)</[p|P]>";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(str);
        while (matcher.find()) {
            String ss = matcher.group(1);
            str = str.replaceAll("<[p|P] [^>]*?>" + ss + "</[p|P]>", ss);
        }
        return str;
    }

    /**
     * 给超链接加上:target="_blank"
     *
     * @param str
     * @return
     */
    public static String addHrefBlank(String str) {
        if (str == null)
            return "";
        String regex = "<[a|A] href=\"([^>]*?)>.*?</[a|A]>";
        Pattern pattern = null;
        pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String ss = matcher.group(1);
            if (ss.indexOf("_blank") == -1) {
                str = str.replaceAll(ss, ss + "  target=\"_blank\"");
            }
        }
        return str;
    }

    // 获取26个字母
    public static char[] getEnChar() {
        char[] cs = new char[26];
        char c = 'A' - 1;
        for (int i = 0; c++ < 'Z'; i++) {
            cs[i] = c;
        }
        return cs;
    }

    // 判断是否在26个字母里面
    public static boolean isInChar(String c) {
        boolean in = false;
        char[] ch = getEnChar();
        for (int i = 0; i < ch.length; i++) {
            if (c.equals(ch[i] + "")) {
                in = true;
                break;
            }
        }
        return in;
    }

    /**
     * 根据大图获得小图地址
     *
     * @param imgurl
     * @return
     */
    public static String getSmallImg(String imgurl) {
        int len = imgurl.lastIndexOf("/");
        if (len > 1)
            return imgurl.substring(0, len + 1) + "t_"
                    + imgurl.substring(len + 1, imgurl.length());
        else
            return imgurl;
    }

    /**
     * 把字符串切成每个字符
     *
     * @param str
     * @return
     */
    public static char[] toArray(String str) {
        return str.toCharArray();
    }

    /**
     * b代替a
     *
     * @param str
     * @param a
     * @param b
     * @return
     */
    public static String replaceStr(String str, String a, String b) {
        if (isBlank(str)) {
            return "";
        }
        return str.replaceAll(a, b);
    }

    /**
     * 获取要过滤的URL
     */
    public static boolean getURLFromFile(String str, String filename) {
        String text;
        String out = "";
        File file = new File(filename); // 读文件
        if (file.exists()) {
            try {
                long filesize = file.length();
                if (filesize == 0)
                    return true;
                BufferedReader input = new BufferedReader(new FileReader(file));
                StringBuffer buffer = new StringBuffer();

                while ((text = input.readLine()) != null) {
                    buffer.append(text);
                }
                out = buffer.toString();
                return filterURL(out, str);
            } catch (FileNotFoundException e) {
                log.error(e.getMessage(), e);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        } else {
            log.error("File is not exist");
            return true;
        }
        return false;
    }

    /**
     * Description: 基本功能：过滤URL
     *
     * @param str
     * @return
     */
    public static boolean filterURL(String str, String regexpURL) {
        Pattern pattern = Pattern.compile(regexpURL);
        Matcher matcher = pattern.matcher(str);
        boolean result = matcher.find();
        return result;
    }

    /**
     * 判断字符串是否正常(不为null，不为空)
     *
     * @param str
     * @return
     */
    public static boolean isFine(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        return true;
    }

    // /**
    // * 去掉html代码
    // * @param html
    // * @return
    // */
    // public static String trimHtml(String html){
    // Parser parser = Parser.createParser(html,"GBK");
    // if(parser!=null){
    // StringBean sb = new StringBean();
    // try {
    // parser.visitAllNodesWith(sb);
    // html = sb.getStrings();
    // } catch (Exception e) {
    // top.winkin.log.error(e, e);
    // }
    // }
    // return html;
    // }

    /**
     * 判断字符串是否一个数字, 正整数
     *
     * @param str
     * @return
     */
    public static boolean isNum(String str) {
        if (str == null)
            return false;
        return str.matches("\\d+");
    }

    /**
     * 判断字符串是否一个数字, 正负都行
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (str == null)
            return false;
        return str.matches("(-)\\d+|\\d+");
    }

    /**
     * 获得字符串去掉空格、回车、tab之后的长度
     *
     * @param str
     * @return
     */
    public static int getLenWithoutBlank(String str) {
        return str.replace("\n", "").replace("\t", "").replace(" ", "")
                .replace("　", "").length();
    }

    public static String parserToWord(String str) {
        String result = "";
        if (str != null) {
            result = str.replaceAll("&lt;br&gt;", "&#10;");
            result = result.replaceAll("<br>", "&#10;");
            result = result.replaceAll(" ", "&ensp;");
            result = result.replaceAll("\r", "");
            result = result.replaceAll("\n", "");
        }
        return result;
    }

    public static String parserToHTMLForTextArea(String str) {
        String result = escapeHTMLTag(str);
        result = result.replaceAll(" ", "&ensp;");
        return result;
    }

    /**
     * 把double由科学计数法转为正常计数法
     *
     * @param
     * @return
     */
    public static String doubleToString(double dnum) {
        NumberFormat numformat = NumberFormat.getNumberInstance();
        numformat.setGroupingUsed(false);
        numformat.setMinimumFractionDigits(2);
        numformat.setMaximumFractionDigits(2);
        String valueN = numformat.format(dnum);
        return valueN;
    }

    /**
     * 获取当前年月份及时间 格式为:yyyyMMddhhmmss
     */
    public static String getcurrentdate() {
        String current = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        current = df.format(new Date());
        return current;
    }


    /**
     * 纯数字
     * 无小数点
     * 不能超过八位数
     */
    public static boolean rechargeString(String str) {
        Pattern pattern = Pattern.compile("(^([1-9][0-9]{0,7})$)");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    public static boolean isNumOfStockCount(String str) {
        return rechargeString(str);
    }

    /**
     * 纯数字(提现Withdraw)
     * 可以有小数点
     * 整数部分不能超过八位数
     * 小数部分不能超过两位
     * 整数的第一个数字和小数的最后一个数字不能是0
     * (^((([1-9]{1}[0-9]{0,7})|[0-9]{1})(|(\.{1}[0-9]{0,1}[1-9]{1})))$)
     * <p/>
     * 纯数字，小数点前后没有位数限制，小数点最后一位不允许是0
     * (^((([1-9]{1}[0-9]+)|[0]{1})(|(\.{1}[0-9]+[1-9]{1})))$)
     */
    public static boolean withdrawString(String str) {
        if (isNumber(str)) {
            Float money = Float.parseFloat(str);
            return money >= 1.0;
        }
        return false;
    }


    /**
     * 纯数字
     * 无小数点
     * 必须为六位数
     */
    public static boolean cellcodeString(String str) {
        Pattern pattern = Pattern.compile("(^([0-9]{6})$)");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 正则表达式判断银行卡号格式
     * 必须为u十九位数
     */
    public static boolean bankCardIdString(String str) {
        Pattern pattern = Pattern.compile("(^([0-9]{16,19})$)");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 正则表达式判断手机号
     * 手机号段
     * 13，15，18全通了
     * 必须为十三位数
     */
    public static boolean iscellPhoneString(String str) {
        if (StringUtil.isBlank(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("(^(13[0-9]|14[57]|15[0-9]|18[0-9]|17[0-9])[0-9]{8}$)");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 正则表达式判断用户真实姓名
     * 必须为2~20位数
     */
    public static boolean isRealNameString(String str) {
        Pattern pattern = Pattern.compile("(^([\u4e00-\u9fa5]{2,20})$)");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 正则表达式判断用户真实身份证号
     * 必须为15/18位数
     */
    public static boolean isIdCardString(String str) {
        Pattern pattern = Pattern.compile("(^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$)");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 正则表达式判断用户银行支行输入
     * 必须为4~20位数
     */
    public static boolean isBankBranch(String str) {
        Pattern pattern = Pattern.compile("(^([\u4e00-\u9fa5]{2,40})$)");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 正则表达式判断用户名昵称
     * 必须为1~25位数
     */
    public static boolean isNickString(String str) {
        Pattern pattern = Pattern.compile("(^((\\w|[\\u4e00-\\u9fa5]|\\.|@){1,25})$)");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }


    /**
     * 官网改版
     * 正则表达式判断用户密码
     * 必须为6~16位数
     * 可见字符
     */
    public static boolean isGaunWangPwdString(String str) {
        Pattern pattern = Pattern.compile("(^([\\p{Graph}]{6,16})$)");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }


    /**
     * 正则表达式判断用户密码
     * 必须为6~50位数
     * 0-9，字母大小写，下划线
     */
    public static boolean isPwdString(String str) {
        Pattern pattern = Pattern.compile("(^([\\w]{6,50})$)");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 正则表达式判断用户交易密码
     * 必须为6位数
     * 0-9，字母大小写，下划线
     */
    public static boolean isTradePwd(String str) {
        Pattern pattern = Pattern.compile("(^([\\d]{6})$)");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 正则表达式判断日期
     * String格式
     * 判断范围1700-01-01 ---- 2099-12-31
     * 月份和日期的0可舍去
     * fixme 用simpledateformat 不是更好吗？
     */
    public static boolean isDateString(String str) {
        if (!isFine(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("(^([1][7-9][0-9][0-9]|[2][0][0-9][0-9])(-)([1-9]|[0][0-9]|[1][0-2])(-)([0-9]|[0-2][0-9]|[3][0-1])$)");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    //		@vip.163.com;http://vip.163.com/
//		@vip.126.com;http://vip.126.com/
//		网易所有邮箱可合并为http://vipmail.163.com/
//		@vip.sina.com;http://vip.sina.com.cn/
//		@vip.qq.com;http://mail.qq.com
    public static String[] emailSubstring(String str) {
        String emailSubString = null;
        Pattern pattern = Pattern.compile("(@(\\w+)\\.)");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            emailSubString = matcher.group(2);
        }
        String email[] = new String[2];
        if (emailSubString.equals("gmail")) {
            email[0] = "top.winkin.http://gmail.com";
            email[1] = "GMail";
            return email;
        } else if (emailSubString.equals("qq") || emailSubString.equals("foxmail")) {
            email[0] = "top.winkin.http://mail.qq.com";
            email[1] = "QQ";
            return email;
        } else if (emailSubString.equals("sina")) {
            email[0] = "top.winkin.http://mail.sina.com";
            email[1] = "新浪";
            return email;
        } else if (emailSubString.equals("aliyun")) {
            email[0] = "top.winkin.http://mail.aliyun.com";
            email[1] = "阿里云";
            return email;
        } else if (emailSubString.equals("163")) {
            email[0] = "top.winkin.http://mail.163.com";
            email[1] = "网易163";
            return email;
        } else if (emailSubString.equals("126")) {
            email[0] = "top.winkin.http://mail.126.com";
            email[1] = "网易126";
            return email;
        } else if (emailSubString.equals("yeah")) {
            email[0] = "top.winkin.http://www.yeah.net";
            email[1] = "网易yeah";
            return email;
        } else if (emailSubString.equals("188")) {
            email[0] = "top.winkin.http://www.188.com";
            email[1] = "网易财富邮";
            return email;
        } else if (emailSubString.equals("yahoo")) {
            email[0] = "top.winkin.http://mail.yahoo.com";
            email[1] = "雅虎";
            return email;
        } else if (emailSubString.equals("outlook") || emailSubString.equals("hotmail")) {
            email[0] = "top.winkin.http://mail.live.com";
            email[1] = "Microsoft";
            return email;
        } else {
            return null;
        }
    }

    public static String moneyFormat(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return "0.00";
        }
        int intValue = bigDecimal.intValue();
        BigDecimal bigDecimalValue = bigDecimal.subtract(BigDecimal.valueOf(intValue));

        String intString = String.valueOf(intValue);
        String bigDecimalString = bigDecimalValue.toString();
        if (bigDecimalValue.toString().length() > 2) {
            bigDecimalString = bigDecimalValue.toString().substring(2, bigDecimalValue.toString().length());
        }
        String money = numberFormat(intString);

        bigDecimalString = bigDecimalFormat(bigDecimalString);

        if (bigDecimalString.length() == 1) {
            bigDecimalString = bigDecimalString + "0";
        } else if (bigDecimalString.length() == 0) {
            bigDecimalString = "00";
        }

        return money + "." + bigDecimalString;
    }

    public static String moneyFormat(BigDecimal bigDecimal, int num) {
        if (bigDecimal == null) {
            return "0.00";
        }
        int intValue = bigDecimal.intValue();
        BigDecimal bigDecimalValue = bigDecimal.subtract(BigDecimal.valueOf(intValue));

        String intString = String.valueOf(intValue);
        String money = numberFormat(intString);

        String bigDecimalString = bigDecimalValue.toString();
        if (bigDecimalValue.toString().length() > 2) {
            bigDecimalString = bigDecimalValue.toString().substring(2, bigDecimalValue.toString().length());
        }

        bigDecimalString = bigDecimalFormat(bigDecimalString, num);

        return money + "." + bigDecimalString;
    }

    public static String numberFormat(String intString) {
        if (!isNum(intString)) {
            return "0";
        }

        int len = intString.length() % 3;
        int forInt = intString.length() / 3;
        String tj = "，";
        String integerValue = "";
        for (int i = 0; i <= forInt; i++) {
            if (i == 0 && len != 0 && forInt != 0) {
                integerValue += intString.substring(0, len) + tj;
            } else if (i != 0 && i != forInt) {
                integerValue += intString.substring((i - 1) * 3 + len, i * 3 + len) + tj;
            } else if (i == 0 && forInt == 0) {
                integerValue += intString.substring(0, len);
            } else if (i == forInt) {
                integerValue += intString.substring((i - 1) * 3 + len, i * 3 + len);
            }
        }
        return integerValue;
    }

    public static String bigDecimalFormat(String bigDecimalString) {
        if (!isNum(bigDecimalString)) {
            return "";
        }

        if (bigDecimalString.length() > 6) {
            bigDecimalString = bigDecimalString.substring(0, 6);
        }

        char chs[] = bigDecimalString.toCharArray();
        int length = bigDecimalString.length();
        for (int i = bigDecimalString.length(); i > 0; i--) {
            String str = String.valueOf(chs[i - 1]);
            if (str.equals("0")) {
                length--;
                continue;
            } else {
                break;
            }
        }
        bigDecimalString = bigDecimalString.substring(0, length);

        return bigDecimalString;
    }

    public static String bigDecimalFormat(String bigDecimalString, int num) {
        if (!isNum(bigDecimalString)) {
            return "";
        }

        char chs[] = bigDecimalString.toCharArray();
        int length = bigDecimalString.length();
        for (int i = bigDecimalString.length(); i > 0; i--) {
            String str = String.valueOf(chs[i - 1]);
            if (str.equals("0")) {
                length--;
                continue;
            } else {
                break;
            }
        }
        bigDecimalString = bigDecimalString.substring(0, length);

        if (bigDecimalString.length() >= num) {
            bigDecimalString = bigDecimalString.substring(0, num);
        } else {
            String zero = "";
            for (int j = 0; j < num - bigDecimalString.length(); j++) {
                zero += "0";
            }
            bigDecimalString += zero;
        }

        return bigDecimalString;
    }

    public static void main(String[] args) {
        System.out.println(withdrawString("0.2"));
        System.out.println(withdrawString("0.20"));
        System.out.println(withdrawString(".2"));
        System.out.println(withdrawString(".20"));
        System.out.println(withdrawString("10.2"));
        System.out.println(withdrawString("10.20"));
        System.out.println(withdrawString("10.02"));
        System.out.println(withdrawString("10.0"));
        System.out.println(withdrawString("0.00"));
        System.out.println(withdrawString("1000.2"));
        System.out.println(withdrawString("10000.2"));
        System.out.println(withdrawString("100000.2"));
        System.out.println(withdrawString("1000000.2"));
        System.out.println(withdrawString("a.2"));
        System.out.println(withdrawString("2a"));
        System.out.println(withdrawString("fxxx.2"));
        System.out.println(withdrawString("2"));
        System.out.println(withdrawString("100"));
        System.out.println(withdrawString("abc"));
        System.out.println(withdrawString("672"));


//		System.out.println(isDateString("2014-06-10"));
        // int a=2147483647;
        // boolean b=isInt("-2");
        // System.out.println(b);

        // String intStr = "3.2万元";
        // System.out.println(truncateInt(intStr));
//		double a = 11333.1000;
//		String b = "923655421";
//		System.out.println(doubleToString(a));
//		System.out.println(moneyFormat(BigDecimal.valueOf(a)));
//		System.out.println(numberFormat(b));
//		System.out.println(bigDecimalFormat(b,1));

        String[] emails = {
                "zzzhc.cn+1@gmail.com",
                "Memory308",
                "hacker@hacker.org%' and 3=3 and '%'='",
                "../../../../../../../../boot.ini",
                null,
                "",
                "zzzhc@zzzhc",

                "zzzhc@zzzhc.com",
                "zzzhc.cn@gmail.com",
                "zzzhc-cn@zzzhc.com.cn",
                "hesx_007@163.com",
                "yes.v.no@163.com",
                "1522278231@qq.com"
        };
        for (String email : emails) {
            boolean pass = isEmail(email);
            System.out.println(email + " is email? " + pass);
            if (pass) {
                subEmailFormat(email);
            }
        }

       /* String nick = "zhaozhe@huoqiur.com";
        subEmailFormat(nick);*/


    }

    public static String subEmailFormat(String nick) {
        String emailSubString = "";
        String emailSubString2 = "";
        Pattern pattern = Pattern.compile("((\\w+)(@\\w+.\\w+))");
        Matcher matcher = pattern.matcher(nick);
        while (matcher.find()) {
            emailSubString = matcher.group(2);
            emailSubString2 = matcher.group(3);
        }

        int lengh = emailSubString.length();
        switch (lengh) {
            case 2:
            case 3: {
                emailSubString = emailSubString.substring(0, lengh - 1) + "*";
            }
            break;
            case 4:
            case 5: {
                emailSubString = emailSubString.substring(0, lengh - 2) + "**";
            }
            break;
            case 6:
            case 7: {
                emailSubString = emailSubString.substring(0, lengh - 3) + "***";
            }
            break;
            default: {
                emailSubString = emailSubString.substring(0, 4) + "*****";
            }
            break;
        }


        return emailSubString + emailSubString2;
    }

    public static String splitAndFilterString(String input, int length) {
        if (input == null || input.trim().equals("")) {
            return "";
        }
        // 去掉所有html元素,
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
                "<[^>]*>", "");
        str = str.replaceAll("[(/>)<]", "");
        int len = str.length();
        if (len <= length) {
            return str;
        } else {
            str = str.substring(0, length);
            str += "...";
        }
        return str;
    }

    public static List<String> getStrings(String str) {
        return getStrings(str, ",");
    }

    public static List<Long> getLongs(String str) {

        if (isBlank(str)) {
            return Lists.newArrayList();
        }

        String[] strs = str.split(",");
        List<Long> strList = new ArrayList<Long>();
        for (int i = 0; i < strs.length; i++) {
            String t = strs[i];
            if (isNum(t)) {
                strList.add(Long.valueOf(strs[i]));
            } else {
                continue;
            }
        }
        return strList;
    }

    public static List<Integer> getIntegers(String str) {

        if (isBlank(str)) {
            return Lists.newArrayList();
        }

        String[] strs = str.split(",");
        List<Integer> strList = new ArrayList<Integer>();
        for (int i = 0; i < strs.length; i++) {
            String t = strs[i];
            if (isNumber(t)) {
                strList.add(Integer.valueOf(strs[i]));
            } else {
                continue;
            }
        }
        return strList;
    }

    public static List<BigDecimal> getBigDecimals(String str) {

        if (isBlank(str)) {
            return Lists.newArrayList();
        }

        String[] strs = str.split(",");
        List<BigDecimal> strList = new ArrayList<BigDecimal>();
        for (int i = 0; i < strs.length; i++) {
            String t = strs[i];
            if (withdrawString(t)) {
                strList.add(BigDecimal.valueOf(Double.valueOf(strs[i])));
            } else {
                continue;
            }
        }
        return strList;
    }

    public static List<String> getStrings(String str, String split) {

        if (isBlank(str)) {
            return Lists.newArrayList();
        }

        String[] strs = str.split(split);
        List<String> strList = new ArrayList<String>();
        for (int i = 0; i < strs.length; i++) {
            strList.add(strs[i]);
        }
        return strList;
    }

    /**
     * @param word : 输入的字符串
     * @return 是否输入的是字符
     */
    public boolean CharIsLetter(String word) {
        boolean sign = true; // 初始化标志为为'true'
        for (int i = 0; i < word.length(); i++) { // 遍历输入字符串的每一个字符
            if (!Character.isLetter(word.charAt(i))) { // 判断该字符是否为英文字符
                sign = false; // 若有一位不是英文字符，则将标志位修改为'false'
            }
        }
        return sign;// 返回标志位结果
    }

    /**
     * 字符串截取
     *
     * @param str        要处理的字符串
     * @param beginIndex 开始位置
     * @param endIndex   结束位置
     * @return
     */
    public String substr(String str, int beginIndex, int endIndex) {
        if (isBlank(str)) {
            return "";
        }
        if (endIndex == -1) {
            return str.substring(beginIndex);
        }

        if (endIndex > str.length()) {
            endIndex = str.length();
        }
        return str.substring(beginIndex, endIndex);
    }

    /**
     * 字符串截取
     *
     * @param str        要处理的字符串
     * @param beginIndex 开始位置
     * @param endIndex   结束位置
     * @param endMark    在结束处加...符
     * @return
     */
    public String substr(String str, int beginIndex, int endIndex,
                         String endMark) {
        if (isBlank(str)) {
            return "";
        }
        if (endIndex == -1) {
            return str.substring(beginIndex);
        }

        if (endIndex > str.length()) {
            endIndex = str.length();
        }
        String restr = str.substring(beginIndex, endIndex);
        if (endIndex < str.length()) {
            restr = restr + endMark;
        }
        return restr;
    }

    // 转化成大写
    public String toUpperCase(String str) {
        if (isBlank(str)) {
            return "";
        }
        return str.toUpperCase();
    }

    // 转化成大写
    public String toLowerCase(String str) {
        if (isBlank(str)) {
            return "";
        }
        return str.toLowerCase();
    }

}
