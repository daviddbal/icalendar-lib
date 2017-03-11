package net.balsoftware.icalendar;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import net.balsoftware.icalendar.components.VComponent;
import net.balsoftware.icalendar.content.ContentLineStrategy;
import net.balsoftware.icalendar.content.Orderer;
import net.balsoftware.icalendar.content.OrdererBase;
import net.balsoftware.icalendar.content.UnfoldingStringIterator;
import net.balsoftware.icalendar.parameters.VParameter;
import net.balsoftware.icalendar.properties.VProperty;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.RRuleElement;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.RecurrenceRuleValue;
import net.balsoftware.icalendar.utilities.ICalendarUtilities;

/**
 * <p>Base class for parent calendar components.</p>
 * 
 * <p>The order of the children from {@link #childrenUnmodifiable()} equals the order they were added.
 * Adding children is not exposed by the implementation, but rather handled internally.  When a {@link VChild} has its
 * value set, it's automatically included in the collection of children by the {@link Orderer}.</p>
 * 
 * <p>The {@link Orderer} requires registering listeners to child properties.</p>
 * 
 * @author David Bal
 */
public abstract class VParentBase<T> implements VParent
{
	/* Setter, getter maps
	 * The first key is the VParent class
	 * The second key is the VChild of that VParent
	 */
	private static final  Map<Class<? extends VParent>, Map<Class<? extends VChild>, Method>> SETTERS = new HashMap<>();
	private static final  Map<Class<? extends VParent>, Map<Class<? extends VChild>, Method>> GETTERS = new HashMap<>();

    /*
     * HANDLE SORT ORDER FOR CHILD ELEMENTS
     */
    protected Orderer orderer;
    /** Return the {@link Orderer} for this {@link VParent} */
    
	@Override
	public void orderChild(VChild addedChild)
	{
		orderer.orderChild(addedChild);
	}
	
	@Override
	public void orderChild(VChild oldChild, VChild newChild)
	{
		orderer.replaceChild(oldChild, newChild);
	}

	@Override
	public void orderChild(int index, VChild addedChild)
	{
		orderer.orderChild(index, addedChild);
	}

	@Override
    public void addChild(VChild child)
    {
		Method setter = getSetter(child);
		boolean isList = Collection.class.isAssignableFrom(setter.getParameters()[0].getType());
		try {
			if (isList)
			{
				Method getter = getGetter(child);
				Collection<VChild> list = (Collection<VChild>) getter.invoke(this);
				if (list == null)
				{
					list = (getter.getReturnType() == List.class) ? new ArrayList<>() :
						   (getter.getReturnType() == Set.class) ? new LinkedHashSet<>() : new ArrayList<>();
					list.add(child);
					setter.invoke(this, list);
				} else
				{
					list.add(child);
					orderChild(child);
				}
			} else
			{
				setter.invoke(this, child);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
    }
	@Override
    public void addChild(int index, VChild child)
    {
		addChild(child);
		orderChild(index, child);
    }
	@Override
	public void addChild(String childContent)
	{
		throw new RuntimeException("not implemented yet");
	}
	@Override
	public boolean removeChild(VChild child)
	{
		Method setter = getSetter(child);
		boolean isList = Collection.class.isAssignableFrom(setter.getParameters()[0].getType());
		try {
			if (isList)
			{
				Method getter = getGetter(child);
				List<VChild> list = (List<VChild>) getter.invoke(this);
				if (list == null)
				{
					return false;
				} else
				{
					boolean result = list.remove(child);
					orderChild(child, null);
					// Should I leave empty lists? - below code removes empty lists
//					if (list.isEmpty())
//					{
//						setter.invoke(this, (Object) null);
//					}
					return result;
				}
			} else
			{
				setter.invoke(this, (Object) null);
				orderChild(child, null);
				return true;
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean removeChild(int index)
	{
		return removeChild(childrenUnmodifiable().get(index));
	}
	@Override
	public boolean replaceChild(int index, VChild child)
	{
		removeChild(index);
		addChild(index, child);
		return true;
	}
	@Override
	public boolean replaceChild(VChild oldChild, VChild newChild)
	{
		return orderer.replaceChild(oldChild, newChild);
	}
	public T withChild(VChild child)
	{
		addChild(child);
		return (T) this;
	}
	
    protected Map<Class<? extends VChild>, Method> getSetters()
    {
    	if (SETTERS.get(getClass()) == null)
    	{
    		Map<Class<? extends VChild>, Method> setterMap = ICalendarUtilities.collectSetterMap(getClass());
			SETTERS.put(getClass(), setterMap);
			return setterMap;
    	}
    	return SETTERS.get(getClass());
    }
    
    protected Map<Class<? extends VChild>, Method> getGetters()
    {
    	if (GETTERS.get(getClass()) == null)
    	{
    		Map<Class<? extends VChild>, Method> getterMap = ICalendarUtilities.collectGetterMap(getClass());
			GETTERS.put(getClass(), getterMap);
			return getterMap;
    	}
    	return GETTERS.get(getClass());
    }
	protected Method getSetter(VChild child)
	{
		return getSetters().get(child.getClass());
	}
	protected Method getGetter(VChild child)
	{
		return getGetters().get(child.getClass());
	}
	
    @Override
    public List<String> parseContent(String content)
    {
        Iterator<String> i = Arrays.asList(content.split(System.lineSeparator())).iterator();
        Map<VElement, List<String>> log = parseContent(new UnfoldingStringIterator(i));
        return log.entrySet()
        		.stream()
        		.flatMap(e -> e.getValue().stream())
        		.collect(Collectors.toList());
    }
    
    public Map<VElement, List<String>> parseContent(Iterator<String> unfoldedLineIterator)
    {
    	final Class<? extends VElement> multilineChildClass;
    	final Class<? extends VElement> singlelineChildClass;
    	if (VCalendar.class.isAssignableFrom(getClass()))
		{
    		multilineChildClass = VComponent.class;
    		singlelineChildClass = VProperty.class;
		} else if (VComponent.class.isAssignableFrom(getClass()))
		{
    		multilineChildClass = null;
    		singlelineChildClass = VProperty.class;			
		} else if (VProperty.class.isAssignableFrom(getClass()))
		{
    		multilineChildClass = null;
    		singlelineChildClass = VParameter.class;			
		} else if (RecurrenceRuleValue.class.isAssignableFrom(getClass()))
		{
    		multilineChildClass = null;
    		singlelineChildClass = RRuleElement.class;			
		} else
		{
    		throw new RuntimeException("Not supported parent class:" + getClass());		
		}
    	
        while (unfoldedLineIterator.hasNext())
        {
            String unfoldedLine = unfoldedLineIterator.next();
            int nameEndIndex = ICalendarUtilities.getPropertyNameIndex(unfoldedLine);
            String propertyName = (nameEndIndex > 0) ? unfoldedLine.substring(0, nameEndIndex) : "";
            boolean isMultiLineElement = propertyName.equals("BEGIN");
			if (isMultiLineElement)
            {
                String subcomponentName = unfoldedLine.substring(nameEndIndex+1);
				VParentBase<?> p = (VParentBase<?>) Elements.newEmptyVElement(multilineChildClass, subcomponentName);
                p.parseContent(unfoldedLineIterator);
                addChild((VChild) p);
            } else if (propertyName.equals("END"))
            {
                break; // exit when end found
            } else
            {  // parse single-line element
//            	System.out.println(singlelineChildClass);
                VChild c = (VChild) Elements.parseNewElement(singlelineChildClass, propertyName, unfoldedLine);
                addChild(c);
            }
        }
        return Collections.EMPTY_MAP;
    }

	
    /* Strategy to build iCalendar content lines */
    protected ContentLineStrategy contentLineGenerator;
        
    @Override
	public List<VChild> childrenUnmodifiable()
    {
    	return orderer.childrenUnmodifiable();
    }
    
    
    public void copyChildrenInto(VParent destination)
    {
        childrenUnmodifiable().forEach((childSource) -> 
        {
        	try {
        		// use copy constructors to make copy of child
        		VChild newChild = childSource.getClass()
        				.getConstructor(childSource.getClass())
        				.newInstance(childSource);
        		destination.addChild(newChild);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
        });
    }
    
    /*
     * CONSTRUCTOR
     */
    public VParentBase()
    {
    	orderer = new OrdererBase(this, getGetters());
    }
    
    // copy constructor
    public VParentBase(VParentBase<T> source)
    {
    	this();
        source.copyChildrenInto(this);
    }
    
	@Override
    public List<String> errors()
    {
        return childrenUnmodifiable().stream()
                .flatMap(c -> c.errors().stream())
                .collect(Collectors.toList());
    }


    @Override
    public String toString()
    {
        if (contentLineGenerator == null)
        {
            throw new RuntimeException("Can't produce content lines because contentLineGenerator isn't set");  // contentLineGenerator MUST be set by subclasses
        }
        return contentLineGenerator.execute();
    }
    
    // Note: can't check equals or hashCode of parents - causes stack overflow
    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if((obj == null) || (obj.getClass() != getClass())) {
            return false;
        }
        VParent testObj = (VParent) obj;
        Map<Class<? extends VChild>, Method> getters = getGetters();
        return getters.entrySet()
        	.stream()
        	.map(e -> e.getValue())
        	.allMatch(m ->
        	{
				try {
					Object v1 = m.invoke(this);
	        		Object v2 = m.invoke(testObj);
//	        		if (v1 != null) System.out.println("equals:" + v1 + " "  +v2 + ":" + Objects.equals(v1, v2) + " " + v1.getClass().getSimpleName());
	        		return Objects.equals(v1, v2);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
					e1.printStackTrace();
				}
				return false;
        	});
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        for (VChild child : childrenUnmodifiable())
        {
            result = prime * result + child.hashCode();
        }
        return result;
    }
}
