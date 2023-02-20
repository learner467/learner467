package com.java.pocs.restClients;

import com.logging.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;


import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Base64;
import java.util.HashMap;

public class RESTClientHttpUrlConnection {
    
    static final String className="com.java.pocs.restClients.RESTClientHttpUrlConnection";
    
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

        if(disableFlag){
            Log.logLine();
            Log.log("CALLING: "+rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_2)+className,method);

            rest_client.invokeGetOperation(rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_2),requestId);
            Log.logLine();
        }

        if(enableFlag){
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

            String restUrl=envPropertiesMap.get(REST_Constants.BASE_URL)+envPropertiesMap.get(REST_Constants.BASE_RESOURCE)+operation+params;
            URL url = new URL(restUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Basic " +
            Base64.getEncoder().encodeToString((envPropertiesMap.get(REST_Constants.USERNAME) + ":" 
            + envPropertiesMap.get(REST_Constants.PASSWORD)).getBytes()));

            int responseCode=conn.getResponseCode();

            Log.log("ResponseCode: "+responseCode, className,method);

            if (responseCode != 200) {
                Log.log("Failed : HTTP error code: "+responseCode, className,method);
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            Log.log("Output from Server START:....\n", className,method);
            String output;
            StringBuilder sb=new StringBuilder();
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                sb.append(output);
            }
            Log.log("Output from Server END:....\n", className,method);
            conn.disconnect();

            parseJson(sb.toString());
  
        } catch (MalformedURLException e) {
            Log.log(e,"MalformedURLException",className,method);
        } catch (IOException e) {
            Log.log(e,"IOException",className,method);
        } catch (Exception e) {
            Log.log(e,"Exception",className,method);
        }

        Log.log("END", className,method);

  
      }

      /**
     * Method to call REST POST operation using HttpURLConnection class
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
            URL url = new URL(restUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Basic " +
            Base64.getEncoder().encodeToString((envPropertiesMap.get(REST_Constants.USERNAME) + ":" 
            + envPropertiesMap.get(REST_Constants.PASSWORD)).getBytes()));

            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes());
            os.flush();

            int responseCode=conn.getResponseCode();

            Log.log("ResponseCode: "+responseCode, className,method);

            if (responseCode != HttpURLConnection.HTTP_CREATED) {
                Log.log("Failed : HTTP error code: "+responseCode, className,method);
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            Log.log("Output from Server START:....\n", className,method);
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            Log.log("Output from Server END:....\n", className,method);
            conn.disconnect();

        } catch (MalformedURLException e) {
            Log.log(e,"MalformedURLException",className,method);
        } catch (IOException e) {
            Log.log(e,"IOException",className,method);
        } catch (Exception e) {
            Log.log(e,"Exception",className,method);
        }

        Log.log("END", className,method);
      }

      public void parseJson(String jsonString){
        String method="parseJson";
        Log.log("START", className,method);
        try{
            JSONObject jObj= new JSONObject(jsonString);
            System.out.println(jObj.get("key"));
        }catch (Exception e) {
            Log.log(e,"Exception",className,method);
        }
        Log.log("END", className,method);
      }
      

}
