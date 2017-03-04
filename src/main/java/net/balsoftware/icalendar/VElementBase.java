package net.balsoftware.icalendar;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class VElementBase implements VElement
{
	public static final  Map<Class<?>, Method> SETTERS = new HashMap<>();
	public static final  Map<Class<?>, Method> GETTERS = new HashMap<>();
	
	// TODO - Can I add setters and getters to the map as elements are added?

}
