package com.domicilio.ricaf;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class ProductListViewAdapter extends BaseAdapter implements View.OnClickListener {
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    ImageLoader nImageLoader;




    /*valore temporaneo della classe listeitem ogni volta che si aggiunge, crea il valore temporaneo per quel determinato oggetto*/
    ClassProduct item=null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public ProductListViewAdapter(Activity a, ArrayList d, Resources resLocal) {

        /********** Take passed values **********/
        activity = a;
        data=d;
        //res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getCount() {

        if(data.size()<=0)
            return 1;

        return data.size();
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView titolo;
        public NetworkImageView immagine;
        public TextView prezzo;
        public RelativeLayout contenitore;

    }
    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ProductListViewAdapter.ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.listview_product_layout, null);


            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ProductListViewAdapter.ViewHolder();
            holder.titolo = (TextView) vi.findViewById(R.id.listTitoloCategory);
            holder.immagine = (NetworkImageView) vi.findViewById(R.id.listImageCategory);
            holder.prezzo = (TextView)vi.findViewById(R.id.listPrezzoProduct);
            holder.contenitore = (RelativeLayout) vi.findViewById(R.id.contenitore_sfondo_1);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ProductListViewAdapter.ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            vi.setVisibility(View.GONE);
        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            item=null;
            item = ( ClassProduct ) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.titolo.setText(item.getTitolo());
            String prezzo_eur =  "€" + " " + item.getPrezzo();

            holder.prezzo.setText(prezzo_eur);

          /*  Context context = holder.immagine.getContext();
            int id = context.getResources().getIdentifier(item.getImmagine(), "drawable", context.getPackageName());
            holder.immagine.setImageResource(id);*/

            /******** Set Item Click Listner for LayoutInflater for each row *******/


            int risultato  = item.getPosizione()%2;


            if(risultato == 1 ){
                int id = activity.getResources().getIdentifier("menu_bg_grey","drawable",activity.getPackageName());
                holder.contenitore.setBackgroundResource(id);
            }else {
                int id = activity.getResources().getIdentifier("menu_bg_grey", "drawable", activity.getPackageName());
                holder.contenitore.setBackgroundResource(id);
            }

            nImageLoader = VolleySingleton.getInstance().getImageLoader();

            holder.immagine.setImageUrl(item.getImmagine(),nImageLoader);
            holder.immagine.setDefaultImageResId(R.drawable.pizza_img);
            holder.immagine.setErrorImageResId(R.drawable.pizza_img);


            vi.setOnClickListener(new ProductListViewAdapter.OnItemClickListener( item.getPosizione() ));

        }
        return vi;

    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private int id;

        OnItemClickListener(int b_id){
            id = b_id;
        }

        @Override

        public void onClick(View arg0) {

            MenuActivity sct = (MenuActivity) activity;

            //sct.onItemClick(id);

        }
    }

}
