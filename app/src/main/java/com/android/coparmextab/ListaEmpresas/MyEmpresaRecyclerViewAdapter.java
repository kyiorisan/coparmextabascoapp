package com.android.coparmextab.ListaEmpresas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.coparmextab.R;
import com.android.coparmextab.UserInterface.MapsActivity;
import com.android.retrofitutils.ClienteRest;
import com.android.utiles.Empresa;
import com.android.utiles.JsonEmpresas;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Url;

/**
 * {@link RecyclerView.Adapter} que puede mostrar {@link Empresa} hace una llamada al
 */
public class MyEmpresaRecyclerViewAdapter extends RecyclerView.Adapter<MyEmpresaRecyclerViewAdapter.EmpresaViewHolder> {


    private final HashMap<String, Integer> imagenes;

    /**
     * Una lista que contiene los objetos de empresa a ser mostrados.
     */

    private final List<Empresa> mValues = new ArrayList<>();


    /**
     * Constante que define si la empresa es una empresa de 10.
     */
    private static final int TYPE_EMPRESA_10 = 1;


    /**
     * Posición seleccionada por el usuario en longpress
     */
    private int selectedPos = -1;

    private FragmentInteractionListener mfragmentInteractionListener;

    public MyEmpresaRecyclerViewAdapter(List<Empresa> lista, FragmentInteractionListener fragmentListener) {
        mfragmentInteractionListener = fragmentListener;
        mValues.addAll(lista);
        imagenes = new HashMap<>();
        imagenes.put("gol970314e77", R.drawable.gruasolmeca);
        imagenes.put("AEC960626I21", R.drawable.ado);
        imagenes.put("BIM011108DJ5", R.drawable.bimbo);
        imagenes.put("ACT6808066SA", R.drawable.tresguerras);
        imagenes.put("CAP821208LQ3", R.drawable.apasco);
        imagenes.put("CCM010710UU1", R.drawable.cuauhtemoc);
        imagenes.put("DRA041022VD8", R.drawable.dagdug);
        imagenes.put("CFS950529980", R.drawable.union);
        imagenes.put("EPS950901R32", R.drawable.silva);
        imagenes.put("ETA050531TB7", R.drawable.exi);
        imagenes.put("FCM070911MM1", R.drawable.fcm);
        imagenes.put("FER941107H25", R.drawable.ferromax);
        imagenes.put("IIS070718V89", R.drawable.integrait);
        imagenes.put("LRT670904UA5", R.drawable.royal);
        imagenes.put("MCI011029MO8", R.drawable.chimay);
        imagenes.put("ODO0010186B7", R.drawable.odonto);
        imagenes.put("PME110921NK6", R.drawable.petrofac);
        imagenes.put("PPL9202137A7", R.drawable.pintaplus);
        imagenes.put("PRO090121GN3", R.drawable.processa);
        imagenes.put("RSY0307231I4", R.drawable.radikal);
        imagenes.put("SEP02102FL3", R.drawable.uag);
        imagenes.put("TEC030626BI9", R.drawable.tecno);
        imagenes.put("TNI8501249L9", R.drawable.todo);
        imagenes.put("UTM010711E2A", R.drawable.tecmi);
        imagenes.put("CCS0308152M5", R.drawable.comer);
        imagenes.put("CAH990125IFA", R.drawable.cahdez);
        imagenes.put("COF010523BS9", R.drawable.cofisur);
        imagenes.put("GCO1012042Y2", R.drawable.comsurlab);
        imagenes.put("CAR851022AB6", R.drawable.conarechiga);
        imagenes.put("CER050524D13", R.drawable.crinon);
        imagenes.put("CCS0308152M5", R.drawable.comer);
        imagenes.put("CCL990108RCA", R.drawable.cuclaras);
        imagenes.put("GCA0606269C4", R.drawable.gca);
        imagenes.put("GAP100211UC9", R.drawable.gaprosur);
        imagenes.put("GMA100512FV1", R.drawable.glamas);
        imagenes.put("ORG0903318x2", R.drawable.orgtec);
        imagenes.put("TCS050422KT9", R.drawable.tcs);
        imagenes.put("ZTI000926236", R.drawable.ztec);
        imagenes.put("APC070307CX8", R.drawable.apcc);
        imagenes.put("BMI9704113PA", R.drawable.bamon);
        imagenes.put("BCH130306J17", R.drawable.bspch);
        imagenes.put("CPN03013RH9", R.drawable.cpnd);
        imagenes.put("ECO110706CH4", R.drawable.editusco);
        imagenes.put("GSM1002269S1", R.drawable.gcservices);
        imagenes.put("GIC030613565", R.drawable.gicze);
        imagenes.put("IEE100923BJ8", R.drawable.iee);
        imagenes.put("LDA060111E32", R.drawable.labdias);
        imagenes.put("MAD120608RR3", R.drawable.mad);
        imagenes.put("MCO9709308E1", R.drawable.manpower);
        imagenes.put("MAA100914PS2", R.drawable.mmaa);
        imagenes.put("GUZA840602SK2", R.drawable.muvit);
        imagenes.put("ISR110125UN8", R.drawable.ram);
        imagenes.put("SOI121217HQ8", R.drawable.soilsolution);
        imagenes.put("TES0607059C6", R.drawable.tiest);
        imagenes.put("TM&070209N94", R.drawable.tmcompany);
    }


    @Override
    public EmpresaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_EMPRESA_10:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empresa_item_10, parent, false);
                break;

            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empresa_item, parent, false);
                break;
        }


        return new EmpresaViewHolder(view, viewType);
    }


    @Override
    public void onBindViewHolder(final EmpresaViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.itemView.setSelected(getSelectedPos() == position);
        holder.itemView.setTag(mValues.get(position));
        holder.mNombreView.setText(holder.mItem.getName());


        //Establece las reglas de el target en el cual se insertará la imagen.
        Target target = new Target() {


            //indica el comportamiento cuando la imagen ha sido cargada
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.mImage_EmpresaView.setVisibility(View.VISIBLE);
                holder.mProgressBar.setVisibility(View.INVISIBLE);
                holder.mImage_EmpresaView.setBackground(new BitmapDrawable(holder.mImage_EmpresaView.getResources(), bitmap));
            }

            //indica el comportamiento cuando no se logra cargar la imagen.
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                holder.mImage_EmpresaView.setBackground(errorDrawable);
                holder.mProgressBar.setVisibility(View.INVISIBLE);
                holder.mImage_EmpresaView.setVisibility(View.VISIBLE);

            }


            //indica el comportamiento mientras la imagen se está cargando.
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                holder.mImage_EmpresaView.setVisibility(View.INVISIBLE);
                holder.mProgressBar.setVisibility(View.VISIBLE);
                holder.mProgressBar.setProgressDrawable(placeHolderDrawable);
                holder.mImage_EmpresaView.setBackgroundResource(R.color.card_background);
            }
        };

        //Picasso.with(holder.mImage_EmpresaView.getContext()).load(imagenes.get(holder.mItem.getRfc())).into(target);



        Picasso.with(holder.mImage_EmpresaView.getContext())
                .load(Uri.parse("http://192.168.112.207:8080/server/imagenes/" + holder.mItem.getRfc() + ".png"))
                .error(R.drawable.user)
                .into(target);

        //Establecer valores de las empresas comunes.
        if (!holder.mItem.isDe10()) {
            //la variable buenoarriba indica si la cantidad de comentarios positivos irá arriba o abajo
            //utilizando la cantidad de comentarios positivos que se tenga de la empresa, esto
            //sólo ocurre con las empresas comunes.
            boolean buenoarriba = holder.mItem.getVALORACIONES().getOK() > holder.mItem.getVALORACIONES().getBAD();

            holder.mNum_TopView.setText(buenoarriba ? String.valueOf(holder.mItem.getVALORACIONES().getOK())
                    : String.valueOf(holder.mItem.getVALORACIONES().getBAD()));
            holder.mNum_BotView.setText(!buenoarriba ? String.valueOf(holder.mItem.getVALORACIONES().getOK())
                    : String.valueOf(holder.mItem.getVALORACIONES().getBAD()));
            holder.mRate_TopView.setImageResource(buenoarriba ? R.drawable.thumbup : R.drawable.thumbdown);
            holder.mRate_BotView.setImageResource(buenoarriba ? R.drawable.thumbdown : R.drawable.thumbup);
            int buen_rate_color = holder.itemView.getResources().getColor(R.color.buenrate);
            int mal_rate_color = holder.itemView.getResources().getColor(R.color.malrate);
            holder.mNum_TopView.setTextColor(buenoarriba ? buen_rate_color : mal_rate_color);
            holder.mNum_BotView.setTextColor(!buenoarriba ? buen_rate_color : mal_rate_color);
        }


        //evento al dar click a un cardView.

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mfragmentInteractionListener.onItemClick(v, position);
            }
        });

        //evento al mantener presionado un cardView.

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectedPos = holder.getAdapterPosition();
                mfragmentInteractionListener.onItemLongPress(v, position);
                notifyItemRangeChanged(0, getItemCount());
                return true;
            }
        });

        //evento al dar click al globito web

        holder.mBrowseView.setOnClickListener(new View.OnClickListener() {
            //usa un intent para usar el navegadorweb.
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "internet", Toast.LENGTH_SHORT);
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + holder.mItem.getWeb()));
                v.getContext().startActivity(i);

            }
        });


        //evento al dar click en el icono de llamada

        holder.mCallView.setOnClickListener(new View.OnClickListener() {

            //crea un intent para el dialer del teléfono usando el número telefónico de la empresa.

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "llamar", Toast.LENGTH_SHORT);
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + holder.mItem.getSucursales().getSucursales()
                        .get(0).getPhone().replaceAll("[^0-9|\\+]", "")));
                Context context = v.getContext();
                context.startActivity(i);

            }
        });


        //evento al dar click en el icono de localización

        holder.mMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "mapa", Toast.LENGTH_SHORT);
                //TODO pedir una consulta de la base de datos que regrese la sucursal principal.
                //TODO implementar una consulta que regrese la más
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                intent.putExtra("name", holder.mItem.getName());
                intent.putExtra("dir", "En un lugar lejano...");
                intent.putExtra("lat", 0.34131324);
                intent.putExtra("lng", 0.2345234);
                v.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Obtiene la opción seleccionada.
     * @return
     */

    public int getSelectedPos() {
        return selectedPos;
    }

    /**
     * Establece la posición seleccionada en el adapter
     * @param selectedPos
     */
    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }


    /**
     * Vista reciclable que se encarga de almacenar los elementos del CardView en el cual
     * se muestran las empresas.
     */

    public class EmpresaViewHolder extends RecyclerView.ViewHolder {


        /**
         * Almacena el nombre de la empresa.
         */
        public final TextView mNombreView;

        /**
         * Almacena la calificación que irá en la parte superior.
         */
        public final TextView mNum_TopView;

        /**
         * Almacena la calificación que irá en la parte inferior.
         */
        public final TextView mNum_BotView;

        /**
         * Almacena la imagen de la empresa.
         */
        public final ImageView mImage_EmpresaView;

        public final ProgressBar mProgressBar;

        /**
         * Almacena la imagen de calificación situada en la parte superior.
         */
        public final ImageView mRate_TopView;
        /**
         * Almacena la imagen de calificación situada en la parte inferior.
         */
        public final ImageView mRate_BotView;
        /**
         * Almacena la imagen de mundo usada para visualizar la web de la empresa.
         */
        public final ImageView mBrowseView;
        /**
         * Almacena la imagen de teléfono usada para marcar al teléfono de la empresa.
         */
        public final ImageView mCallView;
        /**
         * Almacena la imagen de localización usada para mostrar la ubicación de la empresa.
         */
        public final ImageView mMapView;

        /**
         * Objeto que contiene el mapeo de los datos de la empresa.
         */
        public Empresa mItem;

        /**
         * Constructor que recibe el layout y el tipo de layout que se está reciclando.
         * puede ser tipo {@link com.android.coparmextab.R.layout#empresa_item_10}:  usado para
         * empresas de 10,
         * o ser de tipo 0: usado para las empresas comunes.
         *
         * @param view     el layout inflado de empresa de 10 o empresa común.
         * @param viewType
         */


        public EmpresaViewHolder(View view, int viewType) {
            super(view);
            switch (viewType) {
                case TYPE_EMPRESA_10:
                    //inicialización en caso de ser empresa de 10...
                    mNombreView = (TextView) view.findViewById(R.id.nombre10);
                    mProgressBar = (ProgressBar) view.findViewById(R.id.loader10);
                    mBrowseView = (ImageView) view.findViewById(R.id.browse10);
                    mCallView = (ImageView) view.findViewById(R.id.call10);
                    mMapView = (ImageView) view.findViewById(R.id.locate10);
                    mImage_EmpresaView = (ImageView) view.findViewById(R.id.imagen10);
                    mNum_TopView = null;
                    mNum_BotView = null;
                    mRate_TopView = null;
                    mRate_BotView = null;
                    break;

                default:
                    //inicialización en caso de ser empresa común
                    mNombreView = (TextView) view.findViewById(R.id.name);
                    mImage_EmpresaView = (ImageView) view.findViewById(R.id.imagen);
                    mProgressBar = (ProgressBar) view.findViewById(R.id.loader);
                    mNum_TopView = (TextView) view.findViewById(R.id.num_top);
                    mNum_BotView = (TextView) view.findViewById(R.id.num_bot);
                    mRate_BotView = (ImageView) view.findViewById(R.id.emp_iv_bot);
                    mRate_TopView = (ImageView) view.findViewById(R.id.emp_iv_top);
                    mBrowseView = (ImageView) view.findViewById(R.id.browse);
                    mCallView = (ImageView) view.findViewById(R.id.call);
                    mMapView = (ImageView) view.findViewById(R.id.locate);
                    break;
            }
        }

    }


    /**
     * Devuelve el tipo de empresa que se va a inflar por el RecyclerView
     *
     * @param position la posición de la actual empresa a inflar.
     * @return la constante con el tipo de empresa.
     */
    @Override
    public int getItemViewType(int position) {
        return mValues.get(position).isDe10() ? TYPE_EMPRESA_10 : 0;
    }

    /**
     * Actualiza el View para que cambie la lista de RecycleItems(Empresas) que contiene a un nuevo conjunto de
     * Empresas.
     *
     * @param ListaNueva El nuevo conjunto de datos(Empresas) para actualizar.
     */

    public void actualizarListaDatos(List<Empresa> ListaNueva) {
        mValues.clear();
        mValues.addAll(ListaNueva);
        notifyDataSetChanged();
    }

    //<editor-fold desc="Selections">

    public void toggleSelection(int pos) {
        setSelectedPos(pos);
        notifyItemChanged(pos);
    }


    public String getId(int pos) {
        return mValues.get(pos).getRfc();
    }

    /**
     * Este método sirve para actualizar los datos de una sóla empresa en la lista de empresas mostrada
     * en pantalla.
     *
     * @param pos la posición del objeto a cambiar.
     * @param id  el id de la empresa con el que se obtendrán los nuevos datos.
     */
    public void updateItem(final int pos, String id) {
        Call<JsonEmpresas> consulta = ClienteRest.getEmpresaservice().buscarPorId(id);
        consulta.enqueue(new retrofit2.Callback<JsonEmpresas>() {
            @Override
            public void onResponse(Response<JsonEmpresas> response) {
                mValues.set(pos, response.body().getEmpresas().get(0));
                notifyItemChanged(pos);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("no se logró conectar....");
            }
        });
    }


    public void deselect() {
        int deselector = getSelectedPos();
        setSelectedPos(-1);
        notifyItemChanged(deselector);
    }
    //</editor-fold>

    public interface FragmentInteractionListener {
        void onItemClick(View v, int position);

        void onItemLongPress(View v, int position);
    }

}
