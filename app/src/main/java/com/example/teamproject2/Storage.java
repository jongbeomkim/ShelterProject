package com.example.teamproject2;

import android.app.AppComponentFactory;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import static com.example.teamproject2.MainMenuFrag1.items;
public class Storage extends AppCompatActivity {
    public void update() {
        File file = new File( getFilesDir(), "test.txt");
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
}
