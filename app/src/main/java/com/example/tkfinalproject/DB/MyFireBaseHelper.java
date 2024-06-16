package com.example.tkfinalproject.DB;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.example.tkfinalproject.RePostry.User;
import com.example.tkfinalproject.Utility.BaseActivity;
import com.example.tkfinalproject.Utility.IonComplete;
import com.example.tkfinalproject.Utility.UtilityClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This class helps in interacting with Firebase to perform operations such as adding, updating,
 * and removing users. It also handles connectivity changes and manages tasks accordingly.
 */
public class MyFireBaseHelper {
    // Flag to track if the constructor has been called before
    private static boolean isFirstTime = true;
    // Firebase database instance
    FirebaseDatabase database;
    // Reference to the "Users" node in the database
    DatabaseReference reference;
    // Flag indicating if the current task is finished
    Boolean finishd;
    // Flag to indicate if the device was connected at some point
    boolean isSometimeConnected;
    // Flag to track connectivity status
    boolean connected;
    // Flag to indicate if operations have been stopped
    boolean stopped;
    // Application context
    Context myContext;
    // Utility class instance for showing alerts
    UtilityClass utilityClass;
    // Executor service for running tasks asynchronously
    ExecutorService executorService;
    // Future object to track the current task
    private Future<?> currentTask;
    // Callback interface for task completion
    private IonComplete myIonComplete;

    /**
     * Constructor to initialize MyFireBaseHelper with a context.
     *
     * @param context The context to use for various operations.
     */
    public MyFireBaseHelper(Context context) {
        utilityClass = new UtilityClass(context);
        try {
            stopped = false;
            isSometimeConnected = false;
            finishd = false;
            executorService = Executors.newSingleThreadExecutor();
            if (isFirstTime) {
                FirebaseDatabase.getInstance().setPersistenceEnabled(false);
                isFirstTime = false;
            }
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("Users");
            myContext = context;
            DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
            connectedRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    connected = Boolean.TRUE.equals(snapshot.getValue(Boolean.class));
                    if (!connected && isSometimeConnected && !stopped) {
                        stopped = true;
                        stopCurrentTask();
                    } else if (connected) {
                        isSometimeConnected = true;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle onCancelled if needed
                }
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
                database.goOffline();
                if (myIonComplete != null) {
                    myIonComplete.onCompleteBool(false);
                }
                utilityClass.showAlertConnection();
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
        this.myIonComplete = ionComplete;

        Handler handler = new Handler(myContext.getMainLooper());
        handler.postDelayed(() -> {
            if (connected) {
                performAddUser(user, ionComplete);
            } else {
                DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
                ValueEventListener connectivityListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean isConnected = Boolean.TRUE.equals(snapshot.getValue(Boolean.class));
                        if (isConnected) {
                            handler.removeCallbacksAndMessages(null);
                            performAddUser(user, ionComplete);
                            connectedRef.removeEventListener(this);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled if needed
                    }
                };
                connectedRef.addValueEventListener(connectivityListener);

                handler.postDelayed(() -> {
                    connectedRef.removeEventListener(connectivityListener);
                    if (!connected) {
                        if (myContext instanceof BaseActivity) {
                            ((BaseActivity) myContext).hideLoadingOverlay();
                        }
                        utilityClass.showAlertInternet();
                        ionComplete.onCompleteBool(false);
                    }
                }, 7000);
            }
        }, 0);
    }

    /**
     * Performs the actual addition of a user to Firebase.
     *
     * @param user       The user to add.
     * @param ionComplete The callback to be called with the result of the operation.
     */
    private void performAddUser(User user, IonComplete ionComplete) {
        currentTask = executorService.submit(() -> {
            try {
                database.goOnline();
                reference.child(user.getUsername()).setValue(user).addOnCompleteListener(task -> {
                    finishd = true;
                    ionComplete.onCompleteBool(task.isSuccessful());
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
     * @param user       The new user data.
     * @param ionComplete The callback to be called with the result of the operation.
     */
    public void update(User user, IonComplete ionComplete) {
        finishd = false;
        this.myIonComplete = ionComplete;

        Handler handler = new Handler(myContext.getMainLooper());
        handler.postDelayed(() -> {
            if (connected) {
                performUpdateUser(user, ionComplete);
            } else {
                DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
                ValueEventListener connectivityListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean isConnected = Boolean.TRUE.equals(snapshot.getValue(Boolean.class));
                        if (isConnected) {
                            handler.removeCallbacksAndMessages(null);
                            performUpdateUser(user, ionComplete);
                            connectedRef.removeEventListener(this);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled if needed
                    }
                };
                connectedRef.addValueEventListener(connectivityListener);

                handler.postDelayed(() -> {
                    connectedRef.removeEventListener(connectivityListener);
                    if (!connected) {
                        if (myContext instanceof BaseActivity) {
                            ((BaseActivity) myContext).hideLoadingOverlay();
                        }
                        utilityClass.showAlertInternet();
                        ionComplete.onCompleteBool(false);
                    }
                }, 7000);
            }
        }, 0);
    }

    /**
     * Performs the actual update of a user in Firebase.
     *
     * @param user       The new user data.
     * @param ionComplete The callback to be called with the result of the operation.
     */
    private void performUpdateUser(User user, IonComplete ionComplete) {
        currentTask = executorService.submit(() -> {
            try {
                database.goOnline();
                Map<String, Object> updates = new HashMap<>();
                updates.put("username", user.getUsername());
                updates.put("pass", user.getPass());
                reference.child(user.getUsername()).updateChildren(updates).addOnCompleteListener(task -> {
                    finishd = true;
                    ionComplete.onCompleteBool(task.isSuccessful());
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

        Handler handler = new Handler(myContext.getMainLooper());
        handler.postDelayed(() -> {
            if (connected) {
                performRemoveUser(user, ionComplete);
            } else {
                DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
                ValueEventListener connectivityListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean isConnected = Boolean.TRUE.equals(snapshot.getValue(Boolean.class));
                        if (isConnected) {
                            handler.removeCallbacksAndMessages(null);
                            performRemoveUser(user, ionComplete);
                            connectedRef.removeEventListener(this);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled if needed
                    }
                };
                connectedRef.addValueEventListener(connectivityListener);

                handler.postDelayed(() -> {
                    connectedRef.removeEventListener(connectivityListener);
                    if (!connected) {
                        if (myContext instanceof BaseActivity) {
                            ((BaseActivity) myContext).hideLoadingOverlay();
                        }
                        utilityClass.showAlertInternet();
                        ionComplete.onCompleteBool(false);
                    }
                }, 7000);
            }
        }, 0);
    }

    /**
     * Performs the actual removal of a user from Firebase.
     *
     * @param user       The user to remove.
     * @param ionComplete The callback to be called with the result of the operation.
     */
    private void performRemoveUser(User user, IonComplete ionComplete) {
        currentTask = executorService.submit(() -> {
            try {
                database.goOnline();
                reference.child(user.getUsername()).removeValue().addOnCompleteListener(task -> {
                    finishd = true;
                    ionComplete.onCompleteBool(task.isSuccessful());
                });
            } catch (Exception e) {
                utilityClass.showAlertExp();
                ionComplete.onCompleteBool(false);
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

        Handler handler = new Handler(myContext.getMainLooper());
        handler.postDelayed(() -> {
            if (connected) {
                performUserNameExists(username, checkUser);
            } else {
                DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
                ValueEventListener connectivityListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean isConnected = Boolean.TRUE.equals(snapshot.getValue(Boolean.class));
                        if (isConnected) {
                            handler.removeCallbacksAndMessages(null);
                            performUserNameExists(username, checkUser);
                            connectedRef.removeEventListener(this);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled if needed
                    }
                };
                connectedRef.addValueEventListener(connectivityListener);

                handler.postDelayed(() -> {
                    connectedRef.removeEventListener(connectivityListener);
                    if (!connected) {
                        if (myContext instanceof BaseActivity) {
                            ((BaseActivity) myContext).hideLoadingOverlay();
                        }
                        utilityClass.showAlertInternet();
                        checkUser.onCheckedUser(false);
                    }
                }, 7000);
            }
        }, 0);
    }

    /**
     * Performs the actual check for username existence in Firebase.
     *
     * @param username  The username to check.
     * @param checkUser The callback to be called with the result.
     */
    private void performUserNameExists(String username, checkUser checkUser) {
        currentTask = executorService.submit(() -> {
            try {
                database.goOnline();
                reference.child(username).get().addOnCompleteListener(task -> {
                    finishd = true;
                    if (task.isSuccessful() && task.getResult() != null) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String retrievedUsername = String.valueOf(dataSnapshot.child("username").getValue());
                        checkUser.onCheckedUser(username.equals(retrievedUsername));
                    } else {
                        checkUser.onCheckedUser(false);
                    }
                });
            } catch (Exception e) {
                utilityClass.showAlertExp();
                checkUser.onCheckedUser(false);
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

        // Use Handler for initial delay
        Handler handler = new Handler(myContext.getMainLooper());
        handler.postDelayed(() -> {
            if (connected) {
                performUserExists(user, checkUser);
            } else {
                // Delayed execution - add listener for connectivity changes
                DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
                ValueEventListener connectivityListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean isConnected = Boolean.TRUE.equals(snapshot.getValue(Boolean.class));
                        if (isConnected) {
                            // Cancel the delay handler if connected
                            handler.removeCallbacksAndMessages(null);
                            // Execute userExsits now
                            performUserExists(user, checkUser);
                            // Remove this listener after executing
                            connectedRef.removeEventListener(this);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled if needed
                    }
                };
                connectedRef.addValueEventListener(connectivityListener);

                // If connectivity doesn't change within 7 seconds, proceed with alert
                handler.postDelayed(() -> {
                    connectedRef.removeEventListener(connectivityListener);
                    if (!connected) {
                        if (myContext instanceof BaseActivity) {
                            ((BaseActivity) myContext).hideLoadingOverlay();
                        }
                        utilityClass.showAlertInternet();
                    }
                }, 7000); // 7000 milliseconds = 7 seconds delay
            }
        }, 0); // Start the initial delay immediately
    }

    /**
     * Performs the actual check for user existence in Firebase.
     *
     * @param user      The user to check.
     * @param checkUser The callback to be called with the result.
     */
    private void performUserExists(User user, checkUser checkUser) {
        currentTask = executorService.submit(() -> {
            database.goOnline();
            reference.child(user.getUsername()).get().addOnCompleteListener(task -> {
                finishd = true;
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot != null && dataSnapshot.child("username").exists()) {
                        // User found in database, check password
                        String storedPassword = String.valueOf(dataSnapshot.child("pass").getValue());
                        checkUser.onCheckedUser(storedPassword.equals(user.getPass()));
                    } else {
                        checkUser.onCheckedUser(false);
                    }
                }
            });
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

        // Use Handler for initial delay
        Handler handler = new Handler(myContext.getMainLooper());
        handler.postDelayed(() -> {
            if (connected) {
                //getUserByName immediately if connected
                performGetUserByName(username, user);
            } else {
                // Delayed execution - add listener for connectivity changes
                DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
                ValueEventListener connectivityListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean isConnected = Boolean.TRUE.equals(snapshot.getValue(Boolean.class));
                        if (isConnected) {
                            // Cancel the delay handler if connected
                            handler.removeCallbacksAndMessages(null);
                            // Execute getUserByName now
                            executorService.submit(() -> performGetUserByName(username, user));
                            // Remove this listener after executing
                            connectedRef.removeEventListener(this);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled if needed
                    }
                };
                connectedRef.addValueEventListener(connectivityListener);

                // If connectivity doesn't change within 7 seconds, proceed with alert
                handler.postDelayed(() -> {
                    connectedRef.removeEventListener(connectivityListener);
                    if (!connected) {
                        if (myContext instanceof BaseActivity) {
                            ((BaseActivity) myContext).hideLoadingOverlay();
                        }
                        utilityClass.showAlertInternet();
                    }
                }, 10000); // 10000 milliseconds = 10 seconds delay
            }
        }, 0); // Start the initial delay immediately
    }

    /**
     * Performs the actual retrieval of a user by username from Firebase.
     *
     * @param username The username of the user to retrieve.
     * @param user     The callback to be called with the retrieved user.
     */
    private void performGetUserByName(String username, IonComplete.IonCompleteUser user) {
        currentTask = executorService.submit(() -> {
            try {
                database.goOnline();
                reference.child(username).get().addOnCompleteListener(task -> {
                    finishd = true;
                    if (task.isSuccessful() && task.getResult() != null) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String retrievedUsername = String.valueOf(dataSnapshot.child("username").getValue());
                        if (username.equals(retrievedUsername)) {
                            String pass = String.valueOf(dataSnapshot.child("pass").getValue());
                            user.onCompleteUser(new User(retrievedUsername, pass));
                        }
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
        executorService.shutdownNow();
    }
}
