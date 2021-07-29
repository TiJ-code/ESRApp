package de.thetechcrafter.esr.utils;

import android.app.Activity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import de.thetechcrafter.esr.R;
import de.thetechcrafter.esr.adapters.WeekAdapter;
import de.thetechcrafter.esr.model.Week;

import java.util.ArrayList;

public class FragmentHelper {

    public static AbsListView.MultiChoiceModeListener setupListViewMultiSelect(final Activity activity, final ListView listView, final WeekAdapter adapter, final DbHelper db) {
        return new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                final int checkedCount = listView.getCheckedItemCount();
                mode.setTitle(checkedCount + " " + activity.getResources().getString(R.string.selected));
                if(checkedCount == 0) mode.finish();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.toolbar_action_mode, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        ArrayList<Week> removeList = new ArrayList<>();
                        SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                        for(int i = 0; i < checkedItems.size(); i++) {
                            int key = checkedItems.keyAt(i);
                            if(checkedItems.get(key)) {
                                db.deleteWeekById(adapter.getItem(key));
                                removeList.add(adapter.getWeekList().get(key));
                            }
                        }
                        adapter.getWeekList().removeAll(removeList);
                        db.updateWeek(adapter.getWeek());
                        adapter.notifyDataSetChanged();
                        mode.finish();
                        return true;

                    default:
                        return true;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
            }
        };
    }
}
