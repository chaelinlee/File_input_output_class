package com.example.leechaelin.file_input_ouput;

import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Checkpermission();
        et = (EditText)findViewById(R.id.edittext);
    }
    public void myclick(View v){
        if(v.getId()==R.id.btn1){
            try{
                BufferedReader rd=new BufferedReader(new FileReader(getFilesDir()+"test.txt"));
                String readStr="";
                String str = null;
                while((str=rd.readLine())!=null)
                    readStr +=str+"\n";
                rd.close();
                Toast.makeText(getApplicationContext(),readStr.substring(0,readStr.length()-1),Toast.LENGTH_SHORT).show();
            }catch(FileNotFoundException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"File not found",Toast.LENGTH_SHORT).show();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        else if(v.getId()==R.id.btn2){
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir()+"test.txt",true));
                bw.write("안녕하세요!");
                bw.close();
                Toast.makeText(getApplicationContext(),"저장완료",Toast.LENGTH_SHORT).show();
            }catch(IOException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        else if(v.getId()==R.id.btn3){
            try{
                InputStream is = getResources().openRawResource(R.raw.about);
                byte[] readStr=new byte[is.available()];
                is.read(readStr);
                is.close();
                Toast.makeText(this,new String(readStr),Toast.LENGTH_SHORT).show();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        else if(v.getId()==R.id.btn4){
            try{
                String path = getExternalPath();
                BufferedReader br = new BufferedReader(new FileReader(path+"text.txt"));
                String readStr="";
                String str = null;
                while((str=br.readLine())!=null)
                    br.close();
                Toast.makeText(this,readStr.substring(0,readStr.length()-1),Toast.LENGTH_SHORT).show();
            } catch(FileNotFoundException e){
                e.printStackTrace();
                Toast.makeText(this,"File not found",Toast.LENGTH_SHORT).show();
            }catch (IOException e){
                e.printStackTrace();
            }


        }
        else if(v.getId()==R.id.btn5){
            try{
                String path = getExternalPath();
                BufferedWriter bw = new BufferedWriter(new FileWriter(path+"mydiary/"+"text.txt",true));
                bw.write("안녕하세요 SDcard Hello ");
                bw.close();
                Toast.makeText(getApplicationContext(),"저장완료 ",Toast.LENGTH_SHORT).show();
            }catch (IOException e){
                e.printStackTrace();
                Toast.makeText(this,e.getMessage()+":"+getFilesDir(),Toast.LENGTH_SHORT).show();
            }
        }
        else if(v.getId()==R.id.btn6){
            String path = getExternalPath();
            File file = new  File(path+"mydiary");
            file.mkdir();

            String msg="디렉터리 생성";
            if(file.isDirectory()==false)
                msg = "디렉터리 생성 오류";
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();

        }else if(v.getId()==R.id.btn7){
            String path = getExternalPath();
            File [] files = new File(path+"mydiary").listFiles();

            String str ="";
            for(File f:files)
                str += f.getName()+"\n";
            et.setText(str);
        }

    }

    public void Checkpermission(){
        int permissioninfo = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permissioninfo== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),"SDCard 쓰기 권한 있음 ",Toast.LENGTH_SHORT).show();
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Toast.makeText(getApplicationContext(),"권한의 필요성 설명 ",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }else{
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String str = null;
        if(requestCode==100){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                str = "SD Card 쓰기 권한 승인 ";
            else
                str ="SD Card 쓰기 권한 거부";
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public String getExternalPath(){
        String sdPath="";
        String ext = Environment.getExternalStorageState();
        if(ext.equals(Environment.MEDIA_MOUNTED)){
            sdPath=Environment.getExternalStorageDirectory().getAbsolutePath()+ "/";
        }else
            sdPath=getFilesDir()+"";
        Toast.makeText(getApplicationContext(),sdPath,Toast.LENGTH_SHORT).show();
        return sdPath;
    }
}
