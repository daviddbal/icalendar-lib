package net.balsoftware.icalendar.properties.component.recurrence.rrule.byxxx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.balsoftware.icalendar.VElement;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.RRuleElementType;

public abstract class ByRuleIntegerAbstract<U> extends ByRuleAbstract<Integer, U>
{
    @Override
    public void setValue(List<Integer> values)
    {
        // validate values
        values.forEach(value -> 
        {
            if (! isValidValue().test(value))
            {
                throw new IllegalArgumentException("Out of range " + elementType().toString() + " value: " + value);
            }            
        });
        super.setValue(values);
    }
    /** predicate tests value range in listener attached to {@link #getValue()} 
     * Ensures added values are within allowed range */
    abstract Predicate<Integer> isValidValue();
    
    /*
     * CONSTRUCTORS
     */
    public ByRuleIntegerAbstract()
    {
        super();
        setValue(new ArrayList<>());
    }
    
    public ByRuleIntegerAbstract(Integer... values)
    {
        super(values);
    }
    
    public ByRuleIntegerAbstract(ByRuleIntegerAbstract<U> source)
    {
        super(source);
    }
        
    
    @Override
	public List<String> errors()
    {
    	List<String> errors = super.errors();
    	List<String> myErrors = getValue()
			.stream()
			.filter(v -> ! isValidValue().test(v))
			.map(v -> "Out of range " + name() + " value: " + v)
			.collect(Collectors.toList());
    	errors.addAll(myErrors);
    	return errors;
    }
    
	@Override
    public String toString()
    {
        String values = getValue().stream()
                .map(value -> value.toString())
                .collect(Collectors.joining(","));
        return RRuleElementType.enumFromClass(getClass()).toString() + "=" + values;
    }
    
    @Override
    public Map<VElement, List<String>> parseContent(String content)
    {
        Integer[] monthDayArray = Arrays.asList(content.split(","))
                .stream()
                .map(s -> Integer.parseInt(s))
                .toArray(size -> new Integer[size]);
        setValue(monthDayArray);
        return Collections.EMPTY_MAP;
    }
}
