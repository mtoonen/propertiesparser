/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.meg.propertiesutility;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author meine
 */
public class Properties {
    private final Map<String, String> properties = new HashMap<>();
   
    public Properties(){

        
    }
    
    public void setProperty(String key, String value){
        properties.put(key, value);
    }
    
    public String getProperty(String property){
        return properties.get(property);
    }
    
    public String getProperty(String property, String defaultValue){
        if(properties.containsKey(property)){
            return properties.get(property);
        }else{
            return defaultValue;
        }
    }
}
