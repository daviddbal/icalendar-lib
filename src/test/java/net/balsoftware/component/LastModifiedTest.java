package net.balsoftware.component;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import net.balsoftware.components.VComponent;
import net.balsoftware.components.VEvent;
import net.balsoftware.components.VJournal;
import net.balsoftware.components.VLastModified;
import net.balsoftware.components.VTimeZone;
import net.balsoftware.components.VTodo;
import net.balsoftware.properties.component.change.LastModified;

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
