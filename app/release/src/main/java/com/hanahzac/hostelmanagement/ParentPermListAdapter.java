package com.hanahzac.hostelmanagement;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ParentPermListAdapter extends ArrayAdapter<Permission> {

    private Context cont;
    int mRes;

    public ParentPermListAdapter(Context context, int resource, List<Permission> objects) {
        super(context, resource, objects);
        cont = context;
        mRes = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String txt_reason = getItem(position).getReason();
        String txt_place = getItem(position).getPlace();
        String txt_leave = getItem(position).getLeave();
        String txt_leaveTime = getItem(position).getLeaveTime();
        String txt_name = getItem(position).getName();
        String txt_rtrn = getItem(position).getRtrn();
        String txt_rtrnTime = getItem(position).getRtrnTime();
        String txt_parapp = getItem(position).getpPerm();
        String txt_admapp = getItem(position).getAdPerm();

        final Permission permission = new Permission(txt_admapp, txt_leave, txt_leaveTime, txt_name, txt_parapp, txt_place, txt_reason, txt_rtrn, txt_rtrnTime);

        LayoutInflater inflater = LayoutInflater.from(cont);
        convertView = inflater.inflate(mRes, parent, false);

        TextView reason = convertView.findViewById(R.id.reasonText);
        TextView place = convertView.findViewById(R.id.placeText);
        TextView name = convertView.findViewById(R.id.studNameText);

        reason.setText(txt_reason);
        place.setText(txt_place);
        name.setText(txt_name);

        return convertView;
    }
}
