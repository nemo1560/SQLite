package com.example.nemo1.sqliteexample.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nemo1.sqliteexample.Adapter.NotesAdapter;
import com.example.nemo1.sqliteexample.Entity.Note;
import com.example.nemo1.sqliteexample.Interface.SendData;
import com.example.nemo1.sqliteexample.Presenter.DBPresenter;
import com.example.nemo1.sqliteexample.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ControlFragment extends Fragment implements View.OnClickListener,SendData {
    private DBPresenter dbPresenter;
    private EditText search_text,title_AlerBuilder,content_AlertBuilder;
    private Button search_btn,new_btn;
    private RecyclerView recyclerView;
    private SendData sendData;
    private Note note;
    private List<Note> notes;
    private NotesAdapter notesAdapter;
    private int selected = 0;
    public ControlFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control,container,false);
        search_text = view.findViewById(R.id.search_text);
        search_btn = view.findViewById(R.id.search_btn);
        new_btn = view.findViewById(R.id.new_btn);
        recyclerView = view.findViewById(R.id.recycle_view);
        note = new Note();
        initEvent();
        return view;
    }

    private void initEvent() {
        new_btn.setOnClickListener(this);
        search_btn.setOnClickListener(this);
    }

    //Tao menu khi nhan giu trong recyclerView
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu,menu);
        menu.setHeaderTitle("Select the action");
    }
    //Tao menu khi nhan giu trong recyclerView
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                dbPresenter = new DBPresenter(getActivity(),this);
                dbPresenter.getNote(selected);
                customAlertBuilder(note,selected,2); //2: update lai db
                return true;
            case R.id.delete:
                notiBuilderDeleteData();
                return true;
                default:
                    return super.onContextItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == search_btn.getId()){
            String Id = search_text.getText().toString();
            dbPresenter = new DBPresenter(getActivity(),this);
            if(Id.isEmpty()){
                dbPresenter.getAllNotes();
            }
            else {
                dbPresenter.getNote(Integer.valueOf(Id));
            }
        }
        else if (v.getId() == new_btn.getId()){
            customAlertBuilder(note,0,1);//1: add moi trong db
        }
    }

    @Override
    public void onSearchId(int id) {
        this.selected = id;
    }

    @Override
    public void onCallBack() {
        //Ko su dung
    }

    //Interface nhan tat ca cac value String tra ve
    @Override
    public void onSendResult(String result) {
        if(result == "Deleted"){
            Toast.makeText(getActivity(),"Deleted data",Toast.LENGTH_SHORT).show();
        }
        if (result == "Updated"){
            Toast.makeText(getActivity(),"Updated data",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSendListResult(List<Note> noteList) {
        notesAdapter = new NotesAdapter(noteList,this,getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(notesAdapter);
        registerForContextMenu(recyclerView); //add menu (nhan giu) trong list
    }

    @Override
    public void onSendSingleResult(Note note) {
        notes = new ArrayList<>();
        notes.add(note);
        this.note = note; // Luu lai entity de lay title,content da chon update trong AlertBuilder
        notesAdapter = new NotesAdapter(notes,this,getActivity());
        notesAdapter.notifyDataSetChanged();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(notesAdapter);
        registerForContextMenu(recyclerView); //add menu (nhan giu) trong list
    }

    //AlertBuilder de add note moi va update note chon
    private void customAlertBuilder(final Note note, final int Id, final int choice){
        View view = View.inflate(getActivity(),R.layout.custom_alertbuilder,null);
        title_AlerBuilder = view.findViewById(R.id.add_note_title);
        content_AlertBuilder = view.findViewById(R.id.add_note_content);
        title_AlerBuilder.setText(note.getNoteTitle());
        content_AlertBuilder.setText(note.getNoteContent());
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Goi vao presenter -> model de xu ly add DB
                        if(choice == 2){
                            dbPresenter = new DBPresenter(getContext(),ControlFragment.this);
                            note.setNoteId(new Random().nextInt(1000));
                            note.setNoteTitle(title_AlerBuilder.getText().toString());
                            note.setNoteContent(content_AlertBuilder.getText().toString());
                            dbPresenter.updateNote(note,Id);
                            builder.create().dismiss(); // tat bang thong bao nhap thong tin note moi.
                        }
                        else if (choice == 1){
                            dbPresenter = new DBPresenter(getContext(),ControlFragment.this);
                            note.setNoteId(new Random().nextInt(1000));
                            note.setNoteTitle(title_AlerBuilder.getText().toString());
                            note.setNoteContent(content_AlertBuilder.getText().toString());
                            dbPresenter.addNote(note);
                            builder.create().dismiss(); // tat bang thong bao nhap thong tin note moi.
                        }
                    }
                })
                .setNegativeButton("No",null)
                .create().show();
    }

    //tao thong bao co xoa data da chon
    private void notiBuilderDeleteData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete data !")
                .setMessage("Ban co dong y xoa data")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbPresenter = new DBPresenter(getContext(),ControlFragment.this);
                        dbPresenter.deleteNote(selected);
                    }
                })
                .setNegativeButton("No",null)
                .create().show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
