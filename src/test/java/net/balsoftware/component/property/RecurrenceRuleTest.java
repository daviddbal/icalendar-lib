package net.balsoftware.component.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import net.balsoftware.properties.component.recurrence.RecurrenceRule;
import net.balsoftware.properties.component.recurrence.rrule.Count;
import net.balsoftware.properties.component.recurrence.rrule.FrequencyType;
import net.balsoftware.properties.component.recurrence.rrule.Interval;
import net.balsoftware.properties.component.recurrence.rrule.RecurrenceRuleValue;
import net.balsoftware.properties.component.recurrence.rrule.Until;
import net.balsoftware.properties.component.recurrence.rrule.WeekStart;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByDay;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByMonth;
import net.balsoftware.utilities.ICalendarUtilities;
import net.balsoftware.utilities.Pair;

public class RecurrenceRuleTest
{    
    @Test
    public void canParseRRuleProperty()
    {
        String contentLine = "RRULE:FREQ=DAILY;UNTIL=20160417T235959Z;INTERVAL=2";
        List<Pair<String, String>> valueList = ICalendarUtilities.contentToParameterListPair(contentLine);
        List<Pair<String, String>> expectedList = new ArrayList<>();
        expectedList.add(new Pair<>(ICalendarUtilities.PROPERTY_VALUE_KEY, "FREQ=DAILY;UNTIL=20160417T235959Z;INTERVAL=2"));
        assertEquals(expectedList, valueList);
    }
    
    @Test
    public void canParseRRuleProperty2()
    {
        String contentLine = "FREQ=DAILY;UNTIL=20160417T235959Z;INTERVAL=2";        
        List<Pair<String, String>> valueList = ICalendarUtilities.contentToParameterListPair(contentLine);
        List<Pair<String, String>> expectedList = new ArrayList<>();
        expectedList.add(new Pair<>("FREQ", "DAILY"));
        expectedList.add(new Pair<>("UNTIL", "20160417T235959Z"));
        expectedList.add(new Pair<>("INTERVAL", "2"));
        assertEquals(expectedList, valueList);
    }
    
    @Test
    public void canParseRecurrenceRule()
    {
        String content = "RRULE:FREQ=YEARLY;UNTIL=19730429T070000Z;BYMONTH=4;BYDAY=-1SU";
        RecurrenceRule madeProperty = RecurrenceRule.parse(content);
        assertEquals(content, madeProperty.toString());
        RecurrenceRule expectedProperty = new RecurrenceRule(
                new RecurrenceRuleValue()
                    .withFrequency(FrequencyType.YEARLY)
                    .withUntil("19730429T070000Z")
                    .withByRules(new ByMonth(Month.APRIL),
                                new ByDay(new ByDay.ByDayPair(DayOfWeek.SUNDAY, -1))));
        assertEquals(content, madeProperty.toString());
        assertEquals(expectedProperty, madeProperty);
    }
    
    @Test // different ordering of rule parts
    public void canParseRecurrenceRule2()
    {
        String content = "RRULE:BYDAY=-1SU;UNTIL=19730429T070000Z;BYMONTH=4;FREQ=YEARLY";
        RecurrenceRule madeProperty = RecurrenceRule.parse(content);
        RecurrenceRule expectedProperty = new RecurrenceRule(
                new RecurrenceRuleValue()
                    .withByRule(new ByDay(new ByDay.ByDayPair(DayOfWeek.SUNDAY, -1)))
                    .withUntil("19730429T070000Z")
                    .withByRule(new ByMonth(Month.APRIL))
                    .withFrequency(FrequencyType.YEARLY));
        System.out.println(madeProperty);
        System.out.println(expectedProperty);
        assertEquals(content, madeProperty.toString());
        assertEquals(expectedProperty, madeProperty);
    }
    
    @Test
    public void canCopyRecurrenceRule()
    {
        String content = "RRULE:UNTIL=19730429T070000Z;FREQ=YEARLY;BYMONTH=4;BYDAY=-1SU";
        RecurrenceRule r1 = RecurrenceRule.parse(content);
        RecurrenceRule r2 = new RecurrenceRule(r1);
        assertEquals(r1, r2);
        assertTrue(r1 != r2);
        assertTrue(r1.equals(r2));
        assertEquals(content, r2.toString());
        assertTrue(r1.getValue() != r2.getValue());
    }
    
    /*
     * TEST RECURRENCE RULE ELEMENTS
     */
    
    @Test
    public void canParseUntil()
    {
        String content = "19730429T070000Z";
        Until element = Until.parse(content);
        ZonedDateTime t = ZonedDateTime.of(LocalDateTime.of(1973, 4, 29, 7, 0), ZoneId.of("Z"));
        assertEquals(t, element.getValue());
        assertEquals("UNTIL=19730429T070000Z", element.toString());
    }
    
    @Test (expected = DateTimeException.class)
    public void canCatchWrongUntil()
    {
        Thread.currentThread().setUncaughtExceptionHandler((t1, e) ->
        {
            throw (RuntimeException) e;
        });
        String content = "19730429T070000";
        Until.parse(content);
    }
    
    @Test
    public void canParseCount()
    {
        Count element = Count.parse("2");
        assertEquals((Integer) 2, element.getValue());
        assertEquals("COUNT=2", element.toString());
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void canCatchNegativeCount()
    {
        Thread.currentThread().setUncaughtExceptionHandler((t1, e) ->
        {
            throw (RuntimeException) e;
        });
        Count element = new Count(5);
        assertEquals((Integer) 5, element.getValue());
        element.setValue(0);
        assertEquals("COUNT=5", element.toString());
    }
    
    @Test
    public void canParseInterval()
    {
        Interval element = Interval.parse("2");
        assertEquals((Integer) 2, element.getValue());
        assertEquals("INTERVAL=2", element.toString());
    }

    @Test
    public void canParseWeekStart()
    {
        WeekStart element = new WeekStart().withValue("SU");
        assertEquals(DayOfWeek.SUNDAY, element.getValue());
        assertEquals("WKST=SU", element.toString());
    }
    
    @Test
    public void canDetectErrors1()
    {
        RecurrenceRule expectedProperty = new RecurrenceRule(
                new RecurrenceRuleValue()
                    .withUntil("19730429T070000Z")
                    .withFrequency(FrequencyType.YEARLY)
                    .withByRules(new ByDay()));
        assertEquals(1, expectedProperty.errors().size());
        String expected = "BYDAY value is null.  The element MUST have a value.";
        assertEquals(expected, expectedProperty.errors().get(0));
    }
    
    @Test
    public void canDetectErrors2()
    {
        RecurrenceRule madeProperty = new RecurrenceRule(
                new RecurrenceRuleValue()
                .withUntil("19730429T070000Z")
                .withByRules(new ByMonth(Month.APRIL),
                            new ByDay(new ByDay.ByDayPair(DayOfWeek.SUNDAY, -1))));
        assertEquals(1, madeProperty.errors().size());
    }
    
    @Test
    public void canRemoveParameter()
    {
        String content = "FREQ=DAILY;UNTIL=20160417T235959Z;INTERVAL=2";
        RecurrenceRule property1 = RecurrenceRule.parse(content);
        property1.getValue().setUntil((Until) null);
        RecurrenceRule expectedProperty = RecurrenceRule.parse("FREQ=DAILY;INTERVAL=2");
        assertEquals(expectedProperty, property1);
    }
    
    @Test
    public void canRemoveParameter2()
    {
        Until until = new Until(ZonedDateTime.of(LocalDateTime.of(2015, 11, 19, 23, 30), ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Z")));
        RecurrenceRule property1 = new RecurrenceRule(new RecurrenceRuleValue()
            .withFrequency(FrequencyType.DAILY)
            .withUntil(until)
            .withInterval(2));
        Temporal until2 = ZonedDateTime.of(LocalDateTime.of(2015, 11, 18, 23, 30), ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Z"));
        property1.getValue().setUntil(until2); // use Temporal setter to test ability to remove reset property
        property1.getValue().setUntil((Until) null);
        RecurrenceRule expectedProperty = RecurrenceRule.parse("FREQ=DAILY;INTERVAL=2");
        assertEquals(expectedProperty, property1);
    }
    
    @Test
    public void canDetectErrors()
    {
        RecurrenceRule r = new RecurrenceRule(); // value can't be null
        List<String> errors = r.errors();
        List<String> expected = Arrays.asList("RRULE value is null.  The property MUST have a value.");
        assertEquals(expected, errors);
    }
}
