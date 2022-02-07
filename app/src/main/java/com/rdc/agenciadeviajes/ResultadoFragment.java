package com.rdc.agenciadeviajes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.agenciadeviajes.databinding.FragmentResultadoBinding;

public class ResultadoFragment extends Fragment {

    private FragmentResultadoBinding binding;

    public ResultadoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentResultadoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int id_recibido = getArguments().getInt("_id");
        String nombre_recibido = getArguments().getString("nombre");
        String correo_recibido = getArguments().getString("correo");
        String pass_recibido = getArguments().getString("contraseña");
        String lugar_recibido = getArguments().getString("lugar");
        String personas_recibidas = getArguments().getString("cantidad");

        binding.tvid.setText("Id: " + id_recibido);
        binding.tvnombreresult.setText("Nombre: " + nombre_recibido);
        binding.tvcorreoresult.setText("Correo: " + correo_recibido);
        binding.tvpassresult.setText("Contraseña: " + pass_recibido);
        binding.tvlugardeviaje.setText("Lugar: " + lugar_recibido);
        binding.tvcantidadpersonas.setText("Personas: " + personas_recibidas);


        binding.btnInicio.setOnClickListener(view1 -> {

            Navigation.findNavController(view1).navigate(R.id.inicioFragment);

        });
    }
}