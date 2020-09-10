package dev.randomguys.random_library.keyboard;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Created 9/6/2020
 * <p>
 * Collection of helper methods for using the keyboard.
 *
 * @author Ian White
 */
public final class KeyboardUtilities {

  /**
   * Interface is a callback for when the enter key is pressed.
   */
  public interface OnEnterKeyPressed {

    /**
     * Called when the user presses the enter button while focused on a specific {@link
     * TextInputEditText}.
     */
    void onEnterKeyPressed();
  }

  /**
   * Private constructor to prevent instantiating this class.
   */
  private KeyboardUtilities() {
    //Intentionally left blank by Ian White on 4/18/2020
  }

  /**
   * Method registers a listener for the user pressing the enter key while editing a specific {@link
   * TextInputEditText}.
   *
   * @param editText          The edit text to watch for.
   * @param onEnterKeyPressed Callback for when the user presses the enter key.
   */
  public static void setOnEnterKeyPressed(@NonNull final TextInputEditText editText,
      @NonNull final OnEnterKeyPressed onEnterKeyPressed) {

    //Sets a listener for that edit text that listens for keyboard presses
    editText.setOnKeyListener((v, keyCode, event) -> {

      //If the key event is a key being pressed down
      if (event.getAction() == KeyEvent.ACTION_DOWN) {

        //If the keycode is enter or dpad center(accessibility)
        switch (keyCode) {
          case KeyEvent.KEYCODE_DPAD_CENTER:
          case KeyEvent.KEYCODE_ENTER:
            //Call callback
            onEnterKeyPressed.onEnterKeyPressed();
            return true;
          default:
            break;
        }
      }
      return false;
    });
  }

  /**
   * Adapted from https://stackoverflow.com/a/17789187/6398145
   *
   * Hides the soft keyboard.
   *
   * @param activity the calling activity
   */
  public static void hideKeyboard(@NonNull final Activity activity) {

    //Get input manager
    InputMethodManager manager = ((InputMethodManager) activity
        .getSystemService(Activity.INPUT_METHOD_SERVICE));

    //Get current focus
    View v = activity.getCurrentFocus();

    //If no current focus create a new view in the activity
    if (v == null) {
      v = new View(activity);
    }

    //If the manager is not null hide the keyboard
    if (manager != null) {
      manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    //Clear the focus
    v.clearFocus();
  }

}
