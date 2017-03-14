package net.balsoftware.icalendar.component;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import net.balsoftware.icalendar.components.DaylightSavingTime;
import net.balsoftware.icalendar.components.StandardTime;
import net.balsoftware.icalendar.components.VAlarm;
import net.balsoftware.icalendar.components.VComponent;
import net.balsoftware.icalendar.components.VComponentBase;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.components.VFreeBusy;
import net.balsoftware.icalendar.components.VJournal;
import net.balsoftware.icalendar.components.VTimeZone;
import net.balsoftware.icalendar.components.VTodo;
import net.balsoftware.icalendar.properties.component.misc.NonStandardProperty;

/**
 * Test following components:
 * @see VEvent
 * @see VTodo
 * @see VJournal
 * @see VAlarm
 * @see VFreeBusy
 * @see VTimeZone
 * @see StandardTime
 * @see DaylightSavingTime
 * 
 * for the following properties:
 * @see NonStandardProperty
 * 
 * @author David Bal
 *
 */
public class BaseTest
{
    @Test
    public void canBuildBase() throws InstantiationException, IllegalAccessException
    {
        List<VComponentBase> components = Arrays.asList(
                new VEvent()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid")),
                new VTodo()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid")),
                new VJournal()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid")),
                new VFreeBusy()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid")),
                new VAlarm()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid")),
                new VTimeZone()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid")),
                new DaylightSavingTime()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid")),
                new StandardTime()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid"))
                );
        
        for (VComponentBase builtComponent : components)
        {
            String componentName = builtComponent.name();
            
            String expectedContent = "BEGIN:" + componentName + System.lineSeparator() +
                    "X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au" + System.lineSeparator() +
                    "X-TEST-OBJ:testid" + System.lineSeparator() +
                    "END:" + componentName;

            VComponent parsedComponent = builtComponent.getClass().newInstance();
            parsedComponent.addChild(expectedContent);
            assertEquals(parsedComponent, builtComponent);
            assertEquals(expectedContent, builtComponent.toString());            
        }
    }
}
