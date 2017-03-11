package net.balsoftware.icalendar.properties;

import net.balsoftware.icalendar.parameters.AlarmTriggerRelationship;

public interface PropAlarmTrigger<T> extends VProperty<T>
{
    AlarmTriggerRelationship getAlarmTrigger();
    void setAlarmTrigger(AlarmTriggerRelationship AlarmTrigger);
}
