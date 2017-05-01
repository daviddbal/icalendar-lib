package net.balsoftware.icalendar.parameter.rrule;

import static org.junit.Assert.assertEquals;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import net.balsoftware.icalendar.properties.component.recurrence.rrule.FrequencyType;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.RecurrenceRuleValue;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByDay;

public class ByRuleTest
{
    @Test
    public void canParseByDay()
    {
    	ByDay b = new ByDay(DayOfWeek.FRIDAY);
    	RecurrenceRuleValue r = new RecurrenceRuleValue()
        	.withFrequency(FrequencyType.DAILY)
        	.withByRules(b);
    	assertEquals(new HashSet<>(Arrays.asList(b)), r.getByRules());
    	System.out.println(r);
    }
}
