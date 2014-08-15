package nl.meg.propertiesutility;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public class PropertiesTest {

    @Test
    public void iets() {
        Properties properties = new Properties();
        properties.setProperty("key", "value");

        assertEquals("value", properties.getProperty("key"));
    }

}
