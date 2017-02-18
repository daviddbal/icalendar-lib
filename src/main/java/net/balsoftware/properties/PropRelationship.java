package net.balsoftware.properties;

import net.balsoftware.parameters.Relationship;

public interface PropRelationship<T> extends Property<T>
{
    Relationship getRelationship();
    void setRelationship(Relationship relationship);
}
