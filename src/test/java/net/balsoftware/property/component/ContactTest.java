package net.balsoftware.property.component;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import net.balsoftware.properties.component.relationship.Contact;
import net.balsoftware.utilities.ICalendarUtilities;

public class ContactTest
{
    @Test
    public void canParseContact() throws URISyntaxException
    {
        String content = "CONTACT;LANGUAGE=en-US;ALTREP=\"CID:part3.msg970930T083000SILVER@example.com\":Jim Dolittle\\, ABC Industries\\, +1-919-555-1234";
        Contact madeProperty = Contact.parse(content);
        assertEquals(ICalendarUtilities.foldLine(content).toString(), madeProperty.toString());
        Contact expectedProperty = new Contact()
                .withLanguage("en-US")
                .withAlternateText(new URI("CID:part3.msg970930T083000SILVER@example.com"))
                .withValue("Jim Dolittle, ABC Industries, +1-919-555-1234")
                ;
        assertEquals(expectedProperty, madeProperty);
        assertEquals(ICalendarUtilities.foldLine(content).toString(), expectedProperty.toString());
    }
}
