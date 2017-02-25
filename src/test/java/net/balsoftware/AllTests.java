package net.balsoftware;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.balsoftware.component.BaseTest;
import net.balsoftware.component.ComponentStatusTest;
import net.balsoftware.component.CopyComponentTest;
import net.balsoftware.component.DaylightSavingsTimeTest;
import net.balsoftware.component.DescribableTest;
import net.balsoftware.component.DisplayableTest;
import net.balsoftware.component.EqualsTest;
import net.balsoftware.component.ErrorCatchTest;
import net.balsoftware.component.GeneralComponentTest;
import net.balsoftware.component.LocatableTest;
import net.balsoftware.component.ParseComponentTest;
import net.balsoftware.component.PersonalTest;
import net.balsoftware.component.PrimaryTest;
import net.balsoftware.component.RepeatableTest;
import net.balsoftware.component.ScheduleConflictTest;
import net.balsoftware.component.StandardOrDaylightTimeTest;
import net.balsoftware.component.VAlarmTest;
import net.balsoftware.component.VEventTest;
import net.balsoftware.component.VFreeBusyTest;
import net.balsoftware.component.VJournalTest;
import net.balsoftware.component.VTimeZoneTest;
import net.balsoftware.component.VTodoTest;
import net.balsoftware.parameter.AlternateTextRepresentationTest;
import net.balsoftware.parameter.CommonNameTest;
import net.balsoftware.parameter.DelegateesTest;
import net.balsoftware.parameter.DirectoryEntryReferenceTest;
import net.balsoftware.parameter.NonstandardParameterTest;
import net.balsoftware.parameter.ParseDateTest;
import net.balsoftware.parameter.ParseParameterTest;
import net.balsoftware.parameter.ParticipationRoleTest;
import net.balsoftware.parameter.RelationshipTypeTest;
import net.balsoftware.parameter.rrule.ByDayTest;
import net.balsoftware.parameter.rrule.ByHourTest;
import net.balsoftware.parameter.rrule.ByMinuteTest;
import net.balsoftware.parameter.rrule.ByMonthDayTest;
import net.balsoftware.parameter.rrule.ByMonthTest;
import net.balsoftware.parameter.rrule.BySecondTest;
import net.balsoftware.parameter.rrule.BySetPositionTest;
import net.balsoftware.parameter.rrule.ByWeekNumberTest;
import net.balsoftware.parameter.rrule.ByYearDayTest;
import net.balsoftware.parameter.rrule.FrequencyTest;
import net.balsoftware.parameter.rrule.IntervalTest;
import net.balsoftware.parameter.rrule.RRuleErrorTest;
import net.balsoftware.parameter.rrule.RecurrenceRuleParseTest;
import net.balsoftware.parameter.rrule.RecurrenceRuleStreamTest;
import net.balsoftware.property.calendar.MethodTest;
import net.balsoftware.property.component.ActionTest;
import net.balsoftware.property.component.AttachmentTest;
import net.balsoftware.property.component.AttendeeTest;
import net.balsoftware.property.component.CategoriesTest;
import net.balsoftware.property.component.ClassificationTest;
import net.balsoftware.property.component.CommentTest;
import net.balsoftware.property.component.ContactTest;
import net.balsoftware.property.component.DateTimeCompletedTest;
import net.balsoftware.property.component.DateTimeCreatedTest;
import net.balsoftware.property.component.DateTimeDueTest;
import net.balsoftware.property.component.DateTimeEndTest;
import net.balsoftware.property.component.DateTimeStampTest;
import net.balsoftware.property.component.DateTimeStartTest;
import net.balsoftware.property.component.DescriptionTest;
import net.balsoftware.property.component.DurationTest;
import net.balsoftware.property.component.ExceptionDatesTest;
import net.balsoftware.property.component.FreeBusyTimeTest;
import net.balsoftware.property.component.GeneralPropertyTest;
import net.balsoftware.property.component.LastModifiedTest;
import net.balsoftware.property.component.LocationTest;
import net.balsoftware.property.component.NonStandardTest;
import net.balsoftware.property.component.OrganizerTest;
import net.balsoftware.property.component.PriorityTest;
import net.balsoftware.property.component.RecurrenceIdTest;
import net.balsoftware.property.component.RecurrenceRuleTest;
import net.balsoftware.property.component.RecurrencesTest;
import net.balsoftware.property.component.RelatedToTest;
import net.balsoftware.property.component.RepeatCountTest;
import net.balsoftware.property.component.RequestStatusTest;
import net.balsoftware.property.component.ResourcesTest;
import net.balsoftware.property.component.SequenceTest;
import net.balsoftware.property.component.StatusTest;
import net.balsoftware.property.component.SummaryTest;
import net.balsoftware.property.component.TimeTransparencyTest;
import net.balsoftware.property.component.TimeZoneIdentifierTest;
import net.balsoftware.property.component.TimeZoneNameTest;
import net.balsoftware.property.component.TimeZoneOffsetTest;
import net.balsoftware.property.component.TimeZoneURLTest;
import net.balsoftware.property.component.TriggerTest;
import net.balsoftware.property.component.URLTest;
import net.balsoftware.property.component.UniqueIdentifierTest;

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
        BaseTest.class,
        ComponentStatusTest.class,
        CopyComponentTest.class,
        DateTimeEndTest.class,
        DaylightSavingsTimeTest.class,
        DescribableTest.class,
        DisplayableTest.class,
        EqualsTest.class,
        ErrorCatchTest.class,
        GeneralComponentTest.class,
        LastModifiedTest.class,
        LocatableTest.class,
        ParseComponentTest.class,
        PersonalTest.class,
        PrimaryTest.class,
        RepeatableTest.class,
        ScheduleConflictTest.class,
        StandardOrDaylightTimeTest.class,
        VAlarmTest.class,
        VEventTest.class,
        VFreeBusyTest.class,
        VJournalTest.class,
        VTimeZoneTest.class,
        VTodoTest.class,
       
       // property tests
	    ActionTest.class,
	    AttachmentTest.class,
	    AttendeeTest.class,
	    CategoriesTest.class,
	    ClassificationTest.class,
	    CommentTest.class,
	    ContactTest.class,
	    DateTimeCompletedTest.class,
	    DateTimeCreatedTest.class,
	    DateTimeDueTest.class,
	    DateTimeEndTest.class,
	    DateTimeStampTest.class,
	    DateTimeStartTest.class,
	    DescriptionTest.class,
	    DurationTest.class,
	    ExceptionDatesTest.class,
	    FreeBusyTimeTest.class,
	    GeneralPropertyTest.class,
	    LastModifiedTest.class,
	    LocationTest.class,
	    MethodTest.class,
	    NonStandardTest.class,
	    OrganizerTest.class,
	    PriorityTest.class,
	    RecurrenceIdTest.class,
	    RecurrenceRuleTest.class,
	    RecurrencesTest.class,
	    RelatedToTest.class,
	    RepeatCountTest.class,
	    RequestStatusTest.class,
	    ResourcesTest.class,
	    SequenceTest.class,
	    StatusTest.class,
	    SummaryTest.class,
	    TimeTransparencyTest.class,
	    TimeZoneIdentifierTest.class,
	    TimeZoneNameTest.class,
	    TimeZoneOffsetTest.class,
	    TimeZoneURLTest.class,
	    TriggerTest.class,
	    UniqueIdentifierTest.class,
	    URLTest.class,
        
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
        RRuleErrorTest.class,
        ByDayTest.class,
        ByHourTest.class,
        ByMinuteTest.class,
        ByMonthDayTest.class,
        ByMonthTest.class,
        BySecondTest.class,
        BySetPositionTest.class,
        ByWeekNumberTest.class,
        ByYearDayTest.class,
        FrequencyTest.class,
        IntervalTest.class,
        RecurrenceRuleParseTest.class,
        RecurrenceRuleStreamTest.class,
              
              })

public class AllTests {

}
