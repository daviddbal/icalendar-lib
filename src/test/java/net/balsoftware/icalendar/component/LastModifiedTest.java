package net.balsoftware.icalendar.component;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import net.balsoftware.icalendar.components.VComponent;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.components.VJournal;
import net.balsoftware.icalendar.components.VLastModified;
import net.balsoftware.icalendar.components.VTimeZone;
import net.balsoftware.icalendar.components.VTodo;
import net.balsoftware.icalendar.properties.component.change.LastModified;

/**
 * Test following components:
 * @see VEvent
 * @see VTodo
 * @see VJournal
 * @see VTimeZone
 * 
 * for the following properties:
 * @see LastModified
 * 
 * @author David Bal
 *
 */
public class LastModifiedTest
{
    @Test
    public void canBuildLastModified() throws InstantiationException, IllegalAccessException
    {
        List<VLastModified<?>> components = Arrays.asList(
                new VEvent()
                        .withDateTimeLastModified("20160306T080000Z"),
                new VTodo()
                        .withDateTimeLastModified("20160306T080000Z"),
                new VJournal()
                        .withDateTimeLastModified("20160306T080000Z"),
                new VTimeZone()
                        .withDateTimeLastModified("20160306T080000Z")
                );
        
        for (VLastModified<?> builtComponent : components)
        {
            String componentName = builtComponent.name();            
            String expectedContent = "BEGIN:" + componentName + System.lineSeparator() +
                    "LAST-MODIFIED:20160306T080000Z" + System.lineSeparator() +
                    "END:" + componentName;
                    
            VComponent parsedComponent = builtComponent.getClass().newInstance();
            parsedComponent.parseContent(expectedContent);
            
            assertEquals(parsedComponent, builtComponent);
            assertEquals(expectedContent, builtComponent.toString());            
        }
    }
}
