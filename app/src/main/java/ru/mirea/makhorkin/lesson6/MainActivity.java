package ru.mirea.makhorkin.lesson6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import ru.mirea.makhorkin.lesson6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPref = getSharedPreferences("MIREA_settings", Context.MODE_PRIVATE);

        binding.editTextGroupNum.setText(sharedPref.getString("GROUP", "Номер группы"));
        binding.editTextSpisokNum.setText(sharedPref.getString("NUMBER", "Номер по списку"));
        binding.editTextBestFilm.setText(sharedPref.getString("FILM", "Любимый фильм или сериал"));
    }


    public void buttonOnClick(View view){
        sharedPref = getSharedPreferences("MIREA_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("GROUP", String.valueOf(binding.editTextGroupNum.getText()));
        editor.putString("NUMBER", String.valueOf(binding.editTextSpisokNum.getText()));
        editor.putString("FILM", String.valueOf(binding.editTextBestFilm.getText()));

        editor.apply();
    }
}