package tr.yildiz.edu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddQuestionActivity extends AppCompatActivity {

    EditText questionText, aText, bText, cText, dText, eText;
    Button btnQuestion, btnUpload;
    Spinner spinner;
    DBHelper myDB;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        if(getIntent().hasExtra("user_id")){
            id = getIntent().getStringExtra("user_id");

            questionText = (EditText) findViewById(R.id.questionText);
            aText = (EditText)findViewById(R.id.a);
            bText = (EditText)findViewById(R.id.b);
            cText = (EditText)findViewById(R.id.c);
            dText = (EditText)findViewById(R.id.d);
            eText = (EditText)findViewById(R.id.e);
            spinner = (Spinner)findViewById(R.id.dropdown);

            btnQuestion = (Button)findViewById(R.id.btnSaveQuestion);

            myDB = new DBHelper(this);

            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddQuestionActivity.this,
                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.answersList));
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(myAdapter);

            btnQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String question = questionText.getText().toString();
                    String a = aText.getText().toString();
                    String b = bText.getText().toString();
                    String c = cText.getText().toString();
                    String d = dText.getText().toString();
                    String e = eText.getText().toString();
                    String answer = spinner.getSelectedItem().toString();

                    if (a.equals("") || b.equals("") || c.equals("") || d.equals("") || e.equals("") || answer.equals("") || question.equals("")) {
                        Toast.makeText(AddQuestionActivity.this, "Lütfen Tüm Alanları Doldurun", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean result = myDB.insertQuestion(Integer.parseInt(id), question, a, b, c, d, e, answer);
                        if (result == true) {
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException exception) {
                                exception.printStackTrace();
                            }
                            Toast.makeText(AddQuestionActivity.this, "Soru Kaydedildi", Toast.LENGTH_SHORT).show();
                            intent.putExtra("user_id", id);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AddQuestionActivity.this, "HATA: Soru Kaydedilemedi", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }


}