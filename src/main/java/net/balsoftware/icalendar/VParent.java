package net.balsoftware.icalendar;

import java.util.List;

/**
 * <p>Parent calendar components.  Parent components can have children.</p>
 * 
 * <p>Note: Adding children is not exposed, but rather handled internally when a calendar 
 * element is set or changed.</p>
 * 
 * @author David Bal
 */
public interface VParent extends VElement
{
    /** 
     * <p>Returns unmodifiable list of {@link VChild} elements.</p>
     * 
     * @return  unmodifiable list of children
     */
    List<VChild> childrenUnmodifiable();
    
    void addChild(VChild child);
    
	/** Add the next child to the list */
    @Deprecated // replace with addChild
	void orderChild(VChild child);
	
	/** Add the child to particular index in the list */
	void orderChild(int index, VChild child);
}
