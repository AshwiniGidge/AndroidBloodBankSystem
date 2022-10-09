package in.bbms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.bbms.donor.ModelDonor;

public class LoginActivity extends AppCompatActivity {
    Button btnregister, btnlogin, btnforgotPass;
    EditText edtlgnid, edtpass;
    public static String url = "https://bbmsapp.000webhostapp.com/bbms/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        btnregister = (Button) findViewById(R.id.btn_register);
        btnlogin = (Button) findViewById(R.id.btn_login);
//      btnforgotPass=(Button) findViewById(R.id.btn_forgotPass);
        edtlgnid = (EditText) findViewById(R.id.edtlgnid);
        edtpass = (EditText) findViewById(R.id.edtlpass);


        if(ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(LoginActivity.this,
                 new String[]  {   Manifest.permission.WRITE_EXTERNAL_STORAGE},101 );

            // Permission is not granted
        }


        btnregister.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);


        });

/*        btnforgotPass.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, forgotPassword.class);
            startActivity(i);

        });
*/

        btnlogin.setOnClickListener(v -> {

            if (edtlgnid.getText().toString().equals("") || edtpass.getText().toString().equals("") ) {
                if (edtlgnid.getText().toString().equals("")) {
                    edtlgnid.setError("Please Enter UserName(UID).");
                }
                if (edtpass.getText().toString().equals("")) {
                    edtpass.setError("Please Enter Password.");
                }


            } else {
                StringRequest request = new StringRequest(Request.Method.POST, url + "login.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(LoginActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                                if (response.equals("logged in successfully")) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("myuid", edtlgnid.getText().toString());
                                    editor.apply();

//                                    SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
//                                    String setting = sharedPreferences.getString("keyName", "defaultValue");

                                    takedata();

                                }
                                response = null;
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(LoginActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> param = new HashMap<String, String>();
                        param.put("username", edtlgnid.getText().toString());
                        param.put("password", edtpass.getText().toString());
                        return param;

                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(request);

            }
        });

    }
void takedata()
{

    StringRequest request = new StringRequest(Request.Method.POST, url+"getmyinfo.php",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
            String myfname,mylname,mybg;
                   // Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                    try {

                        JSONArray ja=new JSONArray(response);

                            JSONObject job= ja.getJSONObject(0);
                           // dob=new ModelDonor(String.valueOf(job.getInt("srno")),job.getString("ruid"),job.getString("rname"),job.getString("rbg"));
                        SharedPreferences sharedPreferences1 = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                        editor1.putString("myname", job.getString("fname")+" "+job.getString("lname"));
                        editor1.putString("mybg", job.getString("bloodgroup"));
                        editor1.apply();




                        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("firstStart", false);
                        editor.apply();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(LoginActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {

            Map<String,String> param = new HashMap<String,String>();
            param.put("query","SELECT fname,lname,bloodgroup FROM users WHERE uid ='"+edtlgnid.getText().toString()+ "';");
            //param.put("query","SELECT * FROM tasks WHERE rbg ='"+mybg+ "'OR rbg ='" +obg+"';");


            return param;

        }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
    requestQueue.add(request);


}
}