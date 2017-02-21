package net.balsoftware.components;

import java.time.temporal.Temporal;
import java.util.List;
import java.util.stream.Stream;

import net.balsoftware.properties.component.recurrence.RecurrenceDates;
import net.balsoftware.properties.component.recurrence.RecurrenceRule;
import net.balsoftware.properties.component.recurrence.RecurrenceRuleCache;
/**
 * Contains following properties:
 * @see RecurrenceRule
 * @see RecurrenceDates
 * 
 * @author David Bal
 *
 * @param <T> - concrete subclass
 * @see DaylightSavingTime
 * @see StandardTime
 */
public abstract class VRepeatableBase<T> extends VPrimary<T> implements VRepeatable<T>
{
    /**
     * RDATE
     * Recurrence Date-Times
     * RFC 5545 iCalendar 3.8.5.2, page 120.
     * 
     * This property defines the list of DATE-TIME values for
     * recurring events, to-dos, journal entries, or time zone definitions.
     * 
     * NOTE: DOESN'T CURRENTLY SUPPORT PERIOD VALUE TYPE
     * */
    @Override
    public List<RecurrenceDates> getRecurrenceDates() { return recurrenceDates; }
    private List<RecurrenceDates> recurrenceDates;
    @Override
    public void setRecurrenceDates(List<RecurrenceDates> recurrenceDates) { this.recurrenceDates = recurrenceDates; }

    /**
     * RRULE, Recurrence Rule
     * RFC 5545 iCalendar 3.8.5.3, page 122.
     * This property defines a rule or repeating pattern for recurring events, 
     * to-dos, journal entries, or time zone definitions
     * If component is not repeating the value is null.
     * 
     * Examples:
     * RRULE:FREQ=DAILY;COUNT=10
     * RRULE:FREQ=WEEKLY;UNTIL=19971007T000000Z;WKST=SU;BYDAY=TU,TH
     */
    @Override
    public RecurrenceRule getRecurrenceRule() { return recurrenceRule; }
    private RecurrenceRule recurrenceRule;
	@Override
	public void setRecurrenceRule(RecurrenceRule recurrenceRule) { this.recurrenceRule = recurrenceRule; }

    /*
     * CONSTRUCTORS
     */
    VRepeatableBase() { }
    
    public VRepeatableBase(StandardOrDaylight<T> source)
    {
        super(source);
    }

    @Override
    public Stream<Temporal> streamRecurrences(Temporal start)
    {
        Stream<Temporal> inStream = VRepeatable.super.streamRecurrences(start);
        if (getRecurrenceRule() == null)
        {
            return inStream; // no cache is no recurrence rule
        }
        return recurrenceCache().makeCache(inStream);   // make cache of start date/times
    }
    
    @Override
    public List<String> errors()
    {
        return VRepeatable.errorsRepeatable(this);
    }

    /*
     *  RECURRENCE STREAMER
     *  produces recurrence set
     */
    private RecurrenceRuleCache streamer = new RecurrenceRuleCache(this);
    @Override
    public RecurrenceRuleCache recurrenceCache() { return streamer; }
    
}
