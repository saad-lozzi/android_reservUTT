package com.example.reservutt.Common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reservutt.Models.BookingInformation;
import com.example.reservutt.Models.Salle;
import com.example.reservutt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class Common
{
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_SALLE_STORE = "SALLE_SAVE";
    public static final String KEY_DISPLAY_TIME_SLOT = "DISPLAY_TIME_SLOT";
    public static final String KEY_STEP = "STEP";
    public static final int TIME_SLOT_TOTAL = 8;
    public static final Object DISABLE_TAG = "DISABLE";
    public static final String KEY_TIME_SLOT = "TIME_SLOT";
    public static final String KEY_CONFIRM_BOOKING = "CONFIRM_BOOKING";
    public static String IS_LOGIN = "isLogin";
    public static Salle currentSalle;
    public static String Batiment;
    public static Long day;
    public static String list;
    public static int currentTimeSlot =-1;
    public static String currentUserName = "USER_NAME";
    public static String currentUserId = "USER_ID";
    public static String currentSalleName = "SALLE_NAME";
    public static String currentSalleId = "SALLE_ID";
    public static String currentProfession = "PROFESSION";
    public static String slotToDelete = "Slot to delete";
    public static Calendar currentDate = Calendar.getInstance();
    public static SimpleDateFormat simpleFormatDate = new SimpleDateFormat("dd_MM_yyyy");
    public static BookingInformation currentBooking;

    public Common(FirebaseAuth mAuth)
    {
        this.mAuth = mAuth;

        mAuth = FirebaseAuth.getInstance();
    }

    private FirebaseAuth mAuth;


    public static String convertTimeSlotToString(int slot) {
        switch (slot)
        {
            case 0:
                return "8:00 - 9:00";
            case 1:
                return "9:00 - 10:00";
            case 2:
                return "10:00 - 11:00";
            case 3:
                return "11:00 - 12:00";
            case 4:
                return "14:00 - 15:00";
            case 5:
                return "15:00 - 16:00";
            case 6:
                return "16:00 - 17:00";
            case 7:
                return "17:00 - 18:00";
            default:
                return "Fermé";
        }
    }
}
