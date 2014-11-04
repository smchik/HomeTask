package ua.ck.geekhub.ivanov.hometask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ua.ck.geekhub.ivanov.hometask.R;
import ua.ck.geekhub.ivanov.hometask.models.Tool;

public class ToolAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    List<Tool> list;
    List<Tool> listFiltered;

    public ToolAdapter(Context context, List<Tool> list) {
        this.list = list;
        listFiltered = new ArrayList<Tool>();
        listFiltered.addAll(list);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public Tool getTool(int i) {
        return (Tool) getItem(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.tool_item, parent, false);
        }

        Tool tool = getTool(position);

        ((TextView) view.findViewById(R.id.tool_name)).setText(tool.getName());
        ((ImageView) view.findViewById(R.id.tool_image)).setImageResource(tool.getImage());

        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(listFiltered);
        } else {
            for (Tool tool : listFiltered) {
                if (tool.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    list.add(tool);
                }
            }
        }
        notifyDataSetChanged();
    }
}
