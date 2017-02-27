package net.balsoftware.icalendar.properties;

import org.junit.experimental.categories.Categories;

import net.balsoftware.icalendar.parameters.Language;
import net.balsoftware.icalendar.properties.component.timezone.TimeZoneName;

/**
 * Property with language and a text-based value
 *  
 * @param <T> - type of property value
 * 
 * @see PropBaseLanguage
 * 
 * concrete subclasses
 * @see Categories
 * @see TimeZoneName
 */
public interface PropLanguage<T> extends Property<T>
{
    /**
     * LANGUAGE
     * To specify the language for text values in a property or property parameter.
     * 
     * Examples:
     * SUMMARY;LANGUAGE=en-US:Company Holiday Party
     * LOCATION;LANGUAGE=no:Tyskland
     */

    Language getLanguage();
    void setLanguage(Language language);
}
