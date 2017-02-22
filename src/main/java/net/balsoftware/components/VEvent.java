package net.balsoftware.components;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.balsoftware.properties.component.time.DateTimeEnd;
import net.balsoftware.properties.component.time.TimeTransparency;
import net.balsoftware.properties.component.time.TimeTransparency.TimeTransparencyType;
import net.balsoftware.utilities.DateTimeUtilities;

/**
 * VEVENT
 * Event Component
 * RFC 5545, 3.6.1, page 52
 * 
 *    Description:  A "VEVENT" calendar component is a grouping of
      component properties, possibly including "VALARM" calendar
      components, that represents a scheduled amount of time on a
      calendar.  For example, it can be an activity; such as a one-hour
      long, department meeting from 8:00 AM to 9:00 AM, tomorrow.
      Generally, an event will take up time on an individual calendar.
      Hence, the event will appear as an opaque interval in a search for
      busy time.  Alternately, the event can have its Time Transparency

      set to "TRANSPARENT" in order to prevent blocking of the event in
      searches for busy time.

      The "VEVENT" is also the calendar component used to specify an
      anniversary or daily reminder within a calendar.  These events
      have a DATE value type for the "DTSTART" property instead of the
      default value type of DATE-TIME.  If such a "VEVENT" has a "DTEND"
      property, it MUST be specified as a DATE value also.  The
      anniversary type of "VEVENT" can span more than one date (i.e.,
      "DTEND" property value is set to a calendar date after the
      "DTSTART" property value).  If such a "VEVENT" has a "DURATION"
      property, it MUST be specified as a "dur-day" or "dur-week" value.

      The "DTSTART" property for a "VEVENT" specifies the inclusive
      start of the event.  For recurring events, it also specifies the
      very first instance in the recurrence set.  The "DTEND" property
      for a "VEVENT" calendar component specifies the non-inclusive end
      of the event.  For cases where a "VEVENT" calendar component
      specifies a "DTSTART" property with a DATE value type but no
      "DTEND" nor "DURATION" property, the event's duration is taken to
      be one day.  For cases where a "VEVENT" calendar component
      specifies a "DTSTART" property with a DATE-TIME value type but no
      "DTEND" property, the event ends on the same calendar date and
      time of day specified by the "DTSTART" property.

      The "VEVENT" calendar component cannot be nested within another
      calendar component.  However, "VEVENT" calendar components can be
      related to each other or to a "VTODO" or to a "VJOURNAL" calendar
      component with the "RELATED-TO" property.
      
         Example:  The following is an example of the "VEVENT" calendar
      component used to represent a meeting that will also be opaque to
      searches for busy time:

       BEGIN:VEVENT
       UID:19970901T130000Z-123401@example.com
       DTSTAMP:19970901T130000Z
       DTSTART:19970903T163000Z
       DTEND:19970903T190000Z
       SUMMARY:Annual Employee Review
       CLASS:PRIVATE
       CATEGORIES:BUSINESS,HUMAN RESOURCES
       END:VEVENT
 *
 * @author David Bal
 *
 */
// TODO - MAKE INTERFACE AND IMPLEMENTATION
public class VEvent extends VLocatable<VEvent> implements VDateTimeEnd<VEvent>,
    VDescribable2<VEvent>, VRepeatable<VEvent>
{
    /**
     * DTEND
     * Date-Time End
     * RFC 5545, 3.8.2.2, page 95
     * 
     * This property specifies when the calendar component ends.
     * 
     * The value type of this property MUST be the same as the "DTSTART" property, and
     * its value MUST be later in time than the value of the "DTSTART" property.
     * 
     * Example:
     * DTEND;VALUE=DATE:19980704
     */
    @Override
    public DateTimeEnd getDateTimeEnd() { return dateTimeEnd; }
    private DateTimeEnd dateTimeEnd;
    public void setDateTimeEnd(DateTimeEnd dtEnd) { this.dateTimeEnd = dateTimeEnd; }
    /** add listener to Duration to ensure both DURATION and DTEND are not both set */


    /**
     * TRANSP
     * Time Transparency
     * RFC 5545 iCalendar 3.8.2.7. page 101
     * 
     * This property defines whether or not an event is transparent to busy time searches.
     * Events that consume actual time SHOULD be recorded as OPAQUE.  Other
     * events, which do not take up time SHOULD be recorded as TRANSPARENT.
     *    
     * Example:
     * TRANSP:TRANSPARENT
     */
    private TimeTransparency timeTransparency;
    public TimeTransparency getTimeTransparency() { return timeTransparency; }
    public void setTimeTransparency(String timeTransparency) { setTimeTransparency(TimeTransparency.parse(timeTransparency)); }
    public void setTimeTransparency(TimeTransparency timeTransparency) { this.timeTransparency = timeTransparency; }
    public void setTimeTransparency(TimeTransparencyType timeTransparency) { setTimeTransparency(new TimeTransparency(timeTransparency)); }
    public VEvent withTimeTransparency(TimeTransparency timeTransparency)
    {
        setTimeTransparency(timeTransparency);
        return this;
    }
    public VEvent withTimeTransparency(TimeTransparencyType timeTransparencyType)
    {
        setTimeTransparency(timeTransparencyType);
        return this;
    }
    public VEvent withTimeTransparency(String timeTransparency)
    {
    	setTimeTransparency(TimeTransparency.parse(timeTransparency));
        return this;
    }
    
    /*
     * CONSTRUCTORS
     */
    public VEvent()
    {
        super();
    }
    
    /** Copy constructor */
    public VEvent(VEvent source)
    {
        super(source);
    }

    @Override
    public TemporalAmount getActualDuration()
    {
        final TemporalAmount duration;
        if (getDuration() != null)
        {
            duration = getDuration().getValue();
        } else if (getDateTimeEnd() != null)
        {
            Temporal dtstart = getDateTimeStart().getValue();
            Temporal dtend = getDateTimeEnd().getValue();
            duration = DateTimeUtilities.temporalAmountBetween(dtstart, dtend);
        } else
        {
            return Duration.ZERO;
        }
        return duration;
    }
    
    @Override
    public void setEndOrDuration(Temporal startRecurrence, Temporal endRecurrence)
    {
        TemporalAmount duration = DateTimeUtilities.temporalAmountBetween(startRecurrence, endRecurrence);
        if (getDuration() != null)
        {
            setDuration(duration);
        } else if (getDateTimeEnd() != null)
        {
            Temporal dtend = getDateTimeStart().getValue().plus(duration);
            setDateTimeEnd(dtend);
        } else
        {
            throw new RuntimeException("Either DTEND or DURATION must be set");
        }
    }
    
    @Override
    public List<String> errors()
    {
        // TODO - GET ERRORS FROM CHILDREN?
        // REMOVE DTEND LISTENERS??  WHAT ABOUT RDATE AND EXDATE LISTENERS???
        List<String> errors = super.errors();
        List<String> dtendError = VDateTimeEnd.errorsDateTimeEnd(this);
        errors.addAll(dtendError);
//        boolean isDateTimeEndMatch = dtendError.isEmpty();
        if (getDateTimeStart() == null)
        {
            errors.add("DTSTART is not present.  DTSTART is REQUIRED and MUST NOT occur more than once");
        }
        boolean isDateTimeEndPresent = getDateTimeEnd() != null;
        boolean isDurationPresent = getDuration() != null;       
        
        if (! isDateTimeEndPresent && ! isDurationPresent)
        {
//            errors.add("Neither DTEND or DURATION is present.  DTEND or DURATION is REQUIRED and MUST NOT occur more than once"); // not required
        } else if (isDateTimeEndPresent && isDurationPresent)
        {
            errors.add("Both DTEND and DURATION are present.  DTEND or DURATION MAY appear, but both MUST NOT occur in the same " + name());
        }
        
        return Collections.unmodifiableList(errors);
    }
    
    @Override
    public void eraseDateTimeProperties()
    {
        super.eraseDateTimeProperties();
        setDateTimeEnd((DateTimeEnd) null);
    }
    
    /** Parse folded content lines into calendar component object */
    public static VEvent parse(String foldedContent)
    {
        VEvent component = new VEvent();
        List<String> messages = component.parseContent(foldedContent);
        // filter out Success messages
        String filteredMessages = messages.stream()
            .filter(m ->! m.contains(";Success"))
            .collect(Collectors.joining(System.lineSeparator()));
        if (! filteredMessages.isEmpty())
        {
            throw new IllegalArgumentException(messages.stream().collect(Collectors.joining(System.lineSeparator())));
        }
        return component;
    }
}
