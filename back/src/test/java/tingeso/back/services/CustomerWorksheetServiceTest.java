package tingeso.back.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tingeso.back.entities.CustomerWorksheetEntity;
import tingeso.back.repositories.CustomerWorksheetRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomerWorksheetServiceTest {

    @InjectMocks
    private CustomerWorksheetService customerWorksheetService;

    @Mock
    private CustomerWorksheetRepository customerWorksheetRepository;

    private CustomerWorksheetEntity customerWorksheet;
    private String rut = "12345678-9";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerWorksheet = new CustomerWorksheetEntity();
        customerWorksheet.setRut(rut);
    }

    @Test
    void testGetAll() {
        // Arrange
        when(customerWorksheetRepository.findAll()).thenReturn(List.of(customerWorksheet));

        // Act
        List<CustomerWorksheetEntity> result = customerWorksheetService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(rut, result.get(0).getRut());
    }

    @Test
    void testFindByRut_Found() {
        // Arrange
        when(customerWorksheetRepository.findByRut(rut)).thenReturn(customerWorksheet);

        // Act
        CustomerWorksheetEntity result = customerWorksheetService.findByRut(rut);

        // Assert
        assertNotNull(result);
        assertEquals(rut, result.getRut());
    }

    @Test
    void testFindByRut_NotFound() {
        // Arrange
        when(customerWorksheetRepository.findByRut(rut)).thenReturn(null);

        // Act
        CustomerWorksheetEntity result = customerWorksheetService.findByRut(rut);

        // Assert
        assertNull(result);
    }
}
