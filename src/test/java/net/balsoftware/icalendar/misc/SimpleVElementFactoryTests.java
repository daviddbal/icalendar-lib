package net.balsoftware.icalendar.misc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.balsoftware.icalendar.SimpleVElementFactory;
import net.balsoftware.icalendar.VElement;
import net.balsoftware.icalendar.parameters.ValueParameter;
import net.balsoftware.icalendar.properties.ValueType;

public class SimpleVElementFactoryTests
{

    @Test
    public void canMakeParameter()
    {
    	VElement value = SimpleVElementFactory.newElement("VALUE=URI");
    	VElement expectedValue =  new ValueParameter(ValueType.UNIFORM_RESOURCE_IDENTIFIER);
    	assertEquals(expectedValue, value);
    }
}
