package net.balsoftware.icalendar.calendar;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.balsoftware.icalendar.VCalendar;

public class ValidateCalendarTest
{
    @Test
    public void canFindErrorsInEmptyVCalendar()
    {
        VCalendar c = new VCalendar();
        assertEquals(2, c.errors().size());
    }
}
