# Getting Started
This is a fairly straightforward project that does not require any initial setup.  Simply build and run the code through Android Studio.

# Potential Improvements
- Switch to using an expandable list to allow for collapsing of parent groups.
- Add a visual indicator to display while list loads.
- Inject dispatchers in repository and use cases.
- Convert XMLs to Compose.
- Add unit tests for view models, use cases, repositories, and data sources
- Add extra visual flair to the list.
- Consider moving conversion of `Item`s to `ParentListItemState`s/`ChildListItemState`s to `Dispatchers.Default` if the size of the list is too large.  (Having a paging API for larger lists would be even better, although that is not something that the Android app has control over.)
