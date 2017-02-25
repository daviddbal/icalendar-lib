package net.balsoftware.component;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import net.balsoftware.components.DaylightSavingTime;
import net.balsoftware.components.StandardTime;
import net.balsoftware.components.VComponent;
import net.balsoftware.components.VEvent;
import net.balsoftware.components.VFreeBusy;
import net.balsoftware.components.VJournal;
import net.balsoftware.components.VPrimary;
import net.balsoftware.components.VTodo;
import net.balsoftware.properties.component.descriptive.Comment;
import net.balsoftware.properties.component.time.DateTimeStart;

/**
 * Test following components:
 * @see VEvent
 * @see VTodo
 * @see VJournal
 * @see VFreeBusy
 * @see StandardTime
 * @see DaylightSavingTime
 * 
 * for the following properties:
 * @see DateTimeStart
 * @see Comment
 * 
 * @author David Bal
 *
 */
public class PrimaryTest
{
    @Test
    public void canBuildPrimary() throws InstantiationException, IllegalAccessException
    {
        List<VPrimary<?>> components = Arrays.asList(
                new VEvent()
                    .withDateTimeStart("20160306T080000")
                    .withComments("This is a test comment", "Another comment")
                    .withComments("COMMENT:My third comment"),
                new VTodo()
                    .withDateTimeStart("20160306T080000")
                    .withComments("This is a test comment", "Another comment")
                    .withComments("COMMENT:My third comment"),
                new VJournal()
                    .withDateTimeStart("20160306T080000")
                    .withComments("This is a test comment", "Another comment")
                    .withComments("COMMENT:My third comment"),
                new VFreeBusy()
                    .withDateTimeStart("20160306T080000")
                    .withComments("This is a test comment", "Another comment")
                    .withComments("COMMENT:My third comment"),
                new DaylightSavingTime()
                    .withDateTimeStart("20160306T080000")
                    .withComments("This is a test comment", "Another comment")
                    .withComments("COMMENT:My third comment"),
                new StandardTime()
                    .withDateTimeStart("20160306T080000")
                    .withComments("This is a test comment", "Another comment")
                    .withComments("COMMENT:My third comment")
                );
        
        for (VPrimary<?> builtComponent : components)
        {
            String componentName = builtComponent.name();
            String expectedContent = "BEGIN:" + componentName + System.lineSeparator() +
                    "DTSTART:20160306T080000" + System.lineSeparator() +
                    "COMMENT:This is a test comment" + System.lineSeparator() +
                    "COMMENT:Another comment" + System.lineSeparator() +
                    "COMMENT:My third comment" + System.lineSeparator() +
                    "END:" + componentName;
                    
            VComponent parsedComponent = builtComponent.getClass().newInstance();
            parsedComponent.parseContent(expectedContent);

            assertEquals(parsedComponent, builtComponent);
            assertEquals(expectedContent, builtComponent.toString());            
        }
    }
    
    @Test
    public void canIgnoreAlreadySet()
    {
        String content = 
            "BEGIN:VEVENT" + System.lineSeparator() +
            "DTSTART:20151109T090000" + System.lineSeparator() +
            "DTSTART:20151119T090000" + System.lineSeparator() +
            "END:VEVENT";
        VEvent v = VEvent.parse(content);
        assertEquals(LocalDateTime.of(2015, 11, 9, 9, 0), v.getDateTimeStart().getValue());
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void canCatchInvalidProperty()
    {
        String content = 
            "BEGIN:VEVENT" + System.lineSeparator() +
            "DTSTART:INVALID" + System.lineSeparator() +
            "END:VEVENT";
        VEvent.parse(content);
    }

}
