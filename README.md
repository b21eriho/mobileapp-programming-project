# Report
The app was decided to be usable as a calendar for a fictional world of my own creation. Specifically for one of the world months.
This month has 25 days and notably the amount of daylight goes from 12 hours on the first of the month and on the last and 25th the sun does
not set at all. Thus the app should display the time of sunset and sunrise. Additionally some days have specific names that it is called in
conversation instead of the days date. Further there are events that occur on some days and these should also be displayed in the app.
### Sketch/design
The design of the app was naturally made to fill these decided requirements of the app as well as the given requirements from the task.
The given requirements on the design were, at large, to show multiple pieces of information on every entry in a list of many of any thing.
This thing in this case is naturally a day. Further there should be a type of filter that can be activated to only show some of the entries.
Further there should be a screen that displays information about for who the app is useful. Lastly every list-item should be clickable at which
point a new screen with more information of the said item should be shown.

In this case the design was designed as follows (See also sketch below). On the main screen there should be a list of days where each list-item
should show the name of the day, the hour at which the sun rises as well as the amount of events on that day. If the said day does not have a
unique day-name the day should say which weekday and date the day is. Further on the main screen there should, in the top toolbar, a switch
for activating or deactivating a filter which shows only the days that have at least one event. On the toolbar there should also be an icon
of buttons that when clicked opens a window from which it can be chosen to show the about-screen or to reload the list from the webservice.

The about-screen should have the header "about" and should then show a brief text explaining that the app is indeed only interesting for
a person with interest in this particular month of this particular world and it's events.

The detailed screen that will appear when a list-item is clicked should prominently show the name of the day, following the same conventions
discussed earlier. The date of the day should also be displayed as well as the full time that the sun sets and rises. Lastly it should show a
list of the names of all the events that occur on that specific day.
![](MobProg%20sketch.png)
### Webservice - JSON
The JSON-data used in the course was entered according to the course standard but naturally the different fields were used for things relevant
to this app. ID and type (often called login in the course material) were said not to be needed to be explained and thus they will not be.

Name was used to store the name of the day if it had a unique name, otherwise the field would be left empty. This was quite natural seeing as
the name was similar to the usage and thus could keep its name in deserialized form.
Company was used to store the time of sunset, this being a string could have become an issue but seeing as the app would not need to perform any
math on this data a string was not an issue. Location was used to store the time of sunrise, this too is a string but is not an issue for the
same reason as above. Both these rows used the format hour:minute to store the time. Size being an integer was naturally used to store the date
of the day. Auxdata was used to store an array of string with the names of the events on that given day. Notable is the fact that if the
deserialization (in this case gson) tries to convert an empty field to an array it will cause an error. Due to this even the days without
events has the string "[]" as to instead be converted to an empty array. Remaining fields were not used.

Below the data for the first day of the month is exemplified in prettyfied JSON.
```
{
	"ID": "b21eriho_d1",
	"name": "Spring solstice day",
	"type": "b21eriho",
	"company": "18:00",
	"location": "6:00",
	"category": "",
	"size": 1,
	"cost": 0,
	"auxdata": ["Spring-start feast"]
}
```
### Implementation

### Implementation VG

### Reflection