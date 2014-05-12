package com.example.listview;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


import android.R.array;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView listView;
	public static ArrayList<String> lista = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private SharedPreferences settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);

		Consulta ucsal = new Consulta(this);
		ucsal.execute("http://www.casipe.com.br/teste_ucsal_json.php");

		/*
		 * for(int i=0;i<4;i++) { lista.add("teste"); //Log.d("WS",
		 * "Resultado da Conn: "+result.get(i)); }
		 */

		Log.d("WS", "Retorno -> : "+ ucsal.getResultado() );

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, lista);
		listView.setAdapter(adapter);

		settings = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = settings.edit();
		editor.putString("ultimo", "");
		editor.commit();

		registerForContextMenu(listView);

		/*ArrayList<String> res_ucsal = ucsal.getResultado();
		Log.d("WS", "Resultado: "+res_ucsal+ ".");
		for (int i = 0; i < res_ucsal.size(); i++) {
			Log.d("WS", res_ucsal + "\n\n");
		}*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_nova:
			Intent intent = new Intent(this, NovaActivity.class);
			startActivityForResult(intent, 9999);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 9999 && resultCode == RESULT_OK) {
			String tarefa = data.getStringExtra("nome");
			lista.add(tarefa);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}

	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		String nomeItem = (String) listView.getItemAtPosition(info.position);
		switch (item.getItemId()) {
		case R.id.action_exibir:
			Toast.makeText(this, nomeItem, Toast.LENGTH_LONG).show();
			return true;
		case R.id.action_excluir:
			// listView.getItemAtPosition(info.position);
			lista.remove(info.position);
			Toast.makeText(this,
					"O ítem \"" + nomeItem + "\" foi excluído com sucesso",
					Toast.LENGTH_LONG).show();
			onResume();
		case R.id.action_site:
			Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://www.casipe.com.br"));
			startActivity(intent);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	public void teste(int n)
	{
		
		Log.d("WS", "TESTE CONTEXTO: "+n+ ".");
	}

}
