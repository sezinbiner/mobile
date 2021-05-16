package tr.yildiz.edu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText username,password,repassword;
    Button btnSignUp,btnSignIn,btnAddPhoto;
    DBHelper myDB;
    ImageView image;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private boolean isImageUploaded = false;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        repassword = (EditText)findViewById(R.id.repassword);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnAddPhoto = (Button) findViewById(R.id.addPhoto);

        image = findViewById(R.id.img_signup);

        myDB = new DBHelper(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("") || pass.equals("") || repass.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Lütfen Tüm Alanları Doldurunuz", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(repass)) {
                        Boolean userCheckResult = myDB.checkusername(user);
                        if (userCheckResult == false && isImageUploaded == false) {
                            Boolean regResult = myDB.insertData(user, pass);
                            if (regResult == true) {
                                Toast.makeText(RegisterActivity.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("keyname", user);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Kayıt Başarısız", Toast.LENGTH_SHORT).show();
                            }
                        } else if (userCheckResult == false && isImageUploaded == true) {
                            Boolean regResult = myDB.insertDataWithİmage(user, pass, uri);
                            if (regResult == true) {
                                Toast.makeText(RegisterActivity.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("keyname", user);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Kayıtlı Kullanıcı", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Şifreler Uyuşmuyor. Lütfen Tekrar Giriş Yapınız", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        ActivityCompat.requestPermissions(RegisterActivity.this, permissions, REQUEST_CODE_STORAGE_PERMISSION);
                    }else{
                        pickImageFromGallery();
                    }
                }else{
                    pickImageFromGallery();
                }

                isImageUploaded = true;
            }
        });
    }

    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }else{
                    Toast.makeText(this, "İzin Verilmedi", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            uri = data.getData();
            image.setImageURI(uri);
        }
    }
}