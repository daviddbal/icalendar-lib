package net.balsoftware.icalendar.components;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum VComponentElement
{
	VEVENT ("VEVENT", VEvent.class),
    VTODO ("VTODO", VTodo.class),
    VJOURNAL ("VJOURNAL", VJournal.class),
    VTIMEZONE ("VTIMEZONE", VTimeZone.class),
    VFREEBUSY ("VFREEBUSY", VFreeBusy.class),
    DAYLIGHT_SAVING_TIME ("DAYLIGHT", DaylightSavingTime.class),
    STANDARD_TIME ("STANDARD", StandardTime.class),
    VALARM ("VALARM", VAlarm.class),
	;
    
    // Map to match up class to enum
    private final static Map<Class<? extends VComponent>, VComponentElement> CLASS_MAP = Arrays
    		.stream(values())
    		.collect(Collectors.toMap(
    				v -> v.elementClass()
    				, v -> v));
//    private static Map<Class<? extends VComponent>, VComponentElement> makeEnumFromClassMap()
//    {
//    	Map<Class<? extends VComponent>, VComponentElement> map = new HashMap<>();
//    	VComponentElement[] values = VComponentElement.values();
//        for (int i=0; i<values.length; i++)
//        {
//            map.put(values[i].myClass, values[i]);
//        }
//        return map;
//    }
	public static VComponentElement fromClass(Class<? extends VComponent> vElementClass)
	{
		return CLASS_MAP.get(vElementClass);
	}
    	
	/*
	 * CONSTRUCTOR
	 */
    private String name;
    @Override  public String toString() { return name; }

    private Class<? extends VComponent> myClass;
	public Class<? extends VComponent> elementClass() { return myClass; }

    VComponentElement(String name, Class<? extends VComponent> myClass)
    {
        this.name = name;
        this.myClass = myClass;
    }
	
	public static List<String> names = Arrays
			.stream(values())
			.map(v -> v.name)
			.collect(Collectors.toList());
}
