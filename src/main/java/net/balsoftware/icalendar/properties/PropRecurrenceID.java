package net.balsoftware.icalendar.properties;

import net.balsoftware.icalendar.parameters.Range;
import net.balsoftware.icalendar.properties.VProperty;

public interface PropRecurrenceID<T> extends VProperty<T>
{
    Range getRange();
    void setRange(Range range);
}
