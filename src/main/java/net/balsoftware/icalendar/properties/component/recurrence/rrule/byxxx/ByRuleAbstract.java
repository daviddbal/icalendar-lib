package net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import net.balsoftware.icalendar.properties.component.recurrence.rrule.RRuleElement;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.RRulePartBase;

/**
 * BYxxx rule that modify frequency rule (see RFC 5545, iCalendar 3.3.10 Page 42)
 * The BYxxx rules must be applied in a specific order
 * 
 * @author David Bal
 * @see ByMonth
 * @see ByWeekNumber
 * @see ByYearDay
 * @see ByMonthDay
 * @see ByDay
 * @see ByHour
 * @see ByMinute
 * @see BySecond
 * @see BySetPosition
 */
public abstract class ByRuleAbstract<T, U> extends RRulePartBase<List<T>, U> implements ByRule<List<T>>
{
    @Override
    public void setValue(List<T> values)
    {
        super.setValue(values);
    }
    public void setValue(T... values)
    {
        setValue(Arrays.asList(values));
    }
    public void setValue(String values)
    {
        parseContent(values);
    }
    public U withValue(T... values)
    {
        setValue(values);
        return (U) this;
    }
    public U withValue(String values)
    {
        setValue(values);
        return (U) this;
    }
    

    @Override
    public Stream<Temporal> streamRecurrences(Stream<Temporal> inStream, ChronoUnit chronoUnit, Temporal dateTimeStart) { throw new RuntimeException("not implemented"); }
    
    /*
     * Constructors
     */
    
    ByRuleAbstract()
    {
        super();
    }

    ByRuleAbstract(T... values)
    {
        setValue(values);
    }
    
    // Copy constructor
    ByRuleAbstract(ByRuleAbstract<T, U> source)
    {
        setValue(new ArrayList<>(source.getValue()));
    }

    @Override
    public int compareTo(ByRule<List<T>> byRule)
    {
        int p1 = RRuleElement.fromClass(getClass()).sortOrder();
        int p2 = RRuleElement.fromClass(byRule.getClass()).sortOrder();
        return Integer.compare(p1, p2);
    }
    
    @Override
    public List<String> errors()
    {
        List<String> errors = super.errors();
        if ((getValue() != null) && (getValue().isEmpty()))
        {
            errors.add(name() + " value list is empty.  List MUST have at lease one element."); 
        }

        return errors;
    }
}
