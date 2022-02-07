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
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        conn = new BBDD_Helper(getActivity().getApplicationContext(), "bd_viajes.db",null,1);

        binding.btnreserva.setOnClickListener(view1 -> {reservar();});

        binding.btnverreserva.setOnClickListener(view2 -> {verReserva();});
    }

    private void reservar() {

        SQLiteDatabase db = conn.getWritableDatabase();

        String nombre = binding.etnombre.getText().toString();
        String correo = binding.etcorreo.getText().toString();
        String contraseña = binding.etpass.getText().toString();
        String lugar = binding.etlugar.getText().toString();
        String personas = binding.etcantidad.getText().toString();

        if(!nombre.isEmpty() && !correo.isEmpty()){

            ContentValues values = new ContentValues();

            values.put("nombre",nombre);
            values.put("correo", correo);
            values.put("contraseña", contraseña);
            values.put("lugar", lugar);
            values.put("cantidad", personas);

            db.insert("viajes",null,values);
            db.close();

            Toast.makeText(getContext(),"Registro exitoso", Toast.LENGTH_SHORT).show();
            limpiar();

        }else{Toast.makeText(getContext(), "Rellene los campos nombre y correo", Toast.LENGTH_SHORT).show();}
    }

    private void verReserva() {

        conn = new BBDD_Helper(getActivity().getApplicationContext(), "bd_viajes.db", null, 1);

        SQLiteDatabase db = conn.getReadableDatabase();

        String name = binding.etnombre.getText().toString();

        if (!name.isEmpty()){

            Cursor cursor = db.rawQuery("SELECT * FROM viajes WHERE nombre='" + name + "'", null);

            if(cursor != null && cursor.getCount() > 0){

                cursor.moveToFirst();

                do {
                    binding.tvdatos.setText
                            ("Id:" + cursor.getInt(0) + "\nNombre:" + cursor.getString(1) +
                                    "\nCorreo:" + cursor.getString(2) +
                                    "\nContraseña:" + cursor.getString(3) +
                                    "\nLugar:" + cursor.getString(4) +
                                    "\nPersonas:" + cursor.getString(5));

                }while (cursor.moveToNext());

            }else {Toast.makeText(getActivity().getApplicationContext(), "El cliente: " + name + " no existe", Toast.LENGTH_SHORT).show();}
                //Navigation.findNavController(view2).navigate(R.id.resultadoFragment,bundle);*/

                db.close();

            }else{Toast.makeText(getActivity().getApplicationContext(), "Debes introducir tu nombre", Toast.LENGTH_SHORT).show();}
    }

    private void limpiar() {

        binding.etnombre.setText("");
        binding.etcorreo.setText("");
        binding.etpass.setText("");
        binding.etlugar.setText("");
        binding.etcantidad.setText("");
    }
}