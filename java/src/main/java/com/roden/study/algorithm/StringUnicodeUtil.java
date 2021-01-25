package com.roden.study.algorithm;
 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class StringUnicodeUtil {
	
	/**
	 * 含有unicode 的字符串转一般字符串
	 * @param unicodeStr 混有 Unicode 的字符串
	 * @return
	 */
	public static String unicodeStr2String(String unicodeStr) {
		int length = unicodeStr.length();
		int count = 0;
		//正则匹配条件，可匹配“\\u”1到4位，一般是4位可直接使用 String regex = "\\\\u[a-f0-9A-F]{4}";
		String regex = "\\\\u[a-f0-9A-F]{1,4}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(unicodeStr);
		StringBuffer sb = new StringBuffer();
		
		while(matcher.find()) {
			String oldChar = matcher.group();//原本的Unicode字符
			String newChar = unicode2String(oldChar);//转换为普通字符
			int index = unicodeStr.indexOf(oldChar);
			
			sb.append(unicodeStr.substring(count, index));//添加前面不是unicode的字符
			sb.append(newChar);//添加转换后的字符
			count = index+oldChar.length();//统计下标移动的位置
		}
		sb.append(unicodeStr.substring(count, length));//添加末尾不是Unicode的字符
		return sb.toString();
	}
	
	/**
	 * 字符串转换unicode
	 * @param string
	 * @return
	 */
	public static String string2Unicode(String string) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			// 取出每一个字符
			char c = string.charAt(i);
			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}
		return unicode.toString();
	}
 
	/**
	 * unicode 转字符串
	 * @param unicode 全为 Unicode 的字符串
	 * @return
	 */
	public static String unicode2String(String unicode) {
		StringBuffer string = new StringBuffer();
		String[] hex = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) {
			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);
			// 追加成string
			string.append((char) data);
		}
 
		return string.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(string2Unicode("汇联智投"));
		System.out.println(unicode2String("\\u6C47\\u8054\\u667A\\u6295"));

		String str = "abc";
		String str2 = string2Unicode(str);
		System.out.println(str2);
		System.out.println(unicodeStr2String(str2));

		System.out.println(unicodeStr2String("\\u6C47\\u8054\\u667A\\u6295-8030"));
	}
}