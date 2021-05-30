package com.example.teamproject2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class ViewActivity extends AppCompatActivity {

    private int viewCode = 10;   // ViewActivity 의 viewCode

    private ImageView m_v_image;                                      // shelter 이미지 id 를 저장하는 변수
    private TextView m_v_shelterName, m_v_provider, m_v_location;     // 각 editText 의 id를 받아오기 위한 변수들
    private String s_name1, p_name1, l_name1;                         // EditActivity 에서 intent 에 put 된 값들을 get 하기위한 변수들

    int position;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        intent = getIntent();
        m_v_image = (ImageView) findViewById(R.id.v_image);            // 데이터 별로 id를 가져와 a 변수에 저장하고
        m_v_shelterName = (TextView) findViewById(R.id.v_shelterName);
        m_v_provider = (TextView) findViewById(R.id.v_provider);
        m_v_location = (TextView) findViewById(R.id.v_location);

        byte[] byteArray = intent.getByteArrayExtra("icon");    // b 변수에 intent 내부에 들어있는 데이터들을 get 을 사용하여 저장한 뒤
        Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        s_name1 = intent.getStringExtra("shelterName");
        p_name1 = intent.getStringExtra("writer");
        l_name1 = intent.getStringExtra("location");

        m_v_image.setImageBitmap(image);                              // a 변수에(id에 해당하는 곳에) b 변수에 저장된 데이터를 set 으로 설정한다.
        m_v_shelterName.setText(s_name1);
        m_v_provider.setText(p_name1);
        m_v_location.setText(l_name1);
    }


    public void mOnClick(View v) {
        Intent intent;  // ←-- 이걸 없애면 Main → View → Edit → View 상태에서 뒤로가기를 누르면 팅긴다. Why...?

        switch (v.getId()) {
            case R.id.v_button_EDIT:          // ViewActivity 에서 '편집'버튼을 클릭하면~
                intent = new Intent(this, EditActivity.class);

                BitmapDrawable drawable = (BitmapDrawable) m_v_image.getDrawable(); //이미지 동적
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                //  ↑ 구글링으로 찾은 이미지를 put 하는 방법인데 자세히는 모르겠음.
                //  | 아마 bit 로 이루어진 이미지의 bit 를 배열로 저장하고 넘기는 방식인듯 함.
                intent.putExtra("pic", bs.toByteArray());
                intent.putExtra("s_name", m_v_shelterName.getText().toString());
                intent.putExtra("p_name", m_v_provider.getText().toString());
                intent.putExtra("l_name", m_v_location.getText().toString());
                intent.putExtra("viewCode", 10);  //EditActivity 에서 ViewActivity 인 것을 구분하기 위한 코드
                startActivityForResult(intent, viewCode);      // EditActivity 를 호출
                break;
            case R.id.v_button_REMOVE:
                toDecide2();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 10 && resultCode == RESULT_OK) {                // ViewActivity >> EditActivity >> ViewActivity 로 다시 왔을 때~
            position = data.getIntExtra("pos", -1);
            String s = data.getStringExtra("s_name");   // 문자열 변수에 EditActivity 에서 put 한 데이터를 get 으로 가져오고
            String p = data.getStringExtra("p_name");
            String l = data.getStringExtra("l_name");
            byte[] byteArray = data.getByteArrayExtra("icon");    // b 변수에 intent 내부에 들어있는 데이터들을 get 을 사용하여 저장한 뒤
            Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            m_v_image.setImageBitmap(image);
            m_v_shelterName.setText(s);
            m_v_provider.setText(p);
            m_v_location.setText(l);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    // ★☆나중에 edit 함수를 다른 곳에서 쓰지 않는다면 edit 함수를 이곳에 바로 적용가능하지 않을까?☆★
    public void onBackPressed() {
        //super.onBackPressed();
        m_v_image = (ImageView) findViewById(R.id.v_image);            // 데이터 별로 id를 가져와 a 변수에 저장하고
        m_v_shelterName = (TextView) findViewById(R.id.v_shelterName);
        m_v_provider = (TextView) findViewById(R.id.v_provider);
        m_v_location = (TextView) findViewById(R.id.v_location);

        BitmapDrawable drawable = (BitmapDrawable) m_v_image.getDrawable(); //이미지 동적
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        intent.putExtra("icon", byteArray);
        intent.putExtra("pos", position);
        intent.putExtra("s_name", m_v_shelterName.getText().toString());
        intent.putExtra("p_name", m_v_provider.getText().toString());
        intent.putExtra("l_name", m_v_location.getText().toString());
        setResult(40, intent);
        finish();
    }

    public void toDecide2(){    // 정말 삭제할 것인지 확인하는 함수
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewActivity.this);
        builder.setTitle("Notice");
        builder.setMessage("정말 삭제하시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {   // 확인 버튼을 눌렀을 때~
            public void onClick(DialogInterface dialog, int which) {
                intent = getIntent();
                //intent.getIntExtra("position",-1);
                setResult(30, intent);   // MainActivity 로 resultCode 30을 들고 돌아감.
                Toast.makeText(getBaseContext(), "삭제되었습니다.", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        builder.setNegativeButton("취소", null);   // 취소 버튼은 아무것도 실행되지 않는다.
        builder.create().show();
    }
}
