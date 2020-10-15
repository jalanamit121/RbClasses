package com.winbee.rbclasses.WebApi;



import com.winbee.rbclasses.NewModels.AskDoubt;
import com.winbee.rbclasses.NewModels.CourseContent;
import com.winbee.rbclasses.NewModels.DailyUpdate;
import com.winbee.rbclasses.NewModels.LiveMessage;
import com.winbee.rbclasses.NewModels.LogOut;
import com.winbee.rbclasses.NewModels.VideoContent;
import com.winbee.rbclasses.model.CourseContentPdfModel;
import com.winbee.rbclasses.model.CurrentAffairsModel;
import com.winbee.rbclasses.model.ForgetMobile;
import com.winbee.rbclasses.model.LiveChatMessage;
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
import com.winbee.rbclasses.model.ResendOtp;
import com.winbee.rbclasses.model.ResetPassword;
import com.winbee.rbclasses.model.SolutionDoubtQuestion;
import com.winbee.rbclasses.model.SolutionQuestion;
import com.winbee.rbclasses.model.TxnModel;


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
            @Query("refcode") String refcode,
            @Query("IMEI") String IMEI
    );

    //logout
    @POST("fetch_user_cover_information.php")
    Call<LogOut> refCodeLogout(
            @Query("SubURL") int SubURL,//3
            @Query("username") String username,
            @Query("password") String password,
            @Query("refcode") String refcode,
            @Query("IMEI") String IMEI
    );

    //force login
    @POST("fetch_user_cover_information.php")
    Call<RefCode> refCodeForceLogout(
            @Query("SubURL") int SubURL,//4
            @Query("username") String username,
            @Query("password") String password,
            @Query("refcode") String refcode,
            @Query("IMEI") String IMEI
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
    @FormUrlEncoded
    @POST("fetch-daily-update.php")
    Call<DailyUpdate> getDailyupdate(
            @Field("user_id") String user_id,
            @Field("device_id") String device_id
    );


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
    Call<AskDoubt> getQuestion(
            @Query("user_id") String user_id,
            @Query("device_id") String device_id
    );

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
    Call<McqQuestionSolutionModel> getMcqSolution(
            @Query("UserId") String UserId,
            @Query("SubURL") int SubURL,
            @Query("Solution") String Solution,
            @Query("QuestionId") String QuestionId
    );

    @POST("view-MCQ-data.php")
    Call<ArrayList<McqSolutionModel>> getMcqQuestionSolution(
            @Query("question_id") String question_id,
            @Query("user_id") String user_id,
            @Query("user_name") String user_name

            //user_id,user_name
    );

    @POST("fetch-current-affair.php")
    Call<ArrayList<CurrentAffairsModel>> getCurrentAffairs(

    );

    @POST("fetch_bucket_cover_information.php")
    Call<CourseContent> getPurchasedCourse(
            @Query("SubURL") int SubURL,
            @Query("USER_ID") String USER_ID,
            @Query("ORG_ID") String ORG_ID,
            @Query("PARENT_ID") String PARENT_ID,
            @Query("DEVICE_ID") String DEVICE_ID
    );

    @POST("fetch_bucket_cover_information.php")
    Call<VideoContent> getPurchasedCourseContent(
            @Query("SubURL") int SubURL,
            @Query("USER_ID") String USER_ID,
            @Query("ORG_ID") String ORG_ID,
            @Query("PARENT_ID") String PARENT_ID,
            @Query("DEVICE_ID") String DEVICE_ID
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

    @POST("send-otp.php")
    Call<ResendOtp> getResendOtp(
            @Query("username") String username,
            @Query("SubURL") int SubURL
    );

    @POST("insert_liveClass_chat.php")
    Call<LiveChatMessage> getLiveMessage(
            @Query("SubURL") int SubURL,
            @Query("UserId") String UserId,
            @Query("UserName") String UserName,
            @Query("LiveClassId") String LiveClassId,
            @Query("Message") String Message,
            @Query("DeviceId") String DeviceId
    );
    @POST("insert_liveClass_chat.php")
    Call<LiveMessage> getLiveMessageFetch(
            @Query("SubURL") int SubURL,
            @Query("UserId") String UserId,
            @Query("DeviceId") String DeviceId,
            @Query("LiveClassId") String LiveClassId
    );

}
