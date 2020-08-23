package com.osamaelsh3rawy.chat.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osamaelsh3rawy.chat.R;
import com.osamaelsh3rawy.chat.data.model.UsersData;
import com.osamaelsh3rawy.chat.helper.replace;
import com.osamaelsh3rawy.chat.ui.fragment.home.FragmentChats;
import com.osamaelsh3rawy.chat.ui.fragment.home.FragmentGroups;
import com.osamaelsh3rawy.chat.ui.fragment.home.FragmentProfils;
import com.osamaelsh3rawy.chat.ui.fragment.home.FragmentUsers;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.toolpar_image)
    CircleImageView toolparImage;
    @BindView(R.id.toolpar_name)
    TextView toolparName;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        replace.replaceFragment(getSupportFragmentManager(), R.id.action_home_container, new FragmentChats());

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsersData usersData = dataSnapshot.getValue(UsersData.class);
                toolparName.setText(usersData.getName());
                if (usersData.getImageURL().equals("default")) {
                    toolparImage.setImageResource(R.drawable.ic_profile);
                } else {
                    Glide.with(getApplicationContext()).load(usersData.getImageURL()).into(toolparImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        BottomNavigationView bottomNavView = findViewById(R.id.nav_view);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_chats:
                        replace.replaceFragment(getSupportFragmentManager(), R.id.action_home_container, new FragmentChats());
                        break;
                    case R.id.nav_groups:
                        replace.replaceFragment(getSupportFragmentManager(), R.id.action_home_container, new FragmentGroups());
                        break;
                    case R.id.nav_profile:
                        replace.replaceFragment(getSupportFragmentManager(), R.id.action_home_container, new FragmentProfils());
                        break;
                    case R.id.nav_users:
                        replace.replaceFragment(getSupportFragmentManager(), R.id.action_home_container, new FragmentUsers());

                        break;

                }
                return true;
            }
        });

    }
}
