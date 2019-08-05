package com.example.quickmsg;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

   private TextView logout,change_account ;
    private Button bnt_setting;
    private EditText hey,userName;
    private CircleImageView profile;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profil, container, false);

        logout = view.findViewById(R.id.log_out);
        change_account = view.findViewById(R.id.change_account);
        profile=view.findViewById(R.id.profile_image);
        bnt_setting=  view.findViewById(R.id.set_btn_profile);
        hey= view.findViewById(R.id.set_profile);
        userName= view.findViewById(R.id.user_profile);

        change_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUserToAddAcount();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUserToLogin();


            }
        });


        return view;
    }

    private void sendUserToAddAcount() {
        Intent AddAccountIntent = new Intent(getActivity(),AddAccountActivity.class);
        startActivity(AddAccountIntent);
        getActivity().finish();
    }

    private void sendUserToLogin() {
        Intent logoutIntent = new Intent(getActivity(),LoginActivity.class);
        startActivity(logoutIntent);
        getActivity().finish();
    }
}
