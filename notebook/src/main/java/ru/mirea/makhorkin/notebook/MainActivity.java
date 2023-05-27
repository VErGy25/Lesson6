package ru.mirea.makhorkin.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import ru.mirea.makhorkin.notebook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    ActivityMainBinding binding;

    EditText citata;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalStorageWritable()){
                    fileName = binding.editTextFileName.getText().toString();
                    citata = binding.editTextCitata;
                    writeFileToExternalStorage(fileName, citata);
                }else {
                    Log.d(TAG, "No permission");
                }
            }
        });

        binding.buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExternalStorageReadable()){
                    fileName = binding.editTextFileName.getText().toString();
                    citata = binding.editTextCitata;
                    readFileToExternalStorage(fileName, citata);
                }
            }
        });


    }

    public boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            return true;
        }
        return false;
    }

    public void writeFileToExternalStorage(String fileName, EditText data){
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(data.getText().toString());
            outputStreamWriter.close();
            Log.w("wExternalStorage", " Write file %s successful ");
        } catch (IOException e) {
            Log.w("wExternalStorage", " Write file %s failed " + file, e);
        }
    }

    public void readFileToExternalStorage(String fileName, EditText data){
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, fileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                lines.add(line);
                citata.setText(line);
                line = reader.readLine();
            }
            Log.w("rExternalStorage", String.format(" Read file %s successful", lines.toString()));
        } catch (IOException e) {
            Log.w("rExternalStorage", String.format(" Read file %s failed", e.getMessage()));
        }
    }
}