/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meg.propertiesutility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections4.iterators.IteratorEnumeration;

/**
 *
 * @author meine
 */
public class Properties implements Propertable {

    private final Map<String, Property> properties = new HashMap<>();
 //   private final List<Property> propertyList = new LinkedList<>();

    @Override
    public void setProperty(String key, String value) {
        key = key.trim();
        String val = value;
        while (val.charAt(0)== ' '){
            val = val.substring(1);
        }
        
        Property p = new Property(key, val, properties.size(), LineType.PROPERTY);
        properties.put(key, p);
       
       /* int index = -1;
        for (int i = 0 ; i < propertyList.size() ;i ++) {
            if(propertyList.get(i).getKey().equals(key)){
                index = i;
                break;
            }
        }
        if(index != -1){
            propertyList.remove(index);
        }
        propertyList.add(p);*/
    }

    public void setComment(String comment) {
        Property p = new Property(comment, LineType.COMMENT, properties.size());
        properties.put(""+properties.size(),p);
    }

    public void setEmpty() {
        Property p = new Property(LineType.EMPTY, properties.size());
        properties.put(""+properties.size(),p);
    }

    @Override
    public String getProperty(String property) {
        return getProperty(property, null);
    }

    @Override
    public String getProperty(String property, String defaultValue) {
        return properties.containsKey(property)  ? properties.get(property).getValue() : defaultValue;
    }

    @Override
    public void load(Reader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            LineType type = getType(line);
            switch (type) {
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
                    throw new IllegalArgumentException("Entered line is invalid: " + line + ". Expected property, empty or comment.");
            }
        }
    }

    @Override
    public void load(InputStream in) throws IOException {
        InputStreamReader reader = new InputStreamReader(in);
        load(reader);
    }

    @Override
    public void store(OutputStream out, String comments) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
        store(bw, comments);
        out.flush();
    }

    @Override
    public void store(Writer out, String comments) throws IOException {
        BufferedWriter bw = new BufferedWriter(out);
        if (comments != null) {
            if (!comments.startsWith("#")) {
                bw.write('#');
            }
            bw.write(comments);
        }
        List<Property> propertyList = new ArrayList(properties.values());

        Collections.sort(propertyList);
        for (int i = 0; i < propertyList.size(); i++) {
            if (i > 0) {
                bw.write(System.getProperty("line.separator"));
            }
            Property prop = propertyList.get(i);
            bw.write(prop.toString());
        }

        bw.flush();
    }

    LineType getType(String line) {
        String copy = line.trim();
        if (copy.isEmpty()) {
            return LineType.EMPTY;
        } else if (copy.startsWith("#")) {
            return LineType.COMMENT;
        } else {
            return LineType.PROPERTY;
        }
    }

    public void list(PrintWriter pw) {
        for (Property property : properties.values()) {
            pw.println(property.toString());
        }
    }

    public Set<String> stringPropertyNames() {
        return properties.keySet();
    }

    public Enumeration<?> propertyNames() {
        Enumeration enumeration = new IteratorEnumeration(stringPropertyNames().iterator());
        return enumeration;
    }

    public enum LineType {

        COMMENT, PROPERTY, EMPTY;
    }
}
