package tr.yildiz.edu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class ExamSettingsActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    EditText examTime, examNote;
    Spinner examHard;
    Button btnSaveExam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_settings);

        examHard = findViewById(R.id.dropdownHard);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(ExamSettingsActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.hardnessList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        examHard.setAdapter(myAdapter);

        examNote = findViewById(R.id.note_txt);
        examTime = findViewById(R.id.time_txt);
        btnSaveExam = (Button) findViewById(R.id.saveExam);

        sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
        editor = sp.edit();



        try{
            String hard = sp.getString("hard", "-");
            examHard.setSelection(getIndex(hard));
            String time = sp.getString("time", "-");
            examTime.setText(time);
            String note = sp.getString("note", "-");
            examNote.setText(note);

        }catch (Exception e){

        }

        btnSaveExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = examTime.getText().toString();
                String note = examNote.getText().toString();
                String hard = examHard.getSelectedItem().toString();
                editor.putString("time", time);
                editor.putString("note", note);
                editor.putString("hard", hard);
                editor.commit();
                Toast.makeText(ExamSettingsActivity.this, "Ayarlar Güncellendi", Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

    public int getIndex(String answer){
        ArrayList<String> answers = new ArrayList<String>();
        answers.add("2");
        answers.add("3");
        answers.add("4");
        answers.add("5");
        return answers.indexOf(answer);
    }
}

