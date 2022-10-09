package in.bbms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import in.bbms.R;
import in.bbms.history.AdapterHistory;
import in.bbms.history.ModelHistory;

import static in.bbms.MainActivity.hislist;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    AdapterHistory adapterHistory;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setTitle("HISTORY");
        recyclerView = findViewById(R.id.rv_history);

        layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

if(hislist.isEmpty())
{
    getSupportActionBar().setTitle("NO HISTORY AVALABLE");

}

        adapterHistory = new AdapterHistory(HistoryActivity.this,hislist);
        recyclerView.setAdapter(adapterHistory);

    }

    @Override
    public void onBackPressed() {
        hislist.clear();
        super.onBackPressed();
    }
}