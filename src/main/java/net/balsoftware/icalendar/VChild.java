package net.balsoftware.icalendar;

import net.balsoftware.icalendar.VChild;
import net.balsoftware.icalendar.VElement;
import net.balsoftware.icalendar.VParent;

/**
 * A child calendar component that has a {@link VParent}
 * 
 * @author David Bal
 *
 */
public interface VChild extends VElement
{
    /** Gets the {@link VParent} of this {@link VChild} */
    VParent getParent();
    /** Set the {@link VParent}  of this {@link VChild}.  This method is invoked internally by the API.
     * Under normal circumstances it should not be used externally */
    void setParent(VParent parent);
    
    
}
