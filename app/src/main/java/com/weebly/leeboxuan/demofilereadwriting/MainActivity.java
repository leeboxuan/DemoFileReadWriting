package com.weebly.leeboxuan.demofilereadwriting;

import android.Manifest;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    String folderLocation;
    Button btnRead, btnWrite;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnWrite = (Button) findViewById(R.id.btnWrite);
        tv = (TextView) findViewById(R.id.tv);

        folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Testing1";
        File file = new File(folderLocation);


        if (file.exists() == false) {
            boolean result = file.mkdir();
            if (result == true) {
                Log.d("File Read/Write", "Folder created");
            }
        }

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission() == true) {
                    try {
                        folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Testing1";
                        File targetFile_I = new File(folderLocation, "test.txt");
                        FileWriter writer_I = new FileWriter(targetFile_I, true);
                        writer_I.write("test data" + "\n");
                        writer_I.flush();
                        writer_I.close();
                    }catch(Exception e){
                        Toast.makeText(MainActivity.this, "Failed to write!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Failed to permit!", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, 0);


                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission() == true) {
                    String folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Testing1";

                    File targetFile = new File(folderLocation, "test.txt");
                    if (targetFile.exists() == true){
                        String data = "";
                        try {
                            FileReader reader = new FileReader(targetFile);
                            BufferedReader br = new BufferedReader(reader);

                            String line = br.readLine();
                            while (line != null) {
                                data += line + "\n";
                                line = br.readLine();

                            }
                            tv.setText(data);
                            br.close();
                            reader.close();
                        }catch(Exception e){
                            Toast.makeText(MainActivity.this, "Failed to read!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        Log.d("Content", "data");
                    }

                }else{
                    Toast.makeText(MainActivity.this, "Failed to permit!", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, 0);


                }
            }
        });
    }




    private boolean checkPermission() {
        int permissionCheck_readStore = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck_writeStore = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck_writeStore == PermissionChecker.PERMISSION_GRANTED
                || permissionCheck_readStore == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
}
