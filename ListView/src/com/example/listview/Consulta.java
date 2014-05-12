package com.example.listview;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
 
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener; 

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

 
public class Consulta extends AsyncTask<String, Void, Boolean> {
 
    public ArrayList<String> resultado, lista_json;

    
    
	private  Context c;

    
    
	
	public Consulta(Context c) {
		super();
		this.c = c;
	}
	
	
	
	
   
   public ArrayList<String> getResultado() {
		return lista_json;
	}

	public void setResultado(ArrayList<String> list) {
		this.resultado = list;
	}

	@Override
    protected Boolean doInBackground(String... params) {
       	  	
    	
    	
    	String URL = params[0];
        String linha = ""; 
           
        if (params.length > 0)
        {
        
            try {
 /*
                HttpClient client = new DefaultHttpClient();
                HttpGet requisicao = new HttpGet();
                requisicao.setHeader("Content-Type",
                        "text/plain; charset=utf-8");
                requisicao.setURI(new URI(URL));
                HttpResponse resposta = client.execute(requisicao);

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        resposta.getEntity().getContent()));
                StringBuffer stringBuffer  = new StringBuffer("");
 
                
                String NL = System.getProperty("line.separator");
                
                while ((linha = bufferedReader.readLine()) != null) {
                	stringBuffer.append(linha + NL);
                } 
                bufferedReader.close(); 
                linha = stringBuffer.toString();              
                
                //Log.d("RESULTADO", linha);
              */
                linha = "{'retorno':[{'titulo':'Quero Vender Mais','url':'www.querovendermais.com.br'},{'titulo':'CASIPE','url':'www.casipe.com.br'}]}";
                //nativo do SDK  da o parse da string json
                JSONObject object = (JSONObject) new JSONTokener(linha).nextValue();
               
                // monta o array do retorno do json, lembrando que retorno é o PAI do json impresso em page.
                //{"retorno":[ {"campo1":"valor1"},{"campo2":"valor2"},{"campo3":"valor3"} ]}
                JSONArray message = object.getJSONArray("retorno");
         
                
                //Percorre o array retornado
                //campo1, 2 e 3 são os nós da string json {"campo1":"valor1"},{"campo2":"valor2"},{"campo3":"valor3"}
               
                String result = null;
                ArrayList<String> list = new ArrayList<String>(); 
               
               for(int i=0;i<message.length();i++)
               {                 
                	JSONObject lines = (JSONObject) new JSONTokener(message.getString(i)).nextValue();
                    result = lines.getString("titulo")+" -> "+lines.getString("url");   	
                	list.add(result);
                }               	
            
                this.lista_json = list;              
                
 
            } catch (Exception e) {

                Log.e("ERRO", e.getMessage());
               return false;
            }
        }
        return true;
    }
	
	@Override
	public void onPostExecute(Boolean result) {
		super.onPostExecute(result);		
		this.setResultado(this.lista_json);
		testeResultado();
	}
	
	public void testeResultado(){
		
		Log.d("WS", "Resultado: ");
        for(int i=0;i<getResultado().size();i++)
        {                 
        	//c.lista.add(getResultado());
        	Log.d("WS", getResultado()+"\n\n");
        }
		
	}
	

}

