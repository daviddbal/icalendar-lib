package net.balsoftware.icalendar.parameters;

import java.time.ZoneId;
import java.util.HashMap;
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
import net.balsoftware.icalendar.properties.VProperty;
import net.balsoftware.icalendar.properties.ValueType;
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
public enum VParameterElement
{
    // in properties COMMENT, CONTACT, DESCRIPTION, LOCATION, RESOURCES
    ALTERNATE_TEXT_REPRESENTATION ("ALTREP", AlternateText.class) {
        
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.uriConverterWithQuotes();
        }
    },
    // in properties ATTENDEE, ORGANIZER
    COMMON_NAME ("CN", CommonName.class) {
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.defaultStringConverterWithQuotes();
        }
    },
    // in property ATTENDEE
    CALENDAR_USER_TYPE ("CUTYPE", CalendarUser.class) {
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
    },
    // in property ATTENDEE
    DELEGATORS ("DELEGATED-FROM", Delegators.class) {
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.uriListConverter();
        }
    },
    // in property ATTENDEE
    DELEGATEES ("DELEGATED-TO", Delegatees.class) {
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.uriListConverter();
        }
    },
    // in properties ATTENDEE, ORGANIZER
    DIRECTORY_ENTRY_REFERENCE ("DIR", DirectoryEntry.class) {
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.uriConverterWithQuotes();
        }
    },
    // in property ATTACHMENT
    INLINE_ENCODING ("ENCODING", Encoding.class) {
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
    },
    // in property ATTACHMENT
    FORMAT_TYPE ("FMTTYPE", FormatType.class) {
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.defaultStringConverterWithQuotes();
        }
    },
    // in property FREEBUSY
    FREE_BUSY_TIME_TYPE ("FBTYPE", FreeBusyType.class) {
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
    },
    // in properties CATEGORIES, COMMENT, CONTACT, DESCRIPTION, LOCATION, RESOURCES, TZNAME
    LANGUAGE ("LANGUAGE", Language.class) {
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.defaultStringConverterWithQuotes();
        }
    },
    GROUP_OR_LIST_MEMBERSHIP ("MEMBER", GroupMembership.class) {
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.uriListConverter();
        }
    },
    NON_STANDARD ("X-", // parameter name begins with X- prefix
            NonStandardParameter.class) {
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.defaultStringConverterWithQuotes();
        }
    },
    PARTICIPATION_STATUS ("PARTSTAT", ParticipationStatus.class) {
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
    },
    RECURRENCE_IDENTIFIER_RANGE ("RANGE", Range.class) {
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
    },
    ALARM_TRIGGER_RELATIONSHIP ("RELATED", AlarmTriggerRelationship.class) {
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
    },
    RELATIONSHIP_TYPE ("RELTYPE", Relationship.class) {
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
    },
    PARTICIPATION_ROLE ("ROLE", ParticipationRole.class) {
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
    },
    RSVP_EXPECTATION ("RSVP", RSVP.class) {
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.booleanConverter();
        }
    },
    SENT_BY ("SENT-BY", SentBy.class) {
        @Override
        public <T> StringConverter<T> getConverter()
        {
            return (StringConverter<T>) StringConverters.uriConverterWithQuotes();
        }
    },
    TIME_ZONE_IDENTIFIER ("TZID", TimeZoneIdentifierParameter.class) {
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
    },
    VALUE_DATA_TYPES ("VALUE", ValueParameter.class) {
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

    };
    
    // Map to match up name to enum
    private static Map<String, VParameterElement> enumFromNameMap = makeEnumFromNameMap();
    private static Map<String, VParameterElement> makeEnumFromNameMap()
    {
        Map<String, VParameterElement> map = new HashMap<>();
        VParameterElement[] values = VParameterElement.values();
        for (int i=0; i<values.length; i++)
        {
            map.put(values[i].toString(), values[i]);
        }
        return map;
    }
    public static VParameterElement enumFromName(String parameterName)
    {
        final VParameterElement prop;
        // minimum property name is 2 characters
        boolean isLongEnough = parameterName.length() > 2;
        boolean isNonStanderd = (isLongEnough) ? parameterName.substring(0, VParameterElement.NON_STANDARD.toString().length()).equals(VParameterElement.NON_STANDARD.toString()) : false;
        if (isNonStanderd)
        {
            prop = VParameterElement.NON_STANDARD;
        } else
        {
            prop = enumFromNameMap.get(parameterName);   
        }
        return prop;
    }
    
    // Map to match up class to enum
    private static Map<Class<? extends VParameter<?>>, VParameterElement> enumFromClassMap = makeEnumFromClassMap();
    private static Map<Class<? extends VParameter<?>>, VParameterElement> makeEnumFromClassMap()
    {
        Map<Class<? extends VParameter<?>>, VParameterElement> map = new HashMap<>();
        VParameterElement[] values = VParameterElement.values();
        for (int i=0; i<values.length; i++)
        {
            map.put(values[i].myClass, values[i]);
        }
        return map;
    }
    /** get enum from map */
    public static VParameterElement enumFromClass(Class<? extends VElement> myClass)
    {
        VParameterElement p = enumFromClassMap.get(myClass);
        if (p == null)
        {
            throw new IllegalArgumentException(VParameterElement.class.getSimpleName() + " does not contain an enum to match the class:" + myClass.getSimpleName());
        }
        return p;
    }
    
    private String name;

    private Class<? extends VParameter<?>> myClass;
	public Class<? extends VParameter<?>> elementClass() { return myClass; }

    @Override  public String toString() { return name; }
    VParameterElement(String name, Class<? extends VParameter<?>> myClass)
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
    /** return default String converter associated with property value type */
    abstract public <T> StringConverter<T> getConverter();
}
