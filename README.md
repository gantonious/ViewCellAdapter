# ViewCellAdapter [![CircleCI](https://circleci.com/gh/gantonious/ViewCellAdapter.svg?style=svg)](https://circleci.com/gh/gantonious/ViewCellAdapter)

A RecyclerView adapter that can handle holding hetrogeneuous data types, and provides the ability to set up sections in your adapter. View the [sample-app](https://github.com/gantonious/ViewCellAdapter/tree/dev/sample-app/src/main/java/ca/antonious/sample) to see different usage scenarios.

## Features

- Lists with different view types ([HeterogeneousSample](https://github.com/gantonious/ViewCellAdapter/blob/dev/sample-app/src/main/java/ca/antonious/sample/HeterogeneousSample.java))
- Easy event handling ([SampleModelViewCell](https://github.com/gantonious/ViewCellAdapter/blob/dev/sample-app/src/main/java/ca/antonious/sample/viewcells/SampleModelViewCell.java#L42), [BasicHomogeneousSectionSample](https://github.com/gantonious/ViewCellAdapter/blob/dev/sample-app/src/main/java/ca/antonious/sample/BasicHomogeneousSectionSample.java#L38))
- Multiple independent sections ([MultipleSectionsSample](https://github.com/gantonious/ViewCellAdapter/blob/dev/sample-app/src/main/java/ca/antonious/sample/MultipleSectionsSample.java))
- Easy to extend
- Headers ([MultipleSectionsSample](https://github.com/gantonious/ViewCellAdapter/blob/dev/sample-app/src/main/java/ca/antonious/sample/MultipleSectionsSample.java#L37))
- Footers
- Empty state handling ([HeterogeneousSample](https://github.com/gantonious/ViewCellAdapter/blob/dev/sample-app/src/main/java/ca/antonious/sample/HeterogeneousSample.java#L39 ))
- Easy to compose section decorators ([ComplexDecoratorCompositionSample](https://github.com/gantonious/ViewCellAdapter/blob/dev/sample-app/src/main/java/ca/antonious/sample/ComplexDecoratorCompositionSample.java#L41))
- Filtering ([FilteredHomogeneousSectionSample](https://github.com/gantonious/ViewCellAdapter/blob/dev/sample-app/src/main/java/ca/antonious/sample/FilteredHomogeneousSectionSample.java#L72))
- Sorting ([SortedHomogeneousSectionSample](https://github.com/gantonious/ViewCellAdapter/blob/dev/sample-app/src/main/java/ca/antonious/sample/SortedHomogeneousSectionSample.java#L36))
- Dynamic span sizes ([SampleModelViewCell](https://github.com/gantonious/ViewCellAdapter/blob/dev/sample-app/src/main/java/ca/antonious/sample/viewcells/SampleModelViewCell.java#L33), [GridLayoutSample](https://github.com/gantonious/ViewCellAdapter/blob/dev/sample-app/src/main/java/ca/antonious/sample/GridLayoutSample.java#L32))
- Selection ([SelectionSample](https://github.com/gantonious/ViewCellAdapter/blob/dev/sample-app/src/main/java/ca/antonious/sample/SelectionSample.java))

## Creating a ViewCell

When you want to bind an item to a ViewCellAdapter you need a model to bind, a layout to bind to, and a ViewCell to handle the binding logic.

### Define a Model

```java
public class Task {
    public final String name;
    public final int timesCompleted;

    public Task(String name, int numCompletions) {
        this.name = name;
        this.timesCompleted = numCompletions;
    }
}
```

### Define a Layout

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp">

    <TextView
        android:id="@+id/task_title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

    <TextView
        android:id="@+id/task_num_completions"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"/>

</LinearLayout>
```

### Define a ViewCell

```java
public class TaskViewCell extends GenericViewCell<TaskViewCell.TaskViewHolder, Task> {

    public TaskViewCell(Task model) {
        super(model);
    }

    @Override
    public int getLayoutId() {
        return R.layout.task_list_item;
    }

    @Override
    public void bindViewCell(TaskViewHolder viewHolder) {
        Task task = getModel();

        viewHolder.setTaskName(task.name);
        viewHolder.setNumberOfCompletions(task.timesCompleted);
    }

    public static class TaskViewHolder extends BaseViewHolder {
        private TextView taskNameTextView;
        private TextView numberOfCompletionsTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            taskNameTextView = (TextView) itemView.findViewById(R.id.task_title);
            numberOfCompletionsTextView = (TextView) itemView.findViewById(R.id.task_num_completions);
        }

        public void setTaskName(String taskName) {
            taskNameTextView.setText(taskName);
        }

        public void setNumberOfCompletions(int numCompletions) {
            String numberOfCompletions = String.valueOf(numCompletions);
            numberOfCompletionsTextView.setText(numberOfCompletions);
        }
    }
}
```

## Using the Adapter

The `ViewCellAdapter` takes in a list of sections. You can then update each section independently to change what is being rendered.

### Using a HomogeneousSection

The simplest way to get started is to use a `HomogeneousSection`. A `HomogeneousSection` assumes all view cells in the section are binding the same data type.

```java
HomogeneousSection<Task, TaskViewCell> todaysTasksSection = new HomogeneousSection<>(Task.class, TaskViewCell.class);
HomogeneousSection<Task, TaskViewCell> olderTasksSection = new HomogeneousSection<>(Task.class, TaskViewCell.class);

ViewCellAdapter viewCellAdapter = new ViewCellAdapter();
viewCellAdapter.addSection(todaysTasksSection);
viewCellAdapter.addSection(olderTasksSection);

recyclerView.setAdapter(viewCellAdapter);
```

Then each section can be updated independently

```java
List<Task> todaysTasks = getTodaysTasks();
todaysTasksSection.addAll(todaysTasks);

List<Task> olderTasks = getOlderTasks();
olderTasksSection.addAll(olderTasks);
```

### Using a Section

A `Section` does not assume all of it's viewcells are the same type. This allows it to be populated with different view cells that don't share the same view type.

```java
Section heterogeneousSection = new Section();

ViewCellAdapter viewCellAdapter = new ViewCellAdapter();
viewCellAdapter.addSection(heterogeneousSection);

recyclerView.setAdapter(viewCellAdapter);
```

A `Section` can be populated by doing the following (notice the extra level of indirection required to convert the models into viewcells)

```java
Task importantTask = new Task("Important Task", 0);
Task normalTask = new Task("Normal Task", 0);

heterogeneousSection.add(new ImportantTaskViewCell(importantTask));
heterogeneousSection.add(new TaskViewCell(normalTask));
```

### Decorating Sections

It's common to want to add a header or a footer to a list of items. Rather than manually inserting a viewcell at the beginning or end of a section, a `SectionDecorator` can be used to decorate a section with a header or footer.

```java
HomogeneousSection<Task, TaskViewCell> todaysTasksSection = new HomogeneousSection<>(Task.class, TaskViewCell.class);

HeaderSectionDecorator todaysTasksWithHeader = new HeaderSectionDecorator(todaysTasksSection, new HeaderViewCell("Today's Tasks"));
todaysTasksWithHeader.setShowHeaderIfEmpty(false);

ViewCellAdapter viewCellAdapter = new ViewCellAdapter();
viewCellAdapter.addSection(todaysTasksWithHeader);

recyclerView.setAdapter(viewCellAdapter);
```

Since a `SectionDecorator` is a section, you can decorate a decorator to construct complex list setups with ease. The following example applies a header and an empty view to a single section.

```java
HomogeneousSection<Task, TaskViewCell> todaysTasksSection = new HomogeneousSection<>(Task.class, TaskViewCell.class);

HeaderSectionDecorator todaysTasksWithHeader = new HeaderSectionDecorator(todaysTasksSection, new HeaderViewCell("Today's Tasks"));
todaysTasksWithHeader.setShowHeaderIfEmpty(false);

EmptySectionDecorator todaysTasksWithHeaderAndEmptyView = new EmptySectionDecorator(todaysTasksWithHeader, new EmptyViewCell("You have no tasks to do today!"));

ViewCellAdapter viewCellAdapter = new ViewCellAdapter();
viewCellAdapter.addSection(todaysTasksWithHeaderAndEmptyView);

recyclerView.setAdapter(viewCellAdapter);
```

### Using Section Builders

When you need to build a more complex adapter, `SectionBuilder` provides a clean declarative API to build your adapter. The following example shows how to build the same setup described in the last example using the `SectionBuilder` API.

```java
HomogeneousSection<Task, TaskViewCell> todaysTasksSection = new HomogeneousSection<>(Task.class, TaskViewCell.class);

ViewCellAdapter adapter = ViewCellAdapter.create()
    .section(
        SectionBuilder.wrap(todaysTasksSection)
            .header(new HeaderViewCell("Today's Tasks"))
            .hideHeaderIfEmpty()
            .showIfEmpty(new EmptyViewCell("You have no tasks to do today!"))
    )
    .build();
```

### Using your old adapters

If you have a legacy adapter that is not easy to convert to this library's API you can wrap it in an `AdapterWrapperSection`. This allows you to insert your old adapter as a section in a `ViewCellAdapter`. It also lets you decorate it using any `SectionDecorator`.

```java
RecyclerView.Adapter legacyTasksAdapter = ...;
AbstractSection tasksSection = new AdapterWrapperSection<>(legacyTasksAdapter);

// integrate this section with the ViewCellAdapter API
```

## Handling ViewHolder Events

Often times an event can occur in a view holder that you may want to handle in the parent activity/fragment. This can be done by using the `@BindListener` annotation in the viewcell.

### Step 1: Define an event handler interface

```java
public interface OnTaskClickListener {
    void onTaskClicked(Task task);
}
```

### Step 2: Bind the interface to the view holder inside the ViewCell

```java
@BindListener
public void bindOnTaskClick(TaskViewHolder viewHolder, OnTaskClickListener onTaskClickListener) {
    viewHolder.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onTaskClickListener.onTaskClicked(getModel());
        }
    });
}
```

### Step 3: Handle event in activity/fragment

```java
viewCellAdapter.addListener(new TaskViewCell.OnTaskClickListener() {
    @Override
    public void onTaskClicked(Task task) {
        // handle event
    }
});
```

## Download

```groovy
dependencies {
    compile 'ca.antonious:viewcelladapter:2.3.0'
    annotationProcessor 'ca.antonious:viewcelladapter-compiler:2.3.0'
}
```

## License

```
MIT License

Copyright (c) 2016 George Antonious

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```