package net.balsoftware.parameter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.balsoftware.parameters.Relationship;
import net.balsoftware.parameters.Relationship.RelationshipType;

public class RelationshipTypeTest
{
    @Test
    public void canParseRelationshipType1()
    {
        String expectedContent = "NEIGHBOR";
        Relationship p = Relationship.parse(expectedContent);
        assertEquals("RELTYPE=" + expectedContent, p.toContent());
        assertEquals(RelationshipType.UNKNOWN, p.getValue());
    }
    
    @Test
    public void canParseRelationshipType2()
    {
        String expectedContent = "RELTYPE=NEIGHBOR";
        Relationship p = Relationship.parse(expectedContent);
        System.out.println(p.toContent());
        assertEquals(expectedContent, p.toContent());
        assertEquals(RelationshipType.UNKNOWN, p.getValue());
    }
}
