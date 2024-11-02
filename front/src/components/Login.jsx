import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CustomerService from '../services/customer.service'; // Asegúrate de tener tu servicio de cliente configurado
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';

const Login = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value
    }));
  };

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await CustomerService.login(formData);

      if (response.status === 200) {
        setMessage('Login successful!');
        navigate('/dashboard'); // Cambia la ruta a tu página principal o dashboard
      }
    } catch (error) {
      console.error('Login error:', error);
      setMessage('Invalid credentials. Please try again.');
    }
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
        <Typography variant="h4" gutterBottom>
          Login
        </Typography>

        <form onSubmit={handleLogin} style={{ width: '100%' }}>
          <TextField
            fullWidth
            label="Email"
            name="email"
            type="email"
            value={formData.email}
            onChange={handleChange}
            required
            margin="normal"
          />
          <TextField
            fullWidth
            label="Password"
            name="password"
            type="password"
            value={formData.password}
            onChange={handleChange}
            required
            margin="normal"
          />
          
          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            style={{ marginTop: '20px' }}
          >
            Login
          </Button>
        </form>

        {message && (
          <Typography variant="body2" color="error" style={{ marginTop: '20px' }}>
            {message}
          </Typography>
        )}
      </Paper>
    </Grid>
  );
};

export default Login;
