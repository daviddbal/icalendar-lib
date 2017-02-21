package net.balsoftware.components;

import java.util.List;

import net.balsoftware.VElement;

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
public class VEvent implements VElement
{

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> parseContent(String content) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> errors() {
		// TODO Auto-generated method stub
		return null;
	}

}
