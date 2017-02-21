package net.balsoftware;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import net.balsoftware.content.ContentLineStrategy;
import net.balsoftware.content.Orderer;
import net.balsoftware.content.OrdererBase;
import net.balsoftware.utilities.Callback;

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
public abstract class VParentBase implements VParent
{
    /*
     * HANDLE SORT ORDER FOR CHILD ELEMENTS
     */
    protected Orderer orderer = new OrdererBase(this);
//    /** Return the {@link Orderer} for this {@link VParent} */
//    public Orderer orderer() { return orderer; }
    
    /* Strategy to build iCalendar content lines */
    protected ContentLineStrategy contentLineGenerator;
    protected void setContentLineGenerator(ContentLineStrategy contentLineGenerator)
    {
        this.contentLineGenerator = contentLineGenerator;
    }
    
    /** Strategy to copy subclass's children
     * This method MUST be overridden in subclasses */
    @Deprecated
    protected Callback<VChild, Void> copyIntoCallback()
    {
        throw new RuntimeException("Can't copy children.  copyChildCallback isn't overridden in subclass." + this.getClass());
    };

    protected List<VChild> childrenUnmodifiable(List<Method> childrenGetters)
    {
    	return Collections.unmodifiableList(childrenGetters
    		.stream()
    		.map(m -> {
				try {
					return m.invoke(this);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				return null;
			})
    		.filter(p -> p != null)
    		.flatMap(p -> 
    		{
    			if (p instanceof List)
    			{
    				return ((List<VChild>) p).stream();
    			} else
    			{
    				return Arrays.stream(new VChild[]{ (VChild) p });
    			}
    		})
    		.collect(Collectors.toList())
		);
    }
    
//    /** Copy parameters, properties, and subcomponents from source into this component,
//    * essentially making a copy of source 
//    * 
//    * Note: this method only overwrites properties found in source.  If there are properties in
//    * this component that are not present in source then those will remain unchanged.
//    * */
//    @Override
//    @Deprecated
//    public void copyChildrenFrom(VParent source)
//    {
//        source.childrenUnmodifiable().forEach((e) -> copyIntoCallback().call(e));
//    }
    
    @Override
    public void copyInto(VParent destination)
    {
        if (! destination.getClass().equals(getClass()))
        {
            throw new IllegalArgumentException("Property class types must be equal (" + getClass().getSimpleName() +
                    "," + destination.getClass().getSimpleName() + ")");
        }
        // Note: copy functionality in subclasses
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
        
        Collection<VChild> c1 = childrenUnmodifiable();
        Collection<VChild> c2 = testObj.childrenUnmodifiable();
        if (c1.size() == c2.size())
        {
            Iterator<VChild> i1 = childrenUnmodifiable().iterator();
            Iterator<VChild> i2 = testObj.childrenUnmodifiable().iterator();
            for (int i=0; i<c1.size(); i++)
            {
                VChild child1 = i1.next();
                VChild child2 = i2.next();
                if (! child1.equals(child2))
                {
                    return false;
                }
            }
        } else
        {
            return false;
        }
        return true;
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
