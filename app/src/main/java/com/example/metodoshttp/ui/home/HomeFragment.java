package com.example.metodoshttp.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metodoshttp.R;
import com.example.metodoshttp.recurso;
import com.example.metodoshttp.ui.adapter.AdapterUser;
import com.example.metodoshttp.ui.adapter.Item;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class HomeFragment extends Fragment {

    RecyclerView rec;
    LinearLayoutManager lnm;
    AdapterUser adp;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        rec=root.findViewById(R.id.home_recycler);
        lnm=new LinearLayoutManager(context);
        adp=new AdapterUser(context);
        rec.setLayoutManager(lnm);
        rec.setAdapter(adp);

        load();

        return root;
    }

    private void load() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(recurso.ip,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject ob=response.getJSONObject(i);
                        Item item=new Item(ob.getString("_id"),ob.getString("name"),ob.getString("email"),1);
                        adp.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}