package net.balsoftware.icalendar.properties.component.timezone;

import java.time.ZoneOffset;

import net.balsoftware.icalendar.properties.VPropertyBase;

/**
 * Base class for ZoneOffset-based properties
 * 
 * @author David Bal
 *
 * @param <U> - concrete subclass
 * @see TimeZoneOffsetFrom
 * @see TimeZoneOffsetTo
 */
public abstract class PropertyBaseZoneOffset<U> extends VPropertyBase<ZoneOffset,U>
{
    public PropertyBaseZoneOffset(PropertyBaseZoneOffset<U> source)
    {
        super(source);
    }
    
    public PropertyBaseZoneOffset(ZoneOffset value)
    {
        super(value);
    }

    public PropertyBaseZoneOffset()
    {
        super();
    }
}
