package com.example.nemo1.sqliteexample.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nemo1.sqliteexample.Entity.Note;
import com.example.nemo1.sqliteexample.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.RecyclerViewHoler> {
    private List<Note> noteList;

    public NotesAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public RecyclerViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list,viewGroup,false);
        return new RecyclerViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHoler recyclerViewHoler, int i) {
        recyclerViewHoler.Id.setText(noteList.get(i).getNoteId());
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
        }
    }
}
