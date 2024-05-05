package com.example.aula3004;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegistrarActivity extends AppCompatActivity {

    EditText editEmail, editSenha;
    Button buttonSalvar;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);
        buttonSalvar = (Button) findViewById(R.id.buttonSalvar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        buttonSalvar.setOnClickListener(v -> {
            inserirUsuario();
        });
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void inserirUsuario() {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(editEmail.getText().toString(), editSenha.getText().toString())
                .addOnCompleteListener(this, new
                        OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegistrarActivity.this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
                                    Intent i = new
                                            Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(RegistrarActivity.this, "Falha ao inserir usuário", Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });
    }
}