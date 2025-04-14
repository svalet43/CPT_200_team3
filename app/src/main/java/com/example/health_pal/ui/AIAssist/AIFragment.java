package com.example.health_pal.ui.AIAssist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import androidx.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//import com.google.ai.client.generativeai.type.GenerateContentRequest;

import com.example.health_pal.R;
import com.example.health_pal.databinding.FragmentAiBinding;


public class AIFragment extends Fragment {
    private FragmentAiBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AIViewModel aivewmodel  =
                new ViewModelProvider(this).get(AIViewModel.class);
        View view = inflater.inflate(R.layout.fragment_ai, container, false);

        binding = FragmentAiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view,
        @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button testButton;
        testButton = view.findViewById(R.id.buttonTEST);
        EditText ai_in;
        ai_in = view.findViewById(R.id.editTextText);
        TextView ai_out;
        ai_out = view.findViewById(R.id.textView3);

        testButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String in_text = ai_in.getText().toString().trim();

                if (!in_text.isEmpty()) {
                    ai_out.setText("Awating Response...");
                    askGemini(in_text, ai_out);
                }
                else {
                    Toast.makeText(getContext(), "Input cannot be Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void askGemini(String prompt, TextView out_text) {
        gemini model = new gemini();
        String query = prompt;
        out_text.setText("Asking Gemini...");

        model.getResponse(query, new responseCallback() {
            @Override
            public void onResponse(String response) {
                out_text.setText(response);
            }

            @Override
            public void onError(Throwable throwable) {
                out_text.setText("ERROR: " + throwable.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
