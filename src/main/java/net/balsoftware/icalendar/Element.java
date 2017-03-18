package net.balsoftware.icalendar;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.balsoftware.icalendar.components.DaylightSavingTime;
import net.balsoftware.icalendar.components.StandardTime;
import net.balsoftware.icalendar.components.VAlarm;
import net.balsoftware.icalendar.components.VComponent;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.components.VFreeBusy;
import net.balsoftware.icalendar.components.VJournal;
import net.balsoftware.icalendar.components.VTimeZone;
import net.balsoftware.icalendar.components.VTodo;
import net.balsoftware.icalendar.parameters.AlarmTriggerRelationship;
import net.balsoftware.icalendar.parameters.AlternateText;
import net.balsoftware.icalendar.parameters.CalendarUser;
import net.balsoftware.icalendar.parameters.CommonName;
import net.balsoftware.icalendar.parameters.Delegatees;
import net.balsoftware.icalendar.parameters.Delegators;
import net.balsoftware.icalendar.parameters.DirectoryEntry;
import net.balsoftware.icalendar.parameters.Encoding;
import net.balsoftware.icalendar.parameters.FormatType;
import net.balsoftware.icalendar.parameters.FreeBusyType;
import net.balsoftware.icalendar.parameters.GroupMembership;
import net.balsoftware.icalendar.parameters.Language;
import net.balsoftware.icalendar.parameters.NonStandardParameter;
import net.balsoftware.icalendar.parameters.ParticipationRole;
import net.balsoftware.icalendar.parameters.ParticipationStatus;
import net.balsoftware.icalendar.parameters.RSVP;
import net.balsoftware.icalendar.parameters.Range;
import net.balsoftware.icalendar.parameters.Relationship;
import net.balsoftware.icalendar.parameters.SentBy;
import net.balsoftware.icalendar.parameters.TimeZoneIdentifierParameter;
import net.balsoftware.icalendar.parameters.VParameter;
import net.balsoftware.icalendar.parameters.ValueParameter;
import net.balsoftware.icalendar.properties.VProperty;
import net.balsoftware.icalendar.properties.ValueType;
import net.balsoftware.icalendar.properties.calendar.CalendarScale;
import net.balsoftware.icalendar.properties.calendar.ProductIdentifier;
import net.balsoftware.icalendar.properties.calendar.Version;
import net.balsoftware.icalendar.properties.component.alarm.Action;
import net.balsoftware.icalendar.properties.component.alarm.RepeatCount;
import net.balsoftware.icalendar.properties.component.alarm.Trigger;
import net.balsoftware.icalendar.properties.component.change.DateTimeCreated;
import net.balsoftware.icalendar.properties.component.change.DateTimeStamp;
import net.balsoftware.icalendar.properties.component.change.LastModified;
import net.balsoftware.icalendar.properties.component.change.Sequence;
import net.balsoftware.icalendar.properties.component.descriptive.Attachment;
import net.balsoftware.icalendar.properties.component.descriptive.Categories;
import net.balsoftware.icalendar.properties.component.descriptive.Classification;
import net.balsoftware.icalendar.properties.component.descriptive.Comment;
import net.balsoftware.icalendar.properties.component.descriptive.Description;
import net.balsoftware.icalendar.properties.component.descriptive.GeographicPosition;
import net.balsoftware.icalendar.properties.component.descriptive.Location;
import net.balsoftware.icalendar.properties.component.descriptive.PercentComplete;
import net.balsoftware.icalendar.properties.component.descriptive.Priority;
import net.balsoftware.icalendar.properties.component.descriptive.Resources;
import net.balsoftware.icalendar.properties.component.descriptive.Status;
import net.balsoftware.icalendar.properties.component.descriptive.Summary;
import net.balsoftware.icalendar.properties.component.misc.NonStandardProperty;
import net.balsoftware.icalendar.properties.component.misc.RequestStatus;
import net.balsoftware.icalendar.properties.component.recurrence.ExceptionDates;
import net.balsoftware.icalendar.properties.component.recurrence.RecurrenceDates;
import net.balsoftware.icalendar.properties.component.recurrence.RecurrenceRule;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.Count;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.Frequency;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.Interval;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.RRuleElement;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.Until;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.WeekStart;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByDay;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByHour;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByMinute;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByMonth;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByMonthDay;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.BySecond;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.BySetPosition;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByWeekNumber;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx.ByYearDay;
import net.balsoftware.icalendar.properties.component.relationship.Attendee;
import net.balsoftware.icalendar.properties.component.relationship.Contact;
import net.balsoftware.icalendar.properties.component.relationship.Organizer;
import net.balsoftware.icalendar.properties.component.relationship.RecurrenceId;
import net.balsoftware.icalendar.properties.component.relationship.RelatedTo;
import net.balsoftware.icalendar.properties.component.relationship.UniformResourceLocator;
import net.balsoftware.icalendar.properties.component.relationship.UniqueIdentifier;
import net.balsoftware.icalendar.properties.component.time.DateTimeCompleted;
import net.balsoftware.icalendar.properties.component.time.DateTimeDue;
import net.balsoftware.icalendar.properties.component.time.DateTimeEnd;
import net.balsoftware.icalendar.properties.component.time.DateTimeStart;
import net.balsoftware.icalendar.properties.component.time.DurationProp;
import net.balsoftware.icalendar.properties.component.time.FreeBusyTime;
import net.balsoftware.icalendar.properties.component.time.TimeTransparency;
import net.balsoftware.icalendar.properties.component.timezone.TimeZoneIdentifier;
import net.balsoftware.icalendar.properties.component.timezone.TimeZoneName;
import net.balsoftware.icalendar.properties.component.timezone.TimeZoneOffsetFrom;
import net.balsoftware.icalendar.properties.component.timezone.TimeZoneOffsetTo;
import net.balsoftware.icalendar.properties.component.timezone.TimeZoneURL;
import net.balsoftware.icalendar.utilities.Pair;

@Deprecated // go back to separate enums
public enum Element
{
	VCALENDAR ("VCALENDAR", VCalendar.class, VCalendar.class),

	// NOTE: Other calendar element types are represented in other enums: PropertyElement
	
	VEVENT ("VEVENT", VComponent.class, VEvent.class),
    VTODO ("VTODO", VComponent.class, VTodo.class),
    VJOURNAL ("VJOURNAL", VComponent.class, VJournal.class),
    VTIMEZONE ("VTIMEZONE", VComponent.class, VTimeZone.class),
    VFREEBUSY ("VFREEBUSY", VComponent.class, VFreeBusy.class),
    DAYLIGHT_SAVING_TIME (DaylightSavingTime.NAME, VComponent.class, DaylightSavingTime.class),
    STANDARD_TIME ("STANDARD", VComponent.class, StandardTime.class),
    VALARM ("VALARM", VComponent.class, VAlarm.class),
        
	ACTION ("ACTION", VProperty.class, Action.class),
    ATTACHMENT ("ATTACH", VProperty.class, Attachment.class),
    ATTENDEE ("ATTENDEE", VProperty.class, Attendee.class),
    CALENDAR_SCALE ("CALSCALE", VProperty.class, CalendarScale.class),
    CATEGORIES ("CATEGORIES", VProperty.class, Categories.class),
    CLASSIFICATION ("CLASS", VProperty.class, Classification.class),
    COMMENT ("COMMENT", VProperty.class, Comment.class),
    CONTACT ("CONTACT", VProperty.class, Contact.class),
    DATE_TIME_COMPLETED ("COMPLETED", VProperty.class, DateTimeCompleted.class),
    DATE_TIME_CREATED ("CREATED", VProperty.class,  DateTimeCreated.class),
    DATE_TIME_DUE ("DUE", VProperty.class, DateTimeDue.class),
    DATE_TIME_END ("DTEND", VProperty.class, DateTimeEnd.class),
    DATE_TIME_STAMP ("DTSTAMP", VProperty.class, DateTimeStamp.class),
    DATE_TIME_START ("DTSTART", VProperty.class, DateTimeStart.class),
    DESCRIPTION ("DESCRIPTION", VProperty.class, Description.class),
    DURATION ("DURATION", VProperty.class, DurationProp.class),
    EXCEPTION_DATE_TIMES ("EXDATE", VProperty.class, ExceptionDates.class),
    FREE_BUSY_TIME ("FREEBUSY", VProperty.class, FreeBusyTime.class),
    GEOGRAPHIC_POSITION ("GEO", VProperty.class, GeographicPosition.class),
    LAST_MODIFIED ("LAST-MODIFIED", VProperty.class, LastModified.class),
    LOCATION ("LOCATION", VProperty.class, Location.class),
    METHOD ("METHOD", VProperty.class, net.balsoftware.icalendar.properties.calendar.Method.class),
    NON_STANDARD_PROPERTY ("X-", VProperty.class, NonStandardProperty.class),
    ORGANIZER ("ORGANIZER", VProperty.class, Organizer.class),
    PERCENT_COMPLETE ("PERCENT-COMPLETE", VProperty.class, PercentComplete.class),
    PRIORITY ("PRIORITY", VProperty.class, Priority.class),
    PRODUCT_IDENTIFIER ("PRODID", VProperty.class, ProductIdentifier.class),
    RECURRENCE_DATE_TIMES ("RDATE", VProperty.class, RecurrenceDates.class),
    RECURRENCE_IDENTIFIER ("RECURRENCE-ID", VProperty.class, RecurrenceId.class),
    RECURRENCE_RULE ("RRULE", VProperty.class, RecurrenceRule.class),
    RELATED_TO ("RELATED-TO", VProperty.class, RelatedTo.class),
    REPEAT_COUNT ("REPEAT", VProperty.class, RepeatCount.class),
    REQUEST_STATUS ("REQUEST-STATUS", VProperty.class, RequestStatus.class),
    RESOURCES ("RESOURCES", VProperty.class, Resources.class),
    SEQUENCE ("SEQUENCE", VProperty.class, Sequence.class),
    STATUS ("STATUS", VProperty.class, Status.class),
    SUMMARY ("SUMMARY", VProperty.class, Summary.class),
    TIME_TRANSPARENCY ("TRANSP", VProperty.class, TimeTransparency.class),
    TIME_ZONE_IDENTIFIER_PROPERTY ("TZID", VProperty.class, TimeZoneIdentifier.class),
    TIME_ZONE_NAME ("TZNAME", VProperty.class, TimeZoneName.class),
    TIME_ZONE_OFFSET_FROM ("TZOFFSETFROM", VProperty.class, TimeZoneOffsetFrom.class),
    TIME_ZONE_OFFSET_TO ("TZOFFSETTO", VProperty.class, TimeZoneOffsetTo.class),
    TIME_ZONE_URL ("TZURL", VProperty.class, TimeZoneURL.class),
    TRIGGER ("TRIGGER", VProperty.class, Trigger.class),
    UNIQUE_IDENTIFIER ("UID", VProperty.class, UniqueIdentifier.class),
    UNIFORM_RESOURCE_LOCATOR ("URL", VProperty.class, UniformResourceLocator.class),
    VERSION ("VERSION", VProperty.class, Version.class),
	
    ALTERNATE_TEXT_REPRESENTATION ("ALTREP", VParameter.class, AlternateText.class),
    COMMON_NAME ("CN", VParameter.class, CommonName.class),
    CALENDAR_USER_TYPE ("CUTYPE", VParameter.class, CalendarUser.class),
    DELEGATORS ("DELEGATED-FROM", VParameter.class, Delegators.class),
    DELEGATEES ("DELEGATED-TO", VParameter.class, Delegatees.class),
    DIRECTORY_ENTRY_REFERENCE ("DIR", VParameter.class, DirectoryEntry.class),
    INLINE_ENCODING ("ENCODING", VParameter.class, Encoding.class),
    FORMAT_TYPE ("FMTTYPE", VParameter.class, FormatType.class),
    FREE_BUSY_TIME_TYPE ("FBTYPE", VParameter.class, FreeBusyType.class),
    LANGUAGE ("LANGUAGE", VParameter.class, Language.class),
    GROUP_OR_LIST_MEMBERSHIP ("MEMBER", VParameter.class, GroupMembership.class),
    NON_STANDARD_PARAMETER ("X-", VParameter.class, NonStandardParameter.class), // NOTE: Has no no-arg constructor
    PARTICIPATION_STATUS ("PARTSTAT", VParameter.class, ParticipationStatus.class),
    RECURRENCE_IDENTIFIER_RANGE ("RANGE", VParameter.class, Range.class),
    ALARM_TRIGGER_RELATIONSHIP ("RELATED", VParameter.class, AlarmTriggerRelationship.class),
    RELATIONSHIP_TYPE ("RELTYPE", VParameter.class, Relationship.class),
    PARTICIPATION_ROLE ("ROLE", VParameter.class, ParticipationRole.class),
    RSVP_EXPECTATION ("RSVP", VParameter.class, RSVP.class),
    SENT_BY ("SENT-BY", VParameter.class, SentBy.class),
    TIME_ZONE_IDENTIFIER_PARAMETER ("TZID", VParameter.class, TimeZoneIdentifierParameter.class),
	VALUE_DATA_TYPES ("VALUE", VParameter.class, ValueParameter.class),
	
	// TODO - NEED TO HANDLE SORT ORDER AND DEFAULT CHRONO TYPE
	FREQUENCY ("FREQ", RRuleElement.class, Frequency.class),
    INTERVAL ("INTERVAL", RRuleElement.class, Interval.class),
    UNTIL ("UNTIL", RRuleElement.class, Until.class),
    COUNT ("COUNT", RRuleElement.class, Count.class),
    WEEK_START ("WKST", RRuleElement.class, WeekStart.class),
    BY_MONTH ("BYMONTH", RRuleElement.class, ByMonth.class),
    BY_WEEK_NUMBER ("BYWEEKNO", RRuleElement.class, ByWeekNumber.class),
    BY_YEAR_DAY ("BYYEARDAY", RRuleElement.class, ByYearDay.class),
    BY_MONTH_DAY ("BYMONTHDAY", RRuleElement.class, ByMonthDay.class),
    BY_DAY ("BYDAY", RRuleElement.class, ByDay.class),
    BY_HOUR ("BYHOUR", RRuleElement.class, ByHour.class),
    BY_MINUTE ("BYMINUTE", RRuleElement.class, ByMinute.class),
    BY_SECOND ("BYSECOND", RRuleElement.class, BySecond.class),
    BY_SET_POSITION ("BYSETPOS", RRuleElement.class, BySetPosition.class)
	;

	private static final  Map<Pair<Class<? extends VElement>, String>, Constructor<? extends VElement>> NO_ARG_CONSTRUCTORS = makeNoArgConstructorMap();
    private static Map<Pair<Class<? extends VElement>, String>, Constructor<? extends VElement>> makeNoArgConstructorMap()
    {
    	Map<Pair<Class<? extends VElement>, String>, Constructor<? extends VElement>> map = new HashMap<>();
    	Element[] values1 = Element.values();
    	Arrays.stream(values1)
    		.forEach(v ->
	    	{
	    		Pair<Class<? extends VElement>, String> key = new Pair<>(v.superClass, v.name);
				try {
//					System.out.println(v.name);
					Constructor<? extends VElement> constructor = v.myClass.getConstructor();
					map.put(key, constructor);
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
	    	});
//    	map.putAll(PropertyElements.NO_ARG_CONSTRUCTORS);
        return map;
    }
	public static VChild newEmptyVElement(Class<? extends VElement> superclass, String name)
	{
		try {
//			NO_ARG_CONSTRUCTORS.entrySet().forEach(System.out::println);
//			System.out.println("get constructor:" + superclass.getSimpleName() + " " + name);
			String name2 = (name.startsWith("X-")) ? "X-" : name;
			Constructor<? extends VElement> constructor = NO_ARG_CONSTRUCTORS.get(new Pair<>(superclass, name2));
			if (constructor == null) return null;
			return (VChild) constructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    // Map to match up class to enum
    private final static Map<Class<? extends VElement>, Element> CLASS_MAP = makeEnumFromClassMap();
    private static Map<Class<? extends VElement>, Element> makeEnumFromClassMap()
    {
    	Map<Class<? extends VElement>, Element> map = new HashMap<>();
    	Element[] values = Element.values();
        for (int i=0; i<values.length; i++)
        {
            map.put(values[i].myClass, values[i]);
        }
        return map;
    }
	public static Element fromClass(Class<? extends VElement> vElementClass)
	{
		return CLASS_MAP.get(vElementClass);
	}
    	
	/*
	 * CONSTRUCTOR
	 */
    private String name;
    private Class<? extends VElement> superClass;
    private Class<? extends VElement> myClass;
    @Override  public String toString() { return name; }
    Element(String name, Class<? extends VElement> superClass, Class<? extends VElement> myClass)
    {
        this.name = name;
        this.superClass = superClass;
        this.myClass = myClass;
    }
	
	public static List<String> names = Arrays
			.stream(values())
			.map(v -> v.name)
			.collect(Collectors.toList());
	
//    public static String extractValue(String content)
//    {
//        int equalsIndex = content.indexOf('=');
//        final String valueString;
//        if (equalsIndex > 0)
//        {
//            String name = content.substring(0, equalsIndex);
//            boolean hasName1 = names.contains(name.toUpperCase());
////            boolean hasName2 = (IANAParameter.getRegisteredIANAParameters() != null) ? IANAParameter.getRegisteredIANAParameters().contains(name.toUpperCase()) : false;
//            valueString = (hasName1) ? content.substring(equalsIndex+1) : content;    
//        } else
//        {
//            valueString = content;
//        }
//        return valueString;
//    }
    private static final Map<Class<? extends VProperty<?>>, Collection<ValueType>> ALLOWED_VALUE_TYPES_MAP = makeAllowedTypesMap();
	private static Map<Class<? extends VProperty<?>>, Collection<ValueType>> makeAllowedTypesMap()
	{
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented");
	}
}
