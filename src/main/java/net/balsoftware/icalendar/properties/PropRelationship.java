package net.balsoftware.icalendar.properties;

import net.balsoftware.icalendar.parameters.Relationship;
import net.balsoftware.icalendar.properties.VProperty;

public interface PropRelationship<T> extends VProperty<T>
{
    Relationship getRelationship();
    void setRelationship(Relationship relationship);
}
