/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.procasy.dubarah_nocker.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;
import com.procasy.dubarah_nocker.Activity.ChatRoomActivity;
import com.procasy.dubarah_nocker.Activity.JobRequestActivity;
import com.procasy.dubarah_nocker.BadgeInterface;
import com.procasy.dubarah_nocker.MainActivity;
import com.procasy.dubarah_nocker.Model.Message;
import com.procasy.dubarah_nocker.Model.User;
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.Utils.GCMConfig;

import org.json.JSONException;
import org.json.JSONObject;


public class MyGcmPushReceiver extends GcmListenerService  {

    private static final String TAG = MyGcmPushReceiver.class.getSimpleName();


    private NotificationUtils notificationUtils;

    private static final String GCM_TAG = "tag";
    private static final String TITLE_TAG = "title";
    private static final String DESC_TAG = "description";
    private static final String CONTENT_TAG = "content";


    private static final int GENERAL_TAG = 1 ;
    private static final int HELP_TAG = 2 ;
    private static final int USER_Qouta_TAG = 3;
    private static final int Nocker_Qouta_TAG = 4 ;
    private static final int APPOINTEMENT_TAG = 5 ;


    private com.procasy.dubarah_nocker.Helper.Notification mNotification;
    MainActivity activity;
    BadgeInterface badgeInterface = null;


    /**
     * Called when message is received.
     *
     * @param from   SenderID of the sender.
     * @param bundle Data bundle containing message data as key/value pairs.
     *               For Set of keys use data.keySet().
     */



    @Override
    public void onMessageReceived(String from, Bundle bundle) {

        MainActivity.getInstance().updateNotification();


        try {

            Log.e("notify_gcm", "success , type = " + bundle.toString() );

            switch (bundle.getString(GCM_TAG)) {
                case "GENERAL": {
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.drawable.hourly_logo)
                                    .setContentTitle(bundle.getString(TITLE_TAG))
                                    .setContentText(bundle.getString(DESC_TAG));
                    Intent resultIntent = new Intent(this, MainActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                    stackBuilder.addParentStack(MainActivity.class);
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(
                                    0,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    mBuilder.setContentIntent(resultPendingIntent);
                    mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(12, mBuilder.build());
                    mNotification = new com.procasy.dubarah_nocker.Helper.Notification(getApplicationContext());
                    mNotification.open();
                    try {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(mNotification.COL_notification_type, GENERAL_TAG );
                        contentValues.put(mNotification.COL_notfication_status, 0);
                        contentValues.put(mNotification.COL_notfication_title,bundle.getString(TITLE_TAG) );
                        contentValues.put(mNotification.COL_notfication_desc,bundle.getString(DESC_TAG));
                        contentValues.put(mNotification.COL_notfication_content,bundle.getString(CONTENT_TAG) );
                        mNotification.insertEntry(contentValues);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        mNotification.close();
                    }
                    break;
                }

                case "HELP": {
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.drawable.hourly_logo)
                                    .setContentTitle(bundle.getString(TITLE_TAG))
                                    .setContentText(bundle.getString(DESC_TAG));
                    Intent resultIntent = new Intent(this, MainActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                    stackBuilder.addParentStack(MainActivity.class);
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(
                                    0,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    mBuilder.setContentIntent(resultPendingIntent);
                    mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(12, mBuilder.build());
                    mNotification = new com.procasy.dubarah_nocker.Helper.Notification(getApplicationContext());
                    mNotification.open();
                    try {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(mNotification.COL_notification_type, HELP_TAG );
                        contentValues.put(mNotification.COL_notfication_status, 0);
                        contentValues.put(mNotification.COL_notfication_title,bundle.getString(TITLE_TAG) );
                        contentValues.put(mNotification.COL_notfication_desc,bundle.getString(DESC_TAG));
                        contentValues.put(mNotification.COL_notfication_content,bundle.getString(CONTENT_TAG) );
                        mNotification.insertEntry(contentValues);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        mNotification.close();
                    }

                    Intent intent = (new Intent(getApplicationContext(), JobRequestActivity.class));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);

                    break;
                }

                case "APPOINTEMENT": {
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.drawable.hourly_logo)
                                    .setContentTitle(bundle.getString(TITLE_TAG))
                                    .setContentText(bundle.getString(DESC_TAG));
                    Intent resultIntent = new Intent(this, MainActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                    stackBuilder.addParentStack(MainActivity.class);
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(
                                    0,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    mBuilder.setContentIntent(resultPendingIntent);
                    mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(12, mBuilder.build());
                    mNotification = new com.procasy.dubarah_nocker.Helper.Notification(getApplicationContext());
                    mNotification.open();
                    try {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(mNotification.COL_notification_type, APPOINTEMENT_TAG );
                        contentValues.put(mNotification.COL_notfication_status, 0);
                        contentValues.put(mNotification.COL_notfication_title,bundle.getString(TITLE_TAG) );
                        contentValues.put(mNotification.COL_notfication_desc,bundle.getString(DESC_TAG));
                        contentValues.put(mNotification.COL_notfication_content,bundle.getString(CONTENT_TAG) );
                        mNotification.insertEntry(contentValues);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        mNotification.close();
                    }
                    break;
                }

                case "QOUTA_USER": {
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.drawable.hourly_logo)
                                    .setContentTitle(bundle.getString(TITLE_TAG))
                                    .setContentText(bundle.getString(DESC_TAG));
                    Intent resultIntent = new Intent(this, MainActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                    stackBuilder.addParentStack(MainActivity.class);
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(
                                    0,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    mBuilder.setContentIntent(resultPendingIntent);
                    mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(12, mBuilder.build());
                    mNotification = new com.procasy.dubarah_nocker.Helper.Notification(getApplicationContext());
                    mNotification.open();
                    try {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(mNotification.COL_notification_type, USER_Qouta_TAG );
                        contentValues.put(mNotification.COL_notfication_status, 0);
                        contentValues.put(mNotification.COL_notfication_title,bundle.getString(TITLE_TAG) );
                        contentValues.put(mNotification.COL_notfication_desc,bundle.getString(DESC_TAG));
                        contentValues.put(mNotification.COL_notfication_content,bundle.getString(CONTENT_TAG) );
                        mNotification.insertEntry(contentValues);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        mNotification.close();
                    }
                    break;
                }

                case "QOUTA_NOCKER": {
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.drawable.hourly_logo)
                                    .setContentTitle(bundle.getString(TITLE_TAG))
                                    .setContentText(bundle.getString(DESC_TAG));
                    Intent resultIntent = new Intent(this, MainActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                    stackBuilder.addParentStack(MainActivity.class);
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(
                                    0,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    mBuilder.setContentIntent(resultPendingIntent);
                    mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(12, mBuilder.build());
                    mNotification = new com.procasy.dubarah_nocker.Helper.Notification(getApplicationContext());
                    mNotification.open();
                    try {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(mNotification.COL_notification_type, Nocker_Qouta_TAG );
                        contentValues.put(mNotification.COL_notfication_status, 0);
                        contentValues.put(mNotification.COL_notfication_title,bundle.getString(TITLE_TAG) );
                        contentValues.put(mNotification.COL_notfication_desc,bundle.getString(DESC_TAG));
                        contentValues.put(mNotification.COL_notfication_content,bundle.getString(CONTENT_TAG) );
                        mNotification.insertEntry(contentValues);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        mNotification.close();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Processing chat room push message
     * this message will be broadcasts to all the activities registered
     */
    private void processChatRoomPush(String title, boolean isBackground, String data) {
        if (!isBackground) {

            try {
                JSONObject datObj = new JSONObject(data);

                String chatRoomId = datObj.getString("chat_room_id");

                JSONObject mObj = datObj.getJSONObject("message");
                Message message = new Message();
                message.setMessage(mObj.getString("message"));
                message.setId(mObj.getString("message_id"));
                message.setCreatedAt(mObj.getString("created_at"));

                JSONObject uObj = datObj.getJSONObject("user");

                // skip the message if the message belongs to same user as
                // the user would be having the same message when he was sending
                // but it might differs in your scenario

                /// TODO: 8/2/2016 remove to process chat
         /*       if (uObj.getString("user_id").equals(MyApplication.getInstance().getPrefManager().getUser().getId())) {
                    Log.e(TAG, "Skipping the push message as it belongs to same user");
                    return;
                }*/

                User user = new User();
                user.setId(uObj.getString("user_id"));
                user.setEmail(uObj.getString("email"));
                user.setName(uObj.getString("name"));
                message.setUser(user);

                // verifying whether the app is in background or foreground
                if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {

                    // app is in foreground, broadcast the push message
                    Intent pushNotification = new Intent(GCMConfig.PUSH_NOTIFICATION);
                    pushNotification.putExtra("type", GCMConfig.PUSH_TYPE_CHATROOM);
                    pushNotification.putExtra("message", message);
                    pushNotification.putExtra("chat_room_id", chatRoomId);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                    // play notification sound
                    NotificationUtils notificationUtils = new NotificationUtils();
                    notificationUtils.playNotificationSound();
                } else {

                    // app is in background. show the message in notification try
                    Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                    resultIntent.putExtra("chat_room_id", chatRoomId);
                    showNotificationMessage(getApplicationContext(), title, user.getName() + " : " + message.getMessage(), message.getCreatedAt(), resultIntent);
                }

            } catch (JSONException e) {
                Log.e(TAG, "json parsing error: " + e.getMessage());
                Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        } else {
            // the push notification is silent, may be other operations needed
            // like inserting it in to SQLite
        }
    }

    /**
     * Processing user specific push message
     * It will be displayed with / without image in push notification tray
     */
    private void processUserMessage(String title, boolean isBackground, String data) {
        if (!isBackground) {

            String chatRoomId;
            try {
                JSONObject datObj = new JSONObject(data);
                JSONObject mObj = datObj.getJSONObject("message");
                Message message = new Message();
                message.setMessage(mObj.getString("message"));
                message.setId(mObj.getString("message_id"));
                message.setCreatedAt(mObj.getString("created_at"));
                chatRoomId = mObj.getString("chat_room_id");
                JSONObject uObj = datObj.getJSONObject("user");
                User user = new User();
                user.setId(uObj.getString("user_id"));
                user.setEmail(uObj.getString("email"));
                user.setName(uObj.getString("name"));
                message.setUser(user);

                // verifying whether the app is in background or foreground
                if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {

                    // app is in foreground, broadcast the push message
                    Intent pushNotification = new Intent(GCMConfig.PUSH_NOTIFICATION);
                    pushNotification.putExtra("type", GCMConfig.PUSH_TYPE_USER);
                    pushNotification.putExtra("message", message);
                    pushNotification.putExtra("chat_room_id", chatRoomId);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                    // play notification sound
                    NotificationUtils notificationUtils = new NotificationUtils();
                    notificationUtils.playNotificationSound();
                } else {
                    Intent resultIntent = new Intent(getApplicationContext(), ChatRoomActivity.class);
                    resultIntent.putExtra("chat_room_id", chatRoomId);
                    showNotificationMessage(getApplicationContext(), title, message.getMessage(), message.getCreatedAt(), resultIntent);

                }
            } catch (JSONException e) {
                Log.e(TAG, "json parsing error: " + e.getMessage());
                Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        } else {
            // the push notification is silent, may be other operations needed
            // like inserting it in to SQLite
        }
    }

    private void processNotificationMessage(String title, boolean isBackground, String data) {
        if (!isBackground) {

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                showNotificationMessage(getApplicationContext(), title, data, "", resultIntent);
            } else {
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                showNotificationMessage(getApplicationContext(), title, data, "", resultIntent);
            }

        } else {
            // the push notification is silent, may be other operations needed
            // like inserting it in to SQLite
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
