package com.example.teamproject2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int mainCode = 11;
    // activity_main.xml에 있는 버튼들을 묶어서 저장함
    private int[] btnId = new int[]{
            R.id.m_btn1, R.id.m_btn2, R.id.m_btn3,
            R.id.m_btn4, R.id.m_btn5, R.id.m_btn6
    };
    // 버튼 6개를 저장할 배열을 선언함
    private Button[] btn = new Button[6];

    // 프래그먼트의 자식들(Frag1 ~ Frag6)을 저장하기 위해 Fragment 형식으로 선언함
    public Fragment[] fragment = new Fragment[]{
            new MainMenuFrag1(), new MainMenuFrag2(), new MainMenuFrag3(),
            new MainMenuFrag4(), new MainMenuFrag5(), new MainMenuFrag6()

    };

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
            case R.id.edit:
                intent = new Intent(this, EditActivity.class);
                startActivityForResult(intent, mainCode);
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {                //
            String s = data.getStringExtra("s_name");   // 문자열 변수에 EditActivity 에서 put 한 데이터를 get 으로 가져오고
            String p = data.getStringExtra("p_name");
            String l = data.getStringExtra("l_name");
            byte[] byteArray = data.getByteArrayExtra("icon");    // b 변수에 intent 내부에 들어있는 데이터들을 get 을 사용하여 저장한 뒤
            Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ((MainMenuFrag1) fragment[0]).setSelection(R.drawable.shelter, s, p, l);
        }
        else if (requestCode==0) {
            int po = data.getIntExtra("position", -1);
            ((MainMenuFrag1) fragment[0]).remove(po);
        }

    }
}