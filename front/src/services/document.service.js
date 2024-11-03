import httpClient from "../http-common";

const downloadFile = (id, filename) => {
  return httpClient.get(`/api/v1/document/download/${id}/${filename}`, {
    responseType: 'arraybuffer', // Asegúrate de que sea 'arraybuffer'
  });
};

const uploadCustomerDocs = async (userId, savingAccountFile, worksheetFile) => {
  try {
    const formData = new FormData();
    formData.append('savingAccountFile', savingAccountFile);
    formData.append('worksheetFile', worksheetFile);

    const response = await httpClient.post(`/api/v1/document/uploadCustomerDocs/${userId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    return response.data; // Devuelve true si es exitoso
  } catch (error) {
    console.error("Error uploading files:", error);
    return false; // o maneja el error de otra forma
  }
};


const uploadFirstCreditDocs = async (userId, incomeFile, appraisalFile, creditHistoryFile) => {
  try {
    const formData = new FormData();
    formData.append('incomeFile', incomeFile);
    formData.append('appraisalFile', appraisalFile);
    formData.append('creditHistoryFile', creditHistoryFile);

    const response = await httpClient.post(`/api/v1/document/uploadFirstCreditDocs/${userId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    return response.data; // Devuelve true si es exitoso
  } catch (error) {
    console.error("Error uploading first credit docs:", error);
    return false; // o maneja el error de otra forma
  }
};

const uploadSecondCreditDocs = async (userId, incomeFile, appraisalFile, creditHistoryFile, firstHomeFile) => {
  try {
    const formData = new FormData();
    formData.append('incomeFile', incomeFile);
    formData.append('appraisalFile', appraisalFile);
    formData.append('creditHistoryFile', creditHistoryFile);
    formData.append('firstHomeFile', firstHomeFile);

    const response = await httpClient.post(`/api/v1/document/uploadSecondCreditDocs/${userId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    return response.data; // Devuelve true si es exitoso
  } catch (error) {
    console.error("Error uploading second credit docs:", error);
    return false; // o maneja el error de otra forma
  }
};

const uploadCommercialDocs = async (userId, financialStatement, incomeFile, appraisalFile, businessPlanFile) => {
  try {
    const formData = new FormData();
    formData.append('financialStatement', financialStatement);
    formData.append('incomeFile', incomeFile);
    formData.append('appraisalFile', appraisalFile);
    formData.append('businessPlanFile', businessPlanFile);

    const response = await httpClient.post(`/api/v1/document/uploadCommercialDocs/${userId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    return response.data; // Devuelve true si es exitoso
  } catch (error) {
    console.error("Error uploading commercial docs:", error);
    return false; // o maneja el error de otra forma
  }
};

const uploadRemodelingDocs = async (userId, incomeFile, remodelingBudgetFile, updatedAppraisalFile) => {
  try {
    const formData = new FormData();
    formData.append('incomeFile', incomeFile);
    formData.append('remodelingBudgetFile', remodelingBudgetFile);
    formData.append('updatedAppraisalFile', updatedAppraisalFile);

    const response = await httpClient.post(`/api/v1/document/uploadRemodelingDocs/${userId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    return response.data; // Devuelve true si es exitoso
  } catch (error) {
    console.error("Error uploading remodeling docs:", error);
    return false; // o maneja el error de otra forma
  }
};

const isSavingUploaded = (userId) => {
  return httpClient.get(`/api/v1/document/isSavingUploaded/${userId}`);
};

const isWorksheetUploaded = (userId) => {
  return httpClient.get(`/api/v1/document/isWorksheetUploaded/${userId}`);
};

const isIncomeUploaded = (userId) => {
  return httpClient.get(`/api/v1/document/isIncomeUploaded/${userId}`);
};

const isAppraisalUploaded = (userId) => {
  return httpClient.get(`/api/v1/document/isAppraisalUploaded/${userId}`);
};

const isCreditHistoryUploaded = (userId) => {
  return httpClient.get(`/api/v1/document/isCreditHistoryUploaded/${userId}`);
};

const isFirstHomeUploaded = (userId) => {
  return httpClient.get(`/api/v1/document/isFirstHomeUploaded/${userId}`);
};

const isBusinessUploaded = (userId) => {
  return httpClient.get(`/api/v1/document/isBusinessUploaded/${userId}`);
};

const isRemodelingUploaded = (userId) => {
  return httpClient.get(`/api/v1/document/isRemodelingUploaded/${userId}`);
};

const isUpdatedAppraisalUploaded = (userId) => {
  return httpClient.get(`/api/v1/document/isUpdatedAppraisalUploaded/${userId}`);
};

const isFinancialUploaded = (userId) => {
  return httpClient.get(`/api/v1/document/isFinancialUploaded/${userId}`);
};

export default {
  downloadFile,
  uploadCustomerDocs,
  isSavingUploaded,
  isWorksheetUploaded,
  isIncomeUploaded,
  isAppraisalUploaded,
  isCreditHistoryUploaded,
  isFirstHomeUploaded,
  isBusinessUploaded,
  isRemodelingUploaded,
  isUpdatedAppraisalUploaded,
  isFinancialUploaded,
  uploadFirstCreditDocs,
  uploadSecondCreditDocs,
  uploadCommercialDocs,
  uploadRemodelingDocs
};
