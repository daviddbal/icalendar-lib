package net.balsoftware.icalendar.properties;

import net.balsoftware.icalendar.parameters.AlarmTriggerRelationship;
import net.balsoftware.icalendar.properties.VProperty;

public interface PropAlarmTrigger<T> extends VProperty<T>
{
    AlarmTriggerRelationship getAlarmTrigger();
    void setAlarmTrigger(AlarmTriggerRelationship AlarmTrigger);
}
