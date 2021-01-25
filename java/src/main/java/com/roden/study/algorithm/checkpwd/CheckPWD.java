package com.roden.study.algorithm.checkpwd;
public class CheckPWD {
    public static void main(String[] args) {
        String passwd = "myNameJOB123_-+=";
        System.out.println(CheckStrength.checkPasswordStrength(passwd));
        System.out.println(CheckStrength.getPasswordLevel(passwd));
    }
}
