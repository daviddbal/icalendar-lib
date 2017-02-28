package net.balsoftware.icalendar.components;

import java.util.List;

import net.balsoftware.icalendar.properties.component.descriptive.Attachment;
import net.balsoftware.icalendar.properties.component.descriptive.Summary;

/**
 * <p>{@link VComponent} with the following properties
 * <ul>
 * <li>{@link Attachment ATTACH}
 * <li>{@link Summary SUMMARY}
 * </ul>
 * </p>
 * 
 * @author David Bal
 *
 * @param <T> - concrete subclass
 * 
 */
public abstract class VDescribableBase<T> extends VCommon<T> implements VDescribable<T>
{
    /**
     * This property provides the capability to associate a document object with a calendar component.
     * 
     *<p>Example:  The following is an example of this property:
     *<ul>
     *<li>ATTACH:CID:jsmith.part3.960817T083000.xyzMail@example.com
     *<li>ATTACH;FMTTYPE=application/postscript:ftp://example.com/pub/<br>
     *  reports/r-960812.ps
     *</ul>
     *</p>
     */
    @Override
    public List<Attachment<?>> getAttachments() { return attachments; }
    private List<Attachment<?>> attachments;
    @Override
    public void setAttachments(List<Attachment<?>> attachments)
    {
    	this.attachments = attachments;
    	if (attachments != null)
    	{
    		attachments.forEach(c -> c.orderChild(c));
    	}
	}
    
    /**
     *<p>This property defines a short summary or subject for the calendar component</p>
     * 
     *<p>Example:  The following is an example of this property:
     *<ul>
     *<li>SUMMARY:Department Party
     *</ul>
     *</p>
     */
    @Override
    public Summary getSummary() { return summary; }
    private Summary summary;
    @Override
	public void setSummary(Summary summary)
    {
    	this.summary = summary;
    	orderChild(summary);
	}
    
    /*
     * CONSTRUCTORS
     */
    VDescribableBase()
    {
        super();
    }

    VDescribableBase(VDescribableBase<T> source)
    {
        super(source);
    }
}
