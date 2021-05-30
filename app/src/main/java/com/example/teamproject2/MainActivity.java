package com.example.teamproject2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int mainCode = 11;
    // activity_main.xml에 있는 버튼들을 묶어서 저장함
    private int[] btnId = new int[]{
            R.id.m_btn1, R.id.m_btn2, R.id.m_btn3,
            R.id.m_btn4, R.id.m_btn5, R.id.m_btn6
    };
    private Button[] btn = new Button[6];  // 버튼 6개를 저장할 배열을 선언함

    // 프래그먼트의 자식들(Frag1 ~ Frag6)을 저장하기 위해 Fragment 형식으로 선언함
    public Fragment[] fragment = new Fragment[]{
            new MainMenuFrag1(), new MainMenuFrag2(), new MainMenuFrag3(),
            new MainMenuFrag4(), new MainMenuFrag5(), new MainMenuFrag6()
    };
    Fragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment[0]).commit();
        // btn 배열을 초기화
        for (int i = 0; i < btn.length; i++) {
            btn[i] = (Button) findViewById(btnId[i]);
        }

        for (int i = 0; i < btn.length; i++) {
            int finalI = i;
            // btn[0~5]에 대한 onClickListener를 생성함
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 설명 필요

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment[finalI]).commit();
                    isBtnSelected(btn[finalI]);
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.search:       // 메뉴바에서 검색 버튼을 클릭할 경우
                for(int i=0; i<btn.length; i++){
                    btn[i].setVisibility(View.GONE);
                }
                searchFragment = new SearchFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
                break;
            case R.id.edit:         // 메뉴바에서 수정 버튼을 클릭할 경우
                intent = new Intent(this, EditActivity.class);
                startActivityForResult(intent, 11);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 버튼을 6개 중 한 개 선택했을 경우 해당 버튼만 true로 하고 나머지는 false로 해주는 메서드
    public void isBtnSelected(Button tempButton) {
        for (int i = 0; i < btn.length; i++) {
            if (btn[i] == tempButton) {
                btn[i].setSelected(true);
            } else {
                btn[i].setSelected(false);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //    img = findViewById(R.id.item_icon);   //리스트뷰에 img
        //    shelterName =findViewById(R.id.item_shelter);   //리스트뷰에 쉴터 이름
        //   writer =findViewById(R.id.item_writer);   //리스트뷰에 제공자명
        super.onActivityResult(requestCode, resultCode, data);           // 추가버튼을 누른 후
        if ((requestCode == 11) && (resultCode==RESULT_OK)) {            //
            String s = data.getStringExtra("s_name");             // 문자열 변수에 EditActivity 에서 put 한 데이터를 get 으로 가져오고
            String p = data.getStringExtra("p_name");
            String l = data.getStringExtra("l_name");
            byte[] byteArray = data.getByteArrayExtra("icon");
            Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Storage.items.add(new Item(R.drawable.shelter, s, p, l));  //static items 받아서 추가  static 안쓸라면 getitem만들어서 받아와야할듯?
            Storage.storage.writeStorage();
            MainMenuFrag1.myAdapter.notifyDataSetChanged();  //static 안쓸라면 getAdapter만들어서 받아와야할듯?
        }
        else if ((requestCode==0)&&(resultCode==30)) {           // ViewActivity 에서 삭제를 눌러 돌아왔을때~
            int po = data.getIntExtra("position", -1);
            Storage.storage.delete(po);
            MainMenuFrag1.myAdapter.notifyDataSetChanged();//static 안쓸라면 getAdapter만들어서 받아와야할듯?
        }
        else if ((requestCode==0)&&(resultCode==40)){            // ViewActivity 에서 뒤로가기 버튼을 눌러서 왔을 때~
            byte[] byteArray = data.getByteArrayExtra("icon");
            Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Drawable drawable = new BitmapDrawable(getResources(), image);
            int po = data.getIntExtra("position", -1);
            String s = data.getStringExtra("s_name");             // 문자열 변수에 EditActivity 에서 put 한 데이터를 get 으로 가져오고
            String p = data.getStringExtra("p_name");
            String l = data.getStringExtra("l_name");
            Storage.items.set(po, new Item(R.drawable.shelter,s,p,l));  // position 위치의 리스트 정보를 새로 설정
            Storage.storage.update(po, R.drawable.shelter, s, p, l);
            MainMenuFrag1.myAdapter.notifyDataSetChanged();//static 안쓸라면 getAdapter만들어서 받아와야할듯?

        }
    }

    @Override
    public void onBackPressed(){      // 메인에서 뒤로가기를 눌렀을 때 앱을 종료할 것인지 확인하는 함수
        if(searchFragment != null){             // 검색 프래그먼트가 떠 있을때 뒤로가기를 하면 실행
            getSupportFragmentManager().beginTransaction().remove(searchFragment).commit();     // searchFragment를 삭제
            searchFragment = null;
            for(int i=0; i<btn.length; i++){
                btn[i].setVisibility(View.VISIBLE);
            }

            // 메인액티비티로 돌아가면 민방공대피소(1번째) 보여줌
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment[0]).commit();
            isBtnSelected(btn[0]);
        }else {         // 검색 프래그먼트가 없는 메인화면 상태일때 실행
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Notice");
            builder.setMessage("앱을 종료하시겠습니까?");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
            builder.setNegativeButton("취소", null);
            builder.create().show();
        }
    }
    public void update() {
        File file = new File( getFilesDir(), "test.txt");
        Toast.makeText(this, getFilesDir().toString(), Toast.LENGTH_SHORT).show();
        FileWriter fw = null ;     // 파일에 데이터를 쓰기위한 핸들러 fw 선언
        BufferedWriter bufwr = null ;  // ★☆파일을 읽기 위한 버퍼 생성(버퍼가 뭔지 잘 모르겠다,,)☆★
        try {
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            for (Item item: Storage.items) {    // 리스트 데이터들 처음부터 끝까지~
                bufwr.write(item.icon+","+item.shelterName+","+item.writer+","+item.location) ;  // 데이터 사이마다 구분자 "," 를 추가하며 test.txt 에 입력
                bufwr.newLine();       // 각 대피소 정보마다 줄바꿈을 하여 데이터를 저장.
            }
            bufwr.flush();       // ★☆.flush()가 뭐지???☆★
        } catch (Exception e) {
            e.printStackTrace() ;
        }
    }
}