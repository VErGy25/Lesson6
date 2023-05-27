package ru.mirea.makhorkin.employeedb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import ru.mirea.makhorkin.employeedb.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppDataBase appDataBase = App.getInstance().getDatabase();
        EmployeeDAO employeeDAO = appDataBase.employeeDAO();

        Employee employee = new Employee();
        employee.id = 1;
        employee.name = "Soul-of-Cinder";
        employee.salary = 1000;
        // запись сотрудников в базу
        employeeDAO.insert(employee);

        // Загрузка всех работников
        List<Employee> employees = employeeDAO.getAll();
        // Получение определенного работника с id = 1
        employee = employeeDAO.getByID(1);
        // Обновление полей объекта
        employee.salary = 2000;
        employeeDAO.update(employee);
        Log.d(getClass().getSimpleName(), employee.name + " " + employee.salary);
    }
}