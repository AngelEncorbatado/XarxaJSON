package psp.mviel.xarxajson;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private boolean hiHaXarxa;
    protected RecyclerView rvTradicional, rvGson;
    protected ArrayList <Contacte> contactesParsejats;
    protected LinearLayoutManager rvLM;
    final String TAG = "XarxaJSON";
    String documentJSON;
    protected TextView tv_etiqueta1;
    protected AdaptadorContacte ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactesParsejats = new ArrayList<Contacte>();
        tv_etiqueta1 = (TextView) findViewById(R.id.etiqueta1);

        //------------------------------------------------------------------------------------------
        //*************************     IMPORTANT!!!!!
        //Aquestes instruccions han d'anar juntes, sinó no ens funcionarà el RecyclerView
        rvTradicional = (RecyclerView) findViewById(R.id.rvTradicional);
        rvLM = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);//Triem el LayoutManager que volem utilitzar i l'assignem a l'objecte recyclerView
        rvTradicional.setLayoutManager(rvLM);
        rvTradicional.setHasFixedSize(true);  // Si tots els component del RecyclerViw ténen la mateixa amplària
        //------------------------------------------------------------------------------------------

        //rvGson = (RecyclerView) findViewById(R.id.rvGSON);

        hiHaXarxa = comprovaConnexio();
        if(hiHaXarxa){
            Log.d(TAG, "Vaig a connectar...");
            new ConnectaURL().execute("https://jsonplaceholder.typicode.com/users");

        }

    }

    protected boolean comprovaConnexio(){
        ConnectivityManager connMgr = (ConnectivityManager)  getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null  &&  networkInfo.isConnected()) {
            // podem fer la connexió a alguna URL
            hiHaXarxa=true;
            Log.d(TAG, "Vaig a connectar...");
            new ConnectaURL().execute("https://jsonplaceholder.typicode.com/users");

            return true;
        } else {
            // mostrem un error indicant que no dispossem de connexió a la Xarxa
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.relativeLayout),
                    "No hi ha connexió a la Xarxa", Snackbar.LENGTH_INDEFINITE);
            mySnackbar.setAction("Tornar a provar", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hiHaXarxa = comprovaConnexio();

                }
            });
            mySnackbar.show();
            return false;
        }
    }

    private String connectaURL(String llocAConnectar){
        URL url;
        String resposta=null;
        try {
            Log.d(TAG,"Iniciant la connexió: (");

            url = new URL(llocAConnectar);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET"); // Podrà ser PUT, POTS,GET,DELETE,HEAD,OPTIONS,TRACE, vegeu api
            conn.setDoInput(true); /* anem a rebre dades */
             // Comença la connexió

            conn.connect();

            int response = conn.getResponseCode();
            Log.d(TAG, "Rebent dades des del Servidor en streaming: ");
            InputStream  is = new BufferedInputStream(conn.getInputStream());
            Log.d(TAG,"Convertint l'streaming en un String: ");
            resposta = converteixStreamAString(is);
            Log.d(TAG,"Resposta: ("+response+")"+resposta);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            return resposta;
        }
    }


    private String converteixStreamAString(InputStream is) {

        BufferedReader reader = null;  // buffer on anirem llegint el document JSON que ens envie el servidor
        StringBuilder sb = new StringBuilder(); // Cadena on anirem afegint les dades del document JSON
                                                //conforme les anem llegint del buffer, línia a línia


        try {
            // creem un buffer per a anar llegint del InputStreamReader
            reader = new BufferedReader(new InputStreamReader(is));

            String line;
            // Bucle per a llegir totes les línies que ens envia el servidor
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');  // afegim la línia llegida a la cadena
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();  // Tanquem l'InputStreamReader
                reader.close(); // Tanquem el BufferReader
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
}

    private void parsejaJSONManeraTradicional(String documentJSON) {
        // Rebem el document JSON i parsejem les dades rebudes al nostre gust

        Log.d(TAG, "Resposta2: " + documentJSON);

        if (documentJSON != null) {
            try {


                // Aquest document comença amb un JSONArray
                JSONArray contacts =  new JSONArray(documentJSON);

                // bucle per a recórrer tots els Contactes
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject jsonObj = contacts.getJSONObject(i);  //Agafem un contacte

                    //accedim als camps que ens interessa de l'objecte JSON
                    String id = jsonObj.getString("id");
                    String name = jsonObj.getString("name");
                    String email = jsonObj.getString("email");
                    String address = jsonObj.getJSONObject("address").getString("street");


                    // Company és un altre JSONObject
                    JSONObject company = jsonObj.getJSONObject("company");
                    String company_name = company.getString("name");
                    String company_catchPhrase = company.getString("catchPhrase");

                    // Creem un Contacte temporal per a afegir-lo a l'ArrayList
                    Contacte unContacte = new Contacte();

                    // Omplim les dades del Contacte amb les dades obtingudes del JSONObject
                    unContacte.setId(Integer.parseInt(id));
                    unContacte.setName(name);
                    unContacte.setEmail(email);
                    unContacte.setCompany_name(company_name);
                    unContacte.setCompanyCatchPhrase(company_catchPhrase);

                    //Mostrem les dades rebudes
                    Log.d(TAG,unContacte.toString());
                    // AFEGIM EL CONTACTE A L'ARRAYLIST
                    contactesParsejats.add(unContacte);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Error parsejant Json: " + e.getMessage());
                Snackbar.make(findViewById(R.id.relativeLayout),
                        "Error parsejant Json", Snackbar.LENGTH_LONG).show();
            }
        } else {
            Log.e(TAG, "Error intentant rebre el Json.");
            Snackbar.make(findViewById(R.id.relativeLayout),
                    "Error intentant rebre el Json.", Snackbar.LENGTH_LONG).show();


        }

    }

    //Creem una AsyncTask per a
    // 1er connectar-nos a l'URL dessitjada
    // 2n Descarregar el document JSON en un InputStream
    // 3r Convertir L'InputStream en un String per a parsejar-lo i mostrar el contingut en la IU.
    private class ConnectaURL extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            documentJSON = connectaURL(urls[0]);
            long tempsInicial = System.currentTimeMillis();
            // Log.d(TAG, "Vaig a parsejar..." + documentJSON);
            parsejaJSONManeraTradicional(documentJSON);
            long tempsFinal = System.currentTimeMillis();

            return ((tempsFinal-tempsInicial)+" ms.");

        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Mostrem el temps que s'ha tardat en realitzar el parseig
            tv_etiqueta1.append(" realitzat en "+s);
            //Creem l'adaptador que interactuarà amb les dades
            ac = new AdaptadorContacte(contactesParsejats);
            //Enllacem el RecyclerView amb l'adaptador per a que mostre el contingut del Recyclerview
            rvTradicional.setAdapter(ac);
        }
    }
}
