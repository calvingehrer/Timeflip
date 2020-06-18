/*package at.ac.uibk.rest.tests;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import at.ac.uibk.rest.TimeFlipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import tinyb.BluetoothDevice;
import tinyb.BluetoothGattCharacteristic;
import tinyb.BluetoothGattService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor({"tinyb.BluetoothManager", "tinyb.BluetoothObject"})
class TimeFlipServiceTest {

    private static final String NAME = "TimeFlip";
    private static final boolean CONNECTED = true;
    private static final String DEVICE_MAC = "0C:61:F2:A5:CA:21";
    private static final String SERVICE_1_UUID = "f000aa11-0001-0000-0000-000000000000";
    private static final String SERVICE_2_UUID = "0000aa11-0001-0000-0000-000000000000";
    private static final String CHARACTERISTIC_1_UUID = "f000aa11-0002-0000-0000-000000000000";
    private static final String CHARACTERISTIC_2_UUID = "0000aa12-0002-0000-0000-000000000000";


    private BluetoothDevice bluetoothDevice = mock(BluetoothDevice.class);
    private BluetoothGattService bluetoothGattService = mock(BluetoothGattService.class);

    @BeforeEach
    public void setUp() {
        when(bluetoothDevice.getAddress()).thenReturn(DEVICE_MAC);
        when(bluetoothDevice.getName()).thenReturn(NAME);
        when(bluetoothDevice.getConnected()).thenReturn(CONNECTED);

        List<BluetoothGattService> services = new ArrayList<>();
        BluetoothGattService service1 = mock(BluetoothGattService.class);
        when(service1.getUUID()).thenReturn(SERVICE_1_UUID);
        when(service1.getDevice()).thenReturn(bluetoothDevice);
        services.add(service1);
        BluetoothGattService service2 = mock(BluetoothGattService.class);
        when(service1.getUUID()).thenReturn(SERVICE_2_UUID);
        when(service1.getDevice()).thenReturn(bluetoothDevice);
        services.add(service2);
        when(bluetoothDevice.getServices()).thenReturn(services);

        List<BluetoothGattCharacteristic> characteristics = new ArrayList<>();
        BluetoothGattCharacteristic characteristic1 = mock(BluetoothGattCharacteristic.class);
        when(characteristic1.getUUID()).thenReturn(CHARACTERISTIC_1_UUID);
        when(characteristic1.getService()).thenReturn(bluetoothGattService);
        characteristics.add(characteristic1);
        BluetoothGattCharacteristic characteristic2 = mock(BluetoothGattCharacteristic.class);
        when(characteristic2.getUUID()).thenReturn(CHARACTERISTIC_2_UUID);
        when(characteristic2.getService()).thenReturn(bluetoothGattService);
        characteristics.add(characteristic2);
        when(bluetoothGattService.getCharacteristics()).thenReturn(characteristics);
    }

    @org.junit.jupiter.api.Test
    void testGetService() throws InterruptedException {
        List<BluetoothGattService> services = bluetoothDevice.getServices();
        assertEquals(2, services.size());

        BluetoothGattService service1 = TimeFlipService.getService(bluetoothDevice, CHARACTERISTIC_1_UUID);
        assert service1 != null;
        assertEquals(SERVICE_1_UUID, service1.getUUID());
    }

    @org.junit.jupiter.api.Test
    void testGetCharacteristic() {
        List<BluetoothGattCharacteristic> characteristics = bluetoothGattService.getCharacteristics();
        assertEquals(2, characteristics.size());

        BluetoothGattCharacteristic characteristic1 = TimeFlipService.getCharacteristic(bluetoothGattService, CHARACTERISTIC_1_UUID);
        assert characteristic1 != null;
        assertEquals(CHARACTERISTIC_1_UUID, characteristic1.getUUID());
    }
}
 */
