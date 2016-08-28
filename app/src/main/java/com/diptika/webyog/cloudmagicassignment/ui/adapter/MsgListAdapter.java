package com.diptika.webyog.cloudmagicassignment.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diptika.webyog.cloudmagicassignment.R;
import com.diptika.webyog.cloudmagicassignment.ui.activity.MsgDetailActivity;
import com.diptika.webyog.cloudmagicassignment.ui.api.model.Message;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by diptika.s on 27/08/16.
 */
public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.ViewHolder> {
    private List<Message> messageList;
    private Context mContext;

    public MsgListAdapter(Context context, List<Message> messageList) {
        mContext = context;
        this.messageList = messageList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_msg_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.participantName.setText(TextUtils.join(" , ", message.getParticipants()));
        holder.subjectText.setText("Re : " + message.getSubject());
        holder.previewText.setText(message.getPreview());

        //Convert timestamp to date
        convertTS(holder, message.getTimeStamp());

        holder.setTag(R.string.tag_msg_id, message.getId());
        if (message.isRead()) {
            holder.rootLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.border_grey));
        } else {
            holder.rootLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));

        }

        if (message.isStarred()) {
            holder.starredIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.starred));
        } else {
            holder.starredIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.unstar));
        }

    }

    private void convertTS(ViewHolder holder, String timeStamp) {
        try {
            DateFormat sdf = new SimpleDateFormat("dd,MMM");
            Date netDate = (new Date(Long.parseLong(timeStamp) * 1000L));
            holder.timestampText.setText(sdf.format(netDate));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public void removeItem(int position) {
        messageList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, messageList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView participantName;
        public TextView subjectText;
        public TextView previewText;
        public TextView timestampText;
        public ImageView starredIcon;
        public LinearLayout rootLayout;
        public View view;

        /**
         * @param view
         */
        public ViewHolder(View view) {
            super(view);
            this.view = view;
            participantName = (TextView) view.findViewById(R.id.participants_name);
            subjectText = (TextView) view.findViewById(R.id.subject_text);
            previewText = (TextView) view.findViewById(R.id.preview_text);
            timestampText = (TextView) view.findViewById(R.id.time);
            starredIcon = (ImageView) view.findViewById(R.id.starred_icon);
            rootLayout = (LinearLayout) view.findViewById(R.id.root_layout);
            rootLayout.setOnClickListener(this);
        }

        public void setTag(int id, int tag) {
            view.setTag(id, tag);
        }

        @Override
        public void onClick(View v) {
            int id = (Integer) v.getTag(R.string.tag_msg_id);
            rootLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.border_grey));
            mContext.startActivity(new Intent(mContext, MsgDetailActivity.class).putExtra(mContext.getString(R.string.tag_msg_id), id));
        }
    }
}
