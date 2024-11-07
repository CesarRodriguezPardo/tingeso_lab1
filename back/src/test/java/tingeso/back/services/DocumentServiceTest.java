package tingeso.back.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import tingeso.back.entities.DocumentEntity;
import tingeso.back.repositories.DocumentRepository;
import java.util.List;
import java.util.Arrays;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService documentService;

    private DocumentEntity documentEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        documentEntity = new DocumentEntity();
        documentEntity.setUserId("userId");
        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity); // Simula que se obtiene el documento

    }

    @Test
    void testUploadedSavingWithNoFile() {
        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadedSaving("userId");

        assertFalse(result);
    }

    @Test
    void testUploadedSavingWithFile() {
        documentEntity.setSavingAccountFile(new byte[1]);
        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadedSaving("userId");

        assertTrue(result);
    }

    @Test
    void testUploadCustomerDocsWithFiles() throws IOException {
        MockMultipartFile savingAccountFile = new MockMultipartFile("savingAccountFile", "savingAccount.txt", "text/plain", "content".getBytes());
        MockMultipartFile worksheetFile = new MockMultipartFile("worksheetFile", "worksheet.txt", "text/plain", "content".getBytes());

        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadCustomerDocs("userId", savingAccountFile, worksheetFile);

        assertTrue(result);
        assertArrayEquals(savingAccountFile.getBytes(), documentEntity.getSavingAccountFile());
        assertArrayEquals(worksheetFile.getBytes(), documentEntity.getWorksheetFile());
        verify(documentRepository, times(1)).save(documentEntity);
    }




    @Test
    void testUploadFirstCreditDocsWithFiles() throws IOException {
        MockMultipartFile incomeFile = new MockMultipartFile("incomeFile", "income.txt", "text/plain", "content".getBytes());
        MockMultipartFile appraisalFile = new MockMultipartFile("appraisalFile", "appraisal.txt", "text/plain", "content".getBytes());
        MockMultipartFile creditHistoryFile = new MockMultipartFile("creditHistoryFile", "creditHistory.txt", "text/plain", "content".getBytes());

        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadFirstCreditDocs("userId", incomeFile, appraisalFile, creditHistoryFile);

        assertTrue(result);
        verify(documentRepository, times(1)).save(documentEntity);
    }

    @Test
    void testUploadRemodelingDocsWithFiles() throws IOException {
        MockMultipartFile incomeFile = new MockMultipartFile("incomeFile", "income.txt", "text/plain", "content".getBytes());
        MockMultipartFile remodelingBudgetFile = new MockMultipartFile("remodelingBudgetFile", "budget.txt", "text/plain", "content".getBytes());
        MockMultipartFile updatedAppraisalFile = new MockMultipartFile("updatedAppraisalFile", "appraisal.txt", "text/plain", "content".getBytes());

        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadRemodelingDocs("userId", incomeFile, remodelingBudgetFile, updatedAppraisalFile);

        assertTrue(result);
        verify(documentRepository, times(1)).save(documentEntity);
    }


    @Test
    void testUploadedSavingWithFilePresent() {
        documentEntity.setSavingAccountFile(new byte[1]);  // Simula un archivo presente
        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadedSaving("userId");

        assertTrue(result);
    }

    @Test
    void testUploadedSavingWithNoFilePresent() {
        documentEntity.setSavingAccountFile(null);  // Simula un archivo ausente
        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadedSaving("userId");

        assertFalse(result);
    }

    @Test
    void testUploadSecondCreditDocs() throws IOException {
        // Crear archivos simulados
        MockMultipartFile incomeFile = new MockMultipartFile("incomeFile", "income.txt", "text/plain", "content1".getBytes());
        MockMultipartFile appraisalFile = new MockMultipartFile("appraisalFile", "appraisal.txt", "text/plain", "content2".getBytes());
        MockMultipartFile creditHistoryFile = new MockMultipartFile("creditHistoryFile", "creditHistory.txt", "text/plain", "content3".getBytes());
        MockMultipartFile firstHomeFile = new MockMultipartFile("firstHomeFile", "firstHome.txt", "text/plain", "content4".getBytes());

        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        // Ejecutar el método
        Boolean result = documentService.uploadSecondCreditDocs("userId", incomeFile, appraisalFile, creditHistoryFile, firstHomeFile);

        // Verificar que se haya ejecutado correctamente
        assertTrue(result);
        assertArrayEquals(incomeFile.getBytes(), documentEntity.getIncomeFile());
        assertArrayEquals(appraisalFile.getBytes(), documentEntity.getAppraisalFile());
        assertArrayEquals(creditHistoryFile.getBytes(), documentEntity.getCreditHistoryFile());
        assertArrayEquals(firstHomeFile.getBytes(), documentEntity.getFirstHomeFile());

        // Verificar que la entidad fue guardada una vez
        verify(documentRepository, times(1)).save(documentEntity);
    }

    @Test
    void testUploadCommercialDocs() throws IOException {
        // Crear archivos simulados
        MockMultipartFile financialStatement = new MockMultipartFile("financialStatement", "financialStatement.txt", "text/plain", "content1".getBytes());
        MockMultipartFile incomeFile = new MockMultipartFile("incomeFile", "income.txt", "text/plain", "content2".getBytes());
        MockMultipartFile appraisalFile = new MockMultipartFile("appraisalFile", "appraisal.txt", "text/plain", "content3".getBytes());
        MockMultipartFile businessPlanFile = new MockMultipartFile("businessPlanFile", "businessPlan.txt", "text/plain", "content4".getBytes());

        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        // Ejecutar el método
        Boolean result = documentService.uploadCommercialDocs("userId", financialStatement, incomeFile, appraisalFile, businessPlanFile);

        // Verificar que se haya ejecutado correctamente
        assertTrue(result);
        assertArrayEquals(financialStatement.getBytes(), documentEntity.getFinancialStatement());
        assertArrayEquals(incomeFile.getBytes(), documentEntity.getIncomeFile());
        assertArrayEquals(appraisalFile.getBytes(), documentEntity.getAppraisalFile());
        assertArrayEquals(businessPlanFile.getBytes(), documentEntity.getBusinessPlanFile());

        // Verificar que la entidad fue guardada una vez
        verify(documentRepository, times(1)).save(documentEntity);
    }

    @Test
    void testUploadedWorksheet() {
        documentEntity.setWorksheetFile(new byte[1]);
        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadedWorksheet("userId");

        assertTrue(result);
    }

    @Test
    void testUploadedIncome() {
        documentEntity.setIncomeFile(new byte[1]);
        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadedIncome("userId");

        assertTrue(result);
    }

    @Test
    void testUploadedAppraisal() {
        documentEntity.setAppraisalFile(new byte[1]);
        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadedAppraisal("userId");

        assertTrue(result);
    }

    @Test
    void testUploadedCreditHistory() {
        documentEntity.setCreditHistoryFile(new byte[1]);
        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadedCreditHistory("userId");

        assertTrue(result);
    }

    @Test
    void testUploadedFirstHome() {
        documentEntity.setFirstHomeFile(new byte[1]);
        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadedFirstHome("userId");

        assertTrue(result);
    }

    @Test
    void testUploadedBusiness() {
        documentEntity.setBusinessPlanFile(new byte[1]);
        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadedBusiness("userId");

        assertTrue(result);
    }

    @Test
    void testUploadedRemodeling() {
        documentEntity.setRemodelingBudgetFile(new byte[1]);
        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadedRemodeling("userId");

        assertTrue(result);
    }

    @Test
    void testUploadedUpdatedAppraisal() {
        documentEntity.setAppraisalFile(new byte[1]);
        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadedUpdatedAppraisal("userId");

        assertTrue(result);
    }

    @Test
    void testUploadedFinancial() {
        documentEntity.setFinancialStatement(new byte[1]);
        when(documentRepository.findByUserId("userId")).thenReturn(documentEntity);

        Boolean result = documentService.uploadedFinancial("userId");

        assertTrue(result);
    }

    @Test
    void testGetAll() {
        // Crea entidades simuladas de DocumentEntity
        DocumentEntity document1 = new DocumentEntity();
        document1.setUserId("userId1");

        DocumentEntity document2 = new DocumentEntity();
        document2.setUserId("userId2");

        // Simula el comportamiento del repositorio
        when(documentRepository.findAll()).thenReturn(Arrays.asList(document1, document2));

        // Llama al método y verifica el resultado
        List<DocumentEntity> result = documentService.getAll();

        // Verifica que la lista no sea nula y tenga el tamaño correcto
        assertNotNull(result, "La lista no debe ser nula");
        assertEquals(2, result.size(), "El tamaño de la lista debe ser 2");

        // Verifica que los elementos de la lista tengan los userId esperados
        assertEquals("userId1", result.get(0).getUserId(), "El primer userId debe ser 'userId1'");
        assertEquals("userId2", result.get(1).getUserId(), "El segundo userId debe ser 'userId2'");

        // Verifica que el método findAll del repositorio se haya llamado exactamente una vez
        verify(documentRepository, times(1)).findAll();
    }


    @Test
    void testUploadRemodelingDocsIOException() throws IOException {
        // Crea mock de los archivos que lanza una IOException en incomeFile
        MultipartFile incomeFile = mock(MultipartFile.class);
        MultipartFile remodelingBudgetFile = mock(MultipartFile.class);
        MultipartFile updatedAppraisalFile = mock(MultipartFile.class);

        // Simula que getBytes() de incomeFile lanza una IOException
        when(incomeFile.getBytes()).thenThrow(new IOException("Error al leer el archivo"));

        // Simula que los otros archivos no causan problemas y retornan bytes válidos
        when(remodelingBudgetFile.getBytes()).thenReturn(new byte[0]);  // Simula archivo válido
        when(updatedAppraisalFile.getBytes()).thenReturn(new byte[0]);  // Simula archivo válido

        // Llama al método que debe manejar la excepción
        Boolean result = documentService.uploadRemodelingDocs("userId", incomeFile, remodelingBudgetFile, updatedAppraisalFile);

        // Verifica que el resultado sea false debido a un error en la lectura de incomeFile
        assertFalse(result, "Se esperaba que el resultado sea false debido a un error en la lectura del archivo");

        // Verifica que el repositorio no haya guardado el documento debido al error
        verify(documentRepository, never()).save(any(DocumentEntity.class));
    }

    @Test
    void testUploadCommercialDocsIOException() throws IOException {
        // Crea mock de los archivos que lanza una IOException en incomeFile
        MultipartFile financialStatement = mock(MultipartFile.class);
        MultipartFile incomeFile = mock(MultipartFile.class);
        MultipartFile appraisalFile = mock(MultipartFile.class);
        MultipartFile businessPlanFile = mock(MultipartFile.class);

        // Simula que getBytes() de incomeFile lanza una IOException
        when(incomeFile.getBytes()).thenThrow(new IOException("Error al leer el archivo"));

        // Simula que los otros archivos no causan problemas y retornan bytes válidos
        when(financialStatement.getBytes()).thenReturn(new byte[0]);  // Simula archivo válido
        when(appraisalFile.getBytes()).thenReturn(new byte[0]);  // Simula archivo válido
        when(businessPlanFile.getBytes()).thenReturn(new byte[0]);  // Simula archivo válido

        // Llama al método que debe manejar la excepción
        Boolean result = documentService.uploadCommercialDocs("userId", financialStatement, incomeFile, appraisalFile, businessPlanFile);

        // Verifica que el resultado sea false debido a un error en la lectura de incomeFile
        assertFalse(result, "Se esperaba que el resultado sea false debido a un error en la lectura del archivo incomeFile");

        // Verifica que el repositorio no haya guardado el documento debido al error
        verify(documentRepository, never()).save(any(DocumentEntity.class));
    }

    @Test
    void testUploadCommercialDocs2IOException() throws IOException {
        // Crea mock de los archivos que lanza una IOException en incomeFile
        MultipartFile financialStatement = mock(MultipartFile.class);
        MultipartFile incomeFile = mock(MultipartFile.class);
        MultipartFile appraisalFile = mock(MultipartFile.class);
        MultipartFile businessPlanFile = mock(MultipartFile.class);

        // Simula que getBytes() de incomeFile lanza una IOException
        when(incomeFile.getBytes()).thenThrow(new IOException("Error al leer el archivo"));

        // Simula que los otros archivos no causan problemas y retornan bytes válidos
        when(financialStatement.getBytes()).thenReturn(new byte[0]);  // Simula archivo válido
        when(appraisalFile.getBytes()).thenReturn(new byte[0]);  // Simula archivo válido
        when(businessPlanFile.getBytes()).thenReturn(new byte[0]);  // Simula archivo válido

        // Llama al método que debe manejar la excepción
        Boolean result = documentService.uploadFirstCreditDocs("userId", financialStatement, incomeFile, appraisalFile);

        // Verifica que el resultado sea false debido a un error en la lectura de incomeFile
        assertFalse(result, "Se esperaba que el resultado sea false debido a un error en la lectura del archivo incomeFile");

        // Verifica que el repositorio no haya guardado el documento debido al error
        verify(documentRepository, never()).save(any(DocumentEntity.class));
    }

}
