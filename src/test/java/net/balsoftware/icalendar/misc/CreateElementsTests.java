package net.balsoftware.icalendar.misc;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import net.balsoftware.icalendar.Elements;
import net.balsoftware.icalendar.VChild;
import net.balsoftware.icalendar.properties.VProperty;
import net.balsoftware.icalendar.properties.component.descriptive.Summary;

public class CreateElementsTests
{   
//    @Test
//    public void canParseMultilineElement()
//    {
//        String expectedContent =
//                "BEGIN:VCALENDAR" + System.lineSeparator() + 
//                "VERSION:2.0" + System.lineSeparator() + 
//                "BEGIN:VEVENT" + System.lineSeparator() +
//                "CATEGORIES:group05" + System.lineSeparator() +
//                "DTSTART:20151108T100000" + System.lineSeparator() +
//                "DTEND:20151108T110000" + System.lineSeparator() +
//                "DESCRIPTION:Daily1 Description" + System.lineSeparator() +
//                "SUMMARY:revised summary" + System.lineSeparator() +
//                "DTSTAMP:20150110T080000Z" + System.lineSeparator() +
//                "UID:20150110T080000-004@jfxtras.org" + System.lineSeparator() +
//                "RRULE:FREQ=DAILY" + System.lineSeparator() +
//                "SEQUENCE:1" + System.lineSeparator() +
//                "ORGANIZER;CN=Papa Smurf:mailto:papa@smurf.org" + System.lineSeparator() +
//                "END:VEVENT" + System.lineSeparator() + 
//                "END:VCALENDAR";
//        Iterator<String> i = Arrays.asList(expectedContent.split(System.lineSeparator())).iterator();
//        VCalendar v = v.parseContent(i, false);
////        VCalendar v = (VCalendar) Elements.parseNewElement(VCalendar.class, "VCALENDAR", expectedContent);
//    	assertEquals(expectedContent, v.toString());
//    	assertEquals(v, VCalendar.parse(expectedContent));
//    }   
    
    @Test
    public void canUseParseFactory() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
    	Method parseMethod = Summary.class.getMethod("parse", String.class);
    	String content = "test summary";
		Summary s = (Summary) parseMethod.invoke(null, content);
    	assertEquals("SUMMARY:" + content, s.toString());
    	assertEquals(s, Summary.parse(content));
    }
    
    @Test
    public void canMakeEmptySummary()
    {
    	VChild s = Elements.newEmptyVElement(VProperty.class, "SUMMARY");
    	assertEquals(s, new Summary());
    }
    
//    @Test
//    public void canParseNewSummary()
//    {
//    	String content = "test summary";
//    	VElement s = Elements.parseNewElement(VProperty.class, "SUMMARY", "SUMMARY:" + content);
//    	assertEquals(s, Summary.parse(content));
//    }
    
    @Test
    public void canParseNewSummary2()
    {
    	String content = "SUMMARY:test summary";
    	Summary s = Summary.parse(content);
    	System.out.println(s);
    }
    
    @Test
    public void canParseNewSummary3()
    {
    	String content = "SUMMARY;LANGUAGE=en:Department Party";
    	Summary s = new Summary();
    	s.addChild(content);
    	System.out.println(s);
    }
}
