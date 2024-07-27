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

public class CategoryListviewAdapter extends BaseAdapter implements View.OnClickListener  {

    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    ImageLoader nImageLoader;


    /*valore temporaneo della classe listeitem ogni volta che si aggiunge, crea il valore temporaneo per quel determinato oggetto*/
     ClassCategory item=null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public CategoryListviewAdapter(Activity a, ArrayList d, Resources resLocal) {

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
        // impostiamo il relative layout per cambiare sfondo
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
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.listview_category_layout, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.titolo = (TextView) vi.findViewById(R.id.listTitoloCategory);
            holder.immagine = (NetworkImageView) vi.findViewById(R.id.listImageCategory);
            //colleghiamo lo sfondo al Relative Layout al' Id
            holder.contenitore = (RelativeLayout) vi.findViewById(R.id.contenitore_sfondo);



            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            vi.setVisibility(View.GONE);
        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            item=null;
            item = ( ClassCategory ) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.titolo.setText(item.getTitolo());

          /*  Context context = holder.immagine.getContext();
            int id = context.getResources().getIdentifier(item.getImmagine(), "drawable", context.getPackageName());
            holder.immagine.setImageResource(id);*/

            /******** Set Item Click Listner for LayoutInflater for each row *******/
            int risultato  = item.getPosizione()%2;


            if(risultato == 1 ){
                int id = activity.getResources().getIdentifier("menu_bg_grey","drawable",activity.getPackageName());
                holder.contenitore.setBackgroundResource(id);
            }else{
                holder.contenitore.setBackgroundResource(0);
            }

            nImageLoader = VolleySingleton.getInstance().getImageLoader();

            holder.immagine.setImageUrl(item.getImmagine(),nImageLoader);
            holder.immagine.setDefaultImageResId(R.drawable.ico_profilo);
            holder.immagine.setErrorImageResId(R.drawable.ico_profilo);


            // andiamo ad intercettare il clic tramite id
            vi.setOnClickListener(new OnItemClickListener( item.getId()));
            // Prendiamo l'id della risorsa e lo passiamo alla funzione setBackgorund
            // Per alternare gli sfondi


        }
        return vi;

    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private String id;

        OnItemClickListener(String b_id){
            id = b_id;
        }

        @Override

        public void onClick(View arg0) {

            MenuActivity sct = (MenuActivity) activity;

            sct.onItemClick(id);

        }
    }

}
