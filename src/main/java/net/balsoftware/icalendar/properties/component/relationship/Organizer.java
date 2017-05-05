package net.balsoftware.icalendar.properties.component.relationship;

import java.net.URI;

import net.balsoftware.icalendar.VElement;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.components.VFreeBusy;
import net.balsoftware.icalendar.components.VJournal;
import net.balsoftware.icalendar.components.VTodo;

/**
 * ORGANIZER
 * RFC 5545, 3.8.4.3, page 111
 * 
 * This property defines the organizer for a calendar component.
 * 
 * Example:
 * ORGANIZER;CN=John Smith:mailto:jsmith@example.com
 * 
 * @author David Bal
 * @see VEvent
 * @see VTodo
 * @see VJournal
 * @see VFreeBusy
 */
public class Organizer extends PropertyBaseCalendarUser<URI, Organizer> implements VElement
{    
    public Organizer(Organizer source)
    {
        super(source);
    }
    
    public Organizer()
    {
        super();
    }

    public static Organizer parse(String content)
    {
    	Organizer property = Organizer.parse(new Organizer(), content);
        URI.class.cast(property.getValue()); // ensure value class type matches parameterized type
        return property;
    }
}
