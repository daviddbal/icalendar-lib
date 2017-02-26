package net.balsoftware.content;

import java.util.List;

import net.balsoftware.VChild;
import net.balsoftware.VParent;

/** Maintains a sort order of {@link VChild} elements of a {@link VParent}
*
*  Individual children are added automatically, list-based children are added through calling
*  {@link #addChild(VChild) addChild} method
*  */
public interface Orderer
{
	/**
	 * List of children in proper order
	 */
	List<VChild> childrenUnmodifiable();

	/** Add the next child to the list */
	void orderChild(VChild newChild);

	/** Add the next child to the list */
	void orderChild(int index, VChild newChild);

//	/** Replace the oldChild with the newChild*/
//	void orderChild(VChild oldChild, VChild newChild);

//	/** Add the child to particular index in the list */
//	void orderChild(int index, VChild oldChild, VChild newChild);

}
