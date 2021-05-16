package tr.yildiz.edu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.MyViewHolder>{

    private Context context;
    Activity activity;
    private ArrayList questions, optionA, optionB, optionC, optionD, optionE, answers, ids;
    String user_id;



    QuestionsAdapter(Activity activity, Context context, ArrayList questions, ArrayList optionA, ArrayList optionB, ArrayList optionC, ArrayList optionD,
                     ArrayList optionE, ArrayList answers, ArrayList ids, String user_id){
        this.activity = activity;
        this.context = context;
        this.questions = questions;
        this.answers = answers;
        this.optionE = optionE;
        this.optionD = optionD;
        this.optionC = optionC;
        this.optionB = optionB;
        this.optionA = optionA;
        this.ids = ids;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.question_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  QuestionsAdapter.MyViewHolder myViewHolder, int i) {
        int position = i;
        myViewHolder.question_txt.setText(String.valueOf(questions.get(i)));
        myViewHolder.a_txt.setText("A) " + String.valueOf(optionA.get(i)));
        myViewHolder.b_txt.setText("B) " + String.valueOf(optionB.get(i)));
        myViewHolder.c_txt.setText("C) " + String.valueOf(optionC.get(i)));
        myViewHolder.d_txt.setText("D) " + String.valueOf(optionD.get(i)));
        myViewHolder.e_txt.setText("E) " + String.valueOf(optionE.get(i)));
        myViewHolder.answer_txt.setText("Cevap: " + String.valueOf(answers.get(i)));
        myViewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateQuestionActivity.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("id", String.valueOf(ids.get(position)));
                intent.putExtra("question",String.valueOf(questions.get(position)));
                intent.putExtra("a",String.valueOf(optionA.get(position)));
                intent.putExtra("b",String.valueOf(optionB.get(position)));
                intent.putExtra("c",String.valueOf(optionC.get(position)));
                intent.putExtra("d",String.valueOf(optionD.get(position)));
                intent.putExtra("e",String.valueOf(optionE.get(position)));
                intent.putExtra("answer",String.valueOf(answers.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView question_txt, a_txt, b_txt, c_txt, d_txt, e_txt, answer_txt;
        Button btnUpdate, btnDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            question_txt = itemView.findViewById((R.id.question_txt));
            a_txt = itemView.findViewById((R.id.a_txt));
            b_txt = itemView.findViewById((R.id.b_txt));
            c_txt = itemView.findViewById((R.id.c_txt));
            d_txt = itemView.findViewById((R.id.d_txt));
            e_txt = itemView.findViewById((R.id.e_txt));
            answer_txt = itemView.findViewById((R.id.answer_txt));
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}


