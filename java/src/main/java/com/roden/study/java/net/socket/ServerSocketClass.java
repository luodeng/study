package com.roden.study.java.net.socket;

import java.net.ServerSocket;

/**
 *  backlog的定义是已连接但未进行accept处理的socket队列大小，如果这个队列满了，将会发送一个ECONNREFUSED错误信息给到客户端，
 *  即 linux 头文件 /usr/include/asm-generic/errno.h中定义的“Connection refused”。
 * @author Roden
 */
public class ServerSocketClass {

    public static void main(String[] args) throws Exception {
        //ServerSocket构造函数的第二个参数就是backlog的大小，如果backlog小于1或者不传会给一个默认值50
        ServerSocket server = new ServerSocket(8888, 5);
        while (true) {
            // server.accept();
        }
    }

}