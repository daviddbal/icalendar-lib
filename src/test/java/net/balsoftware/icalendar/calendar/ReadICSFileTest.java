package net.balsoftware.icalendar.calendar;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import net.balsoftware.icalendar.VCalendar;
import net.balsoftware.icalendar.utilities.UnfoldingStringIterator;

public class ReadICSFileTest
{
    @Test
    public void canReadICSFile1() throws IOException
    {
        String fileName = "Yahoo_Sample_Calendar.ics";
        URL url = getClass().getResource(fileName);
        Path icsFilePath = Paths.get(url.getFile());
//        VCalendar vCalendar = VCalendar.parse(icsFilePath);
//        System.out.println(vCalendar.toString());
        boolean useResourceStatus = false;
        long t1 = System.currentTimeMillis();
        VCalendar vCalendar = VCalendar.parseICalendarFile(icsFilePath, useResourceStatus);
        long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);
        
        t1 = System.currentTimeMillis();
        String v = vCalendar.toString();
        t2 = System.currentTimeMillis();
        System.out.println(t2-t1);
        
        // HAS REQUEST-STATUS IN LINES - NEED TO REMOVE
        assertEquals(7641, vCalendar.toString().length());
        assertEquals(7, vCalendar.getVEvents().size());
        assertEquals(1, vCalendar.getVTimeZones().size());
        int subcomponents = vCalendar.getVTimeZones().get(0).getStandardOrDaylight().size();
        assertEquals(9, subcomponents);
    }
    
    @Test
    public void canReadICSFile2() throws IOException
    {
//        VCalendar myCalendar = new VCalendar();
//        ICalendarAgenda myAgenda = new ICalendarAgenda(myCalendar);
//        myAgenda.setOrganizer(Organizer.parse("mailto:david@balsoftware.net"));
        String fileName = "mathBirthdays.ics";       
        URL url = getClass().getResource(fileName);
        Path icsFilePath = Paths.get(url.getFile());
        BufferedReader br = Files.newBufferedReader(icsFilePath);
        
//        Iterator<String> unfoldedLineIterator = br.lines().iterator();
//        UnfoldingStringIterator unfoldedLineIterator = new UnfoldingStringIterator(br.lines().iterator());
        UnfoldingStringIterator unfoldedLineIterator = new UnfoldingStringIterator(br.lines().iterator());
        List<String> expectedLines = new ArrayList<>();
        unfoldedLineIterator.forEachRemaining(line -> expectedLines.add(line));
        br.close();
        String content = expectedLines.stream().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(content.length());
        long t1 = System.currentTimeMillis();
        VCalendar v = VCalendar.parse(content);
        long t2 = System.currentTimeMillis();
        System.out.println("lines:"+v.childrenUnmodifiable().size());
        System.out.println(v);
        System.out.println(t2-t1);
        System.exit(0);
//        expectedLines.forEach(System.out::println);
        
        boolean useResourceStatus = false;
        VCalendar vCalendar = VCalendar.parse(icsFilePath);
        System.out.println("done");
        Iterator<String> contentIterator = Arrays.stream(vCalendar.toString().split(System.lineSeparator())).iterator();
        UnfoldingStringIterator unfoldedContentLineIterator = new UnfoldingStringIterator(contentIterator);
        List<String> contentLines = new ArrayList<>();
        unfoldedContentLineIterator.forEachRemaining(line -> contentLines.add(line));
        assertEquals(expectedLines, contentLines);
        assertEquals(13217, expectedLines.size());
    }
}
