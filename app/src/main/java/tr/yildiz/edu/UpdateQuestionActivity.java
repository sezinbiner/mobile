package tr.yildiz.edu;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class UpdateQuestionActivity extends AppCompatActivity {

    EditText update_question, update_a, update_b, update_c, update_d, update_e;
    Spinner update_dropdown;
    Button updateBtnSaveQuestion, updateBtnUpload, btnDeleteQuestion;

    String question, a, b, c, d, e, answer;
    String index;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_question);

        if(getIntent().hasExtra("user_id")) {
            id = getIntent().getStringExtra("user_id");
        }

        update_question = findViewById(R.id.update_question);
        update_a = findViewById(R.id.update_a);
        update_b = findViewById(R.id.update_b);
        update_c = findViewById(R.id.update_c);
        update_d = findViewById(R.id.update_d);
        update_e = findViewById(R.id.update_e);
        update_dropdown = findViewById(R.id.update_dropdown);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(UpdateQuestionActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.answersList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        update_dropdown.setAdapter(myAdapter);


        updateBtnSaveQuestion = findViewById(R.id.updateBtnSaveQuestion);
        getAndSetIntentData();
        updateBtnSaveQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper myDB = new DBHelper(UpdateQuestionActivity.this);
                question = update_question.getText().toString();
                a = update_a.getText().toString();
                b = update_b.getText().toString();
                c = update_c.getText().toString();
                d = update_d.getText().toString();
                e = update_e.getText().toString();
                answer = update_dropdown.getSelectedItem().toString();
                if (a.equals("") || b.equals("") || c.equals("") || d.equals("") || e.equals("") || answer.equals("") || question.equals("")) {
                    Toast.makeText(UpdateQuestionActivity.this, "Lütfen Tüm Alanları Doldurunuz", Toast.LENGTH_SHORT).show();
                }else{
                    int result = myDB.updateData(question, a, b, c, d, e, answer, index);
                    if (result == -1) {
                        Toast.makeText(UpdateQuestionActivity.this, "Güncelleme Başarısız", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdateQuestionActivity.this, "Güncelleme Başarılı", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ListQuestionActivity.class);
                        intent.putExtra("user_id", id);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        btnDeleteQuestion = findViewById(R.id.btnDeleteQuestion);
        btnDeleteQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("question") && getIntent().hasExtra("a") &&
                getIntent().hasExtra("b") && getIntent().hasExtra("c") && getIntent().hasExtra("d") &&
                getIntent().hasExtra("e") && getIntent().hasExtra("answer")) {
            index = getIntent().getStringExtra("id");
            update_question.setText(getIntent().getStringExtra("question"));
            update_a.setText(getIntent().getStringExtra("a"));
            update_b.setText(getIntent().getStringExtra("b"));
            update_c.setText(getIntent().getStringExtra("c"));
            update_d.setText(getIntent().getStringExtra("d"));
            update_e.setText(getIntent().getStringExtra("e"));
            String oldAnswer = getIntent().getStringExtra("answer");
            update_dropdown.setSelection(getIndex(oldAnswer));

        } else {
            Toast.makeText(this, "Kayıt Bulunamadı", Toast.LENGTH_SHORT).show();
        }
    }


    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper myDB = new DBHelper(UpdateQuestionActivity.this);
                int result = (int) myDB.deleteOneRow(index);
                if (result == -1) {
                    Toast.makeText(UpdateQuestionActivity.this, "Silme Başarısız", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateQuestionActivity.this, "Silme Başarılı", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    public int getIndex(String answer){
        ArrayList<String> answers = new ArrayList<String>();
        answers.add("A");
        answers.add("B");
        answers.add("C");
        answers.add("D");
        answers.add("E");
        return answers.indexOf(answer);
    }

}

