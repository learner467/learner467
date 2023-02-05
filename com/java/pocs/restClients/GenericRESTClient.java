package com.java.pocs.restClients;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import com.java.pocs.ConfConstants;
import com.logging.Log;

public class GenericRESTClient {
    
    static final String className="com.java.pocs.restClients.GenericRESTClient";
    /**
     * Initialize environment details from Local Files
     * @return
     */
    public static String initializeEnvironmentDetails(HashMap<String,String> envPropertiesMap){
        String method="initializeEnvironmentDetails";
        Log.log("START", className,method);
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
            

            Log.log("Initilized values are ....START", className,method);
            Log.logLine();
            envPropertiesMap.entrySet().forEach(entry -> {
                Log.log(entry.getKey() + "::: " + entry.getValue(), "initializeEnvironmentDetails");
            });
            Log.logLine();
            Log.log("Initilized values are ....END", className,method);


        }catch (Exception e){
            Log.log("ExeceptionOccured:::Exception:::"+e+Arrays.toString(e.getStackTrace()).replace(",", "\n"), 
            className,method);
            status="ERROR";
        }
        Log.logLine();
        Log.log("END Returning status: "+status, className,method);
        return status;
      }//END initializeEnvironmentDetails()
}
