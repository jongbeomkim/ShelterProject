package com.example.teamproject2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.BufferedReader;  // java.io: Java 에서 File 을 다루기 위해 사용되는 클래스들이 모인 패키지
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class MainMenuFrag1 extends Fragment {
    ListView mList;
    private TextView shelterName;
    private TextView writer;
    private ImageView img;
    static MyAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_1, container, false);

        Storage.items = new ArrayList<>();
        // 데이터를 읽어옴 

        File file = new File(getActivity().getFilesDir(), "test.txt");  // getFilesDir(): 파일의 전체 저장 경로를 가져오는 메소드
        FileReader fr = null;       // 파일 데이터를 읽기 위한 핸들러 fr 선언.
        BufferedReader bufrd = null;
        String s;
        if (file.exists()) {   // file.exists(): 파일이 존재하는지 검사

            try {
                fr = new FileReader(file);    // fr 을 "file"파일을 읽기 위한 핸들러로 선언.
                bufrd = new BufferedReader(fr);
                while ( (s= bufrd.readLine()) != null) {
                    String[] split = new String(s).split(",");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    String imgpath = getActivity().getCacheDir() + "/" +split[1]+split[2]+split[3];   // 내부 저장소에 저장되어 있는 split[1]은 이미지파일명 (쉴터이름)
                    Bitmap bm = BitmapFactory.decodeFile(imgpath);
                    Storage.items.add(new Item(bm, split[1], split[2], split[3],split[4]));
                }
                bufrd.close();
                fr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

            mList = (ListView) rootView.findViewById(R.id.frag1_list);
            myAdapter = new MyAdapter(getContext(), Storage.items);
            mList.setAdapter(myAdapter);

            mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                    //각 아이템을 분간 할 수 있는 position과 뷰
                    img = view.findViewById(R.id.item_icon);   //리스트뷰에 img
                    shelterName = view.findViewById(R.id.item_shelter);   //리스트뷰에 쉴터 이름
                    writer = view.findViewById(R.id.item_writer);   //리스트뷰에 제공자명

                    Toast.makeText(getContext(), "Clicked: " + " " + shelterName.getText(), Toast.LENGTH_SHORT).show();

                    // 그림 가져오는 부분(추가 설명 필요)
                    BitmapDrawable drawable = (BitmapDrawable) img.getDrawable(); //이미지 동적
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    //byte[] byteArray = stream.toByteArray();
                    Intent intent;
                    intent = new Intent(getContext(), ViewActivity.class);
                    //intent.putExtra("icon", byteArray);
                    intent.putExtra("shelterName", shelterName.getText().toString());     //뷰 액티비티로 갈때 값 넘김
                    intent.putExtra("writer", writer.getText().toString());
                    intent.putExtra("location", Storage.items.get(position).location);
                    intent.putExtra("memo", Storage.items.get(position).memo);
                    //intent.putExtra("code",viewCode);

                    intent.putExtra("position", position);
                    getActivity().startActivityForResult(intent, 0);
                }
            });
            return rootView;
        }
    }

    // 리스트에 대피소 정보를 새로 추가하는 함수.
    //static으로 list 직접 추가 수정 해서 팔요 없어졌으나 혹시 몰라서 남김
  /*  public void insert(int img, String s1, String s2, String s3){
        img_plus = img;
        s_plus = s1;
        p_plus = s2;
        l_plus = s3;
        items.add(new Item(img_plus, s_plus, p_plus, l_plus));  // 리스트에 대피소정보 추가
        update();
        myAdapter.notifyDataSetChanged();     // 프레그먼트 재실행 메소드
    }

    // 리스트의 기존 대피소 정보를 삭제하는 함수.
    public  void remove(int position1){
        position = position1;
        items.remove(position1);
        update();
        myAdapter.notifyDataSetChanged();     // 프레그먼트 재실행 메소드
    }

    // 리스트의 기존 대피소 정보를 수정하는 함수.
    public void edit(int position,int img, String s1, String s2, String s3){
        img_plus = img;
        s_plus = s1;
        p_plus = s2;
        l_plus = s3;
        items.set(position, new Item(img_plus, s_plus, p_plus,l_plus));  // position 위치의 리스트 정보를 새로 설정
        update();
        myAdapter.notifyDataSetChanged();     // 프레그먼트 재실행 메소드
    }
    // 리스트의 정보들을 저장소에 적용해 최신화하는 함수.
    public void update() {
        File file = new File( getActivity().getFilesDir(), "test.txt");
        FileWriter fw = null ;     // 파일에 데이터를 쓰기위한 핸들러 fw 선언
        BufferedWriter bufwr = null ;  // ★☆파일을 읽기 위한 버퍼 생성(버퍼가 뭔지 잘 모르겠다,,)☆★
        try {
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            for (Item item: items) {    // 리스트 데이터들 처음부터 끝까지~
                bufwr.write(item.icon+","+item.shelterName+","+item.writer+","+item.location) ;  // 데이터 사이마다 구분자 "," 를 추가하며 test.txt 에 입력
                bufwr.newLine();       // 각 대피소 정보마다 줄바꿈을 하여 데이터를 저장.
            }
            bufwr.flush();       // ★☆.flush()가 뭐지???☆★
        } catch (Exception e) {
            e.printStackTrace() ;
        }
    }*/


