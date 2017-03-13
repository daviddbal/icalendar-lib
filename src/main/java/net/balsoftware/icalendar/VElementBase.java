package net.balsoftware.icalendar;

import java.util.List;
import java.util.Map;

public abstract class VElementBase implements VElement
{
//	// TODO - MAKE PROTECTED
//    @Override
    /** Parse content line into calendar element.
     * If element contains children {@link #parseContent(String)} is invoked recursively to parse child elements also
     * 
     * @param content  calendar content string to parse
     * @return  log of information and error messages
     * @throws IllegalArgumentException  if calendar content is not valid, such as null
     */
	abstract protected Map<VElement, List<String>> parseContent(String content);
}
