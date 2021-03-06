package net.balsoftware.icalendar.parameter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.balsoftware.icalendar.parameters.Relationship;
import net.balsoftware.icalendar.parameters.Relationship.RelationshipType;

public class RelationshipTypeTest
{
    @Test
    public void canParseRelationshipType1()
    {
        String expectedContent = "NEIGHBOR";
        Relationship p = new Relationship()
        		.withValue(expectedContent);
        assertEquals("RELTYPE=" + expectedContent, p.toString());
        assertEquals(RelationshipType.UNKNOWN, p.getValue());
    }
    
    @Test
    public void canParseRelationshipType2()
    {
        String expectedContent = "RELTYPE=NEIGHBOR";
        Relationship p = Relationship.parse(expectedContent);
        assertEquals(expectedContent, p.toString());
        assertEquals(RelationshipType.UNKNOWN, p.getValue());
    }
}
