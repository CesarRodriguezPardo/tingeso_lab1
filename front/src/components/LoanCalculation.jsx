import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import LoanService from '../services/calculate.service'; // Asegúrate de que la ruta sea correcta
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import CircularProgress from '@mui/material/CircularProgress';

const LoanCalculations = () => {
  const location = useLocation();
  const navigate = useNavigate();

  // Recuperar datos enviados desde el formulario
  const { loanType, requestedAmount, requestedTerm, interestRate } = location.state || {};

  const [payment, setPayment] = useState(null);
  const [insurance, setInsurance] = useState(null);
  const [administrationCost, setAdministrationCost] = useState(null);
  const [totalCost, setTotalCost] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!location.state) {
      // Si no hay datos, redirigir al formulario
      navigate('/home');
    } else {
      // Realizar cálculos una vez que se tienen los datos
      const calculateValues = async () => {
        const p = parseFloat(interestRate.replace('%', '')) / 100; // Convertir el porcentaje a decimal
        const n = requestedTerm; // Número de años
        const amount = requestedAmount; // Monto solicitado

        try {
          // Llamadas al servicio para calcular los diferentes costos
          const paymentResponse = await LoanService.calculatePayment(p, amount, n);
          setPayment(paymentResponse.data);

          const insuranceResponse = await LoanService.calculateInsurance(amount, n);
          setInsurance(insuranceResponse.data);

          const administrationResponse = await LoanService.calculateAdministrationCost(amount);
          setAdministrationCost(administrationResponse.data);

          const totalResponse = await LoanService.calculateTotal(paymentResponse.data, n, insuranceResponse.data, administrationResponse.data);
          setTotalCost(totalResponse.data);
        } catch (error) {
          console.error('Error calculating values:', error);
        } finally {
          setLoading(false);
        }
      };

      calculateValues();
    }
  }, [location.state, navigate, interestRate, requestedAmount, requestedTerm]);

  if (loading) {
    return (
      <Grid container justifyContent="center" alignItems="center" style={{ minHeight: '100vh' }}>
        <CircularProgress />
      </Grid>
    );
  }

  return (
    <Grid container justifyContent="center" alignItems="center" style={{ minHeight: '100vh' }}>
      <Paper elevation={10} style={{ padding: '20px', width: '350px', textAlign: 'center' }}>
        <Typography variant="h5" gutterBottom>
          Loan Calculations
        </Typography>
        <Typography variant="body1">
          <strong>Loan Type:</strong> {loanType}
        </Typography>
        <Typography variant="body1">
          <strong>Requested Amount:</strong> {requestedAmount} USD
        </Typography>
        <Typography variant="body1">
          <strong>Requested Term:</strong> {requestedTerm} years
        </Typography>
        <Typography variant="body1">
          <strong>Interest Rate:</strong> {interestRate}
        </Typography>
        <Typography variant="body1">
          <strong>Monthly Payment:</strong> {payment ? payment.toFixed(2) : 'Calculating...'} USD
        </Typography>
        <Typography variant="body1">
          <strong>Insurance:</strong> {insurance ? insurance.toFixed(2) : 'Calculating...'} USD
        </Typography>
        <Typography variant="body1">
          <strong>Administration Cost:</strong> {administrationCost ? administrationCost.toFixed(2) : 'Calculating...'} USD
        </Typography>
        <Typography variant="body1">
          <strong>Total Cost:</strong> {totalCost ? totalCost.toFixed(2) : 'Calculating...'} USD
        </Typography>
        <Button 
          variant="contained" 
          color="primary" 
          onClick={() => navigate('/loancalculation')} 
          style={{ marginTop: '20px' }}
        >
          Back to Application
        </Button>
      </Paper>
    </Grid>
  );
};

export default LoanCalculations;
