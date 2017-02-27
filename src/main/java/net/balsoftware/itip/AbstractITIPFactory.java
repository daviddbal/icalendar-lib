package net.balsoftware.itip;

import net.balsoftware.properties.calendar.Method.MethodType;

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
