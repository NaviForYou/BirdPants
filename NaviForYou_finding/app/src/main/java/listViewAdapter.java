import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class listViewAdapter extends BaseAdapter {
    private LayoutInflater _inflater;
    private ArrayList _profiles;
    private int _layout;

    @Override
    public int getCount() {
        return _profiles.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
