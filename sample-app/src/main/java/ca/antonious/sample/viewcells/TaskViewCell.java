package ca.antonious.sample.viewcells;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.antonious.sample.R;
import ca.antonious.sample.models.Task;
import ca.antonious.viewcelladapter.BaseViewHolder;
import ca.antonious.viewcelladapter.annotations.BindListener;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;

/**
 * Created by George on 2016-11-17.
 */

public class TaskViewCell extends GenericViewCell<TaskViewCell.TaskViewHolder, Task> {

    public TaskViewCell(Task model) {
        super(model);
    }

    @Override
    public int getLayoutId() {
        return R.layout.task_list_item;
    }

    @Override
    public void bindViewCell(TaskViewHolder taskViewHolder) {
        Task task = getModel();

        taskViewHolder.setTaskName(task.name);
        taskViewHolder.setNumberOfCompletions(task.timesCompleted);
    }

    @BindListener
    public void bindOnClickListener(TaskViewHolder taskViewHolder, final OnTaskClickListener onTaskClickListener) {
        taskViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTaskClickListener.onTaskClicked(getModel());
            }
        });
    }

    @BindListener
    public void bindOnCompleteListener(TaskViewHolder taskViewHolder, final OnTaskCompletedListener onTaskCompletedListener) {
        taskViewHolder.setOnButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTaskCompletedListener.onTaskCompleted(getModel());
            }
        });
    }

    public interface OnTaskClickListener {
        void onTaskClicked(Task task);
    }

    public interface OnTaskCompletedListener {
        void onTaskCompleted(Task task);
    }
    

    public static class TaskViewHolder extends BaseViewHolder {
        private TextView taskNameTextView;
        private TextView numberOfCompletionsTextView;
        private Button completeButton;

        public TaskViewHolder(View itemView) {
            super(itemView);

            taskNameTextView = (TextView) itemView.findViewById(R.id.task_title);
            numberOfCompletionsTextView = (TextView) itemView.findViewById(R.id.task_num_compeltions);
            completeButton = (Button) itemView.findViewById(R.id.task_complete_button);
        }

        public void setTaskName(String taskName) {
            taskNameTextView.setText(taskName);
        }

        public void setNumberOfCompletions(int numCompletions) {
            numberOfCompletionsTextView.setText(String.valueOf(numCompletions));
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }

        public void setOnButtonClickListener(View.OnClickListener onButtonClickListener) {
            completeButton.setOnClickListener(onButtonClickListener);
        }
    }
}
