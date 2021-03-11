package dev.randomguys.random_library.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Process;
import android.util.Log;
import androidx.annotation.NonNull;
import dev.randomguys.random_library.lifecycle.RandomActivity;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Utility class for all thing network related.
 *
 * @author Ian White
 * @see NoInternetException
 */
public final class NetworkManager {

  /**
   * Used to identify the class during debug
   */
  private static final String TAG = NetworkManager.class.getSimpleName();

  /**
   * {@link ArrayList<NetworkListener>} holds all instances of {@link OnNetworkChangeListener}s for
   * this class.
   */
  private static final ArrayList<NetworkListener> sNetworkListeners = new ArrayList<>();

  /**
   * Interface is a callback for monitoring network changes.
   */
  public interface OnNetworkChangeListener {

    /**
     * Called when there is a network change.
     *
     * @param hasInternet true if the device has an internet connection, false otherwise.
     */
    void onNetworkStateChange(boolean hasInternet);
  }

  /**
   * Private constructor to prevent initializing this class.
   */
  private NetworkManager() {
  }

  /**
   * Uses {@link ConnectivityManager} to check if user has an internet connection or is establishing
   * a connection. Requires {@link android.Manifest.permission#ACCESS_NETWORK_STATE}.
   *
   * @param context required to access system services
   *
   * @return true if user is connected to the internet.
   */
  public static boolean hasInternetConnection(@NonNull Context context) {
    ConnectivityManager manager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = manager != null ? manager.getActiveNetworkInfo() : null;
    return networkInfo != null && networkInfo.isConnected();
  }

  /**
   * Adds a listener to {@link #sNetworkListeners} that will be called immediately with the current
   * network state, and in the future on any network changes.
   *
   * @param activity                calling activity.
   * @param onNetworkChangeListener the callback called when their is a change in internet state.
   */
  public static void addNetworkListener(@NonNull RandomActivity activity,
      @NonNull OnNetworkChangeListener onNetworkChangeListener) {

    //Immediately call callback with current network state
    onNetworkChangeListener.onNetworkStateChange(hasInternetConnection(activity));

    //Add new listener to list of network listeners
    sNetworkListeners.add(new NetworkListener(activity, onNetworkChangeListener));

    //Log the listener that was added
    Log.i(TAG,
        "Network State Listener Added: " + sNetworkListeners.get(sNetworkListeners.size() - 1)
            .toString());

    //Listen for network changes
    listenForNetworkChanges(activity);
  }

  /**
   * Thank you! https://stackoverflow.com/a/223929/6398145. Removes all {@link NetworkListener}s
   * associated with the given {@link RandomActivity}.
   *
   * @param activity the activity reference to look for in {@link #sNetworkListeners}.
   */
  public static void removeNetworkListener(@NonNull RandomActivity activity) {

    //Iterate over every item in network listeners
    for (Iterator<NetworkListener> iterator = sNetworkListeners.iterator(); iterator.hasNext(); ) {

      //Get the next network listener
      NetworkListener listener = iterator.next();

      //Check if this is the right listener
      if (listener.getACTIVITY().equals(activity)) {

        //Remove the listener
        iterator.remove();

        //Log the name of the listener that was removed
        Log.i(TAG, "Removed Network State Listener: " + listener.toString());
      }
    }
  }

  /**
   * Starts a background {@link Thread} to listen for changes in network state. On change calls all
   * listeners in {@link #sNetworkListeners}.
   *
   * @param activity the calling activity.
   */
  private static void listenForNetworkChanges(@NonNull RandomActivity activity) {

    //If network listeners is greater than 0, then this functions has already been called, and the
    //thread is already listening
    if (sNetworkListeners.size() > 1) {
      return;
    }

    // TODO: Ian White 4/21/2020 add a wait so it runs every 500 ms or so

    //Create a new thread to monitor network connection
    new Thread(() -> {

      //Log thread creation
      Log.v(TAG, "listenForNetworkChanges: Creating Network State Listener Thread...");

      //Set thread priority as background
      Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

      //Log thread priority is set to background
      Log.v(TAG,
          "listenForNetworkChanges: Setting Network State Listener Thread priority to BACKGROUND");

      //Check for initial internet connection
      boolean hasInternet = hasInternetConnection(activity);

      //Log thread status
      Log.i(TAG, "listenForNetworkChanges: Network State Listener Thread running...");

      //Run forever...and ever...and ever...and EVER!!!
      while (true) {

        //If the current internet connection is not the same as the previous internet connection
        //report the current internet connection
        if (hasInternetConnection(activity) != hasInternet) {
          hasInternet = hasInternetConnection(activity);
          final boolean temp = hasInternet;

          //Call every listener
          for (NetworkListener listener : sNetworkListeners) {
            listener.getACTIVITY()
                .runOnUiThread(
                    () -> listener.getON_NETWORK_CHANGE_LISTENER().onNetworkStateChange(temp));
          }

        }
      }
    }).start();//Start the thread
  }

  /**
   * Throws an error if the user does not have an active internet connection.
   *
   * @param context the 'environment' the app is running in.
   *
   * @throws NoInternetException if the user does not have an active internet connection.
   */
  public static void requireInternetConnection(@NonNull Context context) {
    if (!hasInternetConnection(context)) {
      throw new NoInternetException("This action requires an active Internet Connection");
    }
  }

  /**
   * Holds an instance of {@link OnNetworkChangeListener} and {@link RandomActivity}
   */
  static class NetworkListener {

    /**
     * {@link OnNetworkChangeListener}
     */
    private final OnNetworkChangeListener ON_NETWORK_CHANGE_LISTENER;

    /**
     * {@link RandomActivity} from which is calling {@link #ON_NETWORK_CHANGE_LISTENER}
     */
    private final RandomActivity ACTIVITY;

    /**
     * Default constructor takes the {@link RandomActivity} and the {@link OnNetworkChangeListener}
     *
     * @param activity                the calling {@link RandomActivity}
     * @param onNetworkChangeListener {@link OnNetworkChangeListener}
     */
    NetworkListener(@NonNull RandomActivity activity,
        @NonNull OnNetworkChangeListener onNetworkChangeListener) {
      ON_NETWORK_CHANGE_LISTENER = onNetworkChangeListener;
      ACTIVITY = activity;
    }

    /**
     * @return {@link #ON_NETWORK_CHANGE_LISTENER}
     */
    @NonNull
    OnNetworkChangeListener getON_NETWORK_CHANGE_LISTENER() {
      return ON_NETWORK_CHANGE_LISTENER;
    }

    /**
     * @return {@link #ACTIVITY}
     */
    @NonNull
    RandomActivity getACTIVITY() {
      return ACTIVITY;
    }

    @NonNull
    @Override
    public String toString() {
      return "NetworkListener {" +
          "Activity=" + ACTIVITY.getClass().getSimpleName() +
          '}';
    }
  }
}