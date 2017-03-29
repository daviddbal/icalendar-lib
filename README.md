# iCalendar-lib
Summary

Written in Java, iCalendar-lib is an open-source iCalendar library for Java 8. It implements the RFC 5545 standard.

A default iCalendar Transport-Independent Interoperability Protocol (iTIP) factory is included that implements part of the RFC 5546 iTIP protocol standard (single-user methods). The client code an use a replacement factory to implement all of the iTIP protocol methods.
Usage

The top-level iCalendar object is a VCALENDAR. The VCALENDAR contains all calendaring and scheduling information. Typically, this information will consist of an iCalendar stream with a single iCalendar object. However, multiple iCalendar objects can be sequentially grouped together in an iCalendar stream. The first line and last line of the iCalendar object MUST contain a pair of iCalendar object delimiter strings. The syntax for an iCalendar stream is as follows:

"BEGIN" ":" "VCALENDAR" CRLF
icalbody
"END" ":" "VCALENDAR" CRLF

The following is a simple example of an iCalendar object:

BEGIN:VCALENDAR
VERSION:2.0
PRODID:-//hacksw/handcal//NONSGML v1.0//EN
BEGIN:VEVENT
UID:19970610T172345Z-AF23B2@example.com
DTSTAMP:19970610T172345Z
DTSTART:19970714T170000Z
DTEND:19970715T040000Z
SUMMARY:Bastille Day Party
END:VEVENT
END:VCALENDAR

During normal usage, the iCalendar text should be obtained through some I/O mechanism rather than from a hard-coded string. However, for an example the hard-coded string is sufficient. A VCalendar object can be created by parsing the string as follows:

String content =
    "BEGIN:VCALENDAR" + System.lineSeparator() +
    "VERSION:2.0" + System.lineSeparator() +
    "PRODID:-//hacksw/handcal//NONSGML v1.0//EN" + System.lineSeparator() +
    "BEGIN:VEVENT" + System.lineSeparator() +
    "UID:19970610T172345Z-AF23B2@example.com" + System.lineSeparator() +
    "DTSTAMP:19970610T172345Z" + System.lineSeparator() +
    "DTSTART:19970714T170000Z" + System.lineSeparator() +
    "DTEND:19970715T040000Z" + System.lineSeparator() +
    "SUMMARY:Bastille Day Party" + System.lineSeparator() +
    "END:VEVENT" + System.lineSeparator() +
    "END:VCALENDAR";
VCalendar vCalendar = VCalendar.parse(content);

The same VCALENDAR object can also be build by chaining. Note that the date/time properties can be constructed with either a full property line string (as in DateTimeStamp), the property value string (as in DateTimeStart) or the wrapped object value (as in DateTimeEnd). The following object is equal to the one above.

VCalendar vCalendar2 = new VCalendar()
    .withVersion(new Version()) // default is version 2.0
    .withProductIdentifier("-//hacksw/handcal//NONSGML v1.0//EN")
    .withVEvents(new VEvent()
        .withUniqueIdentifier("UID:19970610T172345Z-AF23B2@example.com")
        .withDateTimeStamp("DTSTAMP:19970610T172345Z")
        .withDateTimeStart("19970714T170000Z")
        .withDateTimeEnd(ZonedDateTime.of(LocalDateTime.of(1997, 7, 15, 4, 0), ZoneId.of("Z")))
        .withSummary("Bastille Day Party")
);

Process iTIP Messages

As defined in RFC 4446 iTIP messages can be processed to change a VCalendar. This is done by using the processITIPMessage method in a VCalendar. The following example publishes (adds) a VEVENT.

VCalendar main = new VCalendar();
String publish = "BEGIN:VCALENDAR" + System.lineSeparator() + 
      "METHOD:PUBLISH" + System.lineSeparator() + 
      "PRODID:-//Example/ExampleCalendarClient//EN" + System.lineSeparator() + 
      "VERSION:2.0" + System.lineSeparator() + 
      "BEGIN:VEVENT" + System.lineSeparator() + 
      "ORGANIZER:mailto:a@example.com" + System.lineSeparator() + 
      "DTSTART:19970701T200000Z" + System.lineSeparator() + 
      "DTSTAMP:19970611T190000Z" + System.lineSeparator() + 
      "SUMMARY:ST. PAUL SAINTS -VS- DULUTH-SUPERIOR DUKES" + System.lineSeparator() + 
      "UID:0981234-1234234-23@example.com" + System.lineSeparator() + 
      "END:VEVENT" + System.lineSeparator() + 
      "END:VCALENDAR";
VCalendar itip = VCalendar.parse(publish);
main.processITIPMessage(itip);

The default iTIP factory supports the PUBLISH, REQUEST and CANCEL methods for a single calendar user, the organizer, (no attendees). The client can extend the abstract iTIP factory to support other methods.
Generate iTIP Messages

The API doesn't generate iTIP messages. That feature is done by the client. However, all calendar elements (e.g. VCALENDAR, VEVENT, SUMMARY, etc.) have a toContent method that produces a string containing all of the components, properties and parameters property folded and formatted as described in RFC 5545. For example, the output of vCalendar.toContent() from the first example produces the same string that was parsed to produce it. This string can be modified by the client code to produce the desired iTIP message.
Recurrence Rule

The VEVENT, VTODO and VJOURNAL components as well as VTIMEZONE subcomponent's STANDARD and DAYLIGHT can contain the RRULE property. The RRULE defines a series of date/time recurrence value. The API represents the series by a Java 8 Stream. Some RRULE's define an unending series of date/times. The lazy nature of a Java 8 Stream is ideal for this case because the stream is both unending and does not generate an infinite list of values; values are made only as needed. A client needs to set the start and limit the end of the date/time value generation to obtain desired subset of values. The below example shows its usage:

// Every other week on Monday, Wednesday, and Friday starting on Monday, January 2, 2017:
RecurrenceRule2 rrule = RecurrenceRule2.parse("FREQ=WEEKLY;INTERVAL=2;BYDAY=MO,WE,FR");
rrule.streamRecurrences(LocalDate.of(2017, 1, 2))
        .limit(10)
        .forEach(System.out::println);

produces the following output:

2017-01-02
2017-01-04
2017-01-06
2017-01-16
2017-01-18
2017-01-20
2017-01-30
2017-02-01
2017-02-03
2017-02-13

Note: Java 9 introduces the takeWhile method that can more easily limit the end of date/time recurrence values.
Import iCalendar

ICalendar data can be imported a number of ways.

    Parsing a string.
        Example: VCalendar myCalendar = VCalendar.parse(calendarString);
    Read an .ics file.
        Example: VCalendar myCalendar = VCalendar.parseICalendarFile(icsFilePath);
    Process an iTIP PUBLISH message into an empty VCalendar.
        Example: VCalendar myCalendar = new VCalendar().processITIPMessage(iTIPPublishMessage);

Export iCalendar

The API does not specifically provide a mechanism to export iCalendar data. However, the toContent method provides a string containing the calendar content. That string can easily exported by the client program.

See iCalendarAgenda, a JavaFx Control that displays and edits iCalendar VCALENDAR objects.

For more information please see my YouTube videos:
