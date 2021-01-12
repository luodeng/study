package com.roden.study.java.jvm;

/**
 *
 * public class com.roden.study.java.jvm.AppMain {
 *   public com.roden.study.java.jvm.AppMain();
 *     Code:
 *        0: aload_0
 *        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *        4: return
 *
 *   public int add();
 *     Code:
 *        0: iconst_1    将int类型常量1压入栈    ->  1
 *        1: istore_1    将int类型值存入局部变量1  >a=1
 *        2: iconst_2
 *        3: istore_2
 *        4: iload_1      从局部变量1装载int类型值 ->压入操作树栈
 *        5: iload_2
 *        6: iadd         执行int类型加法
 *        7: bipush        100           将一个8位带符号整数压入栈   (7与8指令合并了 8对应100)
 *        9: imul          执行int类型乘法
 *       10: istore_3
 *       11: iload_3
 *       12: ireturn
 *
 *   public static void main(java.lang.String[]);
 *     Code:
 *        0: new           #2                  // class com/roden/study/java/jvm/AppMain
 *        3: dup
 *        4: invokespecial #3                  // Method "<init>":()V
 *        7: astore_1
 *        8: aload_1
 *        9: invokevirtual #4                  // Method add:()I
 *       12: istore_2
 *       13: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
 *       16: iload_2
 *       17: invokevirtual #6                  // Method java/io/PrintStream.println:(I)V
 *       20: return
 * }
 *
 * Process finished with exit code 0
 */
public class AppMain {
    /**
     * Java VisualVM
     * 堆:
     *                 例600M内存
     *  新生代 1/3         200M
     *      Eden  80%       160M     满后 minor gc 未回收对象年龄加1移到from
     *      from  10%       20M
     *      to    10%       20M
     *  老年代 2/3         400M
     *
     * 方法区(元空间):   类信息     *
     * 本地方法栈: native 修改方法
     * 程序计数器：指向当前线程所执行代码的字节码指令(地址)行号
     * 虚拟机栈
     *  栈帧
     *      局部变量表
     *              类型 int short byte reference long ……
     *      操作数栈
     *      方法出口
     *   ……
     *   栈帧
     *
     *
     */
    //类的属性:常量、变量、成员属性
    private Object object=new Object();
    private static int i=0;
    private static String s="Hello World";
    public int add(){
        int a=1;
        int b=2;
        int c=(a+b)*100;
        return c;
    }

    public static void main(String[] args) {
        AppMain appMain=new AppMain();
        int result=appMain.add();
        System.out.println(result);

    }
}
