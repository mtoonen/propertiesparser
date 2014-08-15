package nl.meg.propertiesutility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;

public class PropertiesTest {

    private final Properties properties = spy(new Properties());

    @Before
    public void before() throws IOException {

    }

    @Test
    public void testSetSimpleKeyValue() {
        properties.setProperty("key", "value");
        assertEquals("value", properties.getProperty("key"));
    }

    @Test
    public void testSetKeyValue() {
        properties.setProperty("parent.child.key", "val");
        assertEquals("val", properties.getProperty("parent.child.key"));
    }

    @Test
    public void testGetDefaultValue() {
        String value = properties.getProperty("not.a.key", "mydefault");
        assertEquals("mydefault", value);
    }

    @Test
    public void testLoad() throws IOException {
        properties.load(getResource());
        assertEquals("Some text you'll never read !@#$%^&*(*\"", properties.getProperty("some.super.long.key"));
        assertEquals("val", properties.getProperty("parent.child"));
        assertEquals("simplevalue", properties.getProperty("simplekey"));
        verify(properties, times(3)).getProperty(anyString());
    }

    @Test
    public void testLoadJavaProperties() throws IOException {
        java.util.Properties properties = spy(new java.util.Properties());
        properties.load(getResource());
        assertEquals("Some text you'll never read !@#$%^&*(*\"", properties.getProperty("some.super.long.key"));
        assertEquals("val", properties.getProperty("parent.child"));
        assertEquals("simplevalue", properties.getProperty("simplekey"));

        verify(properties, times(3)).getProperty(anyString());
    }

    @Test
    public void testStoreSimpleKeyValue() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        properties.setProperty("key", "value");
        properties.store(os, null);

        assertEquals("key=value", new String(os.toByteArray()));
    }

    @Test
    public void testStoreExample() throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(getResource(), writer, "UTF-8");
        String before = writer.toString();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        InputStream resource = spy(getResource());
        properties.load(resource);
        properties.store(os, null);
        
        String after = new String(os.toByteArray());
        
        System.out.println("Before:");
        System.out.println(before);
        System.out.println();
        System.out.println("After:");
        System.out.println(after);
        
        assertEquals(before, after);
    }
    
    
    @Test
    public void testStoreReaderExample() throws IOException, URISyntaxException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(getResource(), writer, "UTF-8");
        String before = writer.toString();

        URL url = PropertiesTest.class.getResource("example.properties");
        URI uri = url.toURI();
        FileReader fr = spy(new FileReader(new File(uri)));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        properties.load(fr);
        properties.store(os, null);
        
        String after = new String(os.toByteArray());
        
        System.out.println("Before:");
        System.out.println(before);
        System.out.println();
        System.out.println("After:");
        System.out.println(after);
        
        assertEquals(before, after);
    }
    
    private InputStream getResource() {
        InputStream stream = PropertiesTest.class.getResourceAsStream("example.properties");
        if (stream == null) {
            stream = PropertiesTest.class.getResourceAsStream("/example.properties");
        }
        return stream;
    }
}
