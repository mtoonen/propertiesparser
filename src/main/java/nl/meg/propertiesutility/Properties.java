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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author meine
 */
public class Properties {
    private Map<String, String> properties = new HashMap<>();
    private List<Property> propertyList = new ArrayList<>();
   
    public Properties(){

        
    }
    
    public void setProperty(String key, String value){
        properties.put(key, value);
        Property p = new Property(key, value, propertyList.size(), LineType.PROPERTY);
        propertyList.add(p);
    }
    
    public void setComment(String comment){
        Property p = new Property( comment, LineType.COMMENT, propertyList.size());
        propertyList.add(p);
    }
    
    public void setEmpty(){
        Property p = new Property(LineType.EMPTY, propertyList.size());
        propertyList.add(p);
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
    
    public void load(InputStream in) throws IOException{
        
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            
            while((line = reader.readLine()) != null){
                LineType type = getType(line);
                switch(type){
                    case EMPTY:
                        setEmpty();
                        break;
                    case COMMENT:
                        setComment(line);
                        break;
                    case PROPERTY:
                        String key = line.substring(0, line.indexOf("="));
                        String value = line.substring(line.indexOf("=") + 1);
                        setProperty(key, value);
                        break;
                    default:
                        throw new IllegalArgumentException("Entered line is invalid: " + line + ". Expected property or comment.");
                }
            }
        } catch (IOException ex) {
            System.err.println("Error reader line");
        }finally{
            in.close();
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
    
    public void printList(){
        for (Iterator<Property> it = propertyList.iterator(); it.hasNext();) {
            Property property = it.next();
            System.out.println(property.toString());
        }
    }
    public enum LineType {
        COMMENT, PROPERTY, EMPTY;
    }
}
