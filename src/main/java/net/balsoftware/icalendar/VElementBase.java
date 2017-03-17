package net.balsoftware.icalendar;

import java.util.List;
import java.util.stream.Collectors;

public abstract class VElementBase implements VElement
{
	protected static final String BEGIN = "BEGIN:";
	protected static final String END = "END:";

//	// TODO - MAKE PROTECTED
//    @Override
    /** Parse content line into calendar element.
     * If element contains children {@link #parseContent(String)} is invoked recursively to parse child elements also
     * 
     * @param content  calendar content string to parse
     * @return  log of information and error messages
     * @throws IllegalArgumentException  if calendar content is not valid, such as null
     */
	abstract protected List<Message> parseContent(String content);
	
	protected static void throwMessageExceptions(List<Message> messages, VElement element) throws IllegalArgumentException
	{
		// keep messages that are labeled as exceptions or produced by parsing itself (not children)
		// now throw all messages as errors
		String error = messages
        	.stream()
        	.filter(m -> ! m.message.startsWith("Unknown"))
//        	.filter(m -> (m.effect == MessageEffect.THROW_EXCEPTION) || (m.element == element))
        	.map(m -> m.element.name() + ":" + m.message)
        	.collect(Collectors.joining(System.lineSeparator()));
        if (! error.isEmpty())
        {
        	throw new IllegalArgumentException(error);
        }
	}

	
    /**
     * Creates a new VElement by parsing a String of iCalendar content text
     * @param <T>
     *
     * @param content  the text to parse, not null
     * @return  the parsed DaylightSavingTime
     */
    protected static <T extends VElementBase> T parse(T element, String valueContent)
    {
    	boolean isContentValid = element.isContentValid(valueContent);
    	if (! isContentValid)
		{
    		throw new IllegalArgumentException("Invalid element:" + valueContent);
		}
        List<Message> messages = element.parseContent(valueContent);
        throwMessageExceptions(messages, element);
        return element;
    }
    
	protected boolean isContentValid(String valueContent)
	{
		return valueContent != null; // override in subclasses
	}

	protected static class Message
	{
		public Message(VElement element, String message, MessageEffect effect) {
			super();
			this.element = element;
			this.message = message;
			this.effect = effect;
		}
		public VElement element;
		public String message;
		public MessageEffect effect;
		
		@Override
		public String toString() {
			return "Message [element=" + element + ", message=" + message + ", effect=" + effect + "]";
		}
	}
	
	public enum MessageEffect {
		MESSAGE_ONLY, THROW_EXCEPTION
	}
}
