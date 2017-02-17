package net.balsoftware.parameter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.balsoftware.parameters.CalendarUser;
import net.balsoftware.parameters.CalendarUser.CalendarUserType;

public class CalendarUserTest
{
    @Test // tests String as value
    public void canParseCalendarUser()
    {
        CalendarUser parameter = CalendarUser.parse("INDIVIDUAL");
        String expectedContent = "CUTYPE=INDIVIDUAL";
        assertEquals(expectedContent, parameter.toString());
    }
    
    @Test // tests String as value
    public void canParseNonStandardCalendarUser()
    {
        CalendarUser parameter = CalendarUser.parse("X-CLAN");
        String expectedContent = "CUTYPE=X-CLAN";
        assertEquals(expectedContent, parameter.toString());
        assertEquals(CalendarUserType.UNKNOWN, parameter.getValue());
    }
}
