package in.bbms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.bbms.donor.ModelDonor;
import in.bbms.history.ModelHistory;

import static in.bbms.DonorActivity.dct;

public class MainActivity extends AppCompatActivity {
    public static List<String> reciverinfoList=new ArrayList<>();
    public static List<String> mifu=new ArrayList<>();
   public static List<ModelDonor> rcvlist = new ArrayList<>();
    Dialog dialog;
    TextView tvname,tvuid;
    public static List<ModelHistory> hislist = new ArrayList<>();
    public static String url1 = "https://bbmsapp.000webhostapp.com/bbms/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("myname", "");
        String uid = sharedPreferences.getString("myuid", "");
        tvname=(TextView)findViewById(R.id.tvmynamedisp);
        tvuid=(TextView)findViewById(R.id.tvmyuiddisp);
        tvname.append(name);
        tvuid.append(uid);


        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_receive);
        dialog.setCancelable(false);
        TextView tv_label = (TextView) dialog.findViewById(R.id.tv_label);
        tv_label.setText(getString(R.string.main_dialog_label));
        TextView btnYes = (TextView) dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(v -> {



            reqblood();
            dialog.dismiss();


        });
        TextView btnNo = (TextView) dialog.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        findViewById(R.id.btn_bDonor).setOnClickListener(v->{

            StringRequest request = new StringRequest(Request.Method.POST, url1+"recivelist.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                           //    Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                            try {
                                SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);

                                ModelDonor dob=new ModelDonor();
                                JSONArray ja=new JSONArray(response);
                                for (int i=0;i<ja.length();i++)
                                {
                                    JSONObject job= ja.getJSONObject(i);


                                        dob = new ModelDonor(String.valueOf(job.getInt("srno")), job.getString("ruid"), job.getString("rname"), job.getString("rbg"));


                                        rcvlist.add(dob);


                                }

                                Intent i = new Intent(MainActivity.this, DonorActivity.class);

                                startActivity(i);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
                                String bg = sharedPreferences.getString("mybg", "B+");
                    String mybg=bg;

                    String obg;
                    if(mybg.contains("+"))
                    {
                        obg="O+";
                    }
                    else
                    {
                        obg="O-";
                    }
                    String myuid = sharedPreferences.getString("myuid", "000");
                    Map<String,String> param = new HashMap<String,String>();

                  param.put("mybg",mybg);
                    param.put("obg",obg);
                    param.put("myuid",myuid);

                    //param.put("query","SELECT * FROM tasks WHERE rbg ='"+mybg+ "'OR rbg ='" +obg+"';");


                    return param;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(request);

        });

        findViewById(R.id.btn_bReciever).setOnClickListener(v->{
            dialog.show();
        });

        findViewById(R.id.btn_history).setOnClickListener(v->{
            StringRequest request = new StringRequest(Request.Method.POST, url1+"showhistory.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                           // Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                            try {
                                SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
                                String task,bg;
                                ModelHistory dob=new ModelHistory();
                                JSONArray ja=new JSONArray(response);
                                for (int i=0;i<ja.length();i++)
                                {
                                    JSONObject job= ja.getJSONObject(i);

                                    String myuid = sharedPreferences.getString("myuid", "000");
                                    if(job.getString("ruid").equals(myuid))
                                    {
                                        task="Reciver";

                                    }
                                    else
                                    {
                                        task="Donate blood";
                                    }
                                    if(job.getString("dbg").equals("null"))
                                    {
                                        bg="Pending";
                                    }
                                    else
                                    {
                                        bg=job.getString("dbg");
                                    }


                                    dob = new ModelHistory(job.getString("date"), task, bg, job.getString("status"));


                                   hislist.add(dob);


                                }

                                Intent i = new Intent(MainActivity.this, HistoryActivity.class);

                                startActivity(i);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);



                    String myuid = sharedPreferences.getString("myuid", "000");
                    Map<String,String> param = new HashMap<String,String>();


                    param.put("myuid",myuid);

                    //param.put("query","SELECT * FROM tasks WHERE rbg ='"+mybg+ "'OR rbg ='" +obg+"';");


                    return param;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(request);



        });


        //
        //
        //
        //for geting info for update
        findViewById(R.id.btn_update_my_info).setOnClickListener(v->{

            StringRequest request = new StringRequest(Request.Method.POST, url1+"getinfoupd.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String myfname,mylname,mybg;
                            // Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                            try {

                                JSONArray ja=new JSONArray(response);

                                JSONObject job= ja.getJSONObject(0);
                                mifu.add(job.getString("fname"));
                                mifu.add(job.getString("lname"));
                                mifu.add(job.getString("phno"));
                                mifu.add(job.getString("emailid"));
                                mifu.add(job.getString("password"));
                                mifu.add(job.getString("bloodgroup"));
                                mifu.add(job.getString("gender"));
                                mifu.add(job.getString("state"));
                                mifu.add(job.getString("district"));
                                mifu.add(job.getString("city"));
                                mifu.add(job.getString("pincode"));

                                Intent i = new Intent(MainActivity.this, UpdateActivity.class);

                                startActivity(i);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
                  //  String bg = sharedPreferences.getString("mybg", "B+");
                    Map<String,String> param = new HashMap<String,String>();
                    param.put("uid",sharedPreferences.getString("myuid", "000"));
                    //param.put("query","SELECT * FROM tasks WHERE rbg ='"+mybg+ "'OR rbg ='" +obg+"';");


                    return param;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(request);

        });


findViewById(R.id.btn_Logout).setOnClickListener(v->{

    SharedPreferences sharedPreferences1 = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
    sharedPreferences1.edit().remove("myname").apply();
    sharedPreferences1.edit().remove("mybg").apply();
    sharedPreferences1.edit().remove("myuid").apply();
    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
    SharedPreferences.Editor editor = prefs.edit();
    editor.remove("firstStart").apply();

    reciverinfoList.clear();
    rcvlist.clear();
    mifu.clear();


    Intent i = new Intent(MainActivity.this, LoginActivity.class);
    startActivity(i);
    finish();
});

    }
    void reqblood()

    {

        StringRequest request = new StringRequest(Request.Method.POST, LoginActivity.url +"reqblood.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(MainActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
                String rbg = sharedPreferences.getString("mybg", "B+");
                String rname= sharedPreferences.getString("myname", "no name");
                Calendar cal=Calendar.getInstance();
                SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
                String date = df.format(cal.getTime());
                String ruid = sharedPreferences.getString("myuid", "404");
                String status="pending";
                Map<String, String> param = new HashMap<String, String>();
                param.put("ruid", ruid);
                param.put("rname", rname);
                param.put("date", date);
                param.put("rbg", rbg);
                param.put("status", status);


                return param;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);


    }
    @Override
    protected void onStart() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart)
        {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        rcvlist.clear();
        hislist.clear();
        super.onBackPressed();
    }
}