package com.logging;

public class Log {
   
    public static void log(String message,String className, String method){
        System.out.println("    "+className+":"+method+"::"+message);
    }
    public static void logDivider(){
        System.out.println("\n\n\n");
    }

    public static void logLine(){
        System.out.println("");
    }

}
