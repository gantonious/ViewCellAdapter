# ViewCellAdapter [![CircleCI](https://circleci.com/gh/gantonious/ViewCellAdapter.svg?style=svg)](https://circleci.com/gh/gantonious/ViewCellAdapter)

A RecyclerView adapter that can handle holding hetrogeneuous data types, and provides the ability to set up sections in your adapter. View the [sample-app](https://github.com/gantonious/ViewCellAdapter/tree/dev/sample-app/src/main/java/ca/antonious/sample) to see different usage scenarios.

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

The `ViewCellAdapter` takes in a list of sections. You can then provide each section with a set of view cells.

### Using sections

The simplest way to get started is to use a `HomogeneousSection`. A `HomogeneousSection` assumes all view cells in the section are binding the same data type.

```java
ViewCellAdapter viewCellAdapter = new ViewCellAdapter();

HomogeneousSection<Task, TaskViewCell> todaysTasksSection = new HomogeneousSection<>(Task.class, TaskViewCell.class);
HomogeneousSection<Task, TaskViewCell> olderTasksSection = new HomogeneousSection<>(Task.class, TaskViewCell.class);

viewCellAdapter.addSection(todaysTasksSection)
               .addSection(olderTasksSection);
```

Then each section can be updated independently

```java
List<Task> todaysTasks = getTodaysTasks();
List<Task> olderTasks = getOlderTasks();

todaysTasksSection.addAll(todaysTasks)
olderTasksSection.addAll(olderTasks);

viewCellAdapter.notifyDataSetChanged();
```

### Using Section Builders

When you need to build a more complex adapter, `SectionBuilder` provides a clean declarative API to build your adapter. The following example shows how to build an adapter with a header and an empty view.

```java
HomogeneousSection<Task, TaskViewCell> todaysTasksSection = new HomogeneousSection<>(Task.class, TaskViewCell.class);

ViewCellAdapter adapter = new ViewCellAdapter()
    .addSection(
        SectionBuilder.wrap(todaysTasksSection)
            .wrapWithHeader(new HeaderViewCell("Today's Tasks"))
            .hideHeaderIfEmpty()
            .wrapWithEmptyView(new EmptyViewCell("You have no tasks to do today!"))
            .build()
    )
    .addListener(new TaskViewCell.OnTaskClickListener() {
        @Override
        public void onTaskClicked(Task task) {
            showSnackbar(task.name);
        }
    });
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

```
dependencies {
    compile 'ca.antonious:viewcelladapter:2.0.0'
    annotationProcessor 'ca.antonious:viewcelladapter-compiler:2.0.0'
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