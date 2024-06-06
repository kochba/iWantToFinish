package com.example.tkfinalproject.Utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.telephony.CellSignalStrength;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * ConnectivityListener is a LiveData class that monitors the network connectivity status.
 * It uses a ConnectivityManager and TelephonyManager to detect changes in network connectivity
 * and signal strength.
 */
public class ConnectivityListener extends LiveData<Boolean> {

    private final ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;
    private final TelephonyManager telephonyManager;

    /**
     * Constructor that initializes the ConnectivityListener with the application context.
     *
     * @param context The application context.
     */
    public ConnectivityListener(Context context) {
        context.getApplicationContext();
        this.telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * Called when the ConnectivityListener becomes active.
     * Registers the default network callback to monitor network changes.
     */
    @Override
    protected void onActive() {
        super.onActive();
        updateConnection();
        connectivityManager.registerDefaultNetworkCallback(getNetworkCallback());
    }

    /**
     * Called when the ConnectivityListener becomes inactive.
     * Unregisters the network callback to stop monitoring network changes.
     */
    @Override
    protected void onInactive() {
        super.onInactive();
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    /**
     * Returns the network callback, creating it if necessary.
     *
     * @return The network callback.
     */
    private ConnectivityManager.NetworkCallback getNetworkCallback() {
        if (networkCallback == null) {
            networkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(@NonNull Network network) {
                    postValue(true);
                }

                @Override
                public void onLost(@NonNull Network network) {
                    postValue(false);
                }
            };
        }
        return networkCallback;
    }

    /**
     * BroadcastReceiver to update the connection status when a connectivity change is detected.
     */
    private final BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateConnection();
        }
    };

    /**
     * Stops observing the network changes by unregistering the network callback.
     */
    public void stopObserving() {
        if (networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }

    /**
     * Updates the connection status and posts the value to LiveData.
     */
    private void updateConnection() {
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        boolean isConnected = capabilities != null &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        postValue(isConnected);
    }

    /**
     * Checks if the device is currently connected to the internet.
     *
     * @return True if connected, false otherwise.
     */
    public boolean isConnected() {
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        return capabilities != null &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }

    /**
     * Checks if the device has a valid signal strength.
     *
     * @return True if there is a valid signal, false otherwise.
     */
    private boolean hasSignal() {
        SignalStrength signalStrength = telephonyManager.getSignalStrength();
        if (signalStrength != null) {
            List<CellSignalStrength> cellSignalStrengths;
            cellSignalStrengths = signalStrength.getCellSignalStrengths();
            if (cellSignalStrengths != null) {
                for (CellSignalStrength cellSignalStrength : cellSignalStrengths) {
                    if (cellSignalStrength.getLevel() > 0) {
                        return true;
                    }
                }
            } else {
                Log.d("SignalCheck", "cellSignalStrengths is null");
            }
        }
        return false;
    }
}
