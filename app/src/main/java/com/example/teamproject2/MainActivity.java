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

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    private int[] btnId = new int[]{
            R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6
    };
    private Button[] btn = new Button[6];

    private Fragment[] fragment = new Fragment[]{
            new Frag1(), new Frag2(), new Frag3(),
            new Frag4(), new Frag5(), new Frag6()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0; i<btn.length; i++){
            btn[i] = (Button) findViewById(btnId[i]);
        }

        for(int i=0; i<btn.length; i++){
            int finalI = i;
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment[finalI]).commit();
                    a(btn[finalI]);
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
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void a(Button tempButton){
        for(int i=0; i<btn.length; i++){
            if(btn[i] == tempButton){
                btn[i].setSelected(true);
            } else{
                btn[i].setSelected(false);
            }
        }
    }
}