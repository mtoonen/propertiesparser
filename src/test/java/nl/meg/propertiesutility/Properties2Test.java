package nl.meg.propertiesutility;

import static org.mockito.Mockito.spy;
import nl.meg.properties2.Properties2;

public class Properties2Test extends AbstractPropertiesTest<Properties2> {

    @Override
    protected Properties2 getProperties() {
        return spy(new Properties2());
    }
}
