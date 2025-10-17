package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private TextView texto;
    private RadioButton radioButton_pulsado;

    public static class Encapsulador {
        private final int imagen;
        private final String titulo;
        private final String texto;
        private boolean dato;

        public Encapsulador(int imagen, String textTitulo, String textContenido, boolean favorito) {
            this.imagen = imagen;
            this.titulo = textTitulo;
            this.texto = textContenido;
            this.dato = favorito;
        }

        public int get_imagen() { return imagen; }
        public String get_textoTitulo() { return titulo; }
        public String get_textoContenido() { return texto; }
        public boolean get_CheckBox() { return dato; }
        public void set_CheckBox(boolean estado) { this.dato = estado; }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.list_view);
        texto = findViewById(R.id.footer_text);

        // Crear la fuente de datos
        ArrayList<Encapsulador> datos = new ArrayList<>();
        datos.add(new Encapsulador(android.R.drawable.ic_menu_gallery, "Opción Uno", "Detalles de la opción 1", false));
        datos.add(new Encapsulador(android.R.drawable.ic_menu_camera, "Opción Dos", "Detalles de la opción 2", false));
        datos.add(new Encapsulador(android.R.drawable.ic_menu_share, "Opción Tres", "Detalles de la opción 3", false));

        Adaptador adaptador = new Adaptador(this, R.layout.entrada, datos) {
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    Encapsulador datosEntrada = (Encapsulador) entrada;

                    // Enlazar vistas
                    ImageView imagen = view.findViewById(R.id.imagen_entrada);
                    TextView titulo = view.findViewById(R.id.texto_titulo);
                    TextView contenido = view.findViewById(R.id.texto_datos);
                    final RadioButton radioButton = view.findViewById(R.id.radio_button_entrada);

                    // Sincronizar datos y estado del RadioButton
                    imagen.setImageResource(datosEntrada.get_imagen());
                    titulo.setText(datosEntrada.get_textoTitulo());
                    contenido.setText(datosEntrada.get_textoContenido());
                    radioButton.setChecked(datosEntrada.get_CheckBox());

                    radioButton.setTag(datosEntrada);

                    radioButton.setOnClickListener(v -> {
                        RadioButton currentRadioButton = (RadioButton) v;
                        Encapsulador currentData = (Encapsulador) currentRadioButton.getTag();

                        // Desmarcar y actualizar
                        if (radioButton_pulsado != null && radioButton_pulsado != currentRadioButton) {
                            radioButton_pulsado.setChecked(false);

                            Encapsulador oldData = (Encapsulador) radioButton_pulsado.getTag();
                            if (oldData != null) {
                                oldData.set_CheckBox(false);
                            }
                        }

                        // Marcar y actualizar
                        currentRadioButton.setChecked(true);
                        radioButton_pulsado = currentRadioButton;
                        currentData.set_CheckBox(true);

                        // Actualizar el footer
                        texto.setText("Seleccionado: " + currentData.get_textoTitulo());
                    });

                    // Rastrea el RadioButton inicial si estaba marcado
                    if (datosEntrada.get_CheckBox()) {
                        radioButton_pulsado = radioButton;
                    }
                }
            }
        };

        lista.setAdapter(adaptador);
    }
}
