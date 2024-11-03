import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import MenuItem from '@mui/material/MenuItem';
import UploadFile from '@mui/icons-material/UploadFile';
import documentService from '../services/document.service'; // Asegúrate de importar tu servicio
import creditService from '../services/credit.service'; // Importa el servicio de crédito

const LoanRequest = () => {
  const [formData, setFormData] = useState({
    loanType: '',
    interestRate: '',
    maxAmount: '',
    term: '',
    requestedAmount: '',
    requestedTerm: '',
    files: {
      incomeFile: null,
      appraisalFile: null,
      creditHistoryFile: null,
      firstHomeFile: null,
      financialStatement: null,
      businessPlanFile: null,
      remodelingBudgetFile: null,
      updatedAppraisalFile: null,
    }
  });

  const navigate = useNavigate();

  const loanTypes = [
    { type: 'First Home', interestRate: 5, maxAmount: '80% property value', term: 30, requiredFiles: ['incomeFile', 'appraisalFile', 'creditHistoryFile'], typeId: 1 },
    { type: 'Second Home', interestRate: 6, maxAmount: '70% property value', term: 20, requiredFiles: ['incomeFile', 'appraisalFile', 'creditHistoryFile', 'firstHomeFile'], typeId: 2 },
    { type: 'Commercial Property', interestRate: 7, maxAmount: '60% property value', term: 25, requiredFiles: ['financialStatement', 'incomeFile', 'appraisalFile', 'businessPlanFile'], typeId: 3 },
    { type: 'Remodeling', interestRate: 8, maxAmount: '50% property value', term: 15, requiredFiles: ['incomeFile', 'remodelingBudgetFile', 'updatedAppraisalFile'], typeId: 4 }
  ];

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (Number(value) < 0) {
      alert('The value cannot be negative.');
      setFormData((prevData) => ({
        ...prevData,
        [name]: ''
      }));
      return;
    }

    if (name === 'loanType') {
      const selectedLoan = loanTypes.find(loan => loan.type === value);
      setFormData({
        ...formData,
        loanType: value,
        interestRate: selectedLoan.interestRate,
        maxAmount: selectedLoan.maxAmount,
        term: selectedLoan.term,
        requestedAmount: '',
        requestedTerm: '',
        files: {
          incomeFile: null,
          appraisalFile: null,
          creditHistoryFile: null,
          firstHomeFile: null,
          financialStatement: null,
          businessPlanFile: null,
          remodelingBudgetFile: null,
          updatedAppraisalFile: null,
        }
      });
    } else {
      setFormData((prevData) => ({
        ...prevData,
        [name]: value
      }));
    }
  };

  const handleFileChange = (e) => {
    const { name, files } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      files: {
        ...prevData.files,
        [name]: files[0]
      }
    }));
  };

  const uploadDocuments = async (userId) => {
    const { loanType, files } = formData;
    let uploadSuccess = false;

    switch (loanType) {
      case 'First Home':
        uploadSuccess = await documentService.uploadFirstCreditDocs(userId, files.incomeFile, files.appraisalFile, files.creditHistoryFile);
        break;
      case 'Second Home':
        uploadSuccess = await documentService.uploadSecondCreditDocs(userId, files.incomeFile, files.appraisalFile, files.creditHistoryFile, files.firstHomeFile);
        break;
      case 'Commercial Property':
        uploadSuccess = await documentService.uploadCommercialDocs(userId, files.financialStatement, files.incomeFile, files.appraisalFile, files.businessPlanFile);
        break;
      case 'Remodeling':
        uploadSuccess = await documentService.uploadRemodelingDocs(userId, files.incomeFile, files.remodelingBudgetFile, files.updatedAppraisalFile);
        break;
      default:
        break;
    }

    return uploadSuccess;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const userRut = JSON.parse(localStorage.getItem("user")).rut;
    const userId = JSON.parse(localStorage.getItem("user")).id; // Asegúrate de obtener el ID del usuario

    try {
      // Subir documentos
      const uploadSuccess = await uploadDocuments(userId);
      if (!uploadSuccess) {
        alert('Failed to upload documents. Please check your files and try again.');
        return;
      }

      // Obtener el tipo de crédito
      const selectedLoan = loanTypes.find(loan => loan.type === formData.loanType);

      // Crear el objeto de crédito a guardar
      const credit = {
        requestedAmount: formData.requestedAmount,
        requestedTerm: formData.requestedTerm,
        interestRate: formData.interestRate,
        rut: userRut, // Usa el RUT del usuario como ID
        type: selectedLoan.typeId // Asignar el ID del tipo de crédito
      };

      console.log(credit);

      // Guardar la solicitud de crédito
      await creditService.saveApply(credit); // Llama al servicio de crédito

      navigate('/home', { state: formData }); // Navega a la siguiente página
    } catch (error) {
      console.error("Error submitting loan request:", error);
      alert('Failed to submit loan request. Please try again.');
    }
  };

  const paperStyle = {
    padding: 20,
    height: 'auto',
    width: 400,
    margin: '20px auto',
  };

  return (
    <Grid container justifyContent="center" alignItems="center" style={{ minHeight: '100vh' }}>
      <Paper elevation={10} style={paperStyle}>
        <Grid align="center">
          <Typography variant="h5" gutterBottom>
            Loan Request
          </Typography>
        </Grid>

        <form onSubmit={handleSubmit}>
          <TextField
            select
            label="Select Loan Type"
            name="loanType"
            value={formData.loanType}
            onChange={handleChange}
            fullWidth
            margin="normal"
          >
            {loanTypes.map((option) => (
              <MenuItem key={option.type} value={option.type}>
                {option.type}
              </MenuItem>
            ))}
          </TextField>

          {formData.loanType && (
            <div>
              <Typography variant="body1" gutterBottom>
                <strong>Interest Rate:</strong> {formData.interestRate}
              </Typography>
              <Typography variant="body1" gutterBottom>
                <strong>Maximum Amount:</strong> {formData.maxAmount}
              </Typography>
              <Typography variant="body1" gutterBottom>
                <strong>Term:</strong> {formData.term} years
              </Typography>
            </div>
          )}

          <TextField
            label="Requested Amount"
            name="requestedAmount"
            type="number"
            value={formData.requestedAmount}
            onChange={handleChange}
            fullWidth
            margin="normal"
            required
          />

          <TextField
            label="Requested Term (in years)"
            name="requestedTerm"
            type="number"
            value={formData.requestedTerm}
            onChange={handleChange}
            fullWidth
            margin="normal"
            required
          />

          {formData.loanType &&
            loanTypes.find(loan => loan.type === formData.loanType).requiredFiles.map((fileType) => (
              <TextField
                key={fileType}
                label={fileType.replace(/([A-Z])/g, ' $1')}
                name={fileType}
                type="file"
                onChange={handleFileChange}
                fullWidth
                margin="normal"
                InputProps={{
                  endAdornment: <UploadFile />,
                }}
              />
            ))
          }

          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            style={{ marginTop: '20px' }}
          >
            Submit Request
          </Button>
        </form>
      </Paper>
    </Grid>
  );
};

export default LoanRequest;
