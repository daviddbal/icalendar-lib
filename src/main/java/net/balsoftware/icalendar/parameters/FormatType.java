package net.balsoftware.icalendar.parameters;

import net.balsoftware.icalendar.properties.component.descriptive.Attachment;

/**
 * Format Type
 * FMTYPE
 * RFC 5545 iCalendar 3.2.8 page 19
 * 
 * To specify the content type of a referenced object.
 * 
 *  Example:
 *  ATTACH;FMTTYPE=application/msword:ftp://example.com/pub/docs/
 *   agenda.doc
 *   
 *   @see Attachment
 */
public class FormatType extends ParameterBase<FormatType, String>
{
    public String getTypeName() { return typeName; }
    private String typeName;
    public void setTypeName(String typeName)
    {
    	this.typeName = typeName;
    	buildNewValue();
	}

    public String getSubtypeName() { return subtypeName; }
    private String subtypeName;
    public void setSubtypeName(String subtypeName)
    {
    	this.subtypeName = subtypeName;
    	buildNewValue();
	}

    // capture type and subtype names
    @Override
    public void setValue(String value)
    {
        int slashIndex = value.indexOf('/');
        if (slashIndex > 0)
        {
            setTypeName(value.substring(0, slashIndex));
            setSubtypeName(value.substring(slashIndex+1));
            super.setValue(value);            
        } else
        {
            throw new IllegalArgumentException(getClass().getSimpleName() + " requires both type-name / subtype-name as defined in RFC4288");
        }
    }
    
	private void buildNewValue()
    {
		if ((getTypeName() != null) && (getSubtypeName() != null))
		{
			String newValue = getTypeName() + "/" + getSubtypeName();
			super.setValue(newValue);
		}
    }

    /*
     * CONSTRUCTORS
     */  
    public FormatType()
    {
        super();
    }

    public FormatType(FormatType source)
    {
        super(source);
    }
    
    public static FormatType parse(String content)
    {
        FormatType parameter = new FormatType();
        parameter.parseContent(content);
        return parameter;
    }
}
