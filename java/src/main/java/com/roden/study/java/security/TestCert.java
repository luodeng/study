package com.roden.study.java.security;

import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class TestCert {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("E:\\20058400001498604.p12");
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate c = (X509Certificate) cf.generateCertificate(fis);
            System.out.println("Certficate for" + c.getSubjectDN());
            System.out.println("Generated with " + c.getSigAlgName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}