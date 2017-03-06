package net.balsoftware.icalendar.component;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import net.balsoftware.icalendar.components.VAlarm;
import net.balsoftware.icalendar.components.VComponent;
import net.balsoftware.icalendar.components.VDescribable;
import net.balsoftware.icalendar.components.VDescribable2;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.components.VJournal;
import net.balsoftware.icalendar.components.VTodo;
import net.balsoftware.icalendar.properties.component.descriptive.Attachment;
import net.balsoftware.icalendar.properties.component.descriptive.Summary;

/**
 * Test following components:
 * @see VEvent
 * @see VTodo
 * @see VJournal
 * @see VAlarm
 * 
 * for the following properties:
 * @see Attachment
 * @see Summary
 * 
 * @author David Bal
 *
 */
public class DescribableTest
{
    @Test
    public void canBuildDescribable() throws InstantiationException, IllegalAccessException
    {
        List<VDescribable<?>> components = Arrays.asList(
                new VEvent()
                    .withAttachments(Attachment.parse("ATTACH;FMTTYPE=text/plain;ENCODING=BASE64;VALUE=BINARY:TG9yZW"),
                            Attachment.parse("ATTACH:CID:jsmith.part3.960817T083000.xyzMail@example.com"))
                    .withSummary(new Summary()
                            .withValue("a test summary")
                            .withLanguage("en-USA")),
                new VTodo()
                    .withAttachments(Attachment.parse("ATTACH;FMTTYPE=text/plain;ENCODING=BASE64;VALUE=BINARY:TG9yZW"),
                            Attachment.parse("ATTACH:CID:jsmith.part3.960817T083000.xyzMail@example.com"))
                    .withSummary(Summary.parse("a test summary")
                            .withLanguage("en-USA")),
                new VJournal()
                    .withAttachments(Attachment.parse("ATTACH;FMTTYPE=text/plain;ENCODING=BASE64;VALUE=BINARY:TG9yZW"),
                            Attachment.parse("ATTACH:CID:jsmith.part3.960817T083000.xyzMail@example.com"))
                    .withSummary(Summary.parse("a test summary")
                            .withLanguage("en-USA")),
                new VAlarm()
                    .withAttachments(Attachment.parse("ATTACH;FMTTYPE=text/plain;ENCODING=BASE64;VALUE=BINARY:TG9yZW"),
                            Attachment.parse("ATTACH:CID:jsmith.part3.960817T083000.xyzMail@example.com"))
                    .withSummary(Summary.parse("a test summary")
                            .withLanguage("en-USA"))
                );
        
        for (VDescribable<?> builtComponent : components)
        {
            String componentName = builtComponent.name();            
            String expectedContent = "BEGIN:" + componentName + System.lineSeparator() +
                    "ATTACH;FMTTYPE=text/plain;ENCODING=BASE64;VALUE=BINARY:TG9yZW" + System.lineSeparator() +
                    "ATTACH:CID:jsmith.part3.960817T083000.xyzMail@example.com" + System.lineSeparator() +
                    "SUMMARY;LANGUAGE=en-USA:a test summary" + System.lineSeparator() +
                    "END:" + componentName;

            Attachment<?> a1 = builtComponent.getAttachments().get(0);
            VComponent parsedComponent = builtComponent.getClass().newInstance();
            parsedComponent.parseContent(expectedContent);
            assertEquals(parsedComponent, builtComponent);
            assertEquals(expectedContent, builtComponent.toString());            
        }
    }
    
    @Test
    public void canBuildDescribable2() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
    {
        List<VDescribable2<?>> components = Arrays.asList(
                new VEvent()
                    .withDescription("Sample description"),
                new VTodo()
                    .withDescription("Sample description"),
                new VAlarm()
                    .withDescription("Sample description")
                );
        
        for (VDescribable2<?> builtComponent : components)
        {
            String componentName = builtComponent.name();
            String expectedContent = "BEGIN:" + componentName + System.lineSeparator() +
                    "DESCRIPTION:Sample description" + System.lineSeparator() +
                    "END:" + componentName;
                    
            VComponent parsedComponent = builtComponent.getClass().newInstance();
            parsedComponent.parseContent(expectedContent);
            
            assertEquals(parsedComponent, builtComponent);
            assertEquals(expectedContent, builtComponent.toString());            
        }
    }
}
