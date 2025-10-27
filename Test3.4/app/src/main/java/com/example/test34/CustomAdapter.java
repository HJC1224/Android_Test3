package com.example.test34;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<Item> itemList;
    private LayoutInflater inflater;
    private Set<Integer> selectedPositions; // 选中的位置集合

    // 构造方法添加选中集合参数
    public CustomAdapter(Context context, List<Item> itemList, Set<Integer> selectedPositions) {
        this.context = context;
        this.itemList = itemList;
        this.selectedPositions = selectedPositions;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.item_image);
            holder.textView = convertView.findViewById(R.id.item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 绑定数据
        Item item = itemList.get(position);
        holder.textView.setText(item.getText());
        holder.imageView.setImageResource(item.getImageResId());

        // 关键：根据是否选中设置背景（选中项高亮）
        if (selectedPositions.contains(position)) {
            convertView.setBackgroundColor(Color.LTGRAY); // 选中时灰色背景
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT); // 未选中时透明
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}