package net.balsoftware.property.component;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.balsoftware.properties.component.descriptive.Classification;
import net.balsoftware.properties.component.descriptive.Classification.ClassificationType;

public class ClassificationTest
{
    @Test
    public void canParseClassification()
    {
        Classification madeProperty = new Classification(ClassificationType.PUBLIC);
        String expectedContent = "CLASS:PUBLIC";
        assertEquals(expectedContent, madeProperty.toString());
    }
    
    @Test
    public void canParseClassification2()
    {
        Classification madeProperty = Classification.parse("CLASS:CUSTOM");
        String expectedContent = "CLASS:CUSTOM";
        assertEquals(expectedContent, madeProperty.toString());
        assertEquals(ClassificationType.UNKNOWN, madeProperty.getValue());
    }
}
