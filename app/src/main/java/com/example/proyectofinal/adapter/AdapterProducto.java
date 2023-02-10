package com.example.proyectofinal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Buscador.Productos;
import com.example.proyectofinal.R;

import java.util.List;

public class AdapterProducto extends RecyclerView.Adapter<AdapterProducto.viewholderProductos> {

    List<Productos> listaprodcutos;

    public AdapterProducto(List<Productos> listaprodcutos) {
        this.listaprodcutos = listaprodcutos;
    }

    @NonNull
    @Override
    public viewholderProductos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_productos,parent,false);
        viewholderProductos holder = new viewholderProductos(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderProductos holder, int position) {

        Productos pr = listaprodcutos.get(position);

        holder.p_nombre.setText("Nombre: "+pr.getNombre());
        holder.p_presentacion.setText("Presentacion: "+pr.getPresentacion());
        holder.p_empresa.setText("Empresa: "+pr.getEmpresa());
        holder.p_tipo.setText("Tipo: "+pr.getTipo());

    }

    @Override
    public int getItemCount() {
        return listaprodcutos.size();
    }

    public class viewholderProductos extends RecyclerView.ViewHolder {

        TextView p_nombre,p_presentacion,p_empresa,p_tipo;
        public viewholderProductos(@NonNull View itemView) {
            super(itemView);
            p_nombre = itemView.findViewById(R.id.nombreproducto);
            p_presentacion = itemView.findViewById(R.id.Presentacion);
            p_empresa = itemView.findViewById(R.id.empresa);
            p_tipo = itemView.findViewById(R.id.tipo);
        }
    }
}
