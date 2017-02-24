package net.balsoftware.properties.calendar;

import net.balsoftware.VCalendar;
import net.balsoftware.VElement;
import net.balsoftware.properties.PropertyBase;

/**
 * PRODID
 * Product Identifier
 * RFC 5545, 3.7.3, page 78
 * 
 * This property specifies the identifier for the product that created the iCalendar object.
 * 
 * The vendor of the implementation SHOULD assure that
 * this is a globally unique identifier; using some technique such as
 * an FPI value, as defined in [ISO.9070.1991]
 * 
 * Example:
 * PRODID:-//ABC Corporation//NONSGML My Product//EN
 * 
 * @author David Bal
 * @see VCalendar
 */
public class ProductIdentifier extends PropertyBase<String, ProductIdentifier> implements VElement
{
    public ProductIdentifier(ProductIdentifier source)
    {
        super(source);
    }
    
    public ProductIdentifier(String productIdentifier)
    {
        super(productIdentifier);
    }

    public ProductIdentifier()
    {
        super();
    }
    
    public static ProductIdentifier parse(String string)
    {
        ProductIdentifier property = new ProductIdentifier();
        property.parseContent(string);
        return property;
    }
}
