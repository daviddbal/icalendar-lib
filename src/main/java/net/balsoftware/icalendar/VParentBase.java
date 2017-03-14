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
import net.balsoftware.icalendar.utilities.Pair;

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
public abstract class VParentBase<T> extends VElementBase implements VParent
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
//		System.out.println("Child:" + child);
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
		parseContent(childContent);
//		throw new RuntimeException("not implemented yet");
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
	protected Map<VElement, List<String>> parseContent(String content)
    {
        Iterator<String> i = Arrays.asList(content.split(System.lineSeparator())).iterator();
        return parseContent(new UnfoldingStringIterator(i));
    }
    
    protected Map<VElement, List<String>> parseContent(Iterator<String> unfoldedLineIterator)
    {
    	final Class<? extends VElement> multilineChildClass;
    	final Class<? extends VElement> singlelineChildClass;
    	if (VCalendar.class.isAssignableFrom(getClass()))
		{
    		multilineChildClass = VComponent.class;
    		singlelineChildClass = VProperty.class;
		} else if (VComponent.class.isAssignableFrom(getClass()))
		{
    		multilineChildClass = VComponent.class;
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
            boolean isMainComponent = unfoldedLine.substring(nameEndIndex+1).equals(name()) && isMultiLineElement;
            if (isMainComponent) continue; // skip main component
			if (propertyName.equals("END"))
            {
                return Collections.EMPTY_MAP; // exit when end found
            }
            final String childName;
            final VElementBase p;
			if (isMultiLineElement)
            {
                childName = unfoldedLine.substring(nameEndIndex+1);
                p = (VElementBase) Elements.newEmptyVElement(multilineChildClass, childName);
                ((VParentBase<?>) p).parseContent(unfoldedLineIterator); // recursively parse child parent
            } else
            {
            	childName = propertyName;
                p = (VElementBase) Elements.parseNewElement(singlelineChildClass, childName, unfoldedLine);
            }
			// PARAMETER AND PROPERTY MUST HAVE OVERRIDDEN PARSECONTENT (to handle value part)
            addChild((VChild) p);
        }
        return Collections.EMPTY_MAP;
    }
	
	private Class<? extends VElement> singleLineChildClass()
	{
    	if (VCalendar.class.isAssignableFrom(getClass()))
		{
    		return VProperty.class;
		} else if (VComponent.class.isAssignableFrom(getClass()))
		{
    		return VProperty.class;			
		} else if (VProperty.class.isAssignableFrom(getClass()))
		{
    		return VParameter.class;			
		} else if (RecurrenceRuleValue.class.isAssignableFrom(getClass()))
		{
    		return RRuleElement.class;			
		} else
		{
    		throw new RuntimeException("Not supported parent class:" + getClass());		
		}
	}
    	
	protected void processInLineChild(Map<VElement, List<String>> messages, Pair<String, String> entry)
	{
		VChild newChild = (VChild) Elements.parseNewElement(singleLineChildClass(), entry.getKey(), entry.getKey() + "=" + entry.getValue());
//		VChild newChild = (VChild) Elements.parseNewElement(singleLineChildClass(), entry.getKey(), entry.getValue());
		if (newChild == null)
		{
			addToList(messages, "Ignored invalid element:" + entry.getValue());
			return;
		}
//		VChild newChild = (VChild) Elements.newEmptyVElement(singleLineChildClass(), entry.getKey());
		Method getter = getGetter(newChild);
		boolean isChildAllowed = getter != null;
		if (isChildAllowed)
		{
			String newMessage = entry.getKey() + " not allowed in " + name();
//			throw new IllegalArgumentException(newMessage);
			addToList(messages, newMessage);
		}
		final boolean isChildAlreadyPresent;
		Object currentParameter = null;
		try {
			currentParameter = getter.invoke(this);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		if (currentParameter instanceof Collection)
		{
			isChildAlreadyPresent = ((Collection<?>) currentParameter).contains(newChild);
		} else
		{
			isChildAlreadyPresent = currentParameter != null;			
		}
//		System.out.println("isChildAlreadyPresent:" + isChildAlreadyPresent + " " + newChild);
		if (isChildAlreadyPresent)
		{
			// TODO - SHOULD I ADD AS MESSAGE OR USE EXCEPTION?
			String newMessage = newChild.getClass().getSimpleName() + " can only occur once in a calendar component.  Ignoring instances beyond first.";
//			addToList(messages, message);
			throw new IllegalArgumentException(newMessage);
		}
		if (isChildAllowed && ! isChildAlreadyPresent)
		{
			addChild(newChild);
		}
	}
	
	private void addToList(Map<VElement, List<String>> messages, String newMessage)
	{
		final List<String> myMessages;
		if ((messages.get(this) == null))
		{
			myMessages = new ArrayList<>();
			messages.put(this, myMessages);
		} else
		{
			myMessages = messages.get(this);
		}
		myMessages.add(newMessage);
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
