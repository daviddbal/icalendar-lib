package net.balsoftware.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.balsoftware.properties.component.misc.NonStandardProperty;

/**
 * <p>{@link VComponent} with the following properties
 * <ul>
 * <li>{@link NonStandardProperty X-PROP}
 * </ul>
 * </p>
 * 
 * @author David Bal
 *
 * @param <T> concrete subclass
 */
public abstract class VCommon<T> extends VComponentBase
{
    /**
     * Provides a framework for defining non-standard properties.
     * 
     * <p>Example:
     * <ul>
     * <l1>X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.
        org/mysubj.au
     *  </ul>
     */
    private List<NonStandardProperty> nonStandardProps;
    public List<NonStandardProperty> getNonStandard() { return nonStandardProps; }
    public void setNonStandard(List<NonStandardProperty> nonStandardProps)
    {
    	this.nonStandardProps = nonStandardProps;
    	nonStandardProps.forEach(c -> orderChild(c));
	}
    /**
     * Sets the value of the {@link #nonStandardProperty()} by parsing a vararg of
     * iCalendar content text representing individual {@link NonStandardProperty} objects.
     * 
     * @return - this class for chaining
     */
    public T withNonStandard(String...nonStandardProps)
    {
    	if (getNonStandard() == null)
    	{
        	setNonStandard(new ArrayList<>());
    	}
        List<NonStandardProperty> newElements = Arrays.stream(nonStandardProps)
                .map(c -> NonStandardProperty.parse(c))
                .collect(Collectors.toList());
        getNonStandard().addAll(newElements);
        return (T) this;
    }
    /**
     * Sets the value of the {@link #nonStandardProperty()}
     * 
     * @return - this class for chaining
     */
    public T withNonStandard(List<NonStandardProperty> nonStandardProps)
    {
    	if (getNonStandard() == null)
    	{
        	setNonStandard(new ArrayList<>());
    	}
    	getNonStandard().addAll(nonStandardProps);
        return (T) this;
    }
    /**
     * Sets the value of the {@link #nonStandardProperty()} from a vararg of {@link NonStandardProperty} objects.
     * 
     * @return - this class for chaining
     */    
    public T withNonStandard(NonStandardProperty...nonStandardProps)
    {
    	if (getNonStandard() == null)
    	{
        	setNonStandard(new ArrayList<>());
    	}
    	getNonStandard().addAll(Arrays.asList(nonStandardProps));
        return (T) this;
    }

    /*
     * CONSTRUCTORS
     */
    VCommon()
    {
        super();
    }
    
    VCommon(VCommon<T> source)
    {
        super(source);
    }
}
