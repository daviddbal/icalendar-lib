package net.balsoftware.icalendar.content;

import net.balsoftware.icalendar.VElement;

/** Interface for delegated content line generators */
public interface ContentLineStrategy
{
    /** Produce output for {@link VElement#toString()} */
    String execute();
}
