package net.balsoftware.icalendar.itip;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import net.balsoftware.icalendar.ICalendarStaticComponents;
import net.balsoftware.icalendar.VCalendar;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.properties.calendar.Version;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.RecurrenceRuleValue;

public class CancelRecurrenceTest
{
    @Test // makes sure when recurrence deleted the parent gets an EXDATE
    public void canDeleteRecurrence()
    {
        VCalendar mainVCalendar = new VCalendar();
        
        VEvent vComponentOriginal = ICalendarStaticComponents.getDaily1();
        final List<VEvent> vComponents = new ArrayList<>(Arrays.asList(vComponentOriginal));
        mainVCalendar.setVEvents(vComponents);
        
        String iTIPMessage =
                "BEGIN:VCALENDAR" + System.lineSeparator() +
                "METHOD:CANCEL" + System.lineSeparator() +
                "PRODID:-//Example/ExampleCalendarClient//EN" + System.lineSeparator() + 
                "VERSION:" + Version.DEFAULT_ICALENDAR_SPECIFICATION_VERSION + System.lineSeparator() +
                "BEGIN:VEVENT" + System.lineSeparator() +
                "CATEGORIES:group05" + System.lineSeparator() +
                "DTSTART:20160517T083000" + System.lineSeparator() +
                "DTEND:20160517T093000" + System.lineSeparator() +
                "DESCRIPTION:Daily1 Description" + System.lineSeparator() +
                "SUMMARY:recurrence summary" + System.lineSeparator() +
                "DTSTAMP:20150110T080000Z" + System.lineSeparator() +
                "UID:20150110T080000-004@jfxtras.org" + System.lineSeparator() +
                "ORGANIZER;CN=Papa Smurf:mailto:papa@smurf.org" + System.lineSeparator() +
                "RECURRENCE-ID:20160517T100000" + System.lineSeparator() +
                "STATUS:CANCELLED" + System.lineSeparator() +
                "END:VEVENT" + System.lineSeparator() +
                "END:VCALENDAR";
        mainVCalendar.processITIPMessage(iTIPMessage);
        assertEquals(1, vComponents.size());
        
        VEvent vComponentExpected = ICalendarStaticComponents.getDaily1()
                .withExceptionDates("20160517T100000")
                .withSequence(1);
        assertEquals(vComponentExpected, vComponents.get(0));
    }

    @Test // delete parent with recurrence children (children become orphans and should be removed)
    public void canDeleteAllWithRecurrence()
    {
        VCalendar mainVCalendar = new VCalendar();
        VEvent vComponentOriginal = ICalendarStaticComponents.getDaily1();
        final List<VEvent> vComponents = new ArrayList<>(Arrays.asList(vComponentOriginal));
        mainVCalendar.setVEvents(vComponents);
        
        // make recurrence
        VEvent vComponentRecurrence = ICalendarStaticComponents.getDaily1()
                .withRecurrenceRule((RecurrenceRuleValue) null)
                .withRecurrenceId(LocalDateTime.of(2016, 5, 17, 10, 0))
                .withSummary("recurrence summary")
                .withDateTimeStart(LocalDateTime.of(2016, 5, 17, 8, 30))
                .withDateTimeEnd(LocalDateTime.of(2016, 5, 17, 9, 30));
        vComponents.add(vComponentRecurrence);
        assertEquals(2, vComponents.size());
        
        String iTIPMessage =
                "BEGIN:VCALENDAR" + System.lineSeparator() +
                "METHOD:CANCEL" + System.lineSeparator() +
                "PRODID:-//Example/ExampleCalendarClient//EN" + System.lineSeparator() + 
                "VERSION:" + Version.DEFAULT_ICALENDAR_SPECIFICATION_VERSION + System.lineSeparator() +
                "BEGIN:VEVENT" + System.lineSeparator() +
                "CATEGORIES:group05" + System.lineSeparator() +
                "DESCRIPTION:Daily1 Description" + System.lineSeparator() +
                "SUMMARY:Daily1 Summary" + System.lineSeparator() +
                "DTSTAMP:20150110T080000Z" + System.lineSeparator() +
                "UID:20150110T080000-004@jfxtras.org" + System.lineSeparator() +
                "ORGANIZER;CN=Papa Smurf:mailto:papa@smurf.org" + System.lineSeparator() +
                "STATUS:CANCELLED" + System.lineSeparator() +
                "END:VEVENT" + System.lineSeparator() +
                "END:VCALENDAR";
        mainVCalendar.processITIPMessage(iTIPMessage);
        assertEquals(0, vComponents.size());
    }
}
