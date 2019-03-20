package com.example.nemo1.sqliteexample.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nemo1.sqliteexample.Entity.Note;
import com.example.nemo1.sqliteexample.Interface.SendData;
import com.example.nemo1.sqliteexample.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.RecyclerViewHoler> {
    private List<Note> noteList;
    private SendData sendData;
    private Context context;

    public NotesAdapter(List<Note> noteList, SendData sendData,Context context) {
        this.noteList = noteList;
        this.sendData = sendData;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list,viewGroup,false);
        return new RecyclerViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHoler recyclerViewHoler, int i) {
        recyclerViewHoler.Id.setText(String.valueOf(noteList.get(i).getNoteId()));
        recyclerViewHoler.title.setText(noteList.get(i).getNoteTitle());
        recyclerViewHoler.content.setText(noteList.get(i).getNoteContent());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class RecyclerViewHoler extends RecyclerView.ViewHolder{
        private TextView Id,
                title,
                content;

        public RecyclerViewHoler(@NonNull View itemView) {
            super(itemView);
            Id = itemView.findViewById(R.id.Id);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            //Tao menu khi nhan giu chuot trong recyclerView, goi toi onCreateContextMenu, onContextItemSelected
            itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) context);
        }
    }
}
