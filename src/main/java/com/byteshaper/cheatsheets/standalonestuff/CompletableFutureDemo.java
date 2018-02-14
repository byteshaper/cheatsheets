package com.byteshaper.cheatsheets.standalonestuff;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String result = CompletableFuture
            .supplyAsync(CompletableFutureDemo::createFirstStringAfterDelay)
            .thenCombine(CompletableFuture.supplyAsync(
                    CompletableFutureDemo::createSecondStringAfterDelay), (s0, s1) -> s0 + " // " + s1)
            .get();
        System.out.println("Combined result: " + result);
    }
    
    public static String createFirstStringAfterDelay() {
        System.out.println("createFirstStringAfterDelay()...");
        waitSeconds(2);
        System.out.println("createFirstStringAfterDelay() FINISHED");
        return "first string";
    }
    
    public static String createSecondStringAfterDelay() {
        System.out.println("createSecondStringAfterDelay()...");
        waitSeconds(2);
        System.out.println("createSecondStringAfterDelay() FINISHED");
        return "second string";
    }
    
    private static void waitSeconds(long seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
