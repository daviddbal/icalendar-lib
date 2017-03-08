package net.balsoftware.icalendar.misc;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.parameters.Encoding.EncodingType;
import net.balsoftware.icalendar.properties.ValueType;
import net.balsoftware.icalendar.properties.component.descriptive.Attachment;
import net.balsoftware.icalendar.properties.component.descriptive.Categories;
import net.balsoftware.icalendar.properties.component.descriptive.Summary;
import net.balsoftware.icalendar.properties.component.recurrence.ExceptionDates;
import net.balsoftware.icalendar.properties.component.recurrence.RecurrenceDates;
import net.balsoftware.icalendar.properties.component.recurrence.RecurrenceRule;

public class OrdererTest
{
    @Test
    public void canReplaceListElement()
    {
        VEvent builtComponent = new VEvent()
                .withCategories("category1")
                .withSummary("test");
        Categories c1 = new Categories("category3");
		Categories c2 = new Categories("category4");
		builtComponent.setCategories(Arrays.asList(c1, c2));
		builtComponent.orderChild(0, c1);
		builtComponent.orderChild(2, c2);
        assertEquals(2, builtComponent.getCategories().size());
        String expectedContent = "BEGIN:VEVENT" + System.lineSeparator() +
                "CATEGORIES:category3" + System.lineSeparator() +
                "SUMMARY:test" + System.lineSeparator() +
                "CATEGORIES:category4" + System.lineSeparator() +
                "END:VEVENT";
        VEvent expectedVEvent = VEvent.parse(expectedContent);
        assertEquals(expectedVEvent, builtComponent);
    }

    @Test
    public void canReplaceListElement2()
    {
        VEvent v = new VEvent()
                .withAttachments(new Attachment<URI>(URI.class, "ATTACH:CID:jsmith.part3.960817T083000.xyzMail@example.com"))
                .withSummary("test")
                .withAttachments(Attachment.parse("ATTACH;FMTTYPE=text/plain;ENCODING=BASE64;VALUE=BINARY:TG9yZW"));
        Attachment<?> a1 = Attachment.parse("ATTACH;FMTTYPE=application/postscript:ftp://example.com/reports/r-960812.ps");
        Attachment<?> a2 = new Attachment<String>(String.class, "TG9yZW")
        .withFormatType("text/plain")
        .withEncoding(EncodingType.BASE64)
        .withValueType(ValueType.BINARY);
		v.withAttachments(Arrays.asList(
                a1,
                a2));
		v.orderChild(0,a1);
		v.orderChild(2,a2);
        String expectedContent = "BEGIN:VEVENT" + System.lineSeparator() +
                "ATTACH;FMTTYPE=application/postscript:ftp://example.com/reports/r-960812.ps" + System.lineSeparator() +
                "SUMMARY:test" + System.lineSeparator() +
                "ATTACH;FMTTYPE=text/plain;ENCODING=BASE64;VALUE=BINARY:TG9yZW" + System.lineSeparator() +
                "END:VEVENT";
        VEvent expectedVEvent = VEvent.parse(expectedContent);
        assertEquals(expectedVEvent, v);
    }
    
    @Test
    public void canReplaceIndividualElement()
    {
        VEvent v = new VEvent()
                .withComments("dogs")
                .withSummary("test")
                .withDescription("cats");
        v.setSummary("birds");
        String expectedContent = "BEGIN:VEVENT" + System.lineSeparator() +
                "COMMENT:dogs" + System.lineSeparator() +
                "SUMMARY:birds" + System.lineSeparator() +
                "DESCRIPTION:cats" + System.lineSeparator() +
                "END:VEVENT";
        VEvent expectedVEvent = VEvent.parse(expectedContent);
        assertEquals(expectedVEvent, v);
    }
    
    @Test
    public void canRemoveIndividualElement()
    {
        VEvent v = new VEvent()
                .withComments("dogs")
                .withSummary("test")
                .withDescription("cats");
        v.setSummary((Summary) null);
        String expectedContent = "BEGIN:VEVENT" + System.lineSeparator() +
                "COMMENT:dogs" + System.lineSeparator() +
                "DESCRIPTION:cats" + System.lineSeparator() +
                "END:VEVENT";
        VEvent expectedVEvent = VEvent.parse(expectedContent);
        assertEquals(expectedVEvent, v);
    }
    
    @Test
    public void canAutoOrderListProperty()
    {
        VEvent v = new VEvent().withExceptionDates(LocalDate.of(2016, 4, 27));
        v.setSummary("here:");
        ExceptionDates e2 = new ExceptionDates(LocalDateTime.of(2016, 4, 28, 12, 0));
        v.getExceptionDates().add(e2);
        ExceptionDates e3 = new ExceptionDates(LocalDateTime.of(2016, 4, 29, 12, 0));
        v.getExceptionDates().add(e3);
        String expectedContent = "BEGIN:VEVENT" + System.lineSeparator() +
        		"EXDATE;VALUE=DATE:20160427" + System.lineSeparator() +
        		"EXDATE:20160428T120000" + System.lineSeparator() +
        		"EXDATE:20160429T120000" + System.lineSeparator() +
        		"SUMMARY:here:" + System.lineSeparator() +
        		"END:VEVENT";
        assertEquals(expectedContent, v.toString());
    }
    
    @Test
    public void canManuallyOrderListProperty()
    {
        VEvent v = new VEvent().withExceptionDates(LocalDate.of(2016, 4, 27));
        v.setSummary("here:");
        ExceptionDates e2 = new ExceptionDates(LocalDateTime.of(2016, 4, 28, 12, 0));
        v.getExceptionDates().add(e2);
        v.orderChild(e2);
        String expectedContent = "BEGIN:VEVENT" + System.lineSeparator() +
        		"EXDATE;VALUE=DATE:20160427" + System.lineSeparator() +
        		"SUMMARY:here:" + System.lineSeparator() +
        		"EXDATE:20160428T120000" + System.lineSeparator() +
        		"END:VEVENT";
        assertEquals(expectedContent, v.toString());
    }
    
    @Test // can remove a property and avoid null pointer with toString
    public void canRemoveProperty()
    {
        VEvent vComponent = new VEvent()
                .withSummary("example")
                .withRecurrenceRule("RRULE:FREQ=DAILY");
        vComponent.setRecurrenceRule((RecurrenceRule) null); // remove Recurrence Rule
        assertEquals(1, vComponent.childrenUnmodifiable().size());
        String expectedContent = "BEGIN:VEVENT" + System.lineSeparator() +
                                 "SUMMARY:example" + System.lineSeparator() +
                                 "END:VEVENT";
        assertEquals(expectedContent, vComponent.toString());
    }
    
    @Test // can remove a property and avoid null pointer with toString
    public void canRemoveListProperty()
    {
        VEvent vComponent = new VEvent()
                .withSummary("example")
                .withRecurrenceDates("RDATE;VALUE=DATE:19970304,19970504,19970704,19970904");
        List<RecurrenceDates> r = new ArrayList<>(vComponent.getRecurrenceDates());
        r.forEach(c -> vComponent.removeChild(c));
        System.out.println(vComponent);
//        vComponent.setRecurrenceDates(null); // remove Recurrence Rule
        assertEquals(1, vComponent.childrenUnmodifiable().size());
        String expectedContent = "BEGIN:VEVENT" + System.lineSeparator() +
                                 "SUMMARY:example" + System.lineSeparator() +
                                 "END:VEVENT";
        assertEquals(expectedContent, vComponent.toString());
    }
    
    @Test // shows removing null property does nothing
    public void canRemoveEmptyProperty()
    {
        VEvent vComponent = new VEvent();
        vComponent.setRecurrenceRule((RecurrenceRule) null); // remove null Recurrence Rule
        assertEquals(0, vComponent.childrenUnmodifiable().size());
        String expectedContent = "BEGIN:VEVENT" + System.lineSeparator() +
                                 "END:VEVENT";
        assertEquals(expectedContent, vComponent.toString());
    }
}
