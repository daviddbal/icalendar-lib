package net.balsoftware.icalendar.content;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.balsoftware.icalendar.VCalendar;
import net.balsoftware.icalendar.VChild;
import net.balsoftware.icalendar.VParent;
import net.balsoftware.icalendar.components.VComponentBase;
import net.balsoftware.icalendar.properties.PropertyBase;
import net.balsoftware.icalendar.properties.component.recurrence.rrule.RecurrenceRuleValue;


/**
 *  Maintains a sort order of {@link VChild} elements of a {@link VParent}
 *
 *  Individual children are added automatically, list-based children are added through calling
 *  {@link #addChild(VChild) addChild} method.
 * 
 * @see VParent
 * @see VCalendar
 * @see VComponentBase
 * @see PropertyBase
 * @see RecurrenceRuleValue
 *  */ 
public class OrdererBase implements Orderer
{
    final private VParent parent;
    final private Map<Class<? extends VChild>, Method> childGetters;
    
    private List<VChild> orderedChildren = new ArrayList<>();

    /*
     * CONSTRUCTOR
     */
    /** Create an {@link OrdererBase} for the {@link VParent} parameter */
    public OrdererBase(VParent aParent, Map<Class<? extends VChild>, Method> map)
    {
        this.parent = aParent;
        this.childGetters = map;
    }

	@Override
	public List<VChild> childrenUnmodifiable()
	{
//		System.out.println("run childrenUnmodifiable");
//		childGetters.forEach(System.out::println);
//		System.out.println("childUN:" + unorderedChildren(parent, childGetters).size() + " " + orderedChildren.size());

		// Remove orphans
		List<VChild> allUnorderedChildren = allUnorderedChildren(parent, childGetters);
//		System.out.println("allUnorderedChildren:" + allUnorderedChildren.size() + " " + orderedChildren.size());
		List<VChild> orphans = orderedChildren
			.stream()
			.filter(c -> ! allUnorderedChildren.contains(c))
			.collect(Collectors.toList());
//		System.out.println("orphans:" + orphans.size());
		orphans.forEach(c -> orderedChildren.remove(c));
		List<VChild> allChildren = new ArrayList<>(orderedChildren);

//		// Add unordered children
//		allUnorderedChildren
//				.stream()
//				.filter(c -> ! orderedChildren.contains(c))
//				.forEach(unorderedChild -> 
//				{
//					Class<? extends VChild> clazz = unorderedChild.getClass();
//					List<VChild> matchedChildren = allChildren.stream()
//						.filter(c2 -> c2.getClass().equals(clazz))
//						.collect(Collectors.toList());
//					if (! matchedChildren.isEmpty())
//					{
//						VChild lastMatchedChild = matchedChildren.get(matchedChildren.size()-1);
//						int index = allChildren.indexOf(lastMatchedChild)+1;
//						allChildren.add(index, unorderedChild); // put after last matched child
//					} else
//					{
//						allChildren.add(unorderedChild); // no match, put at end
//					}
//				});

		return allChildren;
	}
	
    private List<VChild> allUnorderedChildren(VParent parent, Map<Class<? extends VChild>, Method> childGetters2)
    {
    	return Collections.unmodifiableList(childGetters2
			.entrySet()
    		.stream()
    		.map(e -> e.getValue())
//    		.peek(m -> System.out.println(m.getName()))
//    		.filter(m -> m.getName().equals("getVAlarms"))
    		.map(m -> {
				try {
					return m.invoke(parent);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				return null;
			})
    		.filter(p -> p != null)
//    		.peek(System.out::println)
    		.flatMap(p -> 
    		{
    			if (Collection.class.isAssignableFrom(p.getClass()))
    			{
    				return ((Collection<VChild>) p).stream();
    			} else
    			{
    				return Arrays.stream(new VChild[]{ (VChild) p });
    			}
    		})
    		.collect(Collectors.toList())
		);
    }

	@Override
	public void orderChild(VChild newChild)
	{
//		System.out.println("adding:" + newChild + "  " + System.identityHashCode(newChild) + " " + orderedChildren.contains(newChild));
		if ((! orderedChildren.contains(newChild)) && (newChild != null))
		{
			List<VChild> allUnorderedChildren = allUnorderedChildren(parent, childGetters);
			List<VChild> orphans = orderedChildren
					.stream()
					.filter(c -> c.getClass().equals(newChild.getClass()))
					.filter(c -> ! allUnorderedChildren.contains(c))
					.collect(Collectors.toList());
			if (! orphans.isEmpty())
			{ // replace orphan at same index location
				int index = orderedChildren.indexOf(orphans.get(0));
				orphans.forEach(c -> orderedChildren.remove(c));
				orderedChildren.add(index, newChild);				
			} else
			{
				orderedChildren.add(newChild);
			}
			newChild.setParent(parent);
		}
	}
	
	/* Remove orphans matching newChild's class type */
	private void removeOrphans(VChild newChild)
	{
		List<VChild> allUnorderedChildren = allUnorderedChildren(parent, childGetters);
		List<VChild> orphans = orderedChildren
				.stream()
				.filter(c -> c.getClass().equals(newChild.getClass()))
				.filter(c -> ! allUnorderedChildren.contains(c))
				.collect(Collectors.toList());
		orphans.forEach(c -> orderedChildren.remove(c));
	}

//	@Override
//	public void orderChild(VChild oldChild, VChild newChild)
//	{
////		System.out.println("adding:" + newChild);
//		if (oldChild == null)
//		{
//			if (! orderedChildren.contains(newChild))
//			{
//				orderedChildren.add(newChild);
//				newChild.setParent(parent);
//			}
//		} else
//		{
//			int index = orderedChildren.indexOf(oldChild);
//			orderedChildren.remove(oldChild);
//			if (newChild != null)
//			{
//				if (index >= 0)
//				{
//					orderedChildren.add(index, newChild);
//				} else
//				{
//					orderedChildren.add(newChild);				
//				}
//			}
//		}
//	}

	@Override
	public void orderChild(int index, VChild newChild)
	{
		if (newChild != null)
		{
			if (orderedChildren.contains(newChild))
			{
				orderedChildren.remove(newChild);
			}
			removeOrphans(newChild);
			orderedChildren.add(index, newChild);
			newChild.setParent(parent);
		}
	}
}
