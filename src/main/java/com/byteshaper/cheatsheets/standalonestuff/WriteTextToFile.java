package com.byteshaper.cheatsheets.standalonestuff;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates how to write text to a file
 *
 */
public class WriteTextToFile {
    
    /**
     * CRLF as newline char to stay platform-independent
     */
    private static final String NEWLINE = "\r\n";

    public static void main(String[] args) {
        List<String> lines = Arrays.asList("First line", "Second line", "Third line", "Fourth line with umlauts: öäü");
        writeTextUsingFilesClass(lines);
        writeTextUsingFileWriter(lines);
        writeTextUsingBufferedWriter(lines);
    }
    
    /**
     * Just a one-liner so very simple however the platform on which the code is running determines what line break 
     * character is used, so if you need to explicitly control that, this method is not the right choice.
     * 
     * Charset of the file that is written depends on the input - if it contains non-ASCII characters, 
     * it will be UTF-8, otherwise ASCII.
     * 
     * @param lines
     */
    private static void writeTextUsingFilesClass(List<String> lines) {
        try {
            Path path = Files.createTempFile("usingfiles_", ".txt");
            Files.write(path, lines);    
            System.out.println("Wrote to file: " + path);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Pretty simple but writes directly without buffering so not a good choice for writing bigger amounts of text.
     * 
     * Charset of the file that is written depends on the input - if it contains non-ASCII characters, 
     * it will be UTF-8, otherwise ASCII. 
     * 
     */
    private static void writeTextUsingFileWriter(List<String> lines) {
        
        
        try {
            Path path = Files.createTempFile("usingfilewriter_", ".txt");
            
            try(FileWriter fileWriter = new FileWriter(path.toFile())) {
                
                lines.forEach(l -> {
                  try {
                      fileWriter.write(l + NEWLINE);
                  } catch(IOException e) {
                      e.printStackTrace();
                  }
                });
            }
                    
            System.out.println("Wrote to file: " + path);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Similar to FileWriter but the BufferedWriter wrapping the fileWriter buffers some characters before flushing
     * them to the actual file so better suitable for bigger amounts of text.
     * 
     * Charset can be explicitly set that way.
     * 
     */
    private static void writeTextUsingBufferedWriter(List<String> lines) {
        
        try {
            Path path = Files.createTempFile("usingbufferedwriter_", ".txt");
            
            try(BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                
                lines.forEach(l -> {
                    try {
                        bufferedWriter.write(l + NEWLINE);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                  });
            }
            
            System.out.println("Wrote to file: " + path);
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
