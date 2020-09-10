package dev.randomguys.random_library.network;

/**
 * 9/5/2020
 * <p>
 * {@link Exception} thrown when app attempts to access the internet but there is no internet
 * connection.
 *
 * @author Ian White
 * @see NetworkManager
 */
public final class NoInternetException extends RuntimeException {

  /**
   * Standard constructor passes arguments to super class {@link Exception}.
   *
   * @param s the error to print out.
   */
  public NoInternetException(String s) {
    super(s);
  }

}
