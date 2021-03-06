<!DOCTYPE html>
<html lang="en-US">
<head>
	<link rel="stylesheet" type="text/css" href="icalendar.css">
</head>
<body>
<h2 style="text-align: center;">iCalendar-lib</h2>
<h3>Summary</h3>
<p>Written in Java, iCalendar-lib is an open-source iCalendar library for Java 8.  It implements the <a href="https://tools.ietf.org/html/rfc5545">RFC 5545</a> standard.</p>
<p>A default iCalendar Transport-Independent Interoperability Protocol (iTIP) factory is included that implements part of the <a href="https://tools.ietf.org/html/rfc5546">RFC 5546</a> iTIP protocol standard (single-user methods).  The client code an use a replacement factory to implement all of the iTIP protocol methods.</p>
<h3>Usage</h3>
<p>The top-level iCalendar object is a VCALENDAR.  The VCALENDAR contains all calendaring and scheduling information.  Typically, this information will consist of an iCalendar stream with a single iCalendar object.  However, multiple iCalendar objects can be sequentially grouped together in an iCalendar stream.  The first line and last line of the iCalendar object MUST contain a pair of iCalendar object delimiter strings.  The syntax for an iCalendar stream is as follows:</p>
<p>"BEGIN" ":" "VCALENDAR" CRLF<br /> icalbody<br /> "END" ":" "VCALENDAR" CRLF</p>
<p>The following is a simple example of an iCalendar object:</p>
<p style="padding-left: 30px;">BEGIN:VCALENDAR<br /> VERSION:2.0<br /> PRODID:-//hacksw/handcal//NONSGML v1.0//EN<br /> BEGIN:VEVENT<br /> UID:19970610T172345Z-AF23B2@example.com<br /> DTSTAMP:19970610T172345Z<br /> DTSTART:19970714T170000Z<br /> DTEND:19970715T040000Z<br /> SUMMARY:Bastille Day Party<br /> END:VEVENT<br /> END:VCALENDAR</p>
<p>During normal usage, the iCalendar text should be obtained through some I/O mechanism rather than from a hard-coded string.  However, for an example the hard-coded string is sufficient.  A VCalendar object can be created by parsing the string as follows:</p>
<pre>String content =
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
VCalendar vCalendar = VCalendar.parse(content);</pre>
<p>The same VCALENDAR object can also be build by chaining.  Note that the date/time properties can be constructed with either a full property line string (as in DateTimeStamp), the property value string (as in DateTimeStart) or the wrapped object value (as in DateTimeEnd).  The following object is equal to the one above.</p>
<pre>VCalendar vCalendar2 = new VCalendar()
    .withVersion(new Version()) // default is version 2.0
    .withProductIdentifier("-//hacksw/handcal//NONSGML v1.0//EN")
    .withVEvents(new VEvent()
        .withUniqueIdentifier("UID:19970610T172345Z-AF23B2@example.com")
        .withDateTimeStamp("DTSTAMP:19970610T172345Z")
        .withDateTimeStart("19970714T170000Z")
        .withDateTimeEnd(ZonedDateTime.of(LocalDateTime.of(1997, 7, 15, 4, 0), ZoneId.of("Z")))
        .withSummary("Bastille Day Party")
);</pre>
<h3>Process iTIP Messages</h3>
<p>As defined in RFC 4446 iTIP messages can be processed to change a VCalendar.   This is done by using the processITIPMessage method in a VCalendar.  The following example publishes (adds) a VEVENT.</p>
<pre>VCalendar main = new VCalendar();
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
main.processITIPMessage(itip);</pre>
<p>The default iTIP factory supports the PUBLISH, REQUEST and CANCEL methods for a single calendar user, the organizer, (no attendees).  The client can extend the abstract iTIP factory to support other methods.</p>
<h3>Generate iTIP Messages</h3>
<p>The API doesn't generate iTIP messages.  That feature is done by the client.  However, all calendar elements (e.g. VCALENDAR, VEVENT, SUMMARY, etc.) have a toContent method that produces a string containing all of the components, properties and parameters property folded and formatted as described in RFC 5545.  For example, the output of vCalendar.toContent() from the first example produces the same string that was parsed to produce it.  This string can be modified by the client code to produce the desired iTIP message.</p>
<h3>Recurrence Rule</h3>
<p>The VEVENT, VTODO and VJOURNAL components as well as VTIMEZONE subcomponent's STANDARD and DAYLIGHT can contain the RRULE property.  The RRULE defines a series of date/time recurrence value.  The API represents the series by a Java 8 Stream.  Some RRULE's define an unending series of date/times.  The lazy nature of a Java 8 Stream is ideal for this case because the stream is both unending and does not generate an infinite list of values; values are made only as needed.  A client needs to set the start and limit the end of the date/time value generation to obtain desired subset of values.   The below example shows its usage:</p>
<pre>// Every other week on Monday, Wednesday, and Friday starting on Monday, January 2, 2017:
RecurrenceRule2 rrule = RecurrenceRule2.parse("FREQ=WEEKLY;INTERVAL=2;BYDAY=MO,WE,FR");
rrule.streamRecurrences(LocalDate.of(2017, 1, 2))
        .limit(10)
        .forEach(System.out::println);</pre>
<p>produces the following output:</p>
<pre>2017-01-02
2017-01-04
2017-01-06
2017-01-16
2017-01-18
2017-01-20
2017-01-30
2017-02-01
2017-02-03
2017-02-13</pre>
<p>Note: Java 9 introduces the takeWhile method that can more easily limit the end of date/time recurrence values.</p>
<h3>Import iCalendar</h3>
<p>ICalendar data can be imported a number of ways.</p>
<ul>
<li>Parsing a string<em>.</em>
<ul>
<li>Example: VCalendar myCalendar = VCalendar.parse(calendarString);</li>
</ul>
</li>
<li>Read an .ics file<em>.</em>
<ul>
<li>Example: VCalendar myCalendar = VCalendar.parseICalendarFile(icsFilePath);</li>
</ul>
</li>
<li>Process an iTIP PUBLISH message into an empty VCalendar.
<ul>
<li>Example: VCalendar myCalendar = new VCalendar().processITIPMessage(iTIPPublishMessage);</li>
</ul>
</li>
</ul>
<h3>Export iCalendar</h3>
<p>The API does not specifically provide a mechanism to export iCalendar data.  However, the <em>toContent </em>method provides a string containing the calendar content.  That string can easily exported by the client program.</p>
<p>See <a href="https://github.com/JFXtras/jfxtras/tree/9.0/jfxtras-icalendaragenda">iCalendarAgenda</a>, a JavaFx Control that displays and edits iCalendar VCALENDAR objects.</p>

<p>For more information please see my YouTube videos:</p>

<iframe width="560" height="315" src="https://www.youtube.com/embed/videoseries?list=PLGEK8X_aQRqW8HIK3PRBPkuOjD7Z_CeK6" frameborder="0" allowfullscreen>
</iframe>
<iframe width="560" height="315" src="https://www.youtube.com/embed/videoseries?list=PLGEK8X_aQRqW8HIK3PRBPkuOjD7Z_CeK6" frameborder="0" allowfullscreen>
</iframe>

<p style="text-align: center;"><a href="https://github.com/daviddbal/icalendar-lib"><img title="iCalendar-lib at GitHub" src="GitHub-Mark-64px.png" alt="iCalendar-lib at GibHub" width="64" height="64" /></a></p>
<p style="text-align: center;"><a href="https://github.com/daviddbal/icalendar-lib"><strong>iCalendar-lib at GitHub</strong></a></p>
<p>See the <a href="http://balsoftware.net/rrule/">RRULE calculator</a> that uses this library as an example of it's capabilities</p>
</body>
</html>

