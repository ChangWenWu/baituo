package com.yangyu.mycustomtab02;

import java.util.List;
import com.example.delivery_second.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint({ "ResourceAsColor", "InflateParams" }) public class MyAdapter extends BaseAdapter {
	private List<String> l;
	private Context context;
	private int selectItem = -1;

	public MyAdapter(Context context, List<String> l) {
		this.context = context;
		this.l = l;
	}

	@Override
	public int getCount() {
		return l.size();
	}

	@Override
	public Object getItem(int position) {
		return l.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item,
					null);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.name.setText(l.get(position).toString());
		if (position == selectItem) {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.click));
		} else {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.defult));
		}
		return convertView;
	}

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
	}

	class Holder {
		TextView name;
	}

}
