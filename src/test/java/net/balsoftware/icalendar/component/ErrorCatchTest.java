package net.balsoftware.icalendar.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import net.balsoftware.icalendar.VElement;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.properties.component.recurrence.ExceptionDates;
import net.balsoftware.icalendar.utilities.DateTimeUtilities.DateTimeType;

public class ErrorCatchTest
{
    @Test
    public void canCatchInvalidExDates()
    {
        VEvent e = new VEvent()
                .withDateTimeStart(ZonedDateTime.of(LocalDateTime.of(2016, 2, 7, 12, 30), ZoneId.of("America/Los_Angeles")))
                .withExceptionDates(new ExceptionDates(ZonedDateTime.of(LocalDateTime.of(2016, 2, 10, 12, 30), ZoneId.of("America/Los_Angeles"))))
                .withExceptionDates(new ExceptionDates(LocalDateTime.of(2016, 2, 12, 12, 30))) // invalid - stop processing
                .withExceptionDates(new ExceptionDates(ZonedDateTime.of(LocalDateTime.of(2016, 2, 9, 12, 30), ZoneId.of("America/Los_Angeles"))))
                ;
        String error = "EXDATE: DateTimeType " + DateTimeType.DATE_WITH_LOCAL_TIME + " doesn't match previous recurrence's DateTimeType " + DateTimeType.DATE_WITH_LOCAL_TIME_AND_TIME_ZONE;
        boolean isErrorPresent = e.errors()
        	.stream()
        	.anyMatch(r -> r.equals(error));
        assertTrue(isErrorPresent);
    }
    
    @Test
    public void canCatchParseInvalidExDates()
    {
            String content = "BEGIN:VEVENT" + System.lineSeparator() +
            "DTSTART;TZID=America/Los_Angeles:20160207T123000" + System.lineSeparator() +
            "EXDATE;TZID=America/Los_Angeles:20160210T123000" + System.lineSeparator() +
            "EXDATE:20160212T123000" + System.lineSeparator() + // invalid - ignore
            "EXDATE;TZID=America/Los_Angeles:20160209T123000" + System.lineSeparator() +
            "END:VEVENT";
            VEvent v = VEvent.parse(content);
            
            VEvent expected = new VEvent()
                    .withDateTimeStart(ZonedDateTime.of(LocalDateTime.of(2016, 2, 7, 12, 30), ZoneId.of("America/Los_Angeles")))
                    .withExceptionDates(new ExceptionDates(ZonedDateTime.of(LocalDateTime.of(2016, 2, 10, 12, 30), ZoneId.of("America/Los_Angeles"))))
                    .withExceptionDates(new ExceptionDates(ZonedDateTime.of(LocalDateTime.of(2016, 2, 9, 12, 30), ZoneId.of("America/Los_Angeles"))))
                    ;
            assertEquals(expected, v);
    }
    
    @Test
    public void canCatchParseDuplicateProperty()
    {
            String content = "BEGIN:VEVENT" + System.lineSeparator() +
            "SUMMARY:#1" + System.lineSeparator() +
            "DTSTART;TZID=America/Los_Angeles:20160207T123000" + System.lineSeparator() +
            "SUMMARY:#2" + System.lineSeparator() +
            "END:VEVENT";
            VEvent v = new VEvent();
            boolean useRequestStatus = true;
            Map<VElement, List<String>> e = v.parseContent(content, useRequestStatus);
            e.entrySet().forEach(System.out::println);
            
            VEvent expected = new VEvent()
                    .withSummary("#1")
                    .withDateTimeStart(ZonedDateTime.of(LocalDateTime.of(2016, 2, 7, 12, 30), ZoneId.of("America/Los_Angeles")))
                    .withRequestStatus("2.2;Success; invalid property ignored.  Property can only occur once in a calendar component.  Subsequent property is ignored;SUMMARY:#2")
                    ;
            assertEquals(expected, v);
    }
    
    @Test
    public void canCatchParseWithBadLine()
    {
            String content = "BEGIN:VEVENT" + System.lineSeparator() +
            "SUMMARY:#1" + System.lineSeparator() +
            "X-CUSTOM-PROP:THE DATA" + System.lineSeparator() +
            "IGNORE THIS LINE" + System.lineSeparator() +
            "END:VEVENT";
            VEvent v = new VEvent();
            boolean useRequestStatus = true;
            v.parseContent(content, useRequestStatus);
            
            VEvent expected = new VEvent()
                    .withSummary("#1")
                    .withNonStandard("X-CUSTOM-PROP:THE DATA")
                    .withRequestStatus("2.4;Success; unknown, non-standard property ignored.;IGNORE THIS LINE")
                    ;
            System.out.println(expected);
            System.out.println(v);
            assertEquals(expected, v);
    }
}
