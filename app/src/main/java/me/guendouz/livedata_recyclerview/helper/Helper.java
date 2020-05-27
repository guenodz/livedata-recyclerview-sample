package me.guendouz.livedata_recyclerview.helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import me.guendouz.livedata_recyclerview.R;
import me.guendouz.livedata_recyclerview.listener.ButtonClickListener;
import me.guendouz.livedata_recyclerview.listener.DialogButtonClickListener;

public class Helper {

    public void showCustomDialog(String message, String buttonText, Context context,DialogButtonClickListener dialogButtonClickListener){

        Dialog dialog = new Dialog(context,R.style.Theme_Dialog_Custom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null, false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setContentView(view);

        TextView txt_dialog_message = view.findViewById(R.id.txt_dialog_message);
        txt_dialog_message.setText(message);
        Button btn_dialog = view.findViewById(R.id.btn_dialog);
        btn_dialog.setText(buttonText);
        EditText edt_ticket_count = view.findViewById(R.id.edt_ticket_count);
        btn_dialog.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_white_24dp, 0, 0, 0);
        edt_ticket_count.setVisibility(View.VISIBLE);

        dialog.show();

        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogButtonClickListener != null){
                    dialogButtonClickListener.onDialogButtonClicked(edt_ticket_count.getText().toString(),dialog);
                }

            }
        });

        edt_ticket_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edt_ticket_count.getText().toString().equals("")
                        || edt_ticket_count.getText().toString().equals("0")) {
                    btn_dialog.setEnabled(false);
                    btn_dialog.setAlpha((float) 0.50);
                } else {
                    btn_dialog.setEnabled(true);
                    btn_dialog.setAlpha((float) 0.99);
                }
            }
        });
    }
}
