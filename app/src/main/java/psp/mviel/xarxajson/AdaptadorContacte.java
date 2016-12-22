package psp.mviel.xarxajson;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mviel on 27/09/16.
 */
public class AdaptadorContacte extends RecyclerView.Adapter<AdaptadorContacte.ContacteViewHolder> {

    private List<Contacte> llistaContacte; // Llista amb els contactes que volem mostrar


    //Classe interna que defineix el viewHolder.
    // El ViewHolder será un objecte que ens permetrà accedir a tots els
    // camps de l'XML que dissenya el contingut d'un item de la llista
    public static class ContacteViewHolder extends RecyclerView.ViewHolder {
        public View v;
        //elements d'un ítem a mostrar
        public TextView id_contacte;
        public TextView nom_contacte;
        public TextView email_contacte;
        public TextView frase_contacte;

        //Constructor de ClientViewHolder
        public ContacteViewHolder(View v) {
            super(v);
            this.v=v;
            id_contacte = (TextView) v.findViewById(R.id.id_contacte);
            nom_contacte = (TextView) v.findViewById(R.id.nom_contacte);
            email_contacte = (TextView) v.findViewById(R.id.email_contacte);
            frase_contacte = (TextView) v.findViewById(R.id.frase_contacte);
        }

    }

    // El constructor de l'adaptador, rebrà la llista de clients a mostrar
    // i el context on s'executa l'activity que conté la llista
    public AdaptadorContacte(List<Contacte> llista){
        this.llistaContacte=llista;
    }

    @Override
    public int getItemCount(){
        return this.llistaContacte.size();
    }

    @Override
    public ContacteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.un_contacte_del_rv, parent, false);
        return new ContacteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContacteViewHolder holder, int position) {
        final int pos=position;

        holder.id_contacte.setText(""+this.llistaContacte.get(position).getId());
        holder.nom_contacte.setText(this.llistaContacte.get(position).getName());
        holder.email_contacte.setText(this.llistaContacte.get(position).getEmail());
        holder.frase_contacte.setText(this.llistaContacte.get(position).getCompanyCatchPhrase());
    }


}
