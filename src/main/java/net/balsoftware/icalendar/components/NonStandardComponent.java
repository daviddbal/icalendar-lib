package net.balsoftware.icalendar.components;

import net.balsoftware.icalendar.components.VDateTimeEnd;
import net.balsoftware.icalendar.components.VDescribable2;
import net.balsoftware.icalendar.components.VLocatable;
import net.balsoftware.icalendar.components.VRepeatable;

public abstract class NonStandardComponent<T> extends VLocatable<T> implements VDateTimeEnd<T>,
VDescribable2<T>, VRepeatable<T>
{

    NonStandardComponent()
    {
        throw new RuntimeException("not implemented");
        // TODO - FINISH THIS
    }
}
