package nl.meg.propertiesutility;

import java.io.InputStream;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class PropertiesTest {

    private final Properties properties = spy(new Properties());

    @Before
    public void before() {
        doAnswer(new Answer() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                properties.setProperty("some.super.long.key", "Some text you'll never read !@#$%^&*(*");
                properties.setProperty("parent.child", "val");
                properties.setProperty("simplekey", "simplevalue");
                return null;
            }
        }).when(properties).load(any(InputStream.class));
    }
    
    @Test
    public void testStoreSimpleKeyValue() {
        properties.setProperty("key", "value");
        assertEquals("value", properties.getProperty("key"));
    }

    @Test
    public void testStoreKeyValue() {
        properties.setProperty("parent.child.key", "val");
        assertEquals("val", properties.getProperty("parent.child.key"));
    }

    @Test
    public void testGetDefaultValue() {
        String value = properties.getProperty("not.a.key", "mydefault");
        assertEquals("mydefault", value);
    }

    @Test
    public void testLoad() {
        properties.load(PropertiesTest.class.getResourceAsStream("example.properties"));
        assertEquals("Some text you'll never read !@#$%^&*(*", properties.getProperty("some.super.long.key"));
        assertEquals("val", properties.getProperty("parent.child"));
        assertEquals("simplevalue", properties.getProperty("simplekey"));
        
        verify(properties, times(3)).getProperty(anyString());
    }
}
