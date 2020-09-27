package com.example.metodoshttp.ui.notifications;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.metodoshttp.R;
import com.example.metodoshttp.recurso;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class DetailsFragment extends Fragment implements View.OnClickListener {

    String a,b,c;
    EditText name,email;
    Button btn;
    Context context;
    View redir;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            a=getArguments().getString("name");
            b=getArguments().getString("email");
            c=getArguments().getString("id");
        }
        context=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_details, container, false);

        name=root.findViewById(R.id.not_name);
        email=root.findViewById(R.id.not_email);
        btn=root.findViewById(R.id.not_btn);

        name.setText(a);
        email.setText(b);

        //Toast.makeText(context, ""+a+" "+b, Toast.LENGTH_SHORT).show();

        btn.setOnClickListener(this);
        redir=root;
        return root;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.not_btn){
            load();
        }
    }

    private void load() {
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams req=new RequestParams();
        req.put("name",name.getText().toString());
        req.put("email",email.getText().toString());
        client.patch(recurso.ip+c,req,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try{
                    String res=response.getString("message");
                    Toast.makeText(context, ""+res, Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(redir).navigate(R.id.action_navigation_details_to_navigation_notifications);
                }catch (Exception e){}
            }
        });

    }
}