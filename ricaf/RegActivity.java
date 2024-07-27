package com.domicilio.ricaf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class RegActivity extends AppCompatActivity implements View.OnClickListener {


    EditText nome;
    EditText cognome;
    EditText email;
    EditText password;
    TextView luogo_qrcode;

    private Button buttonScan;

    //qr code scanner object
    private IntentIntegrator qrScan;

    // Creiamo id_luogo per la stringa del webservices
    String luogo_id;
    String string_noncereg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);


        nome = findViewById(R.id.edit_nome);
        cognome = findViewById(R.id.edit_cognome);
        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_password);
        luogo_qrcode = findViewById(R.id.text_luogo);

        buttonScan = findViewById(R.id.buttonScan);

        qrScan = new IntentIntegrator(this);


        buttonScan.setOnClickListener(this);


        LoadNonce();


    }

    // NOnce serve per permettere la registrazione
    private void LoadNonce() {

        //String webService = "http://www.ricaf.it/?json=get_nonce&controller=user&method=register" ;
        String webService = getString(R.string.url_nonce);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, webService,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject json = new JSONObject(response);
                            if (json.getString("nonce").equals("")) {

                                Toast.makeText(RegActivity.this, "mancanza rete ", Toast.LENGTH_LONG).show();
                            } else {
                                //andiamo a prelevare dall'oggetto result o lo andiamo a collgare alla stringa luogo_id
                                string_noncereg = json.getString("nonce");
                                Log.d("test", "il nonce è :" + string_noncereg);


                            }

                        } catch (Throwable t) {
                            Log.d("test", t.toString());
                            Toast.makeText(RegActivity.this, "Errore Generico", Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("Error NW MyRel", error.toString());
                Toast.makeText(RegActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                //loading.setVisibility(ProgressBar.GONE);
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }


    public void GiaSeiRegAction(View view) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);


    }


    public void RegistratiAction(View view) {

        String result = CheckFields();

        if (result.equals("ok")){

            // NOTARE: Richiesta http fatta in Get!
            // ? = parte tutto ; & aggiunge i parametri
            final String webservice = "http://www.ricaf.it/?json=user/register";

            String query="";

            // Codice che corregge automaticamente Caratteri o Simboli speciali che possono dare errori negli URL

            try {

                String Email = email.getText().toString();
                String Password = URLEncoder.encode(password.getText().toString(), "utf-8");
                String Nome = URLEncoder.encode(nome.getText().toString(), "utf-8");
                String Cognome = URLEncoder.encode(cognome.getText().toString(), "utf-8");
                String Luogo = URLEncoder.encode(luogo_qrcode.getText().toString(), "utf-8");


                query = "&username=" + Email +
                        "&email=" + Email + "&nonce=" + string_noncereg + "&display_name=" + Email +
                        "&notify=both&user_pass="+ Password + "&first_name=" + Nome +"&last_name=" + Cognome +
                        "&description=" + Luogo + "&insecure=cool";

            }catch(Throwable t ) {
                Log.d("test", t.toString());
                Toast.makeText(RegActivity.this, "Errore Generico UTF8", Toast.LENGTH_LONG).show();
            }

            // Andiamo a collegare le due stringhe per il funzionamento della Registrazione
            final String concatena = webservice + query;


            // Aggiungiamo concatena, per farlo funzionare
            StringRequest stringRequest = new StringRequest(Request.Method.GET, concatena,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try{

                                JSONObject json = new JSONObject(response);
                                if (json.getString("status").equals("")){
                                    Toast.makeText(RegActivity.this, R.string.error_reg_fallita, Toast.LENGTH_LONG).show();
                                }else if (json.getString("status").equals("ok")){

                                    Log.d("test", String.valueOf(json.get("status")));
                                    Toast.makeText(RegActivity.this, R.string.reg_riuscita , Toast.LENGTH_LONG).show();
                                }


                            }catch(Throwable t ){
                                Log.d("test",t.toString());
                                Toast.makeText(RegActivity.this, "Errore Generico", Toast.LENGTH_LONG).show();

                            }

                            //loading.setVisibility(ProgressBar.GONE);
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    Log.d("Error NW MyRel", error.toString());
                    Toast.makeText(RegActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    //loading.setVisibility(ProgressBar.GONE);
                }
            });

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        }else{
            Toast.makeText(RegActivity.this, getString(R.string.error_registrati), Toast.LENGTH_SHORT).show();
        }

    }




    private String CheckFields() {

        String result = "ok";

        //Condizione se non immettiamo i dati, il testo lo peschiamo dalla "res" --> strings
        if (nome.getText().toString().equals("") ||
                (cognome.getText().toString().equals("") ||
                        email.getText().toString().equals("") ||
                        password.getText().toString().equals("") ||
                        luogo_qrcode.getText().toString().equals(""))) {
            result = getString(R.string.error_reg);
        }


        return result;


    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {

                String webService = getString(R.string.url_qrcode) + result.getContents();


                StringRequest stringRequest = new StringRequest(Request.Method.GET, webService,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONObject json = new JSONObject(response);
                                    if (json.getString("status").equals("error")) {

                                        Toast.makeText(RegActivity.this, json.getString("message"), Toast.LENGTH_LONG).show();
                                    } else {
                                        //andiamo a prelevare dall'oggetto result o lo andiamo a collgare alla stringa luogo_id
                                        luogo_id = json.getString("id");
                                        //Andiamo a riempire la text view
                                        luogo_qrcode.setText(json.getString("nome"));

                                    }

                                } catch (Throwable t) {
                                    Log.d("test", t.toString());
                                    Toast.makeText(RegActivity.this, "Errore Generico", Toast.LENGTH_LONG).show();
                                }


                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Error NW MyRel", error.toString());
                        Toast.makeText(RegActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                        //loading.setVisibility(ProgressBar.GONE);
                    }
                });

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        3000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onClick(View view) {

        qrScan.initiateScan();

    }
}






