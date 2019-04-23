package com.example.windows10.mp3player.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.windows10.mp3player.R;
import com.example.windows10.mp3player.activity.SongActivity;
import com.example.windows10.mp3player.adapter.CategoryAdapter;
import com.example.windows10.mp3player.model.TheLoai;
import com.example.windows10.mp3player.service.APIService;
import com.example.windows10.mp3player.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragment extends Fragment {
    View view;
    ListView listViewCategory;
    CategoryAdapter categoryAdapter;
    ArrayList<TheLoai> listTheLoai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category,container,false);
        initControls();
        getData();
        return view;
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<TheLoai>> callBack = dataService.getTheLoai();
        callBack.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                listTheLoai = (ArrayList<TheLoai>) response.body();
                categoryAdapter = new CategoryAdapter(getActivity(),android.R.layout.simple_expandable_list_item_1,listTheLoai);
                listViewCategory.setAdapter(categoryAdapter);
                setListViewHeightBasedOnChildren(listViewCategory);

                listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intenPlaylist = new Intent(getActivity(), SongActivity.class);
                        intenPlaylist.putExtra("theloai",listTheLoai.get(position));
                        startActivity(intenPlaylist);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {

            }
        });
    }

    private void initControls() {
        listViewCategory = view.findViewById(R.id.listViewCategory);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
