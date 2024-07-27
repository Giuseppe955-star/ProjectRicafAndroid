package com.domicilio.ricaf;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProfiloActivity extends AppCompatActivity {

    // Inizializziamo le varibili con il tipo di dato  //

    TextView nome_profilo;
    TextView email_profilo;
    TextView luogo_profilo;





    private void configurazione() {

        // Colleghiamo le varibile al seguente Id del Layout Xml
        nome_profilo = (TextView) findViewById(R.id.text_nome_profilo);
        email_profilo = (TextView) findViewById(R.id.text_email_profilo);
        luogo_profilo = (TextView) findViewById(R.id.text_luogo_profilo);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);


        configurazione();

        getDatiUtente();



    }

    public void getDatiUtente() {

        String webService = "https://www.ricaf.it/webservices/GetProfilo.php?id_utente=2";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, webService,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            // risposta Json
                            // {"status":"ok","user":{"id":"1","id_admintype":"1","nome":"Istituto","cognome":"Infobasic","username":"admin","password":
                            // "21232f297a57a5a743894a0e4a801fc3","email":"info@infobasic.it","u_accesso":"2018-02-22 15:52:20"}}
                            JSONObject json = new JSONObject(response);



                                JSONObject user = new JSONObject(json.getString("user"));
                                // Andiamo a prelevare i seguenti campi dal file json
                                // Andiamo a prelevare i dati in json
                                String nomeString = user.getString("nome");
                                String cognomeString = user.getString("cognome");
                                String emailString = user.getString("email");
                                String luogo_string = user.getString("luogo");

                                String nome_cognome = nomeString + " " + cognomeString;

                                nome_profilo.setText(nome_cognome);
                                email_profilo.setText(emailString);
                                luogo_profilo.setText(luogo_string);






                        } catch (Throwable t) {
                            Log.d("test", t.toString());

                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("Error NW MyRel", error.toString());

            }
        })
                //PER LE PARENTESI ANDARLE A SELEZIONARLE PER NON PERDERE IL LORO ORDINE

                /*CODICE: Serve per creare la lista dei parametri passati in Post al WebService (APPROFONDIRE) */ {

            @Override
            //1/ mappiamo il json  con i valori passati  uername e password con il metodo HashMap() con la seguente chiave valore
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap();
                params.put("email", (Utente.getInstance().getEmail()));
                params.put("id", (Utente.getInstance().getId()));
                return params;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    public void SaveAction(View view){
        // modifica profilo
        // interrogare il webservices ModificaProfilo.php passando i paramentri in POST
        final String webService = getString(R.string.url_base) + getString(R.string.url_modifica_profilo);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, webService,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            // risposta Json
                            // {"status":"ok","user":{"id":"1","id_admintype":"1","nome":"Istituto","cognome":"Infobasic","username":"admin","password":
                            // "21232f297a57a5a743894a0e4a801fc3","email":"info@infobasic.it","u_accesso":"2018-02-22 15:52:20"}}
                            JSONObject json = new JSONObject(response);

                            if (json.getString("status").equals("error")) {
                                String error = json.getString("message");
                                Toast.makeText(ProfiloActivity.this, error, Toast.LENGTH_SHORT).show();

                            } else if (json.getString("status").equals("ok")) {

                                Log.d("test", String.valueOf(json.get("status")));

                                String messageString  = json.getString("message");

                                //JSONObject user = new JSONObject(json.getString("user"));
                                // Andiamo a prelevare i seguenti campi dal file json
                                // Andiamo a prelevare i dati in json




                                Preferenze mPref = new Preferenze(ProfiloActivity.this);

                                mPref.putStringByLabel("nome", nome_profilo.getText().toString());




                                // Andiamo ad inserire le Preferenze nella classe Utente
                                Utente.getInstance().SetUtenteFromPref(ProfiloActivity.this);


                                Toast.makeText(ProfiloActivity.this,messageString, Toast.LENGTH_SHORT).show();





                            } else {
                                Toast.makeText(ProfiloActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

                            }



                        } catch (Throwable t) {
                            Log.d("test", t.toString());

                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("Error NW MyRel", error.toString());

            }
        })
                //PER LE PARENTESI ANDARLE A SELEZIONARLE PER NON PERDERE IL LORO ORDINE

                /*CODICE: Serve per creare la lista dei parametri passati in Post al WebService (APPROFONDIRE) */ {

            @Override
            //1/ mappiamo il json  con i valori passati  uername e password con il metodo HashMap() con la seguente chiave valore
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap();
                params.put("email", (Utente.getInstance().getEmail()));
                params.put("id", (Utente.getInstance().getId()));
                params.put("nome", nome_profilo.getText().toString());
                params.put("luogo", luogo_profilo.getText().toString());

                return params;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


}
