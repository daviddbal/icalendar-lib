package net.balsoftware;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

//import net.balsoftware.calendar.CalendarScaleTest;
//import net.balsoftware.calendar.CopyCalendarTest;
//import net.balsoftware.calendar.GeneralCalendarTest;
//import net.balsoftware.calendar.OrdererTest;
//import net.balsoftware.calendar.ParseCalendarTest;
//import net.balsoftware.calendar.ReadICSFileTest;
//import net.balsoftware.calendar.VCalendarRecurrenceIDTest;
//import net.balsoftware.component.BaseTest;
//import net.balsoftware.component.CopyComponentTest;
//import net.balsoftware.component.DaylightSavingsTimeTest;
//import net.balsoftware.component.DisplayableTest;
//import net.balsoftware.component.EqualsTest;
//import net.balsoftware.component.GeneralComponentTest;
//import net.balsoftware.component.LocatableTest;
//import net.balsoftware.component.ParseComponentTest;
//import net.balsoftware.component.PersonalTest;
//import net.balsoftware.component.PrimaryTest;
//import net.balsoftware.component.RepeatableTest;
//import net.balsoftware.component.ScheduleConflictTest;
//import net.balsoftware.component.StandardOrDaylightTimeTest;
//import net.balsoftware.component.VAlarmTest;
//import net.balsoftware.component.VEventTest;
//import net.balsoftware.component.VFreeBusyTest;
//import net.balsoftware.component.VJournalTest;
//import net.balsoftware.component.VTimeZoneTest;
//import net.balsoftware.component.VTodoTest;
//import net.balsoftware.itip.CancelRecurrenceTest;
//import net.balsoftware.itip.ComboMessageTest;
//import net.balsoftware.itip.HandleRecurrencesTest;
//import net.balsoftware.itip.RequestTest;
//import net.balsoftware.itip.SimpleCancelTest;
//import net.balsoftware.itip.SimplePublishTest;
//import net.balsoftware.itip.WholeDayTest;
//import net.balsoftware.misc.FoldingAndUnfoldingTest;
import net.balsoftware.parameter.AlternateTextRepresentationTest;
//import net.balsoftware.parameter.ByDayTest;
//import net.balsoftware.parameter.ByMonthTest;
//import net.balsoftware.parameter.ByWeekNumberTest;
//import net.balsoftware.parameter.ByYearDayTest;
import net.balsoftware.parameter.CommonNameTest;
import net.balsoftware.parameter.DelegateesTest;
import net.balsoftware.parameter.DirectoryEntryReferenceTest;
///import net.balsoftware.parameter.FrequencyTest;
import net.balsoftware.parameter.NonstandardParameterTest;
import net.balsoftware.parameter.ParseDateTest;
import net.balsoftware.parameter.ParseParameterTest;
import net.balsoftware.parameter.ParticipationRoleTest;
//import net.balsoftware.parameter.RRuleErrorTest;
//import net.balsoftware.parameter.RecurrenceRuleParseTest;
//import net.balsoftware.parameter.RecurrenceRuleStreamTest;
import net.balsoftware.parameter.RelationshipTypeTest;
//import net.balsoftware.property.ActionTest;
//import net.balsoftware.property.AttachmentTest;
//import net.balsoftware.property.AttendeeTest;
//import net.balsoftware.property.CategoriesTest;
//import net.balsoftware.property.ClassificationTest;
//import net.balsoftware.property.CommentTest;
//import net.balsoftware.property.ContactTest;
//import net.balsoftware.property.DateTimeCompletedTest;
//import net.balsoftware.property.DateTimeCreatedTest;
//import net.balsoftware.property.DateTimeDueTest;
//import net.balsoftware.property.DateTimeEndTest;
//import net.balsoftware.property.DateTimeStampTest;
//import net.balsoftware.property.DateTimeStartTest;
//import net.balsoftware.property.DescriptionTest;
//import net.balsoftware.property.DurationTest;
//import net.balsoftware.property.ExceptionDatesTest;
//import net.balsoftware.property.FreeBusyTimeTest;
//import net.balsoftware.property.GeneralPropertyTest;
//import net.balsoftware.property.LocationTest;
//import net.balsoftware.property.NonStandardTest;
//import net.balsoftware.property.OrganizerTest;
//import net.balsoftware.property.PriorityTest;
//import net.balsoftware.property.RecurrenceIdTest;
//import net.balsoftware.property.RecurrenceRuleTest;
//import net.balsoftware.property.RecurrencesTest;
//import net.balsoftware.property.RelatedToTest;
//import net.balsoftware.property.RepeatCountTest;
//import net.balsoftware.property.RequestStatusTest;
//import net.balsoftware.property.ResourcesTest;
//import net.balsoftware.property.SequenceTest;
//import net.balsoftware.property.StatusTest;
//import net.balsoftware.property.SummaryTest;
//import net.balsoftware.property.TimeTransparencyTest;
//import net.balsoftware.property.TimeZoneIdentifierTest;
//import net.balsoftware.property.TimeZoneNameTest;
//import net.balsoftware.property.TimeZoneOffsetTest;
//import net.balsoftware.property.TimeZoneURLTest;
//import net.balsoftware.property.TriggerTest;
//import net.balsoftware.property.URLTest;
//import net.balsoftware.property.UniqueIdentifierTest;
//import net.balsoftware.property.calendar.MethodTest;

@RunWith(Suite.class)
@SuiteClasses({ 
        
        // misc tests
//        FoldingAndUnfoldingTest.class,
//        OrdererTest.class,
        
        // iTIP tests
//        CancelRecurrenceTest.class,
//        ComboMessageTest.class,
//        HandleRecurrencesTest.class,
//        RequestTest.class,
//        SimpleCancelTest.class,
//        SimplePublishTest.class,
//        WholeDayTest.class,
    
        // calendar tests
//        CalendarScaleTest.class,
//        CopyCalendarTest.class,
//        GeneralCalendarTest.class,
//        MethodTest.class,
//        ParseCalendarTest.class,
//        ReadICSFileTest.class,
//        VCalendarRecurrenceIDTest.class,
                
        //component tests
//        BaseTest.class,
//        CopyComponentTest.class,
//        DateTimeEndTest.class,
//        DaylightSavingsTimeTest.class,
//        DisplayableTest.class,
//        EqualsTest.class,
//        GeneralComponentTest.class,
//        LocatableTest.class,
//        ParseComponentTest.class,
//        PrimaryTest.class,
//        PersonalTest.class,
//        RepeatableTest.class,
//        ScheduleConflictTest.class,
//        StandardOrDaylightTimeTest.class,
//        VAlarmTest.class,
//        VEventTest.class,
//        VFreeBusyTest.class,
//        VJournalTest.class,
//        VTimeZoneTest.class,
//        VTodoTest.class,
       
       // property tests
//        ActionTest.class,
//        AttachmentTest.class,
//        AttendeeTest.class,
//        CategoriesTest.class,
//        ClassificationTest.class,
//        CommentTest.class,
//        ContactTest.class,
//        DateTimeCompletedTest.class,
//        DateTimeCreatedTest.class,
//        DateTimeDueTest.class,
//        DateTimeEndTest.class,
//        DateTimeStampTest.class,
//        DateTimeStartTest.class,
//        DescriptionTest.class,
//        DurationTest.class,
//        ExceptionDatesTest.class,
//        FreeBusyTimeTest.class,
//        GeneralPropertyTest.class,
//        LocationTest.class,
//        NonStandardTest.class,
//        OrganizerTest.class,
//        PriorityTest.class,
//        RecurrenceIdTest.class,
//        RecurrenceRuleTest.class,
//        RecurrenceRuleParseTest.class,
//        RecurrencesTest.class,
//        RelatedToTest.class,
//        RepeatCountTest.class,
//        RequestStatusTest.class,
//        ResourcesTest.class,
//        SequenceTest.class,
//        StatusTest.class,
//        SummaryTest.class,
//        TimeTransparencyTest.class,
//        TimeZoneIdentifierTest.class,
//        TimeZoneNameTest.class,
//        TimeZoneOffsetTest.class,
//        TimeZoneURLTest.class,
//        TriggerTest.class,
//        UniqueIdentifierTest.class,
//        URLTest.class,
        
        // parameter tests
        AlternateTextRepresentationTest.class,
        CommonNameTest.class,
        DelegateesTest.class,
        DirectoryEntryReferenceTest.class,
        NonstandardParameterTest.class,
        ParseDateTest.class,
        ParseParameterTest.class,
        ParticipationRoleTest.class,
        RelationshipTypeTest.class,
        
        // Recurrence Rule tests
//        RecurrenceRuleParseTest.class,
//        RecurrenceRuleStreamTest.class,
//        RRuleErrorTest.class,
//        FrequencyTest.class,
//        ByDayTest.class,
//        ByMonthTest.class,
//        ByWeekNumberTest.class,
//        ByYearDayTest.class
              
              })

public class AllTests {

}
