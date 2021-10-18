
package com.example.chatapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatapp.Adapter.UserAdapter;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.Model.User;
//import com.example.chatapp.Notification.Token;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {
    private RecyclerView recyclerView;

    private UserAdapter userAdapter;

    private List<User> mUser;
    FirebaseUser fuser;
    DatabaseReference reference;

    private List<String> userslist;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userslist= new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userslist.clear();
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    Chat chat=ds.getValue(Chat.class);


                   /* if(chat.getSender().equals(fuser.getUid()))
                    {
                        userslist.add(chat.getReceiver());
                    }

                   /* if(chat.getReceiver().equals(fuser.getUid()))
                    {
                        userslist.add(chat.getSender());
                    }*/




                    /* if(chat.getSender().equals(fuser.getUid()))
                     {
                         userslist.add(chat.getReceiver());

                     }
                     if(chat.getReceiver().equals(fuser.getUid()))
                     {
                         userslist.add(chat.getSender());

                     }*/
                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }

    private void readChats()
    {
        mUser=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUser.clear();

                for (DataSnapshot ds:snapshot.getChildren())
                {
                    User user=ds.getValue(User.class);

                    for (String id: userslist)
                    {
                        if(user.getId().equals(id))
                        {
                            if(mUser.size() !=0)
                            {
                                for (User userl : mUser)
                                {
                                    if (!user.getId().equals(userl.getId()))
                                    {
                                        mUser.add(user);
                                    }
                                }
                            }
                            else
                            {
                                mUser.add(user);
                            }
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(),mUser,true);

                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}