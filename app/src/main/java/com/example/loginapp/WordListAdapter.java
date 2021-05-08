package com.example.loginapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class WordListAdapter extends FirebaseRecyclerAdapter<formation,WordListAdapter.myviewholder> {

    public WordListAdapter(@NonNull FirebaseRecyclerOptions<formation> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull formation model) {
        holder.frName.setText(model.getFormationName());
        holder.desc.setText(model.getDesc());
        holder.date.setText(model.getDate());
        holder.nbplace.setText(model.getNbPlace());

        Glide.with(holder.img.getContext()).load(model.getImage()).into(holder.img);


            holder.rev.setOnClickListener(new View.OnClickListener() {
                //button reverver
                @Override
                public void onClick(View v) {

                    int nombre = Integer.valueOf(holder.nbplace.getText().toString());
                    nombre=nombre-1;
                    String nbre=String.valueOf(nombre);

                    //Map<String,Object> map=new HashMap<>();
                    //map.put("nbe",nbre);
                    holder.rev.setVisibility(View.INVISIBLE);
                    FirebaseDatabase.getInstance().getReference().child("foramtions")
                            .child(getRef(position).getKey()).child("nbPlace").setValue(nbre);

                   // Toast.makeText(v.getContext(), ""+nombre, Toast.LENGTH_SHORT).show();


                }

            });


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class  myviewholder extends RecyclerView.ViewHolder{
            CircleImageView img;
            TextView frName ,desc,date ,nbplace;
            Button rev ;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img=(CircleImageView)itemView.findViewById(R.id.img1);
            frName=(TextView)itemView.findViewById(R.id.name);
            date=(TextView)itemView.findViewById(R.id.emailtext);
            desc=(TextView)itemView.findViewById(R.id.coursetext);
            nbplace=(TextView)itemView.findViewById(R.id.nbplace);
            rev=(Button)itemView.findViewById(R.id.reserver);




        }
    }
}
