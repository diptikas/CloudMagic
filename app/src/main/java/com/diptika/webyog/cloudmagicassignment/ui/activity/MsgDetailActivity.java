package com.diptika.webyog.cloudmagicassignment.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.diptika.webyog.cloudmagicassignment.R;

import com.diptika.webyog.cloudmagicassignment.ui.api.model.MessageDetail;
import com.diptika.webyog.cloudmagicassignment.ui.api.model.Participant;
import com.diptika.webyog.cloudmagicassignment.ui.api.retrofit.ApiClient;
import com.diptika.webyog.cloudmagicassignment.ui.api.retrofit.ApiInterface;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by diptika.s on 27/08/16.
 */
public class MsgDetailActivity extends BaseActivity {
    private TextView mSubject;
    private TextView mParticipantName;
    private TextView mDate;
    private TextView mName;
    private TextView mMessageBody;
    private ImageView mStarredIcon;
    private TextView mHeadText;
    private int msgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_detail_activity);
        initToolBar(false, true);
        initView();
        getSupportActionBar().setLogo(ContextCompat.getDrawable(this, R.drawable.ic_white_arrow));
        msgId = getIntent().getExtras().getInt(getString(R.string.tag_msg_id));
        getMessageDetailLst();
    }

    private void getMessageDetailLst() {
        showProgress();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MessageDetail> call = apiService.getMessageDetails(msgId);
        call.enqueue(new Callback<MessageDetail>() {
            @Override
            public void onResponse(Call<MessageDetail>call, Response<MessageDetail> response) {
                hideProgress();
                mSubject.setText(response.body().getSubject());
                String name=TextUtils.join(" , ", getNames(response.body().getParticipants()));
                mParticipantName.setText(name);
                mName.setText(TextUtils.join(" , ", getEmails(response.body().getParticipants())));
                mMessageBody.setText(response.body().getBody());
                mHeadText.setText(""+name.charAt(0));
                if (response.body().isStarred()) {
                    mStarredIcon.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.starred));
                } else {
                    mStarredIcon.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.unstar));
                }
                generateDate(response.body().getTimeStamp());
                 }

            @Override
            public void onFailure(Call<MessageDetail>call, Throwable t) {
                hideProgress();
                showSnackbar("Something went wrong.Please Try Again",false);

            }
        });
    }


    private List<String> getNames(List<Participant> participants) {
        List<String> names = new ArrayList<>();
        for (Participant p : participants) {
            names.add(p.getName());
        }
        return names;
    }

    private List<String> getEmails(List<Participant> participants) {
        List<String> emails = new ArrayList<>();
        for (Participant p : participants) {
            emails.add(p.getEmail());
        }
        return emails;
    }

    private void generateDate(String timeStamp) {
        try {
            DateFormat sdf = new SimpleDateFormat("dd,MMM ,yyyy");
            Date netDate = (new Date(Long.parseLong(timeStamp) * 1000L));
            mDate.setText(sdf.format(netDate));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initView() {
        mParticipantName = (TextView) findViewById(R.id.participants_name);
        mDate = (TextView) findViewById(R.id.time);
        mName = (TextView) findViewById(R.id.name);
        mMessageBody = (TextView) findViewById(R.id.body);
        mSubject = (TextView) findViewById(R.id.welcome);
        mStarredIcon = (ImageView) findViewById(R.id.starred_icon);
        mHeadText=(TextView)findViewById(R.id.head_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.delete:
                showDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MsgDetailActivity.this);
        builder.setTitle(getResources().getString(R.string.title_alert_delete));
        builder.setMessage(getResources().getString(R.string.message_alert_delete));

        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteMsg();
                dialog.dismiss();

            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void deleteMsg() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.deleteMessage(msgId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void>call, Response<Void> response) {
                showSnackbar("Deleted",true);
                 }
            @Override
            public void onFailure(Call<Void>call, Throwable t) {
                hideProgress();
                showSnackbar("Something went wrong.Please Try Again",false);

            }
        });



    }


}
