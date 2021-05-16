package tr.yildiz.edu;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ShowExamActivity extends AppCompatActivity {
    TextView examView;
    Button saveExam;
    String exam;

    private static final String FILE_NAME = "example.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exam);

        DBHelper myDB = new DBHelper(this);
        examView = findViewById(R.id.examView);

        if(getIntent().hasExtra("exam")) {
            exam = getIntent().getStringExtra("exam");
            examView.setText(exam);
        }

        saveExam = findViewById(R.id.saveExam);
        saveExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile(exam);
            }
        });
    }

    void writeToFile(String text){

        try {
            File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+File.separator+"MyAppFolder");
            filePath.mkdirs();
            FileOutputStream fos = new FileOutputStream(filePath+"example.txt");
            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(String exam) {
        String text = exam;
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("FILE_NAME", MODE_PRIVATE);
            fos.write(text.getBytes());
            Toast.makeText(this, "Saved to" + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}