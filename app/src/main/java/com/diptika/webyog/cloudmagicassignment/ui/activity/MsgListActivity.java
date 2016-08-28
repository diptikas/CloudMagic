package com.diptika.webyog.cloudmagicassignment.ui.activity;



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

public class MsgListActivity extends BaseActivity implements Callback<List<Message>>{
    private List<Message> messageList = new ArrayList<>();
    private RecyclerView mMessageRecyclerView;
    private MsgListAdapter mMsgListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar(false,true);
        getSupportActionBar().setLogo(ContextCompat.getDrawable(this,R.drawable.ic_reorder_black_24dp));
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
        mMsgListAdapter=new MsgListAdapter(MsgListActivity.this,messageList);
        mMessageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMessageRecyclerView.setAdapter(mMsgListAdapter);
    }

    @Override
    public void onFailure(Call<List<Message>> call, Throwable t) {
        hideProgress();
        showSnackbar("Something went wrong.Please Try Again",false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMsgListAdapter=new MsgListAdapter(MsgListActivity.this,messageList);
        mMsgListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
