package com.diptika.webyog.cloudmagicassignment.ui.activity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.diptika.webyog.cloudmagicassignment.R;
import com.diptika.webyog.cloudmagicassignment.ui.adapter.MsgListAdapter;
import com.diptika.webyog.cloudmagicassignment.ui.api.model.Message;
import com.diptika.webyog.cloudmagicassignment.ui.api.retrofit.ApiClient;
import com.diptika.webyog.cloudmagicassignment.ui.api.retrofit.ApiInterface;
import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.SwipeableItemClickListener;
import com.hudomju.swipe.adapter.ListViewAdapter;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by diptika.s on 27/08/16.
 */

public class MsgListActivity extends BaseActivity implements Callback<List<Message>> {
    private List<Message> messageList = new ArrayList<>();
    private RecyclerView mMessageRecyclerView;
    private MsgListAdapter mMsgListAdapter;
    private Paint p = new Paint();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar(false, true);
        getSupportActionBar().setLogo(ContextCompat.getDrawable(this, R.drawable.ic_reorder_black_24dp));
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.msg_list);
        getMessageLst();

    }


    private void getMessageLst() {
        showProgress();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Message>> call = apiService.getMessageList();
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
        messageList.addAll(response.body());
        hideProgress();
        mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(MsgListActivity.this));
        mMsgListAdapter = new MsgListAdapter(MsgListActivity.this, messageList);
        mMessageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMessageRecyclerView.setAdapter(mMsgListAdapter);
        initSwipe();
    }

    @Override
    public void onFailure(Call<List<Message>> call, Throwable t) {
        hideProgress();
        showSnackbar("Something went wrong.Please Try Again", false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMsgListAdapter = new MsgListAdapter(MsgListActivity.this, messageList);
        mMsgListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    mMsgListAdapter.removeItem(position);
                    showSnackbar("Deleted",true);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    if (dX < 0) {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon =  ((BitmapDrawable)ContextCompat.getDrawable(MsgListActivity.this,R.drawable.delete_white)).getBitmap();
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(mMessageRecyclerView);



    }
}
