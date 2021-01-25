package com.roden.study.algorithm;

import java.util.regex.Pattern;

/**
 * https://baike.baidu.com/item/%E6%89%8B%E6%9C%BA%E5%8F%B7%E7%A0%81
 *
 * https://zhidao.baidu.com/question/490743515.html
 *
 * https://m.weibo.cn/status/4267519109400179?display=0&retcode=6102
 *
 */
public class PhoneCheckUtil {


    /**
     * 中国移动号码正则
     * 139、138、137、136、135、134、147、150、151、152、157、158、159、178、182、183、184、187、188、198、195
     * 虚拟运营商号段: 1703、1705、1706、165
     **/
    private static final String MOBILE_PATTERN = "(^1(3[4-9]|47|5[0-27-9]|65|78|8[2-478]|98)\\d{8}$)|(^170[356]\\d{7}$)";

    /**
     * 中国电信号码正则
     * 133、149、153、173、177、180、181、189、199、191
     * 虚拟运营商号段: 162、1700、1701、1702
     **/
    private static final String TELECOM_PATTERN = "(^1(33|49|53|62|7[37]|8[019]|9[19])\\d{8}$)|(^170[012]\\d{7}$)";

    /**
     * 中国联通号码正则
     * 130、131、132、155、156、185、186、145、175、176、166、140
     * 虚拟运营商号段: 171、1707、1708、1709、167
     **/
    private static final String UNICOM_PATTERN = "(^1(3[0-2]|4[05]|5[56]|6[67]|7[156]|8[56])\\d{8}$)|(^170[7-9]\\d{7}$)";


    private static final String MOBILE = "mobile";

    private static final String TELECOM = "telecom";

    private static final String UNICOM = "unicom";

    public static String checkOperator(String phone) {
        if (Pattern.matches(MOBILE_PATTERN, phone)) {
            return MOBILE;
        } else if (Pattern.matches(TELECOM_PATTERN, phone)) {
            return TELECOM;
        } else if (Pattern.matches(UNICOM_PATTERN, phone)) {
            return UNICOM;
        } else {
            return "error";
        }
    }

    /*********************************************************************************************************************************************/

    /**
     * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700,173,199
     **/
    private static final String CHINA_TELECOM_PATTERN = "(^1(33|53|77|73|99|8[019])\\d{8}$)|(^1700\\d{7}$)";

    /**
     * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1709
     **/
    private static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";

    /**
     * 中国移动号码格式验证
     * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705
     **/
    private static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";


    /**
     * 查询电话属于哪个运营商
     *
     * @param tel 手机号码
     * @return 0：不属于任何一个运营商，1:移动，2：联通，3：电信
     */
    public static Integer isChinaMobilePhoneNum(String tel) {
        boolean b1 = tel == null || tel.trim().equals("") ? false : match(CHINA_MOBILE_PATTERN, tel);
        if (b1) {
            return 1;
        }
        b1 = tel == null || tel.trim().equals("") ? false : match(CHINA_UNICOM_PATTERN, tel);
        if (b1) {
            return 2;
        }
        b1 = tel == null || tel.trim().equals("") ? false : match(CHINA_TELECOM_PATTERN, tel);
        if (b1) {
            return 3;
        }
        return 0;
    }

    /**
     * 匹配函数
     * @param regex
     * @param tel
     * @return
     */
    private static boolean match(String regex, String tel) {
        return Pattern.matches(regex, tel);
    }

    public static void main(String[] args) {
        System.out.println(isChinaMobilePhoneNum("18883315432"));
        System.out.println(checkOperator("19923567809"));
    }


}
