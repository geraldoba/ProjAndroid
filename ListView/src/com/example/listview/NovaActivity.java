package com.example.listview;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

public class NovaActivity extends Activity {

	private EditText text;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nova);
		text = (EditText) findViewById(R.id.editText1);
		SharedPreferences settings =
		PreferenceManager.getDefaultSharedPreferences(this);
		String valor = settings.getString("ultimo", "Tarefa");
		text.setText(valor);
	}
	
	public void salvar(View v){
		Intent dados = new Intent();
		dados.putExtra("nome", text.getText().toString());
		setResult(RESULT_OK, dados);
		
		finish();
	}

}
