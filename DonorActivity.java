package in.bbms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import in.bbms.donor.AdapterDonor;
import in.bbms.donor.ModelDonor;

import static in.bbms.MainActivity.rcvlist;

public class DonorActivity extends AppCompatActivity {

public static Context dct;

    @Override
    public  AssetManager getAssets() {
        return dct.getAssets();
    }

    RecyclerView.LayoutManager layoutManager;
    AdapterDonor adapterDonor;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);
dct=DonorActivity.this;
getSupportActionBar().setTitle("DONATE BLOOD");
if (rcvlist.isEmpty())
{

    getSupportActionBar().setTitle("NO BLOOD REQUEST AVALABLE");
}
        recyclerView = findViewById(R.id.rv_donor);
        layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);




        adapterDonor = new AdapterDonor(DonorActivity.this,rcvlist);

        recyclerView.setAdapter(adapterDonor);

    }

    @Override
    protected void onRestart() {
        adapterDonor.notifyDataSetChanged();
        recyclerView.setAdapter(adapterDonor);
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        rcvlist.clear();
        super.onBackPressed();
    }
}