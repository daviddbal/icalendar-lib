package net.balsoftware.icalendar;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.balsoftware.icalendar.calendar.CalendarScaleTest;
import net.balsoftware.icalendar.calendar.CopyCalendarTest;
import net.balsoftware.icalendar.calendar.GeneralCalendarTest;
import net.balsoftware.icalendar.calendar.ParseCalendarTest;
import net.balsoftware.icalendar.calendar.ReadICSFileTest;
import net.balsoftware.icalendar.calendar.RecurrenceIDParentValidTest;
import net.balsoftware.icalendar.calendar.VCalendarRecurrenceIDTest;
import net.balsoftware.icalendar.component.BaseTest;
import net.balsoftware.icalendar.component.ComponentStatusTest;
import net.balsoftware.icalendar.component.CopyComponentTest;
import net.balsoftware.icalendar.component.DaylightSavingsTimeTest;
import net.balsoftware.icalendar.component.DescribableTest;
import net.balsoftware.icalendar.component.DisplayableTest;
import net.balsoftware.icalendar.component.EqualsTest;
import net.balsoftware.icalendar.component.ErrorCatchTest;
import net.balsoftware.icalendar.component.GeneralComponentTest;
import net.balsoftware.icalendar.component.LocatableTest;
import net.balsoftware.icalendar.component.ParseComponentTest;
import net.balsoftware.icalendar.component.PersonalTest;
import net.balsoftware.icalendar.component.PrimaryTest;
import net.balsoftware.icalendar.component.RepeatableTest;
import net.balsoftware.icalendar.component.ScheduleConflictTest;
import net.balsoftware.icalendar.component.StandardOrDaylightTimeTest;
import net.balsoftware.icalendar.component.VAlarmTest;
import net.balsoftware.icalendar.component.VEventTest;
import net.balsoftware.icalendar.component.VFreeBusyTest;
import net.balsoftware.icalendar.component.VJournalTest;
import net.balsoftware.icalendar.component.VTimeZoneTest;
import net.balsoftware.icalendar.component.VTodoTest;
import net.balsoftware.icalendar.itip.CancelRecurrenceTest;
import net.balsoftware.icalendar.itip.ComboMessageTest;
import net.balsoftware.icalendar.itip.HandleRecurrencesTest;
import net.balsoftware.icalendar.itip.RequestTest;
import net.balsoftware.icalendar.itip.SimpleCancelTest;
import net.balsoftware.icalendar.itip.SimplePublishTest;
import net.balsoftware.icalendar.itip.WholeDayTest;
import net.balsoftware.icalendar.misc.AddAndRemoveChildrenTests;
import net.balsoftware.icalendar.misc.CreateElementsTests;
import net.balsoftware.icalendar.misc.ErrorDetectingTest;
import net.balsoftware.icalendar.misc.FoldingAndUnfoldingTest;
import net.balsoftware.icalendar.misc.MiscICalendarTests;
import net.balsoftware.icalendar.misc.OrdererTest;
import net.balsoftware.icalendar.parameter.AlternateTextRepresentationTest;
import net.balsoftware.icalendar.parameter.CommonNameTest;
import net.balsoftware.icalendar.parameter.DelegateesTest;
import net.balsoftware.icalendar.parameter.DirectoryEntryReferenceTest;
import net.balsoftware.icalendar.parameter.NonstandardParameterTest;
import net.balsoftware.icalendar.parameter.ParseDateTest;
import net.balsoftware.icalendar.parameter.ParseParameterTest;
import net.balsoftware.icalendar.parameter.ParticipationRoleTest;
import net.balsoftware.icalendar.parameter.RelationshipTypeTest;
import net.balsoftware.icalendar.parameter.rrule.ByDayTest;
import net.balsoftware.icalendar.parameter.rrule.ByHourTest;
import net.balsoftware.icalendar.parameter.rrule.ByMinuteTest;
import net.balsoftware.icalendar.parameter.rrule.ByMonthDayTest;
import net.balsoftware.icalendar.parameter.rrule.ByMonthTest;
import net.balsoftware.icalendar.parameter.rrule.BySecondTest;
import net.balsoftware.icalendar.parameter.rrule.BySetPositionTest;
import net.balsoftware.icalendar.parameter.rrule.ByWeekNumberTest;
import net.balsoftware.icalendar.parameter.rrule.ByYearDayTest;
import net.balsoftware.icalendar.parameter.rrule.FrequencyTest;
import net.balsoftware.icalendar.parameter.rrule.IntervalTest;
import net.balsoftware.icalendar.parameter.rrule.RRuleErrorTest;
import net.balsoftware.icalendar.parameter.rrule.RecurrenceRuleParseTest;
import net.balsoftware.icalendar.parameter.rrule.RecurrenceRuleStreamTest;
import net.balsoftware.icalendar.property.calendar.MethodTest;
import net.balsoftware.icalendar.property.component.ActionTest;
import net.balsoftware.icalendar.property.component.AttachmentTest;
import net.balsoftware.icalendar.property.component.AttendeeTest;
import net.balsoftware.icalendar.property.component.CategoriesTest;
import net.balsoftware.icalendar.property.component.ClassificationTest;
import net.balsoftware.icalendar.property.component.CommentTest;
import net.balsoftware.icalendar.property.component.ContactTest;
import net.balsoftware.icalendar.property.component.DateTimeCompletedTest;
import net.balsoftware.icalendar.property.component.DateTimeCreatedTest;
import net.balsoftware.icalendar.property.component.DateTimeDueTest;
import net.balsoftware.icalendar.property.component.DateTimeEndTest;
import net.balsoftware.icalendar.property.component.DateTimeStampTest;
import net.balsoftware.icalendar.property.component.DateTimeStartTest;
import net.balsoftware.icalendar.property.component.DescriptionTest;
import net.balsoftware.icalendar.property.component.DurationTest;
import net.balsoftware.icalendar.property.component.ExceptionDatesTest;
import net.balsoftware.icalendar.property.component.FreeBusyTimeTest;
import net.balsoftware.icalendar.property.component.GeneralPropertyTest;
import net.balsoftware.icalendar.property.component.LastModifiedTest;
import net.balsoftware.icalendar.property.component.LocationTest;
import net.balsoftware.icalendar.property.component.NonStandardTest;
import net.balsoftware.icalendar.property.component.OrganizerTest;
import net.balsoftware.icalendar.property.component.PriorityTest;
import net.balsoftware.icalendar.property.component.RecurrenceIdTest;
import net.balsoftware.icalendar.property.component.RecurrenceRuleTest;
import net.balsoftware.icalendar.property.component.RecurrencesTest;
import net.balsoftware.icalendar.property.component.RelatedToTest;
import net.balsoftware.icalendar.property.component.RepeatCountTest;
import net.balsoftware.icalendar.property.component.RequestStatusTest;
import net.balsoftware.icalendar.property.component.ResourcesTest;
import net.balsoftware.icalendar.property.component.SequenceTest;
import net.balsoftware.icalendar.property.component.StatusTest;
import net.balsoftware.icalendar.property.component.SummaryTest;
import net.balsoftware.icalendar.property.component.TimeTransparencyTest;
import net.balsoftware.icalendar.property.component.TimeZoneIdentifierTest;
import net.balsoftware.icalendar.property.component.TimeZoneNameTest;
import net.balsoftware.icalendar.property.component.TimeZoneOffsetTest;
import net.balsoftware.icalendar.property.component.TimeZoneURLTest;
import net.balsoftware.icalendar.property.component.TriggerTest;
import net.balsoftware.icalendar.property.component.URLTest;
import net.balsoftware.icalendar.property.component.UniqueIdentifierTest;

@RunWith(Suite.class)
@SuiteClasses({ 
        
        // misc tests
		AddAndRemoveChildrenTests.class,
        CreateElementsTests.class,
		ErrorDetectingTest.class,
        FoldingAndUnfoldingTest.class,
        MiscICalendarTests.class,
        OrdererTest.class,
        
        // iTIP tests
        CancelRecurrenceTest.class,
        ComboMessageTest.class,
        HandleRecurrencesTest.class,
        RequestTest.class,
        SimpleCancelTest.class,
        SimplePublishTest.class,
        WholeDayTest.class,
    
        // calendar tests
        CalendarScaleTest.class,
        CopyCalendarTest.class,
        GeneralCalendarTest.class,
        OrdererTest.class,
        ParseCalendarTest.class,
        ReadICSFileTest.class,
        RecurrenceIDParentValidTest.class,
        VCalendarRecurrenceIDTest.class,
                
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
