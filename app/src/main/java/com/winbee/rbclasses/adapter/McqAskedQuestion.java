package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.rbclasses.LocalData;
import com.winbee.rbclasses.McqSolutionActivity;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.RetrofitApiCall.ApiClient;
import com.winbee.rbclasses.SharedPrefManager;
import com.winbee.rbclasses.WebApi.ClientApi;
import com.winbee.rbclasses.model.McqQuestionSolutionModel;
import com.winbee.rbclasses.model.QuetionDatum;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class McqAskedQuestion extends RecyclerView.Adapter<McqAskedQuestion.ViewHolder> {
    private Context context;
    private List<QuetionDatum> courseDatumList;
    private View view;
    OnSolutionClicked onSolutionClicked;


    public McqAskedQuestion(Context context, List<QuetionDatum> courseDatumList, OnSolutionClicked onSolutionClicked) {
        this.context = context;
        this.courseDatumList = courseDatumList;
        this.onSolutionClicked = onSolutionClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_mcq_asked_question,parent, false);
        return  new ViewHolder(view,onSolutionClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //setting data toAd apter List

        holder.txt_user.setText(courseDatumList.get(position).getUser());
        holder.txt_ask_title.setText(courseDatumList.get(position).getQuestionTitle());
        holder.txt_ask_question.setText(courseDatumList.get(position).getQuestion());
        holder.editTextOtion1.setText(courseDatumList.get(position).getOpt1());
        holder.editTextOtion1_selected.setText(courseDatumList.get(position).getOpt1());
        holder.editTextOtion2_selected.setText(courseDatumList.get(position).getOpt2());
        holder.editTextOtion3_selected.setText(courseDatumList.get(position).getOpt3());
        holder.editTextOtion4_selected.setText(courseDatumList.get(position).getOpt4());
        holder.editTextOtion1_selected_correct.setText(courseDatumList.get(position).getOpt1());
        holder.editTextOtion2_selected_correct.setText(courseDatumList.get(position).getOpt2());
        holder.editTextOtion3_selected_correct.setText(courseDatumList.get(position).getOpt3());
        holder.editTextOtion4_selected_correct.setText(courseDatumList.get(position).getOpt4());
        holder.editTextOtion1_selected_flag0.setText(courseDatumList.get(position).getOpt1());
        holder.editTextOtion2_selected_flag0.setText(courseDatumList.get(position).getOpt2());
        holder.editTextOtion3_selected_flag0.setText(courseDatumList.get(position).getOpt3());
        holder.editTextOtion4_selected_flag0.setText(courseDatumList.get(position).getOpt4());
        holder.editTextOtion2.setText(courseDatumList.get(position).getOpt2());
        holder.editTextOtion3.setText(courseDatumList.get(position).getOpt3());
        holder.editTextOtion4.setText(courseDatumList.get(position).getOpt4());
        if (courseDatumList.get(position).getIs_Attempted()==true){
            if (courseDatumList.get(position).getSolutionFlag().equals(String.valueOf(1))) {
                if (courseDatumList.get(position).getOpt1().equals(courseDatumList.get(position).getAttemptedSolution())) {
                    if (courseDatumList.get(position).getOpt1().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion1.setVisibility(View.GONE);
                        holder.editTextOtion1_selected.setVisibility(View.GONE);
                        holder.editTextOtion1_selected_correct.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    } else if (courseDatumList.get(position).getOpt2().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion1_selected.setVisibility(View.VISIBLE);
                        holder.editTextOtion2.setVisibility(View.GONE);
                        holder.editTextOtion1.setVisibility(View.GONE);
                        holder.editTextOtion2_selected_correct.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    } else if (courseDatumList.get(position).getOpt3().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion3_selected.setVisibility(View.GONE);
                        holder.editTextOtion1_selected.setVisibility(View.VISIBLE);
                        holder.editTextOtion3.setVisibility(View.GONE);
                        holder.editTextOtion1.setVisibility(View.GONE);
                        holder.editTextOtion3_selected_correct.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    } else if (courseDatumList.get(position).getOpt4().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion4_selected.setVisibility(View.GONE);
                        holder.editTextOtion4_selected_correct.setVisibility(View.VISIBLE);
                        holder.editTextOtion1_selected.setVisibility(View.VISIBLE);
                        holder.editTextOtion1.setVisibility(View.GONE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    }
                } else if (courseDatumList.get(position).getOpt2().equals(courseDatumList.get(position).getAttemptedSolution())) {
                    if (courseDatumList.get(position).getOpt2().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion2.setVisibility(View.GONE);
                        holder.editTextOtion2_selected.setVisibility(View.GONE);
                        holder.editTextOtion2_selected_correct.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    } else if (courseDatumList.get(position).getOpt1().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion1.setVisibility(View.GONE);
                        holder.editTextOtion2.setVisibility(View.GONE);
                        holder.editTextOtion1_selected.setVisibility(View.GONE);
                        holder.editTextOtion2_selected.setVisibility(View.VISIBLE);
                        holder.editTextOtion1_selected_correct.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    } else if (courseDatumList.get(position).getOpt3().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion3.setVisibility(View.GONE);
                        holder.editTextOtion2.setVisibility(View.GONE);
                        holder.editTextOtion3_selected.setVisibility(View.GONE);
                        holder.editTextOtion2_selected.setVisibility(View.VISIBLE);
                        holder.editTextOtion3_selected_correct.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    } else if (courseDatumList.get(position).getOpt4().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion4.setVisibility(View.GONE);
                        holder.editTextOtion2.setVisibility(View.GONE);
                        holder.editTextOtion4_selected.setVisibility(View.GONE);
                        holder.editTextOtion2_selected.setVisibility(View.VISIBLE);
                        holder.editTextOtion4_selected_correct.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    }
                } else if (courseDatumList.get(position).getOpt3().equals(courseDatumList.get(position).getAttemptedSolution())) {
                    if (courseDatumList.get(position).getOpt3().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion3.setVisibility(View.GONE);
                        holder.editTextOtion3_selected.setVisibility(View.GONE);
                        holder.editTextOtion3_selected_correct.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    } else if (courseDatumList.get(position).getOpt2().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion2.setVisibility(View.GONE);
                        holder.editTextOtion3.setVisibility(View.GONE);
                        holder.editTextOtion2_selected.setVisibility(View.GONE);
                        holder.editTextOtion3_selected.setVisibility(View.VISIBLE);
                        holder.editTextOtion2_selected_correct.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    } else if (courseDatumList.get(position).getOpt1().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion1.setVisibility(View.GONE);
                        holder.editTextOtion3.setVisibility(View.GONE);
                        holder.editTextOtion1_selected.setVisibility(View.GONE);
                        holder.editTextOtion3_selected.setVisibility(View.VISIBLE);
                        holder.editTextOtion1_selected_correct.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    } else if (courseDatumList.get(position).getOpt4().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion4.setVisibility(View.GONE);
                        holder.editTextOtion3.setVisibility(View.GONE);
                        holder.editTextOtion4_selected.setVisibility(View.GONE);
                        holder.editTextOtion3_selected.setVisibility(View.VISIBLE);
                        holder.editTextOtion4_selected_correct.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    }
                } else if (courseDatumList.get(position).getOpt4().equals(courseDatumList.get(position).getAttemptedSolution())) {
                    if (courseDatumList.get(position).getOpt4().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion4.setVisibility(View.GONE);
                        holder.editTextOtion4_selected.setVisibility(View.GONE);
                        holder.editTextOtion4_selected_correct.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    } else if (courseDatumList.get(position).getOpt3().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion3.setVisibility(View.GONE);
                        holder.editTextOtion4.setVisibility(View.GONE);
                        holder.editTextOtion3_selected.setVisibility(View.GONE);
                        holder.editTextOtion4_selected.setVisibility(View.VISIBLE);
                        holder.editTextOtion3_selected_correct.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    } else if (courseDatumList.get(position).getOpt2().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion2.setVisibility(View.GONE);
                        holder.editTextOtion4.setVisibility(View.GONE);
                        holder.editTextOtion2_selected.setVisibility(View.GONE);
                        holder.editTextOtion4_selected.setVisibility(View.VISIBLE);
                        holder.editTextOtion2_selected_correct.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    } else if (courseDatumList.get(position).getOpt1().equals(courseDatumList.get(position).getSolution())) {
                        holder.editTextOtion1.setVisibility(View.GONE);
                        holder.editTextOtion4.setVisibility(View.GONE);
                        holder.editTextOtion1_selected.setVisibility(View.GONE);
                        holder.editTextOtion4_selected.setVisibility(View.VISIBLE);
                        holder.editTextOtion1_selected_correct.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                        holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                                Intent intent = new Intent(context, McqSolutionActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    }
                }



                //if solution flag is 0
            }else if (courseDatumList.get(position).getSolutionFlag().equals(String.valueOf(0))){
                if (courseDatumList.get(position).getOpt1().equals(courseDatumList.get(position).getAttemptedSolution())) {
                    holder.editTextOtion1.setVisibility(View.GONE);
                    holder.editTextOtion1_selected_flag0.setVisibility(View.VISIBLE);
                    holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                    holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                            Intent intent = new Intent(context, McqSolutionActivity.class);
                            context.startActivity(intent);
                        }
                    });

                } else if (courseDatumList.get(position).getOpt2().equals(courseDatumList.get(position).getAttemptedSolution())) {
                    holder.editTextOtion2.setVisibility(View.GONE);
                    holder.editTextOtion2_selected_flag0.setVisibility(View.VISIBLE);
                    holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                    holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                            Intent intent = new Intent(context, McqSolutionActivity.class);
                            context.startActivity(intent);
                        }
                    });

                } else if (courseDatumList.get(position).getOpt3().equals(courseDatumList.get(position).getAttemptedSolution())) {
                    holder.editTextOtion3.setVisibility(View.GONE);
                    holder.editTextOtion3_selected_flag0.setVisibility(View.VISIBLE);
                    holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                    holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                            Intent intent = new Intent(context, McqSolutionActivity.class);
                            context.startActivity(intent);
                        }
                    });

                } else if (courseDatumList.get(position).getOpt4().equals(courseDatumList.get(position).getAttemptedSolution())) {
                    holder.editTextOtion4.setVisibility(View.GONE);
                    holder.editTextOtion4_selected_flag0.setVisibility(View.VISIBLE);
                    holder.buttonPostMcq1.setVisibility(View.VISIBLE);
                    holder.buttonPostMcq1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LocalData.QuestionID = courseDatumList.get(position).getQuestionId();
                            Intent intent = new Intent(context, McqSolutionActivity.class);
                            context.startActivity(intent);
                        }
                    });
                }

            }
        }else {
            holder.buttonPostMcq1.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return courseDatumList==null ? 0 : courseDatumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_user, txt_ask_title, txt_ask_question, editTextOtion1, editTextOtion1_selected, editTextOtion2,
                editTextOtion2_selected,editTextOtion3,editTextOtion3_selected, editTextOtion4,editTextOtion4_selected,
                editTextOtion1_selected_correct,editTextOtion2_selected_correct,editTextOtion3_selected_correct,editTextOtion4_selected_correct,
                editTextOtion1_selected_flag0,editTextOtion2_selected_flag0,editTextOtion3_selected_flag0,editTextOtion4_selected_flag0;
        RelativeLayout cardView;
        LinearLayout edit_solution;
        Button button_submit_solution, buttonPostMcq1;
        OnSolutionClicked onSolutionClicked;
        LinearLayout edit_option1;

        public ViewHolder(@NonNull View itemView, OnSolutionClicked onSolutionClicked) {
            super(itemView);
            txt_user = itemView.findViewById(R.id.txt_user);
            txt_ask_title = itemView.findViewById(R.id.txt_ask_title);
            txt_ask_question = itemView.findViewById(R.id.txt_ask_question);
            editTextOtion1 = itemView.findViewById(R.id.editTextOtion1);
            editTextOtion1_selected = itemView.findViewById(R.id.editTextOtion1_selected);
            editTextOtion2_selected = itemView.findViewById(R.id.editTextOtion2_selected);
            editTextOtion3_selected = itemView.findViewById(R.id.editTextOtion3_selected);
            editTextOtion4_selected = itemView.findViewById(R.id.editTextOtion4_selected);
            editTextOtion1_selected_correct = itemView.findViewById(R.id.editTextOtion1_selected_correct);
            editTextOtion2_selected_correct = itemView.findViewById(R.id.editTextOtion2_selected_correct);
            editTextOtion3_selected_correct = itemView.findViewById(R.id.editTextOtion3_selected_correct);
            editTextOtion4_selected_correct = itemView.findViewById(R.id.editTextOtion4_selected_correct);
            editTextOtion1_selected_flag0 = itemView.findViewById(R.id.editTextOtion1_selected_flag0);
            editTextOtion2_selected_flag0 = itemView.findViewById(R.id.editTextOtion2_selected_flag0);
            editTextOtion3_selected_flag0 = itemView.findViewById(R.id.editTextOtion3_selected_flag0);
            editTextOtion4_selected_flag0 = itemView.findViewById(R.id.editTextOtion4_selected_flag0);
            editTextOtion2 = itemView.findViewById(R.id.editTextOtion2);
            editTextOtion3 = itemView.findViewById(R.id.editTextOtion3);
            editTextOtion4 = itemView.findViewById(R.id.editTextOtion4);
            buttonPostMcq1 = itemView.findViewById(R.id.buttonPostMcq1);
            cardView = itemView.findViewById(R.id.branch_sem);
            edit_solution = itemView.findViewById(R.id.edit_solution);
            button_submit_solution = itemView.findViewById(R.id.button_submit_solution);
            edit_option1 = itemView.findViewById(R.id.edit_option1);
            editTextOtion1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    solutionValidation();
                }
            });
            editTextOtion2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    solutionValidation1();
                }
            });
            editTextOtion3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    solutionValidation2();
                }
            });
            editTextOtion4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    solutionValidation3();
                }
            });
            this.onSolutionClicked = onSolutionClicked;
        }

        private void solutionValidation() {
            final String solution = String.valueOf(courseDatumList.get(getAdapterPosition()).getOpt1());
            final String question = String.valueOf(courseDatumList.get(getAdapterPosition()).getQuestionId());
            final String userid = SharedPrefManager.getInstance(context).refCode().getUserId();


            McqQuestionSolutionModel mcqQuestionSolutionModel = new McqQuestionSolutionModel();
            mcqQuestionSolutionModel.setQuestionID(question);
            mcqQuestionSolutionModel.setSolution(solution);
            mcqQuestionSolutionModel.setUserID(userid);


            callSolutionApiService(mcqQuestionSolutionModel);

        }

        private void callSolutionApiService(McqQuestionSolutionModel mcqQuestionSolutionModel) {
            ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
            Call<McqQuestionSolutionModel> call = apiCall.getMcqSolution(mcqQuestionSolutionModel.getUserID(), 2, mcqQuestionSolutionModel.getSolution(), mcqQuestionSolutionModel.getQuestionID());
            call.enqueue(new Callback<McqQuestionSolutionModel>() {
                @Override
                public void onResponse(Call<McqQuestionSolutionModel> call, Response<McqQuestionSolutionModel> response) {
                    int statusCode = response.code();
                    if (statusCode == 200 && response.body() != null) {
                        onSolutionClicked.onsolutionclicked();
                        // context.startActivity(new Intent(context, DiscussionActivity.class));
                        Toast.makeText(context, "Option A Submitted", Toast.LENGTH_SHORT).show();
                        editTextOtion1_selected.setVisibility(View.VISIBLE);
                        editTextOtion1.setVisibility(View.GONE);
                        buttonPostMcq1.setVisibility(View.VISIBLE);
                        editTextOtion1.setEnabled(false);
                        editTextOtion2.setEnabled(false);
                        editTextOtion3.setEnabled(false);
                        editTextOtion4.setEnabled(false);


                    } else {
                        System.out.println("Sur: response code" + response.message());
                        Toast.makeText(context, "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<McqQuestionSolutionModel> call, Throwable t) {
                    System.out.println("Suree: " + t.getMessage());
                    Toast.makeText(context, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }


        private void solutionValidation1() {
            final String solution = String.valueOf(courseDatumList.get(getAdapterPosition()).getOpt2());
            final String question = String.valueOf(courseDatumList.get(getAdapterPosition()).getQuestionId());
            final String userid = SharedPrefManager.getInstance(context).refCode().getUserId();
            McqQuestionSolutionModel mcqQuestionSolutionModel1 = new McqQuestionSolutionModel();
            mcqQuestionSolutionModel1.setQuestionID(question);
            mcqQuestionSolutionModel1.setSolution(solution);
            mcqQuestionSolutionModel1.setUserID(userid);


            callSolutionApiService1(mcqQuestionSolutionModel1);

        }

        private void callSolutionApiService1(McqQuestionSolutionModel mcqQuestionSolutionModel1) {
            ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
            Call<McqQuestionSolutionModel> call = apiCall.getMcqSolution(mcqQuestionSolutionModel1.getUserID(), 2, mcqQuestionSolutionModel1.getSolution(), mcqQuestionSolutionModel1.getQuestionID());
            call.enqueue(new Callback<McqQuestionSolutionModel>() {
                @Override
                public void onResponse(Call<McqQuestionSolutionModel> call, Response<McqQuestionSolutionModel> response) {
                    int statusCode = response.code();
                    if (statusCode == 200 && response.body() != null) {
                        onSolutionClicked.onsolutionclicked();
                        // context.startActivity(new Intent(context, DiscussionActivity.class));
                        Toast.makeText(context, "Option B Submitted", Toast.LENGTH_SHORT).show();
                        editTextOtion2_selected.setVisibility(View.VISIBLE);
                        editTextOtion2.setVisibility(View.GONE);
                        buttonPostMcq1.setVisibility(View.VISIBLE);
                        editTextOtion1.setEnabled(false);
                        editTextOtion2.setEnabled(false);
                        editTextOtion3.setEnabled(false);
                        editTextOtion4.setEnabled(false);
                    } else {
                        System.out.println("Sur: response code" + response.message());
                        Toast.makeText(context, "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<McqQuestionSolutionModel> call, Throwable t) {
                    System.out.println("Suree: " + t.getMessage());
                    Toast.makeText(context, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }

        private void solutionValidation2() {
            final String solution = String.valueOf(courseDatumList.get(getAdapterPosition()).getOpt2());
            final String question = String.valueOf(courseDatumList.get(getAdapterPosition()).getQuestionId());
            final String userid = SharedPrefManager.getInstance(context).refCode().getUserId();
            McqQuestionSolutionModel mcqQuestionSolutionModel2 = new McqQuestionSolutionModel();
            mcqQuestionSolutionModel2.setQuestionID(question);
            mcqQuestionSolutionModel2.setSolution(solution);
            mcqQuestionSolutionModel2.setUserID(userid);


            callSolutionApiService2(mcqQuestionSolutionModel2);

        }

        private void callSolutionApiService2(McqQuestionSolutionModel mcqQuestionSolutionModel2) {
            ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
            Call<McqQuestionSolutionModel> call = apiCall.getMcqSolution(mcqQuestionSolutionModel2.getUserID(), 2, mcqQuestionSolutionModel2.getSolution(), mcqQuestionSolutionModel2.getQuestionID());
            call.enqueue(new Callback<McqQuestionSolutionModel>() {
                @Override
                public void onResponse(Call<McqQuestionSolutionModel> call, Response<McqQuestionSolutionModel> response) {
                    int statusCode = response.code();
                    if (statusCode == 200 && response.body() != null) {
                        onSolutionClicked.onsolutionclicked();
                        // context.startActivity(new Intent(context, DiscussionActivity.class));
                        Toast.makeText(context, "Option C Submitted", Toast.LENGTH_SHORT).show();
                        editTextOtion3_selected.setVisibility(View.VISIBLE);
                        editTextOtion3.setVisibility(View.GONE);
                        buttonPostMcq1.setVisibility(View.VISIBLE);
                        editTextOtion1.setEnabled(false);
                        editTextOtion2.setEnabled(false);
                        editTextOtion3.setEnabled(false);
                        editTextOtion4.setEnabled(false);
                    } else {
                        System.out.println("Sur: response code" + response.message());
                        Toast.makeText(context, "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<McqQuestionSolutionModel> call, Throwable t) {
                    System.out.println("Suree: " + t.getMessage());
                    Toast.makeText(context, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }
        private void solutionValidation3() {
            final String solution = String.valueOf(courseDatumList.get(getAdapterPosition()).getOpt2());
            final String question = String.valueOf(courseDatumList.get(getAdapterPosition()).getQuestionId());
            final String userid = SharedPrefManager.getInstance(context).refCode().getUserId();
            McqQuestionSolutionModel mcqQuestionSolutionModel3 = new McqQuestionSolutionModel();
            mcqQuestionSolutionModel3.setQuestionID(question);
            mcqQuestionSolutionModel3.setSolution(solution);
            mcqQuestionSolutionModel3.setUserID(userid);


            callSolutionApiService3(mcqQuestionSolutionModel3);

        }

        private void callSolutionApiService3(McqQuestionSolutionModel mcqQuestionSolutionModel3) {
            ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
            Call<McqQuestionSolutionModel> call = apiCall.getMcqSolution(mcqQuestionSolutionModel3.getUserID(), 2, mcqQuestionSolutionModel3.getSolution(), mcqQuestionSolutionModel3.getQuestionID());
            call.enqueue(new Callback<McqQuestionSolutionModel>() {
                @Override
                public void onResponse(Call<McqQuestionSolutionModel> call, Response<McqQuestionSolutionModel> response) {
                    int statusCode = response.code();
                    if (statusCode == 200 && response.body() != null) {
                        onSolutionClicked.onsolutionclicked();
                        // context.startActivity(new Intent(context, DiscussionActivity.class));
                        Toast.makeText(context, "Option D Submitted", Toast.LENGTH_SHORT).show();
                        editTextOtion4_selected.setVisibility(View.VISIBLE);
                        editTextOtion4.setVisibility(View.GONE);
                        buttonPostMcq1.setVisibility(View.VISIBLE);
                        editTextOtion1.setEnabled(false);
                        editTextOtion2.setEnabled(false);
                        editTextOtion3.setEnabled(false);
                        editTextOtion4.setEnabled(false);
                    } else {
                        System.out.println("Sur: response code" + response.message());
                        Toast.makeText(context, "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<McqQuestionSolutionModel> call, Throwable t) {
                    System.out.println("Suree: " + t.getMessage());
                    Toast.makeText(context, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
    public interface  OnSolutionClicked{
        void onsolutionclicked();
    }
}


