package net.balsoftware.icalendar;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class VElementBase implements VElement
{
	/* Setter and getter maps
	 * The first key is the VParent class
	 * The second key is the VChild of that VParent
	 */
	protected static final  Map<Class<? extends VParent>, Map<Class<? extends VChild>, Method>> SETTERS = new HashMap<>();
	protected static final  Map<Class<? extends VParent>, Map<Class<? extends VChild>, Method>> GETTERS = new HashMap<>();

	// TODO - Can I add setters and getters to the map as elements are added?

}
