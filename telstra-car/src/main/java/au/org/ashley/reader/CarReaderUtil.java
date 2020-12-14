package au.org.ashley.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import au.org.ashley.data.model.CarModelInfo;
import au.org.ashley.data.store.CarModelInfoStore;
import au.org.ashley.data.store.CarModelStoreException;
import au.org.ashley.data.store.CarStore;
import au.org.ashley.data.store.CarStoreException;

/**
 * Contains utility methods for reading car data.
 */
public class CarReaderUtil {
  private static final int INDEX_REGISTRATION = 0;
  private static final int INDEX_MODEL_NUM = 1;

  /**
   * Extracts car model information into a store.
   *
   * @param pReader the reader containing the information.
   * @return the map of car model numbers to car models.
   * @throws IOException if an error occurred.
   * @throws CarModelStoreException thrown if an error occurs.
   */
  public static CarModelInfoStore extractCarModels(final BufferedReader pReader)
      throws IOException, CarModelStoreException {
    int i = 0;
    final Map<String, Integer> mapPropertyToIndex = new HashMap<>();

    for (final String property : pReader.readLine().split(",")) {
      mapPropertyToIndex.put(property, i++);
    }

    final CarModelInfoStore modelStore = new CarModelInfoStore();
    String line;

    while ((line = pReader.readLine()) != null) {
      final CarModelInfo model = new CarModelInfo(mapPropertyToIndex, line.split(","));
      modelStore.add(model);
    }

    return modelStore;
  }

  /**
   * Extracts individual car information into a store.
   *
   * @param pModelStore the store of car model information.
   * @param pReader the reader containing the information.
   * @return the store of cars.
   * @throws IOException thrown if an error occurs.
   * @throws CarStoreException thrown if an error occurs.
   */
  public static CarStore extractCarStore(final CarModelInfoStore pModelStore, final BufferedReader pReader)
      throws IOException, NumberFormatException, CarStoreException {
    pReader.readLine();

    final CarStore store = new CarStore(pModelStore);
    String line;

    while ((line = pReader.readLine()) != null) {
      final String[] params = line.split(",");
      store.add(params[INDEX_REGISTRATION], Integer.parseInt(params[INDEX_MODEL_NUM]));
    }

    return store;
  }
}
