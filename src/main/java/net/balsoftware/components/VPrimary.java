package net.balsoftware.components;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.balsoftware.properties.component.descriptive.Comment;
import net.balsoftware.properties.component.time.DateTimeStart;

/**
 * Components with the following properties:
 * COMMENT, DTSTART
 * 
 * @author David Bal
 *
 * @param <T> - implementation subclass
 * @see VEventNewInt
 * @see VTodoInt
 * @see VJournalInt
 * @see VFreeBusy
 * @see VTimeZone
 */
public abstract class VPrimary<T> extends VCommon<T>
{
    /**
     *  COMMENT: RFC 5545 iCalendar 3.8.1.12. page 83
     * This property specifies non-processing information intended
      to provide a comment to the calendar user.
     * Example:
     * COMMENT:The meeting really needs to include both ourselves
         and the customer. We can't hold this meeting without them.
         As a matter of fact\, the venue for the meeting ought to be at
         their site. - - John
     * */
    public List<Comment> getComments() { return comments; }
    private List<Comment> comments;
    public void setComments(List<Comment> comments) { this.comments = comments; }
    public T withComments(List<Comment> comments)
    {
        setComments(comments);
        return (T) this;
    }
    public T withComments(String...comments)
    {
        List<Comment> list = Arrays.stream(comments)
                .map(c -> Comment.parse(c))
                .collect(Collectors.toList());
        setComments(list);
        return (T) this;
    }
    public T withComments(Comment...comments)
    {
    	setComments(new ArrayList<>(Arrays.asList(comments)));
        return (T) this;
    }
    
    /**
     * DTSTART: Date-Time Start, from RFC 5545 iCalendar 3.8.2.4 page 97
     * Start date/time of repeat rule.  Used as a starting point for making the Stream<LocalDateTime> of valid
     * start date/times of the repeating events.
     * Can contain either a LocalDate (DATE) or LocalDateTime (DATE-TIME)
     */
    // hook to be overridden in subclasses
    @Deprecated // is this needed?
    void dateTimeStartListenerHook() { }
    
    public DateTimeStart getDateTimeStart() { return dateTimeStart; }
    private DateTimeStart dateTimeStart;
    public void setDateTimeStart(DateTimeStart dtStart) { this.dateTimeStart = dtStart; }
    public void setDateTimeStart(String dtStart) { setDateTimeStart(DateTimeStart.parse(dtStart)); }
    public void setDateTimeStart(Temporal temporal) { setDateTimeStart(new DateTimeStart(temporal)); }
    public T withDateTimeStart(DateTimeStart dtStart)
    {
        setDateTimeStart(dtStart);
        return (T) this;
    }
    public T withDateTimeStart(String dtStart)
    {
        setDateTimeStart(dtStart);
        return (T) this;
    }
    public T withDateTimeStart(Temporal dtStart)
    {
        setDateTimeStart(dtStart);
        return (T) this;
    }
    
    /** Component is whole day if dateTimeStart (DTSTART) only contains a date (no time) */
    public boolean isWholeDay()
    {
        return ! getDateTimeStart().getValue().isSupported(ChronoUnit.NANOS);
    }
    
    /*
     * CONSTRUCTORS
     */
    VPrimary() { super(); }

    public VPrimary(VPrimary<T> source)
    {
        super(source);
    }
}
