# ViewCellAdapter

A RecyclerView adapter that can handle holding hetrogeneuous data types, and provides the ability to set up sections in your adatper.

## Setting up the Adapter

### Using sections

```java
ViewCellAdapter viewCellAdapter = new ViewCellAdapter();

SectionViewCell trendingItemsSection = new SectionViewCell();
SectionViewCell oldItemsSection = new SectionViewCell();

viewCellAdapter.add(trendingItemsSection);
viewCellAdapter.add(oldItemsSection);
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

## Download

Note: This library is not available on maven yet, but the dependency will look something like this
```
dependencies {
    compile 'ca.antonious.viewcelladapter:1.0.0'
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