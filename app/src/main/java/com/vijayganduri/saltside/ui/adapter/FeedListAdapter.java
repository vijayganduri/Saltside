package com.vijayganduri.saltside.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.utils.GrayscaleTransformation;
import com.squareup.picasso.Picasso;
import com.vijayganduri.saltside.R;
import com.vijayganduri.saltside.model.Item;

import java.util.ArrayList;
import java.util.List;

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {
    private static final String TAG = FeedListAdapter.class.getSimpleName();
    private final Context context;

    private List<Item> items;
    private OnItemClickListener mOnItemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView description;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            description = (TextView) v.findViewById(R.id.description);
            image = (ImageView) v.findViewById(R.id.image);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position, items != null ? items.get(position) : null);
                    }
                }
            });
        }
    }

    public FeedListAdapter(Context context) {
        this.context = context;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
        notifyDataSetChanged();
    }

    public void addItems(List<Item> items) {
        if (this.items == null) {
            this.items = items;
        } else {
            this.items.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void removeItem(int pos) {
        this.items.remove(pos);
        notifyItemRemoved(pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = items.get(position);
        if (!TextUtils.isEmpty(item.getImage())) {
            Picasso.with(context).load(item.getImage()).resizeDimen(R.dimen.list_item_img_width, R.dimen.list_item_img_height)
                    .transform(new GrayscaleTransformation(Picasso.with(context)))
                    .into(holder.image);
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            holder.title.setText(item.getTitle());
        }
        if (!TextUtils.isEmpty(item.getDescription())) {
            holder.description.setText(item.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static interface OnItemClickListener {
        public void onItemClick(int position, Item item);
    }
}