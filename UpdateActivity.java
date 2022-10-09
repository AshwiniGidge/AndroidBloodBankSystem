package in.bbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static in.bbms.MainActivity.mifu;
import static in.bbms.MainActivity.rcvlist;
import static in.bbms.MainActivity.reciverinfoList;

public class UpdateActivity extends AppCompatActivity {
    String bloodGroups[] = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    String gender[] = {"Male", "Female", "Trans"};
    List<String> bg=new ArrayList<>();
    List<String> gen=new ArrayList<>();
    Spinner spinnerBloodGroup, spinnerGender;
    ArrayAdapter adapterBloodGroup, adapterGender;
    Button upd;
    EditText edtfname, edtlname, edtphno, edtemail, edtpass, edtcpass, edtstate, edtdistrict, edtcity, edtpincode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        for(int i=0;i<bloodGroups.length-1;i++)
        {
            bg.add(bloodGroups[i]);

        }
        for(int i=0;i<gender.length-1;i++)
        {
            gen.add(gender[i]);

        }
        upd = (Button) findViewById(R.id.btn_upd);
        edtfname = (EditText) findViewById(R.id.edtfname);
        edtlname = (EditText) findViewById(R.id.edtlname);
        edtphno = (EditText) findViewById(R.id.edtphno);

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
        edtfname.setText(mifu.get(0));
        edtlname.setText(mifu.get(1));
        edtphno.setText(mifu.get(2));
        edtemail.setText(mifu.get(3));
        edtpass.setText(mifu.get(4));
        edtcpass.setText(mifu.get(4));
        spinnerBloodGroup.setSelection(bg.indexOf(mifu.get(5)));
        spinnerGender.setSelection(gen.indexOf(mifu.get(6)));
        edtstate.setText(mifu.get(7));
        edtdistrict.setText(mifu.get(8));
        edtcity.setText(mifu.get(9));
        edtpincode.setText(mifu.get(10));








        upd.setOnClickListener(v -> {
            String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
            //Creating a pattern object
            Pattern pattern = Pattern.compile(regex);
            //Creating a Matcher object
            Matcher matcher = pattern.matcher(edtemail.getText().toString());
            if(edtfname.getText().toString().equals("") || edtlname.getText().toString().equals("") || edtphno.getText().toString().equals("") || edtphno.getText().toString().equals("") || edtemail.getText().toString().equals("")
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
                StringRequest request = new StringRequest(Request.Method.POST, MainActivity.url1 +"updateinfo.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(UpdateActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                                if(response.toLowerCase().equals("user updated sucessfully".toLowerCase()))
                                {
                                    SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
                                    sharedPreferences.edit().remove("myname").apply();
                                    sharedPreferences.edit().remove("mybg").apply();
                                    sharedPreferences.edit().remove("myuid").apply();
                                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.remove("firstStart").apply();
reciverinfoList.clear();
rcvlist.clear();
mifu.clear();
                                    Intent i = new Intent(UpdateActivity.this, LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                                response=null;
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(UpdateActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
                        Map<String, String> param = new HashMap<String, String>();
                        param.put("uid", sharedPreferences.getString("myuid","000"));
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

                RequestQueue requestQueue = Volley.newRequestQueue(UpdateActivity.this);
                requestQueue.add(request);

            }



        });
    }
}