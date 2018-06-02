package com.example.chrisantus.lab6_1;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {

    EditText inputText;
    Button saveButton,readButton,clearButton,finishButton;
    private String filename="mysdfile.txt";
    private String filepath="MyFileStorage";
    File myExternalFile;
    String myData="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText=(EditText)findViewById(R.id.txtData);
        saveButton=(Button)findViewById(R.id.writeBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    FileOutputStream fos=new FileOutputStream(myExternalFile);
                    fos.write(inputText.getText().toString().getBytes());
                    fos.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
                String data=inputText.getText().toString();
                inputText.setText(data);
            }
        });

        readButton=(Button)findViewById(R.id.readBtn);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    FileInputStream fis=new FileInputStream(myExternalFile);
                    DataInputStream in=new DataInputStream(fis);
                    BufferedReader br=new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    while((strLine=br.readLine())!=null){
                        myData=myData+strLine;
                    }
                    in.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
                inputText.setText(myData);
            }
        });

        clearButton=(Button)findViewById(R.id.Clearbtn);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText.setText("");
                Toast.makeText(MainActivity.this,"Done writing SD'"+filename+"'",Toast.LENGTH_SHORT).show();

            }
        });

        finishButton=(Button)findViewById(R.id.Finbtn);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Done writing SD'"+filename+"'",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        if(!isExternalStorageAvailable() || isExternalStorageReadOnly()){
            saveButton.setEnabled(false);
        }else{
            myExternalFile=new File(getExternalFilesDir(filepath),filename);
        }




    }
    private static boolean isExternalStorageReadOnly(){
        String extStorageState=Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)){
            return true;
        }
        return false;
    }
    private static boolean isExternalStorageAvailable(){
        String extStorageState=Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(extStorageState)){
            return true;
        }
        return false;
    }
}
