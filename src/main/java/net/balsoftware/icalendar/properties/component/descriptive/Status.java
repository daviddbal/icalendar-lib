package net.balsoftware.icalendar.properties.component.descriptive;

import java.util.HashMap;
import java.util.Map;

import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.components.VJournal;
import net.balsoftware.icalendar.components.VTodo;
import net.balsoftware.icalendar.properties.VPropertyBase;
import net.balsoftware.icalendar.properties.component.descriptive.Status.StatusType;
import net.balsoftware.icalendar.utilities.StringConverter;

/**
 * STATUS
 * RFC 5545 iCalendar 3.8.1.11. page 92
 * 
 * This property defines the overall status or confirmation for the calendar component.
 * 
 * Example:
 * STATUS:TENTATIVE
 *
 * @author David Bal
 * 
 * The property can be specified in following components:
 * @see VEvent
 * @see VTodo
 * @see VJournal
 */
public class Status extends VPropertyBase<StatusType, Status>
{
    private final static StringConverter<StatusType> CONVERTER = new StringConverter<StatusType>()
    {
        @Override
        public String toString(StatusType object)
        {
            return object.toString();
        }

        @Override
        public StatusType fromString(String string)
        {
            return StatusType.enumFromName(string);
        }
    };
    
    public Status(StatusType value)
    {
        this();
        setValue(value);
    }
    
    public Status(Status source)
    {
        super(source);
    }
    
    public Status()
    {
        super();
        setConverter(CONVERTER);
    }
    
    public static Status parse(String propertyContent)
    {
        Status property = new Status();
        property.parseContent(propertyContent);
        return property;
    }
    public enum StatusType
    {
        TENTATIVE ("TENTATIVE"),
        CONFIRMED ("CONFIRMED"),
        CANCELLED ("CANCELLED"),
        NEEDS_ACTION ("NEEDS-ACTION"),
        COMPLETED ("COMPLETED"),
        IN_PROCESS ("IN-PROCESS"),
        DRAFT ("DRAFT"),
        FINAL ("FINAL");
        
        private static Map<String, StatusType> enumFromNameMap = makeEnumFromNameMap();
        private static Map<String, StatusType> makeEnumFromNameMap()
        {
            Map<String, StatusType> map = new HashMap<>();
            StatusType[] values = StatusType.values();
            for (int i=0; i<values.length; i++)
            {
                map.put(values[i].toString(), values[i]);
            }
            return map;
        }
        /** get enum from name */
        public static StatusType enumFromName(String propertyName)
        {
            StatusType type = enumFromNameMap.get(propertyName.toUpperCase());
            if (type == null)
            {
                throw new IllegalArgumentException(propertyName + " is not a vaild StatusType");
            }
            return type;
        }
        
        private String name;
        @Override public String toString() { return name; }
        StatusType(String name)
        {
            this.name = name;
        }
    }
}
