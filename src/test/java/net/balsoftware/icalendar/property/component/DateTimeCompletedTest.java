package net.balsoftware.icalendar.property.component;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import net.balsoftware.icalendar.properties.component.time.DateTimeCompleted;

public class DateTimeCompletedTest
{
    @Test
    public void canParseDateTimeCompleted()
    {
        DateTimeCompleted property = DateTimeCompleted.parse("COMPLETED:20160322T174422Z");
        String expectedContentLine = "COMPLETED:20160322T174422Z";
        String madeContentLine = property.toString();
        assertEquals(expectedContentLine, madeContentLine);
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2016, 3, 22, 17, 44, 22), ZoneId.of("Z")), property.getValue());
    }
}
