package net.balsoftware.icalendar.parameters;

import net.balsoftware.icalendar.parameters.CommonName;
import net.balsoftware.icalendar.parameters.VParameterBase;
import net.balsoftware.icalendar.utilities.StringConverter;
import net.balsoftware.icalendar.utilities.StringConverters;

/**
 * CN
 * Common Name
 * RFC 5545, 3.2.2, page 15
 * 
 * To specify the common name to be associated with the calendar user specified by the property.
 * 
 * Example:
 * ORGANIZER;CN="John Smith":mailto:jsmith@example.com
 * 
 * @author David Bal
 */
public class CommonName extends VParameterBase<CommonName, String>
{
	private static final StringConverter<String> CONVERTER = StringConverters.defaultStringConverterWithQuotes();
	
    public CommonName()
    {
        super(CONVERTER);
    }

    public CommonName(CommonName source)
    {
        super(source, CONVERTER);
    }
    
    public static CommonName parse(String content)
    {
    	return CommonName.parse(new CommonName(), content);
    }
}
