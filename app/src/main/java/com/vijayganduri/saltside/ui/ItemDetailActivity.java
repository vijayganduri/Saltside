package com.vijayganduri.saltside.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.vijayganduri.saltside.IntentConstants;
import com.vijayganduri.saltside.R;
import com.vijayganduri.saltside.model.Item;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemDetailActivity extends AppCompatActivity {

    private static final String TAG = ItemDetailActivity.class.getSimpleName();
    private Item mItem;

    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

    @Bind(R.id.backdrop)
    ImageView backdrop;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.share_action)
    FloatingActionButton shareAction;

    @Bind(R.id.description_detail)
    TextView descriptionDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mItem = savedInstanceState.getParcelable(IntentConstants.ITEM);
        } else if (getIntent().getParcelableExtra(IntentConstants.ITEM) != null) {
            mItem = getIntent().getParcelableExtra(IntentConstants.ITEM);
        }

        if (mItem == null) {
            Log.w(TAG, "Exiting the activity as no item details found....");
            finish();
            return;
        }

        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);
        setupToolbar();

        initialize();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initialize(){
        toolbarLayout.setTitle(mItem.getTitle());
        descriptionDetail.setText(generatePseudoString(mItem.getDescription()));

        Picasso.with(this).load(mItem.getImage()).into(backdrop, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) backdrop.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        int primaryDark = getResources().getColor(R.color.colorPrimaryDark);
                        int primary = getResources().getColor(R.color.colorPrimary);
                        toolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
                        toolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
                    }
                });
            }

            @Override
            public void onError() {
                Log.e(TAG, "Unable to load the image");
            }
        });
    }

    // as content is small, repeat it
    private String generatePseudoString(String msg){
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for(int i=0;i<=50;i++){
            int val = random.nextInt(3);
            sb.append(msg);
            sb.append(".");
            sb.append(val==0?" ":(val==1?"\n":"\n\n"));
        }
        return sb.toString();
    }

    @OnClick(R.id.share_action)
    public void shareArticleClicked(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TITLE,"Share article using ..");
        sendIntent.putExtra(Intent.EXTRA_TEXT, String.format("Checkout %s", mItem.getTitle()));
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}