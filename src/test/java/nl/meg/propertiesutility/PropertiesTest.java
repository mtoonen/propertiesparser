package nl.meg.propertiesutility;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public class PropertiesTest {

    private Properties properties = new Properties();

    @Test
    public void storeSimpleKeyValue() {
        properties.setProperty("key", "value");
        assertEquals("value", properties.getProperty("key"));
    }
    
        @Test
    public void storeKeyValue() {
        properties.setProperty("parent.child.key", "val");
        assertEquals("val", properties.getProperty("parent.child.key"));
    }
    
    @Test
    public void getDefaultValue() {
        String value = properties.getProperty("not.a.key", "mydefault");
        assertEquals("mydefault", value);
    }
    
    @Test
    public void load() {
        properties.load(PropertiesTest.class.getResourceAsStream("example.properties"));
        
        
        
    }
}
