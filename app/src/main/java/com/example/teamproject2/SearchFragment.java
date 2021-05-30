package com.example.teamproject2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class SearchFragment extends Fragment {


    private ArrayList<Item> cloneList;      // 검색리스트 복사본
    private ListView listView;
    private EditText editSearch;
    private int viewCode = 30;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        // fragment_search.xml에 있는 검색창과 리스트뷰를 가져옴
        editSearch = (EditText)rootView.findViewById(R.id.search_editText);
        listView = (ListView)rootView.findViewById(R.id.search_list);



        // 리스트 내용을 저장소에서 가져오는 부분
        cloneList = new ArrayList<>();
        cloneList.addAll(MainMenuFrag1.items);

        // 어댑터 생성 후 listView에 어댑터 연결
        listView.setAdapter(MainMenuFrag1.myAdapter);  //frag1이랑 같은 adapter를 사용

        editSearch.addTextChangedListener(new TextWatcher() {       // 입력창에 값이 입력될 때마다 실행
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {      // 값이 변경되고 나서 실행
                String text = editSearch.getText().toString();
                search(text);
            }
        });

        // 검색해서 나온 각 아이템 클릭할 때 실행
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 클릭한 아이템에 있는 컨텐츠를 가져옴
                ImageView img = (ImageView)view.findViewById(R.id.item_icon);   //리스트뷰에 img
                TextView shelterName = (TextView)view.findViewById(R.id.item_shelter);   //리스트뷰에 쉴터 이름
                TextView writer = (TextView)view.findViewById(R.id.item_writer);   //리스트뷰에 제공자명

                // 아이템 클릭 시 대피소명을 Toast로 출력
                Toast.makeText(getContext(), "Clicked: "  + " " + shelterName.getText(), Toast.LENGTH_SHORT).show();

                // 그림 가져오는 부분(추가 설명 필요)
                BitmapDrawable drawable = (BitmapDrawable) img.getDrawable(); //이미지 동적
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] byteArray = stream.toByteArray();
                Intent intent;
                intent = new Intent(getContext(), ViewActivity.class);
                intent.putExtra("icon", byteArray);
                intent.putExtra("shelterName", shelterName.getText().toString());     //뷰 액티비티로 갈때 값 넘김
                intent.putExtra("writer", writer.getText().toString());
                intent.putExtra("location", MainMenuFrag1.items.get(position).location);
                intent.putExtra("code", viewCode);
                intent.putExtra("position", position);
                getActivity().startActivityForResult(intent, 0);
            }
        });

        return rootView;
    }

    // 문자열을 입력받으면 해당 문자가 포함된 원소 모두 출력
    public void search(String text){
        MainMenuFrag1.items.clear();

        if(text.length() == 0){     // 아무것도 입력하지 않았을 때 리스트 전체를 보여줌
            MainMenuFrag1.items.addAll(cloneList);
        } else{
            for(int i=0; i<cloneList.size(); i++){
                // 리스트를 돌면서 각 리스트마다.대피소명을 가져오고.이를 소문자로 변환하고 그 값에.text 문자열이 포함되어 있는지 확인
                if(cloneList.get(i).shelterName.toLowerCase().contains(text)){
                    MainMenuFrag1.items.add(cloneList.get(i));
                }
            }
        }

        MainMenuFrag1.myAdapter.notifyDataSetChanged();
    }

}