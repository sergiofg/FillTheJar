package com.universogtp.fillthejar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JarPersistence {
	public static final String ID_ROW = "_id";
	public static final String REASON = "razon";
	public static final String VALUE = "valor";

	
	private static final String N_BD = "motivador";
	private static final String N_TABLE = "Table_jar";
	private static final int VERSION_BD =2; 
	
	private BDHelper nHelper;
	private final Context nContext;
	private SQLiteDatabase nBD;
	
	private static class BDHelper extends SQLiteOpenHelper {
		public BDHelper(Context context) {
			super(context, N_BD, null, VERSION_BD);
		}

		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE "+N_TABLE+ "("
					+ ID_ROW+" INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ REASON+" TEXT NOT NULL, "
					+VALUE+" INTEGER NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " +N_TABLE+";" );
			onCreate(db);
		}

	}
	
	public JarPersistence (Context c) {
		nContext=c;
	}
	
	private JarPersistence opendb() throws Exception {
		nHelper = new BDHelper(nContext);
		nBD = nHelper.getWritableDatabase();
		return this;
	}
	
	private void closedb() {
		nHelper.close();
	}
	
	public long newJar(int val,String j) {
		ContentValues cv = new ContentValues();
		cv.put("REASON",j);
		cv.put("VALUE",val);
		
		return nBD.insert(N_TABLE, null, cv);
	}
	
	public long updateJar(int val,String j){
		ContentValues cv = new ContentValues();
		cv.put(VALUE, val);

		return nBD.update(N_TABLE, cv, REASON+"='"+j+"'", null);

	}
	
	public void deleteJar(String j){
		nBD.delete(N_TABLE, REASON+"='"+j+"'",null);
	}
	
	private String getDb(String j) {
		String[]columns = new String[]{ID_ROW,VALUE};

		Cursor c = nBD.query(N_TABLE, columns, REASON+" ='"+j+"'",null, null, null, null);
		
		if (c.moveToFirst()) { 
		c.moveToLast();
		 String v = c.getString(1).toString();
		return v;
		}else{
			return  "empty" ;
		}
	}
	
	public void setValue(int val,String j) {
		JarPersistence db = new JarPersistence (nContext);
		try {
			db.opendb();
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.updateJar(val,j);
		db.closedb();
	}
	
	public int getValue(String j) {
		JarPersistence db = new JarPersistence(nContext);
		try {
			db.opendb();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String data = db.getDb(j);
		db.closedb();
		
		return  Integer.parseInt(data);
	}
}