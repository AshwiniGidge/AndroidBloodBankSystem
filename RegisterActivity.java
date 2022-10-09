package in.bbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    String bloodGroups[] = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    String gender[] = {"Male", "Female", "Trans"};
    Spinner spinnerBloodGroup, spinnerGender;
    ArrayAdapter adapterBloodGroup, adapterGender;
    Button btnregi;
    EditText edtfname, edtlname, edtphno,edtuid, edtemail, edtpass, edtcpass, edtstate, edtdistrict, edtcity, edtpincode;

boolean checkall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        checkall=false;
        btnregi = (Button) findViewById(R.id.btn_register);
        edtfname = (EditText) findViewById(R.id.edtfname);
        edtlname = (EditText) findViewById(R.id.edtlname);
        edtphno = (EditText) findViewById(R.id.edtphno);
        edtuid=(EditText)findViewById(R.id.edtuid);
        edtemail=(EditText)findViewById(R.id.edtemail);
        edtpass=(EditText)findViewById(R.id.edtpass);
        edtcpass=(EditText)findViewById(R.id.edtcpass);
        edtstate=(EditText)findViewById(R.id.edtstate);
        edtdistrict=(EditText)findViewById(R.id.edtdistrict);
        edtcity=(EditText)findViewById(R.id.edtcity);
        edtpincode=(EditText)findViewById(R.id.edtpincode);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        spinnerGender = findViewById(R.id.spinnerGender);

        adapterBloodGroup = new ArrayAdapter<String>(this, R.layout.spinner_item, bloodGroups);
        spinnerBloodGroup.setAdapter(adapterBloodGroup);

        adapterGender = new ArrayAdapter<String>(this, R.layout.spinner_item, gender);
        spinnerGender.setAdapter(adapterGender);
        btnregi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
                //Creating a pattern object
                Pattern pattern = Pattern.compile(regex);
                //Creating a Matcher object
                Matcher matcher = pattern.matcher(edtemail.getText().toString());
if(edtfname.getText().toString().equals("") || edtlname.getText().toString().equals("") || edtphno.getText().toString().equals("") || edtemail.getText().toString().equals("")
|| edtpass.getText().toString().equals("") || edtcpass.getText().toString().equals("") || edtstate.getText().toString().equals("") || edtdistrict.getText().toString().equals("") || edtcity.getText().toString().equals("") || edtpincode.getText().toString().equals("") || (!edtpass.getText().toString().equals(edtcpass.getText().toString())) || (!matcher.matches())) {
    if (edtfname.getText().toString().equals("")) {
        edtfname.setError("Please Enter First Name.");
    }
    if (edtlname.getText().toString().equals("")) {
        edtlname.setError("Please Enter Last Name.");
    }
    if (edtphno.getText().toString().equals("")) {
        edtphno.setError("Please Enter Phone Number.");
    }
    if (edtemail.getText().toString().equals("")) {
        edtemail.setError("Please Enter Email Name.");
    }
    if (edtpass.getText().toString().equals("")) {
        edtpass.setError("Please Enter Password.");
    }
    if (edtcpass.getText().toString().equals("")) {
        edtcpass.setError("Please Confirm Password.");
    }
    if (edtstate.getText().toString().equals("")) {
        edtstate.setError("Please Enter State.");
    }
    if (edtdistrict.getText().toString().equals("")) {
        edtdistrict.setError("Please Enter District.");
    }
    if (edtcity.getText().toString().equals("")) {
        edtcity.setError("Please Enter City.");
    }
    if (edtpincode.getText().toString().equals("")) {
        edtpincode.setError("Please Enter Pincode.");
    }
    if (edtuid.getText().toString().equals("")) {
        edtuid.setError("Please Enter Adhar UID.");
    }
    if(!edtpass.getText().toString().equals(edtcpass.getText().toString()))
    {
        edtcpass.setError("Password and Confirmed Passward should be same.");
    }
    if (!matcher.matches()) {
        edtpass.setError("Please Enter Email Correctly.");
    }
}
else
    {
        StringRequest request = new StringRequest(Request.Method.POST, LoginActivity.url +"register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(RegisterActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                        if(response.toLowerCase().equals("User Registered Successfully".toLowerCase()))
                        {
                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                        response=null;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(RegisterActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<String, String>();
                param.put("uid", edtuid.getText().toString());
                param.put("fname", edtfname.getText().toString());
                param.put("lname", edtlname.getText().toString());
                param.put("phno", edtphno.getText().toString());
                param.put("email", edtemail.getText().toString());
                param.put("password", edtpass.getText().toString());
                param.put("bg",bloodGroups[spinnerBloodGroup.getSelectedItemPosition()]);
                param.put("gender", gender[spinnerGender.getSelectedItemPosition()]);
                param.put("state", edtstate.getText().toString());
                param.put("district", edtdistrict.getText().toString());
                param.put("city", edtcity.getText().toString());
                param.put("pincode", edtpincode.getText().toString());

                return param;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(request);

}

            }
        });

    }
}