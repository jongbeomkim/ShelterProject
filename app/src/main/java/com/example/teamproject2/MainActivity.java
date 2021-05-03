package com.example.teamproject2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;


    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;
    private Fragment fragment5;
    private Fragment fragment6;


    private ListView mList;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        fragment1 = new Frag1();
        fragment2 = new Frag2();
        fragment3 = new Frag3();
        fragment4 = new Frag4();
        fragment5 = new Frag5();
        fragment6 = new Frag6();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
                btn1.setSelected(true);
                btn2.setSelected(false);
                btn3.setSelected(false);
                btn4.setSelected(false);
                btn5.setSelected(false);
                btn6.setSelected(false);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
                btn1.setSelected(false);
                btn2.setSelected(true);
                btn3.setSelected(false);
                btn4.setSelected(false);
                btn5.setSelected(false);
                btn6.setSelected(false);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment3).commit();
                btn1.setSelected(false);
                btn2.setSelected(false);
                btn3.setSelected(true);
                btn4.setSelected(false);
                btn5.setSelected(false);
                btn6.setSelected(false);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment4).commit();
                btn1.setSelected(false);
                btn2.setSelected(false);
                btn3.setSelected(false);
                btn4.setSelected(true);
                btn5.setSelected(false);
                btn6.setSelected(false);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment5).commit(); //프래그먼트 전화
                btn1.setSelected(false);
                btn2.setSelected(false);//   안눌린걸로 세팅
                btn3.setSelected(false);
                btn4.setSelected(false);
                btn5.setSelected(true);  /// 눌린걸로 세팅
                btn6.setSelected(false);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment6).commit();
                btn1.setSelected(false);
                btn2.setSelected(false);
                btn3.setSelected(false);
                btn4.setSelected(false);
                btn5.setSelected(false);
                btn6.setSelected(true);
            }
        });
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
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}