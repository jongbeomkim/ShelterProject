package com.example.teamproject2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private ArrayList<Item> searchList;     // 검색리스트
    private ArrayList<Item> cloneList;      // 검색리스트 복사본
    private ListView listView;
    private EditText editSearch;
    private MyAdapter adapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        editSearch = (EditText)rootView.findViewById(R.id.search_editText);
        listView = (ListView)rootView.findViewById(R.id.search_list);

        searchList = new ArrayList<Item>();

        // 리스트 지정할 부분
        searchList.add(new Item(R.drawable.shelter, "hello", "java", "world"));
        searchList.add(new Item(R.drawable.shelter, "asd", "java", "world"));
        searchList.add(new Item(R.drawable.shelter, "fgdw", "java", "world"));
        searchList.add(new Item(R.drawable.shelter, "xcsfhg", "java", "world"));
        searchList.add(new Item(R.drawable.shelter, "vbddf", "java", "world"));
        searchList.add(new Item(R.drawable.shelter, "dhyuj", "java", "world"));
        searchList.add(new Item(R.drawable.shelter, "basq", "java", "world"));
        searchList.add(new Item(R.drawable.shelter, "zsdawf", "java", "world"));

        cloneList = new ArrayList<Item>();
        cloneList.addAll(searchList);

        adapter = new MyAdapter(rootView.getContext(), searchList);
        listView.setAdapter(adapter);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editSearch.getText().toString();
                search(text);
            }
        });

        return rootView;
    }

    public void search(String text){
        searchList.clear();

        if(text.length() == 0){
            searchList.addAll(cloneList);
        } else{
            for(int i=0; i<cloneList.size(); i++){
                if(cloneList.get(i).shelterName.toLowerCase().contains(text)){
                    searchList.add(cloneList.get(i));
                }
            }
        }

        adapter.notifyDataSetChanged();
    }
}