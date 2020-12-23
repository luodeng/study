package com.roden.study.java.text;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Formatter;

public class FormatDecimalTest {
    double f = 111231.5585;

	public void m1() {
		BigDecimal bg = new BigDecimal(f);
		double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(f1);
	}

	/**
	 * DecimalFormat转换最简便
	 */
	public void m2() {
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println(df.format(f));
	}

	/**
	 * String.format打印最简便
	 */
	public void m3() {
		System.out.println(String.format("%.2f", f));
	}

	public void m4() {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		System.out.println(nf.format(f));
	}

	public static void main(String[] args) {
		FormatDecimalTest f = new FormatDecimalTest();
		f.m1();
		f.m2();
		f.m3();
		f.m4();
	}
	public static String format1(double value) {
		 
		 BigDecimal bd = new BigDecimal(value);
		 bd = bd.setScale(2, RoundingMode.HALF_UP);
		 return bd.toString();
		}
	public static String format2(double value) {
		 
		 DecimalFormat df = new DecimalFormat("0.00");
		 df.setRoundingMode(RoundingMode.HALF_UP);
		 return df.format(value);
		}
	public static String format3(double value) {
		 
		 NumberFormat nf = NumberFormat.getNumberInstance();
		 nf.setMaximumFractionDigits(2);
		 /*
		  * setMinimumFractionDigits设置成2
		  * 
		  * 如果不这么做，那么当value的值是100.00的时候返回100
		  * 
		  * 而不是100.00
		  */
		 nf.setMinimumFractionDigits(2);
		 nf.setRoundingMode(RoundingMode.HALF_UP);
		 /*
		  * 如果想输出的格式用逗号隔开，可以设置成true
		  */
		 nf.setGroupingUsed(false);
		 return nf.format(value);
		}
	public static String format4(double value) {
		 /*
		  * %.2f % 表示 小数点前任意位数 2 表示两位小数 格式后的结果为 f 表示浮点型
		  */
		 return new Formatter().format("%.2f", value).toString();
		}
	public static String format5(double value) {
		 
		 return String.format("%.2f", value).toString();
	}

	/*
	 * 对浮点数进行格式化　　　　　　　　　　　　　　　　　　　　　　　　
		占位符格式为： %[index$][标识]*[最小宽度][.精度]转换符

		double num = 123.4567899;
		System.out.print(String.format("%f %n", num)); // 123.456790
		System.out.print(String.format("%a %n", num)); // 0x1.edd3c0bb46929p6
		System.out.print(String.format("%g %n", num)); // 123.457
		可用标识：
			  -，在最小宽度内左对齐,不可以与0标识一起使用。
			  0，若内容长度不足最小宽度，则在左边用0来填充。
			  #，对8进制和16进制，8进制前添加一个0,16进制前添加0x。
			  +，结果总包含一个+或-号。
			  空格，正数前加空格，负数前加-号。
			  ,，只用与十进制，每3位数字间用,分隔。
			  (，若结果为负数，则用括号括住，且不显示符号。
		可用转换符：
			  b，布尔类型，只要实参为非false的布尔类型，均格式化为字符串true，否则为字符串false。
			  n，平台独立的换行符, 也可通过System.getProperty("line.separator")获取。
			  f，浮点数型（十进制）。显示9位有效数字，且会进行四舍五入。如99.99。
			  a，浮点数型（十六进制）。
			  e，指数类型。如9.38e+5。
			  g，浮点数型（比%f，%a长度短些，显示6位有效数字，且会进行四舍五入）
	 */

	@Test
	public void test7(){
		double amount=1234556789.123457d;
		System.out.println(amount);
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置
		//decimalFormat.setMaximumFractionDigits(2);
		System.out.println(decimalFormat.format(amount));
		System.out.println(new BigDecimal(amount));

		BigDecimal aDouble =new BigDecimal(1.22);

		System.out.println("construct with a double value: " + aDouble);
		//construct with a double value: 1.2199999999999999733546474089962430298328399658203125
		System.out.println("construct with a double value: " + aDouble.toPlainString());
		System.out.println("construct with a double value: " + aDouble.doubleValue());

		DecimalFormat df = new DecimalFormat("###############0.00000000#");// 最多保留几位小数，就用几个#，最少位就用0来确定
		System.out.println(df.format(amount));

		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		System.out.println("d:="+nf.format(amount));

		double tamount=10000000.12d;
		System.out.println(tamount);
	}
}