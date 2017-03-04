package net.balsoftware.icalendar;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class VElementBase implements VElement
{
	static final  Map<VElement, Method> SETTERS = new HashMap<>();
	static final  Map<VElement, Method> GETTERS = new HashMap<>();
	
	// TODO - Can I add setters and getters to the map as elements are added?

}
