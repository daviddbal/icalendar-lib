package net.balsoftware.properties.calendar;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.balsoftware.properties.calendar.CalendarScale;
import net.balsoftware.properties.calendar.CalendarScale.CalendarScaleType;

public class CalendarScaleTest
{
    @Test
    public void canParseCalendarScale()
    {
        CalendarScale property = new CalendarScale(CalendarScaleType.GREGORIAN);
        String expectedContent = "CALSCALE:GREGORIAN";
        assertEquals(expectedContent, property.toContent());
        CalendarScale property2 = CalendarScale.parse(expectedContent);
        assertEquals(property, property2);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void canCatchInvalidCalendarScale()
    {
        CalendarScale.parse("INVALID");;
    }
}
