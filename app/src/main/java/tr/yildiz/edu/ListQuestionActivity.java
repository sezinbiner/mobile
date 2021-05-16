package tr.yildiz.edu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListQuestionActivity extends AppCompatActivity {

    RecyclerView rvQuestions;
    DBHelper myDB;
    ArrayList<String> question;
    ArrayList<String> optionA;
    ArrayList<String> optionB;
    ArrayList<String> optionC;
    ArrayList<String> optionD;
    ArrayList<String> optionE;
    ArrayList<String> answer;
    ArrayList<Integer> ids;
    QuestionsAdapter customAdapter;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_question);


        rvQuestions = findViewById(R.id.rvQuestions);

        myDB = new DBHelper(ListQuestionActivity.this);
        question = new ArrayList<>();
        optionA = new ArrayList<>();
        optionB = new ArrayList<>();
        optionC = new ArrayList<>();
        optionD = new ArrayList<>();
        optionE = new ArrayList<>();
        answer = new ArrayList<>();
        ids = new ArrayList<Integer>();
        storeInArrays();

        customAdapter = new QuestionsAdapter(ListQuestionActivity.this, this, question, this.optionA, this.optionB,
                this.optionC, this.optionD, this.optionE, this.answer, this.ids, id);
        rvQuestions.setAdapter(customAdapter);
        rvQuestions.setLayoutManager(new LinearLayoutManager(ListQuestionActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void storeInArrays() {
        if(getIntent().hasExtra("user_id")) {
            id = getIntent().getStringExtra("user_id");
        }
        Cursor cursor = myDB.readAllData(id);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Kayıt Bulunamadı", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {

                ids.add(cursor.getInt(0));
                question.add(cursor.getString(2));
                optionA.add(cursor.getString(3));
                optionB.add(cursor.getString(4));
                optionC.add(cursor.getString(5));
                optionD.add(cursor.getString(6));
                optionE.add(cursor.getString(7));
                answer.add(cursor.getString(8));
            }

        }
    }
}

