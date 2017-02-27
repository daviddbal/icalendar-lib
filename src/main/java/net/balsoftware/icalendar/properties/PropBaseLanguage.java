package net.balsoftware.icalendar.properties;

import org.junit.experimental.categories.Categories;

import net.balsoftware.icalendar.parameters.Language;
import net.balsoftware.icalendar.parameters.ParameterType;
import net.balsoftware.icalendar.properties.component.timezone.TimeZoneName;

/**
 * Property with language and a text-based value
 *  
 * concrete subclasses
 * @see Categories
 * @see TimeZoneName
 * 
 * @author David Bal
 *
 * @param <U> - type of implementing subclass
 * @param <T> - type of property value
 */
public abstract class PropBaseLanguage<T,U> extends PropertyBase<T,U> implements PropLanguage<T>
{
    /**
     * LANGUAGE
     * To specify the language for text values in a property or property parameter.
     * 
     * Examples:
     * SUMMARY;LANGUAGE=en-US:Company Holiday Party
     * LOCATION;LANGUAGE=no:Tyskland
     */
    @Override
    public Language getLanguage() { return language; }
    private Language language;
    @Override
    public void setLanguage(Language language)
    {
    	orderChild(language);
    	this.language = language;
	}
    public void setLanguage(String value) { setLanguage(Language.parse(value)); }
    public U withLanguage(Language language) { setLanguage(language); return (U) this; }
    public U withLanguage(String content) { ParameterType.LANGUAGE.parse(this, content); return (U) this; }    
    
    /*
     * CONSTRUCTORS
     */    
   
    // copy constructor
    public PropBaseLanguage(PropBaseLanguage<T,U> property)
    {
        super(property);
    }
    
    public PropBaseLanguage(T value)
    {
        super(value);
    }
    
    protected PropBaseLanguage()
    {
        super();
    }
}
