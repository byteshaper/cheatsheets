package com.byteshaper.cheatsheets.standalonestuff;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Opens a SSH connection to a remote linux server, runs a shell command, reports every line this 
 * command writes to stdout, and closes the connection. 
 *
 */
public class RemoteSSHCaller {
    
    public void triggerJob() {
        
        String command = "ls -alh";
        JSch jsch = new JSch();
        Session session = null;
        ChannelExec channel = null;
        
        try {
            session = jsch.getSession("myusername", "example.com");
            session.setUserInfo(new CustomUserInfo());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            channel = (ChannelExec) session.openChannel("exec");
            
            try (BufferedReader remoteOutReader = new BufferedReader(new InputStreamReader(channel.getInputStream()))) {
                channel.setCommand(command);
                channel.connect();
                
                String line = remoteOutReader.readLine();
                
                if(line == null) {
                    System.out.println("Nothing to output");
                }
                
                while(line != null) {
                    System.out.println("Remote output: " + line);
                }
            }
        } catch (JSchException | IOException e) {
            // handle exception
        } finally {
            
            if(channel != null) {
                try {
                    channel.disconnect();
                } catch(Exception e){
                 // report exception or swallow it
                }
            }
            
            if(session != null) {
                try {
                    session.disconnect();
                } catch(Exception e){
                 // report exception or swallow it
                }
            }
        }
    }


    private class CustomUserInfo implements UserInfo {

        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public String getPassword() {
            return "secretpasswordofmyuser";
        }

        @Override
        public boolean promptPassword(String message) {
            return true;
        }

        @Override
        public boolean promptPassphrase(String message) {
            return true;
        }

        @Override
        public boolean promptYesNo(String message) {
            return true;
        }

        @Override
        public void showMessage(String message) {

        }

    }
}
