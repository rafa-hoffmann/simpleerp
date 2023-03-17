# simpleerp

App that can register sales and products within a sale. Keeps the list of sales and products in a
local database and let's the user view the total value of sales and order.

# setup

Just use Android Studio [latest version](https://developer.android.com/studio) and build the project
in the `demoDebug` variant, running the `app` configuration.

# preview

<video width="320" autoplay muted>
  <source src="preview/simpleerppreview.webm" type="video/webm">
Your browser does not support the video tag.
</video>

# architecture

| Module         | Responsibility                                                                                                                                     |
|----------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| :app           | Boots the app, sets theme and load feature modules                                                                                                 |
| :build-logic   | Sets build plugins, flavors to use in the app modules                                                                                              |
| :core:common   | Utility functions that sets up coroutine dispatches, converts flows to results                                                                     |
| :core:data     | Repository logic, which calls the network and/or database logic and emits flows of the relevant results                                            |
| :core:database | Database containing logic to save and update the user sales, using Room                                                                            |
| :core:model    | Models used in the UI layer                                                                                                                        |
| :core:testing  | Not yet used                                                                                                                                       |  
| :feature:sales | Feature logic, containing the UI layer. Reaches the `core:data` repositories, loads data and updates it using `ViewModels`, `Fragments` and `Flow` |  

Best practices based on `nowinandroid` project, which follows
the [official architecture guidance](https://developer.android.com/topic/architecture)