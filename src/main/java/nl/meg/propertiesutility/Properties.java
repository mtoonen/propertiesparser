/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.meg.propertiesutility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    
    public void load(InputStream in){
        
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            
            while((line = reader.readLine()) != null){
                LineType type = getType(line);
                switch(type){
                    case EMPTY:
                        break;
                    case COMMENT:
                        break;
                    case PROPERTY:
                        String key = line.substring(0, line.indexOf("="));
                        String value = line.substring(line.indexOf("="));
                        setProperty(key, value);
                        break;
                    default:
                        throw new IllegalArgumentException("Entered line invalid: " + line + ". Expected property or comment.");
                }
            }
        } catch (IOException ex) {
            System.err.println("Error reader line");
        }
    }
    
    public void store (OutputStream out, String comments){
        throw new NotImplementedException();
        
    }
    
    private LineType getType(String line){
        if(line.isEmpty()){
            return LineType.EMPTY;
        }else if(line.startsWith("#")){
            return LineType.COMMENT;
        }else{
            return LineType.PROPERTY;
        }
    }
    
    public enum LineType {
        COMMENT, PROPERTY, EMPTY;
    }
}
