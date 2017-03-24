package net.balsoftware.icalendar;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import net.balsoftware.icalendar.properties.component.recurrence.rrule.RRulePart;
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
		parseContent(childContent); // TODO - Do I want this?
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
	protected List<Message> parseContent(String content)
    {
        Iterator<String> i = Arrays.asList(content.split(System.lineSeparator())).iterator();
        return parseContent(new UnfoldingStringIterator(i));
    }

    /*
     * NOTE: PARAMETER AND PROPERTY MUST HAVE OVERRIDDEN PARSECONTENT (to handle value part)
     */
    protected List<Message> parseContent(Iterator<String> unfoldedLineIterator)
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
    		singlelineChildClass = RRulePart.class;			
		} else
		{
    		throw new RuntimeException("Not supported parent class:" + getClass());		
		}
    	
    	List<Message> messages = new ArrayList<>();
        while (unfoldedLineIterator.hasNext())
        {
            String unfoldedLine = unfoldedLineIterator.next();
            if (unfoldedLine.startsWith(END)) return messages; // exit when end found;
            String childName = elementName(unfoldedLine);
            if (childName != null) childName = (childName.startsWith("X-")) ? "X-" : childName;
            boolean isMultiLineElement = unfoldedLine.startsWith(BEGIN); // e.g. vcalendar, vcomponent
            boolean isMainComponent = name().equals(childName);
            final VElementBase child;
			if (isMultiLineElement)
            {
				if (! isMainComponent)
				{
	                child = (VElementBase) VElementBase.newEmptyVElement(multilineChildClass, childName);
	                List<Message> myMessages = ((VParentBase<?>) child).parseContent(unfoldedLineIterator); // recursively parse child parent
	                messages.addAll(myMessages);
	        		checkAndAddChild(messages, unfoldedLine, childName, (VChild) child);
				}
            } else
            { // single line element (e.g. property, parameter, rrule value)
            	if (isMainComponent)
            	{ // a main component still needs it value and elements processed in subclasses (e.g property)
            		child = this;
            	} else
            	{
            		child = (VElementBase) VElementBase.newEmptyVElement(singlelineChildClass, childName);
            	}
            	
                if (child != null)
                {
	                List<Message> myMessages = ((VParentBase<?>) child).parseContent(unfoldedLine); // recursively parse child parent
	                // don't add single-line children with info or error messages - they have problems and should be ignored
	                if (myMessages.isEmpty())
	                {
	            		checkAndAddChild(messages, unfoldedLine, childName, (VChild) child);                	
	                } else
	                {
	                	messages.addAll(myMessages);
	                }
                } else
                {
                	messages.add(new Message(this,
                			"Unknown element:" + unfoldedLine,
                			MessageEffect.MESSAGE_ONLY));
                }
            }
        }
        return messages;
    }

    // For Recurrence Rule Value and Properties
	protected void processInLineChild(
			List<Message> messages, 
			String childName, 
			String content,
			Class<? extends VElement> singleLineChildClass)
	{
        VChild newChild = VElementBase.newEmptyVElement(singleLineChildClass, childName);
        if (newChild != null)
        {
        	List<Message> myMessages = ((VElementBase) newChild).parseContent(childName + "=" + content);
	        messages.addAll(myMessages);
			checkAndAddChild(messages, content, childName, newChild);
        } else
        {
        	messages.add(new Message(this,
        			"Unknown element:" + content,
        			MessageEffect.MESSAGE_ONLY));
        }
	}

	protected void checkAndAddChild(List<Message> messages, String content, String elementName, VChild newChild) {
		if (newChild == null)
		{
			Message message = new Message(this,
					"Ignored invalid element:" + content,
					MessageEffect.MESSAGE_ONLY);
			messages.add(message);
			return;
		}
		Method getter = getGetter(newChild);
		boolean isChildAllowed = getter != null;
		if (! isChildAllowed)
		{
			Message message = new Message(this,
					 elementName + " not allowed in " + name(),
					MessageEffect.THROW_EXCEPTION);
			messages.add(message);
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
			isChildAlreadyPresent = ((Collection<?>) currentParameter).contains(newChild); // TOSO contains is expensive - try to find a way to avoid
		} else
		{
			isChildAlreadyPresent = currentParameter != null;			
		}
		if (isChildAlreadyPresent)
		{
			Message message = new Message(this,
					newChild.getClass().getSimpleName() + " can only occur once in a calendar component.  Ignoring instances beyond first.",
					MessageEffect.MESSAGE_ONLY);
			messages.add(message);
		}
		if (isChildAllowed && ! isChildAlreadyPresent)
		{
			addChild(newChild);
		}
	}
		
    /* Strategy to build iCalendar content lines */
    protected ContentLineStrategy contentLineGenerator;
        
    @Override
	public List<VChild> childrenUnmodifiable()
    {
    	return orderer.childrenUnmodifiable();
    }
    
    protected void copyChildrenInto(VParent destination)
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
        
        // getter version is slower, but will be correct.
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
