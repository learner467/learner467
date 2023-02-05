package com.java.pocs.restClients;

import com.java.pocs.ConfConstants;
import com.logging.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Base64;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Properties;

public class REST_Client {
    
    HashMap<String,String> envPropertiesMap=new HashMap<String,String>();
    static boolean enableFlag=true;
    static boolean disableFlag=false;
    public static void main(String[] args) {
        Log.logDivider();
        Log.log("START", "com.java.pocs.restClients.REST_Client","main");
        Log.logLine();

        REST_Client rest_client=new REST_Client();
        rest_client.initializeEnvironmentDetails();
        String requestId="3931409";

        if(disableFlag){
            Log.logLine();
            Log.log("============CALLING: "+rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_1)+
            "============", "com.java.pocs.restClients.REST_Client","main");

            rest_client.invokeGetOperation(rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_1),requestId);
            Log.logLine();
        }

        if(disableFlag){
            Log.logLine();
            Log.log("============CALLING: "+rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_2)+
                    "============", "com.java.pocs.restClients.REST_Client","main");

            rest_client.invokeGetOperation(rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_2),requestId);
            Log.logLine();
        }

        if(enableFlag){
            Log.logLine();
            String post_operation1_body=rest_client.envPropertiesMap.get(REST_Constants.POST_OPERATION_1_BODY);
            Log.log("============CALLING: "+rest_client.envPropertiesMap.get(REST_Constants.POST_OPERATION_1)+
                    "============", "com.java.pocs.restClients.REST_Client","main");
            rest_client.invokePostOperation(rest_client.envPropertiesMap.get(REST_Constants.POST_OPERATION_1),post_operation1_body);
            Log.logLine();
        }
        Log.log("END", "com.java.pocs.restClients.REST_Client","main");
        Log.logLine();
    }

    /**
     * Method to call REST GET operation using HttpURLConnection class
     * @param operation
     * @param params
     */
    public void invokeGetOperation(String operation,String params) {
        Log.log("START", "com.java.pocs.restClients.REST_Client","invokeGetOperation");
        try {
            
            Log.log("Calling "+operation+" for params: "+params, 
            "com.java.pocs.restClients.REST_Client","invokeGetOperation");

            String restUrl=envPropertiesMap.get(REST_Constants.BASE_URL)+envPropertiesMap.get(REST_Constants.BASE_RESOURCE)+operation+params;
            URL url = new URL(restUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Basic " +
            Base64.getEncoder().encodeToString((envPropertiesMap.get(REST_Constants.USERNAME) + ":" 
            + envPropertiesMap.get(REST_Constants.PASSWORD)).getBytes()));

            int responseCode=conn.getResponseCode();

            Log.log("ResponseCode: "+responseCode, "com.java.pocs.restClients.REST_Client","invokeGetOperation");

            if (responseCode != 200) {
                Log.log("Failed : HTTP error code: "+responseCode, "com.java.pocs.restClients.REST_Client","invokeGetOperation");
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            Log.log("Output from Server START:....\n", "com.java.pocs.restClients.REST_Client","invokeGetOperation");
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            Log.log("Output from Server END:....\n", "com.java.pocs.restClients.REST_Client","invokeGetOperation");
            conn.disconnect();
  
        } catch (MalformedURLException e) {
            Log.log("ExeceptionOccured:::MalformedURLException:::"+e+Arrays.toString(e.getStackTrace()).replace(",", "\n"), 
            "com.java.pocs.restClients.REST_Client","invokeGetOperation");
  
        } catch (IOException e) {
            Log.log("ExeceptionOccured:::IOException:::"+e+Arrays.toString(e.getStackTrace()).replace(",", "\n"), 
            "com.java.pocs.restClients.REST_Client","invokeGetOperation");
  
        } catch (Exception e) {
            Log.log("ExeceptionOccured:::Exception:::"+e+Arrays.toString(e.getStackTrace()).replace(",", "\n"), 
            "com.java.pocs.restClients.REST_Client","invokeGetOperation");
  
        }

        Log.log("END", "com.java.pocs.restClients.REST_Client","invokeGetOperation");

  
      }

      /**
     * Method to call REST POST operation using HttpURLConnection class
     * @param operation
     * @param params
     */
    public void invokePostOperation(String operation,String body) {
        Log.log("START", "com.java.pocs.restClients.REST_Client","invokePostOperation");
        try {
            
            Log.log("Calling "+operation+" for body: "+body, 
            "com.java.pocs.restClients.REST_Client","invokePostOperation");

            String restUrl=envPropertiesMap.get(REST_Constants.BASE_URL)+envPropertiesMap.get(REST_Constants.BASE_RESOURCE);
            Log.log("REST URL "+restUrl, "com.java.pocs.restClients.REST_Client","invokePostOperation");
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

            Log.log("ResponseCode: "+responseCode, "com.java.pocs.restClients.REST_Client","invokePostOperation");

            if (responseCode != HttpURLConnection.HTTP_CREATED) {
                Log.log("Failed : HTTP error code: "+responseCode, "com.java.pocs.restClients.REST_Client","invokePostOperation");
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            Log.log("Output from Server START:....\n", "com.java.pocs.restClients.REST_Client","invokePostOperation");
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            Log.log("Output from Server END:....\n", "com.java.pocs.restClients.REST_Client","invokePostOperation");
            conn.disconnect();

        } catch (MalformedURLException e) {
            Log.log("ExeceptionOccured:::MalformedURLException:::"+e+Arrays.toString(e.getStackTrace()).replace(",", "\n"), 
            "com.java.pocs.restClients.REST_Client","invokePostOperation");
  
        } catch (IOException e) {
            Log.log("ExeceptionOccured:::IOException:::"+e+Arrays.toString(e.getStackTrace()).replace(",", "\n"), 
            "com.java.pocs.restClients.REST_Client","invokePostOperation");
  
        } catch (Exception e) {
            Log.log("ExeceptionOccured:::Exception:::"+e+Arrays.toString(e.getStackTrace()).replace(",", "\n"), 
            "com.java.pocs.restClients.REST_Client","invokePostOperation");
  
        }

        Log.log("END", "com.java.pocs.restClients.REST_Client","invokePostOperation");
      }

    /**
     * Initialize environment details from Local Files
     * @return
     */
    public String initializeEnvironmentDetails(){
        Log.log("START", "com.java.pocs.restClients.REST_Client","initializeEnvironmentDetails");
        Log.logLine();
        String status="SUCCESS";
        try(InputStream input = new FileInputStream(ConfConstants.ENV_CONFIG_FILE)){
            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            envPropertiesMap.put(REST_Constants.BASE_URL, prop.getProperty("base.url"));
            envPropertiesMap.put(REST_Constants.BASE_RESOURCE, prop.getProperty("base.resource"));
            
            envPropertiesMap.put(REST_Constants.USERNAME, prop.getProperty("username"));
            envPropertiesMap.put(REST_Constants.PASSWORD, prop.getProperty("password"));

            envPropertiesMap.put(REST_Constants.GET_OPERATION_1, prop.getProperty("get.operation1"));
            envPropertiesMap.put(REST_Constants.GET_OPERATION_2, prop.getProperty("get.operation2"));

            envPropertiesMap.put(REST_Constants.POST_OPERATION_1, prop.getProperty("post.operation1"));
            envPropertiesMap.put(REST_Constants.POST_OPERATION_1_BODY, prop.getProperty("post.operation1_body"));
            

            Log.log("Initilized values are ....START", "com.java.pocs.restClients.REST_Client","initializeEnvironmentDetails");
            Log.logLine();
            envPropertiesMap.entrySet().forEach(entry -> {
                Log.log(entry.getKey() + "::: " + entry.getValue(), "initializeEnvironmentDetails");
            });
            Log.logLine();
            Log.log("Initilized values are ....END", "com.java.pocs.restClients.REST_Client","initializeEnvironmentDetails");


        }catch (Exception e){
            Log.log("ExeceptionOccured:::Exception:::"+e+Arrays.toString(e.getStackTrace()).replace(",", "\n"), 
            "com.java.pocs.restClients.REST_Client","initializeEnvironmentDetails");
            status="ERROR";
        }
        Log.logLine();
        Log.log("END Returning status: "+status, "com.java.pocs.restClients.REST_Client","initializeEnvironmentDetails");
        return status;
      }//END initializeEnvironmentDetails()


}
