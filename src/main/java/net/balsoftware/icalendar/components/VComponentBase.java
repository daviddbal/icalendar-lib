package net.balsoftware.icalendar.components;

import java.util.List;

import net.balsoftware.icalendar.CalendarComponent;
import net.balsoftware.icalendar.Elements;
import net.balsoftware.icalendar.VParent;
import net.balsoftware.icalendar.VParentBase;
import net.balsoftware.icalendar.content.MultiLineContent;;

/**
 * <p>Base class implementation of a {@link VComponent}</p>
 * 
 * @author David Bal
 */
public abstract class VComponentBase<T> extends VParentBase<T> implements VComponent
{
    private static final String FIRST_LINE_PREFIX = "BEGIN:";
    private static final String LAST_LINE_PREFIX = "END:";
    
    protected VParent parent;
    @Override public void setParent(VParent parent) { this.parent = parent; }
    @Override public VParent getParent() { return parent; }
    
    final private CalendarComponent componentType;
    @Override
    public String name() { return componentType.toString(); }

    /*
     * CONSTRUCTORS
     */
    /**
     * Create default component by setting {@link componentName}, and setting content line generator.
     */
    VComponentBase()
    {
    	super();
    	componentType = CalendarComponent.enumFromClass(this.getClass());
        contentLineGenerator = new MultiLineContent(
                orderer,
                FIRST_LINE_PREFIX + name(),
                LAST_LINE_PREFIX + name(),
                400);
    }
    
    /**
     * Creates a deep copy of a component
     */
    VComponentBase(VComponentBase<T> source)
    {
    	super(source);
    	componentType = CalendarComponent.enumFromClass(this.getClass());
        contentLineGenerator = new MultiLineContent(
                orderer,
                FIRST_LINE_PREFIX + name(),
                LAST_LINE_PREFIX + name(),
                400);
    }
   
    /**
     * Hook to add subcomponent such as {@link #VAlarm}, {@link #StandardTime} and {@link #DaylightSavingTime}
     * 
     * @param subcomponent
     */
    void addSubcomponent(VComponent subcomponent)
    { // no opp by default
    }
    
    /**
     * Creates a new VComponent by parsing a String of iCalendar content text
     * @param <T>
     *
     * @param content  the text to parse, not null
     * @return  the parsed DaylightSavingTime
     */
    public static <T extends VComponentBase<?>> T parse(String content)
    {
        boolean isMultiLineElement = content.startsWith("BEGIN");
        if (! isMultiLineElement)
        {
        	throw new IllegalArgumentException("VComponent must begin with BEGIN [" + content + "]");
        }
        int firstLineBreakIndex = content.indexOf(System.lineSeparator());
        String name = content.substring(6,firstLineBreakIndex);
    	T component = (T) Elements.newEmptyVElement(VComponent.class, name);
        List<Message> messages = component.parseContent(content);
        throwMessageExceptions(messages);
        return component;
    }
}
