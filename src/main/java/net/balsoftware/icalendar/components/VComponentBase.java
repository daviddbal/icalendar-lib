package net.balsoftware.icalendar.components;

import net.balsoftware.icalendar.CalendarComponent;
import net.balsoftware.icalendar.VParent;
import net.balsoftware.icalendar.VParentBase;
import net.balsoftware.icalendar.content.MultiLineContent;;

/**
 * <p>Base class implementation of a {@link VComponent}</p>
 * 
 * @author David Bal
 */
public abstract class VComponentBase<T> extends VParentBase<T> implements VComponent
{
    private static final String FIRST_LINE_PREFIX = "BEGIN:";
    private static final String LAST_LINE_PREFIX = "END:";
    
    protected VParent parent;
    @Override public void setParent(VParent parent) { this.parent = parent; }
    @Override public VParent getParent() { return parent; }
    
    final private CalendarComponent componentType;
    @Override
    public String name() { return componentType.toString(); }

    /*
     * CONSTRUCTORS
     */
    /**
     * Create default component by setting {@link componentName}, and setting content line generator.
     */
    VComponentBase()
    {
    	super();
    	componentType = CalendarComponent.enumFromClass(this.getClass());
        contentLineGenerator = new MultiLineContent(
                orderer,
                FIRST_LINE_PREFIX + name(),
                LAST_LINE_PREFIX + name(),
                400);
    }
    
    /**
     * Creates a deep copy of a component
     */
    VComponentBase(VComponentBase<T> source)
    {
    	super(source);
    	componentType = CalendarComponent.enumFromClass(this.getClass());
        contentLineGenerator = new MultiLineContent(
                orderer,
                FIRST_LINE_PREFIX + name(),
                LAST_LINE_PREFIX + name(),
                400);
    }

//    @Override
//    protected Map<VElement, List<String>> parseContent(String content)
//    {
//        return parseContent(content, false);
//    }
//    
//    public Map<VElement, List<String>> parseContent(String content, boolean useRequestStatus)
//    {
//        if (content == null)
//        {
//            throw new IllegalArgumentException("Calendar component content string can't be null");
//        }
//        content.indexOf(System.lineSeparator());
//        List<String> contentLines = Arrays.asList(content.split(System.lineSeparator()));
//        UnfoldingStringIterator unfoldedLineIterator = new UnfoldingStringIterator(contentLines.iterator());
//        return parseContent(unfoldedLineIterator, useRequestStatus);
//    }
//
////    @Override
//    public Map<VElement, List<String>> parseContent(Iterator<String> unfoldedLineIterator, boolean useRequestStatus)
//    {
//        if (unfoldedLineIterator == null)
//        {
//            throw new IllegalArgumentException("Calendar component content iterator can't be null");
//        }
//        Map<VElement, List<String>> messageMap = new HashMap<>();
//        List<String> myMessages = new ArrayList<>();
//        while (unfoldedLineIterator.hasNext())
//        {
//            String unfoldedLine = unfoldedLineIterator.next();
////            System.out.println("unfoldedLine:" + line++ + unfoldedLine + " " + unfoldedLineIterator.hasNext());
//            int nameEndIndex = ICalendarUtilities.getPropertyNameIndex(unfoldedLine);
//            String propertyName = (nameEndIndex > 0) ? unfoldedLine.substring(0, nameEndIndex) : "";
////            System.out.println(propertyName);
//            // Parse subcomponent
//            if (propertyName.equals("BEGIN"))
//            {
//                boolean isMainComponent = unfoldedLine.substring(nameEndIndex+1).equals(name());
//                if  (! isMainComponent)
//                {
//                    String subcomponentName = unfoldedLine.substring(nameEndIndex+1);
////                    VComponent subcomponent = SimpleVElementFactory.newElement(unfoldedLineIterator);
//                    // TODO - TRY TO REPLACE WITH SOMETHING BETTER - A METHOD IN VELEMENT OR VPARENT?
//                    VComponentBase subcomponent = (VComponentBase) SimpleVComponentFactory.emptyVComponent(subcomponentName);
//                    Map<VElement, List<String>> subMessages = subcomponent.parseContent(unfoldedLineIterator, useRequestStatus);
//                    messageMap.putAll(subMessages);
//                    addSubcomponent(subcomponent);
//                    orderChild(subcomponent);
//                }
//            } else if (propertyName.equals("END"))
//            {
//                break; // exit when end found
//            } else
//                // TODO - GENERATE RFC 5546 REQUEST STATUS MESSAGES
//            {  // parse properties - ignore unknown properties
//                PropertyType propertyType = PropertyType.enumFromName(propertyName);
//                if (propertyType != null)
//                {
//                    Object existingProperty = propertyType.getProperty(this);
//                    if (existingProperty == null || existingProperty instanceof List)
//                    {
//                            try
//                            {
////                            	ElementType elementType = ElementType.getEnum(this, propertyName);
////                            	ElementType.parse(this, unfoldedLine);
////                            	Class<? extends Property> clazz = propertyType.getPropertyClass();
////                            	Property n = clazz.getConstructor().newInstance();
////                            	n.
//                                propertyType.parse(this, unfoldedLine);
//                            } catch (Exception e)
//                            {
//                                if (propertyType.isRequired(this))
//                                {
//                                    myMessages.add("3.2;Invalid property value;" + unfoldedLine);
//                                } else
//                                {
//                                    myMessages.add("2.2;Success; invalid property ignored;" + unfoldedLine);
//                                }
//                            }
//                    } else
//                    {
//                        myMessages.add("2.2;Success; invalid property ignored.  Property can only occur once in a calendar component.  Subsequent property is ignored;" + unfoldedLine);
//                    }
//                } else
//                {
//                    myMessages.add("2.4;Success; unknown, non-standard property ignored.;" + unfoldedLine);
//                }
//            }
//        }
//        if (myMessages.isEmpty())
//        {
//            myMessages.add("2.0;Success");
//        }
////        System.out.println(System.identityHashCode(this) + " " + this.name() + " " + myMessages);
//        messageMap.put(this, myMessages);
//        // TODO - Log status messages if not using RequestStatus
//        return messageMap;
//    }
   
    /**
     * Hook to add subcomponent such as {@link #VAlarm}, {@link #StandardTime} and {@link #DaylightSavingTime}
     * 
     * @param subcomponent
     */
    void addSubcomponent(VComponent subcomponent)
    { // no opp by default
    }
}
