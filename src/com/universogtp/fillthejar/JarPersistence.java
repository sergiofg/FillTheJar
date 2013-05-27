package com.universogtp.fillthejar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JarPersistence {
	public static final String ID_ROW = "_id";
	public static final String NAME = "name";
	public static final String VALUE = "value";
	public static final String CREATED = "date_of_created"; // poner fecha actual
	public static final String FRECUENCY = "frecuency";
	public static final String WEEKENDS = "weekends";
	public static final String FILLPERCYCLE = "fill_per_cycle";
	public static final String FILLTHISCYCLE = "fill_this_cycle";
	public static final String LASTFILL = "last_fill";
	public static final String STREAK = "streak";

	
	private static final String N_DB = "fill_the_jar";
	private static final String N_TABLE = "jar";
	private static final int VERSION_DB =4; 
	
	private long date = System.currentTimeMillis();
	private int today = (int)date;
	
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
					+ NAME+" TEXT NOT NULL, "
					+ VALUE+ " INTEGER NOT NULL, "
					+ CREATED+" INTEGER, "
					+ FRECUENCY+" INTEGER ,"
					+ WEEKENDS +" INTEGER, " // VALUE 0 = FALSE, 1 = TRUE
					+ FILLPERCYCLE+" INTEGER, "
					+ FILLTHISCYCLE+" INTEGER, "
					+ LASTFILL+" INTEGER, "
					+ STREAK+" INTEGER);");
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

		values.put(NAME,jar.getName());
		values.put(VALUE,jar.getValue());
		values.put(CREATED, today);
		values.put(FRECUENCY,jar.getFrecuency());
		long id = db.insert(N_TABLE, null, values);
		jar.setID(id);
	}
	
	public void updateJar(Jar jar){
		ContentValues values = new ContentValues();

		values.put(NAME,jar.getName());
		values.put(VALUE,jar.getValue());
		values.put(LASTFILL, today);

		db.update(N_TABLE, values, ID_ROW+"='"+jar.getID()+"'", null);
	}
	
	public void deleteJar(Jar jar){
		db.delete(N_TABLE, ID_ROW+"='"+jar.getID()+"'",null);
	}
	
	public JarList getJarList()  throws Exception {
		JarList jarList = new JarList();
		
		String[] columns = new String[]{ID_ROW, NAME, VALUE,FRECUENCY};
		Cursor cursor = null;
		cursor = db.query(N_TABLE, columns, null, null, null, null, null);
		int numRows = cursor.getCount();
		cursor.moveToFirst();
		
		for (int i = 0; i < numRows; ++i) {
			long id = cursor.getLong(0);
			String NAME = cursor.getString(1);
			int value = cursor.getInt(2);
			int frecuency = cursor.getInt(3);
			
			Jar jar = new Jar(id, NAME,frecuency);
			jar.setValue(value);
			
			jarList.addJar(jar);
			
			cursor.moveToNext();
		}
		
		return jarList;
	}
}