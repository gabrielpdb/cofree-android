package com.example.cofree.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.cofree.activities.MainActivity;

public class SessionManager {

    private final Context context;
    private final TokenManager tokenManager;

    public SessionManager(Context context) {
        this.context = context;
        this.tokenManager = new TokenManager(context);
    }

    public void logout() {

        tokenManager.clear();

        Runnable action = () -> {
            Intent intent = new Intent(context, MainActivity.class);

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            context.startActivity(intent);

            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        };

        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(action);
        } else {
            action.run();
        }
    }
}
