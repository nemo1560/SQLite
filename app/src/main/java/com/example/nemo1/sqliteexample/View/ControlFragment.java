package com.example.nemo1.sqliteexample.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.nemo1.sqliteexample.Adapter.NotesAdapter;
import com.example.nemo1.sqliteexample.Entity.Note;
import com.example.nemo1.sqliteexample.Interface.SendData;
import com.example.nemo1.sqliteexample.Presenter.DBPresenter;
import com.example.nemo1.sqliteexample.R;

import java.util.ArrayList;
import java.util.List;

public class ControlFragment extends Fragment implements View.OnClickListener,SendData {
    private DBPresenter dbPresenter;
    private EditText search_text;
    private Button search_btn;
    private RecyclerView recyclerView;
    private SendData sendData;
    private List<Note> notes;
    private NotesAdapter notesAdapter;
    private static final int MENU_ITEM_VIEW = 111;
    private static final int MENU_ITEM_EDIT = 222;
    private static final int MENU_ITEM_CREATE = 333;
    private static final int MENU_ITEM_DELETE = 444;

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
        recyclerView = view.findViewById(R.id.recycle_view);
        initEvent();
        return view;
    }

    private void initEvent() {

        search_btn.setOnClickListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select the action");
        menu.add(0,MENU_ITEM_VIEW,0,"View note");
        menu.add(0, MENU_ITEM_CREATE , 1, "Create Note");
        menu.add(0, MENU_ITEM_EDIT , 2, "Edit Note");
        menu.add(0, MENU_ITEM_DELETE, 4, "Delete Note");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);

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
    }

    @Override
    public void onSearchId(int id) {

    }

    @Override
    public void onCallBack() {

    }

    @Override
    public void onSendResult(String result) {

    }

    @Override
    public void onSendListResult(List<Note> noteList) {
        notesAdapter = new NotesAdapter(noteList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(notesAdapter);
    }

    @Override
    public void onSendSingleResult(Note note) {
        notes = new ArrayList<>();
        notes.add(note);
        if(notes.size() > 0){
            notesAdapter = new NotesAdapter(notes);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(notesAdapter);
        }
    }
}
