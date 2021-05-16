package tr.yildiz.edu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    Button btnLogin,btnRegister;
    DBHelper myDB;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.userNameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        btnLogin = (Button) findViewById((R.id.btnLogin));
        btnRegister = (Button) findViewById((R.id.btnSignUpInLogin));

        myDB = new DBHelper(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if(count<2){
                    if(user.equals("") || pass.equals("")){
                        Toast.makeText(MainActivity.this, "Lütfen Tüm Alanları Doldurunuz", Toast.LENGTH_SHORT).show();
                    }else{
                        Boolean signInResult = myDB.checkusernamePassword(user,pass);
                        if(signInResult){
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("username", user);
                            startActivity(intent);
                            finish();

                        }else{
                            count = count + 1;
                            Toast.makeText(MainActivity.this, "Hatalı Giriş " + count , Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    btnLogin.setClickable(false);
                    btnLogin.setEnabled(false);
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intent);                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}