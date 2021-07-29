package de.thetechcrafter.esr.utils;

import android.app.Activity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import de.thetechcrafter.esr.R;
import de.thetechcrafter.esr.adapters.WeekAdapter;

public class FragmentHelper {

    public static AbsListView.MultiChoiceModeListener setupListViewMultiSelect(final Activity activity, final ListView listView, final WeekAdapter adapter, final DbHelper db) {
        return new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                final int checkedCount = listView.getCheckedItemCount();
                mode.setTitle(checkedCount + " " + activity.getResources().getString(R.string.selected));
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        }
    }
}
