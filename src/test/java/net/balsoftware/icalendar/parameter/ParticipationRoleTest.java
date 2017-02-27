package net.balsoftware.icalendar.parameter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.balsoftware.icalendar.parameters.ParticipationRole;
import net.balsoftware.icalendar.parameters.ParticipationRole.ParticipationRoleType;

public class ParticipationRoleTest
{
    @Test
    public void canParseKnownRole()
    {
        String expectedContent = "CHAIR";
        ParticipationRole p = ParticipationRole.parse(expectedContent);
        assertEquals("ROLE=" + expectedContent, p.toString());
        assertEquals(ParticipationRoleType.CHAIR, p.getValue());
    }

    @Test
    public void canParseUnknownRole()
    {
        String expectedContent = "GRAND-POOBAH";
        ParticipationRole p = ParticipationRole.parse(expectedContent);
        assertEquals("ROLE=" + expectedContent, p.toString());
        assertEquals(ParticipationRoleType.UNKNOWN, p.getValue());
    }

}
