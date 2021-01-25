package com.roden.study.examples.com.jsch;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class JschTest {
    public static final int CONNECT_TIMEOUT=5000;
    @Test
    public void  testConnect() throws JSchException {
        Remote remote = new Remote();
        remote.setHost("192.168.1.91");
        remote.setPassword("Hl123456");
        Session session = getSession(remote);
        session.connect(CONNECT_TIMEOUT);
        if (session.isConnected()) {
            log.info("Host({}) connected.",remote.getHost());
        }
        session.disconnect();
    }

    @Test
    public void  testCertConnect() throws JSchException {
        Remote remote = new Remote();
        remote.setHost("106.55.51.151");
        remote.setPort(18122);
        remote.setIdentity("F:\\花宜美\\sshKey\\huaemei_ssh_login");
        //remote.setPassword("Hl123456");
        Session session = getSession(remote);
        session.connect(CONNECT_TIMEOUT);
        if (session.isConnected()) {
            log.info("Host({}) connected.",remote.getHost());
        }
        session.disconnect();
    }


    @Test
    public void  testCommand() throws JSchException {
        Remote remote = new Remote();
        remote.setHost("192.168.1.91");
        remote.setPassword("Hl123456");
        Session session = getSession(remote);
        session.connect(CONNECT_TIMEOUT);
        if (session.isConnected()) {
            log.info("Host({}) connected.", remote.getHost());
        }

        remoteExecute(session, "pwd");
        remoteExecute(session, "mkdir /root/jsch-demo");
        remoteExecute(session, "ls /root/jsch-demo");
        remoteExecute(session, "touch /root/jsch-demo/test1; touch /root/jsch-demo/test2");
        remoteExecute(session, "echo 'It a test file.' > /root/jsch-demo/test-file");
        remoteExecute(session, "ls -all /root/jsch-demo");
        remoteExecute(session, "ls -all /root/jsch-demo | grep test");
        remoteExecute(session, "cat /root/jsch-demo/test-file");

        session.disconnect();
    }

    @Test
    public void  testScp() throws JSchException {
        Remote remote = new Remote();
        remote.setHost("192.168.1.91");
        remote.setPassword("Hl123456");
        Session session = getSession(remote);
        session.connect(CONNECT_TIMEOUT);
        if (session.isConnected()) {
            log.debug("Host({}) connected.", remote.getHost());
        }

        remoteExecute(session, "ls /root/jsch-demo/");
        scpTo("test.txt", session, "/root/jsch-demo/");
        remoteExecute(session, "ls /root/jsch-demo/");
        remoteExecute(session, "echo ' append text.' >> /root/jsch-demo/test.txt");
        scpFrom(session, "/root/jsch-demo/test.txt", "file-from-remote.txt");

        session.disconnect();
    }

    @Test
    public void  testEdit() throws JSchException {
        Remote remote = new Remote();
        remote.setHost("192.168.1.91");
        remote.setPassword("Hl123456");
        Session session = getSession(remote);
        session.connect(CONNECT_TIMEOUT);
        if (session.isConnected()) {
            log.debug("Host({}) connected.", remote.getHost());
        }

        remoteExecute(session, "echo 'It a test file.' > /root/jsch-demo/test");
        remoteExecute(session, "cat /root/jsch-demo/test");
        remoteEdit(session, "/root/jsch-demo/test", (inputLines) -> {
            List<String> outputLines = new ArrayList<>();
            for (String inputLine : inputLines) {
                outputLines.add(inputLine.toUpperCase());
            }
            return outputLines;
        });
        remoteExecute(session, "cat /root/jsch-demo/test");

        session.disconnect();
    }

    public static Session getSession(Remote remote) throws JSchException {
        JSch jSch = new JSch();
        if (Files.exists(Paths.get(remote.getIdentity()))) {
            jSch.addIdentity(remote.getIdentity(), remote.getPassphrase());
        }
        Session session = jSch.getSession(remote.getUser(), remote.getHost(),remote.getPort());
        session.setPassword(remote.getPassword());
        session.setConfig("StrictHostKeyChecking", "no");
        return session;
    }

    public static List<String> remoteExecute(Session session, String command) throws JSchException {
        log.debug(">> {}", command);
        List<String> resultLines = new ArrayList<>();
        ChannelExec channel = null;
        try{
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            InputStream input = channel.getInputStream();
            channel.connect(CONNECT_TIMEOUT);
            try {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(input));
                String inputLine = null;
                while((inputLine = inputReader.readLine()) != null) {
                    log.debug("   {}", inputLine);
                    resultLines.add(inputLine);
                }
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (Exception e) {
                        log.error("JSch inputStream close error:", e);
                    }
                }
            }
        } catch (IOException e) {
            log.error("IOcxecption:", e);
        } finally {
            if (channel != null) {
                try {
                    channel.disconnect();
                } catch (Exception e) {
                    log.error("JSch channel disconnect error:", e);
                }
            }
        }
        return resultLines;
    }

    public static long scpTo(String source, Session session, String destination) {
        FileInputStream fileInputStream = null;
        try {
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();
            boolean ptimestamp = false;
            String command = "scp";
            if (ptimestamp) {
                command += " -p";
            }
            command += " -t " + destination;
            channel.setCommand(command);
            channel.connect(CONNECT_TIMEOUT);
            if (checkAck(in) != 0) {
                return -1;
            }
            File _lfile = new File(source);
            if (ptimestamp) {
                command = "T " + (_lfile.lastModified() / 1000) + " 0";
                // The access time should be sent here,
                // but it is not accessible with JavaAPI ;-<
                command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
                out.write(command.getBytes());
                out.flush();
                if (checkAck(in) != 0) {
                    return -1;
                }
            }
            //send "C0644 filesize filename", where filename should not include '/'
            long fileSize = _lfile.length();
            command = "C0644 " + fileSize + " ";
            if (source.lastIndexOf('/') > 0) {
                command += source.substring(source.lastIndexOf('/') + 1);
            } else {
                command += source;
            }
            command += "\n";
            out.write(command.getBytes());
            out.flush();
            if (checkAck(in) != 0) {
                return -1;
            }
            //send content of file
            fileInputStream = new FileInputStream(source);
            byte[] buf = new byte[1024];
            long sum = 0;
            while (true) {
                int len = fileInputStream.read(buf, 0, buf.length);
                if (len <= 0) {
                    break;
                }
                out.write(buf, 0, len);
                sum += len;
            }
            //send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            if (checkAck(in) != 0) {
                return -1;
            }
            return sum;
        } catch(JSchException e) {
            log.error("scp to catched jsch exception, ", e);
        } catch(IOException e) {
            log.error("scp to catched io exception, ", e);
        } catch(Exception e) {
            log.error("scp to error, ", e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    log.error("File input stream close error, ", e);
                }
            }
        }
        return -1;
    }
    public static long scpFrom(Session session, String source, String destination) {
        FileOutputStream fileOutputStream = null;
        try {
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand("scp -f " + source);
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] buf = new byte[1024];
            //send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            while(true) {
                if (checkAck(in) != 'C') {
                    break;
                }
            }
            //read '644 '
            in.read(buf, 0, 4);
            long fileSize = 0;
            while (true) {
                if (in.read(buf, 0, 1) < 0) {
                    break;
                }
                if (buf[0] == ' ') {
                    break;
                }
                fileSize = fileSize * 10L + (long)(buf[0] - '0');
            }
            String file = null;
            for (int i = 0; ; i++) {
                in.read(buf, i, 1);
                if (buf[i] == (byte) 0x0a) {
                    file = new String(buf, 0, i);
                    break;
                }
            }
            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            // read a content of lfile
            if (Files.isDirectory(Paths.get(destination))) {
                fileOutputStream = new FileOutputStream(destination + File.separator +file);
            } else {
                fileOutputStream = new FileOutputStream(destination);
            }
            long sum = 0;
            while (true) {
                int len = in.read(buf, 0 , buf.length);
                if (len <= 0) {
                    break;
                }
                sum += len;
                if (len >= fileSize) {
                    fileOutputStream.write(buf, 0, (int)fileSize);
                    break;
                }
                fileOutputStream.write(buf, 0, len);
                fileSize -= len;
            }
            return sum;
        } catch(JSchException e) {
            log.error("scp to catched jsch exception, ", e);
        } catch(IOException e) {
            log.error("scp to catched io exception, ", e);
        } catch(Exception e) {
            log.error("scp to error, ", e);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    log.error("File output stream close error, ", e);
                }
            }
        }
        return -1;
    }
    private static int checkAck(InputStream in) throws IOException {
        int b=in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if(b==0) return b;
        if(b==-1) return b;
        if(b==1 || b==2){
            StringBuffer sb=new StringBuffer();
            int c;
            do {
                c=in.read();
                sb.append((char)c);
            }
            while(c!='\n');
            if(b==1){ // error
                log.debug(sb.toString());
            }
            if(b==2){ // fatal error
                log.debug(sb.toString());
            }
        }
        return b;
    }
    private static boolean remoteEdit(Session session, String source, Function<List<String>, List<String>> process) {
        InputStream in = null;
        OutputStream out = null;
        try {
            String fileName = source;
            int index = source.lastIndexOf('/');
            if (index >= 0) {
                fileName = source.substring(index + 1);
            }
            //backup source
            remoteExecute(session, String.format("cp %s %s", source, source + ".bak." +System.currentTimeMillis()));
            //scp from remote
            String tmpSource = System.getProperty("java.io.tmpdir") + session.getHost() +"-" + fileName;
            scpFrom(session, source, tmpSource);
            in = new FileInputStream(tmpSource);
            //edit file according function process
            String tmpDestination = tmpSource + ".des";
            out = new FileOutputStream(tmpDestination);
            List<String> inputLines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                inputLines.add(inputLine);
            }
            List<String> outputLines = process.apply(inputLines);
            for (String outputLine : outputLines) {
                out.write((outputLine + "\n").getBytes());
                out.flush();
            }
            //scp to remote
            scpTo(tmpDestination, session, source);
            return true;
        } catch (Exception e) {
            log.error("remote edit error, ", e);
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    log.error("input stream close error", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    log.error("output stream close error", e);
                }
            }
        }
    }


}
