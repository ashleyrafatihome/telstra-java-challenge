package au.org.ashley.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import au.org.ashley.data.model.Car;
import au.org.ashley.data.model.CarModelInfo;
import au.org.ashley.data.model.CarProperty;
import au.org.ashley.data.store.CarModelInfoStore;
import au.org.ashley.data.store.CarModelStoreException;
import au.org.ashley.data.store.CarStore;
import au.org.ashley.data.store.CarStoreException;
import au.org.ashley.reader.CarReaderUtil;

/**
 * Unit tests included here are only intended to demonstrate the use of the library, since the spec did not require a
 * UI. They are not a comprehensive set of tests, which the assignment did not call for.
 */
class TestCarStore {
  private static final String PATH_MODEL_DATA = "src/test/input/car_specs.csv";
  private static final String PATH_REGISTRATION_DATA = "src/test/input/cars_regos.csv";

  private static final int MODEL_INDEX_MID = 2538;
  private static final int MODEL_INDEX_LAST = 5076;

  private static final int ID_FIRST = 1;
  private static final int ID_MID = 2538;
  private static final int ID_LAST = 5076;

  private static final int CAR_ID_FIRST = 1;
  private static final int CAR_ID_MID = 50;
  private static final int CAR_ID_LAST = 100;

  private static final short YEAR_FIRST = 2011;
  private static final short YEAR_MID = 2010;
  private static final short YEAR_LAST = 2012;

  private static final short CAR_YEAR_FIRST = 2011;
  private static final short CAR_YEAR_MID = 2012;
  private static final short CAR_YEAR_LAST = 2011;

  private static final String MAKE_FIRST = "Chevrolet";
  private static final String MAKE_MID = "Acura";
  private static final String MAKE_LAST = "GMC";

  private static final String CAR_MAKE_FIRST = "Chevrolet";
  private static final String CAR_MAKE_MID = "Honda";
  private static final String CAR_MAKE_LAST = "Kia";

  private static final String MODEL_FIRST = "Silverado 1500 Hybrid";
  private static final String MODEL_MID = "RDX";
  private static final String MODEL_LAST = "Savana";

  private static final String CAR_MODEL_FIRST = "Silverado 1500 Hybrid";
  private static final String CAR_MODEL_MID = "Civic Sedan";
  private static final String CAR_MODEL_LAST = "Forte";

  private static final String CAR_REGISTRATION_FIRST = "ABC123";
  private static final String CAR_REGISTRATION_MID = "LMN123";
  private static final String CAR_REGISTRATION_LAST = "LMN173";

  /**
   * Demonstrates retrieving car model information from the car model store.
   *
   * @throws IOException thrown if an error occurs.
   * @throws CarModelStoreException thrown if an error occurs.
   */
  @Test
  void testModelStore() throws IOException, CarModelStoreException {
    System.out.println("\nTEST METHOD: testModelStore()");
    final CarModelInfoStore modelStore = createModelStore();

    System.out.println("\nMODEL 1: ");
    final CarModelInfo modelInfoFirst = modelStore.get(1);
    printModelInfo(modelInfoFirst);
    testModelInfo(modelInfoFirst, ID_FIRST, YEAR_FIRST, MAKE_FIRST, MODEL_FIRST);

    System.out.println("\nMODEL 2: ");
    final CarModelInfo modelInfoMid = modelStore.get(MODEL_INDEX_MID);
    printModelInfo(modelInfoMid);
    testModelInfo(modelInfoMid, ID_MID, YEAR_MID, MAKE_MID, MODEL_MID);

    System.out.println("\nMODEL 3: ");
    final CarModelInfo modelInfoLast = modelStore.get(MODEL_INDEX_LAST);
    printModelInfo(modelInfoLast);
    testModelInfo(modelInfoLast, ID_LAST, YEAR_LAST, MAKE_LAST, MODEL_LAST);
  }

  /**
   * Tests retrieving individual car information by registration.
   *
   * @throws IOException thrown if an error occurs.
   * @throws CarStoreException thrown if an error occurs.
   * @throws CarModelStoreException thrown if an error occurs.
   */
  @Test
  void testCarStoreByReg() throws IOException, CarStoreException, CarModelStoreException {
    System.out.println("\nTEST METHOD: testCarStoreByReg()");
    final CarStore carStore = createCarStore(createModelStore());

    System.out.println("\nCAR 1: " + CAR_REGISTRATION_FIRST);
    final CarModelInfo modelInfoFirst = carStore.get(CAR_REGISTRATION_FIRST).getModelInfo();
    printModelInfo(modelInfoFirst);
    testModelInfo(modelInfoFirst, CAR_ID_FIRST, CAR_YEAR_FIRST, CAR_MAKE_FIRST, CAR_MODEL_FIRST);

    System.out.println("\nCAR 2: " + CAR_REGISTRATION_MID);
    final CarModelInfo modelInfoMid = carStore.get(CAR_REGISTRATION_MID).getModelInfo();
    printModelInfo(modelInfoMid);
    testModelInfo(modelInfoMid, CAR_ID_MID, CAR_YEAR_MID, CAR_MAKE_MID, CAR_MODEL_MID);

    System.out.println("\nCAR 3: " + CAR_ID_LAST);
    final CarModelInfo modelInfoLast = carStore.get(CAR_REGISTRATION_LAST).getModelInfo();
    printModelInfo(modelInfoLast);
    testModelInfo(modelInfoLast, CAR_ID_LAST, CAR_YEAR_LAST, CAR_MAKE_LAST, CAR_MODEL_LAST);
  }

  /**
   * Test retrieving individual car information by model details.
   *
   * @throws IOException thrown if an error occurs.
   * @throws CarModelStoreException thrown if an error occurs.
   * @throws NumberFormatException thrown if an error occurs.
   * @throws CarStoreException thrown if an error occurs.
   */
  @Test
  void testCarStoreByModelInfo() throws IOException, CarModelStoreException, NumberFormatException, CarStoreException {
    System.out.println("\nTEST METHOD: testCarStoreByModelInfo()");
    final Car car = createCarStore(createModelStore()).get(CAR_MAKE_FIRST, CAR_MODEL_FIRST, CAR_YEAR_FIRST).iterator()
        .next();

    System.out.println("\nCAR PARAMETERS:");
    System.out.println("MAKE: " + CAR_MAKE_FIRST);
    System.out.println("MODEL: " + CAR_MODEL_FIRST);
    System.out.println("YEAR: " + CAR_YEAR_FIRST);
    System.out.println("REGISTRATION FOUND: " + car.getRegistration());

    Assert.assertEquals(CAR_REGISTRATION_FIRST, car.getRegistration());
  }

  private static CarModelInfoStore createModelStore() throws IOException, CarModelStoreException {
    final BufferedReader reader = new BufferedReader(new FileReader(PATH_MODEL_DATA));

    return CarReaderUtil.extractCarModels(reader);
  }

  private static CarStore createCarStore(final CarModelInfoStore pModelStore)
      throws IOException, NumberFormatException, CarStoreException {
    final BufferedReader reader = new BufferedReader(new FileReader(PATH_REGISTRATION_DATA));

    return CarReaderUtil.extractCarStore(pModelStore, reader);
  }

  private static void testModelInfo(final CarModelInfo modelInfo, final int pID, final short pYear, final String pMake,
      final String pModel) {
    Assert.assertEquals(pID, modelInfo.getID());
    Assert.assertEquals(pYear, modelInfo.getYear());
    Assert.assertEquals(pMake, modelInfo.getMake());
    Assert.assertEquals(pModel, modelInfo.getModel());
  }

  private static void printModelInfo(final CarModelInfo pModelInfo) {
    for (final CarProperty property : pModelInfo) {
      System.out.println(property.getName() + ": " + property.getValue());
    }
  }
}
