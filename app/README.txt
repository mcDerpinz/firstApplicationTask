I started by setting up a RecyclerView to display the base set of data, then added a PopupWindow
to display the rest of the data for the given clicked user.  This is all handled inside of the MyAdapter.kt
file as it is all related and tied together.

I went with minimal frills for setting up the UI.  Added a rounded edge background for the RecyclerView items
and an additional edge outline on the popup window to help visually separate it from the RecyclerView behind it.
Added padding at the end of the list to allow the user to scroll past the list and have blank space for the popup
window to display in.

For parsing the JSON file, I went ahead and set up a fallback value for each item to prevent any issues with
oddball/nonstandard data in each field.  Each field will default to an empty string if there is a problem parsing it.
I set up the parser as a function inside of a BeneficiariesParser object.  Something to note, all the parsing is
happening on the main thread here.  Depending on the situation, I could see moving this into a coroutine context to
prevent blocking the main thread if we were parsing potentially large files that could hold up the UI.  For this
program, I went with a simple setup.

Added the Beneficiaries.json file to the assets folder
App/src/main/assets/Beneficiaries.json
