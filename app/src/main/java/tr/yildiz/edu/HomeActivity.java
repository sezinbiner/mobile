package tr.yildiz.edu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView card1,card2,card3,card4,card5;
    public String id;
    public TextView nameText;
    public ImageView userPhoto;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userPhoto = findViewById(R.id.userPhoto);

        card1 = (CardView) findViewById(R.id.c1);
        card2 = (CardView) findViewById(R.id.c2);
        card3 = (CardView) findViewById(R.id.c3);
        card4 = (CardView) findViewById(R.id.c4);
        card5 = (CardView) findViewById(R.id.c5);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);

        nameText = (TextView) findViewById(R.id.nameText);

        sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
        editor = sp.edit();
        DBHelper myDB = new DBHelper(this);

        if(getIntent().hasExtra("username")){
            username = getIntent().getStringExtra("username");
            id = myDB.getUserId(username).toString();
            nameText.setText("Hoşgeldin  \n" + username);
            editor.putString("user_id", id.toString());
            editor.putString("username", username);
            editor.commit();
        }else{
            username = sp.getString("username", "Admin");
            nameText.setText("Hoşgeldin  \n" + username);
        }


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.c1 :
                intent = new Intent(getApplicationContext(),AddQuestionActivity.class);
                intent.putExtra("user_id",id);
                startActivity(intent);
                break;
            case R.id.c2:
                intent = new Intent(this,ListQuestionActivity.class);
                intent.putExtra("user_id",id);
                startActivity(intent);
                break;
            case R.id.c3:
                intent = new Intent(this,ExamSettingsActivity.class);
                intent.putExtra("user_id",id);
                startActivity(intent);
                break;
            case R.id.c4 :
                intent = new Intent(this,CreateExamActivity.class);
                intent.putExtra("user_id",id);
                startActivity(intent);
                break;
            case R.id.c5 :
                editor.clear();
                editor.commit();
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }

}