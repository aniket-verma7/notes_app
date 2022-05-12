package com.project.notesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.notesapp.R;
import com.project.notesapp.entity.Note;
import com.project.notesapp.util.FileHandler;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.Holder> {

    private List<Note> noteList;
    private INoteDetail iNoteDetail;
    private Context context;

    public NotesAdapter(List<Note> noteList, INoteDetail iNoteDetail) {
        this.noteList = noteList;
        this.iNoteDetail = iNoteDetail;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        try {
            holder.title.setText(noteList.get(position).getTitle());
            holder.description.setText(noteList.get(position).getDescription());

            String imageList = noteList.get(holder.getAdapterPosition()).getImageList();
            if (!imageList.isEmpty()) {
                imageList = imageList.substring(0, imageList.indexOf(", "));
                holder.image.setImageBitmap(FileHandler.readImageFromExternalStorage(context,imageList));
                holder.mainLinearLayout.setOnClickListener(onClick -> iNoteDetail.showNote(noteList.get(holder.getAdapterPosition())));
            }
        }catch(Exception ex){
            System.out.println(ex.getLocalizedMessage());
        }


    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void listUpdated(List<Note> notes) {
        noteList = notes;
        notifyItemChanged(noteList.size());
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView title, description;
        LinearLayout mainLinearLayout;
        ImageView image;

        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.noteTitle);
            description = itemView.findViewById(R.id.noteDescription);
            mainLinearLayout = itemView.findViewById(R.id.mainCardLayout);
            image = itemView.findViewById(R.id.image);
        }
    }

    public interface INoteDetail {
        public void showNote(Note note);
    }
}
