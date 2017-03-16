package net.balsoftware.icalendar.parameters;

import net.balsoftware.icalendar.utilities.StringConverter;
import net.balsoftware.icalendar.utilities.StringConverters;

/**
 * LANGUAGE
 * Language
 * RFC 5545, 3.2.10, page 21
 * 
 * To specify the language for text values in a property or property parameter.
 * 
 * Example:
 * SUMMARY;LANGUAGE=en-US:Company Holiday Party
 * 
 * @author David Bal
 *
 */
public class Language extends VParameterBase<Language, String>
{
	private static final StringConverter<String> CONVERTER = StringConverters.defaultStringConverterWithQuotes();

    public Language()
    {
        super(CONVERTER);
    }

    public Language(Language source)
    {
        super(source, CONVERTER);
    }
    
    public static Language parse(String content)
    {
    	return Language.parse(new Language(), content);
    }
}
