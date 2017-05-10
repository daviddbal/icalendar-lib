package net.balsoftware.icalendar.components;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import net.balsoftware.icalendar.VCalendar;
import net.balsoftware.icalendar.VElement;
import net.balsoftware.icalendar.components.DaylightSavingTime;
import net.balsoftware.icalendar.components.StandardTime;
import net.balsoftware.icalendar.components.VAlarm;
import net.balsoftware.icalendar.components.VComponent;
import net.balsoftware.icalendar.components.VComponentElement;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.components.VFreeBusy;
import net.balsoftware.icalendar.components.VJournal;
import net.balsoftware.icalendar.components.VTimeZone;
import net.balsoftware.icalendar.components.VTodo;

/**
 * <p>Enumerated type containing all the {@link VComponent} elements that can be in a {@link VCalendar}</p>
 * 
 * @author David Bal
 *
 */
public enum VComponentElement
{
    // MAIN COMPONENTS
    VEVENT ("VEVENT", VEvent.class),
    VTODO ("VTODO", VTodo.class),
    VJOURNAL ("VJOURNAL", VJournal.class),
    VTIMEZONE ("VTIMEZONE", VTimeZone.class),
    VFREEBUSY ("VFREEBUSY", VFreeBusy.class),
    DAYLIGHT_SAVING_TIME ("DAYLIGHT", DaylightSavingTime.class),
    STANDARD_TIME ("STANDARD", StandardTime.class),
    VALARM ("VALARM", VAlarm.class)
    ;

    // Map to match up name to enum
    private static final Map<String, VComponentElement> NAME_MAP = Arrays.stream(values())
    		.collect(Collectors.toMap(
    				v -> v.toString(),
    				v -> v));
    public static VComponentElement fromName(String propertyName)
    {
        return NAME_MAP.get(propertyName.toUpperCase());
    }
    
    // Map to match up class to enum
    private static final Map<Class<? extends VComponent>, VComponentElement> CLASS_MAP = Arrays.stream(values())
    		.collect(Collectors.toMap(
    				v -> v.elementClass(),
    				v -> v));
    /** get enum from map */
    public static VComponentElement fromClass(Class<? extends VElement> myClass)
    {
    	VComponentElement p = CLASS_MAP.get(myClass);
        if (p == null)
        {
            throw new IllegalArgumentException(VComponentElement.class.getSimpleName() + " does not contain an enum to match the class:" + myClass.getSimpleName());
        }
        return p;
    }
    
    /*
     * FIELDS
     */
    private Class<? extends VComponent> myClass;
    public Class<? extends VComponent> elementClass() { return myClass; }
    
    private String name;
    @Override
    public String toString() { return name; }

    /*
     * CONSTRUCTOR
     */
    VComponentElement(String name, Class<? extends VComponent> myClass)
    {
        this.name = name;
        this.myClass = myClass;
    }
}
