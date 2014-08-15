package nl.meg.propertiesutility;

import static org.mockito.Mockito.spy;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PropertiesTest extends AbstractPropertiesTest<Properties> {

    @Override
    protected Properties getProperties() {
        return spy(new Properties());
    }

    @Test
    public void testGetType() {
        assertEquals(Properties.LineType.COMMENT, properties.getType("#aapnootmies"));
        assertEquals(Properties.LineType.COMMENT, properties.getType("##aapnootmies"));
        assertEquals(Properties.LineType.COMMENT, properties.getType("  #aapnootmies"));

        assertEquals(Properties.LineType.EMPTY, properties.getType(""));
        assertEquals(Properties.LineType.EMPTY, properties.getType(System.getProperty("line.separator")));
        assertEquals(Properties.LineType.EMPTY, properties.getType("" + System.getProperty("line.separator")));

        assertEquals(Properties.LineType.PROPERTY, properties.getType("aap=noot"));
        assertEquals(Properties.LineType.PROPERTY, properties.getType("   aap=noot"));
        assertEquals(Properties.LineType.PROPERTY, properties.getType("aap = noot"));
    }
}
