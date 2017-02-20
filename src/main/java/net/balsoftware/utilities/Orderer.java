package net.balsoftware.utilities;

import java.util.List;

import net.balsoftware.VChild;
import net.balsoftware.VParent;

/** Maintains a sort order of {@link VChild} elements of a {@link VParent} */
public interface Orderer
{
	/**
	 * 
	 * @return
	 */
	List<VChild> childrenUnmodifiable();
	
	void addChild(VChild addedChild);
}
