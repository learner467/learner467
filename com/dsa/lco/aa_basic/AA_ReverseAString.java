package com.dsa.lco.aa_basics;
public class AA_ReverseAString{

    public static void main(String[] args) {
        log("main","START");
        String myString="parag";
        log("main","Calling reverseString for string "+myString);

        reverseString(myString);
        log("main","END");
    }

    public static String reverseString(String myString){
        log("reverseString","START");
        log("reverseString","Input String is: "+myString);
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

        log("reverseString","Reversed String is: "+myString);
        log("reverseString","END");
        return myString;
        
    }

    public static void log(String method,String message){
        System.out.println("AA_ReverseAString:"+method+"::"+message);
    }
}