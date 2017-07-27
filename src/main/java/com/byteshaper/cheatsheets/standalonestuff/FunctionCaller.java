package com.byteshaper.cheatsheets.standalonestuff;

import java.util.Arrays;

/**
 * Demonstrates how to find out the caller of a function
 *
 */
public class FunctionCaller {

    public static void main(String[] args) {
        myFirstFunction();
    }
    
    public static void myFirstFunction() {
        mySecondFunction();
    }
    
    public static void  mySecondFunction() {
        
        // The stacktrace elements contain the callers in reverse order - the first element is the
        // function itself, the next is the function that has called this function, and so on
        // till the root, in this example the main() method:
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        Arrays
            .stream(stackTraceElements)
            .forEach(ste -> System.out.println(ste.getClassName() + " / " + ste.getMethodName()));
    }
    
}
