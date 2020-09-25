package com.hanahzac.hostelmanagement;

import android.content.Context;
import android.content.res.ColorStateList;
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

public class PermissionListAdapter extends ArrayAdapter<Permission> {

    private Context cont;
    int mRes;

    public PermissionListAdapter(Context context, int resource, List<Permission> objects) {
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

        LayoutInflater inflater = LayoutInflater.from(cont);
        convertView = inflater.inflate(mRes, parent, false);

        TextView reason = convertView.findViewById(R.id.reasonText);
        TextView place = convertView.findViewById(R.id.placeText);
        TextView leave = convertView.findViewById(R.id.leaveText);
        TextView leaveTime = convertView.findViewById(R.id.leaveTimeText);
        TextView name = convertView.findViewById(R.id.studNameText);
        TextView rtrn = convertView.findViewById(R.id.rtrnText);
        TextView rtrnTime = convertView.findViewById(R.id.rtrnTimeText);
        TextView parapp = convertView.findViewById(R.id.parentAppText);
        TextView admapp = convertView.findViewById(R.id.adminAppText);

        if (txt_admapp.equals("Permission Granted!")) {
            admapp.setTextColor(Color.parseColor("#092BEA"));
        }

        if (txt_parapp.equals("Permission Granted!")) {
            parapp.setTextColor(Color.parseColor("#092BEA"));
        }

        reason.setText(txt_reason);
        place.setText(txt_place);
        leave.setText(txt_leave);
        leaveTime.setText(txt_leaveTime);
        name.setText(txt_name);
        rtrn.setText(txt_rtrn);
        rtrnTime.setText(txt_rtrnTime);
        parapp.setText(txt_parapp);
        admapp.setText(txt_admapp);

        return convertView;
    }
}
