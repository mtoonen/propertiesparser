/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meg.propertiesutility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 *
 * @author Gertjan
 */
public interface Propertable {

    String getProperty(String key);

    String getProperty(String key, String defaultValue);

    void load(InputStream resource) throws IOException;

    void load(Reader reader) throws IOException;

    void setProperty(String key, String value);

    void store(OutputStream out, String comments) throws IOException;
    
    void store(Writer out, String comments) throws IOException;

}
