# Getting Started
This app was built on, and is confirmed to run on, Android Studio Meerkat | 2024.3.1 Patch 1, with:
- Android Gradle Plugin (AGP) 8.9.1
- Android 15.0 (API Level 35)
- Android SDK Build-Tools 35.0.0

With that configuration, building and running the project should work immediately without any modifications.

# Potential Improvements
- Extra logic was added to sort the items within each list grouping by their numerical ordering rather than by their alphabetical ordering (i.e., to ensure "Item 2" is shown before "Item 11" rather than vice versa), but if that is not preferred, that could be rolled back to the simpler alphabetical ordering.
- Switch to using an expandable list to allow for collapsing of parent groups.  Or, as an alternative, we could introduce "sticky" headers to keep the parent groups list items visible as the user scrolls.
- Inject dispatchers in repository and use cases.
- Convert XMLs to Compose.
- Add unit tests for the view model, use case, and repository
- Add extra visual flair to the list.
- Consider moving conversion of `Item`s to `ParentListItemState`s/`ChildListItemState`s to `Dispatchers.Default` if the size of the list is too large.  (Having a paging API for larger lists would be even better, although that is not something that the Android app has control over.)
- Convert the basic progress indicator to something more advanced, such as a "skeleton screen", when data is being loaded.
- Add a local data source, such as a Room database, to cache data and to allow offline use of the app.
- Consider avoiding fetching data again if we already have cached data, whether it is within a local data source, or within our view model (as may be the case after a configuration change).
- Add a way for the user to retry the data fetch if the first attempt fails.