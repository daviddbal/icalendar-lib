package net.balsoftware.components;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import net.balsoftware.VElement;
import net.balsoftware.properties.component.change.DateTimeStamp;
import net.balsoftware.properties.component.misc.RequestStatus;
import net.balsoftware.properties.component.relationship.Attendee;
import net.balsoftware.properties.component.relationship.Organizer;
import net.balsoftware.properties.component.relationship.UniformResourceLocator;
import net.balsoftware.properties.component.relationship.UniqueIdentifier;
import net.balsoftware.utilities.Callback;
import net.balsoftware.utilities.DateTimeUtilities;
import net.balsoftware.utilities.UnfoldingStringIterator;

/**
 * Components with the following properties:
 * ATTENDEE, DTSTAMP, ORGANIZER, REQUEST-STATUS, UID, URL
 * 
 * @author David Bal
 *
 * @param <T> - implementation subclass
 * @see VEventNewInt
 * @see VTodoInt
 * @see VJournalInt
 * @see VFreeBusy
 */
public abstract class VPersonal<T> extends VPrimary<T> implements VAttendee<T>
{
    /**
     * ATTENDEE: Attendee
     * RFC 5545 iCalendar 3.8.4.1 page 107
     * This property defines an "Attendee" within a calendar component.
     * 
     * Examples:
     * ATTENDEE;MEMBER="mailto:DEV-GROUP@example.com":
     *  mailto:joecool@example.com
     * ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=ACCEPTED;CN=Jane Doe
     *  :mailto:jdoe@example.com
     */
    private List<Attendee> attendees;
    @Override
    public List<Attendee> getAttendees() { return attendees; }
    @Override
    public void setAttendees(List<Attendee> attendees)
    {
    	this.attendees = attendees;
    	attendees.forEach(c -> orderChild(c));
	}
    
    /**
     * DTSTAMP: Date-Time Stamp, from RFC 5545 iCalendar 3.8.7.2 page 137
     * This property specifies the date and time that the instance of the
     * iCalendar object was created
     */
    private DateTimeStamp dateTimeStamp;
    public DateTimeStamp getDateTimeStamp() { return dateTimeStamp; }
    public void setDateTimeStamp(String dtStamp) { setDateTimeStamp(DateTimeStamp.parse(dtStamp)); }
    public void setDateTimeStamp(DateTimeStamp dtStamp)
    {
    	this.dateTimeStamp = dtStamp;
    	orderChild(dtStamp);
	}
    public void setDateTimeStamp(ZonedDateTime dtStamp) { setDateTimeStamp(new DateTimeStamp(dtStamp)); }
    public T withDateTimeStamp(ZonedDateTime dtStamp)
    {
        setDateTimeStamp(dtStamp);
        return (T) this;
    }
    public T withDateTimeStamp(String dtStamp)
    {
        setDateTimeStamp(dtStamp);
        return (T) this;
    }
    public T withDateTimeStamp(DateTimeStamp dtStamp)
    {
        setDateTimeStamp(dtStamp);
        return (T) this;
    }

    /**
     * ORGANIZER: Organizer
     * RFC 5545 iCalendar 3.8.4.3 page 111
     * This property defines the organizer for a calendar component
     * 
     * Example:
     * ORGANIZER;CN=John Smith:mailto:jsmith@example.com
     */
    public Organizer getOrganizer() { return organizer; }
    private Organizer organizer;
    public void setOrganizer(Organizer organizer)
    {
    	this.organizer = organizer;
    	orderChild(organizer);
	}
    public void setOrganizer(String organizer) { setOrganizer(Organizer.parse(organizer)); }
    public T withOrganizer(String organizer)
    {
        setOrganizer(organizer);
        return (T) this;
    }
    public T withOrganizer(Organizer organizer)
    {
        setOrganizer(organizer);
        return (T) this;
    }

    /**
     * REQUEST-STATUS: Request Status
     * RFC 5545 iCalendar 3.8.8.3 page 141
     * This property defines the status code returned for a scheduling request.
     * 
     * Examples:
     * REQUEST-STATUS:2.0;Success
     * REQUEST-STATUS:3.7;Invalid calendar user;ATTENDEE:
     *  mailto:jsmith@example.com
     * 
     */
    public List<RequestStatus> getRequestStatus() { return requestStatus; }
    private List<RequestStatus> requestStatus;
    public void setRequestStatus(List<RequestStatus> requestStatus)
    {
    	this.requestStatus = requestStatus;
    	requestStatus.forEach(c -> orderChild(c));
	}
    public T withRequestStatus(List<RequestStatus> requestStatus)
    {
        setRequestStatus(requestStatus);
        return (T) this;
    }
    public T withRequestStatus(String...requestStatus)
    {
        List<RequestStatus> list = Arrays.stream(requestStatus)
                .map(c -> RequestStatus.parse(c))
                .collect(Collectors.toList());
        setRequestStatus(list);
        return (T) this;
    }
    public T withRequestStatus(RequestStatus...requestStatus)
    {
    	setRequestStatus(new ArrayList<>(Arrays.asList(requestStatus)));
        return (T) this;
    }

    /**
     * UID, Unique identifier
     * RFC 5545, iCalendar 3.8.4.7 page 117
     * A globally unique identifier for the calendar component.
     * required property
     * 
     * Example:
     * UID:19960401T080045Z-4000F192713-0052@example.com
     */
    private UniqueIdentifier uniqueIdentifier;
    public UniqueIdentifier getUniqueIdentifier() { return uniqueIdentifier; }
    public void setUniqueIdentifier(UniqueIdentifier uniqueIdentifier)
    {
    	this.uniqueIdentifier = uniqueIdentifier;
    	orderChild(uniqueIdentifier);
	}
    public void setUniqueIdentifier(String uniqueIdentifier) { setUniqueIdentifier(UniqueIdentifier.parse(uniqueIdentifier)); }
    /** Set uniqueIdentifier by calling uidGeneratorCallback */
    public void setUniqueIdentifier() { setUniqueIdentifier(getUidGeneratorCallback().call(null)); }
    public T withUniqueIdentifier(String uniqueIdentifier)
    {
        setUniqueIdentifier(uniqueIdentifier);
        return (T) this;
    }
    public T withUniqueIdentifier(UniqueIdentifier uniqueIdentifier)
    {
        setUniqueIdentifier(uniqueIdentifier);
        return (T) this;
    }
    /** Assign UID by using UID generator callback */
    public T withUniqueIdentifier()
    {
        setUniqueIdentifier(getUidGeneratorCallback().call(null));
        return (T) this;
    }
    
   
    /** Callback for creating unique uid values  */
    public Callback<Void, String> getUidGeneratorCallback()
    {
    	return uidGeneratorCallback;
	}
    private static Integer nextKey = 0;
    private Callback<Void, String> uidGeneratorCallback = (Void) ->
    { // default UID generator callback
        String dateTime = DateTimeUtilities.LOCAL_DATE_TIME_FORMATTER.format(LocalDateTime.now());
        String domain = "jfxtras.org";
        return dateTime + "-" + nextKey++ + domain;
    };
    public void setUidGeneratorCallback(Callback<Void, String> uidCallback)
    {
    	this.uidGeneratorCallback = uidCallback;
	}
    /** set UID callback generator.  This MUST be set before using the no-arg withUniqueIdentifier if
     * not using default callback.
     */
    public T withUidGeneratorCallback(Callback<Void, String> uidCallback)
    {
        setUidGeneratorCallback(uidCallback);
        return (T) this;
    }

    
    /**
     * URL: Uniform Resource Locator
     * RFC 5545 iCalendar 3.8.4.6 page 116
     * This property defines a Uniform Resource Locator (URL)
     * associated with the iCalendar object
     * 
     * Example:
     * URL:http://example.com/pub/calendars/jsmith/mytime.ics
     */
    public UniformResourceLocator getUniformResourceLocator() { return uniformResourceLocator; }
    private UniformResourceLocator uniformResourceLocator;
    public void setUniformResourceLocator(UniformResourceLocator url)
    {
    	this.uniformResourceLocator = url;
    	orderChild(url);
	};
    public void setUniformResourceLocator(String url) { setUniformResourceLocator(UniformResourceLocator.parse(url)); };
    public void setUniformResourceLocator(URI url) { setUniformResourceLocator(new UniformResourceLocator(url)); };
    public T withUniformResourceLocator(String url)
    {
        setUniformResourceLocator(url);
        return (T) this;
    }
    public T withUniformResourceLocator(URI url)
    {
        setUniformResourceLocator(url);
        return (T) this;
    }
    public T withUniformResourceLocator(UniformResourceLocator url)
    {
        setUniformResourceLocator(url);
        return (T) this;
    }
    
    /*
     * CONSTRUCTORS
     */
    VPersonal() { super(); }
    
    public VPersonal(VPersonal<T> source)
    {
        super(source);
    }
    
    @Override
    public Map<VElement, List<String>> parseContent(UnfoldingStringIterator lineIterator, boolean useRequestStatus)
    {
        Map<VElement, List<String>> statusMessages = super.parseContent(lineIterator, useRequestStatus);
        if (useRequestStatus)
        { // Set REQUEST-STATUS for each message
            setRequestStatus(FXCollections.observableArrayList());
            statusMessages.entrySet()
                    .stream()
                    .flatMap(e -> e.getValue().stream())
                    .forEach(e -> getRequestStatus().add(RequestStatus.parse(e)));
        }
        return statusMessages;
    }
    
    @Override
    public List<String> errors()
    {
//        List<String> errors = new ArrayList<>();
        List<String> errors = super.errors();
        if (getDateTimeStamp() == null)
        {
            errors.add("DTSTAMP is not present.  DTSTAMP is REQUIRED and MUST NOT occur more than once");
        }
        if (getUniqueIdentifier() == null)
        {
            errors.add("UID is not present.  UID is REQUIRED and MUST NOT occur more than once");
        }
        return errors;
    }
}
