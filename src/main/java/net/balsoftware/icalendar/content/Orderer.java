package net.balsoftware.icalendar.content;

import java.util.List;

import net.balsoftware.icalendar.VChild;
import net.balsoftware.icalendar.VParent;

/** Maintains a sort order of {@link VChild} elements of a {@link VParent}
*
*  Individual children are added automatically, list-based children are added through calling
*  {@link #addChild(VChild) addChild} method
*  */
public interface Orderer
{
	/**
	 * List of children in proper order
	 * 
	 * Orphaned children are automatically removed
	 * Non-ordered children are included
	 */
	List<VChild> childrenUnmodifiable();

	/**
	 * List of children in proper order
	 * 
	 * Only includes ordered children.  No orphan removal occurs.  No inclusion of non-ordered children.
	 * About 10x faster than the complete version.
	 */
//	List<VChild> childrenUnmodifiableFast();
	
	/** Add the next child to the list */
	void orderChild(VChild newChild);

	/** Add the next child to the list */
	void orderChild(int index, VChild newChild);

//	/** Remove child from ordered list.  Returns former index of child */
//	int removeChild(VChild child);

	/** Replace oldChild with newChild in ordered list 
	 * @return true if success, false if failure
	 * */
	boolean replaceChild(VChild oldChild, VChild newChild);
}
