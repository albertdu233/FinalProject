package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends AppCompatActivity {
    private User login;
    private ArrayList<User> users = new ArrayList<>();
    private DatabaseHelper db;
    private RecyclerView viewRank;
    private ImageView rankBoard_avatar;
    private TextView rankBoard_info;
    private int rankLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankboard);
        db = new DatabaseHelper(this);
        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        login = db.getUser(username);
        users = db.getAll();
        for(int i = 0;i<users.size();i++){
            if(users.get(i).getUsername().equals(username)){
                rankLogin = i+1;
            }
        }
// Show the Up button in the action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String detail = login.getUsername()+" \n"
                +login.getEmail()+"\n"+
               login.getBestScore()+"\n"+"You are No."+rankLogin;
        rankBoard_avatar = (ImageView) findViewById(R.id.rank_avatar) ;
        rankBoard_info = (TextView) findViewById(R.id.txt_rank_name) ;
        rankBoard_avatar.setImageResource(login.getAvatraId());

        rankBoard_info.setText(detail);



        viewRank = (RecyclerView) findViewById(R.id.rank_list);
        viewRank.setLayoutManager(new LinearLayoutManager(this));
        viewRank.setAdapter
                (new SimpleItemRecyclerViewAdapter(users));
    }





    class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter
            <SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<User> list;

        SimpleItemRecyclerViewAdapter(List<User> items) {
            list = items;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_content, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.user = list.get(position);
            holder.rankId.setText(String.valueOf(position + 1));
            if(position==0){
                holder.rankId.setTextColor(Color.rgb(255,215,0));
                holder.rankId.setTextSize(30);
            }
            if(position==1){
                holder.rankId.setTextColor(Color.rgb(192,192,192));
                holder.rankId.setTextSize(30);
            }
            if(position==2){
                holder.rankId.setTextColor(Color.rgb(186,110,64));
                holder.rankId.setTextSize(30);
            }
            String detail = users.get(position).getUsername()+" \n"
                    +users.get(position).getEmail()+"\n"+"Score: "+
                    users.get(position).getBestScore()+"\n";
            holder.rankDetail.setText(detail);
            holder.rankAvatar.setImageResource(users.get(position).getAvatraId());
        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final View mView;
            final TextView rankId;
            final TextView rankDetail;
            final ImageView rankAvatar;
            User user;

            ViewHolder(View view) {
                super(view);
                mView = view;
                rankId = (TextView) view.findViewById(R.id.list_rank);
                rankDetail = (TextView) view.findViewById(R.id.list_detail);
                rankAvatar = (ImageView) view.findViewById(R.id.list_avatar);
            }
        }
    }


    //function for the return arrow;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
