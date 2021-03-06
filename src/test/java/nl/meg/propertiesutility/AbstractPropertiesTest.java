package nl.meg.propertiesutility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractPropertiesTest<T extends Propertable> {

    protected T properties;

    @Before
    public void before() throws IOException {
        properties = getProperties();
    }

    protected abstract T getProperties();

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
        properties.load(getResourceStream());
        assertEquals("Some text you'll never read !@#$%^&*(*\"", properties.getProperty("some.super.long.key"));
        assertEquals("val", properties.getProperty("parent.child"));
        assertEquals("simplevalue", properties.getProperty("simplekey"));
        assertEquals("noot", properties.getProperty("aap"));
        assertEquals("mies", properties.getProperty("jet"));
        verify(properties, times(5)).getProperty(anyString());
    }

    @Test
    public void testLoadReader() throws IOException, URISyntaxException {
        Reader reader = spy(getResourceReader());
        properties.load(reader);
        assertEquals("Some text you'll never read !@#$%^&*(*\"", properties.getProperty("some.super.long.key"));
        assertEquals("val", properties.getProperty("parent.child"));
        assertEquals("simplevalue", properties.getProperty("simplekey"));
        assertEquals("noot", properties.getProperty("aap"));
        assertEquals("mies", properties.getProperty("jet"));
        verify(properties, times(5)).getProperty(anyString());
    }

    @Test
    public void testLoadJavaProperties() throws IOException {
        java.util.Properties properties = spy(new java.util.Properties());
        properties.load(getResourceStream());
        assertEquals("Some text you'll never read !@#$%^&*(*\"", properties.getProperty("some.super.long.key"));
        assertEquals("val", properties.getProperty("parent.child"));
        assertEquals("simplevalue", properties.getProperty("simplekey"));
        assertEquals("noot", properties.getProperty("aap"));
        assertEquals("mies", properties.getProperty("jet"));

        verify(properties, times(5)).getProperty(anyString());
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
        IOUtils.copy(getResourceStream(), writer, "UTF-8");
        String before = writer.toString();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        InputStream resource = spy(getResourceStream());
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
        IOUtils.copy(getResourceStream(), writer, "UTF-8");
        String before = writer.toString();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Reader reader = getResourceReader();
        properties.load(reader);
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
    public void testUpdateExistingProperty() throws FileNotFoundException, URISyntaxException, IOException{
        Reader reader = spy(getResourceReader());
        properties.load(reader);
        assertEquals("simplevalue",properties.getProperty("simplekey"));
        properties.setProperty("simplekey", "hardervalue");
        assertEquals("hardervalue",properties.getProperty("simplekey"));
        
        
        assertEquals("noot",properties.getProperty("aap"));
        properties.setProperty("aap", " peul");
        assertEquals("peul",properties.getProperty("aap"));
        
    }
    

    private Reader getResourceReader() throws FileNotFoundException, URISyntaxException {
        URL url = AbstractPropertiesTest.class.getResource("example.properties");
        URI uri = url.toURI();
        FileReader fr = new FileReader(new File(uri));
        return fr;
    }

    private InputStream getResourceStream() {
        InputStream stream = AbstractPropertiesTest.class.getResourceAsStream("example.properties");
        if (stream == null) {
            stream = AbstractPropertiesTest.class.getResourceAsStream("/example.properties");
        }
        return stream;
    }
}
