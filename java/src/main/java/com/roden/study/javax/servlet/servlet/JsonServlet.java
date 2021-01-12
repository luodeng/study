package com.roden.study.javax.servlet.servlet;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/12/23.
 */
public class JsonServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        java.io.BufferedReader bis = new java.io.BufferedReader(
                new java.io.InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = bis.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bis.close();
        }
        System.out.println(new String(java.util.Base64.getDecoder().decode(sb.toString().getBytes("utf-8")),"utf-8"));
        String str= new String(Base64.decodeBase64(sb.toString().getBytes("utf-8")),"utf-8");
        PrintWriter pw=new PrintWriter("D:/1.txt");
        pw.println(str);
        out.println("success");
        out.flush();
        out.close();
    }
}
