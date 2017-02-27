package net.balsoftware.icalendar.properties;

import net.balsoftware.icalendar.parameters.Range;

public interface PropRecurrenceID<T> extends Property<T>
{
    Range getRange();
    void setRange(Range range);
}
