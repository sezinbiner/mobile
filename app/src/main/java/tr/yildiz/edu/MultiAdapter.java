package tr.yildiz.edu;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MyViewHolder>{
    private Context context;
    Activity activity;
    ArrayList<Question> data;
    ArrayList<Question> selectedItems = new ArrayList<>();
    boolean isSelectMode = false;

    MultiAdapter(Activity activity, Context context,ArrayList<Question> data){
        this.activity = activity;
        this.context = context;
        this.data = data;
    }

    public ArrayList<Question> getSelected(){
        ArrayList<Question> selected = new ArrayList<>();
        for(int i = 0; i < data.size();i++){
            if(data.get(i).isChecked()){
                selected.add(data.get(i));
            }
        }
        return selected;
    }

    @NonNull
    @Override
    public MultiAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exam_question_row, viewGroup, false);
        return new MultiAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiAdapter.MyViewHolder myViewHolder, int i) {
        int position = i;
        myViewHolder.question_txt.setText(data.get(i).getQuestion());
        myViewHolder.a_txt.setText("A) " + data.get(i).getA());
        myViewHolder.b_txt.setText("B) " + data.get(i).getB());
        myViewHolder.c_txt.setText("C) " + data.get(i).getC());
        myViewHolder.d_txt.setText("D) " + data.get(i).getD());
        myViewHolder.e_txt.setText("E) " + data.get(i).getE());
        myViewHolder.answer_txt.setText("Cevap: " + data.get(i).getAnswer());
        myViewHolder.bind(data.get(i));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView question_txt, a_txt, b_txt, c_txt, d_txt, e_txt, answer_txt;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            question_txt = itemView.findViewById((R.id.question_txt));
            a_txt = itemView.findViewById((R.id.a_txt));
            b_txt = itemView.findViewById((R.id.b_txt));
            c_txt = itemView.findViewById((R.id.c_txt));
            d_txt = itemView.findViewById((R.id.d_txt));
            e_txt = itemView.findViewById((R.id.e_txt));
            answer_txt = itemView.findViewById((R.id.answer_txt));
            img = itemView.findViewById(R.id.img);
        }

        void bind(final Question quest){
            img.setVisibility(quest.isChecked() ? View.VISIBLE : View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quest.setChecked((!quest.isChecked()));
                    img.setVisibility(quest.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }
}


