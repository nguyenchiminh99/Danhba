package com.ncm.nguyenchiminh.danhba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ncm.nguyenchiminh.danhba.MainActivity;
import com.ncm.nguyenchiminh.danhba.R;
import com.ncm.nguyenchiminh.danhba.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private Context context;
    private int resource;
    private List<Contact> arrContact;

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrContact = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact_listview, parent, false);
            viewHolder.imgAvatar = convertView.findViewById(R.id.img_avatar);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.number = (TextView) convertView.findViewById(R.id.tv_number);
            convertView.setTag(viewHolder);
        } else {
           viewHolder = (ViewHolder) convertView.getTag();
        }

        /*Lấy thông tin contact trong array list ra*/
        Contact contact = arrContact.get(position);
        viewHolder.name.setText(contact.getmName());
        viewHolder.number.setText(contact.getmNumber());
        if (contact.isMale()) {
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.boy);
        } else {
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.girl);
        }
        return convertView;
    }

    public class ViewHolder {
        ImageView imgAvatar;
        TextView name;
        TextView number;
    }
}
