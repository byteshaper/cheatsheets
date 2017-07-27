package com.byteshaper.cheatsheets.standalonestuff;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates how to write text to a file
 *
 */
public class WriteTextToFile {

    public static void main(String[] args) {
        List<String> lines = Arrays.asList("First line", "Second line", "Third line");
        writeTextUsingFilesClass(lines);
    }
    
    /**
     * Just a one-liner so very simple however the platform on which the code is running determines what line break 
     * character is used, so if you need to explicitly control that, this method is not the right choice.
     * 
     * @param lines
     */
    public static void writeTextUsingFilesClass(List<String> lines) {
        try {
            Path path = Files.createTempFile("usingfiles_", ".txt");
            Files.write(path, lines);    
            System.out.println("Wrote to file: " + path);
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }
}
