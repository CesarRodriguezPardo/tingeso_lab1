package tingeso.back.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tingeso.back.entities.CustomerWorksheetEntity;
import tingeso.back.entities.DocumentEntity;
import tingeso.back.entities.SavingAccountEntity;
import tingeso.back.entities.UserEntity;
import tingeso.back.repositories.CustomerWorksheetRepository;
import tingeso.back.repositories.DocumentRepository;
import tingeso.back.repositories.SavingAccountRepository;
import tingeso.back.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomerWorksheetRepository worksheetRepository;

    @Mock
    private SavingAccountRepository savingAccountRepository;

    @Mock
    private DocumentRepository documentRepository;

    private UserEntity user;
    private CustomerWorksheetEntity worksheet;
    private SavingAccountEntity savingAccount;
    private DocumentEntity document;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity();
        user.setId(1L);
        user.setRut("12.345.678-9");
        user.setEmail("test@example.com");
        user.setPhone("123456789");
        user.setVerified(false);

        worksheet = new CustomerWorksheetEntity();
        worksheet.setRut("12.345.678-9");

        savingAccount = new SavingAccountEntity();
        savingAccount.setRut("12.345.678-9");

        document = new DocumentEntity();
        document.setUserId("1");
    }

    @Test
    void whenGetAll_thenReturnListOfUsers() {
        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setRut("98.765.432-1");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user, user2));

        List<UserEntity> result = userService.getAll();

        assertThat(result).hasSize(2).contains(user, user2);
    }

    @Test
    void whenFindById_thenReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserEntity result = userService.findById(1L);

        assertThat(result).isEqualTo(user);
    }

    @Test
    void whenFindByRut_thenReturnUser() {
        when(userRepository.findByRut("12.345.678-9")).thenReturn(user);

        UserEntity result = userService.findByRut("12.345.678-9");

        assertThat(result).isEqualTo(user);
    }

    @Test
    void whenFindByEmail_thenReturnUser() {
        when(userRepository.findByPhone("test@example.com")).thenReturn(user);

        UserEntity result = userService.findByEmail("test@example.com");

        assertThat(result).isEqualTo(user);
    }

    @Test
    void whenFindByPhone_thenReturnUser() {
        when(userRepository.findByPhone("123456789")).thenReturn(user);

        UserEntity result = userService.findByPhone("123456789");

        assertThat(result).isEqualTo(user);
    }

    @Test
    void whenGetIdByRut_thenReturnUserId() {
        when(userRepository.findByRut("12.345.678-9")).thenReturn(user);

        String result = userService.getIdByRut("12.345.678-9");

        assertThat(result).isEqualTo("1");
    }

    @Test
    void whenSaveApplicationWithExistingRutEmailOrPhone_thenReturnFalse() {
        when(userRepository.findByRut(user.getRut())).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.findByPhone(user.getPhone())).thenReturn(null);

        Boolean result = userService.saveApplication(user);

        assertThat(result).isFalse();
    }

    @Test
    void whenSaveApplication_thenReturnTrue() {
        when(userRepository.findByRut(user.getRut())).thenReturn(null);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.findByPhone(user.getPhone())).thenReturn(null);

        Boolean result = userService.saveApplication(user);

        verify(userRepository).save(user);
        verify(worksheetRepository).save(any(CustomerWorksheetEntity.class));
        verify(savingAccountRepository).save(any(SavingAccountEntity.class));
        verify(documentRepository).save(any(DocumentEntity.class));
        assertThat(result).isTrue();
    }



    @Test
    void whenLoginWithCorrectCredentials_thenReturnUser() {
        when(userRepository.findByEmailAndPassword("test@example.com", "password")).thenReturn(user);

        UserEntity result = userService.login("test@example.com", "password");

        assertThat(result).isEqualTo(user);
    }

    @Test
    void whenFindByVerified_thenReturnListOfVerifiedUsers() {
        when(userRepository.findByVerified(true)).thenReturn(Arrays.asList(user));

        List<UserEntity> result = userService.findByVerified(true);

        assertThat(result).contains(user);
    }



    @Test
    void whenSetBalance_thenUpdateBalance() {
        when(savingAccountRepository.findByRut(user.getRut())).thenReturn(savingAccount);

        userService.setBalance(user.getRut(), 1000f);

        assertThat(savingAccount.getBalance()).isEqualTo(1000f);
        verify(savingAccountRepository).save(savingAccount);
    }

    @Test
    void whenSetDicom_thenUpdateDicomStatus() {
        when(worksheetRepository.findByRut(user.getRut())).thenReturn(worksheet);

        userService.setDicom(user.getRut(), true);

        assertThat(worksheet.getDicom()).isTrue();
        verify(worksheetRepository).save(worksheet);
    }

    @Test
    void whenSetTotalDebts_thenUpdateTotalDebts() {
        when(worksheetRepository.findByRut(user.getRut())).thenReturn(worksheet);

        userService.setTotalDebts(user.getRut(), 5000f);

        assertThat(worksheet.getTotalDebts()).isEqualTo(5000f);
        verify(worksheetRepository).save(worksheet);
    }

    @Test
    void whenSetIndependentJob_thenUpdateIndependentJobStatus() {
        when(worksheetRepository.findByRut(user.getRut())).thenReturn(worksheet);

        userService.setIndependentJob(user.getRut(), true);

        assertThat(worksheet.getIndependentJob()).isTrue();
        verify(worksheetRepository).save(worksheet);
    }

    @Test
    void whenSetJobSeniority_thenUpdateJobSeniority() {
        when(worksheetRepository.findByRut(user.getRut())).thenReturn(worksheet);

        userService.setJobSeniority(user.getRut(), 10);

        assertThat(worksheet.getJobSeniority()).isEqualTo(10);
        verify(worksheetRepository).save(worksheet);
    }

    @Test
    void whenSetSalary_thenUpdateSalary() {
        when(worksheetRepository.findByRut(user.getRut())).thenReturn(worksheet);

        userService.setSalary(user.getRut(), 2000);

        assertThat(worksheet.getSalary()).isEqualTo(2000);
        verify(worksheetRepository).save(worksheet);
    }

    @Test
    void whenSetIndepentEvaluate_thenUpdateEvaluateStatus() {
        when(worksheetRepository.findByRut(user.getRut())).thenReturn(worksheet);

        userService.setIndepentEvaluate(user.getRut(), true);

        assertThat(worksheet.getIndependentEvaluate()).isTrue();
        verify(worksheetRepository).save(worksheet);
    }

    @Test
    void whenSetLatePayment_thenUpdateLatePaymentStatus() {
        when(worksheetRepository.findByRut(user.getRut())).thenReturn(worksheet);

        userService.setLatePayment(user.getRut(), true);

        assertThat(worksheet.getLatePayment()).isTrue();
        verify(worksheetRepository).save(worksheet);
    }

    @Test
    void whenSetRetiredCash_thenUpdateRetiredCash() {
        when(savingAccountRepository.findByRut(user.getRut())).thenReturn(savingAccount);

        userService.setRetiredCash(user.getRut(), 300f);

        assertThat(savingAccount.getRetiredCash()).isEqualTo(300f);
        verify(savingAccountRepository).save(savingAccount);
    }

    @Test
    void whenSetPeriodicDeposit_thenUpdatePeriodicDeposit() {
        when(savingAccountRepository.findByRut(user.getRut())).thenReturn(savingAccount);

        userService.setPeriodicDeposit(user.getRut(), 200f);

        assertThat(savingAccount.getPeriodicDeposit()).isEqualTo(200f);
        verify(savingAccountRepository).save(savingAccount);
    }

    @Test
    void whenSetRecentlyRetiredCash_thenUpdateRecentlyRetiredCash() {
        when(savingAccountRepository.findByRut(user.getRut())).thenReturn(savingAccount);

        userService.setRecentlyRetiredCash(user.getRut(), 100f);

        assertThat(savingAccount.getRecentlyRetiredCash()).isEqualTo(100f);
        verify(savingAccountRepository).save(savingAccount);
    }

    @Test
    void whenFindByIdNotFound_thenReturnNull() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        UserEntity result = userService.findById(99L);

        assertThat(result).isNull();
    }

    @Test
    void whenSaveApplicationWithExistingRut_thenReturnFalse() {
        when(userRepository.findByRut(user.getRut())).thenReturn(user);  // Simulamos que el rut ya existe.
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.findByPhone(user.getPhone())).thenReturn(null);

        Boolean result = userService.saveApplication(user);

        assertThat(result).isFalse();
        verify(userRepository, never()).save(user);  // Verifica que no se guarda el usuario si el rut ya existe.
    }

    @Test
    void whenSaveApplicationWithExistingEmail_thenReturnFalse() {
        when(userRepository.findByRut(user.getRut())).thenReturn(null);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);  // Simulamos que el email ya existe.
        when(userRepository.findByPhone(user.getPhone())).thenReturn(null);

        Boolean result = userService.saveApplication(user);

        assertThat(result).isFalse();
        verify(userRepository, never()).save(user);  // Verifica que no se guarda el usuario si el email ya existe.
    }

    @Test
    void whenSaveApplicationWithExistingPhone_thenReturnFalse() {
        when(userRepository.findByRut(user.getRut())).thenReturn(null);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.findByPhone(user.getPhone())).thenReturn(user);  // Simulamos que el teléfono ya existe.

        Boolean result = userService.saveApplication(user);

        assertThat(result).isFalse();
        verify(userRepository, never()).save(user);  // Verifica que no se guarda el usuario si el teléfono ya existe.
    }




    @Test
    void whenLoginWithIncorrectCredentials_thenReturnNull() {
        when(userRepository.findByEmailAndPassword("test@example.com", "wrongpassword")).thenReturn(null);

        UserEntity result = userService.login("test@example.com", "wrongpassword");

        assertThat(result).isNull();
    }

    @Test
    void whenFindByRutNotFound_thenReturnNull() {
        when(userRepository.findByRut("11.222.333-4")).thenReturn(null);

        UserEntity result = userService.findByRut("11.222.333-4");

        assertThat(result).isNull();
    }

    @Test
    void whenSaveApplicationWithUniqueRutEmailAndPhone_thenReturnTrue() {
        when(userRepository.findByRut(user.getRut())).thenReturn(null);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.findByPhone(user.getPhone())).thenReturn(null);

        Boolean result = userService.saveApplication(user);

        assertThat(result).isTrue();
        verify(userRepository).save(user);
        verify(worksheetRepository).save(any(CustomerWorksheetEntity.class));
        verify(savingAccountRepository).save(any(SavingAccountEntity.class));
        verify(documentRepository).save(any(DocumentEntity.class));
    }

    @Test
    void whenLoginWithNonExistentUser_thenReturnNull() {
        when(userRepository.findByEmailAndPassword("nonexistent@example.com", "password")).thenReturn(null);

        UserEntity result = userService.login("nonexistent@example.com", "password");

        assertThat(result).isNull();
    }

    @Test
    void whenFindByVerified_thenInvokeRepositoryMethod() {
        when(userRepository.findByVerified(true)).thenReturn(Arrays.asList(user));

        List<UserEntity> result = userService.findByVerified(true);

        assertThat(result).contains(user);
        verify(userRepository).findByVerified(true);  // Verifica que se llama el método correctamente
    }


    @Test
    void whenSaveApplicationWithDuplicateRutAndPhone_thenReturnFalse() {
        when(userRepository.findByRut(user.getRut())).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.findByPhone(user.getPhone())).thenReturn(user);  // Simulamos que el teléfono ya existe.

        Boolean result = userService.saveApplication(user);

        assertThat(result).isFalse();
        verify(userRepository, never()).save(user);  // Verifica que no se guarda el usuario
    }


    @Test
    void setSeniority_ShouldHandleNullAccount() {
        // Arrange
        String testRut = "12345678-9";
        int newSeniority = 5;

        when(savingAccountRepository.findByRut(testRut)).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            userService.setSeniority(testRut, newSeniority);
        });

        verify(savingAccountRepository).findByRut(testRut);
        verify(savingAccountRepository, never()).save(any());
    }

    @Test
    void verifyCostumer_ShouldVerifyUser() {
        // Arrange
        UserEntity mockUser = new UserEntity();
        mockUser.setRut("1234567820");
        mockUser.setVerified(false);

        when(userRepository.findByRut("1234567820")).thenReturn(mockUser);

        // Act
        userService.verifyCostumer("1234567820");

        // Assert
        verify(userRepository).findByRut("1234567820");
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void verifyCostumer_ShouldHandleNullUser() {
        // Arrange
        when(userRepository.findByRut("1234567820")).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            userService.verifyCostumer("1234567820");
        });

        verify(userRepository).findByRut("1234567820");
        verify(userRepository, never()).save(any());
    }

    @Test
    void getIdByRut_ShouldReturnNull_WhenUserNotFound() {
        // Arrange
        String testRut = "1234567820";
        when(userRepository.findByRut(testRut)).thenReturn(null);

        // Act
        String result = userService.getIdByRut(testRut);

        // Assert
        assertNull(result);
        verify(userRepository).findByRut(testRut);
    }

    @Test
    void deleteByRut_ShouldDeleteAllRelatedEntities() {
        // Arrange
        Long testId = 1L;

        CustomerWorksheetEntity worksheetEntity = new CustomerWorksheetEntity();
        worksheetEntity.setId(testId);

        SavingAccountEntity savingEntity = new SavingAccountEntity();
        savingEntity.setId(testId);

        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setId(testId);

        // Configurar los mocks para retornar las entidades
        when(worksheetRepository.findById(testId)).thenReturn(Optional.of(worksheetEntity));
        when(savingAccountRepository.findById(testId)).thenReturn(Optional.of(savingEntity));
        when(documentRepository.findById(testId)).thenReturn(Optional.of(documentEntity));

        // Act
        userService.deleteByRut(testId);

        // Assert
        verify(userRepository).deleteById(testId);
        verify(worksheetRepository).deleteById(worksheetEntity.getId());
        verify(savingAccountRepository).deleteById(savingEntity.getId());
        verify(documentRepository).deleteById(documentEntity.getId());
    }
}
