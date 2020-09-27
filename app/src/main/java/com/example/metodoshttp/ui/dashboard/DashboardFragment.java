package com.example.metodoshttp.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.metodoshttp.R;
import com.example.metodoshttp.recurso;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    EditText name,email;
    Button btn;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        name=root.findViewById(R.id.dash_name);
        email=root.findViewById(R.id.dash_email);
        btn=root.findViewById(R.id.dash_btn);

        btn.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.dash_btn){
            load();
        }
    }

    private void load() {
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams req=new RequestParams();
        req.put("name",name.getText().toString());
        req.put("email",email.getText().toString());
        client.post(recurso.ip,req,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try{
                    String res=response.getString("message");
                    Toast.makeText(context, ""+res, Toast.LENGTH_SHORT).show();
                }catch (Exception e){}
            }
        });
    }
}