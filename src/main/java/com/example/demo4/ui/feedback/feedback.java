package com.example.demo4.ui.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo4.R;

public class feedback extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feedback,container,false);
    }

    private EditText temail, tsubject, tmessage;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        temail = view.findViewById(R.id.feedback_email);
        tsubject = view.findViewById(R.id.feedback_subject);
        tmessage = view.findViewById(R.id.feedback_message);
        Button button = view.findViewById(R.id.sendButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = temail.getText().toString();
                String subject = tsubject.getText().toString();
                String message = tmessage.getText().toString();

                //we use email as of now to send a mail
//                Intent gmail = new Intent(Intent.ACTION_VIEW);
//                gmail.setClassName("com.google.android.gm","com.google.android.gmComposeActivityGmail");
//                gmail.putExtra(Intent.EXTRA_EMAIL,new String[] {"tusharsrivastava2904@gmail.com"});
//                gmail.setData(Uri.parse("tusharsrivastava2904@gmail.com"));
//                gmail.putExtra(Intent.EXTRA_SUBJECT,subject);
//                gmail.setType("plain/text");
//                gmail.putExtra(Intent.EXTRA_TEXT,message);
//                startActivity(gmail);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"tusharsrivastava2904@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });
    }
}
