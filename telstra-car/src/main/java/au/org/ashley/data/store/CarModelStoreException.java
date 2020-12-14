package au.org.ashley.data.store;

/**
 * An exception representing an error in the car model store.
 */
public class CarModelStoreException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   *
   * @param pMessage the detail message.
   */
  CarModelStoreException(final String pMessage) {
    super(pMessage);
  }
}
