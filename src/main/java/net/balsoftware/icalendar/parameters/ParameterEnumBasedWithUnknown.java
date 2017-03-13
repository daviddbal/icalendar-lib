package net.balsoftware.icalendar.parameters;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.balsoftware.icalendar.VElement;

public abstract class ParameterEnumBasedWithUnknown<U,T> extends ParameterBase<U,T>
{
    private String nonStandardValue; // contains exact string for unknown value
    
    /*
     * CONSTRUCTORS
     */
    public ParameterEnumBasedWithUnknown() //List<String> registeredIANAValues)
    {
        super();
    }
  
    public ParameterEnumBasedWithUnknown(T value) //, List<String> registeredIANAValues)
    {
        this();
        setValue(value);
    }
    
    public ParameterEnumBasedWithUnknown(ParameterEnumBasedWithUnknown<U,T> source)
    {
        super(source);
        nonStandardValue = source.nonStandardValue;
    }
        
    @Override
    String valueAsString()
    {
        return (getValue().toString().equals("UNKNOWN")) ? nonStandardValue : super.valueAsString();
    }
    
    @Override
    protected Map<VElement, List<String>> parseContent(String content)
    {
        super.parseContent(content);
        if (getValue().toString().equals("UNKNOWN"))
        {
            String valueString = VParameter.extractValue(content);
            nonStandardValue = valueString;
        }
        return Collections.EMPTY_MAP;
    }
}
