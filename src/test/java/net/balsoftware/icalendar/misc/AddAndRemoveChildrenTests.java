package net.balsoftware.icalendar.misc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

import org.junit.Test;

import net.balsoftware.icalendar.ICalendarStaticComponents;
import net.balsoftware.icalendar.components.VAlarm;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.parameters.Language;
import net.balsoftware.icalendar.properties.component.alarm.Action;
import net.balsoftware.icalendar.properties.component.alarm.Action.ActionType;
import net.balsoftware.icalendar.properties.component.alarm.RepeatCount;
import net.balsoftware.icalendar.properties.component.alarm.Trigger;
import net.balsoftware.icalendar.properties.component.descriptive.Categories;
import net.balsoftware.icalendar.properties.component.descriptive.Comment;
import net.balsoftware.icalendar.properties.component.descriptive.Summary;
import net.balsoftware.icalendar.properties.component.relationship.Attendee;
import net.balsoftware.icalendar.properties.component.time.DateTimeStart;

public class AddAndRemoveChildrenTests
{
    @Test
    public void canAddIndividualChildren()
    {
        VEvent v = new VEvent();
        Summary summary = Summary.parse("test summary");
		v.addChild(summary);
        summary.addChild(Language.parse("en-US"));

        String expectedContent = 
        		"BEGIN:VEVENT" + System.lineSeparator() +
        		"SUMMARY;LANGUAGE=en-US:test summary" + System.lineSeparator() +
        		"END:VEVENT";
        assertEquals(expectedContent, v.toString());        
    }
    
    @Test
    public void canAddListChildren()
    {
        VEvent v = new VEvent();
        v.addChild(Comment.parse("comment1"));
        VAlarm alarm = new VAlarm()
                .withAction(new Action(ActionType.DISPLAY))
                .withAttendees(Attendee.parse("mailto:jsmith@example.com"))
                .withDuration(Period.ofDays(-2))
                .withRepeatCount(new RepeatCount(2));
        v.addChild(alarm);
        v.addChild(Comment.parse("comment2"));
        alarm.addChild(new Trigger<Duration>(Duration.ofMinutes(-15)));
        v.addChild(new DateTimeStart(LocalDate.of(2017, 1, 1)));
        String expectedContent = 
        		"BEGIN:VEVENT" + System.lineSeparator() +
        		"COMMENT:comment1" + System.lineSeparator() +
        		"BEGIN:VALARM" + System.lineSeparator() +
        		"ACTION:DISPLAY" + System.lineSeparator() +
        		"ATTENDEE:mailto:jsmith@example.com" + System.lineSeparator() +
        		"DURATION:-P2D" + System.lineSeparator() +
        		"REPEAT:2" + System.lineSeparator() +
        		"TRIGGER:-PT15M" + System.lineSeparator() +
        		"END:VALARM" + System.lineSeparator() +
        		"COMMENT:comment2" + System.lineSeparator() +
        		"DTSTART;VALUE=DATE:20170101" + System.lineSeparator() +
        		"END:VEVENT";
        assertEquals(expectedContent, v.toString());        
    }
    
    @Test
    public void canRemoveIndividualChildren()
    {
        VEvent v = ICalendarStaticComponents.getDaily1();
        Summary summary = v.getSummary();
        v.removeChild(summary);
        assertNull(v.getSummary());

    }
    
    @Test
    public void canRemoveListChildren()
    {
        VEvent v = ICalendarStaticComponents.getDaily1();
        Categories category = v.getCategories().get(0);
        v.removeChild(category);
        assertTrue(v.getCategories().isEmpty());
    }
}
