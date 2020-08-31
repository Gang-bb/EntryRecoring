package com.example.entryrecording.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.entryrecording.R;
import com.example.entryrecording.bean.Entrybase;

import java.util.List;

import butterknife.BindView;

public class MainAdapter extends SimpleAdapter<Entrybase> {

    @BindView(R.id.base_name)
    TextView baseName;

    public MainAdapter(Context context, List<Entrybase> datas) {
        super(context, R.layout.template_main_entrybase, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, Entrybase item) {
        viewHoder.getTextView(R.id.base_name).setText(item.getB_b_name());

    }
}
