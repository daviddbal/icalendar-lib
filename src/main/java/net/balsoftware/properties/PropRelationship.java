package net.balsoftware.properties;

import javafx.beans.property.ObjectProperty;
import net.balsoftware.parameters.Relationship;

public interface PropRelationship<T> extends Property<T>
{
    Relationship getRelationship();
    ObjectProperty<Relationship> relationshipProperty();
    void setRelationship(Relationship relationship);
}
