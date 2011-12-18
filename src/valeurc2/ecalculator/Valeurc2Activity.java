package valeurc2.ecalculator;

//import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class Valeurc2Activity extends Activity {
	private final String DB = "EXPENSES";
	private final String SAMPLE_TABLE = "EXPENSES";
	
	public SQLiteDatabase sampleDB = null;    

	final Calendar c = Calendar.getInstance();
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set ContentView
        setContentView(R.layout.login_view);
       
            sampleDB =  this.openOrCreateDatabase(DB, MODE_PRIVATE, null);
            final Spinner spinner = (Spinner) findViewById(R.id.acceditor_type);
            spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this, R.array.type, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            
        
        // Event handler
            final EditText sum =(EditText)findViewById(R.id.acceditor_initval);
            //final Spinner spinner = (Spinner)findViewById(R.id.acceditor_type);
        findViewById(R.id.acceditor_ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
		        
		        try {
		        	
					
		        	int mYear = c.get(Calendar.YEAR);
		            int mMonth = c.get(Calendar.MONTH);
		            int mDay = c.get(Calendar.DAY_OF_MONTH); 
		            
		        	sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
		        			SAMPLE_TABLE +
		        			" (Food INT(8), Oil INT(8)," +
		        			" Clothes INT(8), Others INT(8), Day INT(8), Month INT(8), Year INT(8));");
		    
					
					Cursor c=null;
					if(sampleDB!= null){
					c = sampleDB.rawQuery("SELECT * FROM " +
		        			SAMPLE_TABLE +
		        			" WHERE Day= "+ Integer.toString(mDay) + " AND Month= "+ Integer.toString(mMonth) + " AND  Year= "+ Integer.toString(mYear), null);
					}
		        	
					if (!(c.moveToFirst())){
			        	sampleDB.execSQL("INSERT INTO " +
			             SAMPLE_TABLE +
			        	 " Values (0,0,0,0," + Integer.toString(mDay)+","+ Integer.toString(mMonth)+ ","+ Integer.toString(mYear)+");");}
		        	
		        	//Toast.makeText(getApplicationContext(), sum.getText().toString(), Toast.LENGTH_LONG).show();
		        	sampleDB.execSQL("UPDATE " +
		        			SAMPLE_TABLE +
		        			" SET " + spinner.getSelectedItem().toString()+ " = "+ sum.getText().toString()+ " + " +spinner.getSelectedItem().toString()+ 
		        			" WHERE Day= "+ Integer.toString(mDay) + " AND Month= "+ Integer.toString(mMonth) + " AND  Year= "+ Integer.toString(mYear)+" ;");
		        	
		        	Toast.makeText(getApplicationContext(), "Operation successfully done", Toast.LENGTH_LONG).show();
		        	}
		        	
		        	
		        	
		         catch (SQLiteException se ) {
		        	 Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
		        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
		        } 
		         
		         sampleDB.close();
		    }});
        
        
        findViewById(R.id.acceditor_view).setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
				Intent inii = new Intent(Valeurc2Activity.this, register_view.class);
				Valeurc2Activity.this.startActivity(inii);
			}
		});
    }
}