package net.balsoftware.icalendar.itip;

import net.balsoftware.icalendar.properties.calendar.Method.MethodType;

/**
 * Abstract class for iCalendar Transport-Independent Interoperability Protocol (iTIP) RFC 5546
 * 
 * @author David Bal
 *
 */
public abstract class AbstractITIPFactory
{
    public abstract Processable getITIPMessageProcess(MethodType methodType);
}
