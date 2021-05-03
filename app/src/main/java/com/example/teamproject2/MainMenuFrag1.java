package com.example.teamproject2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainMenuFrag1 extends Fragment {

    ArrayList<MyData> myData;
    ListView mList;
    private TextView name1;
    private TextView name2;
    private static MyAdapter myAdapter;
    private int viewCode = 20;     // Frag1의 viewCode

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_1, container, false);

        myData = new ArrayList<>();
        myData.add(new MyData(R.drawable.shelter, "대피소1","김동현"));
        myData.add(new MyData(R.drawable.shelter, "대피소2","김종범"));
        myData.add(new MyData(R.drawable.shelter, "대피소3","이학준"));
        myData.add(new MyData(R.drawable.shelter, "대피소4","조윤진"));

        mList = (ListView) rootView.findViewById(R.id.frag1_list);
        myAdapter = new MyAdapter(getContext(), myData);
        mList.setAdapter(myAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //각 아이템을 분간 할 수 있는 position과 뷰
                name1=view.findViewById(R.id.list_name1);   //리스트뷰에 쉴터 이름
                name2=view.findViewById(R.id.list_name2);   //리스트뷰에 제공자명
                Toast.makeText(getContext(), "Clicked: "  +" " + name1.getText(), Toast.LENGTH_SHORT).show();
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shelter);  //구글링해서 잘모름
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Intent intent;
                intent = new Intent(getContext(), ViewActivity.class);
                intent.putExtra("pic", byteArray);
                intent.putExtra("s_name", name1.getText().toString());     //뷰 액티비티로 갈때 값 넘김
                intent.putExtra("p_name", name2.getText().toString());
                intent.putExtra("code",viewCode);
                startActivityForResult(intent, 0);
                }


        });

        return rootView;
    }
}