package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Button; // ¡IMPORTACIÓN NECESARIA!
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView texto;
    private RadioButton radioButton_pulsado;

    public static class Encapsulador {
        private final int imagen;
        private final String titulo;
        private final String texto;
        private boolean dato;

        public Encapsulador(int imagen, String textTitulo, String textContenido, boolean selected) {
            this.imagen = imagen;
            this.titulo = textTitulo;
            this.texto = textContenido;
            this.dato = selected;
        }

        public int get_imagen() { return imagen; }
        public String get_textoTitulo() { return titulo; }
        public String get_textoContenido() { return texto; }
        public boolean get_CheckBox() { return dato; }
        public void set_CheckBox(boolean estado) { this.dato = estado; }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lista = findViewById(R.id.list_view);
        texto = findViewById(R.id.footer_text);

        Button btnClearSelection = findViewById(R.id.btn_clear_selection);

        // Crear la fuente de datos
        ArrayList<Encapsulador> datos = new ArrayList<>();
        datos.add(new Encapsulador(R.drawable.drs1, "Dr.Stone Manga 1", "Dr.Stone Manga 1 disponible en amazon", false));
        datos.add(new Encapsulador(R.drawable.drs2, "Dr.Stone Manga 2", "Dr.Stone Manga 2 disponible en amazon", false));
        datos.add(new Encapsulador(R.drawable.drs3, "Dr.Stone Manga 3", "Dr.Stone Manga 3 disponible en amazon", false));
        datos.add(new Encapsulador(R.drawable.drs4, "Dr.Stone Manga 4", "Dr.Stone Manga 4 disponible en amazon", false));
        datos.add(new Encapsulador(R.drawable.drs5, "Dr.Stone Manga 5", "Dr.Stone Manga 5 disponible en amazon", false));

        Adaptador adaptador = new Adaptador(this, R.layout.entrada, datos) {
            @SuppressLint("SetTextI18n")
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
                        texto.setText("Manga Selecinado: " + currentData.get_textoTitulo());
                    });

                    // Rastrea el RadioButton inicial si estaba marcado
                    if (datosEntrada.get_CheckBox()) {
                        radioButton_pulsado = radioButton;
                    }
                }
            }
        };
        lista.setAdapter(adaptador);

        // IMPLEMENTACIÓN DEL BOTÓN DE DESMARCAR SELECCIÓN
        btnClearSelection.setOnClickListener(v -> {
            // 1. Desmarcar el RadioButton visible
            if (radioButton_pulsado != null) {
                radioButton_pulsado.setChecked(false);
            }

            // 2. Limpiar el estado de selección en la fuente de datos
            for(Encapsulador item : datos) {
                item.set_CheckBox(false);
            }

            // 3. Resetear la referencia de control
            radioButton_pulsado = null;

            // 4. Notificar al adaptador para que actualice la vista (necesario para el scroll)
            adaptador.notifyDataSetChanged();

            // 5. Actualizar el footer
            texto.setText("Elige un manga");
        });
    }
}