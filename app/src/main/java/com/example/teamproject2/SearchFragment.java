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

    private ArrayList<Item> searchList;     // 검색리스트
    private ArrayList<Item> cloneList;      // 검색리스트 복사본
    private ListView listView;
    private EditText editSearch;
    private MyAdapter adapter;
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

        searchList = new ArrayList<Item>();

        // 리스트 내용을 저장소에서 가져오는 부분
        File file = new File(getActivity().getFilesDir(), "test.txt");  // getFilesDir(): 파일의 전체 저장 경로를 가져오는 메소드
        FileReader fr = null;       // 파일 데이터를 읽기 위한 핸들러 fr 선언.
        BufferedReader bufrd = null;
        String s;
        if (file.exists()) {   // file.exists(): 파일이 존재하는지 검사
            try {
                fr = new FileReader(file);    // fr 을 "file"파일을 읽기 위한 핸들러로 선언.
                bufrd = new BufferedReader(fr);
                while ((s = bufrd.readLine()) != null) {
                    String[] split = new String(s).split(",");
                    searchList.add(new Item(Integer.parseInt(split[0]) , split[1], split[2], split[3]));
                }
                bufrd.close();
                fr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        cloneList = new ArrayList<Item>();
        cloneList.addAll(searchList);

        // 어댑터 생성 후 listView에 어댑터 연결
        adapter = new MyAdapter(rootView.getContext(), searchList);
        listView.setAdapter(adapter);

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
                intent.putExtra("location", searchList.get(position).location);
                intent.putExtra("code", viewCode);
                intent.putExtra("position", position);
                getActivity().startActivityForResult(intent, 50);
            }
        });

        return rootView;
    }

    // 문자열을 입력받으면 해당 문자가 포함된 원소 모두 출력
    public void search(String text){
        searchList.clear();

        if(text.length() == 0){     // 아무것도 입력하지 않았을 때 리스트 전체를 보여줌
            searchList.addAll(cloneList);
        } else{
            for(int i=0; i<cloneList.size(); i++){
                // 리스트를 돌면서 각 리스트마다.대피소명을 가져오고.이를 소문자로 변환하고 그 값에.text 문자열이 포함되어 있는지 확인
                if(cloneList.get(i).shelterName.toLowerCase().contains(text)){
                    searchList.add(cloneList.get(i));
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

}