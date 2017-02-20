package net.balsoftware.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import net.balsoftware.VCalendar;
import net.balsoftware.VChild;
import net.balsoftware.VParent;
import net.balsoftware.components.VComponentBase;
import net.balsoftware.properties.PropertyBase;
//import net.balsoftware.properties.component.recurrence.rrule.RecurrenceRuleValue;

/** Maintains a sort order of {@link VChild} elements of a {@link VParent}
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
    
    private List<VChild> children = new ArrayList<>();

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
		// TODO - need list of all children - not just recorded ones
		// need to put list-based ones that are not recorded with the ones that are recorded
		parent.childrenUnmodifiable();
		
		return Collections.unmodifiableList(children);
	}

	@Override
	public void addChild(VChild addedChild)
	{
		if (! children.contains(addedChild))
		{
			children.add(addedChild);
		};
	}
    

}
