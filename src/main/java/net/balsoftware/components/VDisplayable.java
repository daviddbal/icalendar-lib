package net.balsoftware.components;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.balsoftware.VCalendar;
import net.balsoftware.properties.PropertyType;
import net.balsoftware.properties.component.change.DateTimeCreated;
import net.balsoftware.properties.component.change.LastModified;
import net.balsoftware.properties.component.change.Sequence;
import net.balsoftware.properties.component.descriptive.Attachment;
import net.balsoftware.properties.component.descriptive.Categories;
import net.balsoftware.properties.component.descriptive.Classification;
import net.balsoftware.properties.component.descriptive.Classification.ClassificationType;
import net.balsoftware.properties.component.descriptive.Status;
import net.balsoftware.properties.component.descriptive.Status.StatusType;
import net.balsoftware.properties.component.descriptive.Summary;
import net.balsoftware.properties.component.recurrence.ExceptionDates;
import net.balsoftware.properties.component.recurrence.RecurrenceDates;
import net.balsoftware.properties.component.recurrence.RecurrenceRule;
import net.balsoftware.properties.component.recurrence.RecurrenceRuleCache;
import net.balsoftware.properties.component.relationship.Contact;
import net.balsoftware.properties.component.relationship.RecurrenceId;
import net.balsoftware.properties.component.relationship.RelatedTo;
import net.balsoftware.properties.component.time.DateTimeStart;
import net.balsoftware.utilities.Callback;
import net.balsoftware.utilities.DateTimeUtilities;
import net.balsoftware.utilities.DateTimeUtilities.DateTimeType;

/**
 * <p>{@link VComponent} with the following properties
 * <ul>
 * <li>{@link Attachment ATTACH}
 * <li>{@link Categories CATEGORIES}
 * <li>{@link Classification CLASS}
 * <li>{@link Contact CONTACT}
 * <li>{@link DateTimeCreated CREATED}
 * <li>{@link ExceptionDates EXDATE}
 * <li>{@link LastModified LAST-MODIFIED}
 * <li>{@link RecurrenceId RECURRENCE-ID}
 * <li>{@link RelatedTo RELATED-TO}
 * <li>{@link RecurrenceRule RRULE}
 * <li>{@link Sequence SEQUENCE}
 * <li>{@link Status STATUS}
 * <li>{@link Summary SUMMARY}
 * </ul>
 * </p>
 * 
 * @author David Bal
 *
 * @param <T> concrete subclass
 */
public abstract class VDisplayable<T> extends VPersonal<T> implements VRepeatable<T>, VDescribable<T>, VLastModified<T>
{
    /**
     * This property provides the capability to associate a document object with a calendar component.
     * 
     *<p>Example:  The following is an example of this property:
     *<ul>
     *<li>ATTACH:CID:jsmith.part3.960817T083000.xyzMail@example.com
     *<li>ATTACH;FMTTYPE=application/postscript:ftp://example.com/pub/<br>
     *  reports/r-960812.ps
     *</ul>
     *</p>
     */
    @Override
    public List<Attachment<?>> getAttachments() { return attachments; }
    private List<Attachment<?>> attachments;
    @Override
    public void setAttachments(List<Attachment<?>> attachments) { this.attachments = attachments; }
    
    /**
     * CATEGORIES:
     * RFC 5545 iCalendar 3.8.1.12. page 81
     * This property defines the categories for a calendar component.
     * Example:
     * CATEGORIES:APPOINTMENT,EDUCATION
     * CATEGORIES:MEETING
     */
    public List<Categories> getCategories() { return categories; }
    private List<Categories> categories;
    public void setCategories(List<Categories> categories) { this.categories = categories; }
    public T withCategories(List<Categories> categories)
    {
        setCategories(categories);
        return (T) this;
    }
    public T withCategories(String...categories)
    {
        List<Categories> list = Arrays.stream(categories)
                .map(c -> Categories.parse(c))
                .collect(Collectors.toList());
    	setCategories(list);
        return (T) this;
    }
    public T withCategories(Categories...categories)
    {
    	setCategories(new ArrayList<>(Arrays.asList(categories)));
        return (T) this;
    }
    
    /**
     * CLASS
     * Classification
     * RFC 5545, 3.8.1.3, page 82
     * 
     * This property defines the access classification for a calendar component.
     * 
     * Example:
     * CLASS:PUBLIC
     */
    public Classification getClassification() { return classification; }
    private Classification classification;
    public void setClassification(String classification) { setClassification(Classification.parse(classification)); }
    public void setClassification(Classification classification) { this.classification = classification; }
    public void setClassification(ClassificationType classification) { setClassification(new Classification(classification)); }
    public T withClassification(Classification classification)
    {
        setClassification(classification);
        return (T) this;
    }
    public T withClassification(ClassificationType classification)
    {
        setClassification(classification);
        return (T) this;
    }
    public T withClassification(String classification)
    {
        setClassification(classification);
        return (T) this;
    }
    
    /**
     * CONTACT:
     * RFC 5545 iCalendar 3.8.4.2. page 109
     * This property is used to represent contact information or
     * alternately a reference to contact information associated with the
     * calendar component.
     * 
     * Example:
     * CONTACT;ALTREP="ldap://example.com:6666/o=ABC%20Industries\,
     *  c=US???(cn=Jim%20Dolittle)":Jim Dolittle\, ABC Industries\,
     *  +1-919-555-1234
     */
    public List<Contact> getContacts() { return contacts; }
    private List<Contact> contacts;
    public void setContacts(List<Contact> contacts) { this.contacts = contacts; }
    public T withContacts(List<Contact> contacts)
    {
        setContacts(contacts);
        return (T) this;
    }
    public T withContacts(String...contacts)
    {
        List<Contact> list = Arrays.stream(contacts)
                .map(c -> Contact.parse(c))
                .collect(Collectors.toList());
        setContacts(list);
        return (T) this;
    }
    public T withContacts(Contact...contacts)
    {
    	setContacts(new ArrayList<>(Arrays.asList(contacts)));
        return (T) this;
    }
    
    /**
     * CREATED: Date-Time Created
     * RFC 5545 iCalendar 3.8.7.1 page 136
     * This property specifies the date and time that the calendar information was created.
     * This is analogous to the creation date and time for a file in the file system.
     * The value MUST be specified in the UTC time format.
     * 
     * Example:
     * CREATED:19960329T133000Z
     */
    public DateTimeCreated getDateTimeCreated() { return dateTimeCreated; }
    private DateTimeCreated dateTimeCreated;
    public void setDateTimeCreated(String dtCreated) { setDateTimeCreated(DateTimeCreated.parse(dtCreated)); }
    public void setDateTimeCreated(DateTimeCreated dtCreated) { this.dateTimeCreated = dtCreated; }
    public void setDateTimeCreated(ZonedDateTime dtCreated) { setDateTimeCreated(new DateTimeCreated(dtCreated)); }
    public T withDateTimeCreated(ZonedDateTime dtCreated)
    {
        setDateTimeCreated(dtCreated);
        return (T) this;
    }
    public T withDateTimeCreated(String dtCreated)
    {
        setDateTimeCreated(dtCreated);
        return (T) this;
    }
    public T withDateTimeCreated(DateTimeCreated dtCreated)
    {
        setDateTimeCreated(dtCreated);
        return (T) this;
    }
    
   /** 
    * EXDATE
    * Exception Date-Times
    * RFC 5545 iCalendar 3.8.5.1, page 117.
    * 
    * This property defines the list of DATE-TIME exceptions for
    * recurring events, to-dos, journal entries, or time zone definitions.
    */
    public List<ExceptionDates> getExceptionDates() { return exceptionDates; }
    private List<ExceptionDates> exceptionDates;
    public void setExceptionDates(List<ExceptionDates> exceptionDates)
    {
        if (exceptionDates != null)
        {
            String error = checkRecurrencesConsistency(exceptionDates);
            if (error != null) throw new DateTimeException(error);
        }
        this.exceptionDates = exceptionDates;
    }
    public T withExceptionDates(List<ExceptionDates> exceptions)
    {
        setExceptionDates(exceptions);
        return (T) this;
    }
    public T withExceptionDates(String...exceptions)
    {
        List<ExceptionDates> list = Arrays.stream(exceptions)
                .map(c -> ExceptionDates.parse(c))
                .collect(Collectors.toList());  
        setExceptionDates(list);
        return (T) this;
    }
    public T withExceptionDates(Temporal...exceptions)
    {
        List<ExceptionDates> list = Arrays.stream(exceptions)
                .map(t -> new ExceptionDates(t))
                .collect(Collectors.toList()); 
    	setExceptionDates(list);
        return (T) this;
    }
    public T withExceptionDates(ExceptionDates...exceptions)
    {
    	setExceptionDates(new ArrayList<>(Arrays.asList(exceptions)));
        return (T) this;
    }
    
    /**
    * LAST-MODIFIED
    * RFC 5545, 3.8.7.3, page 138
    * 
    * This property specifies the date and time that the
    * information associated with the calendar component was last
    * revised in the calendar store.
    *
    * Note: This is analogous to the modification date and time for a
    * file in the file system.
    * 
    * The value MUST be specified as a date with UTC time.
    * 
    * Example:
    * LAST-MODIFIED:19960817T133000Z
    */
    @Override
    public LastModified getDateTimeLastModified() { return lastModified; }
    private LastModified lastModified;
    public void setDateTimeLastModified(LastModified lastModified) { this.lastModified = lastModified; }
    // Other setters are default methods in interface
    
    /**
     * RDATE
     * Recurrence Date-Times
     * RFC 5545 iCalendar 3.8.5.2, page 120.
     * 
     * This property defines the list of DATE-TIME values for
     * recurring events, to-dos, journal entries, or time zone definitions.
     * 
     * NOTE: DOESN'T CURRENTLY SUPPORT PERIOD VALUE TYPE
     * */
    @Override
    public List<RecurrenceDates> getRecurrenceDates() { return recurrenceDates; }
    private List<RecurrenceDates> recurrenceDates;
    @Override
    public void setRecurrenceDates(List<RecurrenceDates> recurrenceDates) { this.recurrenceDates = recurrenceDates; }

    /**
     * RECURRENCE-ID: Recurrence Identifier
     * RFC 5545 iCalendar 3.8.4.4 page 112
     * The property value is the original value of the "DTSTART" property of the 
     * recurrence instance before an edit that changed the value.
     * 
     * Example:
     * RECURRENCE-ID;VALUE=DATE:19960401
     */
    public RecurrenceId getRecurrenceId() { return recurrenceId; }
    private RecurrenceId recurrenceId;
    public void setRecurrenceId(RecurrenceId recurrenceId) { this.recurrenceId = recurrenceId; }
    public void setRecurrenceId(String recurrenceId) { setRecurrenceId(RecurrenceId.parse(recurrenceId)); }
    public void setRecurrenceId(Temporal temporal)
    {
        if ((temporal instanceof LocalDate) || (temporal instanceof LocalDateTime) || (temporal instanceof ZonedDateTime))
        {
            if (getRecurrenceId() == null)
            {
                setRecurrenceId(new RecurrenceId(temporal));
            } else
            {
                getRecurrenceId().setValue(temporal);
            }
        } else
        {
            throw new DateTimeException("Only LocalDate, LocalDateTime and ZonedDateTime supported. "
                    + temporal.getClass().getSimpleName() + " is not supported");
        }
    }
    public T withRecurrenceId(RecurrenceId recurrenceId)
    {
        setRecurrenceId(recurrenceId);
        return (T) this;
    }
    public T withRecurrenceId(String recurrenceId)
    {
        setRecurrenceId(recurrenceId);
        return (T) this;
    }
    public T withRecurrenceId(Temporal recurrenceId)
    {
        setRecurrenceId(recurrenceId);
        return (T) this;
    }
        
    /** Checks if RecurrenceId has same date-time type as DateTimeStart.  Returns String containing
     * error message if there is a problem, null otherwise. */
    String checkRecurrenceIdConsistency()
    {
        if (getRecurrenceId() != null && recurrenceParent() != null)
        {
            DateTimeType recurrenceIdType = DateTimeUtilities.DateTimeType.of(getRecurrenceId().getValue());
            DateTimeType parentDateTimeStartType = DateTimeUtilities.DateTimeType.of(recurrenceParent().getDateTimeStart().getValue());
            if (recurrenceIdType != parentDateTimeStartType)
            {
                return PropertyType.RECURRENCE_IDENTIFIER.toString() + ":RecurrenceId DateTimeType (" + recurrenceIdType +
                        ") must be same as the type of its parent's DateTimeStart (" + parentDateTimeStartType + ")";
            }
        }
        return null;
    }

    /**
     * RELATED-TO:
     * 3.8.4.5, RFC 5545 iCalendar, page 115
     * This property is used to represent a relationship or reference between
     * one calendar component and another.  By default, the property value points to another
     * calendar component's UID that has a PARENT relationship to the referencing object.
     * This field is null unless the object contains as RECURRENCE-ID value.
     * 
     * Example:
     * RELATED-TO:19960401-080045-4000F192713-0052@example.com
     */
    public List<RelatedTo> getRelatedTo() { return relatedTo; }
    private List<RelatedTo> relatedTo;
    public void setRelatedTo(List<RelatedTo> relatedTo) { this.relatedTo = relatedTo; }
    public T withRelatedTo(List<RelatedTo> relatedTo) { setRelatedTo(relatedTo); return (T) this; }
    public T withRelatedTo(String...relatedTo)
    {
        List<RelatedTo> list = Arrays.stream(relatedTo)
                .map(c -> RelatedTo.parse(c))
                .collect(Collectors.toList());
        setRelatedTo(list);
        return (T) this;
    }
    public T withRelatedTo(RelatedTo...relatedTo)
    {
    	setRelatedTo(new ArrayList<>(Arrays.asList(relatedTo)));
        return (T) this;
    }
    
    /**
     * RRULE, Recurrence Rule
     * RFC 5545 iCalendar 3.8.5.3, page 122.
     * This property defines a rule or repeating pattern for recurring events, 
     * to-dos, journal entries, or time zone definitions
     * If component is not repeating the value is null.
     * 
     * Examples:
     * RRULE:FREQ=DAILY;COUNT=10
     * RRULE:FREQ=WEEKLY;UNTIL=19971007T000000Z;WKST=SU;BYDAY=TU,TH
     */
    @Override
    public RecurrenceRule getRecurrenceRule() { return recurrenceRule; }
    private RecurrenceRule recurrenceRule;
    public void setRecurrenceRule(RecurrenceRule recurrenceRule) { this.recurrenceRule = recurrenceRule; }
 
    /**
     * SEQUENCE:
     * RFC 5545 iCalendar 3.8.7.4. page 138
     * This property defines the revision sequence number of the calendar component within a sequence of revisions.
     * Example:  The following is an example of this property for a calendar
     * component that was just created by the "Organizer":
     *
     * SEQUENCE:0
     *
     * The following is an example of this property for a calendar
     * component that has been revised two different times by the
     * "Organizer":
     *
     * SEQUENCE:2
     */
    public Sequence getSequence() { return sequence; }
    private Sequence sequence;
    public void setSequence(Sequence sequence) { this.sequence = sequence; }
    public void setSequence(String sequence) { setSequence(Sequence.parse(sequence)); }
    public void setSequence(Integer sequence) { setSequence(new Sequence(sequence)); }
    public T withSequence(Sequence sequence)
    {
        if (getSequence() == null)
        {
            setSequence(sequence);
            return (T) this;
        } else
        {
            throw new IllegalArgumentException("Property can only occur once in the calendar component");
        }
    }
    public T withSequence(Integer sequence)
    {
        if (getSequence() == null)
        {
            setSequence(sequence);
            return (T) this;
        } else
        {
            throw new IllegalArgumentException("Property can only occur once in the calendar component");
        }
    }
    public T withSequence(String sequence)
    {
        if (getSequence() == null)
        {
            setSequence(sequence);
            return (T) this;
        } else
        {
            throw new IllegalArgumentException("Property can only occur once in the calendar component");
        }
    }
    public void incrementSequence()
    {
        if (getSequence() != null)
        {
            setSequence(getSequence().getValue()+1);            
        } else
        {
            setSequence(1);            
        }
    }
    
    /**
     * STATUS
     * RFC 5545 iCalendar 3.8.1.11. page 92
     * 
     * This property defines the overall status or confirmation for the calendar component.
     * 
     * Example:
     * STATUS:TENTATIVE
     */
    public Status getStatus() { return status; }
    private Status status;
    public void setStatus(Status status) { this.status = status; }
    public void setStatus(String status) { setStatus(Status.parse(status)); }
    public void setStatus(StatusType status) { setStatus(new Status(status)); }
    public T withStatus(Status status)
    {
        setStatus(status);
        return (T) this;
    }
    public T withStatus(StatusType status)
    {
        setStatus(status);
        return (T) this;
    }
    public T withStatus(String status)
    {
        setStatus(status);
        return (T) this;
    }

    /**
     * SUMMARY
     * RFC 5545 iCalendar 3.8.1.12. page 93
     * 
     * This property defines a short summary or subject for the calendar component.
     * 
     * Example:
     * SUMMARY:Department Party
     */
    @Override
    public Summary getSummary() { return summary; }
    private Summary summary;
    public void setSummary(Summary summary) { this.summary = summary; }

    
    @Override
    void dateTimeStartListenerHook()
    {
        super.dateTimeStartListenerHook();
        String reccurenceIDErrorString = checkRecurrenceIdConsistency();
        if (reccurenceIDErrorString != null)
        {
            throw new RuntimeException(reccurenceIDErrorString);
        }
    }
    
    /*
     * CONSTRUCTORS
     */
    public VDisplayable() { super(); }
    
    public VDisplayable(VDisplayable<T> source)
    {
        super(source);
    }
    
    @Override
    @Deprecated // need to move to VCalendar
    public Stream<Temporal> streamRecurrences(Temporal start)
    {
        // get stream with recurrence rule (RRULE) and recurrence date (RDATE)
        Stream<Temporal> inStream = VRepeatable.super.streamRecurrences(start);

        // assign temporal comparator to match start type
        final Comparator<Temporal> temporalComparator = DateTimeUtilities.getTemporalComparator(start);
        
        // Handle Recurrence IDs
        final Stream<Temporal> stream2;
        List<VDisplayable<?>> children = recurrenceChildren();
        if (children != null)
        {
            // If present, remove recurrence ID original values
            List<Temporal> recurrenceIDTemporals = recurrenceChildren()
                    .stream()
                    .map(c -> c.getRecurrenceId().getValue())
                    .collect(Collectors.toList());
            stream2 = inStream.filter(t -> ! recurrenceIDTemporals.contains(t));
        } else
        {
            stream2 = inStream;
        }
        
        // If present, remove exceptions
        final Stream<Temporal> stream3;
        if (getExceptionDates() != null)
        {
            List<Temporal> exceptions = getExceptionDates()
                    .stream()
                    .flatMap(r -> r.getValue().stream())
                    .map(v -> v)
                    .sorted(temporalComparator)
                    .collect(Collectors.toList());
            stream3 = stream2.filter(d -> ! exceptions.contains(d));
        } else
        {
            stream3 = stream2;
        }
        
        if (getRecurrenceRule() == null)
        {
            return stream3; // no cache is no recurrence rule
        }
    	if (getRecurrenceRule().getValue().getCount() == null)
    	{
            return recurrenceCache().makeCache(stream3);  // make cache of start date/times
    	} else
    	{ // if RRULE has COUNT must start at DTSTART
    		return stream3;
    	}
    }

    /*
     *  RECURRENCE STREAMER
     *  produces recurrence set
     */
    private RecurrenceRuleCache recurrenceCache = new RecurrenceRuleCache(this);
    @Override
    public RecurrenceRuleCache recurrenceCache() { return recurrenceCache; }

    /*
     * RECURRENCE CHILDREN - (RECURRENCE-IDs AND MATCHING UID)
     */
    /**  Callback to make list of child components (those with RECURRENCE-ID and same UID)
     * Callback assigned in {@link VCalendar#displayableListChangeListener } */
    private Callback<VDisplayable<?>, List<VDisplayable<?>>> makeRecurrenceChildrenListCallBack;
//    @Override
//    public Callback<VComponentDisplayableBase<?>, List<VComponentDisplayableBase<?>>> getChildComponentsListCallBack()
//    {
//        return makeChildComponentsListCallBack;
//    }
    public void setRecurrenceChildrenListCallBack(Callback<VDisplayable<?>, List<VDisplayable<?>>> makeRecurrenceChildrenListCallBack)
    {
        this.makeRecurrenceChildrenListCallBack = makeRecurrenceChildrenListCallBack;
    }

    public List<VDisplayable<?>> recurrenceChildren()
    {
        if ((getRecurrenceId() == null) && (makeRecurrenceChildrenListCallBack != null))
        {
            return Collections.unmodifiableList(makeRecurrenceChildrenListCallBack.call(this));
        }
        return Collections.unmodifiableList(Collections.emptyList());
    }
    
    /*
     * RECURRENCE PARENT - (the VComponent with matching UID and no RECURRENCEID)
     */
    private Callback<VDisplayable<?>, VDisplayable<?>> recurrenceParentCallBack;

    public void setRecurrenceParentListCallBack(Callback<VDisplayable<?>, VDisplayable<?>> recurrenceParentCallBack)
    {
        this.recurrenceParentCallBack = recurrenceParentCallBack;
    }
    
    public VDisplayable<?> recurrenceParent()
    {
        if ((getRecurrenceId() != null) && (recurrenceParentCallBack != null))
        {
            return recurrenceParentCallBack.call(this);
        }
        return null;
    }

    /** returns list of orphaned recurrence components due to a change.  These
     * components should be deleted */
    public List<VDisplayable<?>> orphanedRecurrenceChildren()
    {
        boolean isRecurrenceParent = getRecurrenceId() == null;
        if (isRecurrenceParent)
        {
            VCalendar vCalendar = (VCalendar) getParent();
            if (vCalendar != null)
            {
                final String uid = getUniqueIdentifier().getValue();
                return vCalendar.uidComponentsMap().get(uid)
                        .stream()
                        .filter(v -> v.getRecurrenceId() != null)
                        .filter(v -> 
                        {
                            Temporal myRecurrenceID = v.getRecurrenceId().getValue();
                            Temporal cacheStart = recurrenceCache().getClosestStart(myRecurrenceID);
                            Temporal nextRecurrenceDateTime = getRecurrenceRule().getValue()
                                    .streamRecurrences(cacheStart)
                                    .filter(t -> ! DateTimeUtilities.isBefore(t, myRecurrenceID))
                                    .findFirst()
                                    .orElseGet(() -> null);
                            return ! Objects.equals(nextRecurrenceDateTime, myRecurrenceID);
                        })
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
    


    @Override
    public List<String> errors()
    {
        List<String> errors = super.errors();
        String reccurenceIDErrorString = checkRecurrenceIdConsistency();
        if (reccurenceIDErrorString != null)
        {
            errors.add(reccurenceIDErrorString);
        }
        
        if (getDateTimeStart() != null)
        {
            DateTimeType startType = DateTimeUtilities.DateTimeType.of(getDateTimeStart().getValue());
            if ((getExceptionDates() != null) && (! getExceptionDates().get(0).getValue().isEmpty()))
            {
                // assumes all exceptions are same Temporal type.  There is a listener to guarantee that assumption.
                Temporal e1 = getExceptionDates().get(0).getValue().iterator().next();
                DateTimeType exceptionType = DateTimeUtilities.DateTimeType.of(e1);
                boolean isExceptionTypeMatch = startType == exceptionType;
                if (! isExceptionTypeMatch)
                {
                    errors.add("DTSTART, EXDATE: The value type of EXDATE elements MUST be the same as the DTSTART property (" + exceptionType + ", " + startType + ")");
                }
            }
            

//            if (getRecurrenceId() != null && getParent() != null)
//            {
//                DateTimeType recurrenceIdType = DateTimeUtilities.DateTimeType.of(getRecurrenceId().getValue());
//                List<VComponentDisplayableBase<?>> relatedComponents = ((VCalendar) getParent()).uidComponentsMap().get(getUniqueIdentifier().getValue());
//                VComponentDisplayableBase<?> parentComponent = relatedComponents.stream()
//                        .filter(v -> v.getRecurrenceId() == null)
//                        .findFirst()
//                        .orElseGet(() -> null);
//                if (parentComponent != null)
//                {
//                    DateTimeType dateTimeStartType = DateTimeUtilities.DateTimeType.of(parentComponent.getDateTimeStart().getValue());
//                    if (recurrenceIdType != dateTimeStartType)
//                    {
//                        errors.add("The value type of RECURRENCE-ID MUST be the same as the parent's DTSTART property (" + recurrenceIdType + ", " + dateTimeStartType);
//                    }
//                } else
//                {
//                    errors.add("Parent of this component with RECURRENCE-ID can't be found.");                    
//                }
//            }
            
            // Tests from Repeatable
            errors.addAll(VRepeatable.errorsRepeatable(this));
        }
    
        return errors;
    }
    
    /** Erase all date/time properties such as DTSTART, DTEND, DURATION, and DUE (which ever exist).  This
     * is necessary to prepare a CANCEL iTIP message for one recurrence instance. */
    public void eraseDateTimeProperties()
    {
        setDateTimeStart((DateTimeStart) null);
    }
}
