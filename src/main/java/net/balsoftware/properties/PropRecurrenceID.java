package net.balsoftware.properties;

import javafx.beans.property.ObjectProperty;
import net.balsoftware.parameters.Range;

public interface PropRecurrenceID<T> extends Property<T>
{
    Range getRange();
    ObjectProperty<Range> rangeProperty();
    void setRange(Range range);
}
