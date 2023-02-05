package com.java.pocs.restClients;

import com.java.pocs.ConfConstants;
import com.logging.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public static void main(String[] args) {
        Log.logDivider();
        Log.log("START", "com.java.pocs.restClients.REST_Client","main");
        Log.logLine();

        REST_Client rest_client=new REST_Client();
        rest_client.initializeEnvironmentDetails();
        String requestId="3931409";
        rest_client.getCallERPIService(rest_client.envPropertiesMap.get(REST_Constants.GET_OPERATION_1),requestId);

        Log.log("END", "com.java.pocs.restClients.REST_Client","main");
        Log.logLine();
    }

    public void getCallERPIService(String operation,String params) {
        Log.log("START", "com.java.pocs.restClients.REST_Client","getCallERPIService");
        try {
            
            Log.log("Calling "+operation+" for params: "+params, 
            "com.java.pocs.restClients.REST_Client","getCallERPIService");

            String restUrl=envPropertiesMap.get(REST_Constants.BASE_URL)+envPropertiesMap.get(REST_Constants.BASE_RESOURCE)+operation+params;
            URL url = new URL(restUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Basic " +
            Base64.getEncoder().encodeToString((envPropertiesMap.get(REST_Constants.USERNAME) + ":" 
            + envPropertiesMap.get(REST_Constants.PASSWORD)).getBytes()));

            int responseCode=conn.getResponseCode();

            Log.log("ResponseCode: "+responseCode, "com.java.pocs.restClients.REST_Client","getCallERPIService");

            if (responseCode != 200) {
                Log.log("Failed : HTTP error code: "+responseCode, "com.java.pocs.restClients.REST_Client","getCallERPIService");
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            Log.log("Output from Server START:....\n", "com.java.pocs.restClients.REST_Client","getCallERPIService");
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            Log.log("Output from Server END:....\n", "com.java.pocs.restClients.REST_Client","getCallERPIService");
            conn.disconnect();
  
        } catch (MalformedURLException e) {
            Log.log("ExeceptionOccured:::MalformedURLException:::"+e+Arrays.toString(e.getStackTrace()).replace(",", "\n"), 
            "com.java.pocs.restClients.REST_Client","getCallERPIService");
  
        } catch (IOException e) {
            Log.log("ExeceptionOccured:::IOException:::"+e+Arrays.toString(e.getStackTrace()).replace(",", "\n"), 
            "com.java.pocs.restClients.REST_Client","getCallERPIService");
  
        } catch (Exception e) {
            Log.log("ExeceptionOccured:::Exception:::"+e+Arrays.toString(e.getStackTrace()).replace(",", "\n"), 
            "com.java.pocs.restClients.REST_Client","getCallERPIService");
  
        }

        Log.log("END", "com.java.pocs.restClients.REST_Client","getCallERPIService");

  
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
            envPropertiesMap.put(REST_Constants.GET_OPERATION_1, prop.getProperty("get.operation1"));
            envPropertiesMap.put(REST_Constants.USERNAME, prop.getProperty("username"));
            envPropertiesMap.put(REST_Constants.PASSWORD, prop.getProperty("password"));

            Log.log("Initilized values are ....START", "com.java.pocs.restClients.REST_Client","initializeEnvironmentDetails");
            Log.logLine();
            envPropertiesMap.entrySet().forEach(entry -> {
                Log.log("   "+entry.getKey() + "::: " + entry.getValue(), "com.java.pocs.restClients.REST_Client","initializeEnvironmentDetails");
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
