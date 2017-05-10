package net.balsoftware.icalendar.parameters;

import java.net.URI;

import net.balsoftware.icalendar.parameters.DirectoryEntry;
import net.balsoftware.icalendar.parameters.VParameterBase;
import net.balsoftware.icalendar.utilities.StringConverter;
import net.balsoftware.icalendar.utilities.StringConverters;

/**
 * DIR
 * Directory Entry Reference
 * RFC 5545, 3.2.6, page 18
 * 
 * To specify reference to a directory entry associated with
 *     the calendar user specified by the property.
 * 
 * Example:
 * ORGANIZER;DIR="ldap://example.com:6666/o=ABC%20Industries,
 *  c=US???(cn=Jim%20Dolittle)":mailto:jimdo@example.com
 * 
 * @author David Bal
 *
 */
public class DirectoryEntry extends VParameterBase<DirectoryEntry, URI>
{
	private static final StringConverter<URI> CONVERTER = StringConverters.uriConverterWithQuotes();

    public DirectoryEntry(URI uri)
    {
        super(uri, CONVERTER);
    }
    
    public DirectoryEntry()
    {
        super(CONVERTER);
    }

    public DirectoryEntry(DirectoryEntry source)
    {
        super(source, CONVERTER);
    }
    
    public static DirectoryEntry parse(String content)
    {
    	return DirectoryEntry.parse(new DirectoryEntry(), content);
    }
}
