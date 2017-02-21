package net.balsoftware.content;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.balsoftware.VCalendar;
//import net.balsoftware.VCalendar;
import net.balsoftware.VChild;
import net.balsoftware.VParent;
import net.balsoftware.components.VComponentBase;
import net.balsoftware.properties.PropertyBase;
//import net.balsoftware.properties.component.recurrence.rrule.RecurrenceRuleValue;


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
    
    private List<VChild> orderedChildren = new ArrayList<>();

    /*
     * CONSTRUCTOR
     */
    /** Create an {@link OrdererBase} for the {@link VParent} parameter */
    public OrdererBase(VParent aParent)
    {
        this.parent = aParent;
    }

	@Override
	public List<VChild> childrenUnmodifiable()
	{
		List<VChild> allChildren = new ArrayList<>(orderedChildren);
		parent.childrenUnmodifiable()
				.stream()
				.filter(c -> ! orderedChildren.contains(c))
				.forEach(unorderedChild -> 
				{
					Class<? extends VChild> clazz = unorderedChild.getClass();
					List<VChild> matchedChildren = allChildren.stream()
						.filter(c2 -> c2.getClass().equals(clazz))
						.collect(Collectors.toList());
					if (! matchedChildren.isEmpty())
					{
						VChild lastMatchedChild = matchedChildren.get(matchedChildren.size()-1);
						int index = allChildren.indexOf(lastMatchedChild);
						allChildren.add(index, unorderedChild); // put after last matched child
					} else
					{
						allChildren.add(unorderedChild); // no match, put at end
					}
				});
		return allChildren;
	}

	@Override
	public void addChild(VChild addedChild)
	{
		if (! orderedChildren.contains(addedChild))
		{
			orderedChildren.add(addedChild);
		};
	}

	@Override
	public void addChild(int index, VChild addedChild)
	{
		orderedChildren.add(index, addedChild);
	}
    

}
