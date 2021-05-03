package com.example.teamproject2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private Fragment[] fragment = new Fragment[]{
            new Frag1(), new Frag2(), new Frag3(),
            new Frag4(), new Frag5(), new Frag6()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // btn 배열을 초기화
        for(int i=0; i<btn.length; i++){
            btn[i] = (Button) findViewById(btnId[i]);
        }

        for(int i=0; i<btn.length; i++){
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
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.edit:
                intent = new Intent(this, EditActivity.class);
                startActivityForResult(intent, mainCode);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 버튼을 6개 중 한 개 선택했을 경우 해당 버튼만 true로 하고 나머지는 false로 해주는 메서드
    public void isBtnSelected(Button tempButton){
        for(int i=0; i<btn.length; i++){
            if(btn[i] == tempButton){
                btn[i].setSelected(true);
            } else{
                btn[i].setSelected(false);
            }
        }
    }
}