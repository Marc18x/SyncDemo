package com.example.marc.syncdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.marc.syncdemo.Model.Info;
import com.example.marc.syncdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoAdapter extends ArrayAdapter<Info> {

    private int resourceId;

    public InfoAdapter(Context context, int resource,List<Info> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent)
    {
        Info info = getItem(position);
        View view;
        ViewHolder viewHolder;

        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder); //将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder
        }

        viewHolder.tv_id.setText(info.getId()+"");
        viewHolder.tv_tid.setText(info.getTid()+"");
        viewHolder.tv_name.setText(info.getName());
        viewHolder.tv_phone.setText(info.getPhone());
        viewHolder.tv_state.setText("(状态："+info.getState()+")");
        viewHolder.tv_anchor.setText(info.getAnchor());
        return view;
    }




    class ViewHolder{

        @BindView(R.id.tv_id)
        TextView tv_id;

        @BindView(R.id.tv_tid)
        TextView tv_tid;

        @BindView(R.id.tv_name)
        TextView tv_name;

        @BindView(R.id.tv_phone)
        TextView tv_phone;

        @BindView(R.id.tv_state)
        TextView tv_state;

        @BindView(R.id.tv_anchor)
        TextView tv_anchor;

        //初始化绑定ButterKinife
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}



