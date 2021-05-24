package com.example.teamproject2;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class EditActivity extends AppCompatActivity {

    private ImageView imageView;
    private String s_name2, p_name2, l_name2;
    private EditText s_text , p_text, l_text;
    private Intent intent;
    int code = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        intent = getIntent();
        code = intent.getIntExtra("viewCode",-1);   // ViewActivity 에서 불려졌다면 code 가 10으로 바뀐다.

        checkActivity(code);
    }



    public void mOnClick(View v){
        int code = intent.getIntExtra("viewCode",-1);  //뷰에서 온 확인코드 10
        int code2 = intent.getIntExtra("code",-1);    //메인에서 온 확인코드 20
        switch (v.getId()){
            case R.id.e_image:
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
                break;
            case R.id.e_button_OK:          // 수정 완료 눌렀을 때 뷰로 값 전송
                toDecide(code);
                break;
            case R.id.e_button_CANCEL:
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


    public void checkActivity(int code){         // 추가 화면(from MainActivity)인지 수정 화면(from ViewActivity)인지 구분하는 함수
        switch (code){
            case 10:    // ViewActivity 에서 편집 버튼을 눌러서 왔다면~
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


    public void toDecide(int code) {       // 정말 (수정 or 추가)할 것인지 확인하는 함수
        intent = getIntent();
        imageView = findViewById(R.id.e_image);
        s_text = findViewById(R.id.e_shelterName);
        p_text = findViewById(R.id.e_provider);
        l_text = findViewById(R.id.e_location);
        intent = new Intent();
        AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
        builder.setTitle("Notice");
        if (s_text.getText().toString().isEmpty() ||  p_text.getText().toString().isEmpty() || l_text.getText().toString().isEmpty()) {///하나라도 빈칸이면
            builder.setMessage("칸을 다 채워주세요");
            builder.setPositiveButton("확인",null);
        } else {
            if (code != 10) builder.setMessage("대피소를 추가하시겠습니까?");
            if (code == 10) builder.setMessage("대피소 정보를 수정하시겠습니까?");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {   // 확인 버튼을 눌렀을 때~
                public void onClick(DialogInterface dialog, int which) {

                    BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();   //이미지 동적
                    Bitmap bitmap = drawable.getBitmap();
                    //int a=Integer.parseInt(String.valueOf(drawable));
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    //intent.putExtra("id",a);
                    intent.putExtra("icon", byteArray);
                    intent.putExtra("s_name", s_text.getText().toString());
                    intent.putExtra("p_name", p_text.getText().toString());
                    intent.putExtra("l_name", l_text.getText().toString());
                    setResult(RESULT_OK, intent);
                    // if(code != 10) Toast.makeText(getBaseContext(), "대피소가 추가되었습니다.", Toast.LENGTH_LONG).show();
                    if (code == 10)
                        Toast.makeText(getBaseContext(), "대피소가 수정되었습니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
            builder.setNegativeButton("취소", null);   // 취소 버튼은 아무것도 실행되지 않는다.
        }
        builder.create().show();
                                   // 설정한 메시지 박스 생성
    }
}