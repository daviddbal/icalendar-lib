package net.balsoftware.parameter.rrule;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.balsoftware.properties.component.recurrence.rrule.Interval;
import net.balsoftware.properties.component.recurrence.rrule.RecurrenceRuleValue;

public class IntervalTest
{
    @Test
    public void canRemoveInterval()
    {
        RecurrenceRuleValue r = RecurrenceRuleValue.parse("FREQ=DAILY;INTERVAL=2");
        r.setInterval((Interval) null);
        String expectedContent = "FREQ=DAILY";
        RecurrenceRuleValue expectedVEvent = RecurrenceRuleValue.parse(expectedContent);
        assertEquals(expectedVEvent, r);
        assertEquals(expectedContent, r.toString());
    }
    
    @Test
    public void canChangeInterval()
    {
        RecurrenceRuleValue r = RecurrenceRuleValue.parse("FREQ=DAILY;INTERVAL=2");
        r.setInterval(new Interval(3));
        String expectedContent = "FREQ=DAILY;INTERVAL=3";
        assertEquals(expectedContent, r.toString());
    }
}
