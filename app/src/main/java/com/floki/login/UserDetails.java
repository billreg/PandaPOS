package com.floki.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.floki.MainActivity;
import com.floki.R;
import com.floki.entity.Employee;
import com.floki.entity.ListPreferences;
import com.floki.settings.SettingPreferences;
import com.floki.sqlite.SqliteHelperJarvis;

public class UserDetails extends AppCompatActivity {
    private SettingPreferences settingPreferences;
    private ListPreferences listPreferences;
    public SqliteHelperJarvis sqliteHelperJarvis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        TextView userEmail = findViewById(R.id.user_email);
        TextView userType = findViewById(R.id.user_type);

        TextView closeSesion = findViewById(R.id.close_sesion);
        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getBaseContext());
        settingPreferences = new SettingPreferences(getBaseContext());
        listPreferences = new SettingPreferences(getBaseContext()).getListPreferences();

        Employee employee = listPreferences.getEmployee();
        userEmail.setText(employee.getUsername());
        userType.setText(employee.getUserType());

        closeSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listPreferences.setToken(null);
                settingPreferences.save(listPreferences);
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        });

    }

}