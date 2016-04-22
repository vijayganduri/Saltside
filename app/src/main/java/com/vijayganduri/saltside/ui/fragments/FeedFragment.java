package com.vijayganduri.saltside.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vijayganduri.saltside.Config;
import com.vijayganduri.saltside.IntentConstants;
import com.vijayganduri.saltside.R;
import com.vijayganduri.saltside.eventbus.BusHandler;
import com.vijayganduri.saltside.model.Item;
import com.vijayganduri.saltside.rest.RestHandler;
import com.vijayganduri.saltside.ui.ItemDetailActivity;
import com.vijayganduri.saltside.ui.adapter.FeedListAdapter;
import com.vijayganduri.saltside.ui.utils.ConnectivityUtils;
import com.vijayganduri.saltside.ui.utils.DividerItemDecoration;
import com.vijayganduri.saltside.ui.utils.ToastManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FeedFragment extends Fragment implements FeedListAdapter.OnItemClickListener {

    private static final String TAG = FeedFragment.class.getSimpleName();

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private FeedListAdapter mAdapter;
    private Activity mActivity;
    private Context mContext;

    private String tagType;

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        return fragment;
    }

    public FeedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);
        BusHandler.getInstance().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));

        mAdapter = new FeedListAdapter(mContext, this);
        mRecyclerView.setAdapter(mAdapter);
        fetchFeed();
    }

    private void fetchFeed() {
        if (ConnectivityUtils.isInternetOn(getContext())) {
            RestHandler.getInstance().getFeed(getResponseListener());
        } else {
            showToast(R.string.no_network);
        }
    }

    private RestHandler.OnResponseListener getResponseListener() {
        return new RestHandler.OnResponseListener() {

            @Override
            public void onResponse(List<Item> articles) {
                mAdapter.addItems(articles);
            }

            @Override
            public void onError(String error) {
                Log.d(TAG, "onError : " + error);
                showToast(R.string.error_load);
            }
        };
    }

    @Override
    public void onItemClick(int position, Item item) {
        Intent intent = new Intent(getContext(), ItemDetailActivity.class);
        intent.putExtra(IntentConstants.ITEM, item);
        startActivity(intent);
    }

    private void showToast(int resId) {
        ToastManager.showToast(getContext(), resId);
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onDestroyView() {
        BusHandler.getInstance().unregister(this);
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
