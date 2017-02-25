package net.balsoftware.components;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import net.balsoftware.properties.component.recurrence.PropertyBaseRecurrence;
import net.balsoftware.properties.component.recurrence.RecurrenceDates;
import net.balsoftware.properties.component.recurrence.RecurrenceRule;
import net.balsoftware.properties.component.recurrence.RecurrenceRuleCache;
import net.balsoftware.properties.component.recurrence.rrule.RecurrenceRuleValue;
import net.balsoftware.properties.component.time.DateTimeStart;
import net.balsoftware.utilities.DateTimeUtilities;
import net.balsoftware.utilities.DateTimeUtilities.DateTimeType;
import net.balsoftware.utilities.ICalendarUtilities;

/**
 * Contains following properties:
 * @see RecurrenceRule
 * @see RecurrenceDates
 * 
 * @author David Bal
 * @see VEventOld
 * @see VTodo
 * @see VJournal
 * @see StandardTime
 * @see DaylightSavingTime
 *
 * @param <T> implemented class
 * @param <R> recurrence type
 */
public interface VRepeatable<T> extends VComponent
{    
    /**
     * RDATE: Recurrence Date-Times
     * Set of date/times for recurring events, to-dos, journal entries.
     * 3.8.5.2, RFC 5545 iCalendar
     * 
     * Examples:
     * RDATE;TZID=America/New_York:19970714T083000
     * RDATE;VALUE=DATE:19970101,19970120,19970217,19970421
     *  19970526,19970704,19970901,19971014,19971128,19971129,1997122
     */
    List<RecurrenceDates> getRecurrenceDates();
    void setRecurrenceDates(List<RecurrenceDates> recurrenceDates);
    default T withRecurrenceDates(List<RecurrenceDates> recurrenceDates)
    {
        setRecurrenceDates(recurrenceDates);
        return (T) this;
    }
    default T withRecurrenceDates(String...recurrenceDates)
    {
        List<RecurrenceDates> list = Arrays.stream(recurrenceDates)
                .map(c -> RecurrenceDates.parse(c))
                .collect(Collectors.toList());
        setRecurrenceDates(list);
        return (T) this;
    }
    default T withRecurrenceDates(Temporal...recurrenceDates)
    {
        List<RecurrenceDates> list = Arrays.stream(recurrenceDates)
                .map(c -> new RecurrenceDates(c))
                .collect(Collectors.toList());
        setRecurrenceDates(list);
        return (T) this;
    }
    default T withRecurrenceDates(RecurrenceDates...recurrenceDates)
    {
    	setRecurrenceDates(new ArrayList<>(Arrays.asList(recurrenceDates)));
        return (T) this;
    }
    
    /**
     * Determines if recurrence objects are valid.  They are valid if the date-time types are the same and matches
     * DateTimeStart.  This should be run when a change occurs to the recurrences list and when the recurrences
     * Observable list is set.
     * 
     * Also works for exceptions.
     * @param <U>
     * 
     * @param list - list of recurrence objects to be tested.
     * @param firstRecurrence - example of Temporal to match against.  If null uses first element in first recurrence in list
     * @return - true is valid, throws exception otherwise
     */
    default String checkRecurrencesConsistency(List<? extends PropertyBaseRecurrence<?>> list)
    {
        if ((list == null) || list.isEmpty() || (getDateTimeStart() == null))
        {
            return null;
        }
//        Temporal firstRecurrence = list.get(0).getValue().iterator().next();
        Temporal firstRecurrence = getDateTimeStart().getValue();
        if (firstRecurrence == null)
        {
            return null;
        }
        DateTimeType dateTimeStartType = DateTimeUtilities.DateTimeType.of(firstRecurrence);
        Optional<DateTimeType> nonMatchingType = list.stream()
                .flatMap(p -> p.getValue().stream())
                .map(t -> DateTimeUtilities.DateTimeType.of(t))
                .filter(y -> ! y.equals(dateTimeStartType))
                .findAny();
//        System.out.println("bad:" + badType);
        if (nonMatchingType.isPresent())
        {
            return list.get(0).name() + ": Recurrences DateTimeType " + nonMatchingType.get() +
                    " doesn't match DTSTART DateTimeType " + dateTimeStartType; 
        }
        return null;
    }
        
    static String checkPotentialRecurrencesConsistency(List<? extends PropertyBaseRecurrence<?>> list, PropertyBaseRecurrence<?> testObj)
    {
        if ((list == null) || (list.isEmpty()))
        {
            return null;
        }
        Temporal firstRecurrence = list.get(0).getValue().iterator().next();
        if (firstRecurrence == null)
        {
            return null;
        }
        DateTimeType firstDateTimeTypeType = DateTimeUtilities.DateTimeType.of(firstRecurrence);
        Optional<DateTimeType> nonMatchingType = testObj.getValue().stream()
                .map(t -> DateTimeUtilities.DateTimeType.of(t))
//                .peek(t -> System.out.println("ttt:" + firstDateTimeTypeType + " " + t))
                .filter(y -> ! y.equals(firstDateTimeTypeType))
                .findAny();
//        System.out.println("bad:" + badType);
        if (nonMatchingType.isPresent())
        {
            return list.get(0).name() + ": Added recurrences DateTimeType " + nonMatchingType.get() +
                    " doesn't match previous recurrences DateTimeType " + firstDateTimeTypeType;            
        }
        return null;
    }

    
    default void checkDateTimeStartConsistency()
    {
        if ((getRecurrenceDates() != null) && (getDateTimeStart() != null))
        {
            Temporal firstRecurrence = getRecurrenceDates().get(0).getValue().iterator().next();
            DateTimeType recurrenceType = DateTimeUtilities.DateTimeType.of(firstRecurrence);
            DateTimeType dateTimeStartType = DateTimeUtilities.DateTimeType.of(getDateTimeStart().getValue());
            if (recurrenceType != dateTimeStartType)
            {
                throw new DateTimeException("Recurrences DateTimeType (" + recurrenceType +
                        ") must be same as the DateTimeType of DateTimeStart (" + dateTimeStartType + ")");
            }
        }        
    }
    
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
    RecurrenceRule getRecurrenceRule();
    void setRecurrenceRule(RecurrenceRule recurrenceRule);
    default void setRecurrenceRule(RecurrenceRuleValue rrule)
    {
    	if (rrule == null)
    	{
    		setRecurrenceRule((RecurrenceRule) null);
    	} else
    	{
    		setRecurrenceRule(new RecurrenceRule(rrule));
    	}
    }
    default void setRecurrenceRule(String rrule)
    {
    	setRecurrenceRule(RecurrenceRule.parse(rrule));
    }
    default T withRecurrenceRule(String rrule)
    {
        setRecurrenceRule(rrule);
        return (T) this;
    }
    default T withRecurrenceRule(RecurrenceRule rrule)
    {
        setRecurrenceRule(rrule);
        return (T) this;
    }
    default T withRecurrenceRule(RecurrenceRuleValue rrule)
    {
        setRecurrenceRule(rrule);
        return (T) this;
    }
    
    // From VComponentPrimary
    DateTimeStart getDateTimeStart();

    /**
     * Handles caching of recurrence start Temporal values.
     */
    RecurrenceRuleCache recurrenceCache();

    /**
     * Produces a stream of dates or date-times bounded by the start and end parameters.  See {@link #streamRecurrences(Temporal)}
     * 
     * @param start - include recurrences that END before this value (inclusive)
     * @param end - include recurrences that START before this value (exclusive)
     * @return - stream of start dates or date/times for the recurrence set
     */
    default Stream<Temporal> streamRecurrences(Temporal start, Temporal end)
    {
        return ICalendarUtilities.takeWhile(streamRecurrences(start), a -> DateTimeUtilities.isBefore(a, end)); // exclusive
    }

    
    /** 
     * Produces a stream of dates or date-times (depending on DTSTART) that represents the start
     * of each element in the recurrence set.
     * The values are calculated after applying DTSTART, RDATE, RRULE, and EXDATE properties.
     * 
     * If the RRULE is forever, then the stream has no end as well.

     * For a VEvent without RRULE or RDATE the stream will contain only one element.
     * 
     * @param start - include recurrences that END before this value
     * @return - stream of start dates or date/times for the recurrence set
     */
    default Stream<Temporal> streamRecurrences(Temporal start)
    {
        DateTimeType startType = DateTimeUtilities.DateTimeType.of(start);
        DateTimeType dateTimeStartType = DateTimeUtilities.DateTimeType.of(getDateTimeStart().getValue());
        if (startType != dateTimeStartType)
        {
            throw new DateTimeException("Start type " + startType + " must match DTSTART type of " + dateTimeStartType);
        }
        // get recurrence rule stream, or make a one-element stream from DTSTART if no recurrence rule is present
        final Stream<Temporal> stream1;
        if (getRecurrenceRule() == null)
        {
            stream1 = Arrays.asList(getDateTimeStart().getValue()).stream();
        } else
        {
        	if (getRecurrenceRule().getValue().getCount() == null)
        	{
	            Temporal cacheStart = recurrenceCache().getClosestStart(start);
	            stream1 = getRecurrenceRule().getValue().streamRecurrences(cacheStart);
        	} else
        	{ // if RRULE has COUNT must start at DTSTART
        		stream1 = getRecurrenceRule().getValue().streamRecurrences(getDateTimeStart().getValue());
        	}
        }
        
        // assign temporal comparator to match start type
        final Comparator<Temporal> temporalComparator = DateTimeUtilities.getTemporalComparator(start);
        
        // add recurrences, if present
        final Stream<Temporal> stream2 = (getRecurrenceDates() == null) ? stream1 : merge(
                stream1,
                getRecurrenceDates()
                        .stream()
                        .flatMap(r -> r.getValue().stream())
                        .map(v -> v)
                        .filter(t -> ! DateTimeUtilities.isBefore(t, start)) // remove too early events;
                        .sorted(temporalComparator)
                , temporalComparator);
        
        return stream2
                .filter(t -> ! DateTimeUtilities.isBefore(t, start));
//                .peek(t -> System.out.println("stream:" + t + " " + start + " " + ! DateTimeUtilities.isBefore(t, start)));
    }
    
    /** Stream of recurrences starting at dateTimeStart (DTSTART) 
     * @link {@link #streamRecurrences(Temporal)}*/
    default Stream<Temporal> streamRecurrences()
    {
        return streamRecurrences(getDateTimeStart().getValue());
    }
    
    /**
     * finds previous stream Temporal before input parameter value
     * 
     * @param value
     * @return
     */
    default Temporal previousStreamValue(Temporal value)
    {
        Temporal cacheStart = recurrenceCache().getClosestStart(value);
        Iterator<Temporal> i = streamRecurrences(cacheStart).iterator();
        Temporal lastT = null;
        while (i.hasNext())
        {
            Temporal t = i.next();
            if (! DateTimeUtilities.isBefore(t, value)) break;
            lastT = t;
        }
        return lastT;
    }
    
    /** Returns true if temporal is in vComponent's stream of start date-time
     * values, false otherwise.
     */
    default boolean isRecurrence(Temporal temporal)
    {
        if (temporal == null) throw new DateTimeException("Temporal parameter must not be null.");
        Iterator<Temporal> startInstanceIterator = streamRecurrences(temporal).iterator();
        while (startInstanceIterator.hasNext())
        {
            Temporal myStartInstance = startInstanceIterator.next();
            if (myStartInstance.equals(temporal))
            {
                return true;
            }
            if (DateTimeUtilities.isAfter(myStartInstance, temporal))
            {
                return false;
            }
        }
        return false;
    }

    /** Returns true if VComponent has zero instances in recurrence set */
    default boolean isRecurrenceSetEmpty()
    {
        Iterator<Temporal> i = streamRecurrences().iterator();
        return ! i.hasNext();
    }
    
    /** returns the last date or date/time of the series.  If infinite returns null */
    default Temporal lastRecurrence()
    {
        if ((getRecurrenceRule() != null) && (getRecurrenceRule().getValue().isInfinite()))
        {
            return null;
        } else
        {
            Iterator<Temporal> i = streamRecurrences().iterator();
            Temporal myTemporal = null;
            while (i.hasNext())
            {
                myTemporal = i.next();
            }
            return myTemporal;
        }       
    }
    
    static List<String> errorsRepeatable(VRepeatable<?> testObj)
    {
        List<String> errors = new ArrayList<>();
        String recurrenceDateError = testObj.checkRecurrencesConsistency(testObj.getRecurrenceDates());
        if (recurrenceDateError != null) errors.add(recurrenceDateError);

        if (testObj.getRecurrenceRule() != null && testObj.getRecurrenceRule().getValue().getUntil() != null)
        {
            Temporal until = testObj.getRecurrenceRule().getValue().getUntil().getValue();
            DateTimeType untilType = DateTimeType.of(until);
            DateTimeType startType = DateTimeType.of(testObj.getDateTimeStart().getValue());
            switch (startType)
            {
            case DATE:
                if (untilType != DateTimeType.DATE)
                {
                    errors.add("If DTSTART specifies a DATE then UNTIL must also specify a DATE value instead of:" + untilType);
                }
                break;
            case DATE_WITH_LOCAL_TIME:
            case DATE_WITH_LOCAL_TIME_AND_TIME_ZONE:
            case DATE_WITH_UTC_TIME:
                if (untilType != DateTimeType.DATE_WITH_UTC_TIME)
                {
                    errors.add("If DTSTART specifies a DATE_WITH_LOCAL_TIME, DATE_WITH_LOCAL_TIME_AND_TIME_ZONE or DATE_WITH_UTC_TIME then UNTIL must specify a DATE_WITH_UTC_TIME value instead of:" + untilType);
                }
                break;
            default:
                throw new RuntimeException("unsupported DateTimeType:" + startType);
            }
        }
        return errors;
    }
    
    static public List<String> errorsRecurrence(List<? extends PropertyBaseRecurrence<?>> dates, DateTimeStart dtstart)
    {
    	List<String> errors = new ArrayList<>();
//    	List<RecurrenceDates> recurrenceDates = component.getRecurrenceDates();
    	List<? extends PropertyBaseRecurrence<?>> recurrenceDates = dates;
    	
    	// error check - all Temporal types must be same
    	if ((recurrenceDates != null) && (! recurrenceDates.isEmpty()))
		{
        	Temporal sampleTemporal = recurrenceDates.stream()
            		.flatMap(r -> r.getValue().stream())
            		.findAny()
            		.get();
    		DateTimeType sampleType = DateTimeUtilities.DateTimeType.of(sampleTemporal);
        	Optional<String> error1 = recurrenceDates
        		.stream()
        		.flatMap(r -> r.getValue().stream())
	        	.map(v ->
	        	{
	        		DateTimeType recurrenceType = DateTimeUtilities.DateTimeType.of(v);
	        		if (! recurrenceType.equals(sampleType))
	        		{
	                    return "Recurrences DateTimeType " + recurrenceType +
	                            " doesn't match previous recurrences DateTimeType " + sampleType;            
	        		}
	        		return null;
	        	})
	        	.filter(s -> s != null)
	        	.findAny();
        	
        	if (error1.isPresent())
        	{
        		errors.add(error1.get());
        	}
        	
        	// DTSTART check
            DateTimeType dateTimeStartType = DateTimeUtilities.DateTimeType.of(dtstart.getValue());
            if (sampleType != dateTimeStartType)
            {
                errors.add("Recurrences DateTimeType (" + sampleType +
                        ") must be same as the DateTimeType of DateTimeStart (" + dateTimeStartType + ")");
            }
            
            // ensure all ZoneId values are the same
            if (sampleTemporal instanceof ZonedDateTime)
            {
                ZoneId zone = ((ZonedDateTime) sampleTemporal).getZone();
                boolean allZonesIdentical = recurrenceDates
                        .stream()
                        .flatMap(r -> r.getValue().stream())
                        .map(t -> ((ZonedDateTime) t).getZone())
                        .allMatch(z -> z.equals(zone));
                if (! allZonesIdentical)
                {
                	errors.add("ZoneId are not all identical");
                }
                
            }
        }
        return errors;
    }
    
    @Deprecated // may not be used - if not remove or move to utility class
    public static <T> Stream<T> merge(Stream<T> stream1, Stream<T> stream2, Comparator<T> comparator)
    {
            Iterator<T> iterator = new MergedIterator<T>(
                    stream1.iterator()
                  , stream2.iterator()
                  , comparator);
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
    }
    
    /*
     * Recommend using with StreamSupport.stream(iteratorStream, false);
     */

    /** Merge two sorted iterators */
    static class MergedIterator<T> implements Iterator<T>
    {
        private final Iterator<T> iterator1;
        private final Iterator<T> iterator2;
        private final Comparator<T> comparator;
        private T next1;
        private T next2;
        
        public MergedIterator(Iterator<T> iterator1, Iterator<T> iterator2, Comparator<T> comparator)
        {
            this.iterator1 = iterator1;
            this.iterator2 = iterator2;
            this.comparator = comparator;
        }
        
        @Override
        public boolean hasNext()
        {
            return  iterator1.hasNext() || iterator2.hasNext() || (next1 != null) || (next2 != null);
        }

        @Override
        public T next()
        {
            if (iterator1.hasNext() && (next1 == null)) next1 = iterator1.next();
            if (iterator2.hasNext() && (next2 == null)) next2 = iterator2.next();
            T theNext;
            int result = (next1 == null) ? 1 :
                         (next2 == null) ? -1 :
                         comparator.compare(next1, next2);
            if (result > 0)
            {
                theNext = next2;
                next2 = null;
            } else if (result < 0)
            {
                theNext = next1;
                next1 = null;
            } else
            { // same element, return one, advance both
                theNext = next1;
                next1 = null;
                next2 = null;
            }
            return theNext;
        }
    }

}
