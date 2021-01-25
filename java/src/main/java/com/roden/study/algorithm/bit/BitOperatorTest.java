package com.roden.study.algorithm.bit;

public class BitOperatorTest
{
	public static void main(String[] args)
	{		
		System.out.println(getBinaryString(5));
		System.out.println(getBinaryString(9));
		System.out.println(getBinaryString("9-5(",9-5));
		System.out.println(getBinaryString("5 & 9(",5 & 9));
		System.out.println(getBinaryString("5 | 9(",5 | 9));
		System.out.println(getBinaryString("5 ^ 9(",5 ^ 9)); 
		System.out.println("-----------------------------------------------------------------");
		System.out.println(getBinaryString(-5));
		System.out.println(getBinaryString("~-5(",~-5));
		System.out.println("--------------------------------------------------------------------");
		System.out.println(getBinaryString("5 << 2(",5 << 2));
		System.out.println(getBinaryString("-5 << 2(",-5 << 2));
		System.out.println(getBinaryString("-5 >> 2(",-5 >> 2));
		System.out.println(getBinaryString("-5 >>> 2(",-5 >>> 2)); 
	}
	public static String getBinaryString(Integer num){
		String temp=Integer.toBinaryString(num).toString();
		String str="";
		for(int i=0;i<32-temp.length();i++)
			str="0"+str;
		String s="";
		for(int i=0;i<32-num.toString().length();i++)
			s=" "+s;
		return s+num+":"+str+temp;
	}
	public static String getBinaryString(String s,Integer num){
		String temp=Integer.toBinaryString(num).toString();
		String str="";
		for(int i=0;i<32-temp.length();i++)
			str="0"+str;	
		String ss="";
		for(int i=0;i<31-num.toString().length()-s.length();i++)
			ss=" "+ss;
		return ss+s+num+"):"+str+temp;
	}
}