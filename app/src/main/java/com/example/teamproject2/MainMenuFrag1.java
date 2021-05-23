package com.example.teamproject2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class MainMenuFrag1 extends Fragment {

    ArrayList<Item> items;
    ListView mList;
    private TextView shelterName;
    private TextView writer;
    private ImageView img;
    private static MyAdapter myAdapter;
    private int viewCode = 20;     // Frag1의 viewCode
    private Drawable img_plus;
    private  String s_plus, p_plus, l_plus;
    int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_1, container, false);
        items = new ArrayList<>();

        Drawable shelter = getResources().getDrawable(R.drawable.shelter );
            File file = new File(getActivity().getFilesDir(), "test.txt");
            FileReader fr = null;
            BufferedReader bufrd = null;
            String s;
            if (file.exists()) {
                try {
                    fr = new FileReader(file);
                    bufrd = new BufferedReader(fr);
                    while ((s = bufrd.readLine()) != null) {
                        String[] split = new String(s).split(",");
                        items.add(new Item(shelter,split[1],split[2],split[3]));
                    }
                    bufrd.close();
                    fr.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
                intent.putExtra("location",items.get(position).location);
                intent.putExtra("code",viewCode);
                intent.putExtra("position",position);
                getActivity().startActivityForResult(intent, 0);

            }


        });

        return rootView;
    }
    public void setSelection(Drawable img, String s1, String s2, String s3){
        img_plus = img;
        s_plus = s1;
        p_plus = s2;
        l_plus = s3;
        items.add(new Item(img_plus, s_plus, p_plus,l_plus));
        myAdapter.notifyDataSetChanged();     // 프레그먼트 재실행
        update();
    }
    public  void remove(int position1){
        position = position1;
        items.remove(position1);
        myAdapter.notifyDataSetChanged();
        update();
    }
    public void edit(int position,Drawable img, String s1, String s2, String s3){
        img_plus = img;
        s_plus = s1;
        p_plus = s2;
        l_plus = s3;
        items.set(position,new Item(img_plus, s_plus, p_plus,l_plus));
        myAdapter.notifyDataSetChanged();
        update();
    }
    public void update() {
        File file = new File( getActivity().getFilesDir(), "test.txt");
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;
        try {
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            for (Item item: items) {
                bufwr.write(item.icon+","+item.shelterName+","+item.writer+","+item.location) ;
                bufwr.newLine();
            }
            bufwr.flush() ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
    }
}

