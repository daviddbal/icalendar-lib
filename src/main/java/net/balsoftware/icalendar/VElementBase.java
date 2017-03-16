package net.balsoftware.icalendar;

import java.util.List;
import java.util.stream.Collectors;

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
	abstract protected List<Message> parseContent(String content);
	
	protected static void throwMessageExceptions(List<Message> messages) throws IllegalArgumentException
	{
    String error = messages
        	.stream()
        	.filter(m -> m.effect == MessageEffect.THROW_EXCEPTION)
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
    protected static <T extends VElementBase> T parse(T parameter, String valueContent)
    {
        List<Message> messages = parameter.parseContent(valueContent);
        throwMessageExceptions(messages);
        return parameter;
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
