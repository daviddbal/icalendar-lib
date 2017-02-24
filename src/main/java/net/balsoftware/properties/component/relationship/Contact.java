package net.balsoftware.properties.component.relationship;

import net.balsoftware.components.VEvent;
import net.balsoftware.components.VFreeBusy;
import net.balsoftware.components.VJournal;
import net.balsoftware.components.VTodo;
import net.balsoftware.properties.PropBaseAltText;

/**
 * CONTACT
 * RFC 5545 iCalendar 3.8.4.2. page 109
 * 
 * This property is used to represent contact information or
 * alternately a reference to contact information associated with the
 * calendar component.
 * 
 * Example:
 * CONTACT:Jim Dolittle\, ABC Industries\, +1-919-555-1234
 * 
 * @author David Bal
 * @see VEvent
 * @see VTodo
 * @see VJournal
 * @see VFreeBusy
 */
public class Contact extends PropBaseAltText<String, Contact>
{   
    public Contact(Contact source)
    {
        super(source);
    }

    public Contact()
    {
        super();
    }
    
    public static Contact parse(String propertyContent)
    {
        Contact property = new Contact();
        property.parseContent(propertyContent);
        return property;
    }
}
