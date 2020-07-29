package com.winbee.rbclasses.WebApi;



import com.winbee.rbclasses.model.AskDoubtQuestion;
import com.winbee.rbclasses.model.BannerModel;
import com.winbee.rbclasses.model.BranchName;
import com.winbee.rbclasses.model.CourseContentModel;
import com.winbee.rbclasses.model.CourseContentPdfModel;
import com.winbee.rbclasses.model.CourseModel;
import com.winbee.rbclasses.model.CurrentAffairsModel;
import com.winbee.rbclasses.model.ForgetMobile;
import com.winbee.rbclasses.model.LiveClass;
import com.winbee.rbclasses.model.McqAskedQuestionModel;
import com.winbee.rbclasses.model.McqQuestionModel;
import com.winbee.rbclasses.model.McqQuestionSolutionModel;
import com.winbee.rbclasses.model.McqSolutionModel;
import com.winbee.rbclasses.model.NewDoubtQuestion;
import com.winbee.rbclasses.model.NotesModel;
import com.winbee.rbclasses.model.OtpVerify;
import com.winbee.rbclasses.model.PaymentModel;
import com.winbee.rbclasses.model.RefCode;
import com.winbee.rbclasses.model.RefUser;
import com.winbee.rbclasses.model.ResetPassword;
import com.winbee.rbclasses.model.SolutionDoubtQuestion;
import com.winbee.rbclasses.model.SolutionQuestion;
import com.winbee.rbclasses.model.TxnModel;
import com.winbee.rbclasses.model.UpdateModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ClientApi {
    //login
    @POST("fetch_user_cover_information.php")
    Call<RefCode> refCodeSignIn(
            @Query("SubURL") int SubURL,
            @Query("username") String username,
            @Query("password") String password,
            @Query("refcode") String refcode
    );

    //register
    @POST("user_registration_information.php")
    Call<RefUser> refUserSignIn(
            @Query("SubURL") int SubURL,
            @Query("name") String name,
            @Query("email") String email,
            @Query("mobile") String mobile,
            @Query("refcode") String refcode,
            @Query("password") String password);

    //forget password
    @POST("send-otp.php")
    Call<ForgetMobile> getForgetMobile(
            @Query("SubURL") int SubURL,
            @Query("username") String username
    );


    //reset password after forget
    @POST("reset-password.php")
    Call<ResetPassword> getResetPassword(
            @Query("SubURL") int SubURL,
            @Query("username") String username,
            @Query("otp") String otp,
            @Query("new_password") String new_password
    );

    //otp verify
    @POST("verify-otp.php")
    Call<OtpVerify> getOtpVerify(
            @Query("SubURL") int SubURL,
            @Query("username") String username,
            @Query("otp") String otp
    );


    //fetch live class
    @POST("fetch-all-live-classes.php")
    Call<ArrayList<LiveClass>> getLive();

    //fetch all notes
    @POST("fetch-notes-of-live-class.php")
    Call<ArrayList<NotesModel>> getNotes(
            @Query("live_class_id") String live_class_id
    );

    // daily updates
    @POST("fetch-daily-update.php")
    Call<ArrayList<UpdateModel>> getDailyupdate();



    //submit doubt
    @FormUrlEncoded
    @POST("ask-doubt.php")
    Call<NewDoubtQuestion> getNewQuestion(
            @Field("title") String title,
            @Field("question") String question,
            @Field("userid") String userid
    );


    // get all doubt
    @POST("beta-doubt-storage.php")
    Call<ArrayList<AskDoubtQuestion>> getQuestion();

    @FormUrlEncoded
    @POST("ask-doubt.php")
    Call<SolutionDoubtQuestion> getNewSolution(
            @Field("userid") String userid,
            @Field("filename") String filename,
            @Field("answer") String answer
    );

    @POST("doubt-storage.php")
    Call<ArrayList<SolutionQuestion>> getSolution(
            @Query("filename") String filename
    );

    @POST("insert_mcq.php")
    Call<McqQuestionSolutionModel> getMcqSolution(
            @Query("UserId") String UserId,
            @Query("SubURL") int SubURL,
            @Query("Solution") String Solution,
            @Query("QuestionId") String QuestionId
    );

    @POST("view-MCQ-data.php")
    Call<ArrayList<McqSolutionModel>> getMcqQuestionSolution(
            @Query("question_id") String question_id
    );

    @POST("insert_mcq.php")
    Call<McqQuestionModel> mcqQuestionNo(
            @Query("UserId") String UserId,
            @Query("Question") String Question,
            @Query("QuestionTitle") String QuestionTitle,
            @Query("Opt1") String Opt1,
            @Query("Opt2") String Opt2,
            @Query("Opt3") String Opt3,
            @Query("Opt4") String Opt4,
            @Query("SubURL") int SubURL,
            @Query("SolutionFlag") int SolutionFlag,
            @Query("Solution") String Solution
    );

    @POST("insert_mcq.php")
    Call<McqAskedQuestionModel> mcqAskedQuestion(
            @Query("UserId") String UserId,
            @Query("SubURL") int SubURL
    );

    @POST("insert_mcq.php")
    Call<McqQuestionModel> mcqQuestionYes(
            @Query("UserId") String UserId,
            @Query("Question") String Question,
            @Query("QuestionTitle") String QuestionTitle,
            @Query("Opt1") String Opt1,
            @Query("Opt2") String Opt2,
            @Query("Opt3") String Opt3,
            @Query("Opt4") String Opt4,
            @Query("SubURL") int SubURL,
            @Query("SolutionFlag") int SolutionFlag,
            @Query("Solution") String Solution
    );

    @POST("fetch-current-affair.php")
    Call<ArrayList<CurrentAffairsModel>> getCurrentAffairs();

    @POST("fetch_bucket_cover_information.php")
    Call<ArrayList<CourseModel>> getPurchasedCourse(
            @Query("SubURL") int SubURL,
            @Query("USER_ID") String USER_ID,
            @Query("ORG_ID") String ORG_ID,
            @Query("PARENT_ID") String PARENT_ID
    );

    @POST("fetch_bucket_cover_information.php")
    Call<ArrayList<CourseContentModel>> getPurchasedCourseContent(
            @Query("SubURL") int SubURL,
            @Query("USER_ID") String USER_ID,
            @Query("ORG_ID") String ORG_ID,
            @Query("PARENT_ID") String PARENT_ID
    );

    @POST("fetch_bucket_cover_information.php")
    Call<ArrayList<CourseContentPdfModel>> getPurchasedCoursePdfContent(
            @Query("SubURL") int SubURL,
            @Query("USER_ID") String USER_ID,
            @Query("ORG_ID") String ORG_ID,
            @Query("PARENT_ID") String PARENT_ID
    );


    @POST("get-order-id-api.php")
    @FormUrlEncoded
    Call<PaymentModel> fetchPaymentData(
            @Field("course_id") String course_id,
            @Field("user_id") String user_id,
            @Field("amount_org_id") String amount_org_id,
            @Field("org_id") String org_id
    );

    @POST("fetch_payment_data.php")
    Call<ArrayList<TxnModel>> getPaymentData(
            @Query("USER_ID") String USER_ID,
            @Query("ORG_ID") String ORG_ID
    );

}
