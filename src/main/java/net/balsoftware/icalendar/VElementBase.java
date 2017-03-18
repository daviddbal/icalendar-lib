package net.balsoftware.icalendar;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.balsoftware.icalendar.properties.PropertyElement;
import net.balsoftware.icalendar.properties.VProperty;
import net.balsoftware.icalendar.utilities.Pair;

public abstract class VElementBase implements VElement
{
	protected static final String BEGIN = "BEGIN:";
	protected static final String END = "END:";

    @Override
    public String name()
    {
    	if (name == null)
    	{
    		return Element.fromClass(getClass()).toString();
    	}
        return name;
    }
    private String name;
    
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
	
	// All no-arg constructors made from calendar element enums
	private static final  Map<Pair<Class<? extends VElement>, String>, Constructor<? extends VElement>> NO_ARG_CONSTRUCTORS = makeNoArgConstructorMap();
    private static Map<Pair<Class<? extends VElement>, String>, Constructor<? extends VElement>> makeNoArgConstructorMap()
    {
    	Map<Pair<Class<? extends VElement>, String>, Constructor<? extends VElement>> map = new HashMap<>();

    	// Add VProperty elements
    	PropertyElement[] values1 = PropertyElement.values();
    	Arrays.stream(values1)
    		.forEach(v ->
	    	{
	    		Pair<Class<? extends VElement>, String> key = new Pair<>(VProperty.class, v.toString());
				try {
//					System.out.println(v.name);
					Constructor<? extends VElement> constructor = v.elementClass().getConstructor();
					map.put(key, constructor);
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
	    	});

        return map;
    }

	public static VChild newEmptyVElement(Class<? extends VElement> superclass, String name)
	{
		try {
//			NO_ARG_CONSTRUCTORS.entrySet().forEach(System.out::println);
//			System.out.println("get constructor:" + superclass.getSimpleName() + " " + name);
			String name2 = (name.startsWith("X-")) ? "X-" : name;
			Constructor<? extends VElement> constructor = NO_ARG_CONSTRUCTORS.get(new Pair<>(superclass, name2));
			if (constructor == null) return null;
			return (VChild) constructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
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
