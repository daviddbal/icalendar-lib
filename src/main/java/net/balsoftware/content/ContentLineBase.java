package net.balsoftware.content;

import net.balsoftware.utilities.Orderer;

public abstract class ContentLineBase implements ContentLineStrategy
{
    private Orderer orderer;
    Orderer orderer() { return orderer; }

    public ContentLineBase() { }
    
    public ContentLineBase(Orderer orderer)
    {
        this.orderer = orderer;
    }
}
