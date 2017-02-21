package net.balsoftware.properties.calendar;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.balsoftware.VCalendar;

public class ValidateCalendarTest
{
    @Test
    public void canFindErrorsInEmptyVCalendar()
    {
        VCalendar c = new VCalendar();
        assertEquals(2, c.errors().size());
    }
}
