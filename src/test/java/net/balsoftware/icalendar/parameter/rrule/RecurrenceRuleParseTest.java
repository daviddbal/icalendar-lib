package net.balsoftware.icalendar.parameter.rrule;

import static org.junit.Assert.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import net.balsoftware.icalendar.properties.component.recurrence.rrule.FrequencyType;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.RecurrenceRuleValue;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByDay;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByDay.ByDayPair;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByMonth;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByMonthDay;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByWeekNumber;

public class RecurrenceRuleParseTest
{
    /** tests parsing FREQ=YEARLY;INTERVAL=2;BYMONTH=1;BYDAY=SU */
    @Test
    public void canParseRRule1()
    {
        String s = "FREQ=YEARLY;INTERVAL=2;BYMONTH=1;BYDAY=SU";
        RecurrenceRuleValue rRule = RecurrenceRuleValue.parse(s);
        RecurrenceRuleValue expectedRRule = new RecurrenceRuleValue()
                .withFrequency(FrequencyType.YEARLY)
                .withInterval(2)
                .withByRules(new ByMonth(Month.JANUARY), new ByDay(DayOfWeek.SUNDAY));
        assertEquals(s, expectedRRule.toString());
        assertEquals(expectedRRule, rRule);
    }
    
    @Test
    public void canParseRRule2()
    {
        String s = "FREQ=MONTHLY;BYDAY=SA;BYMONTHDAY=7,8,9,10,11,12,13";
        RecurrenceRuleValue rRule = RecurrenceRuleValue.parse(s);
        RecurrenceRuleValue expectedRRule = new RecurrenceRuleValue()
                .withFrequency(FrequencyType.MONTHLY)
                .withByRules(new ByDay(DayOfWeek.SATURDAY), new ByMonthDay(7,8,9,10,11,12,13));
        assertEquals(s, expectedRRule.toString());
        assertEquals(s, rRule.toString());
        assertEquals(expectedRRule, rRule);
    }
    
    @Test
    public void canParseRRule3()
    {
        String s = "FREQ=YEARLY;BYWEEKNO=20;BYDAY=2MO,3MO";
        RecurrenceRuleValue rRule = RecurrenceRuleValue.parse(s);
        RecurrenceRuleValue expectedRRule = new RecurrenceRuleValue()
                .withFrequency(FrequencyType.YEARLY)
                .withByRules(new ByWeekNumber(20), new ByDay(new ByDayPair(DayOfWeek.MONDAY, 2), new ByDayPair(DayOfWeek.MONDAY, 3)));
        assertEquals(expectedRRule, rRule);
    }

    @Test
    public void canParseRRule4()
    {
        String s = "UNTIL=20151201T100000Z;INTERVAL=2;FREQ=DAILY";
        RecurrenceRuleValue rRule = RecurrenceRuleValue.parse(s);
        RecurrenceRuleValue expectedRRule = new RecurrenceRuleValue()
                .withUntil(ZonedDateTime.of(LocalDateTime.of(2015, 12, 1, 10, 0),ZoneId.of("Z")))
                .withInterval(2)
                .withFrequency(FrequencyType.DAILY);
        assertEquals(expectedRRule, rRule);
    }
}
