package net.balsoftware.properties.component.recurrence.rrule;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import net.balsoftware.VChild;
import net.balsoftware.VParent;
import net.balsoftware.VParentBase;
import net.balsoftware.properties.component.recurrence.RecurrenceRule;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByDay;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByHour;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByMinute;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByMonth;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByMonthDay;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByRule;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.BySecond;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByYearDay;
import net.balsoftware.utilities.DateTimeUtilities;
import net.balsoftware.utilities.DateTimeUtilities.DateTimeType;
import net.balsoftware.utilities.ICalendarUtilities;

/**
 * RRULE
 * Recurrence Rule
 * RFC 5545 iCalendar 3.3.10 page 38
 * 
 * Contains the following Recurrence Rule elements:
 * COUNT
 * UNTIL
 * FREQUENCY
 * INTERVAL
 * BYxxx RULES in a List
 * 
 * The value part of the recurrence rule.  It supports the following elements: <br>
 * ( "FREQ" "=" freq ) <br>
 * ( "UNTIL" "=" enddate ) <br>
 * ( "COUNT" "=" 1*DIGIT ) <br>
 * ( "INTERVAL" "=" 1*DIGIT ) <br>
 * ( "BYSECOND" "=" byseclist ) <br>
 * ( "BYMINUTE" "=" byminlist ) <br>
 * ( "BYHOUR" "=" byhrlist ) <br>
 * ( "BYDAY" "=" bywdaylist ) <br>
 * ( "BYMONTHDAY" "=" bymodaylist ) <br>
 * ( "BYYEARDAY" "=" byyrdaylist ) <br>
 * ( "BYWEEKNO" "=" bywknolist ) <br>
 * ( "BYMONTH" "=" bymolist ) <br>
 * ( "BYSETPOS" "=" bysplist ) <br>
 * ( "WKST" "=" weekday ) <br>
 * 
 * In addition to methods to support iCalendar recurrence rule parts, there is a method
 * {@link #streamRecurrences(Temporal)}  that produces a stream of start date/times for the recurrences
 * defined by the rule.
 * 
 * @author David Bal
 * @see RecurrenceRule
 *
 */
// TODO - LISTENER TO PREVENT COUNT AND UNTIL FROM BOTH BEING SET
public class RecurrenceRuleValue extends VParentBase implements VChild
{
    private VParent myParent;
    @Override public void setParent(VParent parent) { myParent = parent; }
    @Override public VParent getParent() { return myParent; }
    
    private static final String NAME = "RRULE";
    @Override public String name() { return NAME; }
    
    /** 
     * BYxxx Rules
     * RFC 5545, iCalendar 3.3.10 Page 42
     * 
     * List contains any of the following.  The following list also indicates the processing order:
     * {@link ByMonth} {@link ByWeekNo} {@link ByYearDay} {@link ByMonthDay} {@link ByDay} {@link ByHour}
     * {@link ByMinute} {@link BySecond} {@link BySetPos}
     * 
     * BYxxx rules modify the recurrence set by either expanding or limiting it.
     * 
     * Each BYxxx rule can only occur once
     *  */
    public List<ByRule<?>> byRules() { return byRules; }
    private List<ByRule<?>> byRules;
    public RecurrenceRuleValue withByRules(ByRule<?>...byRules)
    {
        for (ByRule<?> myByRule : byRules)
        {
            byRules().add(myByRule);
        }
        return this;
    }
    public RecurrenceRuleValue withByRules(String...byRules)
    {
        Arrays.stream(byRules).forEach(c -> parseContent(c));
        return this;
    }
    
    /** Return ByRule associated with class type */
    public ByRule<?> lookupByRule(Class<? extends ByRule<?>> byRuleClass)
    {
        Optional<ByRule<?>> rule = byRules()
                .stream()
                .filter(r -> byRuleClass.isInstance(r))
                .findFirst();
        return (rule.isPresent()) ? rule.get() : null;
    }
    
    /**
     * COUNT:
     * RFC 5545 iCalendar 3.3.10, page 41
     * 
     * The COUNT rule part defines the number of occurrences at which to
     * range-bound the recurrence.  The "DTSTART" property value always
     * counts as the first occurrence.
     */
    private Count count;
    public Count getCount() { return count; }
    public void setCount(Count count) { this.count = count; }
    public void setCount(int count) { setCount(new Count(count)); }
    public RecurrenceRuleValue withCount(Count count)
    {
        setCount(count);
        return this;
    }
    public RecurrenceRuleValue withCount(int count)
    {
        setCount(count);
        return this;
    }
    
    /** 
     * FREQUENCY
     * FREQ
     * RFC 5545 iCalendar 3.3.10 p40
     * 
     * required element
     * 
     * The FREQ rule part identifies the type of recurrence rule.  This
     * rule part MUST be specified in the recurrence rule.  Valid values
     * include SECONDLY, to specify repeating events based on an interval
     * of a second or more; MINUTELY, to specify repeating events based
     * on an interval of a minute or more; HOURLY, to specify repeating
     * events based on an interval of an hour or more; DAILY, to specify
     * repeating events based on an interval of a day or more; WEEKLY, to
     * specify repeating events based on an interval of a week or more;
     * MONTHLY, to specify repeating events based on an interval of a
     * month or more; and YEARLY, to specify repeating events based on an
     * interval of a year or more.
     */
    private Frequency frequency;
    public Frequency getFrequency() { return frequency; }
    public void setFrequency(Frequency frequency) { this.frequency = frequency; }
    public void setFrequency(String frequency) { setFrequency(Frequency.parse(frequency)); }
    public void setFrequency(FrequencyType frequency) { setFrequency(new Frequency(frequency)); }
    public RecurrenceRuleValue withFrequency(Frequency frequency)
    {
        setFrequency(frequency);
        return this;
    }
    public RecurrenceRuleValue withFrequency(String frequency)
    {
        setFrequency(frequency);
        return this;
    }
    public RecurrenceRuleValue withFrequency(FrequencyType frequency)
    {
        setFrequency(frequency);
        return this;
    }
    
    /**
     * INTERVAL
     * RFC 5545 iCalendar 3.3.10, page 40
     * 
     * The INTERVAL rule part contains a positive integer representing at
     * which intervals the recurrence rule repeats.  The default value is
     * "1", meaning every second for a SECONDLY rule, every minute for a
     * MINUTELY rule, every hour for an HOURLY rule, every day for a
     * DAILY rule, every week for a WEEKLY rule, every month for a
     * MONTHLY rule, and every year for a YEARLY rule.  For example,
     * within a DAILY rule, a value of "8" means every eight days.
     */
    private Interval interval;
    public Interval getInterval() { return interval; }
    public void setInterval(Interval interval) { this.interval = interval; }
    public void setInterval(Integer interval) { setInterval(new Interval(interval)); }
    public RecurrenceRuleValue withInterval(int interval) { setInterval(interval); return this; }
    public RecurrenceRuleValue withInterval(Interval interval) { setInterval(interval); return this; }
    
    /**
     * UNTIL:
     * RFC 5545 iCalendar 3.3.10, page 41
     * 
     * The UNTIL rule part defines a DATE or DATE-TIME value that bounds
     * the recurrence rule in an inclusive manner.  If the value
     * specified by UNTIL is synchronized with the specified recurrence,
     * this DATE or DATE-TIME becomes the last instance of the
     * recurrence.  The value of the UNTIL rule part MUST have the same
     * value type as the "DTSTART" property.  Furthermore, if the
     * "DTSTART" property is specified as a date with local time, then
     * the UNTIL rule part MUST also be specified as a date with local
     * time.  If the "DTSTART" property is specified as a date with UTC
     * time or a date with local time and time zone reference, then the
     * UNTIL rule part MUST be specified as a date with UTC time.  In the
     * case of the "STANDARD" and "DAYLIGHT" sub-components the UNTIL
     * rule part MUST always be specified as a date with UTC time.  If
     * specified as a DATE-TIME value, then it MUST be specified in a UTC
     * time format.  If not present, and the COUNT rule part is also not
     * present, the "RRULE" is considered to repeat forever
     */
    private Until until;
    public Until getUntil() { return until; }
    public void setUntil(Until until) { this.until = until; }
    public void setUntil(Temporal until) { setUntil(new Until(until)); }
    public void setUntil(String until) { setUntil(DateTimeUtilities.temporalFromString(until)); }
    public RecurrenceRuleValue withUntil(Temporal until) {  setUntil(until); return this; }
    public RecurrenceRuleValue withUntil(String until) { setUntil(until); return this; }
    public RecurrenceRuleValue withUntil(Until until) { setUntil(until); return this; }
    
    /**
     * Week Start
     * WKST:
     * RFC 5545 iCalendar 3.3.10, page 42
     * 
     * The WKST rule part specifies the day on which the workweek starts.
     * Valid values are MO, TU, WE, TH, FR, SA, and SU.  This is
     * significant when a WEEKLY "RRULE" has an interval greater than 1,
     * and a BYDAY rule part is specified.  This is also significant when
     * in a YEARLY "RRULE" when a BYWEEKNO rule part is specified.  The
     * default value is MO.
     */
    private WeekStart weekStart;
    public WeekStart getWeekStart() { return weekStart; }
    public void setWeekStart(WeekStart weekStart) { this.weekStart = weekStart; }
    public void setWeekStart(DayOfWeek weekStart) { setWeekStart(new WeekStart(weekStart)); }
    public RecurrenceRuleValue withWeekStart(WeekStart weekStart) { setWeekStart(weekStart); return this; }
    public RecurrenceRuleValue withWeekStart(DayOfWeek weekStart) { setWeekStart(weekStart); return this; }
    
//   // bind to values in the Byxxx rules that use Week Start
//    private void addWeekStartBindings(ByRule<?> rule)
//    {
//        if (getWeekStart() != null)
//        {
//            if (rule instanceof ByDay)
//            {
//                ((ByDay) rule).weekStartProperty().bind(getWeekStart().valueProperty());
//            } else if (rule instanceof ByWeekNumber)
//            {
//                ((ByWeekNumber) rule).weekStartProperty().bind(getWeekStart().valueProperty());
//            }
//        }
//    }
    
    
    @Override
    public void copyInto(VParent destination)
    {
        super.copyInto(destination);
        childrenUnmodifiable().forEach((childSource) -> 
        {
            RRuleElementType type = RRuleElementType.enumFromClass(childSource.getClass());
            if (type != null)
            {
                type.copyElement((RRuleElement<?>) childSource, (RecurrenceRuleValue) destination);
            } 
        });
    }
    
//    /** 
//     * SORT ORDER
//     * 
//     * Element sort order map.  Key is element, value is the sort order.  The map is automatically
//     * populated when parsing the content lines to preserve the existing property order.
//     * 
//     * When producing the content lines, if a element is not present in the map, it is put at
//     * the end of the sorted ones in the order appearing in {@link #RecurrenceRuleElement} 
//     * Generally, this map shouldn't be modified.  Only modify it when you want
//     * to force a specific property order (e.g. unit testing).
//     */
//    @Deprecated
//    public Map<RRuleElementType, Integer> elementSortOrder() { return elementSortOrder; }
//    @Deprecated
//    final private Map<RRuleElementType, Integer> elementSortOrder = new HashMap<>();
//    private Integer elementCounter = 0;
    
    /*
     * CONSTRUCTORS
     */
    
    public RecurrenceRuleValue()
    {
//        orderer.registerSortOrderProperty(frequencyProperty());
//        byRules = FXCollections.observableArrayList();
//        orderer().registerSortOrderProperty(byRules);
//        
//        // Listener that ensures user doesn't add same ByRule a second time.  Also keeps the byRules list sorted.
//        byRules().addListener((ListChangeListener<? super ByRule<?>>) (change) ->
//        {
//            while (change.next())
//            {
//                if (change.wasAdded())
//                {
//                    change.getAddedSubList().stream().forEach(c ->
//                    {
//                        ByRule<?> newByRule = c;
//                        long alreadyPresent = change.getList()
//                                .stream()
//                                .map(r -> r.elementType())
//                                .filter(p -> p.equals(c.elementType()))
//                                .count();
//                        if (alreadyPresent > 1)
//                        {
//                            throw new IllegalArgumentException("Can't add " + newByRule.getClass().getSimpleName() + " (" + c.elementType() + ") more than once.");
//                        }
//                        addWeekStartBindings(newByRule);
//                    });
//                    
//                    // Sort after addition
////                    orderer().unregisterSortOrderProperty(byRules());
//                    Collections.sort(change.getList());
////                    orderer().registerSortOrderProperty(byRules());
//                }
//            }
//        });
    }

    // Copy constructor
    public RecurrenceRuleValue(RecurrenceRuleValue source)
    {
        this();
        source.copyInto(this);
//        copyChildrenFrom(source);
    }
    
    /** Parse component from content line */
    @Override
    public List<String> parseContent(String contentLine)
    {
        ICalendarUtilities.contentToParameterListPair(contentLine)
                .stream()
                .forEach(entry ->
                {
                    RRuleElementType element = RRuleElementType.enumFromName(entry.getKey());
                    if (element != null)
                    {
                        element.parse(this, entry.getValue());
                    } else
                    {
                        throw new IllegalArgumentException("Unsupported Recurrence Rule element: " + entry.getKey());                        
                    }
                });
        return errors();
    }

    /**
     * STREAM RECURRENCES
     * 
     * Resulting stream of start date/times by applying Frequency temporal adjuster and all, if any,
     *
     * Starts on startDateTime, which MUST be a valid occurrence date/time, but not necessarily the
     * first date/time (DTSTART) in the sequence. A later startDateTime can be used to more efficiently
     * get to later dates in the stream.
     * 
     * @param start - starting point of stream (MUST be a valid occurrence date/time)
     * @return
     */
    public Stream<Temporal> streamRecurrences(Temporal start)
    {
        int interval = (getInterval() == null) ? Interval.DEFAULT_INTERVAL : getInterval().getValue();
        Stream<Temporal> frequencyStream = getFrequency().streamRecurrences(start, interval);
        
        Stream<Temporal> recurrenceStream = frequencyStream
                .flatMap(value ->
                {
                    // process byRules
                    chronoUnit = getFrequency().getValue().getChronoUnit(); // initial chronoUnit from Frequency
                    myStream = Arrays.asList(value).stream();
                    byRules().stream()
                            .sorted()
                            .forEach(rule ->
                            {
                                myStream = rule.streamRecurrences(myStream, chronoUnit, start);
                                chronoUnit = rule.getChronoUnit();
                            });
                    // must filter out too early recurrences
                    return myStream.filter(r -> ! DateTimeUtilities.isBefore(r, start));
                });
        
        if (getCount() != null)
        {
            return recurrenceStream.limit(getCount().getValue());
        } else if (getUntil() != null)
        {
            ZoneId zone = (start instanceof ZonedDateTime) ? ((ZonedDateTime) start).getZone() : null;
            Temporal convertedUntil = DateTimeType.of(start).from(getUntil().getValue(), zone);
            return takeWhile(recurrenceStream, a -> ! DateTimeUtilities.isAfter(a, convertedUntil));
        }
        return recurrenceStream;
    }
    private ChronoUnit chronoUnit; // must be field instead of local variable due to use in lambda expression
    private Stream<Temporal> myStream; // must be field instead of local variable due to use in lambda expression
    
    /**
     * Determines if recurrence set is goes on forever
     * 
     * @return - true if recurrence set is infinite, false otherwise
     */
    public boolean isInfinite()
    {
        return ((getCount() == null) && (getUntil() == null));
    }
    
    
    // takeWhile - From http://stackoverflow.com/questions/20746429/limit-a-stream-by-a-predicate
    static <T> Spliterator<T> takeWhile(Spliterator<T> splitr, Predicate<? super T> predicate)
    {
      return new Spliterators.AbstractSpliterator<T>(splitr.estimateSize(), 0) {
        boolean stillGoing = true;
        @Override public boolean tryAdvance(Consumer<? super T> consumer) {
          if (stillGoing) {
            boolean hadNext = splitr.tryAdvance(elem -> {
              if (predicate.test(elem)) {
                consumer.accept(elem);
              } else {
                stillGoing = false;
              }
            });
            return hadNext && stillGoing;
          }
          return false;
        }
      };
    }

    static <T> Stream<T> takeWhile(Stream<T> stream, Predicate<? super T> predicate) {
       return StreamSupport.stream(takeWhile(stream.spliterator(), predicate), false);
    }
    
    @Override
    public String toString()
    {
        return childrenUnmodifiable().stream()
                .map(c -> c.toString())
                .collect(Collectors.joining(";"));
    }
    
    @Override
    public List<String> errors()
    {
        List<String> errors = new ArrayList<>();
        if (getFrequency() == null)
        {
            errors.add("FREQ is not present.  FREQ is REQUIRED and MUST NOT occur more than once");
        }
        boolean isUntilPresent = getUntil() != null;
        boolean isCountPresent = getCount() != null;
        if (isUntilPresent && isCountPresent)
        {
            errors.add("UNTIL and COUNT are both present.  UNTIL or COUNT rule parts are OPTIONAL, but they MUST NOT both occur.");
        }
        childrenUnmodifiable().forEach(c -> errors.addAll(c.errors()));
//        byRules().forEach(b -> errors.addAll(b.errors()));
        return errors;
    }

    public static RecurrenceRuleValue parse(String propertyContent)
    {
        RecurrenceRuleValue property = new RecurrenceRuleValue();
        property.parseContent(propertyContent);
        return property;
    }
}
