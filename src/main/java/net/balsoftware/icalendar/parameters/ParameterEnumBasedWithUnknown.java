package net.balsoftware.icalendar.parameters;

import java.util.Collections;
import java.util.List;

import net.balsoftware.icalendar.utilities.StringConverter;

public abstract class ParameterEnumBasedWithUnknown<U,T> extends VParameterBase<U,T>
{
    private String nonStandardValue; // contains exact string for unknown value
    
    /*
     * CONSTRUCTORS
     */
    public ParameterEnumBasedWithUnknown(StringConverter<T> stringConverter)
    {
        super(stringConverter);
    }
  
    public ParameterEnumBasedWithUnknown(T value, StringConverter<T> stringConverter) 
    {
        this(stringConverter);
        setValue(value);
    }
    
    public ParameterEnumBasedWithUnknown(ParameterEnumBasedWithUnknown<U,T> source, StringConverter<T> stringConverter)
    {
        super(source, stringConverter);
        nonStandardValue = source.nonStandardValue;
    }
        
    @Override
    String valueAsString()
    {
        return (getValue().toString().equals("UNKNOWN")) ? nonStandardValue : super.valueAsString();
    }
    
    @Override
    protected List<Message> parseContent(String content)
    {
        super.parseContent(content);
        if (getValue().toString().equals("UNKNOWN"))
        {
            String valueString = VParameter.extractValue(content);
            nonStandardValue = valueString;
        }
        return Collections.EMPTY_LIST;
    }
}
