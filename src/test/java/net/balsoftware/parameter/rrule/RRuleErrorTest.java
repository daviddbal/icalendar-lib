package net.balsoftware.parameter.rrule;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.balsoftware.properties.component.recurrence.rrule.FrequencyType;
import net.balsoftware.properties.component.recurrence.rrule.RecurrenceRuleValue;

public class RRuleErrorTest
{
    @Test
    public void canDetectIntervalError()
    {
        RecurrenceRuleValue rrule = new RecurrenceRuleValue()
                .withFrequency(FrequencyType.YEARLY)
                .withInterval(0); // invalid
        assertEquals(1, rrule.errors().size());
    }

    @Test
    public void canDetectMissingFrequency()
    {
        RecurrenceRuleValue rrule = new RecurrenceRuleValue();
        assertEquals(1, rrule.errors().size());
    }
    
    @Test
    public void canDetectCountAndUntilFrequency()
    {
        RecurrenceRuleValue rrule = new RecurrenceRuleValue()
                .withFrequency(FrequencyType.YEARLY)
                .withCount(10)
                .withUntil("19970610T172345Z"); // invalid
        assertEquals(1, rrule.errors().size());
    }
}
