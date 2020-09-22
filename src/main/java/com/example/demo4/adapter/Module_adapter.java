package com.example.demo4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo4.R;
import com.example.demo4.model.Module;

import java.util.List;

public class Module_adapter extends RecyclerView.Adapter<Module_adapter.MyView>{
    private List<Module> list;
    private DemoListener demoListener;

    public Module_adapter(List<Module> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_item,parent, false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        Module module = list.get(position);
        holder.im.setImageResource(module.getImage());
        holder.tname.setText(module.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyView extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView im;
        TextView tname;

        public MyView(@NonNull View itemView){
            super(itemView);
            tname = itemView.findViewById(R.id.module_title);
            im = itemView.findViewById(R.id.module_logo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            demoListener.clickOnItem(view, getAdapterPosition());
        }
    }

    public interface DemoListener{
        void clickOnItem(View view, int position);
    }

    public void setOnDemoListener(DemoListener demoListener){
        this.demoListener = demoListener;
    }
}
