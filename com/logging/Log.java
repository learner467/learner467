package com.logging;

import java.util.Arrays;

public class Log {
   
    public static final String DISABLE="DISABLE";
    public static void log(String message,String className, String method){
        if(!DISABLE.equals(method)){
            System.out.println(className+":"+method+"::"+message);
        }  
    }

    public static void log(String message,String flag){
        if(!DISABLE.equals(flag)){
            int n=20;
            String spaces = String.format("%1$"+n+"s", "");
            System.out.println(spaces+message);
        }  
    }

    public static void log(Exception e,String message,String className, String method){
        if(!DISABLE.equals(method)){
            System.out.println(className+":"+method+"::ExeceptionOccured:::"+message+e+Arrays.toString(e.getStackTrace()).replace(",", "\n"));
           
       
        }
    }
    public static void logDivider(){
        System.out.println("\n\n\n");
    }

    public static void logLine(){
        System.out.println("");
    }

}
