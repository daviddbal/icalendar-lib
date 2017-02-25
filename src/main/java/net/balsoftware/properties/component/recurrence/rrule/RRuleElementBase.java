package net.balsoftware.properties.component.recurrence.rrule;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.balsoftware.VParent;

abstract public class RRuleElementBase<T, U> implements RRuleElement<T>
{
    private VParent myParent;
    @Override public void setParent(VParent parent) { myParent = parent; }
    @Override public VParent getParent() { return myParent; }
    
    /*
     * Recurrence Rule element value
     * For example, FREQ=DAILY the value is DAILY
     * 
     */
    @Override
    public T getValue() { return value.get(); }
    @Override
    public ObjectProperty<T> valueProperty() { return value; }
    private ObjectProperty<T> value;
    @Override
    public void setValue(T value) { this.value.set(value); }
    public U withValue(T value) { setValue(value); return (U) this; }
    
    /**
     * ELEMENT TYPE
     * 
     *  The enumerated type of the parameter.
     */
    @Override
    public RRuleElementType elementType() { return elementType; }
    final private RRuleElementType elementType;
    
    @Override
    public String name() { return elementType.toString(); }
    
    /*
     * CONSTRUCTORS
     */
    protected RRuleElementBase()
    {
        elementType = RRuleElementType.enumFromClass(getClass());
        value = new SimpleObjectProperty<>(this, RRuleElementType.enumFromClass(getClass()).toString());
    }
    
    @Override
    public List<String> errors()
    {
        List<String> errors = new ArrayList<>();
        if (getValue() == null)
        {
            errors.add(elementType() + " value is null.  The element MUST have a value."); 
        }
        return errors;
    }
    
    @Override
    public String toString()
    {
        return RRuleElementType.enumFromClass(getClass()).toString() + "=" + getValue().toString();
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RRuleElementBase<?,?> other = (RRuleElementBase) obj;
        if (getValue() == null)
        {
            if (other.getValue() != null)
                return false;
        } else if (!getValue().equals(other.getValue()))
            return false;
        return true;
    }
    
    
}
