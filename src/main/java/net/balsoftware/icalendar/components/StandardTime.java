package net.balsoftware.icalendar.components;

import net.balsoftware.icalendar.components.StandardOrDaylight;
import net.balsoftware.icalendar.components.StandardTime;
import net.balsoftware.icalendar.components.VTimeZone;
import net.balsoftware.icalendar.properties.component.descriptive.Comment;
import net.balsoftware.icalendar.properties.component.misc.NonStandardProperty;
import net.balsoftware.icalendar.properties.component.recurrence.RecurrenceDates;
import net.balsoftware.icalendar.properties.component.recurrence.RecurrenceRule;
import net.balsoftware.icalendar.properties.component.time.DateTimeStart;
import net.balsoftware.icalendar.properties.component.timezone.TimeZoneName;
import net.balsoftware.icalendar.properties.component.timezone.TimeZoneOffsetFrom;
import net.balsoftware.icalendar.properties.component.timezone.TimeZoneOffsetTo;

/**
 * <p>STANDARD<br>
 * Describes Standard Time<br>
 * RFC 5545, 3.6.5, page 65</p>
 * 
 * <p>The DAYLIGHT sub-component is always a child of a VTIMEZONE calendar component.  It can't
 * exist alone.  The "STANDARD" or "DAYLIGHT" sub-component MUST
 * include the {@link DateTimeStart DTSTART}, {@link TimeZoneOffsetFrom TZOFFSETFROM},
 * and {@link TimeZoneOffsetTo TZOFFSETTO} properties.</p>
 *
 * <p>The "DAYLIGHT" sub-component consists of a collection of properties
 * that describe Standard Time.  In general, this collection
 * of properties consists of:
 *<ul>
 *<li>the first onset DATE-TIME for the observance;
 *<li>the last onset DATE-TIME for the observance, if a last onset is
 *        known;
 *<li>the offset to be applied for the observance;
 *<li>a rule that describes the day and time when the observance
 *        takes effect;
 *<li>an optional name for the observance.</p>
 *</ul>
 *</p>
 *<p>Properties available to this sub-component include:
 *<ul>
 *<li>{@link Comment COMMENT}
 *<li>{@link DateTimeStart DTSTART}
 *<li>{@link RecurrenceDates RDATE}
 *<li>{@link RecurrenceRule RRULE}
 *<li>{@link TimeZoneName TZNAME}
 *<li>{@link TimeZoneOffsetFrom TZOFFSETFROM}
 *<li>{@link TimeZoneOffsetTo TZOFFSETTO}
 *<li>{@link NonStandardProperty X-PROP}
 *</ul>
 *</p>
 * 
 * @author David Bal
 * 
 * @see VTimeZone
 *
 */
public class StandardTime extends StandardOrDaylight<StandardTime>
{  
    /*
     * CONSTRUCTORS
     */
    
    /**
     * Creates a default StandardTime calendar component with no properties
     */
    public StandardTime()
    {
        super();
    }

    /**
     * Creates a deep copy of a StandardTime calendar component
     */
    public StandardTime(StandardTime source)
    {
        super(source);
    }

    /**
     * Creates a new VFreeBusy calendar component by parsing a String of iCalendar content lines
     *
     * @param content  the text to parse, not null
     * @return  the parsed VFreeBusy
     */
    public static StandardTime parse(String content)
    {
    	return StandardTime.parse(new StandardTime(), content);
    }
}
