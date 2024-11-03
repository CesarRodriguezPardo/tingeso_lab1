import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CustomerService from '../services/user.service';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';  
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';

const Register = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    middleName: '',
    firstSurname: '',
    secondSurname: '',
    rut: '',
    email: '',
    password: '',
    phone: '',
    age: '',
  });
  
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    console.log('Register button clicked!');
  
    const formDataToSend = new FormData();
    Object.keys(formData).forEach((key) => {
      formDataToSend.append(key, formData[key]);
    });
  
    console.log('FormData to send:', Array.from(formDataToSend.entries()));
  
    try {
      const response = await CustomerService.saveApply(formDataToSend);
      console.log('Response:', response);
  
      if (response.status === 200 || response.status === 201) {
        const userId = response.data.id; // Asegúrate de que esto sea lo que devuelve tu API
        setMessage('Registration successful!');
  
        // Guardar el RUT en localStorage
        localStorage.setItem('rut', formData.rut);
  
        navigate('/registerdocument', { state: { userId: userId } }); // Solo pasamos el ID del usuario
      } else {
        console.error('Unexpected response status:', response.status);
        setMessage('Unexpected response from the server. Please try again.');
      }
    } catch (error) {
      console.error('Registration error:', error.response ? error.response.data : error.message);
      setMessage('Error during registration. Please check your details.');
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
          Register
        </Typography>
        
        <form onSubmit={handleRegister} style={{ width: '100%' }}>
          <TextField
            fullWidth
            label="First Name"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
            required
            margin="normal"
          />
          <TextField
            fullWidth
            label="Middle Name"
            name="middleName"
            value={formData.middleName}
            onChange={handleChange}
            margin="normal"
          />
          <TextField
            fullWidth
            label="First Surname"
            name="firstSurname"
            value={formData.firstSurname}
            onChange={handleChange}
            required
            margin="normal"
          />
          <TextField
            fullWidth
            label="Second Surname"
            name="secondSurname"
            value={formData.secondSurname}
            onChange={handleChange}
            margin="normal"
          />
          <TextField
            fullWidth
            label="RUT"
            name="rut"
            value={formData.rut}
            onChange={handleChange}
            required
            margin="normal"
          />
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
          <TextField
            fullWidth
            label="Phone"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
            margin="normal"
          />
          <TextField
            fullWidth
            label="Age"
            name="age"
            type="number"
            value={formData.age}
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
            Continue register
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

export default Register;
