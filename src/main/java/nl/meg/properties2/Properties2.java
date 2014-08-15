/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meg.properties2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Gertjan
 */
public class Properties2 {

    private final LinkedList<Object> properties = new LinkedList<>();
    private final HashMap<String, AbstractMap.SimpleEntry<String, String>> lookup = new HashMap<>();

    public void setProperty(String key, String value) {
        AbstractMap.SimpleEntry<String, String> entry = new AbstractMap.SimpleEntry(key, value);
        properties.add(entry);
        lookup.put(key, entry);
    }

    public String getProperty(String key) {
        return getProperty(key, null);
    }

    public String getProperty(String key, String defaultValue) {
        return lookup.containsKey(key) ? lookup.get(key).getValue() : defaultValue;
    }

    public void load(InputStream resource) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(resource)) {
            load(reader);
        }
    }

    public void store(OutputStream out, String comments) {
        try (PrintWriter writer = new PrintWriter(out)) {
            if (comments != null) {
                writer.println(comments.startsWith("#") ? comments : "#" + comments);
            }

            for (Object o : properties) {
                if (o.equals(properties.getLast())) {
                    writer.print(o);
                } else {
                    writer.println(o);
                }
            }
        }
    }

    public void load(Reader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (!line.startsWith("#") && line.contains("=")) {
                int index = line.indexOf("=");
                setProperty(line.substring(0, index), line.substring(index + 1));
            } else {
                properties.add(line);
            }
        }
    }
}
