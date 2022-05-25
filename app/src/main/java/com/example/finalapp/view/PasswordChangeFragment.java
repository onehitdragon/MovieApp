package com.example.finalapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalapp.R;
import com.example.finalapp.databinding.FragmentPasswordChangeBinding;
import com.example.finalapp.viewmodel.PasswordChangeViewModelFrag;

public class PasswordChangeFragment extends Fragment {
    private Context context;
    private Button btnChangePassword;
    private PasswordChangeViewModelFrag passwordChangeViewModelFrag;
    private FragmentPasswordChangeBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password_change, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find view
        btnChangePassword = view.findViewById(R.id.btnChangePassword);

        // init
        passwordChangeViewModelFrag = new ViewModelProvider(this).get(PasswordChangeViewModelFrag.class);
        binding.setPasswordChangeAccount(passwordChangeViewModelFrag.getPasswordChangeAccount());

        // event
        btnChangePassword.setOnClickListener((View v) -> {

        });
    }
}