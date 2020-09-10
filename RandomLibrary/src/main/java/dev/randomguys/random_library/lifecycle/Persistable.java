package dev.randomguys.random_library.lifecycle;

/**
 * 9/5/2020
 * <p>
 * Interface connects {@link RandomActivity} and/or {@link * DormamuFragment} to the Android
 * Lifecycle. Allows for subclasses to save data before the activity * is destroyed, and to retrieve
 * saved data after it is restarted.
 *
 * @author Ian White
 */
interface Persistable {

  /**
   * Called when activity is started, to retrieve data saved in the {@link #save()} method.
   */
  void update();

  /**
   * Called when activity is destroyed to save data using {@link androidx.lifecycle.ViewModel}.
   */
  void save();

}
