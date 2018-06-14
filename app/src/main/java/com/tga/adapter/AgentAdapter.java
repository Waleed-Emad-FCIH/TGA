package com.tga.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tga.Activity.ChatActivity;
import com.tga.R;
import com.tga.models.AgentModel;
import com.tga.models.ChatMessage;

import java.util.ArrayList;

public class AgentAdapter extends BaseAdapter {
    Context c;
    ArrayList<AgentModel> Agents;

    public AgentAdapter(Context c, ArrayList<AgentModel> agents) {
        this.c = c;
        Agents = agents;
    }

    @Override
    public int getCount() {
        return Agents.size();
    }

    @Override
    public Object getItem(int position) {
        return Agents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.users_single_layout,parent,false);

        }
        TextView messageTxt=(TextView)convertView.findViewById(R.id.user_single_name);
        TextView messageTime=(TextView)convertView.findViewById(R.id.user_single_status);
        final   AgentModel agent = (AgentModel) this.getItem(position);

        messageTxt.setText(agent.getName());
        messageTime.setText(agent.getCompany());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c,ChatActivity.class);
                intent.putExtra("user_id",agent.getId());
                c.startActivity(intent);
            }
        });

return convertView;
    }
}
