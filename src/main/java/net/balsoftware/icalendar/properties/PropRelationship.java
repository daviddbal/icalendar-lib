package net.balsoftware.icalendar.properties;

import net.balsoftware.icalendar.parameters.Relationship;

public interface PropRelationship<T> extends Property<T>
{
    Relationship getRelationship();
    void setRelationship(Relationship relationship);
}
