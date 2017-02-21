package net.balsoftware.components;

import java.util.List;

import net.balsoftware.VElement;

/**
 * VTODO
 * To-Do Component
 * RFC 5545, 3.6.2, page 55
 * 
 * A "VTODO" calendar component is a grouping of component
      properties and possibly "VALARM" calendar components that
      represent an action-item or assignment.  For example, it can be

      used to represent an item of work assigned to an individual; such
      as "turn in travel expense today".

      The "VTODO" calendar component cannot be nested within another
      calendar component.  However, "VTODO" calendar components can be
      related to each other or to a "VEVENT" or to a "VJOURNAL" calendar
      component with the "RELATED-TO" property.

      A "VTODO" calendar component without the "DTSTART" and "DUE" (or
      "DURATION") properties specifies a to-do that will be associated
      with each successive calendar date, until it is completed.
      
      Examples:  The following is an example of a "VTODO" calendar
      component that needs to be completed before May 1st, 2007.  On
      midnight May 1st, 2007 this to-do would be considered overdue.

       BEGIN:VTODO
       UID:20070313T123432Z-456553@example.com
       DTSTAMP:20070313T123432Z
       DUE;VALUE=DATE:20070501
       SUMMARY:Submit Quebec Income Tax Return for 2006
       CLASS:CONFIDENTIAL
       CATEGORIES:FAMILY,FINANCE
       STATUS:NEEDS-ACTION
       END:VTODO
 * 
 * @author David Bal
 *
 */
public class VTodo implements VElement
{

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> parseContent(String content) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> errors() {
		// TODO Auto-generated method stub
		return null;
	}    

}
