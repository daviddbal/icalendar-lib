package net.balsoftware.icalendar.parameter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.balsoftware.icalendar.parameters.Delegatees;

public class DelegateesTest
{
    @Test // can copy list
    public void canCopyDelegatees()
    {
        Delegatees parameter = Delegatees.parse("\"mailto:jdoe@example.com\",\"mailto:jqpublic@example.com\"");
        Delegatees parameter2 = new Delegatees(parameter);
        assertEquals(parameter, parameter2);
    }
}
