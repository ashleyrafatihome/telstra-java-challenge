package au.org.ashley.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import au.org.ashley.data.model.Car;
import au.org.ashley.data.model.CarModelInfo;
import au.org.ashley.data.store.CarModelInfoStore;
import au.org.ashley.data.store.CarStore;

/**
 * Contains utility methods for reading car data.
 */
public class CarReaderUtil {
  private static final int INDEX_REGISTRATION = 0;
  private static final int INDEX_MODEL_NUM = 1;

  /**
   * Extracts car model information.
   * 
   * @param pReader the reader containing the information.
   * @return the map of car model numbers to car models.
   * @throws IOException if an error occurred.
   */
  public static CarModelInfoStore extractCarModels(BufferedReader pReader) throws IOException {
    int i = 0;
    Map<String, Integer> mapPropertyToIndex = new HashMap<>();

    for (String property : pReader.readLine().split(",")) {
      mapPropertyToIndex.put(property, i++);
    }

    CarModelInfoStore modelStore = new CarModelInfoStore();
    String line;

    while ((line = pReader.readLine()) != null) {
      CarModelInfo model = new CarModelInfo(mapPropertyToIndex, line.split(","));
      modelStore.add(model);
    }

    return modelStore;
  }

  /**
   * Extracts individual car information.
   * 
   * @param pModelStore the store of car model information.
   * @param pReader the reader containing the information.
   * @return the store of cars.
   * @throws IOException
   */
  public static CarStore extractCarStore(CarModelInfoStore pModelStore, BufferedReader pReader) throws IOException {
    pReader.readLine();

    CarStore store = new CarStore();
    String line;

    while ((line = pReader.readLine()) != null) {
      String[] params = line.split(",");
      int modelID = Integer.parseInt(params[INDEX_MODEL_NUM]);
      Car car = new Car(params[INDEX_REGISTRATION], pModelStore.get(modelID));

      store.add(car);
    }

    return store;
  }
}
