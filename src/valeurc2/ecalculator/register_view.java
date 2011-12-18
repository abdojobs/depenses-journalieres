package valeurc2.ecalculator;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class register_view extends ListActivity {
	
	private final String SAMPLE_DB_NAME = "EXPENSES";
	private final String TABLE_NAME = "EXPENSES";
	
	
	final Calendar c = Calendar.getInstance();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        ArrayList<String> results = new ArrayList<String>();
        SQLiteDatabase sampleDB = null;
        
        try {
        	this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
        	sampleDB =  this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
        	// get the current date
            
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH); 
            
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
        			TABLE_NAME +
        			" (Food INT(8), Oil INT(8)," +
        			" Clothes INT(8), Others INT(8), Day INT(8), Month INT(8), Year INT(8));");
        	
        	Cursor cu=null;
        	if(sampleDB!= null){
				cu = sampleDB.rawQuery("SELECT * FROM " +
	        			TABLE_NAME +
	        			" WHERE Day= "+ Integer.toString(mDay) + " AND Month= "+ Integer.toString(mMonth) + " AND  Year= "+ Integer.toString(mYear), null);
				}
        	
        	if (!(cu.moveToFirst())){
		        	sampleDB.execSQL("INSERT INTO " +
		             TABLE_NAME +
		        	 " Values (0,0,0,0," + Integer.toString(mDay)+","+ Integer.toString(mMonth)+ ","+ Integer.toString(mYear)+");");}
        	
        	cu = sampleDB.rawQuery("SELECT * FROM " +
        			TABLE_NAME +
        			" WHERE Day= "+ Integer.toString(mDay) + " AND Month= "+ Integer.toString(mMonth) + " AND  Year= "+ Integer.toString(mYear), null);
        	if (cu != null ) {
        		if  (cu.moveToFirst()) {

        			do {
        				
        				int food = cu.getInt(cu.getColumnIndex("Food"));
        				int oil = cu.getInt(cu.getColumnIndex("Oil"));
        				int clothes = cu.getInt(cu.getColumnIndex("Clothes"));
        				int others = cu.getInt(cu.getColumnIndex("Others"));
        				results.add("Food: " + food + "\nOil: " + oil+ "\nClothes: " + clothes + "\nOthers: " + others);
        			}while (cu.moveToNext());
        		} 
        	}
        	else
        		results.add("Food: 0"  + "\nOil: 0"+ "\nClothes: 0" + "\nOthers: 0");
        		
        	
        	this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,results));
        	
        } catch (SQLiteException se ) {
        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } 
        
        		sampleDB.close();

    }
}