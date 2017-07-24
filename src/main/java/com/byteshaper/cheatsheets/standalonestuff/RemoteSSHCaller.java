package com.byteshaper.cheatsheets.standalonestuff;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Opens a SSH connection to a remote linux server, runs a shell command, reports every line this 
 * command writes to stdout and stderr, and closes the connection.
 * 
 * Creates two callables and lets them run asynchronously to make sure neither waiting for stdout and stderr 
 * does not block each other. 
 *
 */
public class RemoteSSHCaller {
    
    private static class OutputMonitor implements Callable<String> {
        
        private final BufferedReader outputReader;
        
        private final String rolename;
        
        public OutputMonitor(BufferedReader outputReader, String rolename) {
            this.outputReader = outputReader;
            this.rolename = rolename;
        }

        @Override
        public String call() throws Exception {
            
            StringBuilder remoteProcessOutputBuffer = new StringBuilder("\r\n");
            
            try {
                String line = outputReader.readLine();
                
                if(line == null) {
                    remoteProcessOutputBuffer.append("No " + rolename + "-output from remote process on stream");
                }
                
                while(line != null) {
                    remoteProcessOutputBuffer.append("\r\n").append(line);
                    line = outputReader.readLine();
                }
                
            } catch(Exception e) {
                // log error
            } 
            
            return remoteProcessOutputBuffer.toString();
        }
    }
    
    
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
            
            try (BufferedReader remoteOutReader = new BufferedReader(new InputStreamReader(channel.getInputStream()));
                 BufferedReader remoteErrReader = new BufferedReader(new InputStreamReader(channel.getErrStream()))) {
                channel.setCommand(command);
                channel.connect();
                
                OutputMonitor outMonitor = new OutputMonitor(remoteOutReader, "stdout");
                OutputMonitor errMonitor = new OutputMonitor(remoteErrReader, "stderr");
                
                ExecutorService executorService = Executors.newFixedThreadPool(2);
                String result = executorService
                    .invokeAll(Arrays.asList(outMonitor, errMonitor))
                    .stream()
                    .map(future -> {
                        
                        try {
                            return future.get();
                        } catch(ExecutionException | InterruptedException e) {
                            return "Problems reading result from thread";
                        }
                        
                    })
                    .collect(Collectors.joining("\r\n"));
                System.out.println(result);
            }
        } catch (JSchException | IOException | InterruptedException e) {
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
