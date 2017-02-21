package net.balsoftware.properties.calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import jfxtras.icalendarfx.ICalendarTestAbstract;
import net.balsoftware.VCalendar;
import net.balsoftware.components.VTodo;
import net.balsoftware.properties.calendar.CalendarScale;
import net.balsoftware.properties.calendar.ProductIdentifier;
import net.balsoftware.properties.calendar.Version;

public class CopyCalendarTest extends ICalendarTestAbstract
{
    @Test
    public void canCopyCalendar()
    {
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
        assertEquals(c, c2);
        assertFalse(c == c2);
    }
}
