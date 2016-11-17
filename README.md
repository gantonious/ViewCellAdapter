# ViewCellAdapter

## Download

Note: This library is not available on maven yet, but the dependency will look something like this
```
dependencies {
    compile 'ca.antonious.viewcelladapter:1.0.0'
}
```

## Creating a ViewCell

When you want to bind an item to a ViewCellAdapter you need a, model to bind, a layout to bind to, and a ViewCell to handle the binding logic.

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
        android:id="@+id/task_num_compeltions"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"/>

</LinearLayout>
```

### Define a ViewCell

```java
public class TaskViewCell extends GenericSingleViewCell<TaskViewCell.ViewHolder, Task> {

    public TaskViewCell(Task model) {
        super(model);
    }

    @Override
    public int getLayoutId() {
        return R.layout.task_list_item;
    }

    @Override
    public void bindViewCell(ViewHolder viewHolder) {
        Task task = getModel();

        viewHolder.setTaskName(task.name);
        viewHolder.setNumberOfCompletions(task.timesCompleted);
    }

    public static class ViewHolder extends BaseViewHolder {
        private TextView taskNameTextView;
        private TextView numberOfCompletionsTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            taskNameTextView = (TextView) itemView.findViewById(R.id.task_title);
            numberOfCompletionsTextView = (TextView) itemView.findViewById(R.id.task_num_compeltions);
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