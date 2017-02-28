package net.balsoftware.icalendar.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.DateTimeException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import net.balsoftware.icalendar.components.VAlarm;
import net.balsoftware.icalendar.components.VComponent;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.components.VLocatable;
import net.balsoftware.icalendar.components.VTodo;
import net.balsoftware.icalendar.properties.component.descriptive.Description;
import net.balsoftware.icalendar.properties.component.descriptive.GeographicPosition;
import net.balsoftware.icalendar.properties.component.descriptive.Location;
import net.balsoftware.icalendar.properties.component.descriptive.Priority;
import net.balsoftware.icalendar.properties.component.descriptive.Resources;
import net.balsoftware.icalendar.properties.component.time.DurationProp;

/**
 * Test following components:
 * @see VEvent
 * @see VTodo
 * 
 * for the following properties:
 * @see Description
 * @see GeographicPosition
 * @see DurationProp
 * @see Location
 * @see Priority
 * @see Resources
 * 
 * @author David Bal
 *
 */
public class LocatableTest
{
    @Test
    public void canBuildLocatable() throws InstantiationException, IllegalAccessException
    {
        List<VLocatable<?>> components = Arrays.asList(
                new VEvent()
                    .withDescription("DESCRIPTION:A simple description")
                    .withDuration(Duration.ofMinutes(45))
                    .withGeographicPosition("37.386013;-122.082932")
                    .withLocation("Antarctica")
                    .withPriority(2)
                    .withResources(new Resources("Nettoyeur haute pression")
                            .withLanguage("fr"))
                    .withVAlarms(VAlarm.parse("BEGIN:VALARM" + System.lineSeparator() +
                                     "ACTION:DISPLAY" + System.lineSeparator() +
                                     "DESCRIPTION:Test alarm" + System.lineSeparator() +
                                     "TRIGGER;RELATED=START:-PT30M" + System.lineSeparator() +
                                     "END:VALARM"),
                                 VAlarm.parse("BEGIN:VALARM" + System.lineSeparator() +
                                     "TRIGGER;VALUE=DATE-TIME:19970317T133000Z" + System.lineSeparator() +
                                     "REPEAT:4" + System.lineSeparator() +
                                     "DURATION:PT15M" + System.lineSeparator() +
                                     "ACTION:AUDIO" + System.lineSeparator() +
                                     "ATTACH;FMTTYPE=audio/basic:ftp://example.com/pub/sounds/bell-01.aud" + System.lineSeparator() +
                                     "END:VALARM")),
                new VTodo()
                    .withDescription("DESCRIPTION:A simple description")
                    .withDuration(Duration.ofMinutes(45))
                    .withGeographicPosition("37.386013;-122.082932")
                    .withLocation("Antarctica")
                    .withPriority(2)
                    .withResources(new Resources("Nettoyeur haute pression")
                            .withLanguage("fr"))
                    .withVAlarms(VAlarm.parse("BEGIN:VALARM" + System.lineSeparator() +
                            "ACTION:DISPLAY" + System.lineSeparator() +
                            "DESCRIPTION:Test alarm" + System.lineSeparator() +
                            "TRIGGER;RELATED=START:-PT30M" + System.lineSeparator() +
                            "END:VALARM"),
                        VAlarm.parse("BEGIN:VALARM" + System.lineSeparator() +
                            "TRIGGER;VALUE=DATE-TIME:19970317T133000Z" + System.lineSeparator() +
                            "REPEAT:4" + System.lineSeparator() +
                            "DURATION:PT15M" + System.lineSeparator() +
                            "ACTION:AUDIO" + System.lineSeparator() +
                            "ATTACH;FMTTYPE=audio/basic:ftp://example.com/pub/sounds/bell-01.aud" + System.lineSeparator() +
                            "END:VALARM"))
                );
        
        for (VLocatable<?> builtComponent : components)
        {
            String componentName = builtComponent.name();            
            String expectedContent = "BEGIN:" + componentName + System.lineSeparator() +
                    "DESCRIPTION:A simple description" + System.lineSeparator() +
                    "DURATION:PT45M" + System.lineSeparator() +
                    "GEO:37.386013;-122.082932" + System.lineSeparator() +
                    "LOCATION:Antarctica" + System.lineSeparator() +
                    "PRIORITY:2" + System.lineSeparator() +
                    "RESOURCES;LANGUAGE=fr:Nettoyeur haute pression" + System.lineSeparator() +
                    "BEGIN:VALARM" + System.lineSeparator() +
                    "ACTION:DISPLAY" + System.lineSeparator() +
                    "DESCRIPTION:Test alarm" + System.lineSeparator() +
                    "TRIGGER;RELATED=START:-PT30M" + System.lineSeparator() +
                    "END:VALARM" + System.lineSeparator() +
                    "BEGIN:VALARM" + System.lineSeparator() +
                    "TRIGGER;VALUE=DATE-TIME:19970317T133000Z" + System.lineSeparator() +
                    "REPEAT:4" + System.lineSeparator() +
                    "DURATION:PT15M" + System.lineSeparator() +
                    "ACTION:AUDIO" + System.lineSeparator() +
                    "ATTACH;FMTTYPE=audio/basic:ftp://example.com/pub/sounds/bell-01.aud" + System.lineSeparator() +
                    "END:VALARM" + System.lineSeparator() +
                    "END:" + componentName;

            VComponent parsedComponent = builtComponent.getClass().newInstance();
            parsedComponent.parseContent(expectedContent);
            assertEquals(parsedComponent, builtComponent);
            assertEquals(expectedContent, builtComponent.toString());            
        }
    }
    
    @Test (expected = DateTimeException.class)
    public void canCatchNegativeDuration()
    {
        Thread.setDefaultUncaughtExceptionHandler((t1, e) ->
        {
            throw (RuntimeException) e;
        });
        VEvent vEvent = new VEvent()
                .withDuration(Duration.ofHours(-1))
                .withSummary("test");
        assertNull(vEvent.getDuration());
        assertEquals("test", vEvent.getSummary().getValue());
    }
}
