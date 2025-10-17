package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public abstract class Adaptador extends BaseAdapter {

    private final ArrayList<?> entradas;
    private final int R_layout_IdView;
    private final Context contexto;

    // Constructor
    public Adaptador(Context contexto, int R_layout_IdView, ArrayList<?> entradas) {
        super();
        this.contexto = contexto;
        this.entradas = entradas;
        this.R_layout_IdView = R_layout_IdView;
    }

    // Método abstracto para asignar datos a la vista
    public abstract void onEntrada(Object entrada, View view);

    @Override
    public int getCount() {
        return entradas.size();
    }

    @Override
    public Object getItem(int posicion) {
        return entradas.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup parent) {
        View v = view;

        // Inflar la vista si es nula
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)
                    contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R_layout_IdView, parent, false);
        }

        // Asignar los datos
        onEntrada(entradas.get(posicion), v);

        return v;
    }
}
