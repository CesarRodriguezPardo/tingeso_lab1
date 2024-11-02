import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import MenuItem from '@mui/material/MenuItem';

const LoanApplication = () => {
  const [formData, setFormData] = useState({
    loanType: '',
    interestRate: '',
    maxAmount: '',
    term: '',
    requestedAmount: '', // Monto solicitado
    requestedTerm: '' // Tiempo solicitado
  });
  const navigate = useNavigate();

  // Datos ficticios de ejemplo
  const loanTypes = [
    { type: 'First Home', interestRate: '5%', maxAmount: '80% property value', term: '30 years' },
    { type: 'Second Home', interestRate: '6%', maxAmount: '70% property value', term: '20 years' },
    { type: 'Commercial Property', interestRate: '7%', maxAmount: '60% property value', term: '25 years' },
    { type: 'Remodeling', interestRate: '6%', maxAmount: '50% property value', term: '15 years' }
  ];

  const handleChange = (e) => {
    const { name, value } = e.target;

    // Si cambia el tipo de crédito, actualiza las tasas de interés y otros detalles
    if (name === 'loanType') {
      const selectedLoan = loanTypes.find(loan => loan.type === value);
      setFormData({
        loanType: value,
        interestRate: selectedLoan.interestRate,
        maxAmount: selectedLoan.maxAmount,
        term: selectedLoan.term,
        requestedAmount: '', // Reiniciar monto solicitado
        requestedTerm: '' // Reiniciar tiempo solicitado
      });
    } else {
      setFormData((prevData) => ({
        ...prevData,
        [name]: value
      }));
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Aquí podrías agregar lógica para validar el monto y el tiempo solicitados
    // Por ejemplo, asegurarte de que el monto no exceda el máximo permitido

    // Redirigir a la página de cálculos y pasar el estado
    navigate('/loancalculation', { state: formData });
  };

  const paperStyle = {
    padding: 20,
    height: 'auto',
    width: 350,
    margin: '20px auto',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
  };

  return (
    <Grid container justifyContent="center" alignItems="center" style={{ minHeight: '100vh' }}>
      <Paper elevation={10} style={paperStyle}>
        <Grid align="center">
          <Typography variant="h5" gutterBottom>
            Simulate
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
                <strong>Term:</strong> {formData.term}
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

          <Button
            type="submit" // Asegúrate de que el tipo sea "submit"
            variant="contained"
            color="primary"
            fullWidth
            style={{ marginTop: '20px' }}
          >
            Calculate
          </Button>
        </form>
      </Paper>
    </Grid>
  );
};

export default LoanApplication;