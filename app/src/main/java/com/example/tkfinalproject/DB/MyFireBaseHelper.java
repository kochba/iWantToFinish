package com.example.tkfinalproject.DB;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.example.tkfinalproject.RePostry.User;
import com.example.tkfinalproject.Utility.BaseActivity;
import com.example.tkfinalproject.Utility.ConnectivityListener;
import com.example.tkfinalproject.Utility.IonComplete;
import com.example.tkfinalproject.Utility.UtilityClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class helps in interacting with Firebase to perform operations such as adding, updating,
 * and removing users. It also handles connectivity changes and manages tasks accordingly.
 */
public class MyFireBaseHelper {
    // Firebase database instance
    FirebaseDatabase database;
    // Reference to the "Users" node in the database
    DatabaseReference reference;
    // Flag indicating if the current task is finished
    Boolean finishd;
    // Flag indicating if the task should not be stopped
    Boolean dontStooped;
    // Application context
    Context myContext;
    // Utility class instance for showing alerts
    UtilityClass utilityClass;
    // Executor service for running tasks asynchronously
    ExecutorService executorService;
    // Listener for connectivity changes
    ConnectivityListener connectivityListener;
    // AtomicBoolean to track connectivity status
    AtomicBoolean isConnected = new AtomicBoolean(true);
    // Future object to track the current task
    private Future<?> currentTask;

    /**
     * Constructor to initialize MyFireBaseHelper with a context and lifecycle owner.
     *
     * @param context        The context to use for various operations.
     * @param lifecycleOwner The lifecycle owner to observe connectivity changes.
     */
    public MyFireBaseHelper(Context context, LifecycleOwner lifecycleOwner) {
        utilityClass = new UtilityClass(context);
        try {
            finishd = false;
            executorService = Executors.newSingleThreadExecutor();
            connectivityListener = new ConnectivityListener(context);
            dontStooped = connectivityListener.isConnected();
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("Users");
            myContext = context;

            // Observe connectivity changes
            connectivityListener.observe(lifecycleOwner, connected -> {
                if (!connected) {
                    stopCurrentTask();
                }
                isConnected.set(connected);
            });
        } catch (Exception e) {
            utilityClass.showAlertExp();
        }
    }

    /**
     * Stops the current task if it is running.
     */
    private void stopCurrentTask() {
        if (currentTask != null) {
            if (!finishd) {
                dontStooped = false;
                utilityClass.showAlertInternet();
                if (myContext instanceof BaseActivity) {
                    ((BaseActivity) myContext).hideLoadingOverlay();
                }
            }
        }
    }

    /**
     * Adds a new user to Firebase. Calls the provided callback with the result of the operation.
     *
     * @param user       The user to add.
     * @param ionComplete The callback to be called with the result of the operation.
     */
    public void addUser(User user, IonComplete ionComplete) {
        finishd = false;
        if (!isConnected.get() || !dontStooped) {
            utilityClass.showAlertInternet();
            ionComplete.onCompleteBool(false);
            dontStooped = connectivityListener.isConnected();
            if (myContext instanceof BaseActivity) {
                ((BaseActivity) myContext).hideLoadingOverlay();
            }
            return;
        }
        currentTask = executorService.submit(() -> {
            try {
                reference.child(user.getUsername()).setValue(user).addOnCompleteListener(task -> {
                    finishd = true;
                    if (dontStooped) {
                        ionComplete.onCompleteBool(task.isSuccessful());
                    } else {
                        dontStooped = connectivityListener.isConnected();
                    }
                });
            } catch (Exception e) {
                utilityClass.showAlertExp();
                ionComplete.onCompleteBool(false);
            }
        });
    }

    /**
     * Updates the user data in Firebase. Calls the provided callback with the result of the operation.
     *
     * @param current    The current user data.
     * @param user       The new user data.
     * @param ionComplete The callback to be called with the result of the operation.
     */
    public void update(User current, User user, IonComplete ionComplete) {
        finishd = false;
        if (!isConnected.get() || !dontStooped) {
            utilityClass.showAlertInternet();
            ionComplete.onCompleteBool(false);
            dontStooped = connectivityListener.isConnected();
            if (myContext instanceof BaseActivity) {
                ((BaseActivity) myContext).hideLoadingOverlay();
            }
            return;
        }
        currentTask = executorService.submit(() -> {
            try {
                Map<String, Object> updates = new HashMap<>();
                updates.put("username", user.getUsername());
                updates.put("pass", user.getPass());
                reference.child(user.getUsername()).updateChildren(updates).addOnCompleteListener(task -> {
                    finishd = true;
                    if (dontStooped) {
                        ionComplete.onCompleteBool(task.isSuccessful());
                    } else {
                        dontStooped = connectivityListener.isConnected();
                        updates.put("username", current.getUsername());
                        updates.put("pass", current.getPass());
                    }
                });
            } catch (Exception e) {
                utilityClass.showAlertExp();
                ionComplete.onCompleteBool(false);
            }
        });
    }

    /**
     * Removes a user from Firebase. Calls the provided callback with the result of the operation.
     *
     * @param user       The user to remove.
     * @param ionComplete The callback to be called with the result of the operation.
     */
    public void remove(User user, IonComplete ionComplete) {
        finishd = false;
        if (!isConnected.get() || !dontStooped) {
            utilityClass.showAlertInternet();
            dontStooped = connectivityListener.isConnected();
            if (myContext instanceof BaseActivity) {
                ((BaseActivity) myContext).hideLoadingOverlay();
            }
            return;
        }
        currentTask = executorService.submit(() -> {
            try {
                reference.child(user.getUsername()).removeValue().addOnCompleteListener(task -> {
                    finishd = true;
                    if (dontStooped) {
                        ionComplete.onCompleteBool(task.isSuccessful());
                    } else {
                        dontStooped = connectivityListener.isConnected();
                    }
                });
            } catch (Exception e) {
                utilityClass.showAlertExp();
            }
        });
    }

    /**
     * Interface to check if a user exists in Firebase.
     */
    public interface checkUser {
        void onCheckedUser(boolean flag);
    }

    /**
     * Checks if a username exists in Firebase and calls the provided callback with the result.
     *
     * @param username  The username to check.
     * @param checkUser The callback to be called with the result.
     */
    public void userNameExsIts(String username, checkUser checkUser) {
        finishd = false;
        if (!isConnected.get() || !dontStooped) {
            utilityClass.showAlertInternet();
            dontStooped = connectivityListener.isConnected();
            if (myContext instanceof BaseActivity) {
                ((BaseActivity) myContext).hideLoadingOverlay();
            }
            return;
        }
        currentTask = executorService.submit(() -> {
            try {
                reference.child(username).get().addOnCompleteListener(task -> {
                    finishd = true;
                    if (dontStooped) {
                        DataSnapshot dataSnapshot = task.getResult();
                        checkUser.onCheckedUser(task.isSuccessful() && String.valueOf(dataSnapshot.child("username").getValue()).equals(username));
                    } else {
                        dontStooped = connectivityListener.isConnected();
                    }
                });
            } catch (Exception e) {
                utilityClass.showAlertExp();
            }
        });
    }

    /**
     * Checks if a user exists in Firebase and calls the provided callback with the result.
     *
     * @param user      The user to check.
     * @param checkUser The callback to be called with the result.
     */
    public void userExsits(User user, checkUser checkUser) {
        finishd = false;
        if (!isConnected.get() || !dontStooped) {
            utilityClass.showAlertInternet();
            dontStooped = connectivityListener.isConnected();
            if (myContext instanceof BaseActivity) {
                ((BaseActivity) myContext).hideLoadingOverlay();
            }
            return;
        }
        currentTask = executorService.submit(() -> {
            try {
                reference.child(user.getUsername()).get().addOnCompleteListener(task -> {
                    finishd = true;
                    if (dontStooped) {
                        DataSnapshot dataSnapshot = task.getResult();
                        if (task.isSuccessful() && String.valueOf(dataSnapshot.child("username").getValue()).equals(user.getUsername())) {
                            checkUser.onCheckedUser(String.valueOf(dataSnapshot.child("pass").getValue()).equals(user.getPass()));
                        } else {
                            checkUser.onCheckedUser(false);
                        }
                    } else {
                        dontStooped = connectivityListener.isConnected();
                    }
                });
            } catch (Exception e) {
                utilityClass.showAlertExp();
            }
        });
    }

    /**
     * Retrieves a user by username from Firebase and calls the provided callback with the result.
     *
     * @param username The username of the user to retrieve.
     * @param user     The callback to be called with the retrieved user.
     */
    public void getUserByName(String username, IonComplete.IonCompleteUser user) {
        finishd = false;
        if (!isConnected.get() || !dontStooped) {
            utilityClass.showAlertInternet();
            dontStooped = connectivityListener.isConnected();
            if (myContext instanceof BaseActivity) {
                ((BaseActivity) myContext).hideLoadingOverlay();
            }
            return;
        }
        currentTask = executorService.submit(() -> {
            try {
                reference.child(username).get().addOnCompleteListener(task -> {
                    finishd = true;
                    if (dontStooped) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            DataSnapshot dataSnapshot = task.getResult();
                            String retrievedUsername = String.valueOf(dataSnapshot.child("username").getValue());
                            if (username.equals(retrievedUsername)) {
                                String pass = String.valueOf(dataSnapshot.child("pass").getValue());
                                user.onCompleteUser(new User(retrievedUsername, pass));
                            }
                        }
                    } else {
                        dontStooped = connectivityListener.isConnected();
                    }
                });
            } catch (Exception e) {
                utilityClass.showAlertExp();
            }
        });
    }

    /**
     * Destroys the MyFireBaseHelper instance by stopping connectivity observation and shutting down the executor service.
     */
    public void destroy() {
        connectivityListener.stopObserving();
        executorService.shutdownNow();
    }

}