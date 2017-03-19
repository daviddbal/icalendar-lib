package net.balsoftware.icalendar.properties.component.recurrence.rrule;

import net.balsoftware.icalendar.VChild;
import net.balsoftware.icalendar.VElement;

public interface RRulePart<T> extends VElement, VChild
{
    /**
     * The value of the recurrence rule value part.
     * 
     * For example, in the below part:
     * BYDAY=MO
     * The value is MO
     * 
     * Note: the value's object must have an overridden toString method that complies
     * with iCalendar content line output.
     */
    T getValue();
  
    /** Set the value of this parameter */  
    void setValue(T value);
}
