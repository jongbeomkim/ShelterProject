package com.example.teamproject2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class Storage extends AppCompatActivity {
    static ArrayList<Item> items;

    // 리스트에 대피소 정보를 추가하는 메서드
    public void insert(Bitmap p_image, String p_shelterName, String p_writer, String p_location,String p_memo){
        items.add(new Item(p_image, p_shelterName, p_writer, p_location,p_memo));    // 리스트에 대피소 정보 추가
        writeStorage();
        MainMenuFrag1.myAdapter.notifyDataSetChanged();     // 프레그먼트 재실행
    }

    // 리스트의 기존 대피소 정보를 삭제하는 메서드_jb
    public void delete(int pos){
        items.remove(pos);
        writeStorage();
        MainMenuFrag1.myAdapter.notifyDataSetChanged();     // 프레그먼트 재실행
    }

    // 리스트의 기존 대피소 정보를 수정하는 메서드
    public void update(int pos, Bitmap p_image, String p_shelterName, String p_writer, String p_location,String p_memo){
        items.set(pos, new Item(p_image, p_shelterName, p_writer, p_location,p_memo));  // position 위치의 리스트 정보를 새로 설정
        writeStorage();
        MainMenuFrag1.myAdapter.notifyDataSetChanged();     // 프레그먼트 재실행
    }

    // test.txt에 데이터를 입력할 때 사용하는 메서드
    public void writeStorage() {
        File file = new File("data/user/0/com.example.teamproject2/files", "test.txt");
        FileWriter fw = null ;     // 파일에 데이터를 쓰기위한 핸들러 fw 선언
        BufferedWriter bufwr = null ;  // ★☆파일을 읽기 위한 버퍼 생성(버퍼가 뭔지 잘 모르겠다,,)☆★
        try {
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;
            for (Item item: items) {    // 리스트 데이터들 처음부터 끝까지~
                bufwr.write(item.icon + "," + item.shelterName + "," + item.writer + "," + item.location+","+item.memo) ;  // 데이터 사이마다 구분자 "," 를 추가하며 test.txt 에 입력
                bufwr.newLine();       // 각 대피소 정보마다 줄바꿈을 하여 데이터를 저장.
            }
            bufwr.flush();       // ★☆.flush()가 뭐지???☆★
        } catch (Exception e) {
            e.printStackTrace() ;
        }
    }





    /* public void changeBitmap(String s, ImageView mimageView){
        try {
            String imgpath = getCacheDir() + "/" + s;   // 내부 저장소에 저장되어 있는 이미지 경로
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            mimageView.setImageBitmap(bm);   // 내부 저장소에 저장된 이미지를 이미지뷰에 셋
            Toast.makeText(this, getCacheDir().toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "파일 로드 실패", Toast.LENGTH_SHORT).show();
        }
    }

    // test.txt에서 데이터를 읽어올 때 사용하는 메서드_jb
   public void readStorage()
    {
        File file = new File("data/user/0/com.example.teamproject2/files", "test.txt");  // getFilesDir(): 파일의 전체 저장 경로를 가져오는 메소드
        FileReader fr = null;       // 파일 데이터를 읽기 위한 핸들러 fr 선언.
        BufferedReader bufrd = null;
        String s;
        if (file.exists()) {   // file.exists(): 파일이 존재하는지 검사
            try {
                fr = new FileReader(file);    // fr 을 "file"파일을 읽기 위한 핸들러로 선언.
                bufrd = new BufferedReader(fr);
                while ((s = bufrd.readLine()) != null) {
                    String[] split = new String(s).split(",");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    String imgpath = getAct.getCacheDir() + "/" + split[0];   // 내부 저장소에 저장되어 있는 split[1]은 이미지파일명 (쉴터이름)
                    Bitmap bm = BitmapFactory.decodeFile(imgpath);
                    Storage.items.add(new Item(bm, split[1], split[2], split[3]));
                }
                bufrd.close();
                fr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

}
