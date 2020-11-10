package com.winbee.rbclasses.Utils;

import com.winbee.rbclasses.model.StudentQAModel;

import java.util.ArrayList;
import java.util.List;

public class OnlineTestData {
    public static String org_code="WB_005";
    public static String auth_code="1";
    //fetch-section-details.php data
    public static String CoachingID="";
    public static String bucketID="";
    public static String bucketName="";
    public static String bucketInfo="";
    public static String logData="";
    public static String status="";
    public static String totalTest="";
    public static int totalQuestion;

    //fetch-section-individual-assessment-cover-details.php data
    public static String bucketIDre="";
    public static String paperID="";
    public static String paperName="";
    public static String paperSection_Encode="";
    public static String isNegativeMarking_encode="";
    public static String time="";
    public static String isOpen="";
    public static String openDate="";
    public static String isNegativeMarking_decode="";
    public static String isPremium_encode="";
    public static String isPremium_decode="";
    public static String description="";
    //fetch-section-individual-assessment-data.php data

    public static String sectionCode="";
    public static String questionID="";
    public static String questionTitle="";
    public static String option1="";
    public static String option2="";
    public static String option3="";
    public static String option4="";
    public static String questionGUID="";

    public static String c="";
    public static String n="";

    //view-result.php data
    public static String PaperID="";
   // public static String TotalQuestion="";
    public static String Attempt="";
    public static String Correct="";
    public static String Wrong="";
    public static String Review="";
    public static String TotalMarks="";

    public static List<StudentQAModel> studentQAModels=new ArrayList<>();

}
