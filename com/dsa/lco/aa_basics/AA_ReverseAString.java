package com.dsa.lco.aa_basics;

import com.logging.Log;
public class AA_ReverseAString{

    public static void main(String[] args) {
        Log.logDivider();
        Log.log("START", "com.dsa.lco.aa_basics.AA_ReverseAString","main");
        Log.logLine();

        String myString="parag";
        Log.log("Calling reverseString for string "+myString, "com.dsa.lco.aa_basics.AA_ReverseAString","main");
        
        reverseString(myString);

        Log.logLine();
        Log.log("END", "com.dsa.lco.aa_basics.AA_ReverseAString","main");
        Log.logDivider();
    }

    public static String reverseString(String myString){
        Log.log("START", "com.dsa.lco.aa_basics.AA_ReverseAString","reverseString");
        Log.log("Input String is: "+myString, "com.dsa.lco.aa_basics.AA_ReverseAString","reverseString");

        char myArrary[]=myString.toCharArray();

        int lastIndex=myArrary.length-1;

        for(int i=0; i<myArrary.length/2;i++){
            if(i!=lastIndex-1){
                char temp=myArrary[i];
                myArrary[i]= myArrary[lastIndex-i];
                myArrary[lastIndex-i]=temp;
            }
            
        }
        myString =new String(myArrary);

        Log.log("Reversed String is: "+myString, "com.dsa.lco.aa_basics.AA_ReverseAString","reverseString");
        Log.log("END", "com.dsa.lco.aa_basics.AA_ReverseAString","reverseString");

        return myString;
        
    }

}