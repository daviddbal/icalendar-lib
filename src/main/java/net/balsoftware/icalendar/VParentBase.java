package net.balsoftware.icalendar;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import net.balsoftware.icalendar.content.ContentLineStrategy;
import net.balsoftware.icalendar.content.Orderer;
import net.balsoftware.icalendar.content.OrdererBase;
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
					if (list.isEmpty())
					{
						setter.invoke(this, (Object) null);
					}
					return result;
				}
			} else
			{
				setter.invoke(this, (Object) null);
				return true;
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
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
//        		System.out.println("destination:" + destination.getClass().getSimpleName());
//        		System.out.println("childSource:" + childSource.getClass().getSimpleName());
        		VChild newChild = childSource.getClass()
        				.getConstructor(childSource.getClass())
        				.newInstance(childSource);
//        		System.out.println("newChild:" + newChild);
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
//		System.out.println("error check");
//        return orderer.childrenUnmodifiableFast().stream()
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
        		Object v1;
				try {
					v1 = m.invoke(this);
	        		Object v2 = m.invoke(testObj);
	        		return Objects.equals(v1, v2);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
					e1.printStackTrace();
				}
				return false;
        	});
        
//        System.out.println("equals");
//        Collection<VChild> c1 = childrenUnmodifiable();
//        Collection<VChild> c2 = testObj.childrenUnmodifiable();
//        Collection<VChild> c1 = orderer.childrenUnmodifiableFast();
//        Collection<VChild> c2 = ((VParentBase) testObj).orderer.childrenUnmodifiableFast();
//        if (c1.size() == c2.size())
//        {
//            Iterator<VChild> i1 = c1.iterator();
//            Iterator<VChild> i2 = c2.iterator();
//            for (int i=0; i<c1.size(); i++)
//            {
//                VChild child1 = i1.next();
//                VChild child2 = i2.next();
//                if (! child1.equals(child2))
//                {
//                    return false;
//                }
//            }
//        } else
//        {
//            return false;
//        }
//        return true;
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
