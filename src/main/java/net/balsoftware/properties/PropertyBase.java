package net.balsoftware.properties;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import junit.runner.Version;
import net.balsoftware.VParent;
import net.balsoftware.VParentBase;
import net.balsoftware.content.OrdererBase;
import net.balsoftware.content.SingleLineContent;
import net.balsoftware.parameters.NonStandardParameter;
import net.balsoftware.parameters.Parameter;
import net.balsoftware.parameters.ParameterType;
import net.balsoftware.parameters.ValueParameter;
import net.balsoftware.properties.calendar.CalendarScale;
import net.balsoftware.properties.calendar.ProductIdentifier;
//import net.balsoftware.properties.calendar.CalendarScale;
//import net.balsoftware.properties.calendar.Method;
//import net.balsoftware.properties.calendar.ProductIdentifier;
//import net.balsoftware.properties.calendar.Version;
import net.balsoftware.properties.component.relationship.UniqueIdentifier;
import net.balsoftware.utilities.ICalendarUtilities;
import net.balsoftware.utilities.Pair;
//import net.balsoftware.utilities.ICalendarUtilities;
import net.balsoftware.utilities.StringConverter;

/**
 * Base iCalendar property class
 * Contains property value, value parameter (ValueType) and other-parameters
 * Also contains several support methods used by other properties
 * 
 * concrete subclasses
 * @see UniqueIdentifier
 * @see CalendarScale
 * @see Method
 * @see ProductIdentifier
 * @see Version
 * 
 * @author David Bal
 *
 * @param <U> - type of implementing subclass
 * @param <T> - type of property value
 */
public abstract class PropertyBase<T,U> extends VParentBase implements Property<T>
{
    private VParent myParent;
    @Override
    public void setParent(VParent parent)
    {
    	myParent = parent;
	}
    @Override
    public VParent getParent()
    {
    	return myParent;
	}
    
    /**
     * PROPERTY VALUE
     * 
     * Example: for the property content LOCATION:The park the property
     * value is the string "The park"
     */
    @Override
    public T getValue()
    {
    	return value;
	}
    private T value; // initialized in constructor
    @Override
    public void setValue(T value)
    {
        this.value = value;
    }
    public U withValue(T value) { setValue(value); return (U) this; } // in constructor

    /** The propery's value converted by string converted to content string */
    protected String valueContent()
    {
        /* default code below works for all properties with a single value.  Properties with multiple embedded values,
         * such as RequestStatus, require an overridden method */
        return (getConverter().toString(getValue()) == null) ? getUnknownValue() : getConverter().toString(getValue());
    }

    // class of value.  If collection, returns type of element instead. 
    // Used to verify value class is allowed for the property type
    private Class<T> valueClass;
    private Class<?> getValueClass()
    {
        if (valueClass == null)
        {
            if (getValue() != null)
            {
                if (getValue() instanceof Collection)
                {
                    if (! ((Collection<?>) getValue()).isEmpty())
                    {
                        return ((Collection<?>) getValue()).iterator().next().getClass();
                    } else
                    {
                        return null;
                    }
                }
                return getValue().getClass();
            }
            return null;
        } else
        {
            return valueClass;
        }
    }
    
    /** The name of the property, such as DESCRIPTION
     * Remains the default value unless set by a non-standard property*/
    @Override
    public String name()
    {
    	if (propertyName == null)
    	{
    		return propertyType().toString();
    	}
        return propertyName;
    }
    private String propertyName;
    /** Set the name of the property.  Only allowed for non-standard and IANA properties */
    public void setPropertyName(String name)
    {
        if (propertyType().equals(PropertyType.NON_STANDARD))
        {
            if (name.substring(0, 2).toUpperCase().equals("X-"))
            {
                propertyName = name;
            } else
            {
                throw new RuntimeException("Non-standard properties must begin with X-");                
            }
        } else if (propertyType().toString().equals(name)) // let setting name to default value have no operation
        {
        	// do nothing
        } else
        {
            throw new RuntimeException("Custom property names can only be set for non-standard and IANA-registered properties (" + name + ")");
        }
    }
    public U withPropertyName(String name) { setPropertyName(name); return (U) this; }
    
    /**
     * PROPERTY TYPE
     * 
     *  The enumerated type of the property.
     *  Some essential methods are in the enumerated type.
     */
    public PropertyType propertyType()
    {
    	return propertyType;
	}
    final private PropertyType propertyType;
    
    /*
     * Unknown values
     * contains exact string for unknown property value
     */
    private String unknownValue;
    protected String getUnknownValue()
    {
    	return unknownValue;
    }
    private void setUnknownValue(String value)
    {
    	unknownValue = value;
	}
    
    /**
     * VALUE TYPE
     * Value Data Types
     * RFC 5545, 3.2.20, page 29
     * 
     * To specify the value for text values in a property or property parameter.
     * This parameter is optional for properties when the default value type is used.
     * 
     * Examples:
     * VALUE=DATE-TIME  (Date-Time is default value, so it isn't necessary to specify)
     * VALUE=DATE
     */
    @Override
    public ValueParameter getValueType() { return valueType; }
    private ValueParameter valueType;
    @Override
    public void setValueType(ValueParameter valueType)
    {
        if (valueType == null || isValueTypeValid(valueType.getValue()))
        {
        	orderer.orderChild(this.valueType, valueType);
            this.valueType = valueType;
            valueParamenterConverter(valueType); // convert new value
        } else
        {
            throw new IllegalArgumentException("Invalid Value Date Type:" + valueType.getValue() + ", allowed = " + propertyType().allowedValueTypes());
        }
    }
    public void setValueType(ValueType value)
    {
        setValueType(new ValueParameter(value));
    }
    public U withValueType(ValueType value)
    {
        setValueType(value);
        return (U) this;
    } 

    // Synch value with type produced by string converter
    private void valueParamenterConverter(ValueParameter newValueParameter)
    {
        if (! isCustomConverter())
        {
            // Convert property value string, if present
            if (getPropertyValueString() != null)
            {
                T newPropValue = getConverter().fromString(getPropertyValueString());
                this.value = newPropValue;
//                setValue(newPropValue);
            }
        }
        
        // verify value class is allowed
        if (newValueParameter != null && getValueClass() != null) // && ! newValue.getValue().allowedClasses().contains(getValueClass()))
        {
            boolean isMatch = newValueParameter.getValue().allowedClasses()
                    .stream()
                    .map(c -> getValueClass().isAssignableFrom(c))
                    .findAny()
                    .isPresent();
            if (! isMatch)
            {
                throw new IllegalArgumentException("Value class " + getValueClass().getSimpleName() +
                        " doesn't match allowed value classes: " + newValueParameter.getValue().allowedClasses());
            }
        }    	
    }
    
    /**
     * <h2>NON-STANDARD PARAMETERS</h2>
     * 
     * <p>x-param     = x-name "=" param-value *("," param-value)<br>
     ; A non-standard, experimental parameter.</p>
     */
    private List<NonStandardParameter> nonStandardParams;
    @Override
    public List<NonStandardParameter> getNonStandard()
    {
        return nonStandardParams;
    }
    @Override
    public void setNonStandard(List<NonStandardParameter> nonStandardParams)
    {
        this.nonStandardParams = nonStandardParams;
    }
    /**
     * NON-STANDARD PARAMETERS
     * 
     * Sets the value of the {@link #NonStandardParameter()} by parsing a vararg of
     * iCalendar content text representing individual {@link NonStandardParameter} objects.
     * 
     * @return - this class for chaining
     */
    public U withNonStandard(String...nonStandardParams)
    {
        List<NonStandardParameter> a = Arrays.stream(nonStandardParams)
                .map(c -> NonStandardParameter.parse(c))
                .collect(Collectors.toList());
        setNonStandard(a);
        return (U) this;
    }
    /**
     * Sets the value of the {@link #NonStandardParameter()}
     * 
     * @return - this class for chaining
     */
    public U withNonStandard(List<NonStandardParameter> nonStandardParams)
    {
        setNonStandard(nonStandardParams);
        return (U) this;
    }
    /**
     * Sets the value of the {@link #NonStandardParameter()} from a vararg of {@link NonStandardParameter} objects.
     * 
     * @return - this class for chaining
     */    
    public U withNonStandard(NonStandardParameter...nonStandardParams)
    {
        setNonStandard(new ArrayList<>(Arrays.asList(nonStandardParams)));
        return (U) this;
    }
    
    @Override
    public void copyInto(VParent destination)
    {
        super.copyInto(destination);
        PropertyBase<T,U> castDestination = (PropertyBase<T,U>) destination;
        castDestination.setConverter(getConverter());
        T valueCopy = copyValue(getValue());
        castDestination.setValue(valueCopy);
        castDestination.setPropertyName(name());
        childrenUnmodifiable().forEach((childSource) -> 
        {
            ParameterType type = ParameterType.enumFromClass(childSource.getClass());
            if (type != null)
            {
                type.copyParameter((Parameter<?>) childSource, (Property<?>) destination);
            } 
        });
    }
    
    // property value as string - kept if string converter changes the value can change
    // needed to make subsequent conversions if value type changes.
    private String propertyValueString = null;
    // Note: in subclasses additional text can be concatenated to string (e.g. ZonedDateTime classes add time zone as prefix)
    protected String getPropertyValueString()
    {
    	return propertyValueString;
	}
    
    
    /**
     * STRING CONVERTER
     * 
     * Get the property's value string converter.  There is a default converter in ValueType associated
     * with the default value type of the property.  For most value types that converter is
     * acceptable.  However, for the TEXT value type it often needs to be replaced.
     * For example, the value type for TimeZoneIdentifier is TEXT, but the Java object is
     * ZoneId.  A different converter is required to make the conversion to ZoneId.
     */ 
    protected StringConverter<T> getConverter()
    {
        if (converter == null)
        {
            ValueType valueType = (getValueType() == null) ? propertyType.allowedValueTypes().get(0) : getValueType().getValue();
            return valueType.getConverter(); // use default converter assigned to value type if no customer converter assigned
        }
        return converter;
    }
    private StringConverter<T> converter;
    protected void setConverter(StringConverter<T> converter) { this.converter = converter; }
    private boolean isCustomConverter()
    {
        return converter != null;
    }
    
    /*
     * CONSTRUCTORS
     */
    
    protected PropertyBase()
    {
        propertyType = PropertyType.enumFromClass(getClass());
        if (propertyType != PropertyType.NON_STANDARD)
        {
            setPropertyName(propertyType.toString());
        }
        orderer = new OrdererBase(this, propertyType.childGetters());
        contentLineGenerator  = new SingleLineContent(
                orderer,
                (Void) -> name(),
                50);
    }

    public PropertyBase(Class<T> valueClass, String contentLine)
    {
        this();
        this.valueClass = valueClass;
        setConverterByClass(valueClass);
        parseContent(contentLine);
    }

    // copy constructor
    public PropertyBase(PropertyBase<T,U> source)
    {
        this();
        setConverter(source.getConverter());
        source.copyInto(this);
        T valueCopy = copyValue(source.getValue());
        setValue(valueCopy);
        setPropertyName(source.name());
    }
    
    // constructor with only value parameter
    public PropertyBase(T value)
    {
        this();
        setValue(value);
    }

    // return a copy of the value
    protected T copyValue(T source)
    {
        return source; // for mutable values override in subclasses
    }
    
    // Set converter when using constructor with class parameter
    protected void setConverterByClass(Class<T> valueClass)
    {
        // do nothing - hook to override in subclass for functionality
    }
    
    /** Parse content line into calendar property */
    @Override
    public List<String> parseContent(String unfoldedContent)
    {
        // perform tests, make changes if necessary
        final String propertyValue;
        List<Integer> indices = new ArrayList<>();
        indices.add(unfoldedContent.indexOf(':'));
        indices.add(unfoldedContent.indexOf(';'));
        Optional<Integer> hasPropertyName = indices
                .stream()
                .filter(v -> v > 0)
                .min(Comparator.naturalOrder());
        if (hasPropertyName.isPresent())
        {
            int endNameIndex = hasPropertyName.get();
            String propertyName = (endNameIndex > 0) ? unfoldedContent.subSequence(0, endNameIndex).toString().toUpperCase() : null;
            boolean isMatch = propertyName.equals(propertyType.toString());
            boolean isNonStandardProperty = propertyName.substring(0, PropertyType.NON_STANDARD.toString().length()).equals(PropertyType.NON_STANDARD.toString());
            if (isMatch || isNonStandardProperty)
            {
                if (isNonStandardProperty)
                {
                    setPropertyName(unfoldedContent.substring(0,endNameIndex));
                }
                propertyValue = unfoldedContent.substring(endNameIndex, unfoldedContent.length()); // strip off property name
            } else
            {
                if (PropertyType.enumFromName(propertyName) == null)
                {
                    propertyValue = ":" + unfoldedContent; // doesn't match a known property name, assume its all a property value
                } else
                {
                    throw new IllegalArgumentException("Property name " + propertyName + " doesn't match class " +
                            getClass().getSimpleName() + ".  Property name associated with class " + 
                            getClass().getSimpleName() + " is " +  propertyType.toString());
                }
            }
        } else
        {
            propertyValue = ":" + unfoldedContent;
        }
        
        // parse parameters
        List<Pair<String, String>> list = ICalendarUtilities.contentToParameterListPair(propertyValue);
        list.stream()
            .forEach(entry ->
            {
                ParameterType parameterType = ParameterType.enumFromName(entry.getKey());
                boolean isAllowed = propertyType().allowedParameters().contains(parameterType);
                if (parameterType != null && isAllowed)
                {
                    Object existingParemeter = parameterType.getParameter(this);
                    if (existingParemeter == null || existingParemeter instanceof List)
                    {
                        parameterType.parse(this, entry.getKey() + "=" + entry.getValue());
                    } else
                    {
                        throw new IllegalArgumentException(parameterType + " can only occur once in a calendar component");
                    }
                } else if (entry.getKey() == ICalendarUtilities.PROPERTY_VALUE_KEY)
                {
                 // save property value
                    propertyValueString = entry.getValue();
                    T value = getConverter().fromString(getPropertyValueString());
                    if (value == null)
                    {
                        setUnknownValue(propertyValueString);
                    } else
                    {
                        setValue(value);
                        if (value.toString() == "UNKNOWN") // enum name indicating unknown value
                        {
                            setUnknownValue(propertyValueString);
                        }
                    }
                }
            });
        
        if (! isValid())
        {
            throw new IllegalArgumentException("Error in parsing " + propertyType().toString() + " content line:" + System.lineSeparator()
                    + errors().stream().collect(Collectors.joining(System.lineSeparator())));
        }
        return errors();
    }
    
    @Override
    public List<String> errors()
    {
        List<String> errors = super.errors();
//        List<String> errors = new ArrayList<>();
        if (getValue() == null)
        {
            errors.add(name() + " value is null.  The property MUST have a value."); 
        }
        
        final ValueType valueType;
        if (getValueType() != null)
        {
            valueType = getValueType().getValue();
            boolean isValueTypeOK = isValueTypeValid(valueType);
            if (! isValueTypeOK)
            {
                errors.add(name() + " value type " + getValueType().getValue() + " is not supported.  Supported types include:" +
                        propertyType().allowedValueTypes().stream().map(v -> v.toString()).collect(Collectors.joining(",")));
            }
        } else
        {
            // use default valueType
            valueType = propertyType().allowedValueTypes().get(0);
        }
        
        List<String> valueTypeErrorList = valueType.createErrorList(getValue());
        if (valueTypeErrorList != null)
        {
            errors.addAll(valueTypeErrorList);
        }
        
//        List<String> propertyErrors = propertyType.errors(getParent());
//        propertyErrors.forEach(System.out::println);
//        errors.addAll(propertyErrors);
        return errors;
    }
    
    /* test if value type is valid */
    private boolean isValueTypeValid(ValueType value)
    {
        boolean isValueTypeOK = propertyType().allowedValueTypes().contains(value);
        boolean isUnknownType = value.equals(ValueType.UNKNOWN);
        boolean isNonStandardProperty = propertyType().equals(PropertyType.NON_STANDARD);
        return (isValueTypeOK || isUnknownType || isNonStandardProperty);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.append(":" + valueContent());
        // return folded line
        return ICalendarUtilities.foldLine(builder).toString();
    }
    
    @Override // Note: can't check equality of parents - causes stack overflow
    public boolean equals(Object obj)
    {
        boolean childrenEquals = super.equals(obj);
        if (! childrenEquals) return false;
        PropertyBase<?,?> testObj = (PropertyBase<?,?>) obj;
        boolean valueEquals = (getValue() == null) ? testObj.getValue() == null : getValue().equals(testObj.getValue());
        if (! valueEquals) return false;
        boolean nameEquals = name().equals(testObj.name());
        return nameEquals;
    }

    @Override // Note: can't check hashCode of parents - causes stack overflow
    public int hashCode()
    {
        int hash = super.hashCode();
        final int prime = 31;
        hash = prime * hash + ((converter == null) ? 0 : converter.hashCode());
        hash = prime * hash + ((propertyName == null) ? 0 : propertyName.hashCode());
        hash = prime * hash + ((propertyValueString == null) ? 0 : propertyValueString.hashCode());
        hash = prime * hash + ((unknownValue == null) ? 0 : unknownValue.hashCode());
        hash = prime * hash + ((value == null) ? 0 : value.hashCode());
        return hash;
    }
}
