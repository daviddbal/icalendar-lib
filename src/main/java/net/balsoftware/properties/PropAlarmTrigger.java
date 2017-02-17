package net.balsoftware.properties;

import javafx.beans.property.ObjectProperty;
import net.balsoftware.parameters.AlarmTriggerRelationship;

public interface PropAlarmTrigger<T> extends Property<T>
{
    AlarmTriggerRelationship getAlarmTrigger();
    ObjectProperty<AlarmTriggerRelationship> AlarmTriggerProperty();
    void setAlarmTrigger(AlarmTriggerRelationship AlarmTrigger);
}
