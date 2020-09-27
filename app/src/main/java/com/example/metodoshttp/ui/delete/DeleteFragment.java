package com.example.metodoshttp.ui.delete;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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


public class DeleteFragment extends Fragment implements AdapterUser.SendData{

    Context context;

    RecyclerView rec;
    LinearLayoutManager lnm;
    AdapterUser adp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_delete, container, false);

        rec=root.findViewById(R.id.recycler_delete);
        lnm=new LinearLayoutManager(context);
        adp=new AdapterUser(context,this);
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
                        Item item=new Item(ob.getString("_id"),ob.getString("name"),ob.getString("email"),3);
                        adp.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void dataUser(Bundle data) {
        String id=data.getString("id");
        delete(id);
    }

    private void delete(final String id) {
        AsyncHttpClient client=new AsyncHttpClient();
        client.delete(recurso.ip+id,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Toast.makeText(context, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}