package com.example.uleulue;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class layoutHistoryHolder extends RecyclerView.ViewHolder {
    TextView nameinh,addressinh,exittimeinh,entrytimeinh,exitdateinh,entrydateinh,realexinh,realeninh;
    public layoutHistoryHolder(@NonNull View itemView) {
        super(itemView);
        nameinh =itemView.findViewById(R.id.nmaeinhistory);
        addressinh =itemView.findViewById(R.id.Addressinh);
        exittimeinh =itemView.findViewById(R.id.extinh);
        entrytimeinh = itemView.findViewById(R.id.entinh);
        exitdateinh=itemView.findViewById(R.id.exdinh);
        entrydateinh=itemView.findViewById(R.id.entdinh);
        realeninh =itemView.findViewById(R.id.rentdinh2);
        realexinh =itemView.findViewById(R.id.rextdinh);

    }
}
