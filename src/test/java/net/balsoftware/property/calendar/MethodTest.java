package net.balsoftware.property.calendar;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.balsoftware.properties.calendar.Method;
import net.balsoftware.properties.calendar.Method.MethodType;

public class MethodTest
{
    @Test
    public void canParseAction()
    {
        Method madeProperty = Method.parse("method:publish");
        String expectedContent = "METHOD:PUBLISH";
        assertEquals(expectedContent, madeProperty.toString());
        assertEquals(MethodType.PUBLISH, madeProperty.getValue());
    }
}
