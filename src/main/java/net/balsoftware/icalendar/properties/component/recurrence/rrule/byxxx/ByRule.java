package net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.stream.Stream;

import net.balsoftware.icalendar.properties.component.recurrence.rrule.RRulePart;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByDay;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByHour;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByMinute;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByMonth;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByMonthDay;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByRule;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByRuleAbstract;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.BySecond;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.BySetPosition;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByWeekNumber;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByYearDay;

/**
 * Interface for a rule that applies a modification to a Stream of start date/times, such
 * as BYxxx rules, in a recurring event (RRULE).
 * 
 * @author David Bal
 * @see ByRuleAbstract
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
public interface ByRule<T> extends Comparable<ByRule<T>>, RRulePart<T>
{
    /** 
     * New stream of date/times made after applying rule that either filters out some date/times
     * or adds additional date/times.
     *  
     * @param inStream - Current stream to be added to or subtracted from
     * @param chronoUnit - ChronoUnit of last modification to inStream
     * @param startTemporal - start Temporal (date or date/time)
     * @return
     */
    Stream<Temporal> streamRecurrences(Stream<Temporal> inStream, ChronoUnit chronoUnit, Temporal dateTimeStart);
}
