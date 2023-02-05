package com.java.pocs.restClients;

import com.logging.Log;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.net.http.HttpRequest.BodyPublishers;
import java.util.Base64;
import java.util.HashMap;

public class REST_Client_HttpClient {
    
    static final String className="com.java.pocs.restClients.REST_Client_HttpClient";
    
    HashMap<String,String> envPropertiesMap=new HashMap<String,String>();
    static boolean enableFlag=true;
    static boolean disableFlag=false;
    public static void main(String[] args) {
        String method="main";
        Log.logDivider();
        Log.log("START", className,method);
        Log.logLine();

        RESTClientHttpUrlConnection rest_client=new RESTClientHttpUrlConnection();
        String initializationStatus=GenericRESTClient.initializeEnvironmentDetails(rest_client.envPropertiesMap);
        if(!"SUCCESS".equals(initializationStatus)){
            Log.log("Environment Initialization failed. Aborting.", className,method);
        }
        String requestId="3931409";

        if(disableFlag){
            Log.logLine();
            Log.log("CALLING: "+rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_1), className,method);
            rest_client.invokeGetOperation(rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_1),requestId);
            Log.logLine();
        }

        if(enableFlag){
            Log.logLine();
            Log.log("CALLING: "+rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_2)+className,method);

            rest_client.invokeGetOperation(rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_2),requestId);
            Log.logLine();
        }

        if(disableFlag){
            Log.logLine();
            String post_operation1_body=rest_client.envPropertiesMap.get(REST_Constants.POST_OPERATION_1_BODY);
            Log.log("CALLING: "+rest_client.envPropertiesMap.get(REST_Constants.POST_OPERATION_1)+className,method);
            rest_client.invokePostOperation(rest_client.envPropertiesMap.get(REST_Constants.POST_OPERATION_1),post_operation1_body);
            Log.logLine();
        }
        Log.log("END", className,method);
        Log.logLine();
    }

    /**
     * Method to call REST GET operation using HttpURLConnection class
     * @param operation
     * @param params
     */
    public void invokeGetOperation(String operation,String params) {
        String method="invokeGetOperation";
        Log.log("START", className,method);
        try {

            Log.log("Calling "+operation+" for params: "+params, 
            className,method);

            String restUrl=envPropertiesMap.get(REST_Constants.BASE_URL)+envPropertiesMap.get(REST_Constants.BASE_RESOURCE)
                                +operation+params;
            Log.log("REST URL "+restUrl, className,method);

            HttpRequest.Builder requestBuilder=HttpRequest.newBuilder();
            requestBuilder.uri(new URI(restUrl));
            requestBuilder.header("Content-Type", "application/json");
            requestBuilder.header("Authorization", "Basic " +
                Base64.getEncoder().encodeToString((envPropertiesMap.get(REST_Constants.USERNAME) + ":" 
                + envPropertiesMap.get(REST_Constants.PASSWORD)).getBytes()));
            requestBuilder.GET();

            HttpRequest postRequest=requestBuilder.build();

            HttpClient httpClient=HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

            int statusCode=response.statusCode();
            String responseBody=response.body();
            
            Log.log("statusCode: "+statusCode, className,method);
            Log.log("responseBody: "+responseBody, className,method);

        }catch (Exception e) {
            Log.log(e,"Exception",className,method);
        }

        Log.log("END", className,method);

  
      }

      /**
     * Method to call REST POST operation using HttpClient class
     * @param operation
     * @param params
     */
    public void invokePostOperation(String operation,String body) {
        String method="invokePostOperation";
        Log.log("START", className,method);
        try {
            
            Log.log("Calling "+operation+" for body: "+body, 
            className,method);

            String restUrl=envPropertiesMap.get(REST_Constants.BASE_URL)+envPropertiesMap.get(REST_Constants.BASE_RESOURCE);
            Log.log("REST URL "+restUrl, className,method);

            HttpRequest.Builder requestBuilder=HttpRequest.newBuilder();
            requestBuilder.uri(new URI(restUrl));
            requestBuilder.header("Content-Type", "application/json");
            requestBuilder.header("Authorization", "Basic " +
                Base64.getEncoder().encodeToString((envPropertiesMap.get(REST_Constants.USERNAME) + ":" 
                + envPropertiesMap.get(REST_Constants.PASSWORD)).getBytes()));
            requestBuilder.POST(BodyPublishers.ofString(body));

            HttpRequest postRequest=requestBuilder.build();

            HttpClient httpClient=HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

            int statusCode=response.statusCode();
            String responseBody=response.body();
            
            Log.log("statusCode: "+statusCode, className,method);
            Log.log("responseBody: "+responseBody, className,method);


        }catch (Exception e) {
            Log.log(e,"Exception",className,method);
        }

        Log.log("END", className,method);
      }
}
