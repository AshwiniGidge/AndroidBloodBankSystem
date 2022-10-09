package in.bbms;
import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static in.bbms.DonorActivity.dct;
import static in.bbms.MainActivity.rcvlist;
import static in.bbms.MainActivity.reciverinfoList;

public class InfoActivity extends AppCompatActivity {
TextView tname,tphno,temail,tbloodgroup,tcity,tdistrict,tstate,tpin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
tname=(TextView)findViewById(R.id.et_name);
        tphno=(TextView)findViewById(R.id.et_phone);
        temail=(TextView)findViewById(R.id.et_email);
        tbloodgroup=(TextView)findViewById(R.id.et_bloodGroup);
        tcity=(TextView)findViewById(R.id.et_city);
        tdistrict=(TextView)findViewById(R.id.et_district);
        tstate=(TextView)findViewById(R.id.et_state);
        tpin=(TextView)findViewById(R.id.et_pincode);

        tname.setText(reciverinfoList.get(0));
        tphno.setText(reciverinfoList.get(1));
        temail.setText(reciverinfoList.get(2));
        tbloodgroup.setText(reciverinfoList.get(3));
        tcity.setText(reciverinfoList.get(4));
        tdistrict.setText(reciverinfoList.get(5));
        tstate.setText(reciverinfoList.get(6));
        tpin.setText(reciverinfoList.get(7));
        if(ContextCompat.checkSelfPermission(InfoActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(InfoActivity.this,
                    new String[]  {   Manifest.permission.WRITE_EXTERNAL_STORAGE},101 );

            createPdf();
            // Permission is not granted
        }
createPdf();



    }

    @Override
    public void onBackPressed() {
        rcvlist.clear();
        Intent i =new Intent(InfoActivity.this,MainActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }












    private void createPdf(){
        // create a new document
        PdfDocument document = new PdfDocument();
        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);

        paint.setColor(Color.BLACK);
        canvas.drawText("Donated By            :  "+sharedPreferences.getString("myname","").toString(), 50, 50, paint);
        canvas.drawText("Recived By            : "+reciverinfoList.get(0), 50, 70, paint);
        canvas.drawText("Recivers Information", 70, 100, paint);
        canvas.drawText("Phone Num.         : "+reciverinfoList.get(1), 50, 120, paint);
        canvas.drawText("Email ID              : "+reciverinfoList.get(2), 50, 140, paint);
        canvas.drawText("Blood Group        : "+reciverinfoList.get(3), 50, 160, paint);
        canvas.drawText("State                     : "+reciverinfoList.get(4), 50, 180, paint);
        canvas.drawText("District                 : "+reciverinfoList.get(5), 50, 200, paint);
        canvas.drawText("City                       : "+reciverinfoList.get(6), 50, 220, paint);
        canvas.drawText("Pin Code             : "+reciverinfoList.get(7), 50, 240, paint);

        //canvas.drawt
        // finish the page
        document.finishPage(page);
// draw text on the graphics object of the page
        // Create Page 2

        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        Calendar calendar = Calendar.getInstance();
        //Returns current time in millis
        String df=String.valueOf(calendar.getTimeInMillis());
        String targetPdf = directory_path+reciverinfoList.get(0)+ "Doneted To "+df+".pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Information File Saved To"+Environment.getExternalStorageDirectory().getPath() + "/bbms/"+df, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }





    ////////////////
}

