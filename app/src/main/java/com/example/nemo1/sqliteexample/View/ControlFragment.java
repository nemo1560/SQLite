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
    private List<Note> notes;
    private NotesAdapter notesAdapter;
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
            case R.id.create:
                Toast.makeText(getActivity(),"create",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.view:
                Toast.makeText(getActivity(),"view",Toast.LENGTH_SHORT).show();
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
            customAlerBuilder();
        }
    }

    @Override
    public void onSearchId(int id) {

    }

    @Override
    public void onCallBack() {

    }

    @Override
    public void onSendResult(String result) {
        customAlerBuilder();
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
        notesAdapter = new NotesAdapter(notes,this,getActivity());
        notesAdapter.notifyDataSetChanged();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(notesAdapter);
        registerForContextMenu(recyclerView); //add menu (nhan giu) trong list
    }

    //Custom parttern AlertBuilder
    private void customAlerBuilder(){
        final View view = View.inflate(getActivity(),R.layout.custom_alertbuilder,null);
        title_AlerBuilder = view.findViewById(R.id.add_note_title);
        content_AlertBuilder = view.findViewById(R.id.add_note_content);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Goi vao presenter -> model de xu ly add DB
                dbPresenter = new DBPresenter(getContext(),ControlFragment.this);
                Note note = new Note(new Random().nextInt(1000),title_AlerBuilder.getText().toString(),content_AlertBuilder.getText().toString());
                dbPresenter.addNote(note);
                builder.create().dismiss(); // tat bang thong bao nhap thong tin note moi.
            }
        });
        builder.setNegativeButton("No",null);
        builder.create().show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
