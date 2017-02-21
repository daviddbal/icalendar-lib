package net.balsoftware.properties.component.recurrence;

import net.balsoftware.components.DaylightSavingTime;
import net.balsoftware.components.StandardTime;
import net.balsoftware.components.VEvent;
import net.balsoftware.components.VJournal;
import net.balsoftware.components.VTodo;
import net.balsoftware.properties.PropertyBase;
import net.balsoftware.properties.component.recurrence.rrule.RecurrenceRuleValue;

/**
 * RRULE
 * Recurrence Rule
 * RFC 5545 iCalendar 3.8.5.3, page 122.
 * 
 * This property defines a rule or repeating pattern for
 * recurring events, to-dos, journal entries, or time zone definitions.
 * 
 * Produces a stream of start date/times after applying all modification rules.
 * 
 * @author David Bal
 * @see VEvent
 * @see VTodo
 * @see VJournal
 * @see DaylightSavingTime
 * @see StandardTime
 */
public class RecurrenceRule extends PropertyBase<RecurrenceRuleValue, RecurrenceRule>
{
    public RecurrenceRule(RecurrenceRuleValue value)
    {
        super(value);
    }
    
    public RecurrenceRule()
    {
        super();
    }

    public RecurrenceRule(RecurrenceRule source)
    {
        super(source);
    }

    public static RecurrenceRule parse(String propertyContent)
    {
        RecurrenceRule property = new RecurrenceRule();
        property.parseContent(propertyContent);
        return property;
    }
    
    @Override
    protected RecurrenceRuleValue copyValue(RecurrenceRuleValue source)
    {
        return new RecurrenceRuleValue(source);
    }
}
