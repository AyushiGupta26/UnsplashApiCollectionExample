package com.example.bipolarfactoryassisgnment.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bipolarfactoryassisgnment.ApiInterface;
import com.example.bipolarfactoryassisgnment.ListItem;
import com.example.bipolarfactoryassisgnment.MyAdapter;
import com.example.bipolarfactoryassisgnment.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    private ApiInterface api;
    private List<ListItem> listItems;
    private RecyclerView.Adapter adapter;
    private boolean userScrolled=false, loadMoreStatus = false;
    private int index=1, getTotalcount, lastVisibleItem, page=2;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.photosRV);
        listItems = new ArrayList<>();
        adapter = new MyAdapter(listItems,getContext());
        recyclerView.setAdapter(adapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        api = retrofit.create(ApiInterface.class);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    userScrolled = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                getTotalcount = gridLayoutManager.getItemCount();
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                if (getTotalcount - 1 == lastVisibleItem && userScrolled) {
                    if (!loadMoreStatus) {
                        //hiting the api from view
                        if (index==1){
                            pageViewModel.loadPetsString(page);
                        }else {
                            pageViewModel.loadNatureString(page);
                        }
                        page=page+1;
                        loadMoreStatus = true;
                        userScrolled = false;
                    }
                } else {
                    loadMoreStatus = false;
                }
            }
        });

        pageViewModel.getString().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                displayData(s);
            }
        });
        return root;
    }

    void displayData(String response) {
        try {
//            listItems.clear();
            JSONArray array = new JSONArray(response);
            Log.d("photos fragment", array.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                String name = o.getString("description");
                Log.d("Photos", "Name: " + name);
                JSONObject urls = o.getJSONObject("urls");
                String url = urls.getString("small");
                Log.d("Photos", "url: " + url);
                ListItem listItem = new ListItem(name.equals("null") ? "" : name, url);
                listItems.add(listItem);
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}