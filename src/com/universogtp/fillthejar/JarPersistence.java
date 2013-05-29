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
	public static final String FREQUENCY = "frequency";
	public static final String WEEKENDS = "weekends";
	public static final String FILLPERCYCLE = "fill_per_cycle";
	public static final String FILLTHISCYCLE = "fill_this_cycle";
	public static final String LASTFILL = "last_fill";
	public static final String CURRENTCYCLESTART = "current_cycle_start";
	public static final String STREAK = "streak";

	
	private static final String N_DB = "fill_the_jar";
	private static final String N_TABLE = "jar";
	private static final int VERSION_DB =7; 
		
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
					+ FREQUENCY+" INTEGER ,"
					+ WEEKENDS +" INTEGER, "
					+ FILLPERCYCLE+" INTEGER, "
					+ FILLTHISCYCLE+" INTEGER, "
					+ LASTFILL+" INTEGER, "
					+ CURRENTCYCLESTART+" INTEGER, "
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
	
	private ContentValues getJarValues(Jar jar) {
		ContentValues values = new ContentValues();
		
		values.put(NAME,jar.getName());
		values.put(FREQUENCY,jar.getFrequency());
		values.put(WEEKENDS,jar.getWeekends());
		values.put(FILLPERCYCLE, jar.getFillsPerCycle());
		
		values.put(VALUE,jar.getValue());
		values.put(STREAK, jar.getStreak());
		values.put(FILLTHISCYCLE, jar.getFillsThisCycle());
		values.put(LASTFILL, jar.getLastFill());
		values.put(CURRENTCYCLESTART, jar.getCurrentCycleStart());
		
		return values;
	}
	
	public void newJar(Jar jar) {
		ContentValues values = getJarValues(jar);
		
		int today =(int) (System.currentTimeMillis()/1000);		
		jar.setCurrentCycleStart(today);

		values.put(CREATED, today);
		values.put(CURRENTCYCLESTART, today);
		
		long id = db.insert(N_TABLE, null, values);
		jar.setID(id);
		jar.setCreated(today);
	}
	
	public void updateJar(Jar jar){
		ContentValues values = getJarValues(jar);
		
		db.update(N_TABLE, values, ID_ROW+"='"+jar.getID()+"'", null);
	}
	
	public void deleteJar(Jar jar){
		db.delete(N_TABLE, ID_ROW+"='"+jar.getID()+"'",null);
	}
	
	public JarList getJarList()  throws Exception {
		JarList jarList = new JarList();
		
		String[] columns = new String[]{ID_ROW, NAME, VALUE,CREATED, FREQUENCY,WEEKENDS,FILLPERCYCLE, FILLTHISCYCLE, LASTFILL, CURRENTCYCLESTART, STREAK};
		Cursor cursor = null;
		cursor = db.query(N_TABLE, columns, null, null, null, null, null);
		int numRows = cursor.getCount();
		cursor.moveToFirst();
		
		for (int i = 0; i < numRows; ++i) {
			long id = cursor.getLong(0);
			String NAME = cursor.getString(1);
			int value = cursor.getInt(2);
			int created = cursor.getInt(3);
			int frecuency = cursor.getInt(4);
			int weekends = cursor.getInt(5);
			int fillsPerCycle = cursor.getInt(6);
			int fillsThisCycle = cursor.getInt(7);
			int lastFill = cursor.getInt(8);
			int currentCycleStart = cursor.getInt(9);
			int streak = cursor.getInt(10);
			
			Jar jar = new Jar(id, NAME);			
			jar.setValue(value);
			jar.setCreated(created);
			jar.setFrequency(frecuency);
			jar.setWeekends(weekends);
			jar.setFillsPerCycle(fillsPerCycle);
			jar.setFillsThisCycle(fillsThisCycle);
			jar.setLastFill(lastFill);
			jar.setCurrentCycleStart(currentCycleStart);
			jar.setStreak(streak);
			
			jarList.addJar(jar);
			
			cursor.moveToNext();
		}
		
		return jarList;
	}
}