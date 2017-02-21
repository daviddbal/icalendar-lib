package net.balsoftware.properties;

import org.junit.experimental.categories.Categories;

import net.balsoftware.parameters.Language;
import net.balsoftware.parameters.ParameterType;
//import net.balsoftware.properties.component.descriptive.Categories;
//import net.balsoftware.properties.component.timezone.TimeZoneName;

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
    	this.language = language;
    	orderer.addChild(language);
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
