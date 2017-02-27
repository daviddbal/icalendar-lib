package net.balsoftware.icalendar.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import net.balsoftware.icalendar.ICalendarTestAbstract;
import net.balsoftware.icalendar.components.VComponent;
import net.balsoftware.icalendar.components.VComponentBase;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.RecurrenceRuleValue;
import net.balsoftware.icalendar.properties.component.time.DateTimeEnd;
import net.balsoftware.icalendar.properties.component.time.TimeTransparency;
import net.balsoftware.icalendar.properties.component.time.TimeTransparency.TimeTransparencyType;

/**
 * Test following components:
 * @see VEvent
 * 
 * for the following properties:
 * @see TimeTransparency
 * 
 * @author David Bal
 *
 */
public class VEventTest extends ICalendarTestAbstract
{
    @Test
    public void canBuildVEvent()
    {
        VEvent builtComponent = new VEvent()
                .withDateTimeEnd(LocalDate.of(1997, 3, 1))
                .withTimeTransparency(TimeTransparencyType.OPAQUE);
        String componentName = builtComponent.name();
        
        String content = "BEGIN:" + componentName + System.lineSeparator() +
                "DTEND;VALUE=DATE:19970301" + System.lineSeparator() +
                "TRANSP:OPAQUE" + System.lineSeparator() +
                "END:" + componentName;
                
        VEvent madeComponent = VEvent.parse(content);
        assertEquals(madeComponent, builtComponent);
        assertEquals(content, builtComponent.toString());
    }
        
    @Test (expected = DateTimeException.class)
    public void canCatchBothDurationAndDTEnd()
    {
        Thread.setDefaultUncaughtExceptionHandler((t1, e) ->
        {
            throw (RuntimeException) e;
        });
        new VEvent()
                .withDateTimeEnd(LocalDate.of(1997, 3, 1))
                .withDuration(Duration.ofMinutes(30));
    }
    
    @Test (expected = DateTimeException.class)
    public void canCatchBothDurationAndDTEnd2()
    {
        Thread.setDefaultUncaughtExceptionHandler((t1, e) ->
        {
            throw (RuntimeException) e;
        });
       new VEvent()
             .withDuration(Duration.ofMinutes(30))
             .withDateTimeEnd(LocalDate.of(1997, 3, 1));
    }
    
    @Test
    public void canChangeDTEndToDuration()
    {
        VEvent builtComponent = new VEvent()
             .withDateTimeEnd(LocalDate.of(1997, 3, 1));
        assertEquals(LocalDate.of(1997, 3, 1), builtComponent.getDateTimeEnd().getValue());
        builtComponent.setDateTimeEnd((DateTimeEnd) null);
        builtComponent.withDateTimeEnd((DateTimeEnd) null).withDuration("PT15M");
        assertEquals(Duration.ofMinutes(15), builtComponent.getDuration().getValue());
        assertNull(builtComponent.getDateTimeEnd());
    }
    
    
    @Test
    public void canStreamWithEnd()
    {
        VEvent e = new VEvent()
                .withDateTimeStart(LocalDateTime.of(2015, 11, 9, 20, 0))
                .withDateTimeEnd(LocalDateTime.of(2015, 11, 10, 2, 0))
                .withRecurrenceRule(new RecurrenceRuleValue()
                        .withCount(6)
                        .withFrequency("DAILY")
                        .withInterval(3));
        List<LocalDateTime> expectedDates = new ArrayList<LocalDateTime>(Arrays.asList(
                LocalDateTime.of(2015, 11, 15, 20, 0)
              , LocalDateTime.of(2015, 11, 18, 20, 0)
              , LocalDateTime.of(2015, 11, 21, 20, 0)
              , LocalDateTime.of(2015, 11, 24, 20, 0)
                ));
        List<Temporal> madeDates = e.streamRecurrences(LocalDateTime.of(2015, 11, 15, 22, 0))
               .collect(Collectors.toList());
        assertEquals(expectedDates, madeDates);
    }
    
    @Test
    public void canStreamWithRange()
    {
        VEvent e = new VEvent()
                .withDateTimeStart(LocalDateTime.of(2015, 11, 9, 20, 0))
                .withDuration(Duration.ofHours(6))
                .withRecurrenceRule(new RecurrenceRuleValue()
                        .withFrequency("DAILY")
                        .withInterval(3));
        List<LocalDateTime> expectedDates = new ArrayList<LocalDateTime>(Arrays.asList(
                LocalDateTime.of(2015, 11, 15, 20, 0)
              , LocalDateTime.of(2015, 11, 18, 20, 0)
              , LocalDateTime.of(2015, 11, 21, 20, 0)
                ));
        List<Temporal> madeDates = e.streamRecurrences(LocalDateTime.of(2015, 11, 14, 20, 0), 
                                                           LocalDateTime.of(2015, 11, 22, 0, 0))
               .collect(Collectors.toList());
        assertEquals(expectedDates, madeDates);
    }

    /** use {@link VComponentBase#copyComponentFrom} */
    @Test
    public void canCopyComponent()
    {
        VEvent e = getYearly1();
        VEvent e2 = new VEvent();
        e.copyInto(e2);
        assertEquals(e, e2);
    }

    // Use copy constructor
    @Test
    public void canCopyComponent2()
    {
        VEvent e = getYearly1();
        VEvent e2 = new VEvent(e);
        assertEquals(e, e2);
    }
    
    @Test // use reflection
    public void canCopyComponent3()
    {
        VComponent e = getYearly1();
        try
        {
            VComponent e2 = e.getClass().newInstance();
            e.copyInto(e2);
            assertEquals(e, e2);
            assertFalse(e == e2);
        } catch (InstantiationException | IllegalAccessException e1)
        {
            e1.printStackTrace();
        }
    }
    
    @Test
    public void canChangeLocalDateToLocalDateTime()
    {
        VEvent vEvent = new VEvent()
                .withDateTimeEnd(LocalDate.of(2016, 3, 7))
                .withDateTimeStart(LocalDate.of(2016, 3, 6));
        vEvent.setDateTimeStart(LocalDateTime.of(2016, 3, 6, 4, 30));// NOTE: this allows a temporary invalid state
        String expectedError = "DTEND value type (DATE) must be the same value type as DTSTART (DATE_WITH_LOCAL_TIME)";
        boolean errorFound = vEvent.errors().stream().anyMatch(s -> s.equals(expectedError));
        assertTrue(errorFound);
    }

    @Test
    public void canCatchTooEarlyDTEND()
    {
        VEvent vEvent = new VEvent()
                .withDateTimeEnd(LocalDate.of(2016, 3, 5))
                .withDateTimeStart(LocalDate.of(2016, 3, 6)); // makes DTEND too early
        String expectedError = "DTEND is not after DTSTART.  DTEND MUST be after DTSTART (2016-03-05, 2016-03-06)";
        boolean errorFound = vEvent.errors().stream().anyMatch(s -> s.equals(expectedError));
        assertTrue(errorFound);
    }
    
    @Test (expected = DateTimeException.class)
    public void canCatchTooEarlyDTEND2()
    {
        Thread.setDefaultUncaughtExceptionHandler((t1, e) ->
        {
            throw (RuntimeException) e;
        });
        VEvent vEvent = new VEvent()
                .withDateTimeStart(LocalDate.of(2016, 3, 6));
        vEvent.setDateTimeEnd(LocalDate.of(2016, 3, 5)); // can't set an invalid DTEND
        assertNull(vEvent.getDateTimeEnd());
    }
    
    @Test (expected = DateTimeException.class)
    public void canCatchWrongDTENDType()
    {
        Thread.setDefaultUncaughtExceptionHandler((t1, e) ->
        {
            throw (RuntimeException) e;
        });
        VEvent vEvent = new VEvent()
                .withDateTimeStart(LocalDate.of(2016, 3, 6));
        vEvent.setDateTimeEnd(LocalDateTime.of(2016, 3, 6, 4, 30));
    }
    
}