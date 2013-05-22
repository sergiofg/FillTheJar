package com.universogtp.fillthejar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JarPersistence {
	public static final String ID_ROW = "_id";
	public static final String REASON = "name";
	public static final String VALUE = "value";

	
	private static final String N_DB = "fill_the_jar";
	private static final String N_TABLE = "jar";
	private static final int VERSION_DB =4; 
	
	private DBHelper dbHelper;
	private final Context context;
	private SQLiteDatabase db;
	
	private static class DBHelper extends SQLiteOpenHelper {
		public DBHelper(Context context) {
			super(context, N_DB, null, VERSION_DB);
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
	
	public JarPersistence (Context context) throws Exception {
		this.context=context;
		dbHelper = new DBHelper(this.context);
		openDb();
	}
	
	private void openDb() throws Exception {
		if (db == null) {
			db = dbHelper.getWritableDatabase();
		}
	}
	
	public void cleanup() {
		if (db != null) {
			db.close();
			db= null;
		}
	}
	
	public void newJar(Jar jar) {
		ContentValues values = new ContentValues();
		values.put(REASON,jar.getName());
		values.put(VALUE,jar.getValue());
		
		long id = db.insert(N_TABLE, null, values);
		jar.setID(id);
	}
	
	public void updateJar(Jar jar){
		ContentValues values = new ContentValues();
		values.put(REASON,jar.getName());
		values.put(VALUE,jar.getValue());

		db.update(N_TABLE, values, ID_ROW+"='"+jar.getID()+"'", null);
	}
	
	public void deleteJar(Jar jar){
		db.delete(N_TABLE, ID_ROW+"='"+jar.getID()+"'",null);
	}
	
	public JarList getJarList()  throws Exception {
		JarList jarList = new JarList();
		
		String[] columns = new String[]{ID_ROW, REASON, VALUE};
		Cursor cursor = null;
		cursor = db.query(N_TABLE, columns, null, null, null, null, null);
		int numRows = cursor.getCount();
		cursor.moveToFirst();
		
		for (int i = 0; i < numRows; ++i) {
			long id = cursor.getLong(0);
			String reason = cursor.getString(1);
			int value = cursor.getInt(2);
			
			Jar jar = new Jar(id, reason);
			jar.setValue(value);
			
			jarList.addJar(jar);
			
			cursor.moveToNext();
		}
		
		return jarList;
	}
}