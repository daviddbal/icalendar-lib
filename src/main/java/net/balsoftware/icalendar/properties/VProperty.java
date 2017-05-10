package net.balsoftware.icalendar.properties;

import java.util.List;

import net.balsoftware.icalendar.VChild;
import net.balsoftware.icalendar.VParent;
import net.balsoftware.icalendar.parameters.NonStandardParameter;
import net.balsoftware.icalendar.parameters.ValueParameter;
import net.balsoftware.icalendar.properties.VPropertyBase;

/**
 * top-level interface for all iCalendar properties
 * 
 * @author David Bal
 * @see PropertyType - enum of all supported Properties
 * @see VPropertyBase
 *
 * @param <T> - type of value stored in Property
 */
public interface VProperty<T> extends VParent, VChild
{    
    /**
     * The value of the property.
     * 
     * For example, in the below property:
     * LOCATION;LANGUAGE=en:Bob's house
     * The value is the String "Bob's house"
     * 
     */
    T getValue();
    /** Set the value of the property */
    void setValue(T value);
        
    /**
     * VALUE
     * Value Date Types
     * RFC 5545 iCalendar 3.2.10 page 29
     * 
     * To explicitly specify the value type format for a property value.
     * 
     * Property value type.  Optional if default type is used.
     * Example:
     * VALUE=DATE
     */
    ValueParameter getValueType();
    /** Set the value type */
    void setValueType(ValueParameter value);

    /**
     * <h2>Non-Standard Parameters</h2>
     * 
     * <p>x-param     = x-name "=" param-value *("," param-value)<br>
     ; A non-standard, experimental parameter.</p>
     */
    List<NonStandardParameter> getNonStandard();
    void setNonStandard(List<NonStandardParameter> nonStandardParams);
}
