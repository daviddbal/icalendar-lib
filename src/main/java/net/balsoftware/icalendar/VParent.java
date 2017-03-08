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
    
//    /**
//     * Copy this {@link VElement} into destination {@link VElement}
//     */
//    void copyChildren(VParent destination);
    
    void addChild(VChild child);
    
    /**
     * Remove child from parent.
     * 
     * @param child
     * @return true is success, false if failure
     */
    boolean removeChild(VChild child);
    
	/** Add the child to the end of the ordered list 
	 * Should only be used for list-based children that are added by accessing the
	 * list.  A better alternative would be to use the {@link addChild} method which
	 * automatically adds a child and orders it.  
	 * */
	void orderChild(VChild child);
	
	/** Insert the child at the index in the ordered list */
	void orderChild(int index, VChild child);

	/** Replace the oldChild with the newChild in the ordered list */
	void orderChild(VChild oldChild, VChild newChild);
}
