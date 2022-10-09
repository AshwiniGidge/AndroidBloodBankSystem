package in.bbms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class forgotPassword extends AppCompatActivity {

    Button getpassword;
    EditText userEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        userEmail = findViewById(R.id.edtmailid);
        getpassword = findViewById(R.id.btn_getpass);

        getpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(ContextCompat.checkSelfPermission(forgotPassword.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {

                    ActivityCompat.requestPermissions(forgotPassword.this,
                            new String[]  {   Manifest.permission.WRITE_EXTERNAL_STORAGE},101 );

                    // Permission is not granted
                }


                getpassword.setOnClickListener(v -> {

                    if (userEmail.getText().toString().equals("")) {
                        if (userEmail.getText().toString().equals("")) {
                            userEmail.setError("Please Enter Mail id.");
                        }


                    } else {
                        StringRequest request = new StringRequest(Request.Method.POST, LoginActivity.url + "forgotpassword.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        Toast.makeText(forgotPassword.this, "" + response, Toast.LENGTH_SHORT).show();
                                        if (response.equals("logged in successfully")) {
                                            SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("mymailid", userEmail.getText().toString());
                                            editor.apply();

//                                    SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
//                                    String setting = sharedPreferences.getString("keyName", "defaultValue");


                                        }
                                        response = null;
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Toast.makeText(forgotPassword.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> param = new HashMap<String, String>();
                                param.put("userEmail", userEmail.getText().toString());
                                return param;

                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(forgotPassword.this);
                        requestQueue.add(request);

                    }
                });

            }
        });


    }
}