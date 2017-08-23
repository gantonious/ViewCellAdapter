package ca.antonious.sample.about;

import android.view.View;
import android.widget.TextView;

import ca.antonious.sample.R;
import ca.antonious.viewcelladapter.annotations.BindListener;
import ca.antonious.viewcelladapter.viewcells.BaseViewHolder;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;

/**
 * Created by George on 2017-01-08.
 */

public class LibraryViewCell extends GenericViewCell<LibraryViewCell.LibraryViewHolder, Library> {
    public LibraryViewCell(Library library) {
        super(library);
    }

    @Override
    public int getLayoutId() {
        return R.layout.library_list_item;
    }

    @Override
    public void bindViewCell(LibraryViewHolder viewHolder) {
        Library library = getData();

        viewHolder.setName(library.getTitle());
        viewHolder.setSourceControlUrl(library.getSourceControlUrl());
    }

    public interface OnLibraryClickListener {
        void onLibraryClick(Library library);
    }

    @BindListener
    public void bindOnClickListener(LibraryViewHolder libraryViewHolder, final OnLibraryClickListener listener) {
        libraryViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onLibraryClick(getData());
            }
        });
    }

    public static class LibraryViewHolder extends BaseViewHolder {
        private TextView libraryNameTextView;
        private TextView librarySourceControlTextView;

        public LibraryViewHolder(View itemView) {
            super(itemView);

            libraryNameTextView = (TextView) itemView.findViewById(R.id.library_name);
            librarySourceControlTextView = (TextView) itemView.findViewById(R.id.library_source_control);
        }

        public void setName(String name) {
            libraryNameTextView.setText(name);
        }

        public void setSourceControlUrl(String sourceControlUrl) {
            librarySourceControlTextView.setText(sourceControlUrl);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}
