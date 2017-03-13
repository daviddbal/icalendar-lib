package net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx;
import static java.time.temporal.ChronoUnit.MONTHS;

import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.balsoftware.icalendar.VElement;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.RRuleElementType;

/** BYMONTH from RFC 5545, iCalendar 3.3.10, page 42 */
public class ByMonth extends ByRuleAbstract<Month, ByMonth>
{
    @Override
    public ChronoUnit getChronoUnit() { return ChronoUnit.MONTHS; }
    /** sorted array of months to be included
     * January = 1 - December = 12
     * Uses a varargs parameter to allow any number of months
     */
//    public Month[] getValue() { return months; }
//    private Month[] months;
//    private void setMonths(Month... months) { this.months = months; }
//    public void setValue(Month... months)
//    {
//        setValue(FXCollections.observableArrayList(months));
//    }
//    public void setValue(String months)
//    {
//        parseContent(months);
//    }
    public void setValue(int... months)
    {
        Month[] monthArray = Arrays.stream(months)
                .mapToObj(i -> Month.of(i))
                .toArray(size -> new Month[size]);
        setValue(monthArray);
    }
//    public ByMonth withValue(Month... months)
//    {
//        setValue(months);
//        return this;
//    }
    public ByMonth withValue(int... months)
    {
        setValue(months);
        return this;
    }
//    public ByMonth withValue(String months)
//    {
//        setValue(months);
//        return this;
//    }
    
    /*
     * CONSTRUCTORS
     */
    public ByMonth()
    {
        super();
//        super(ByMonth.class);
    }
    
    public ByMonth(int... months)
    {
        this();
        setValue(months);
    }
    
    public ByMonth(Month... months)
    {
        this();
        setValue(Arrays.asList(months));
    }
    
    public ByMonth(ByMonth source)
    {
        super(source);
    }

    @Override
    public String toString()
    {
        String days = getValue().stream()
                .map(d -> Integer.toString(d.getValue()))
                .collect(Collectors.joining(","));
        return RRuleElementType.BY_MONTH + "=" + days;
    }

    @Override
    public Stream<Temporal> streamRecurrences(Stream<Temporal> inStream, ChronoUnit chronoUnit, Temporal startTemporal)
//    public Stream<Temporal> streamRecurrences(Stream<Temporal> inStream, ObjectProperty<ChronoUnit> chronoUnit, Temporal startDateTime)
    {
//        ChronoUnit originalChronoUnit = chronoUnit; //.get();
//        chronoUnit.set(MONTHS);
        switch (chronoUnit)
        {
        case HOURS:
        case MINUTES:
        case SECONDS:
        case DAYS:
        case WEEKS:
        case MONTHS:
            return inStream.filter(t ->
            { // filter out all but qualifying months
                Month myMonth = Month.from(t);
                for (Month month : getValue())
                {
                    if (month == myMonth) return true;
                }
                return false;
            });
        case YEARS:
            return inStream.flatMap(t -> 
            { // Expand to include matching all matching months
                List<Temporal> dates = new ArrayList<>();
                int monthNum = Month.from(t).getValue();
                for (Month month : getValue())
                {
                    int myMonthNum = month.getValue();
                    int monthShift = myMonthNum - monthNum;
                    Temporal newTemporal = t.plus(monthShift, MONTHS);
//                    if (! DateTimeUtilities.isBefore(newTemporal, startTemporal))
//                    {
                        dates.add(newTemporal);
//                    }
                }
                return dates.stream();
            });
        default:
            throw new RuntimeException("Not implemented ChronoUnit: " + chronoUnit); // probably same as DAILY
        }
    }
    
//    private static ObservableList<Month> makeMonthList(String months)
//    {
//        return FXCollections.observableArrayList(
//            Arrays.asList(months.split(","))
//                .stream()
//                .map(s -> Month.of(Integer.parseInt(s)))
//                .toArray(size -> new Month[size]));
//    }

    @Override
    public Map<VElement, List<String>> parseContent(String content)
    {
        Month[] monthArray = Arrays.asList(content.split(","))
                .stream()
                .map(s -> Month.of(Integer.parseInt(s)))
                .toArray(size -> new Month[size]);
        setValue(monthArray);
        return Collections.EMPTY_MAP;
    }
    
    public static ByMonth parse(String content)
    {
        ByMonth element = new ByMonth();
        element.parseContent(content);
        return element;
    }
}
