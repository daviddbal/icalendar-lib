package net.balsoftware.misc;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;

import javafx.collections.FXCollections;
import net.balsoftware.components.VEvent;
import net.balsoftware.parameters.Encoding.EncodingType;
import net.balsoftware.properties.ValueType;
import net.balsoftware.properties.component.descriptive.Attachment;
import net.balsoftware.properties.component.descriptive.Categories;
import net.balsoftware.properties.component.descriptive.Summary;
import net.balsoftware.properties.component.recurrence.ExceptionDates;

public class OrdererTest
{
    @Test
    public void canReplaceListElement()
    {
        VEvent builtComponent = new VEvent()
                .withCategories("category1")
                .withSummary("test");
        builtComponent.withCategories(FXCollections.observableArrayList(new Categories("category3"), new Categories("category4")));
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
        v.withAttachments(FXCollections.observableArrayList(
                Attachment.parse("ATTACH;FMTTYPE=application/postscript:ftp://example.com/reports/r-960812.ps"),
                new Attachment<String>(String.class, "TG9yZW")
                .withFormatType("text/plain")
                .withEncoding(EncodingType.BASE64)
                .withValueType(ValueType.BINARY)
                ));
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
}
