package com.example.teamproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

//
public class EditActivity extends AppCompatActivity {

    private ImageView imageView;
    private String s_name2, p_name2, l_name2;
    private EditText s_text , p_text, l_text;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        intent = getIntent();
        int code = intent.getIntExtra("viewCode",-1);

        checkActivity(code);
    }

    public void checkActivity(int code){         ///////////뷰에서 수정 눌렀을때 값 받아옴
        switch (code){
            case 10:
                intent = getIntent();

                imageView = findViewById(R.id.e_image);
                s_text = findViewById(R.id.e_shelterName);
                p_text = findViewById(R.id.e_provider);
                l_text = findViewById(R.id.e_location);

                byte[] byteArray = intent.getByteArrayExtra("pic");
                Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                s_name2 = intent.getStringExtra("s_name");
                p_name2 = intent.getStringExtra("p_name");
                l_name2 = intent.getStringExtra("l_name");

                imageView.setImageBitmap(image);
                s_text.setText(s_name2);
                p_text.setText(p_name2);
                l_text.setText(l_name2);
        }
    }

    public void mOnClick(View v){
        Intent intent;

        switch (v.getId()){
            case R.id.e_image:
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
                break;
            case R.id.e_button_OK:          // 수정 완료 눌렀을 때 뷰로 값 전송
                s_text = findViewById(R.id.e_shelterName);
                p_text = findViewById(R.id.e_provider);
                l_text=findViewById(R.id.e_location);
                intent = new Intent();
                intent.putExtra("s_name",s_text.getText().toString());
                intent.putExtra("p_name",p_text.getText().toString());
                intent.putExtra("l_name",l_text.getText().toString());
                setResult(RESULT_OK,intent);
                Toast.makeText(this,"대피소가 업데이트 되었습니다.",Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.e_button_CANCEL:
                setResult(RESULT_OK);
                Toast.makeText(this,"최소되었습니다.",Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   //갤러리에서 사진 받아오기
        imageView = findViewById(R.id.e_image);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    imageView.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}