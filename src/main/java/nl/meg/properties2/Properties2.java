/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meg.properties2;

import nl.meg.propertiesutility.Propertable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Gertjan
 */
public class Properties2 implements Propertable {

    private final LinkedList<Object> properties = new LinkedList<>();
    private final HashMap<String, AbstractMap.SimpleEntry<String, String>> lookup = new HashMap<>();
    private static Pattern PATTERN;

    static {
        try {
            //(\\\\s*=\\\\s*)([\\^]+)
            PATTERN = Pattern.compile("([\\^]+)(\\\\s*=\\\\s*)([\\^]+)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setProperty(String key, String value) {
        AbstractMap.SimpleEntry<String, String> entry;
        if ((entry = lookup.get(key)) == null) {
            entry = new AbstractMap.SimpleEntry(key, value);
            lookup.put(key, entry);
            properties.add(entry);
        } else {
            entry.setValue(value);
        }
    }

    @Override
    public String getProperty(String key) {
        return getProperty(key, null);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return lookup.containsKey(key) ? lookup.get(key).getValue() : defaultValue;
    }

    @Override
    public void load(InputStream resource) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(resource)) {
            load(reader);
        }
    }

    @Override
    public void load(Reader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (!line.startsWith("#") && line.contains("=")) {
                Matcher matcher = PATTERN.matcher(line);
                matcher.group();
                if(matcher.find()){
                    setProperty(matcher.group(1), matcher.group(2));
                }
            } else {
                properties.add(line);
            }
        }
    }

    @Override
    public void store(OutputStream out, String comments) throws IOException {
        store(new PrintWriter(out), comments);
    }

    @Override
    public void store(Writer out, String comments) throws IOException {
        store(new PrintWriter(out), comments);
    }

    private void store(PrintWriter out, String comments) throws IOException {
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
}
