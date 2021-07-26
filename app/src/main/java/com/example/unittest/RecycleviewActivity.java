package com.example.unittest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RecycleviewActivity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        recyclerView = findViewById(R.id.recycleview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("Item " + i);
        }
        MRecAdapter adapter = new MRecAdapter(this, data);
        recyclerView.setAdapter(adapter);
    }

    public void showDialog(String msg) {
        new AlertDialog.Builder(this).setTitle(msg)
                .setMessage("this is dialog")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
    }

    public class MRecAdapter extends RecyclerView.Adapter<MRecAdapter.RecViewHolder> {

        private final List<String> data = new ArrayList<>();
        private final Context context;

        public MRecAdapter(Context context, List<String> data) {
            this.context = context;
            this.data.addAll(data);
        }

        @Override
        public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.item_recycleview, parent, false));
        }

        @Override
        public void onBindViewHolder(RecViewHolder holder, int position) {
            holder.tvItem.setText(data.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(data.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class RecViewHolder extends RecyclerView.ViewHolder {

            public TextView tvItem;

            public RecViewHolder(View itemView) {
                super(itemView);
                tvItem = itemView.findViewById(R.id.tv_item);
            }
        }

    }

}
