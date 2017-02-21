package net.balsoftware.properties.component.recurrence.rrule.byxxx;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.balsoftware.properties.component.recurrence.rrule.RRuleElementType;

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
        
//        /*
//         * Add Listener to validate additions to value list
//         */
//        getValue().addListener((ListChangeListener.Change<? extends Integer> change) ->
//        {
//            while (change.next())
//            {
//                if (change.wasAdded())
//                {
//                    change.getAddedSubList().forEach(value -> 
//                    {
//                        if (! isValidValue().test(value))
//                        {
//                            throw new IllegalArgumentException("Out of range " + elementType().toString() + " value: " + value);
//                        }
//                    });
//                }
//            }
//        });
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
    public String toString()
    {
        String values = getValue().stream()
                .map(value -> value.toString())
                .collect(Collectors.joining(","));
        return RRuleElementType.enumFromClass(getClass()).toString() + "=" + values;
    }
    
    @Override
    public List<String> parseContent(String content)
    {
        Integer[] monthDayArray = Arrays.asList(content.split(","))
                .stream()
                .map(s -> Integer.parseInt(s))
                .toArray(size -> new Integer[size]);
        setValue(monthDayArray);
        return errors();
    }
}
