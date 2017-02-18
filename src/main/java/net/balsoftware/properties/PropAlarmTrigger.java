package net.balsoftware.properties;

import net.balsoftware.parameters.AlarmTriggerRelationship;

public interface PropAlarmTrigger<T> extends Property<T>
{
    AlarmTriggerRelationship getAlarmTrigger();
    void setAlarmTrigger(AlarmTriggerRelationship AlarmTrigger);
}
