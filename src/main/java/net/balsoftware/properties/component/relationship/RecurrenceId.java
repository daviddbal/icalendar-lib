package net.balsoftware.properties.component.relationship;

import java.time.temporal.Temporal;

import net.balsoftware.components.VEvent;
import net.balsoftware.components.VJournal;
import net.balsoftware.components.VTodo;
import net.balsoftware.parameters.Range;
import net.balsoftware.parameters.Range.RangeType;
import net.balsoftware.properties.PropBaseDateTime;
import net.balsoftware.properties.PropRecurrenceID;

/**
 * RECURRENCE-ID
 * RFC 5545, 3.8.4.4, page 112
 * 
 * This property is used in conjunction with the "UID" and
 * "SEQUENCE" properties to identify a specific instance of a
 * recurring "VEVENT", "VTODO", or "VJOURNAL" calendar component.
 * The property value is the original value of the "DTSTART" property
 * of the recurrence instance.
 * 
 * The "RANGE" parameter is used to specify the effective range of
 * recurrence instances from the instance specified by the
 * "RECURRENCE-ID" property value.  The value for the range parameter
 * can only be "THISANDFUTURE"  Note: THISANDFUTURE is not supported by
 * most iCalendar implementations.  It may be better to truncate the
 * unbounded recurring calendar component (i.e., with the "COUNT"
 * or "UNTIL" rule parts), and create two new unbounded recurring
 * calendar components for the future instances.
 * 
 * Example:
 * RECURRENCE-ID;VALUE=DATE:19960401
 * 
 * @author David Bal
 * @see VEvent
 * @see VTodo
 * @see VJournal
 */
public class RecurrenceId extends PropBaseDateTime<Temporal, RecurrenceId> implements PropRecurrenceID<Temporal>
{
    /**
     * RANGE
     * Recurrence Identifier Range
     * RFC 5545, 3.2.13, page 23
     * 
     * To specify the effective range of recurrence instances from
     *  the instance specified by the recurrence identifier specified by
     *  the property.
     * 
     * Example:
     * RECURRENCE-ID;RANGE=THISANDFUTURE:19980401T133000Z
     * 
     * @author David Bal
     *
     */
    @Override
    public Range getRange() { return range; }
    private Range range;
    @Override
    public void setRange(Range range)
    {
    	orderer.orderChild(this.range, range);
    	this.range = range;
	}
    public void setRange(String value) { setRange(new Range(value)); }
    public RecurrenceId withRange(Range altrep) { setRange(altrep); return this; }
    public RecurrenceId withRange(RangeType value) { setRange(new Range(value)); return this; }
    public RecurrenceId withRange(String content) { setRange(content); return this; }

    /*
     * CONSTRUCTORS
     */
   public RecurrenceId(Temporal temporal)
    {
        super(temporal);
    }
    
    public RecurrenceId(RecurrenceId source)
    {
        super(source);
    }
    
    public RecurrenceId()
    {
        super();
    }
    
    /** Parse string to Temporal.  Not type safe.  Implementation must
     * ensure parameterized type is the same as date-time represented by String parameter */
    public static RecurrenceId parse(String value)
    {
        RecurrenceId property = new RecurrenceId();
        property.parseContent(value);
        return property;
    }
    
    /** Parse string with Temporal class explicitly provided as parameter */
    public static RecurrenceId parse(Class<? extends Temporal> clazz, String value)
    {
        RecurrenceId property = new RecurrenceId();
        property.parseContent(value);
        clazz.cast(property.getValue()); // class check
        return property;
    }
}
