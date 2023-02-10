package com.example.proyectofinal.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Buscador.Comerciantes;
import com.example.proyectofinal.Buscador.Productos;
import com.example.proyectofinal.Inicio;
import com.example.proyectofinal.PerfilComercioLista;
import com.example.proyectofinal.Perfil_Comerciante;
import com.example.proyectofinal.R;

import java.util.List;

public class AdapterComerciantes extends RecyclerView.Adapter<AdapterComerciantes.viewholderComerciantes> {

    List<Comerciantes> listacomercios;

    public AdapterComerciantes(List<Comerciantes> listacomercios) {
        this.listacomercios = listacomercios;
    }

    @NonNull
    @Override
    public viewholderComerciantes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comercios,parent,false);
        viewholderComerciantes holder = new viewholderComerciantes(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderComerciantes holder, int position) {

        Comerciantes cm = listacomercios.get(position);

        holder.p_Usuario.setText("Tienda: "+cm.getComercio());
        holder.p_Comercio.setText("Usuario: "+cm.getUsuario());
        holder.p_Dirrecion.setText("Direccion: "+cm.getCalle()+" "+cm.getColonia()+" ,"+cm.getMunicipio()+" "+cm.getEstado());


        holder.PComercio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.PComercio.getContext(), PerfilComercioLista.class);
                intent.putExtra("ID",cm.getID());
                holder.PComercio.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listacomercios.size();
    }

    public class viewholderComerciantes extends RecyclerView.ViewHolder {

        TextView p_Usuario,p_Comercio,p_Dirrecion;
        Button PComercio;

        public viewholderComerciantes(@NonNull View itemView) {
            super(itemView);

            p_Usuario = itemView.findViewById(R.id.nombrecomercio);
            p_Comercio = itemView.findViewById(R.id.usuarioComercio);
            p_Dirrecion = itemView.findViewById(R.id.DireccionComercio);
            PComercio = itemView.findViewById(R.id.PerfilComercio);

        }
    }
}
