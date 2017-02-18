package net.balsoftware.properties;

import net.balsoftware.parameters.Range;

public interface PropRecurrenceID<T> extends Property<T>
{
    Range getRange();
    void setRange(Range range);
}
