package net.balsoftware.icalendar.parameters;

import java.net.URI;
import java.util.List;

import net.balsoftware.icalendar.parameters.Delegators;
import net.balsoftware.icalendar.parameters.VParameterBase;
import net.balsoftware.icalendar.utilities.StringConverter;
import net.balsoftware.icalendar.utilities.StringConverters;

/**
 * DELEGATED-FROM
 * Delegators
 * RFC 5545, 3.2.4, page 17
 * 
 * To specify the calendar users that have delegated their
 *    participation to the calendar user specified by the property.
 * 
 * Example:
 * ATTENDEE;DELEGATED-FROM="mailto:jsmith@example.com":mailto:
 *  jdoe@example.com
 * 
 * @author David Bal
 */
public class Delegators extends VParameterBase<Delegators, List<URI>>
{
	private static final StringConverter< List<URI>> CONVERTER = StringConverters.uriListConverter();

    public Delegators(List<URI> list)
    {
        super(list, CONVERTER);
    }
    
    public Delegators()
    {
        super(CONVERTER);
    }
    
    public Delegators(Delegators source)
    {
        super(source, CONVERTER);
    }
    
    public static Delegators parse(String content)
    {
    	return Delegators.parse(new Delegators(), content);
    }
}
