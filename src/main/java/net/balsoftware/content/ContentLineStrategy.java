package net.balsoftware.content;

import net.balsoftware.VElement;

/** Interface for delegated content line generators */
public interface ContentLineStrategy
{
    /** Produce output for {@link VElement#toString()} */
    String execute();
}
