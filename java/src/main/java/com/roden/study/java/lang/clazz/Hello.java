package com.roden.study.java.lang.clazz;
public class Hello
{
	public static void main(String[] args)
	{
		ClassLoader classLoader=Hello.class.getClassLoader();
		while (classLoader!=null){
			System.out.println(classLoader.getClass().getCanonicalName());
			classLoader=classLoader.getParent();
		}
		for (String arg : args)
		{
			System.out.println("运行Hello的参数：" + arg);
		}
	}
}
