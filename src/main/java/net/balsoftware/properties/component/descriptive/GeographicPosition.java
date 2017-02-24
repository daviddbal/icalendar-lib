package net.balsoftware.properties.component.descriptive;

import java.text.DecimalFormat;

import net.balsoftware.components.VEvent;
import net.balsoftware.components.VTodo;
import net.balsoftware.properties.PropertyBase;

/**
 * GEO
 * Geographic Position
 * RFC 5545 iCalendar 3.8.1.6. page 86
 * 
 * This property specifies information related to the global
 * position for the activity specified by a calendar component.
 * 
 * This property value specifies latitude and longitude,
 * in that order (i.e., "LAT LON" ordering).
 * 
 * Example:
 * GEO:37.386013;-122.082932
 *  
 * @author David Bal
 * 
 * The property can be specified in following components:
 * @see VEvent
 * @see VTodo
 */
public class GeographicPosition extends PropertyBase<String, GeographicPosition>
{
    public Double getLatitude() { return latitude; }
    private Double latitude;
    public void setLatitude(Double latitude)
    {
    	this.latitude = latitude;
    	buildNewValue();
	}
    public GeographicPosition withLatitude(Double latitude) { setLatitude(latitude); return this; }
    private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.######");

    public Double getLongitude() { return longitude; }
    private Double longitude;
    public void setLongitude(Double longitude)
    {
    	this.longitude = longitude;
    	buildNewValue();
	}
    public GeographicPosition withLongitude(Double longitude) { setLongitude(longitude); return this; }

    /*
     * CONSTRUCTORS
     */
    public GeographicPosition(GeographicPosition source)
    {
        super(source);
        setupListeners();
        updateParts(getValue());
    }
    
    public GeographicPosition(double latitude, double longitude)
    {
        super();
        setupListeners();
        setLatitude(latitude);
        setLongitude(longitude);
    }
    
    public GeographicPosition()
    {
        super();
        setupListeners();
    }
    
    /** Applies string converter only to description and exception parts of the RequestStatus property
     * This leaves the semicolon delimiters unescaped
     */
    @Override
    protected String valueContent()
    {
        StringBuilder builder = new StringBuilder(20);
        builder.append(DECIMAL_FORMAT.format(getLatitude()) + ";");
        builder.append(DECIMAL_FORMAT.format(getLongitude()));
        return builder.toString();
    }
    
    public static GeographicPosition parse(String value)
    {
        GeographicPosition geographicPosition = new GeographicPosition();
        geographicPosition.parseContent(value);
        return geographicPosition;
    }
    
    /*
     * LISTENERS
     * Used to keep latitude and longitude synchronized with the property value
     */
    private void setupListeners()
    {
//        latitudeProperty().addListener(doubleChangeListener);
//        longitudeProperty().addListener(doubleChangeListener);
//        valueProperty().addListener(valueChangeListener);        
    }
    
//    private final ChangeListener<? super String> valueChangeListener = (observable, oldValue, newValue) -> updateParts(newValue);
    
    
    
    private void updateParts(String newValue)
    {
//        latitudeProperty().removeListener(doubleChangeListener);
//        longitudeProperty().removeListener(doubleChangeListener);
        String[] values = newValue.split(";");
        if (values.length == 2)
        {
            setLatitude(Double.parseDouble(values[0]));
            setLongitude(Double.parseDouble(values[1]));
        } else
        {
            throw new IllegalArgumentException("Can't parse geographic position value:" + newValue);
        }
//        latitudeProperty().addListener(doubleChangeListener);
//        longitudeProperty().addListener(doubleChangeListener);
    }
        
//    private final ChangeListener<? super Double> doubleChangeListener = (observable, oldValue, newValue) -> buildNewValue();

    @Override
	public void setValue(String value)
    {
		super.setValue(value);
		updateParts(value);
	}
    
	private void buildNewValue()
    {
        if ((getLatitude() != null) && (getLongitude() != null))
        {
//            valueProperty().removeListener(valueChangeListener);
            StringBuilder builder = new StringBuilder(20);
            builder.append(DECIMAL_FORMAT.format(getLatitude()) + ";");
            builder.append(DECIMAL_FORMAT.format(getLongitude()));
            setValue(builder.toString());
//            valueProperty().addListener(valueChangeListener);
        }
    }
}
