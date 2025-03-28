# Getting Started
This is a fairly straightforward project that does not require any initial setup.  Simply build and run the code through Android Studio.

# Potential Improvements
- Switch to using an expandable list to allow for collapsing of parent groups.
- Inject dispatchers in repository and use cases.
- Convert XMLs to Compose.
- Add unit tests for the view model, use case, and repository
- Add extra visual flair to the list.
- Consider moving conversion of `Item`s to `ParentListItemState`s/`ChildListItemState`s to `Dispatchers.Default` if the size of the list is too large.  (Having a paging API for larger lists would be even better, although that is not something that the Android app has control over.)
- Convert the basic progress indicator to something more advanced, such as a "skeleton screen", when data is being loaded.
- Add a local data source, such as a Room database, to persist data and to allow offline use of the app.
- Add a way for the user to retry the data fetch if the first attempt fails.