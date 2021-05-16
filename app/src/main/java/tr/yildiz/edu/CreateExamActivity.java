package tr.yildiz.edu;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CreateExamActivity extends AppCompatActivity {
    RecyclerView multiple_rv;
    DBHelper myDB;
    MultiAdapter customAdapter;
    ArrayList<Question> data = new ArrayList<>();
    Button btnExam, btnSetting;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exam);

        if(getIntent().hasExtra("user_id")) {
            id = getIntent().getStringExtra("user_id");
        }

        multiple_rv = findViewById(R.id.multiple_rv);
        btnExam = findViewById(R.id.createExam);
        btnSetting =findViewById(R.id.changeSettings);

        myDB = new DBHelper(CreateExamActivity.this);
        storeInArrays();

        customAdapter = new MultiAdapter(CreateExamActivity.this, this, data);
        multiple_rv.setAdapter(customAdapter);
        multiple_rv.setLayoutManager(new LinearLayoutManager(CreateExamActivity.this));
        multiple_rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }



        btnExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),ShowExamActivity.class);
                intent.putExtra("exam",createExam().toString());
                startActivity(intent);
                }
            }
        );

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ExamSettingsActivity.class);
                startActivity(intent);
            }
        });

    }



    void storeInArrays() {
        Cursor cursor = myDB.readAllData(id);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Kayıt Bulunamadı", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                data.add(new Question(String.valueOf(cursor.getInt(0)), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5),
                        cursor.getString(6), cursor.getString(7), cursor.getString(8)));
            }

        }
    }

    StringBuilder createExam() {

        SharedPreferences prefs = this.getSharedPreferences("UserInfo", MODE_PRIVATE);
        String hard = prefs.getString("hard", null);
        StringBuilder stringBuilder = new StringBuilder();
        if (customAdapter.getSelected().size() > 0) {
            for (int i = 0; i < customAdapter.getSelected().size(); i++) {
                int numberOfOptions = Integer.parseInt(hard)-1;
                ArrayList<String> answers = new ArrayList<>();
                stringBuilder.append("\n");
                stringBuilder.append((i +1) + ")  " + customAdapter.getSelected().get(i).getQuestion());
                stringBuilder.append("\n \n");
                ArrayList<String> options = new ArrayList<String>();
                options.add("A");
                options.add("B");
                options.add("C");
                options.add("D");
                options.add("E");
                switch (customAdapter.getSelected().get(i).getAnswer()){
                    case "A":
                        answers.add(customAdapter.getSelected().get(i).getA());
                        options.remove("A");
                        break;
                    case "B":
                        answers.add(customAdapter.getSelected().get(i).getB());
                        options.remove("B");
                        break;
                    case "C":
                        answers.add(customAdapter.getSelected().get(i).getC());
                        options.remove("C");
                        break;
                    case "D":
                        answers.add(customAdapter.getSelected().get(i).getD());
                        options.remove("D");
                        break;
                    case "E":
                        answers.add(customAdapter.getSelected().get(i).getE());
                        options.remove("E");
                        break;
                }
                if (hard != null) {
                    int j = 0;
                    while(j < numberOfOptions){
                        numberOfOptions = numberOfOptions-1;
                        switch (options.get(j)){
                            case "A":
                                answers.add(customAdapter.getSelected().get(i).getA());
                                options.remove("A");
                                break;
                            case "B":
                                answers.add(customAdapter.getSelected().get(i).getB());
                                options.remove("B");
                                break;
                            case "C":
                                answers.add(customAdapter.getSelected().get(i).getC());
                                options.remove("C");
                                break;
                            case "D":
                                answers.add(customAdapter.getSelected().get(i).getD());
                                options.remove("D");
                                break;
                            case "E":
                                answers.add(customAdapter.getSelected().get(i).getE());
                                options.remove("E");
                                break;
                        }
                    }
                    Collections.shuffle(answers);
                    char x='A';
                    for(int k = 0;k<answers.size();k++){
                        stringBuilder.append(x + ")  "  + answers.get(k));
                        stringBuilder.append("\n");
                        x+=1;
                    }
                }
            }
        }else {
        Toast.makeText(CreateExamActivity.this, "Kayıt Bulunamadı", Toast.LENGTH_SHORT).show();
        }
        return stringBuilder;
    }
}

