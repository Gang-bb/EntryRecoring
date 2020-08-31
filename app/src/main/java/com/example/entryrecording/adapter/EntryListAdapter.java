package com.example.entryrecording.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.entryrecording.R;
import com.example.entryrecording.bean.Entry;

import java.util.List;

import butterknife.BindView;

public class EntryListAdapter extends SimpleAdapter<Entry> {


    @BindView(R.id.entry_name)
    TextView entryName;

    public EntryListAdapter(Context context, List<Entry> datas) {
        super(context, R.layout.template_entry_list, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, Entry item) {
        viewHoder.getTextView(R.id.entry_name).setText(item.getE_content());

    }
}
