package net.balsoftware.property.component;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.balsoftware.properties.component.time.TimeTransparency;
import net.balsoftware.properties.component.time.TimeTransparency.TimeTransparencyType;

public class TimeTransparencyTest
{
    @Test
    public void canParseStatus()
    {
        String content = "TRANSP:TRANSPARENT";
        TimeTransparency madeProperty = TimeTransparency.parse(content);
        assertEquals(content, madeProperty.toString());
        TimeTransparency expectedProperty = new TimeTransparency(TimeTransparencyType.TRANSPARENT);
        assertEquals(expectedProperty, madeProperty);
        assertEquals(TimeTransparencyType.TRANSPARENT, madeProperty.getValue());
    }
    
    @Test
    public void canParseStatus2()
    {
        String content = "TRANSP:OPAQUE";
        TimeTransparency madeProperty = new TimeTransparency();
        assertEquals(content, madeProperty.toString());
        assertEquals(TimeTransparencyType.OPAQUE, madeProperty.getValue());
    }
}
