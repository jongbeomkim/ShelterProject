package com.example.teamproject2;

import android.app.AppComponentFactory;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;

public class Storage extends AppCompatActivity {

    static ArrayList<Item> items;
    public void setSelection(int p_image, String p_shelterName, String p_writer, String p_location){
        items.add(new Item(p_image, p_shelterName, p_writer, p_location));  // 리스트에 대피소정보 추가
        writeStorage();
        MainMenuFrag1.myAdapter.notifyDataSetChanged();     // 프레그먼트 재실행 메소드
    }

    // 리스트의 기존 대피소 정보를 삭제하는 함수.
    public void delete(int pos){
        items.remove(pos);
        writeStorage();
        MainMenuFrag1.myAdapter.notifyDataSetChanged();     // 프레그먼트 재실행 메소드
    }

    // 리스트의 기존 대피소 정보를 수정하는 함수.
    public void update(int pos, int p_image, String p_shelterName, String p_writer, String p_location){
        items.set(pos, new Item(p_image, p_shelterName, p_writer, p_location));  // position 위치의 리스트 정보를 새로 설정
        writeStorage();
        MainMenuFrag1.myAdapter.notifyDataSetChanged();     // 프레그먼트 재실행 메소드
    }

    public void writeStorage() {
        File file = new File("data/user/0/com.example.teamproject2/files", "test.txt");
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
    }

    public void readStorage()
    {

    }
}
