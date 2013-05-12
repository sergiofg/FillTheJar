package com.universogtp.fillthejar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JarPersistence 
{
	public static final String ID_ROW = "_id";
	public static final String VALUE = "valor";
	
	private static final String N_BD = "motivador";
	private static final String N_TABLE = "Table_motivador";
	private static final int VERSION_BD =1; 
	
	private BDHelper nHelper;
	private final Context nContext;
	private SQLiteDatabase nBD;
	
	private static class BDHelper extends SQLiteOpenHelper
	{
		public BDHelper(Context context) 
		{
			super(context, N_BD, null, VERSION_BD);
		}

		public void onCreate(SQLiteDatabase db) 
		{
			db.execSQL("CREATE TABLE "+N_TABLE+ "("
					+ ID_ROW+" INTEGER PRIMARY KEY AUTOINCREMENT, "
					+VALUE+" INTEGER NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			db.execSQL("DROP TABLE IF EXISTS " +N_TABLE+";" );
			onCreate(db);
		}

	}
	
	public JarPersistence (Context c)
	{
		nContext=c;
	}
	
	private JarPersistence opendb() throws Exception
	{
		nHelper = new BDHelper(nContext);
		nBD = nHelper.getWritableDatabase();
		return this;
	}
	
	private void closedb() 
	{
		nHelper.close();
	}
	
	private long dataInput( int val) 
	{
		ContentValues cv = new ContentValues();
		cv.put(VALUE, val);
		
		return nBD.insert(N_TABLE, null, cv);
	}
	
	private int getDb() 
	{
		String[]columns = new String[]{ID_ROW,VALUE};
		Cursor c = nBD.query(N_TABLE, columns, null,null, null, null, null);
		int result =0;
		
		int iValue = c.getColumnIndex(VALUE);
		
		if (c.moveToFirst()) {
			c.moveToLast();
			result =c.getInt(iValue);
		}
		
		return result;
	}
	
	public void setValue(int val)
	{
		JarPersistence db = new JarPersistence (nContext);
		try {
			db.opendb();
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.dataInput(val);
		db.closedb();
	}
	
	public int getValue()
	{
		JarPersistence db = new JarPersistence(nContext);
		try {
			db.opendb();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int data = db.getDb();
		db.closedb();
		
		return  data;
	}
}
