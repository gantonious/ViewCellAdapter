package ca.antonious.sample.viewcells;

import android.view.View;
import android.widget.TextView;

import ca.antonious.sample.R;
import ca.antonious.sample.models.Task;
import ca.antonious.viewcelladapter.BaseViewHolder;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;
import ca.antonious.viewcelladapter.ListenerCollection;

/**
 * Created by George on 2016-11-17.
 */

public class TaskViewCell extends GenericViewCell<TaskViewCell.ViewHolder, Task> {

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

    @Override
    public void bindListeners(ViewHolder viewHolder, ListenerCollection listeners) {
        bindOnClickListener(viewHolder, listeners.getListener(OnTaskClickListener.class));
    }

    private void bindOnClickListener(ViewHolder viewHolder, final OnTaskClickListener onTaskClickListener) {
        if (onTaskClickListener != null) {
            viewHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTaskClickListener.onTaskClicked(getModel());
                }
            });
        }
    }

    public interface OnTaskClickListener {
        void onTaskClicked(Task task);
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
            numberOfCompletionsTextView.setText(String.valueOf(numCompletions));
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}
