package com.example.teamproject2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainMenuFrag1 extends Fragment {

    ArrayList<Item> items;
    ListView mList;
    private TextView shelterName;
    private TextView writer;
    private ImageView img;
    private static MyAdapter myAdapter;
    private int viewCode = 20;     // Frag1의 viewCode

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_1, container, false);

        items = new ArrayList<>();
        items.add(new Item(R.drawable.test, "대피소1","김동현"));
        items.add(new Item(R.drawable.shelter, "대피소2","김종범"));
        items.add(new Item(R.drawable.shelter, "대피소3","이학준"));
        items.add(new Item(R.drawable.shelter, "대피소4","조윤진"));
        items.add(new Item(R.drawable.shelter, "대피소1","김동현"));
        items.add(new Item(R.drawable.shelter, "대피소2","김종범"));
        items.add(new Item(R.drawable.shelter, "대피소3","이학준"));
        items.add(new Item(R.drawable.shelter, "대피소4","조윤진"));
        items.add(new Item(R.drawable.shelter, "대피소1","김동현"));
        items.add(new Item(R.drawable.shelter, "대피소2","김종범"));
        items.add(new Item(R.drawable.shelter, "대피소3","이학준"));
        items.add(new Item(R.drawable.shelter, "대피소4","조윤진"));
        items.add(new Item(R.drawable.shelter, "대피소1","김동현"));
        items.add(new Item(R.drawable.shelter, "대피소2","김종범"));
        items.add(new Item(R.drawable.shelter, "대피소3","이학준"));
        items.add(new Item(R.drawable.shelter, "대피소4","조윤진"));
        items.add(new Item(R.drawable.shelter, "대피소1","김동현"));
        items.add(new Item(R.drawable.shelter, "대피소2","김종범"));
        items.add(new Item(R.drawable.shelter, "대피소3","이학준"));
        items.add(new Item(R.drawable.shelter, "대피소4","조윤진"));

        mList = (ListView) rootView.findViewById(R.id.frag1_list);
        myAdapter = new MyAdapter(getContext(), items);
        mList.setAdapter(myAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //각 아이템을 분간 할 수 있는 position과 뷰
                img = view.findViewById(R.id.item_icon);   //리스트뷰에 img
                shelterName = view.findViewById(R.id.item_shelter);   //리스트뷰에 쉴터 이름
                writer = view.findViewById(R.id.item_writer);   //리스트뷰에 제공자명

                Toast.makeText(getContext(), "Clicked: "  +" " + shelterName.getText(), Toast.LENGTH_SHORT).show();

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
                intent.putExtra("code",viewCode);
                startActivityForResult(intent, 0);
            }

        });

        return rootView;
    }
}