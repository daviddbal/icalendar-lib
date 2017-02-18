package net.balsoftware.properties;

import net.balsoftware.parameters.FreeBusyType;

public interface PropFreeBusy<T> extends Property<T>
{
    /**
     * FBTYPE: Incline Free/Busy Time Type
     * RFC 5545, 3.2.9, page 20
     * 
     * To specify the free or busy time type.
     * 
     * Values can be = "FBTYPE" "=" ("FREE" / "BUSY" / "BUSY-UNAVAILABLE" / "BUSY-TENTATIVE"
     */
    FreeBusyType getFreeBusyType();
    void setFreeBusyType(FreeBusyType freeBusyType);
}
