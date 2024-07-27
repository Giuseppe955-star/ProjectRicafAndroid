package com.domicilio.ricaf;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    GridView mylist;
    ProductListViewAdapter listAdapter;
    public ProductActivity ActivityView = null;
    public ArrayList<ClassProduct> CustomListViewProduct = new ArrayList<ClassProduct>();
    Resources res = null;

    String[] titoli;
    String[] immagini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        mylist = (GridView) findViewById(R.id.listView1);

        ActivityView = this;
        res =getResources();

        //titoli = getResources().getStringArray(R.array.titoli_array);
        //immagini = getResources().getStringArray(R.array.immagini_array);

        /*for (int i = 0; i < titoli.length; i++){

            final Album item = new Album(titoli[i],immagini[i],i);

            item.setTitolo(titoli[i]);
            item.setImmagine(immagini[i]);
            item.setPosizione(i);

            CustomListViewUsers.add(item);

        }

        listAdapter = new AlbumListviewAdapter(ActivityView, CustomListViewUsers, res);
        mylist.setAdapter(listAdapter);*/


        String id = getIntent().getStringExtra("id");


        String webService = "https://www.ricaf.it/webservices/GetProducts.php?id_categoria=" + id ;





        StringRequest stringRequest = new StringRequest(Request.Method.GET, webService,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try{

                            JSONObject json = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(json.getString("prodotti"));

                            for(int i= 0; i<jsonArray.length();i++) {

                                JSONObject obj = jsonArray.getJSONObject(i);

                                final ClassProduct item = new ClassProduct(obj.getString("nome"), obj.getString("immagine"), i,obj.getString("id"),obj.getString("prezzo"));

                                CustomListViewProduct.add(item);

                            }

                        } catch(Throwable t ){
                            Log.d("test",t.toString());
                        }

                        listAdapter = new ProductListViewAdapter(ProductActivity.this, CustomListViewProduct,  res);
                        mylist.setAdapter(listAdapter);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("Error NW MyRel", error.toString());

                //loading.setVisibility(ProgressBar.GONE);
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);



        /*


        link = getResources().getStringArray(R.array.titoli_array);
        // definisco un array
        final ArrayList<String> listp = new ArrayList<String>();
        for (int i = 0; i < link.length; i++) {
            listp.add(link[i]);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.simple_list_item_1, listp);
        mylist.setAdapter(adapter);


        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity.this,AlbumActivity.class);
                intent.putExtra("position",i);
                startActivity(intent);


            }
        });
*/
    }

}
