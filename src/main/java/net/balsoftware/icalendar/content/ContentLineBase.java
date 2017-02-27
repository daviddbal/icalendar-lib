package net.balsoftware.icalendar.content;

public abstract class ContentLineBase implements ContentLineStrategy
{
    final Orderer orderer;
    
    public ContentLineBase(Orderer orderer)
    {
        this.orderer = orderer;
    }
}
