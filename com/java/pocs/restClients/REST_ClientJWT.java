package com.java.pocs.restClients;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

import com.logging.Log;

public class REST_ClientJWT {
    
    static final String className="com.java.pocs.restClients.REST_ClientJWT";
    
    HashMap<String,String> envPropertiesMap=new HashMap<String,String>();
    static boolean enableFlag=true;
    static boolean disableFlag=false;

    public static void main(String[] args) {
        String method="main";
        Log.logDivider();
        Log.log("START", className,method);
        Log.logLine();

        REST_ClientJWT rest_client=new REST_ClientJWT();
        String initializationStatus=GenericRESTClient.initializeEnvironmentDetails(rest_client.envPropertiesMap);
        if(!"SUCCESS".equals(initializationStatus)){
            Log.log("Environment Initialization failed. Aborting.", className,method);
        }
        String requestId="3931409";

        if(disableFlag){
            Log.logLine();
            Log.log("CALLING: "+rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_1), className,method);
            //est_client.invokeGetOperation(rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_1),requestId);
            Log.logLine();
        }

        if(enableFlag){
            Log.logLine();
            Log.log("CALLING: "+rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_2)+className,method);

            rest_client.invokeGetOperationByJWT(rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_2),requestId);
            Log.logLine();
        }

        if(disableFlag){
            Log.logLine();
            String post_operation1_body=rest_client.envPropertiesMap.get(REST_Constants.POST_OPERATION_1_BODY);
            Log.log("CALLING: "+rest_client.envPropertiesMap.get(REST_Constants.POST_OPERATION_1)+className,method);
            //rest_client.invokePostOperation(rest_client.envPropertiesMap.get(REST_Constants.POST_OPERATION_1),post_operation1_body);
            Log.logLine();
        }
        Log.log("END", className,method);
        Log.logLine();
    }

    /**
     * Method to call REST GET operation using JWT 
     * @param operation
     * @param params
     */
    public void invokeGetOperationByJWT(String operation,String params) {
        String method="invokeGetOperationByJWT";
        Log.log("START", className,method);
        try {
            
            Log.log("Calling "+operation+" for params: "+params, className,method);

            String restUrl=envPropertiesMap.get(REST_Constants.BASE_URL)+envPropertiesMap.get(REST_Constants.BASE_RESOURCE)+operation+params;
            
  
        } catch (Exception e) {
            Log.log(e,"Exception",className,method);
        }

        Log.log("END", className,method);

  
      }
    
}
