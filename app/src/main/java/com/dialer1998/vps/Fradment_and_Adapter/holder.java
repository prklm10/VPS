package com.dialer1998.vps.Fradment_and_Adapter;

import android.view.View;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dialer1998.vps.R;

class holder extends RecyclerView.ViewHolder {
    TextView Title;
    TextView update;
    public holder(@NonNull View itemView) {
        super ( itemView );
        this.Title=itemView.findViewById ( R.id.Status_Title );
        this.update=itemView.findViewById (R.id.status_update );


    }
}
