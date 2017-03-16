package net.balsoftware.icalendar.parameters;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.balsoftware.icalendar.VElement;
import net.balsoftware.icalendar.parameters.AlarmTriggerRelationship.AlarmTriggerRelationshipType;
import net.balsoftware.icalendar.parameters.CalendarUser.CalendarUserType;
import net.balsoftware.icalendar.parameters.Encoding.EncodingType;
import net.balsoftware.icalendar.parameters.FreeBusyType.FreeBusyTypeEnum;
import net.balsoftware.icalendar.parameters.ParticipationRole.ParticipationRoleType;
import net.balsoftware.icalendar.parameters.ParticipationStatus.ParticipationStatusType;
import net.balsoftware.icalendar.parameters.Range.RangeType;
import net.balsoftware.icalendar.parameters.Relationship.RelationshipType;
import net.balsoftware.icalendar.properties.PropAlarmTrigger;
import net.balsoftware.icalendar.properties.PropAltText;
import net.balsoftware.icalendar.properties.PropAttachment;
import net.balsoftware.icalendar.properties.PropAttendee;
import net.balsoftware.icalendar.properties.PropBaseAltText;
import net.balsoftware.icalendar.properties.PropBaseLanguage;
import net.balsoftware.icalendar.properties.PropDateTime;
import net.balsoftware.icalendar.properties.PropFreeBusy;
import net.balsoftware.icalendar.properties.PropRecurrenceID;
import net.balsoftware.icalendar.properties.PropRelationship;
import net.balsoftware.icalendar.properties.VProperty;
import net.balsoftware.icalendar.properties.VPropertyBase;
import net.balsoftware.icalendar.properties.ValueType;
import net.balsoftware.icalendar.properties.component.relationship.PropertyBaseCalendarUser;
import net.balsoftware.icalendar.utilities.StringConverter;
import net.balsoftware.icalendar.utilities.StringConverters;

/**
 * For each VComponent property parameter (RFC 5545, 3.2, page 13) contains the following: <br>
 * <br>
 * Parameter name {@link #toString()} <br>
 * Parameter class {@link #getPropertyClass()}<br>
 * Method to parse parameter string into parent component {@link #parse(Property<?>, String)}<br>
 * Method to get parameter from property {@link #getParameter(Property<?>)}<br>
 * Method to copy parameter into new parent property {@link #copyParameter(VParameter, VProperty)}<br>
 * 
 * @author David Bal
 *
 */
//@Deprecated // Replace with Elements
public enum ParameterType
{
    // in properties COMMENT, CONTACT, DESCRIPTION, LOCATION, RESOURCES
    ALTERNATE_TEXT_REPRESENTATION ("ALTREP", AlternateText.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropAltText<?> castProperty = (PropAltText<?>) parent;
            return castProperty.getAlternateText();
        }
        
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.uriConverterWithQuotes();
        }

        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropBaseAltText<?,?> castDestination = (PropBaseAltText<?,?>) destination;
            AlternateText parameterCopy = new AlternateText((AlternateText) child);
            castDestination.setAlternateText(parameterCopy);
        }
    },
    // in properties ATTENDEE, ORGANIZER
    COMMON_NAME ("CN", CommonName.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropertyBaseCalendarUser<?,?> castProperty = (PropertyBaseCalendarUser<?,?>) parent;
            return castProperty.getCommonName();
        }

        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropertyBaseCalendarUser<?,?> castDestination = (PropertyBaseCalendarUser<?,?>) destination;
            CommonName parameterCopy = new CommonName((CommonName) child);
            castDestination.setCommonName(parameterCopy);
        }

        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.defaultStringConverterWithQuotes();
        }
    },
    // in property ATTENDEE
    CALENDAR_USER_TYPE ("CUTYPE", CalendarUser.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropAttendee<?> castProperty = (PropAttendee<?>) parent;
            return castProperty.getCalendarUser();
        }

        @Override
        public <T> StringConverter<T> getConverter()
        {
            return new StringConverter<T>()
            {
                @Override
                public String toString(T object)
                {
                    return ((CalendarUserType) object).toString();
                }

                @Override
                public T fromString(String string)
                {
                    return (T) CalendarUserType.valueOfWithUnknown(string.toUpperCase());
                }
            };
        }
        
        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropAttendee<?> castDestination = (PropAttendee<?>) destination;
            CalendarUser parameterCopy = new CalendarUser((CalendarUser) child);
            castDestination.setCalendarUser(parameterCopy);
        }
    },
    // in property ATTENDEE
    DELEGATORS ("DELEGATED-FROM", Delegators.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropAttendee<?> castProperty = (PropAttendee<?>) parent;
            return castProperty.getDelegators();
        }
        
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.uriListConverter();
        }

        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropAttendee<?> castDestination = (PropAttendee<?>) destination;
            Delegators parameterCopy = new Delegators((Delegators) child);
            castDestination.setDelegators(parameterCopy);
        }
    },
    // in property ATTENDEE
    DELEGATEES ("DELEGATED-TO", Delegatees.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropAttendee<?> castProperty = (PropAttendee<?>) parent;
            return castProperty.getDelegatees();
        }

        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.uriListConverter();
        }

        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropAttendee<?> castDestination = (PropAttendee<?>) destination;
            Delegatees parameterCopy = new Delegatees((Delegatees) child);
            castDestination.setDelegatees(parameterCopy);
        }
    },
    // in properties ATTENDEE, ORGANIZER
    DIRECTORY_ENTRY_REFERENCE ("DIR", DirectoryEntry.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropertyBaseCalendarUser<?,?> castProperty = (PropertyBaseCalendarUser<?,?>) parent;
            return castProperty.getDirectoryEntryReference();
        }
        
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.uriConverterWithQuotes();
        }

        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropAttendee<?> castDestination = (PropAttendee<?>) destination;
            DirectoryEntry parameterCopy = new DirectoryEntry((DirectoryEntry) child);
            castDestination.setDirectoryEntryReference(parameterCopy);
        }
    },
    // in property ATTACHMENT
    INLINE_ENCODING ("ENCODING", Encoding.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropAttachment<?> castProperty = (PropAttachment<?>) parent;
            return castProperty.getEncoding();
        }
        
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return new StringConverter<T>()
            {
                @Override
                public String toString(T object)
                {
                    return ((EncodingType) object).toString();
                }

                @Override
                public T fromString(String string)
                {
                    return (T) EncodingType.enumFromName(string.toUpperCase());
                }
            };
        }

        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropAttachment<?> castDestination = (PropAttachment<?>) destination;
            Encoding parameterCopy = new Encoding((Encoding) child);
            castDestination.setEncoding(parameterCopy);
        }
    },
    // in property ATTACHMENT
    FORMAT_TYPE ("FMTTYPE", FormatType.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropAttachment<?> castProperty = (PropAttachment<?>) parent;
            return castProperty.getFormatType();
        }

        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropAttachment<?> castDestination = (PropAttachment<?>) destination;
            FormatType parameterCopy = new FormatType((FormatType) child);
            castDestination.setFormatType(parameterCopy);
        }

        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.defaultStringConverterWithQuotes();
        }
    },
    // in property FREEBUSY
    FREE_BUSY_TIME_TYPE ("FBTYPE", FreeBusyType.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropFreeBusy<?> castProperty = (PropFreeBusy<?>) parent;
            return castProperty.getFreeBusyType();
        }

        @Override
        public <T> StringConverter<T> getConverter()
        {
            return new StringConverter<T>()
            {
                @Override
                public String toString(T object)
                {
                    return ((FreeBusyTypeEnum) object).toString();
                }

                @Override
                public T fromString(String string)
                {
                    return (T) FreeBusyTypeEnum.enumFromName(string.toUpperCase());
                }
            };
        }
        
        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropFreeBusy<?> castDestination = (PropFreeBusy<?>) destination;
            FreeBusyType parameterCopy = new FreeBusyType((FreeBusyType) child);
            castDestination.setFreeBusyType(parameterCopy);
        }
    },
    // in properties CATEGORIES, COMMENT, CONTACT, DESCRIPTION, LOCATION, RESOURCES, TZNAME
    LANGUAGE ("LANGUAGE", Language.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropBaseLanguage<?,?> castProperty = (PropBaseLanguage<?,?>) parent;
            return castProperty.getLanguage();
        }

        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropBaseLanguage<?,?> castDestination = (PropBaseLanguage<?,?>) destination;
            Language parameterCopy = new Language((Language) child);
            castDestination.setLanguage(parameterCopy);
        }

        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.defaultStringConverterWithQuotes();
        }
    },
    GROUP_OR_LIST_MEMBERSHIP ("MEMBER", GroupMembership.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropAttendee<?> castProperty = (PropAttendee<?>) parent;
            return castProperty.getGroupMembership();
        }
        
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.uriListConverter();
        }

        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropFreeBusy<?> castDestination = (PropFreeBusy<?>) destination;
            FreeBusyType parameterCopy = new FreeBusyType((FreeBusyType) child);
            castDestination.setFreeBusyType(parameterCopy);
        }
    },
    NON_STANDARD ("X-", // parameter name begins with X- prefix
            NonStandardParameter.class) {
        @Override
        public Object getParameter(VProperty<?> parent)
        {
            return parent.getNonStandard();
        }

        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            final List<NonStandardParameter> list;
            if (destination.getNonStandard() == null)
            {
                list = new ArrayList<>();
                destination.setNonStandard(list);
            } else
            {
                list = destination.getNonStandard();
            }
            list.add(new NonStandardParameter((NonStandardParameter) child));
        }

        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.defaultStringConverterWithQuotes();
        }
    },
    PARTICIPATION_STATUS ("PARTSTAT", ParticipationStatus.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropAttendee<?> castProperty = (PropAttendee<?>) parent;
            return castProperty.getParticipationStatus();
        }

        @Override
        public <T> StringConverter<T> getConverter()
        {
            return new StringConverter<T>()
            {
                @Override
                public String toString(T object)
                {
                    return ((ParticipationStatusType) object).toString();
                }

                @Override
                public T fromString(String string)
                {
                    return (T) ParticipationStatusType.enumFromName(string.toUpperCase());
                }
            };
        }
        
        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropAttendee<?> castDestination = (PropAttendee<?>) destination;
            ParticipationStatus parameterCopy = new ParticipationStatus((ParticipationStatus) child);
            castDestination.setParticipationStatus(parameterCopy);
        }
    },
    RECURRENCE_IDENTIFIER_RANGE ("RANGE", Range.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropRecurrenceID<?> castProperty = (PropRecurrenceID<?>) parent;
            return castProperty.getRange();
        }

        @Override
        public <T> StringConverter<T> getConverter()
        {
            return new StringConverter<T>()
            {
                @Override
                public String toString(T object)
                {
                    return ((RangeType) object).toString();
                }

                @Override
                public T fromString(String string)
                {
                    return (T) RangeType.enumFromName(string.toUpperCase());
                }
            };
        }
        
        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropRecurrenceID<?> castDestination = (PropRecurrenceID<?>) destination;
            Range parameterCopy = new Range((Range) child);
            castDestination.setRange(parameterCopy);
        }
    },
    ALARM_TRIGGER_RELATIONSHIP ("RELATED", AlarmTriggerRelationship.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropAlarmTrigger<?> castProperty = (PropAlarmTrigger<?>) parent;
            return castProperty.getAlarmTrigger();
        }

        @Override
        public <T> StringConverter<T> getConverter()
        {
            return new StringConverter<T>()
            {
                @Override
                public String toString(T object)
                {
                    return ((AlarmTriggerRelationshipType) object).toString();
                }

                @Override
                public T fromString(String string)
                {
                    return (T) AlarmTriggerRelationshipType.valueOf(string.toUpperCase());
                }
            };
        }
        
        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropAlarmTrigger<?> castDestination = (PropAlarmTrigger<?>) destination;
            AlarmTriggerRelationship parameterCopy = new AlarmTriggerRelationship((AlarmTriggerRelationship) child);
            castDestination.setAlarmTrigger(parameterCopy);
        }
    },
    RELATIONSHIP_TYPE ("RELTYPE", Relationship.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropRelationship<?> castProperty = (PropRelationship<?>) parent;
            return castProperty.getRelationship();
        }

        
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return new StringConverter<T>()
            {
                @Override
                public String toString(T object)
                {
                    return ((RelationshipType) object).toString();
                }

                @Override
                public T fromString(String string)
                {
                    return (T) RelationshipType.valueOfWithUnknown(string.toUpperCase());
                }
            };
        }
        
        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropRelationship<?> castDestination = (PropRelationship<?>) destination;
            Relationship parameterCopy = new Relationship((Relationship) child);
            castDestination.setRelationship(parameterCopy);
        }
    },
    PARTICIPATION_ROLE ("ROLE", ParticipationRole.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropAttendee<?> castProperty = (PropAttendee<?>) parent;
            return castProperty.getParticipationRole();
        }
        
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return new StringConverter<T>()
            {
                @Override
                public String toString(T object)
                {
                    return ((ParticipationRoleType) object).toString();
                }

                @Override
                public T fromString(String string)
                {
                    return (T) ParticipationRoleType.enumFromName(string.toUpperCase());
                }
            };
        }

        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropAttendee<?> castDestination = (PropAttendee<?>) destination;
            ParticipationRole parameterCopy = new ParticipationRole((ParticipationRole) child);
            castDestination.setParticipationRole(parameterCopy);
        }
    },
    RSVP_EXPECTATION ("RSVP", RSVP.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropAttendee<?> castProperty = (PropAttendee<?>) parent;
            return castProperty.getRSVP();
        }
        
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.booleanConverter();
        }
        
        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropAttendee<?> castDestination = (PropAttendee<?>) destination;
            RSVP parameterCopy = new RSVP((RSVP) child);
            castDestination.setRSVP(parameterCopy);
        }
    },
    SENT_BY ("SENT-BY", SentBy.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropertyBaseCalendarUser<?,?> castProperty = (PropertyBaseCalendarUser<?,?>) parent;
            return castProperty.getSentBy();
        }
        
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.uriConverterWithQuotes();
        }

        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropertyBaseCalendarUser<?,?> castDestination = (PropertyBaseCalendarUser<?,?>) destination;
            SentBy parameterCopy = new SentBy((SentBy) child);
            castDestination.setSentBy(parameterCopy);
        }
    },
    TIME_ZONE_IDENTIFIER ("TZID", TimeZoneIdentifierParameter.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            PropDateTime<?> castProperty = (PropDateTime<?>) parent;
            TimeZoneIdentifierParameter parameter = castProperty.getTimeZoneIdentifier();
            return ((parameter == null) || (parameter.getValue().equals(ZoneId.of("Z")))) ? null : parameter;
        }

        @Override
        public <T> StringConverter<T> getConverter()
        {
            return new StringConverter<T>()
            {
                @Override
                public String toString(T object)
                {
                    return ((ZoneId) object).toString();
                }

                @Override
                public T fromString(String string)
                {
                    return (T) ZoneId.of(string);
                }
            };
        }
        
        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            PropDateTime<?> castDestination = (PropDateTime<?>) destination;
            TimeZoneIdentifierParameter parameterCopy = new TimeZoneIdentifierParameter((TimeZoneIdentifierParameter) child);
            castDestination.setTimeZoneIdentifier(parameterCopy);
        }
    },
    VALUE_DATA_TYPES ("VALUE", ValueParameter.class) {
        @Override
        public VParameter<?> getParameter(VProperty<?> parent)
        {
            VPropertyBase<?,?> castProperty = (VPropertyBase<?,?>) parent;
            return castProperty.getValueType();
        }
        
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return new StringConverter<T>()
            {
                @Override
                public String toString(T object)
                {
                    return ((ValueType) object).toString();
                }

                @Override
                public T fromString(String string)
                {
                    return (T) ValueType.enumFromName(string.toUpperCase());
                }
            };
        }
        
        @Override
        public void copyParameter(VParameter<?> child, VProperty<?> destination)
        {
            VPropertyBase<?,?> castDestination = (VPropertyBase<?,?>) destination;
            ValueParameter parameterCopy = new ValueParameter((ValueParameter) child);
            castDestination.setValueType(parameterCopy);
        }
    };
    
    // Map to match up name to enum
    private static Map<String, ParameterType> enumFromNameMap = makeEnumFromNameMap();
    private static Map<String, ParameterType> makeEnumFromNameMap()
    {
        Map<String, ParameterType> map = new HashMap<>();
        ParameterType[] values = ParameterType.values();
        for (int i=0; i<values.length; i++)
        {
            map.put(values[i].toString(), values[i]);
        }
        return map;
    }
    public static ParameterType enumFromName(String parameterName)
    {
        final ParameterType prop;
        // minimum property name is 2 characters
        boolean isLongEnough = parameterName.length() > 2;
        boolean isNonStanderd = (isLongEnough) ? parameterName.substring(0, ParameterType.NON_STANDARD.toString().length()).equals(ParameterType.NON_STANDARD.toString()) : false;
        if (isNonStanderd)
        {
            prop = ParameterType.NON_STANDARD;
        } else
        {
            prop = enumFromNameMap.get(parameterName);   
        }
        return prop;
    }
    
    // Map to match up class to enum
    private static Map<Class<? extends VParameter<?>>, ParameterType> enumFromClassMap = makeEnumFromClassMap();
    private static Map<Class<? extends VParameter<?>>, ParameterType> makeEnumFromClassMap()
    {
        Map<Class<? extends VParameter<?>>, ParameterType> map = new HashMap<>();
        ParameterType[] values = ParameterType.values();
        for (int i=0; i<values.length; i++)
        {
            map.put(values[i].myClass, values[i]);
        }
        return map;
    }
    /** get enum from map */
    public static ParameterType enumFromClass(Class<? extends VElement> myClass)
    {
        ParameterType p = enumFromClassMap.get(myClass);
        if (p == null)
        {
            throw new IllegalArgumentException(ParameterType.class.getSimpleName() + " does not contain an enum to match the class:" + myClass.getSimpleName());
        }
        return p;
    }
    
    private String name;
    private Class<? extends VParameter<?>> myClass;
    @Override  public String toString() { return name; }
    ParameterType(String name, Class<? extends VParameter<?>> myClass)
    {
        this.name = name;
        this.myClass = myClass;
    }

    /*
     * STATIC METHODS
     */

    /**
     * Remove parameter name and equals sign, if present, otherwise returns input string
     * 
     * @param input - parameter content with or without name and equals sign
     * @param name - name of parameter
     * @return - nameless string
     * 
     * example input:
     * ALTREP="CID:part3.msg.970415T083000@example.com"
     * output:
     * "CID:part3.msg.970415T083000@example.com"
     */
    static String extractValue(String content)
    {
        int equalsIndex = content.indexOf('=');
        return (equalsIndex > 0) ? content.substring(equalsIndex+1) : content;
    }

    /*
     * ABSTRACT METHODS
     */
//    /** Parses string and sets parameter.  Called by {@link VPropertyBase#parseContent()} */
//    abstract public VChild parse(VProperty<?> property, String content);
    
    /** Returns associated Property<?> or List<Property<?>> */
    // TODO - MAY BE OBSOLETE WITH USE OF ORDERER - ONLY BEING USED TO DOUBLE-CHECK EXISTANCE OF ALL PARAMETERS WHEN COPYING
    abstract public Object getParameter(VProperty<?> parent);
    
    /** return default String converter associated with property value type */
    abstract public <T> StringConverter<T> getConverter();
    
    @Deprecated
    abstract public void copyParameter(VParameter<?> child, VProperty<?> destination);
}
