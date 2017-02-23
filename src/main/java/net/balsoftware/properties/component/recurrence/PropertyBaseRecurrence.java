package net.balsoftware.properties.component.recurrence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import net.balsoftware.properties.PropBaseDateTime;
import net.balsoftware.properties.ValueType;
import net.balsoftware.utilities.DateTimeUtilities;
import net.balsoftware.utilities.DateTimeUtilities.DateTimeType;
import net.balsoftware.utilities.StringConverter;

/**
 * Abstract class for Exceptions and Recurrences
 * 
 * @author David Bal
 *
 * @param <U> - subclass
 * @param <T> - property value type
 * @see ExceptionDates
 * @see RecurrenceDates
 */
public abstract class PropertyBaseRecurrence<U> extends PropBaseDateTime<Set<Temporal>, U>
{
//    private ZoneId zone;
//    private DateTimeType myType;
    
    private final StringConverter<Set<Temporal>> CONVERTER = new StringConverter<Set<Temporal>>()
    {
        @Override
        public String toString(Set<Temporal> object)
        {
            return object.stream()
                    .sorted(DateTimeUtilities.TEMPORAL_COMPARATOR)
                    .peek(a -> System.out.println("here:" + a))
                    .map(t -> DateTimeUtilities.temporalToString(t))
                    .peek(a -> System.out.println("here:" + a))
                    .collect(Collectors.joining(","));
        }

        @Override
        public Set<Temporal> fromString(String string)
        {
            Set<Temporal> set = Arrays.stream(string.split(","))
                    .map(s -> DateTimeUtilities.temporalFromString(s))
                    .collect(Collectors.toSet());
            TreeSet<Temporal> treeSet = new TreeSet<Temporal>(DateTimeUtilities.TEMPORAL_COMPARATOR);
            treeSet.addAll(set);
            return set;
        }
    };
    
    /*
     * CONSTRUCTORS
     */
    public PropertyBaseRecurrence(Set<Temporal> value)
    {
        this();
        setValue(value);
        if (! isValid())
        {
            throw new IllegalArgumentException("Error in parsing " + value);
        }
    }
    
    public PropertyBaseRecurrence(Temporal...temporals)
    {
        this();
        Set<Temporal> tree = new TreeSet<>(DateTimeUtilities.TEMPORAL_COMPARATOR);
        tree.addAll(Arrays.asList(temporals));
        setValue(tree);
        if (! isValid())
        {
            throw new IllegalArgumentException("Error in parsing " + temporals);
        }
    }
    
    public PropertyBaseRecurrence()
    {
        super(new TreeSet<>(DateTimeUtilities.TEMPORAL_COMPARATOR));
        setConverter(CONVERTER);
    }

    public PropertyBaseRecurrence( PropertyBaseRecurrence<U> source)
    {
        super(source);
    }

    @Override
    public void setValue(Set<Temporal> value)
    {
        if (! value.isEmpty())
        {
            Temporal sampleValue = value.iterator().next();
            if (sampleValue instanceof LocalDate)
            {
                setValueType(ValueType.DATE); // must set value parameter to force output of VALUE=DATE
            } else if (! (sampleValue instanceof LocalDateTime) && ! (sampleValue instanceof ZonedDateTime))
            {
                throw new RuntimeException("can't convert property value to type: " + sampleValue.getClass().getSimpleName() +
                        ". Accepted types are: " + propertyType().allowedValueTypes());                
            }
        }
        super.setValue(value);
//        setupListener();
    }
        
    @Override
    public List<String> errors()
    {
    	List<String> errors = new ArrayList<>();
//    	List<RecurrenceDates> recurrenceDates = component.getRecurrenceDates();
    	Set<Temporal> recurrenceDates = getValue();
    	
    	// error check - all Temporal types must be same
    	if ((recurrenceDates != null) && (! recurrenceDates.isEmpty()))
		{
        	Temporal sampleTemporal = recurrenceDates.stream()
//            		.flatMap(r -> r.getValue().stream())
            		.findAny()
            		.get();
    		DateTimeType sampleType = DateTimeUtilities.DateTimeType.of(sampleTemporal);
        	Optional<String> error1 = recurrenceDates
        		.stream()
//        		.flatMap(r -> r.getValue().stream())
	        	.map(v ->
	        	{
	        		DateTimeType recurrenceType = DateTimeUtilities.DateTimeType.of(v);
	        		if (! recurrenceType.equals(sampleType))
	        		{
	                    return "Recurrences DateTimeType " + recurrenceType +
	                            " doesn't match previous recurrences DateTimeType " + sampleType;            
	        		}
	        		return null;
	        	})
	        	.filter(s -> s != null)
	        	.findAny();
        	
        	if (error1.isPresent())
        	{
        		errors.add(error1.get());
        	}
        	
//        	// DTSTART check - DO IN COMPONENT ERROR TEST
//            DateTimeType dateTimeStartType = DateTimeUtilities.DateTimeType.of(dtstart.getValue());
//            if (sampleType != dateTimeStartType)
//            {
//                errors.add("Recurrences DateTimeType (" + sampleType +
//                        ") must be same as the DateTimeType of DateTimeStart (" + dateTimeStartType + ")");
//            }
            
            // ensure all ZoneId values are the same
            if (sampleTemporal instanceof ZonedDateTime)
            {
                ZoneId zone = ((ZonedDateTime) sampleTemporal).getZone();
                boolean allZonesIdentical = recurrenceDates
                        .stream()
//                        .flatMap(r -> r.getValue().stream())
                        .map(t -> ((ZonedDateTime) t).getZone())
                        .allMatch(z -> z.equals(zone));
                if (! allZonesIdentical)
                {
                	errors.add("ZoneId are not all identical");
                }
                
            }
        }
        return errors;
    }
        
    @Override
    protected Set<Temporal> copyValue(Set<Temporal> source)
    {
        Set<Temporal> newCollection = new TreeSet<>(DateTimeUtilities.TEMPORAL_COMPARATOR);
        newCollection.addAll(source);
        return newCollection;
    }
}
