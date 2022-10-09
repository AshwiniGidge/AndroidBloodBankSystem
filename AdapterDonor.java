package in.bbms.donor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

import in.bbms.InfoActivity;
import in.bbms.LoginActivity;
import in.bbms.MainActivity;
import in.bbms.R;

import static in.bbms.DonorActivity.dct;
import static in.bbms.MainActivity.reciverinfoList;

public class AdapterDonor extends RecyclerView.Adapter<AdapterDonor.ViewHolder> {

    Dialog dialog;

    Context ctx;
    List<ModelDonor> list;

    public AdapterDonor(Context ctx, List<ModelDonor> list){
        this.ctx  = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterDonor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.item_donor,parent,false);




        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDonor.ViewHolder holder, int position) {

        holder.btn_accept.setOnClickListener(v ->{
            dialog(ctx.getString(R.string.donor_dialog_label)+" "+list.get(position).getName()+" ?",position);
            dialog.show();
        });

        holder.tv_name.setText("Name : "+list.get(position).getName());
        holder.tv_bloodGroup.setText("B.G : "+list.get(position).getBlood());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name,tv_bloodGroup;
        Button btn_accept;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_bloodGroup = itemView.findViewById(R.id.tv_bloodGroup);
            btn_accept = itemView.findViewById(R.id.btn_accept);

        }
    }

    private void dialog(String text,int posi){
        dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.dialog_receive);
        dialog.setCancelable(false);
        TextView tv_label = (TextView) dialog.findViewById(R.id.tv_label);
        tv_label.setText(text);
        TextView btnYes = (TextView) dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(v -> {



            StringRequest request = new StringRequest(Request.Method.POST, LoginActivity.url +"donblood.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d("mnv",response);
                              //  Toast.makeText(dct, "" + response, Toast.LENGTH_SHORT).show();

                                        // When timer is finished

                                        JSONArray ja = new JSONArray(response);

                                        JSONObject job = ja.getJSONObject(0);

                                       reciverinfoList.add(job.getString("fname")+" "+job.getString("lname"));
                                        reciverinfoList.add(job.getString("phno"));
                                        reciverinfoList.add(job.getString("emailid"));
                                        reciverinfoList.add(job.getString("bloodgroup"));
                                        reciverinfoList.add(job.getString("city"));
                                        reciverinfoList.add(job.getString("district"));
                                        reciverinfoList.add(job.getString("state"));
                                        reciverinfoList.add(job.getString("pincode"));
                                        Intent i=new Intent(dct, InfoActivity.class);
                                        dct.startActivity(i);



                                        // Execute your code here


                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(dct, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    SharedPreferences sharedPreferences = dct.getSharedPreferences("myinfo", Context.MODE_PRIVATE);
                    String ruid=MainActivity.rcvlist.get(posi).id;
                    String srno=MainActivity.rcvlist.get(posi).getsrno();
                    String dbg = sharedPreferences.getString("mybg", "B+");
                    String dname= sharedPreferences.getString("myname", "no name");

                    String duid = sharedPreferences.getString("myuid", "404");

                    Map<String, String> param = new HashMap<String, String>();
                    param.put("ruid", ruid);
//                    Log.d("srmn: ",srno);
                   // Log.d("rud: ",ruid);
                    param.put("srno",srno);
                    param.put("duid", duid);
                    param.put("dname", dname);
                    param.put("dbg", dbg);


                    return param;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(dct);
            requestQueue.add(request);



            dialog.dismiss();
            Toast.makeText(ctx, "Requested Accepted", Toast.LENGTH_SHORT).show();
        });
        TextView btnNo = (TextView) dialog.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
