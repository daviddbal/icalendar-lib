package net.balsoftware;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.balsoftware.component.property.ActionTest;
import net.balsoftware.component.property.AttachmentTest;
import net.balsoftware.component.property.AttendeeTest;
import net.balsoftware.component.property.CategoriesTest;
import net.balsoftware.component.property.ClassificationTest;
import net.balsoftware.component.property.CommentTest;
import net.balsoftware.component.property.ContactTest;
import net.balsoftware.component.property.DateTimeCompletedTest;
import net.balsoftware.component.property.DateTimeCreatedTest;
import net.balsoftware.component.property.DateTimeDueTest;
import net.balsoftware.component.property.DateTimeEndTest;
import net.balsoftware.component.property.DateTimeStampTest;
import net.balsoftware.component.property.DateTimeStartTest;
import net.balsoftware.component.property.DescriptionTest;
import net.balsoftware.component.property.DurationTest;
import net.balsoftware.component.property.ExceptionDatesTest;
import net.balsoftware.component.property.FreeBusyTimeTest;
import net.balsoftware.component.property.GeneralPropertyTest;
import net.balsoftware.component.property.LocationTest;
import net.balsoftware.component.property.NonStandardTest;
import net.balsoftware.component.property.OrganizerTest;
import net.balsoftware.component.property.PriorityTest;
import net.balsoftware.component.property.RecurrenceIdTest;
import net.balsoftware.component.property.RecurrenceRuleTest;
import net.balsoftware.component.property.RecurrencesTest;
import net.balsoftware.component.property.RelatedToTest;
import net.balsoftware.component.property.RepeatCountTest;
import net.balsoftware.component.property.RequestStatusTest;
import net.balsoftware.component.property.ResourcesTest;
import net.balsoftware.component.property.SequenceTest;
import net.balsoftware.component.property.StatusTest;
import net.balsoftware.component.property.SummaryTest;
import net.balsoftware.component.property.TimeTransparencyTest;
import net.balsoftware.component.property.TimeZoneIdentifierTest;
import net.balsoftware.component.property.TimeZoneNameTest;
import net.balsoftware.component.property.TimeZoneOffsetTest;
import net.balsoftware.component.property.TimeZoneURLTest;
import net.balsoftware.component.property.TriggerTest;
import net.balsoftware.component.property.URLTest;
import net.balsoftware.component.property.UniqueIdentifierTest;
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
	    LocationTest.class,
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
