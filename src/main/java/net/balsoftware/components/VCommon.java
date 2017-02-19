package net.balsoftware.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.balsoftware.properties.component.misc.NonStandardProperty;

/**
 * <p>{@link VComponent} with the following properties
 * <ul>
 * <li>{@link NonStandardProperty X-PROP}
 * </ul>
 * </p>
 * 
 * @author David Bal
 *
 * @param <T> concrete subclass
 */
public abstract class VCommon<T> extends VComponentBase
{
    /**
     * Provides a framework for defining non-standard properties.
     * 
     * <p>Example:
     * <ul>
     * <l1>X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.
        org/mysubj.au
     *  </ul>
     */
    private List<NonStandardProperty> nonStandardProps;
    public List<NonStandardProperty> getNonStandard() { return nonStandardProps; }
    public void setNonStandard(List<NonStandardProperty> nonStandardProps) { this.nonStandardProps = nonStandardProps; }
    /**
     * Sets the value of the {@link #nonStandardProperty()} by parsing a vararg of
     * iCalendar content text representing individual {@link NonStandardProperty} objects.
     * 
     * @return - this class for chaining
     */
    public T withNonStandard(String...nonStandardProps)
    {
        List<NonStandardProperty> a = Arrays.stream(nonStandardProps)
                .map(c -> NonStandardProperty.parse(c))
                .collect(Collectors.toList());
        setNonStandard(FXCollections.observableArrayList(a));
        return (T) this;
    }
    /**
     * Sets the value of the {@link #nonStandardProperty()}
     * 
     * @return - this class for chaining
     */
    public T withNonStandard(ObservableList<NonStandardProperty> nonStandardProps)
    {
        setNonStandard(nonStandardProps);
        return (T) this;
    }
    /**
     * Sets the value of the {@link #nonStandardProperty()} from a vararg of {@link NonStandardProperty} objects.
     * 
     * @return - this class for chaining
     */    
    public T withNonStandard(NonStandardProperty...nonStandardProps)
    {
    	setNonStandard(new ArrayList<>(Arrays.asList(nonStandardProps)));
        return (T) this;
    }

    /*
     * CONSTRUCTORS
     */
    VCommon()
    {
        super();
    }
    
    VCommon(VCommon<T> source)
    {
        super(source);
    }
}
