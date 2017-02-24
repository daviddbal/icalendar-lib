package net.balsoftware.properties.component.descriptive;

import net.balsoftware.components.DaylightSavingTime;
import net.balsoftware.components.StandardTime;
import net.balsoftware.components.VEvent;
import net.balsoftware.components.VFreeBusy;
import net.balsoftware.components.VJournal;
import net.balsoftware.components.VTodo;
import net.balsoftware.properties.PropBaseAltText;

/**
 * COMMENT
 * RFC 5545 iCalendar 3.8.1.4. page 83
 * 
 * This property specifies non-processing information intended
 * to provide a comment to the calendar user
 * 
 * Example:
 * COMMENT:The meeting really needs to include both ourselves
 *  and the customer. We can't hold this meeting without them.
 *  As a matter of fact\, the venue for the meeting ought to be at
 *  their site. - - John
 *  
 * @author David Bal
 * 
 * The property can be specified in following components:
 * @see VEvent
 * @see VTodo
 * @see VJournal
 * @see VFreeBusy
 * @see StandardTime
 * @see DaylightSavingTime
 */
public class Comment extends PropBaseAltText<String, Comment>
{   
    public Comment(Comment source)
    {
        super(source);
    }

    public Comment()
    {
        super();
    }
    
    public static Comment parse(String propertyContent)
    {
        Comment property = new Comment();
        property.parseContent(propertyContent);
        return property;
    }
}
