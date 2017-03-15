package net.balsoftware.icalendar.parameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.balsoftware.icalendar.VElementBase;
import net.balsoftware.icalendar.VParent;
import net.balsoftware.icalendar.utilities.StringConverter;

/**
 * Base class of all iCalendar Parameters.  Parameters can't have children.
 * Example VALUE=DATE
 * 
 * @author David Bal
 *
 * @param <T> - type of value stored in the Parameter, such as String for text-based, or the enumerated type of the classes based on an enum
 * @param <U> - implemented subclass
 */
abstract public class ParameterBase<U,T> extends VElementBase implements VParameter<T>
{
    private VParent myParent;
    @Override public void setParent(VParent parent) { myParent = parent; }
    @Override public VParent getParent() { return myParent; }
    
    final private String name;
    @Override
    public String name() { return name; }

    /*
     * PARAMETER VALUE
     */
    @Override
    public T getValue()
    {
        return value;
    }
    private T value;
    @Override
    public void setValue(T value)
    {
        this.value = value;
    }
    public U withValue(T value)
    {
        setValue(value);
        return (U) this;
    }
    
    // convert value to string, is overridden for enum-based parameters to handle UNKNOWN value
    String valueAsString()
    {
        return getConverter().toString(getValue());
    }
    
    @Override
    protected List<Message> parseContent(String content)
    {
        String valueString = VParameter.extractValue(content);
        T value = getConverter().fromString(valueString);
        setValue(value);
        return Collections.EMPTY_LIST;
    }
    
    /**
     * STRING CONVERTER
     * 
     * Get the parameter value string converter.
     */ 
    private StringConverter<T> getConverter()
    {
        return converter;
    }
    final private StringConverter<T> converter;
    
    /**
     * PARAMETER TYPE
     * 
     *  The enumerated type of the parameter.
     */
    @Override
    public ParameterType parameterType() { return parameterType; }
    final private ParameterType parameterType;

//	@Override
//	public void copyInto(VElement destination)
//	{
//		setValue(((Parameter<T>) destination).getValue());
//	}
	
    @Override
    public String toString()
    {
        String string = valueAsString();
        String content = (getValue() != null) ? parameterType().toString() + "=" + string : null;
        return content;
    }

    @Override // Note: can't check equality of parents - causes stack overflow
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if((obj == null) || (obj.getClass() != getClass())) {
            return false;
        }
        ParameterBase<U,T> testObj = (ParameterBase<U,T>) obj;
        boolean valueEquals = getValue().equals(testObj.getValue());
        return valueEquals;
    }
    
    @Override // Note: can't check hashCode of parents - causes stack overflow
    public int hashCode()
    {
        int hash = super.hashCode();
        final int prime = 31;
        hash = prime * hash + getValue().hashCode();
        return hash;
    }
    
    /*
     * CONSTRUCTORS
     */
    ParameterBase()
    {
        parameterType = ParameterType.enumFromClass(getClass());
        name = parameterType.toString();
        converter = parameterType.getConverter();
    }

    ParameterBase(T value)
    {
        this();
        setValue(value);
    }

    
    ParameterBase(ParameterBase<U,T> source)
    {
        this();
        setValue(source.getValue());
    }
    
    @Override
    public List<String> errors()
    {
        List<String> errors = new ArrayList<>();
        if (getValue() == null)
        {
            errors.add(name() + " value is null.  The parameter MUST have a value."); 
        }
        return errors;
    }
}
