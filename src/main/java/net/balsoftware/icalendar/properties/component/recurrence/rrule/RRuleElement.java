package net.balsoftware.icalendar.properties.component.recurrence.rrule;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import net.balsoftware.icalendar.VElement;
import net.balsoftware.icalendar.parameters.VParameterElement;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByDay;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByHour;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByMinute;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByMonth;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByMonthDay;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.BySecond;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.BySetPosition;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByWeekNumber;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByYearDay;

public enum RRuleElement
{
    FREQUENCY ("FREQ", Frequency.class, 0, null),
    INTERVAL ("INTERVAL", Interval.class, 0, null),
    UNTIL ("UNTIL", Until.class, 0, null),
    COUNT ("COUNT", Count.class, 0, null),
    WEEK_START ("WKST", WeekStart.class, 0, null),
    BY_MONTH ("BYMONTH", ByMonth.class, 100, ChronoUnit.MONTHS),
    BY_WEEK_NUMBER ("BYWEEKNO", ByWeekNumber.class, 110, ChronoUnit.DAYS),
    BY_YEAR_DAY ("BYYEARDAY", ByYearDay.class, 120, ChronoUnit.DAYS),
    BY_MONTH_DAY ("BYMONTHDAY", ByMonthDay.class, 130, ChronoUnit.DAYS),
    BY_DAY ("BYDAY", ByDay.class, 140, ChronoUnit.DAYS),
    BY_HOUR ("BYHOUR", ByHour.class, 150, ChronoUnit.HOURS),
    BY_MINUTE ("BYMINUTE", ByMinute.class, 160, ChronoUnit.MINUTES),
    BY_SECOND ("BYSECOND", BySecond.class, 170, ChronoUnit.SECONDS),
    BY_SET_POSITION ("BYSETPOS", BySetPosition.class, 180, null)
    ;
    
    // Map to match up name to enum
    private static final Map<String, RRuleElement> NAME_MAP = Arrays.stream(values())
    		.collect(Collectors.toMap(
    				v -> v.toString(),
    				v -> v));
    public static RRuleElement fromName(String propertyName)
    {
        return NAME_MAP.get(propertyName.toUpperCase());
    }
    
    // Map to match up class to enum
    private static final Map<Class<? extends RRulePart<?>>, RRuleElement> CLASS_MAP = Arrays.stream(values())
    		.collect(Collectors.toMap(
    				v -> v.elementClass(),
    				v -> v));
    /** get enum from map */
    public static RRuleElement fromClass(Class<? extends VElement> myClass)
    {
        RRuleElement p = CLASS_MAP.get(myClass);
        if (p == null)
        {
            throw new IllegalArgumentException(VParameterElement.class.getSimpleName() + " does not contain an enum to match the class:" + myClass.getSimpleName());
        }
        return p;
    }
    
    private String name;
    @Override
    public String toString() { return name; }
    
    private Class<? extends RRulePart<?>> myClass;
	public Class<? extends RRulePart<?>> elementClass() { return myClass; }
    
    private int sortOrder;
    public int sortOrder() { return sortOrder; }
    
    private ChronoUnit chronoUnit;
    public ChronoUnit getChronoUnit() { return chronoUnit; }

    RRuleElement(String name, Class<? extends RRulePart<?>> myClass, int sortOrder, ChronoUnit chronoUnit)
    {
        this.name = name;
        this.myClass = myClass;
        this.sortOrder = sortOrder;
        this.chronoUnit = chronoUnit;
    }
}
