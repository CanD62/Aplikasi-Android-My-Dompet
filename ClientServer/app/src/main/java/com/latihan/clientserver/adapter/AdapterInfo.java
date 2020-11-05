package com.latihan.clientserver.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.latihan.clientserver.R;
import com.latihan.clientserver.model.DataModelInfo;


//class ini digunakan untuk mengubah tampilan listview tidak seperti bawaan Android pada umumnya
public class AdapterInfo extends BaseAdapter {

	List<DataModelInfo> mList ;
	Context ctx;
	private String url;

	// 1
	public AdapterInfo(Context context,  List<DataModelInfo> mList) {
		this.ctx = context;
		this.mList = mList;
	}

	// 2
	@Override
	public int getCount() {
		return mList.size();
	}

	// 3
	@Override
	public long getItemId(int position) {
		return 0;
	}

	// 4
	@Override
	public Object getItem(int position) {
		return null;
	}

	// 5
	public View getView(int position, View convertView, ViewGroup parent) {
		// 1
		final List<DataModelInfo> book = mList;

		// 2
//		if (convertView == null) {
//			final LayoutInflater layoutInflater = LayoutInflater.from(ctx);
//			convertView = layoutInflater.inflate( R.layout.adapter_info, null);
//		}
//
//		// 3
//		TextView tgl, oleh, judul;
//		tgl = (TextView)convertView.findViewById(R.id.tvTgl);
//		oleh = (TextView)convertView.findViewById(R.id.tvOleh);
//		judul = (TextView)convertView.findViewById(R.id.tvJudul);
//
//		 4
//		tgl.setText(ConvertVariabel.tanggal(mList.get( position ).getTgl() ) );
//		oleh.setText( "Oleh : " +mList.get( position ).getOleh() );
//		judul.setText( "Judul "+mList.get( position ).getJudul());

		return convertView;
	}
}