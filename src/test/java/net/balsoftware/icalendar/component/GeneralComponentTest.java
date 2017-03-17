package net.balsoftware.icalendar.component;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

import net.balsoftware.icalendar.ICalendarStaticComponents;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.properties.component.descriptive.Categories;
import net.balsoftware.icalendar.properties.component.descriptive.Classification.ClassificationType;
import net.balsoftware.icalendar.properties.component.descriptive.Description;
import net.balsoftware.icalendar.properties.component.time.DateTimeStart;

public class GeneralComponentTest
{
    @Test
    public void canEscapeTest()
    {
        String contentLine = "DESCRIPTION:a dog\\nran\\, far\\;\\naway \\\\\\\\1";
        Description d = Description.parse(contentLine);
        String expectedValue = "a dog" + System.lineSeparator() +
                               "ran, far;" + System.lineSeparator() +
                               "away \\\\1";
        assertEquals(expectedValue, d.getValue());
        assertEquals(contentLine, d.toString());
    }

    
    @Test
    public void canFoldAndUnfoldLine()
    {
        String line = "Ek and Lorentzon said they would consider halting investment at th,eir headquarters in Stockholm. The pioneering music streaming company employs about 850 people in the city, and more than 1,000 in nearly 30 other offices around the world.";
        VEvent builtComponent = new VEvent()
                .withComments(line);
        String componentName = builtComponent.name();
        String expectedContent = "BEGIN:" + componentName + System.lineSeparator() +
                                 "COMMENT:Ek and Lorentzon said they would consider halting investment at th" + System.lineSeparator() +
                                 " \\,eir headquarters in Stockholm. The pioneering music streaming company em" + System.lineSeparator() +
                                 " ploys about 850 people in the city\\, and more than 1\\,000 in nearly 30 oth" + System.lineSeparator() +
                                 " er offices around the world." + System.lineSeparator() +
                                 "END:" + componentName;
        assertEquals(expectedContent, builtComponent.toString());
        assertEquals(line, builtComponent.getComments().get(0).getValue());
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void canIgnoreSecondAssignment()
    {
        String componentName = "VEVENT";
        String content = "BEGIN:" + componentName + System.lineSeparator() +
                "CLASS:PUBLIC" + System.lineSeparator() +
                "CLASS:PRIVATE" + System.lineSeparator() + // not allowed
                "END:" + componentName;
        VEvent v = VEvent.parse(content);
        String expectedContent = "BEGIN:" + componentName + System.lineSeparator() +
                "CLASS:PUBLIC" + System.lineSeparator() +
                "END:" + componentName;
        assertEquals(expectedContent, v.toString());
    }
    
    @Test
    public void canChangePropertyAllowedOnlyOnce()
    {
        String componentName = "VEVENT";
        String content = "BEGIN:" + componentName + System.lineSeparator() +
                "CLASS:PUBLIC" + System.lineSeparator() +
                "END:" + componentName;
        VEvent madeComponent = VEvent.parse(content);
        madeComponent.setClassification("PRIVATE");
        assertEquals(ClassificationType.PRIVATE, madeComponent.getClassification().getValue());
    }
    
    @Test // tests manually adding a list and individual property to orderer
    public void canSetPropertyOrder()
    {
        String contentLines = 
        		  "BEGIN:VEVENT" + System.lineSeparator()
                + "DTSTAMP:20150110T080000Z" + System.lineSeparator()
                + "DTSTART:20151109T100000Z" + System.lineSeparator()
                + "DTEND:20151109T110000Z" + System.lineSeparator()
                + "UID:20150110T080000-0@jfxtras.org" + System.lineSeparator()
                + "CATEGORIES:group03" + System.lineSeparator()
                + "SUMMARY:DailyUTC Summary" + System.lineSeparator()
                + "DESCRIPTION:DailyUTC Description" + System.lineSeparator()
                + "RRULE:FREQ=DAILY;INTERVAL=2;UNTIL=20151201T100000Z" + System.lineSeparator()
                + "END:VEVENT";
        VEvent builtComponent = VEvent.parse(contentLines);
        builtComponent.setDescription((Description) null);
        Categories category2 = Categories.parse("group05");
        builtComponent.getCategories().add(category2);
        builtComponent.orderChild(category2);
        builtComponent.setClassification(ClassificationType.PRIVATE);
        builtComponent.replaceChild(1, DateTimeStart.parse("20171109T110000Z"));
        
        String contentLines2 = "BEGIN:VEVENT" + System.lineSeparator()
                + "DTSTAMP:20150110T080000Z" + System.lineSeparator()
        		+ "DTSTART:20171109T110000Z" + System.lineSeparator()
                + "DTEND:20151109T110000Z" + System.lineSeparator()
                + "UID:20150110T080000-0@jfxtras.org" + System.lineSeparator()
                + "CATEGORIES:group03" + System.lineSeparator()
                + "SUMMARY:DailyUTC Summary" + System.lineSeparator()
                + "RRULE:FREQ=DAILY;INTERVAL=2;UNTIL=20151201T100000Z" + System.lineSeparator()
                + "CATEGORIES:group05" + System.lineSeparator()
                + "CLASS:PRIVATE" + System.lineSeparator()
                + "END:VEVENT";
        assertEquals(contentLines2, builtComponent.toString());
    }
    
    @Test
    public void canMaintainOrderAfterEdit()
    {
        VEvent vevent = ICalendarStaticComponents.getDaily1()
                .withExceptionDates(LocalDateTime.of(2015, 11, 19, 9, 30));
        DateTimeStart dtStart = new DateTimeStart(LocalDateTime.of(2015, 11, 9, 9, 30));
        vevent.setDateTimeStart(dtStart);
        assertEquals(dtStart, vevent.childrenUnmodifiable().get(1));
    }
}
