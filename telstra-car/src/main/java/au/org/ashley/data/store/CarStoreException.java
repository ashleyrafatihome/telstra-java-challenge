package au.org.ashley.data.store;

/**
 * An exception representing an error in the car store.
 */
public class CarStoreException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   *
   * @param pMessage the detail message.
   */
  CarStoreException(final String pMessage) {
    super(pMessage);
  }
}
