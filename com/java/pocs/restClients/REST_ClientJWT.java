package com.java.pocs.restClients;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        if(enableFlag){
            Log.logLine();
            Log.log("CALLING: "+rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_2)+className,method);

            rest_client.invokeGetOperationByJWT(rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_2),requestId);
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
        String cookieValue=null;
        String cookieKey=null;
        String jwtToken=null;
        try {
            
            String tokenKey1 =  envPropertiesMap.get(REST_Constants.TOKEN_KEY_1);
            Log.log("Calling getCookie() for  "+tokenKey1+" token.", className,method);
            HashMap<String,String> cookiesMap=getCookie();
            for(Map.Entry<String, String> set:cookiesMap.entrySet()){
                if(set.getKey().contains(tokenKey1)){
                    cookieKey=set.getKey();
                    cookieValue=set.getValue();
                    Log.log("cookieKey: "+cookieKey+" cookieValue: "+cookieValue, className,method);

                }
            }

            Log.log("Calling getJWTToken().", className,method);
            jwtToken="xyz";
            if(jwtToken!=null){
                Log.log("Calling "+operation+" for params: "+params, 
                className,method);
    
                String restUrl=envPropertiesMap.get(REST_Constants.BASE_URL)+envPropertiesMap.get(REST_Constants.BASE_RESOURCE)
                                    +operation+params;
                Log.log("REST URL "+restUrl, className,method);
    
                HttpRequest.Builder requestBuilder=HttpRequest.newBuilder();
                requestBuilder.uri(new URI(restUrl));
                requestBuilder.header("Content-Type", "application/json");
                requestBuilder.header("Authorization", "Bearer " +jwtToken);
                requestBuilder.GET();
    
                HttpRequest postRequest=requestBuilder.build();
    
                HttpClient httpClient=HttpClient.newHttpClient();
                HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
    
                int statusCode=response.statusCode();
                String responseBody=response.body();
                
                Log.log("statusCode: "+statusCode, className,method);
                Log.log("responseBody: "+responseBody, className,method);
            }
            
            
  
        } catch (Exception e) {
            Log.log(e,"Exception",className,method);
        }
        Log.logLine();
        Log.log("END", className,method);

  
      }

      public HashMap<String,String> getCookie(){
        String method="getCookie";
        HashMap<String,String> cookiesMap=new HashMap<String,String>();
        Log.log("START", className,method);

        String cookieValue=null;
        String cookieKey=null;
        try {

            String restUrl1=envPropertiesMap.get(REST_Constants.BASE_URL)+envPropertiesMap.get(REST_Constants.BASE_RESOURCE_1);
            Log.log("restUrl1: "+restUrl1, className,method);

            URL url1 = new URL(restUrl1);
            URLConnection conn = url1.openConnection();
            Map<String, List<String>> headerFields = conn.getHeaderFields();
            Set<String> headerFieldsSet = headerFields.keySet();
            Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();
            String tokenKey1 =  envPropertiesMap.get(REST_Constants.TOKEN_KEY_1);

            while(hearerFieldsIter.hasNext()) {
                String headerFieldKey = hearerFieldsIter.next();
                if("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
                    List<String> headerFieldValue = headerFields.get(headerFieldKey);
                    for (String headerValue : headerFieldValue) {
                        Log.logLine();
                        Log.log("Set-Cookie headerValue: "+headerValue, className,method);
                        if(headerValue.contains(tokenKey1)){
                            String tempString=cookieValue = headerValue.substring( 0, headerValue.indexOf(";"));
                            cookieKey = tempString.substring(0,tempString.indexOf("="));
                            cookieValue = tempString.substring(tempString.indexOf("=")+1,tempString.length());
                            Log.log("Set-Cookie tempString: "+tempString, className,method);
                            Log.log("Set-Cookie cookieKey: "+cookieKey, className,method);
                            Log.log("Set-Cookie cookieValue: "+cookieValue, className,method);
                            cookiesMap.put(cookieKey, cookieValue);

                        }

                    }//END for()
                }//END if("Set-Cookie".equalsIgnoreCase(headerFieldKey))
                else{
                    List<String> headerFieldValue = headerFields.get(headerFieldKey);
                    for (String headerValue : headerFieldValue) {
                        Log.log("headerValue: "+headerValue, className,Log.DISABLE);
                    }
                }//END Else
           }//END while(hearerFieldsIter.hasNext())



  
        } catch (Exception e) {
            Log.log(e,"Exception",className,method);
        }

        Log.logLine();
        Log.log("END: "+cookiesMap, className,method);
        return cookiesMap;
      }
}
