package net.balsoftware.icalendar.calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import net.balsoftware.icalendar.ICalendarTestAbstract;
import net.balsoftware.icalendar.VCalendar;
import net.balsoftware.icalendar.components.VTodo;
import net.balsoftware.icalendar.properties.calendar.CalendarScale;
import net.balsoftware.icalendar.properties.calendar.ProductIdentifier;
import net.balsoftware.icalendar.properties.calendar.Version;
import net.balsoftware.icalendar.properties.component.time.DateTimeCompleted;

public class CopyCalendarTest extends ICalendarTestAbstract
{
    @Test
    public void canCopyCalendar()
    {
    	DateTimeCompleted.SETTERS.entrySet().forEach(System.out::println);
        VCalendar c = new VCalendar()
                .withProductIdentifier(new ProductIdentifier())
                .withVersion(new Version())
                .withCalendarScale(new CalendarScale())
                .withVTodos(new VTodo()
                        .withDateTimeCompleted("COMPLETED:19960401T150000Z")
                        .withDateTimeDue("TZID=America/Los_Angeles:19960401T050000")
                        .withPercentComplete(35))
                .withVTimeZones(getTimeZone1())
                .withVEvents(getYearly1())
                .withVEvents(getMonthly6());
        VCalendar c2 = new VCalendar(c);
//        System.out.println(c);
        System.out.println(c2);
        assertEquals(c, c2);
        assertFalse(c == c2);
    }
}
