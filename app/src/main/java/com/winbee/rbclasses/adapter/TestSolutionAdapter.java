package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.model.SIADDSolutionDataModel;

import java.util.ArrayList;

public class TestSolutionAdapter extends RecyclerView.Adapter<TestSolutionAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SIADDSolutionDataModel> list;

    public TestSolutionAdapter(Context context, ArrayList<SIADDSolutionDataModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_test_solution_adapter,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //setting data toAd apter List
        holder.text_title.setText(Html.fromHtml(list.get(position).getQuestionTitle()));
        holder.tv_question_num.setText("Q"+Integer.toString(position+1)+".");
        holder.text_discription.setText(Html.fromHtml(list.get(position).getAnswerDetails()));
        holder.editTextOtion1.setText(Html.fromHtml(list.get(position).getOption1()));
        holder.editTextOtion2.setText(Html.fromHtml(list.get(position).getOption2()));
        holder.editTextOtion3.setText(Html.fromHtml(list.get(position).getOption3()));
        holder.editTextOtion4.setText(Html.fromHtml(list.get(position).getOption4()));



        if (list.get(position).getAnswerDetails().equalsIgnoreCase("")){
            holder.txt_discription.setVisibility(View.GONE);
        }else{
            holder.txt_discription.setVisibility(View.VISIBLE);
        }



        if (list.get(position).getIsAnswered().equals(1)){
            if (list.get(position).getUserAnswer().equals(list.get(position).getCorrectAnswer())){
                if (list.get(position).getOption1().equals(list.get(position).getCorrectAnswer())){
                    holder.view_correct_a.setVisibility(View.VISIBLE);
                    holder.view_b.setVisibility(View.VISIBLE);
                    holder.view_c.setVisibility(View.VISIBLE);
                    holder.view_d.setVisibility(View.VISIBLE);
                    holder.correct_image_a.setVisibility(View.VISIBLE);
                }else if (list.get(position).getOption2().equals(list.get(position).getCorrectAnswer())){
                    holder.view_correct_b.setVisibility(View.VISIBLE);
                    holder.view_a.setVisibility(View.VISIBLE);
                    holder.view_c.setVisibility(View.VISIBLE);
                    holder.view_d.setVisibility(View.VISIBLE);
                    holder.correct_image_b.setVisibility(View.VISIBLE);
                }else if (list.get(position).getOption3().equals(list.get(position).getCorrectAnswer())){
                    holder.view_correct_c.setVisibility(View.VISIBLE);
                    holder.view_b.setVisibility(View.VISIBLE);
                    holder.view_a.setVisibility(View.VISIBLE);
                    holder.view_d.setVisibility(View.VISIBLE);
                    holder.correct_image_c.setVisibility(View.VISIBLE);
                }else if (list.get(position).getOption4().equals(list.get(position).getCorrectAnswer())){
                    holder.view_correct_d.setVisibility(View.VISIBLE);
                    holder.view_b.setVisibility(View.VISIBLE);
                    holder.view_c.setVisibility(View.VISIBLE);
                    holder.view_a.setVisibility(View.VISIBLE);
                    holder.correct_image_d.setVisibility(View.VISIBLE);
                }

                // option is wrong
            }else if (list.get(position).getUserAnswer()!=(list.get(position).getCorrectAnswer())){
                if (list.get(position).getOption1().equals(list.get(position).getUserAnswer())){
                    if (list.get(position).getOption2().equals(list.get(position).getCorrectAnswer())){
                        holder.view_correct_b.setVisibility(View.VISIBLE);
                        holder.correct_image_b.setVisibility(View.VISIBLE);
                        holder.view_d.setVisibility(View.VISIBLE);
                        holder.view_c.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption3().equals(list.get(position).getCorrectAnswer())){
                        holder.view_correct_c.setVisibility(View.VISIBLE);
                        holder.correct_image_c.setVisibility(View.VISIBLE);
                        holder.view_d.setVisibility(View.VISIBLE);
                        holder.view_b.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption4().equals(list.get(position).getCorrectAnswer())){
                        holder.view_correct_d.setVisibility(View.VISIBLE);
                        holder.correct_image_d.setVisibility(View.VISIBLE);
                        holder.view_b.setVisibility(View.VISIBLE);
                        holder.view_c.setVisibility(View.VISIBLE);
                    }
                    holder.editTextOtion1.setVisibility(View.VISIBLE);
                    holder.view_wrong_a.setVisibility(View.VISIBLE);
                    holder.worng_image_a.setVisibility(View.VISIBLE);
                }else if (list.get(position).getOption2().equals(list.get(position).getUserAnswer())){
                    if (list.get(position).getOption1().equals(list.get(position).getCorrectAnswer())){
                        holder.view_correct_a.setVisibility(View.VISIBLE);
                        holder.correct_image_a.setVisibility(View.VISIBLE);
                        holder.view_d.setVisibility(View.VISIBLE);
                        holder.view_c.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption3().equals(list.get(position).getCorrectAnswer())){
                        holder.view_correct_c.setVisibility(View.VISIBLE);
                        holder.correct_image_c.setVisibility(View.VISIBLE);
                        holder.view_d.setVisibility(View.VISIBLE);
                        holder.view_a.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption4().equals(list.get(position).getCorrectAnswer())){
                        holder.view_correct_d.setVisibility(View.VISIBLE);
                        holder.correct_image_d.setVisibility(View.VISIBLE);
                        holder.view_a.setVisibility(View.VISIBLE);
                        holder.view_c.setVisibility(View.VISIBLE);
                    }
                    holder.editTextOtion2.setVisibility(View.VISIBLE);
                    holder.view_wrong_b.setVisibility(View.VISIBLE);
                    holder.worng_image_b.setVisibility(View.VISIBLE);
                }else if (list.get(position).getOption3().equals(list.get(position).getUserAnswer())){
                    if (list.get(position).getOption1().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion1.setVisibility(View.VISIBLE);
                        holder.view_correct_a.setVisibility(View.VISIBLE);
                        holder.correct_image_a.setVisibility(View.VISIBLE);
                        holder.view_d.setVisibility(View.VISIBLE);
                        holder.view_b.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption2().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion2.setVisibility(View.generateViewId());
                        holder.view_correct_b.setVisibility(View.VISIBLE);
                        holder.correct_image_b.setVisibility(View.VISIBLE);
                        holder.view_d.setVisibility(View.VISIBLE);
                        holder.view_a.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption4().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion4.setVisibility(View.VISIBLE);
                        holder.view_correct_d.setVisibility(View.VISIBLE);
                        holder.correct_image_d.setVisibility(View.VISIBLE);
                        holder.view_a.setVisibility(View.VISIBLE);
                        holder.view_b.setVisibility(View.VISIBLE);
                    }
                    holder.editTextOtion3.setVisibility(View.VISIBLE);
                    holder.view_wrong_c.setVisibility(View.VISIBLE);
                    holder.worng_image_c.setVisibility(View.VISIBLE);
                }else if (list.get(position).getOption4().equals(list.get(position).getUserAnswer())){
                    if (list.get(position).getOption1().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion1.setVisibility(View.VISIBLE);
                        holder.view_correct_a.setVisibility(View.VISIBLE);
                        holder.correct_image_a.setVisibility(View.VISIBLE);
                        holder.view_b.setVisibility(View.VISIBLE);
                        holder.view_c.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption2().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion2.setVisibility(View.VISIBLE);
                        holder.view_correct_b.setVisibility(View.VISIBLE);
                        holder.correct_image_b.setVisibility(View.VISIBLE);
                        holder.view_a.setVisibility(View.VISIBLE);
                        holder.view_c.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption3().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion3.setVisibility(View.VISIBLE);
                        holder.view_correct_c.setVisibility(View.VISIBLE);
                        holder.correct_image_c.setVisibility(View.VISIBLE);
                        holder.view_a.setVisibility(View.VISIBLE);
                        holder.view_b.setVisibility(View.VISIBLE);
                    }
                    holder.editTextOtion4.setVisibility(View.VISIBLE);
                    holder.view_wrong_d.setVisibility(View.VISIBLE);
                    holder.worng_image_d.setVisibility(View.VISIBLE);
                }


            }
            //if user has not given answer only show the correct answer
        }else if (list.get(position).getIsAnswered().equals(0)){
            if (list.get(position).getOption1().equals(list.get(position).getCorrectAnswer())){
                holder.editTextOtion1.setVisibility(View.VISIBLE);
                holder.view_unselected_a.setVisibility(View.VISIBLE);
                holder.view_b.setVisibility(View.VISIBLE);
                holder.view_c.setVisibility(View.VISIBLE);
                holder.view_d.setVisibility(View.VISIBLE);
            }else if (list.get(position).getOption2().equals(list.get(position).getCorrectAnswer())){
                holder.editTextOtion2.setVisibility(View.VISIBLE);
                holder.view_unselected_b.setVisibility(View.VISIBLE);
                holder.view_a.setVisibility(View.VISIBLE);
                holder.view_c.setVisibility(View.VISIBLE);
                holder.view_d.setVisibility(View.VISIBLE);
            }else if (list.get(position).getOption3().equals(list.get(position).getCorrectAnswer())){
                holder.editTextOtion3.setVisibility(View.VISIBLE);
                holder.view_unselected_c.setVisibility(View.VISIBLE);
                holder.view_b.setVisibility(View.VISIBLE);
                holder.view_a.setVisibility(View.VISIBLE);
                holder.view_d.setVisibility(View.VISIBLE);
            }else if (list.get(position).getOption4().equals(list.get(position).getCorrectAnswer())){
                holder.editTextOtion4.setVisibility(View.VISIBLE);
                holder.view_unselected_d.setVisibility(View.VISIBLE);
                holder.view_b.setVisibility(View.VISIBLE);
                holder.view_c.setVisibility(View.VISIBLE);
                holder.view_a.setVisibility(View.VISIBLE);
            }
        }

        if (list.get(position).getQuestionTitle_img().endsWith("jpg")){
           holder.img_solution_title.setVisibility(View.VISIBLE);
            Picasso.get().load(list.get(position).getQuestionTitle_img()).into(holder.img_solution_title);
        }else {
            holder.img_solution_title.setVisibility(View.GONE);
        }
        if (list.get(position).getOption1_img().endsWith("jpg")){
            holder.img_solution_option1.setVisibility(View.VISIBLE);
            Picasso.get().load(list.get(position).getOption1_img()).into(holder.img_solution_option1);
        }else {
            holder.img_solution_option1.setVisibility(View.GONE);
        }

        if (list.get(position).getOption2_img().endsWith("jpg")){
            holder.img_solution_option2.setVisibility(View.VISIBLE);
            Picasso.get().load(list.get(position).getOption2_img()).into(holder.img_solution_option2);
        }else {
            holder.img_solution_option2.setVisibility(View.GONE);
        }
        if (list.get(position).getOption3_img().endsWith("jpg")){
            holder.img_solution_option3.setVisibility(View.VISIBLE);
            Picasso.get().load(list.get(position).getOption3_img()).into(holder.img_solution_option3);
        }else {
            holder.img_solution_option3.setVisibility(View.GONE);
        }
            if (list.get(position).getOption4_img().endsWith("jpg")){
                        holder.img_solution_option4.setVisibility(View.VISIBLE);
                        Picasso.get().load(list.get(position).getOption4_img()).into(holder.img_solution_option4);
                    }else {
                        holder.img_solution_option4.setVisibility(View.GONE);
                    }
         if (list.get(position).getAnswerDetails_img().endsWith("jpg")){
                                holder.img_solution_discription.setVisibility(View.VISIBLE);
                                Picasso.get().load(list.get(position).getAnswerDetails_img()).into(holder.img_solution_discription);
                            }else {
                                holder.img_solution_discription.setVisibility(View.GONE);
                            }


    }


    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_title,editTextOtion1,editTextOtion2,editTextOtion3,editTextOtion4,text_discription,tv_question_num,txt_discription;
        private RelativeLayout branch_live;
        private View view_correct_a,view_correct_b,view_correct_c,view_correct_d,view_wrong_a,view_wrong_b,view_wrong_c,view_wrong_d,
                view_unselected_a,view_unselected_b,view_unselected_c,view_unselected_d,view_a,view_b,view_c,view_d;
        private ImageView img_solution_title,img_solution_option1,img_solution_option2,img_solution_option3,
                img_solution_option4,img_solution_discription,correct_image_a,correct_image_b,correct_image_c,correct_image_d,
                worng_image_a,worng_image_b,worng_image_c,worng_image_d;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_title = itemView.findViewById(R.id.text_title);
            tv_question_num = itemView.findViewById(R.id.tv_question_num);
            txt_discription = itemView.findViewById(R.id.txt_discription);
            editTextOtion1 = itemView.findViewById(R.id.editTextOtion1);
            editTextOtion2 = itemView.findViewById(R.id.editTextOtion2);
            editTextOtion3 = itemView.findViewById(R.id.editTextOtion3);
            editTextOtion4 = itemView.findViewById(R.id.editTextOtion4);
            view_correct_a = itemView.findViewById(R.id.view_correct_a);
            view_correct_b = itemView.findViewById(R.id.view_correct_b);
            view_correct_c = itemView.findViewById(R.id.view_correct_c);
            view_correct_d = itemView.findViewById(R.id.view_correct_d);
            view_wrong_a = itemView.findViewById(R.id.view_wrong_a);
            view_wrong_b = itemView.findViewById(R.id.view_wrong_b);
            view_wrong_c = itemView.findViewById(R.id.view_wrong_c);
            view_wrong_d = itemView.findViewById(R.id.view_wrong_d);
            view_unselected_a = itemView.findViewById(R.id.view_unselected_a);
            view_unselected_b = itemView.findViewById(R.id.view_unselected_b);
            view_unselected_c = itemView.findViewById(R.id.view_unselected_c);
            view_unselected_d = itemView.findViewById(R.id.view_unselected_d);
            text_discription = itemView.findViewById(R.id.text_discription);
            img_solution_title = itemView.findViewById(R.id.img_solution_title);
            img_solution_option1 = itemView.findViewById(R.id.img_solution_option1);
            img_solution_option2 = itemView.findViewById(R.id.img_solution_option2);
            img_solution_option3 = itemView.findViewById(R.id.img_solution_option3);
            img_solution_option4 = itemView.findViewById(R.id.img_solution_option4);
            img_solution_discription = itemView.findViewById(R.id.img_solution_discription);
            correct_image_a = itemView.findViewById(R.id.correct_image_a);
            correct_image_b = itemView.findViewById(R.id.correct_image_b);
            correct_image_c = itemView.findViewById(R.id.correct_image_c);
            correct_image_d = itemView.findViewById(R.id.correct_image_d);
            worng_image_a = itemView.findViewById(R.id.worng_image_a);
            worng_image_b = itemView.findViewById(R.id.worng_image_b);
            worng_image_c = itemView.findViewById(R.id.worng_image_c);
            worng_image_d = itemView.findViewById(R.id.worng_image_d);
            view_a = itemView.findViewById(R.id.view_a);
            view_b = itemView.findViewById(R.id.view_b);
            view_c = itemView.findViewById(R.id.view_c);
            view_d = itemView.findViewById(R.id.view_d);
        }
    }
}

