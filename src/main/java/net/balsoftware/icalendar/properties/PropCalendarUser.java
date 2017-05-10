package net.balsoftware.icalendar.properties;

import net.balsoftware.icalendar.parameters.CommonName;
import net.balsoftware.icalendar.parameters.DirectoryEntry;
import net.balsoftware.icalendar.parameters.SentBy;
import net.balsoftware.icalendar.properties.PropLanguage;
import net.balsoftware.icalendar.properties.component.relationship.Attendee;
import net.balsoftware.icalendar.properties.component.relationship.Organizer;
import net.balsoftware.icalendar.properties.component.relationship.PropertyBaseCalendarUser;

/**
 * Abstract class for properties with a CAL-ADDRESS value.
 * The value is stored as a URI object
 * 
 * CAL-ADDRESS
 * Calendar User Address
 * RFC 5545, 3.3.3, page 31
 *  
 * This value type is used to identify properties that contain a calendar user address.
 * The email address of a calendar user.
 * 
 * Example: mailto:jane_doe@example.com
 * 
 * @author David Bal
 *
 * @param <T>
 * @param <T>
 *
 * Base class
 * @see PropertyBaseCalendarUser
 *  
 * concrete subclasses
 * @see Organizer
 * @see Attendee
 */
public interface PropCalendarUser<T> extends PropLanguage<T>
{
    /**
     * CN
     * Common Name
     * RFC 5545, 3.2.2, page 15
     * 
     * To specify the common name to be associated with the calendar user specified by the property.
     * 
     * Example:
     * ORGANIZER;CN="John Smith":mailto:jsmith@example.com
     */
    CommonName getCommonName();
    void setCommonName(CommonName commonName);  

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
     */
    DirectoryEntry getDirectoryEntryReference();
    void setDirectoryEntryReference(DirectoryEntry directoryEntryReference);
    
    /**
     * SENT-BY
     * RFC 5545, 3.2.18, page 27
     * 
     * To specify the calendar user that is acting on behalf of
     * the calendar user specified by the property.
     * 
     * Example:
     * ORGANIZER;SENT-BY="mailto:sray@example.com":mailto:
     *  jsmith@example.com
     */
    SentBy getSentBy();
    void setSentBy(SentBy sentBy);
}
