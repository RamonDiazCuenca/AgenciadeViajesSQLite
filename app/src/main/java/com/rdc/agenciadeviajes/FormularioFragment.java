package com.rdc.agenciadeviajes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.rdc.agenciadeviajes.databinding.FragmentFormularioBinding;

public class FormularioFragment extends Fragment {

    private FragmentFormularioBinding binding;

    BBDD_Helper conn;

    public FormularioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFormularioBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        conn = new BBDD_Helper(getActivity().getApplicationContext(), "bd_viajes.db", null, 1);

        binding.btnreserva.setOnClickListener(view1 -> {
            reservar();
        });

        binding.btnverreserva.setOnClickListener(view2 -> {
            verReserva();
        });
    }

    private void verReserva() {

        conn = new BBDD_Helper(getActivity().getApplicationContext(), "bd_viajes.db", null, 1);

        String name = binding.etnombre.getText().toString();

        if(!name.isEmpty()){ //sí el campo nombre no esta vacio

            try {
                String[] parametros = {name};//buscamos por este parámetro

                SQLiteDatabase db = conn.getReadableDatabase();

                Cursor cursor = db.rawQuery("SELECT * FROM viajes WHERE nombre=? ", parametros);

                if(cursor.moveToFirst()) {//sí mi consulta contiene valores

                    Bundle bundle = new Bundle();

                    bundle.putInt("_id", cursor.getInt(0));
                    bundle.putString("nombre", cursor.getString(1));
                    bundle.putString("correo", cursor.getString(2));
                    bundle.putString("contraseña", cursor.getString(3));
                    bundle.putString("lugar", cursor.getString(4));
                    bundle.putString("cantidad", cursor.getString(5));


                    Navigation.findNavController(getView()).navigate(R.id.resultadoFragment, bundle);

                    db.close();

                }else{Toast.makeText(getActivity().getApplicationContext(), "Este cliente no tiene ninguna reserva", Toast.LENGTH_LONG).show();}

            } catch(Exception e){Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();}
        }else{Toast.makeText(getActivity().getApplicationContext(), "Debes introducir tu nombre", Toast.LENGTH_LONG).show();}
    }

    private void reservar() {

        String nombre = binding.etnombre.getText().toString();
        String correo = binding.etcorreo.getText().toString();
        String contraseña = binding.etpass.getText().toString();
        String lugar = binding.etlugar.getText().toString();
        String personas = binding.etcantidad.getText().toString();

        if(!nombre.isEmpty()){//sí el campo nombre no esta vacio, primero me buscas este cliente a ver si esta en la bd

            SQLiteDatabase db = conn.getWritableDatabase();
            String[] parametros = {nombre};// buscamos por este parámetro

            try {
                Cursor cursor = db.rawQuery("SELECT * FROM viajes WHERE nombre=? ", parametros);

                if(cursor.moveToFirst()){//sí mi consulta contiene valores

                    Toast.makeText(getActivity().getApplicationContext(), "El cliente ya existe", Toast.LENGTH_SHORT).show();

                    cursor.close();
                    limpiar();

                }else{//sino me registras ese cliente

                    ContentValues values = new ContentValues();

                    values.put("nombre", nombre);
                    values.put("correo", correo);
                    values.put("contraseña", contraseña);
                    values.put("lugar", lugar);
                    values.put("cantidad", personas);

                    db.insert("viajes", null, values);
                    db.close();

                    Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                    limpiar();
                }

            }catch (Exception e) {Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();}

        }else{Toast.makeText(getContext(), "Rellene el nombre", Toast.LENGTH_SHORT).show();}
    }

    private void limpiar() {

        binding.etnombre.setText("");
        binding.etcorreo.setText("");
        binding.etpass.setText("");
        binding.etlugar.setText("");
        binding.etcantidad.setText("");
    }
}