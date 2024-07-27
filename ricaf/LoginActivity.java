package com.domicilio.ricaf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_password);
    }


    public void AccediAction(View view) {

        String result = CheckFields();

        if (CheckFields().equals("ok")) {

            //Controllare i dati del Login
            final String webService = "http://www.ricaf.it/?json=user/generate_auth_cookie&username=" + email.getText().toString()
                    + "&password=" + password.getText().toString() + "&insecure=cool";


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
                                    String error = json.getString("error");
                                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();

                                } else if (json.getString("status").equals("ok")) {

                                    Log.d("test", String.valueOf(json.get("status")));


                                    JSONObject user = new JSONObject(json.getString("user"));
                                    // Andiamo a prelevare i seguenti campi dal file json
                                    String id = user.getString("id");
                                    String nome = user.getString("nome");
                                    String cognome = user.getString("cognome");
                                    String email = user.getString("email");
                                    String username = user.getString("username");

                                    Log.d("test", String.valueOf(user));

                                    // Andiamo a salvare nelle preferenze
                                    Preferenze mPref = new Preferenze(LoginActivity.this);
                                    mPref.putStringByLabel("id",id);
                                    mPref.putStringByLabel("nome",nome);
                                    mPref.putStringByLabel("cognome",cognome);
                                    mPref.putStringByLabel("email",email);
                                    mPref.putStringByLabel("username",username);

                                    // Andiamo ad inserire le Preferenze nella classe Utente
                                    Utente.getInstance().SetUtenteFromPref(LoginActivity.this);


                                    // Un volta inserite nella classe passiamo all'altra Activity
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(LoginActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

                                }

                                /*
                                JSONArray jsonArray = new JSONArray(json.getString("albums"));

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject obj = jsonArray.getJSONObject(i);

                                }*/

                            } catch (Throwable t) {
                                Log.d("test", t.toString());
                            }
                        }

                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    Log.d("Error NW MyRel", error.toString());

                    //loading.setVisibility(ProgressBar.GONE);
                }
            })
                    //PER LE PARENTESI ANDARLE A SELEZIONARLE PER NON PERDERE IL LORO ORDINE

                    /*CODICE: Serve per creare la lista dei parametri passati in Post al WebService (APPROFONDIRE) */ {

                @Override
                //1/ mappiamo il json  con i valori passati  uername e password con il metodo HashMap() con la seguente chiave valore
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap();
                    params.put("email", email.getText().toString());
                    params.put("password", password.getText().toString());
                    return params;
                }
            };


            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

            // info ctrl
            // funzione se i campi dei form sono stati compilati


        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.error_login), Toast.LENGTH_SHORT).show();

        }


    }

    private String CheckFields() {

        String result = "ok";

        //Condizione se non immettiamo i dati, il testo lo peschiamo dalla "res" --> strings
        if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
            result = getString(R.string.error_login);
        }


        return result;


    }



    public void NonSeiRegAction(View view) {

        Intent intent = new Intent(this, RegActivity.class);
        startActivity(intent);


    }


    public void ProvaAction(View view) {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);


    }


}
