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
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections4.iterators.IteratorEnumeration;

/**
 *
 * @author meine
 */
public class Properties {
    private final Map<String,Property> properties = new HashMap<>();
    private final List<Property> propertyList = new LinkedList<>();

    public void setProperty(String key, String value) {
        Property p = new Property(key, value, propertyList.size(), LineType.PROPERTY);
        propertyList.add(p);
        properties.put(key, p);
    }

    public void setComment(String comment) {
        Property p = new Property(comment, LineType.COMMENT, propertyList.size());
        propertyList.add(p);
    }

    public void setEmpty() {
        Property p = new Property(LineType.EMPTY, propertyList.size());
        propertyList.add(p);
    }

    public String getProperty(String property) {
        return properties.containsKey(property) ? properties.get(property).getValue() : null;
    }

    public String getProperty(String property, String defaultValue) {
        String returnValue = getProperty(property);
        return returnValue != null ? returnValue : defaultValue;
    }

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

    public void load(InputStream in) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(in)) {
            load(reader);
        }
    }

    public void store(OutputStream out, String comments) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
        store(bw, comments);
        out.flush();
    }

    public void store(Writer out, String comments) throws IOException {
        BufferedWriter bw = new BufferedWriter(out);
        if (comments != null) {
            bw.write(comments);
        }
        Collections.sort(propertyList);
        for (int i = 0; i < propertyList.size(); i++) {
            Property prop = propertyList.get(i);
            bw.write(prop.toString());
            if (i + 1 < propertyList.size()) {
                bw.write(System.getProperty("line.separator"));
            }
        }
        bw.flush();
    }

    private LineType getType(String line) {
        if (line.isEmpty()) {
            return LineType.EMPTY;
        } else if (line.startsWith("#")) {
            return LineType.COMMENT;
        } else {
            return LineType.PROPERTY;
        }
    }

    public void list(PrintWriter pw) {
        for (Property property : propertyList) {
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
