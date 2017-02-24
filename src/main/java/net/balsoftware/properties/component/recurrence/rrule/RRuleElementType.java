package net.balsoftware.properties.component.recurrence.rrule;

import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import net.balsoftware.VChild;
import net.balsoftware.VElement;
import net.balsoftware.parameters.ParameterType;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByDay;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByHour;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByMinute;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByMonth;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByMonthDay;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.BySecond;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.BySetPosition;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByWeekNumber;
import net.balsoftware.properties.component.recurrence.rrule.byxxx.ByYearDay;
import net.balsoftware.utilities.DateTimeUtilities;

public enum RRuleElementType
{
    FREQUENCY ("FREQ", Frequency.class, 0, null) {
        @Override
        public RRuleElement<?> getElement(RecurrenceRuleValue rrule)
        {
            return rrule.getFrequency();
        }

        @Override
        public VChild parse(RecurrenceRuleValue recurrenceRule, String content)
        {
            if (recurrenceRule.getFrequency() == null)
            {
                Frequency child = Frequency.parse(content);
				recurrenceRule.setFrequency(child);
				return child;
            } else
            {
                throw new IllegalArgumentException(toString() + " can only occur once in a calendar component");
            }
        }

//        @Override
//        public void copyElement(RecurrenceRule2 source, RecurrenceRule2 destination)
//        {
//            destination.setFrequency(new Frequency(source.getFrequency()));
//        }

        @Override
        public void copyElement(RRuleElement<?> child, RecurrenceRuleValue destination)
        {
            Frequency childCopy = new Frequency((Frequency) child);
            destination.setFrequency(childCopy);
        }
    },
    INTERVAL ("INTERVAL", Interval.class, 0, null) {
        @Override
        public RRuleElement<?> getElement(RecurrenceRuleValue rrule)
        {
            return rrule.getInterval();
        }

        @Override
        public VChild parse(RecurrenceRuleValue recurrenceRule, String content)
        {
            if (recurrenceRule.getInterval() == null)
            {
            	Interval child = Interval.parse(content);
                recurrenceRule.setInterval(child);
                return child;
            } else
            {
                throw new IllegalArgumentException(toString() + " can only occur once in a calendar component");
            }
        }

//        @Override
//        public void copyElement(RecurrenceRule2 source, RecurrenceRule2 destination)
//        {
//            destination.setInterval(new Interval(source.getInterval()));
//        }

        @Override
        public void copyElement(RRuleElement<?> child, RecurrenceRuleValue destination)
        {
            Interval childCopy = new Interval((Interval) child);
            destination.setInterval(childCopy);
        }
    },
    UNTIL ("UNTIL", Until.class, 0, null) {
        @Override
        public RRuleElement<?> getElement(RecurrenceRuleValue rrule)
        {
            return rrule.getUntil();
        }

        @Override
        public VChild parse(RecurrenceRuleValue recurrenceRule, String content)
        {
            if (recurrenceRule.getUntil() == null)
            {
            	Until child = Until.parse(content);
                recurrenceRule.setUntil(child);
                return child;
            } else
            {
                throw new IllegalArgumentException(toString() + " can only occur once in a calendar component");
            }
        }

//        @Override
//        public void copyElement(RecurrenceRule2 source, RecurrenceRule2 destination)
//        {
//            destination.setUntil(new Until(source.getUntil()));
//        }

        @Override
        public void copyElement(RRuleElement<?> child, RecurrenceRuleValue destination)
        {
            Until childCopy = new Until((Until) child);
            destination.setUntil(childCopy);
        }
    },
    COUNT ("COUNT", Count.class, 0, null) {
        @Override
        public RRuleElement<?> getElement(RecurrenceRuleValue rrule)
        {
            return rrule.getCount();
        }

        @Override
        public VChild parse(RecurrenceRuleValue recurrenceRule, String content)
        {
            if (recurrenceRule.getCount() == null)
            {
            	Count child = Count.parse(content);
                recurrenceRule.setCount(child);
                return child;
            } else
            {
                throw new IllegalArgumentException(toString() + " can only occur once in a calendar component");
            }
        }

//        @Override
//        public void copyElement(RecurrenceRule2 source, RecurrenceRule2 destination)
//        {
//            destination.setCount(new Count(source.getCount()));
//        }

        @Override
        public void copyElement(RRuleElement<?> child, RecurrenceRuleValue destination)
        {
            Count childCopy = new Count((Count) child);
            destination.setCount(childCopy);
        }
    },
    WEEK_START ("WKST", WeekStart.class, 0, null) {
        @Override
        public RRuleElement<?> getElement(RecurrenceRuleValue rrule)
        {
            return rrule.getWeekStart();
        }

        @Override
        public VChild parse(RecurrenceRuleValue recurrenceRule, String content)
        {
            if (recurrenceRule.getWeekStart() == null)
            {
                DayOfWeek dayOfWeek = DateTimeUtilities.dayOfWeekFromAbbreviation(content);
                WeekStart child = new WeekStart(dayOfWeek);
                recurrenceRule.setWeekStart(child);
                return child;
            } else
            {
                throw new IllegalArgumentException(toString() + " can only occur once in a calendar component");
            }
        }

//        @Override
//        public void copyElement(RecurrenceRule2 source, RecurrenceRule2 destination)
//        {
//            destination.setWeekStart(new WeekStart(source.getWeekStart()));
//        }

        @Override
        public void copyElement(RRuleElement<?> child, RecurrenceRuleValue destination)
        {
            WeekStart childCopy = new WeekStart((WeekStart) child);
            destination.setWeekStart(childCopy);
        }
    },
    BY_MONTH ("BYMONTH", ByMonth.class, 100, ChronoUnit.MONTHS) {
        @Override
        public RRuleElement<?> getElement(RecurrenceRuleValue rrule)
        {
            return rrule.lookupByRule(ByMonth.class);
        }

        @Override
        public VChild parse(RecurrenceRuleValue recurrenceRule, String content)
        {
            if (recurrenceRule.lookupByRule(ByMonth.class) == null)
            {
                ByMonth child = ByMonth.parse(content);
				recurrenceRule.getByRules().add(child);
				return child;
            } else
            {
                throw new IllegalArgumentException(toString() + " can only occur once in a calendar component");
            }
        }

//        @Override
//        public void copyElement(RecurrenceRule2 source, RecurrenceRule2 destination)
//        {
//            destination.byRules().add(new ByMonth((ByMonth) source.lookupByRule(ByMonth.class)));
//        }

        @Override
        public void copyElement(RRuleElement<?> child, RecurrenceRuleValue destination)
        {
            ByMonth childCopy = new ByMonth((ByMonth) child);
            destination.getByRules().add(childCopy);
        }
    },
    BY_WEEK_NUMBER ("BYWEEKNO", ByWeekNumber.class, 110, ChronoUnit.DAYS) {
        @Override
        public RRuleElement<?> getElement(RecurrenceRuleValue rrule)
        {
            return rrule.lookupByRule(ByWeekNumber.class);
        }

        @Override
        public VChild parse(RecurrenceRuleValue recurrenceRule, String content)
        {
            if (recurrenceRule.lookupByRule(ByWeekNumber.class) == null)
            {
                ByWeekNumber child = ByWeekNumber.parse(content);
				recurrenceRule.getByRules().add(child);
				return child;
            } else
            {
                throw new IllegalArgumentException(toString() + " can only occur once in a calendar component");
            }
        }

//        @Override
//        public void copyElement(RecurrenceRule2 source, RecurrenceRule2 destination)
//        {
//            destination.byRules().add(new ByWeekNumber((ByWeekNumber) source.lookupByRule(ByWeekNumber.class)));
//        }

        @Override
        public void copyElement(RRuleElement<?> child, RecurrenceRuleValue destination)
        {
            ByWeekNumber childCopy = new ByWeekNumber((ByWeekNumber) child);
            destination.getByRules().add(childCopy);
        }
    },
    BY_YEAR_DAY ("BYYEARDAY", ByYearDay.class, 120, ChronoUnit.DAYS) {
        @Override
        public RRuleElement<?> getElement(RecurrenceRuleValue rrule)
        {
            return rrule.lookupByRule(ByYearDay.class);
        }

        @Override
        public VChild parse(RecurrenceRuleValue recurrenceRule, String content)
        {
            if (recurrenceRule.lookupByRule(ByYearDay.class) == null)
            {
                ByYearDay child = ByYearDay.parse(content);
				recurrenceRule.getByRules().add(child);
				return child;
            } else
            {
                throw new IllegalArgumentException(toString() + " can only occur once in a calendar component");
            }
        }

//        @Override
//        public void copyElement(RecurrenceRule2 source, RecurrenceRule2 destination)
//        {
//            destination.byRules().add(new ByYearDay((ByYearDay) source.lookupByRule(ByYearDay.class)));
//        }

        @Override
        public void copyElement(RRuleElement<?> child, RecurrenceRuleValue destination)
        {
            ByYearDay childCopy = new ByYearDay((ByYearDay) child);
            destination.getByRules().add(childCopy);
        }
    },
    BY_MONTH_DAY ("BYMONTHDAY", ByMonthDay.class, 130, ChronoUnit.DAYS) {
        @Override
        public RRuleElement<?> getElement(RecurrenceRuleValue rrule)
        {
            return rrule.lookupByRule(ByMonthDay.class);
        }

        @Override
        public VChild parse(RecurrenceRuleValue recurrenceRule, String content)
        {
            if (recurrenceRule.lookupByRule(ByMonthDay.class) == null)
            {
                ByMonthDay child = ByMonthDay.parse(content);
				recurrenceRule.getByRules().add(child);
				return child;
            } else
            {
                throw new IllegalArgumentException(toString() + " can only occur once in a calendar component");
            }
        }

//        @Override
//        public void copyElement(RecurrenceRule2 source, RecurrenceRule2 destination)
//        {
//            destination.byRules().add(new ByMonthDay((ByMonthDay) source.lookupByRule(ByMonthDay.class)));
//        }

        @Override
        public void copyElement(RRuleElement<?> child, RecurrenceRuleValue destination)
        {
            ByMonthDay childCopy = new ByMonthDay((ByMonthDay) child);
            destination.getByRules().add(childCopy);
        }
    },
    BY_DAY ("BYDAY", ByDay.class, 140, ChronoUnit.DAYS) {
        @Override
        public RRuleElement<?> getElement(RecurrenceRuleValue rrule)
        {
            return rrule.lookupByRule(ByDay.class);
        }

        @Override
        public VChild parse(RecurrenceRuleValue recurrenceRule, String content)
        {
            if (recurrenceRule.lookupByRule(ByDay.class) == null)
            {
                ByDay child = ByDay.parse(content);
				recurrenceRule.getByRules().add(child);
				return child;
            } else
            {
                throw new IllegalArgumentException(toString() + " can only occur once in a calendar component");
            }
        }

//        @Override
//        public void copyElement(RecurrenceRule2 source, RecurrenceRule2 destination)
//        {
//            destination.byRules().add(new ByDay((ByDay) source.lookupByRule(ByDay.class)));
//        }

        @Override
        public void copyElement(RRuleElement<?> child, RecurrenceRuleValue destination)
        {
            ByDay childCopy = new ByDay((ByDay) child);
            destination.getByRules().add(childCopy);
        }
    },
    BY_HOUR ("BYHOUR", ByHour.class, 150, ChronoUnit.HOURS) {
        @Override
        public RRuleElement<?> getElement(RecurrenceRuleValue rrule)
        {
            return rrule.lookupByRule(ByHour.class);
        }

        @Override
        public VChild parse(RecurrenceRuleValue recurrenceRule, String content)
        {
            if (recurrenceRule.lookupByRule(ByHour.class) == null)
            {
                ByHour child = ByHour.parse(content);
				recurrenceRule.getByRules().add(child);
				return child;
            } else
            {
                throw new IllegalArgumentException(toString() + " can only occur once in a calendar component");
            }
        }

//        @Override
//        public void copyElement(RecurrenceRule2 source, RecurrenceRule2 destination)
//        {
//            destination.byRules().add(new ByWeekNumber((ByWeekNumber) source.lookupByRule(ByWeekNumber.class)));
//        }

        @Override
        public void copyElement(RRuleElement<?> child, RecurrenceRuleValue destination)
        {
            ByHour childCopy = new ByHour((ByHour) child);
            destination.getByRules().add(childCopy);
        }
    },
    BY_MINUTE ("BYMINUTE", ByMinute.class, 160, ChronoUnit.MINUTES) {
        @Override
        public RRuleElement<?> getElement(RecurrenceRuleValue rrule)
        {
            return rrule.lookupByRule(ByMinute.class);
        }

        @Override
        public VChild parse(RecurrenceRuleValue recurrenceRule, String content)
        {
            if (recurrenceRule.lookupByRule(ByMinute.class) == null)
            {
                ByMinute child = ByMinute.parse(content);
				recurrenceRule.getByRules().add(child);
				return child;
            } else
            {
                throw new IllegalArgumentException(toString() + " can only occur once in a calendar component");
            }
        }

//        @Override
//        public void copyElement(RecurrenceRule2 source, RecurrenceRule2 destination)
//        {
//            destination.byRules().add(new ByMinute((ByMinute) source.lookupByRule(ByMinute.class)));
//        }

        @Override
        public void copyElement(RRuleElement<?> child, RecurrenceRuleValue destination)
        {
            ByMinute childCopy = new ByMinute((ByMinute) child);
            destination.getByRules().add(childCopy);
        }
    },
    BY_SECOND ("BYSECOND", BySecond.class, 170, ChronoUnit.SECONDS) {
        @Override
        public RRuleElement<?> getElement(RecurrenceRuleValue rrule)
        {
            return rrule.lookupByRule(BySecond.class);
        }

        @Override
        public VChild parse(RecurrenceRuleValue recurrenceRule, String content)
        {
            if (recurrenceRule.lookupByRule(BySecond.class) == null)
            {
                ByMinute child = BySecond.parse(content);
				recurrenceRule.getByRules().add(child);
				return child;
            } else
            {
                throw new IllegalArgumentException(toString() + " can only occur once in a calendar component");
            }
        }

//        @Override
//        public void copyElement(RecurrenceRule2 source, RecurrenceRule2 destination)
//        {
//            destination.byRules().add(new BySecond((BySecond) source.lookupByRule(BySecond.class)));
//        }

        @Override
        public void copyElement(RRuleElement<?> child, RecurrenceRuleValue destination)
        {
            BySecond childCopy = new BySecond((BySecond) child);
            destination.getByRules().add(childCopy);
        }
    },
    BY_SET_POSITION ("BYSETPOS", BySetPosition.class, 180, null) {
        @Override
        public RRuleElement<?> getElement(RecurrenceRuleValue rrule)
        {
            return rrule.lookupByRule(BySetPosition.class);
        }

        @Override
        public VChild parse(RecurrenceRuleValue recurrenceRule, String content)
        {
            if (recurrenceRule.lookupByRule(BySetPosition.class) == null)
            {
                BySetPosition child = BySetPosition.parse(content);
				recurrenceRule.getByRules().add(child);
				return child;
            } else
            {
                throw new IllegalArgumentException(toString() + " can only occur once in a calendar component");
            }
        }

//        @Override
//        public void copyElement(RecurrenceRule2 source, RecurrenceRule2 destination)
//        {
//            destination.byRules().add(new BySetPosition((BySetPosition) source.lookupByRule(BySetPosition.class)));
//        }

        @Override
        public void copyElement(RRuleElement<?> child, RecurrenceRuleValue destination)
        {
            BySetPosition childCopy = new BySetPosition((BySetPosition) child);
            destination.getByRules().add(childCopy);
        }
    };
    
    // Map to match up name to enum
    private static Map<String, RRuleElementType> enumFromNameMap = makeEnumFromNameMap();
    private static Map<String, RRuleElementType> makeEnumFromNameMap()
    {
        Map<String, RRuleElementType> map = new HashMap<>();
        RRuleElementType[] values = RRuleElementType.values();
        for (int i=0; i<values.length; i++)
        {
            map.put(values[i].toString(), values[i]);
        }
        return map;
    }
    public static RRuleElementType enumFromName(String propertyName)
    {
        return enumFromNameMap.get(propertyName.toUpperCase());
    }
    
    // Map to match up class to enum
    private static Map<Class<? extends RRuleElement<?>>, RRuleElementType> enumFromClassMap = makeEnumFromClassMap();
    private static Map<Class<? extends RRuleElement<?>>, RRuleElementType> makeEnumFromClassMap()
    {
        Map<Class<? extends RRuleElement<?>>, RRuleElementType> map = new HashMap<>();
        RRuleElementType[] values = RRuleElementType.values();
        for (int i=0; i<values.length; i++)
        {
            map.put(values[i].myClass, values[i]);
        }
        return map;
    }
    /** get enum from map */
    public static RRuleElementType enumFromClass(Class<? extends VElement> myClass)
    {
        RRuleElementType p = enumFromClassMap.get(myClass);
        if (p == null)
        {
            throw new IllegalArgumentException(ParameterType.class.getSimpleName() + " does not contain an enum to match the class:" + myClass.getSimpleName());
        }
        return p;
    }
    
    private String name;
    @Override
    public String toString() { return name; }
    
    private Class<? extends RRuleElement<?>> myClass;
    
    private int sortOrder;
    public int sortOrder() { return sortOrder; }
    
    private ChronoUnit chronoUnit;
    public ChronoUnit getChronoUnit() { return chronoUnit; }
    
//    private List<Method> getters;
//    public List<Method> childGetters() { return getters; }

    RRuleElementType(String name, Class<? extends RRuleElement<?>> myClass, int sortOrder, ChronoUnit chronoUnit)
    {
        this.name = name;
        this.myClass = myClass;
        this.sortOrder = sortOrder;
        this.chronoUnit = chronoUnit;
//        this.getters = ICalendarUtilities.collectGetters(myClass);
    }
 
    /*
     * ABSTRACT METHODS
     */
    /** Returns associated Element 
     * @deprecated  not needed due to addition of Orderer, may be deleted */
    @Deprecated
    abstract public RRuleElement<?> getElement(RecurrenceRuleValue rrule);
    
    abstract public VChild parse(RecurrenceRuleValue recurrenceRule, String content);
    
    /** copies the associated element from the source RecurrenceRule to the destination RecurrenceRule */
//    abstract public void copyElement(RecurrenceRule2 source, RecurrenceRule2 destination);
    abstract public void copyElement(RRuleElement<?> child, RecurrenceRuleValue destination);
//    {
//        throw new RuntimeException("not implemented");
//        throw new RuntimeException("not implemented");
//    }
}
