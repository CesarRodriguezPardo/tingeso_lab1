import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CustomerService from '../services/customer.service';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';

const RegisterFile = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
  });
  const [files, setFiles] = useState({
    file1: null,
    file2: null,
  });
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleFileChange = (e) => {
    const { name, files } = e.target;
    setFiles((prevFiles) => ({
      ...prevFiles,
      [name]: files[0],
    }));
  };

  const handleRegister = async (e) => {
    e.preventDefault();

    const formDataToSend = new FormData();
    Object.keys(formData).forEach((key) => {
      formDataToSend.append(key, formData[key]);
    });
    formDataToSend.append('file1', files.file1);
    formDataToSend.append('file2', files.file2);

    try {
      const response = await CustomerService.saveApply(formDataToSend);

      if (response.status === 201) {
        setMessage('Registration successful!');
        navigate('/login');
      }
    } catch (error) {
      console.error('Registration error:', error);
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
          Register with Files
        </Typography>

        <form onSubmit={handleRegister} style={{ width: '100%' }}>         
          <TextField
            fullWidth
            label="Upload File 1"
            name="file1"
            type="file"
            onChange={handleFileChange}
            InputLabelProps={{
              shrink: true,
            }}
            required
            margin="normal"
          />
          <TextField
            fullWidth
            label="Upload File 2"
            name="file2"
            type="file"
            onChange={handleFileChange}
            InputLabelProps={{
              shrink: true,
            }}
            required
            margin="normal"
          />

          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            style={{ marginTop: '20px' }}
            onClick={() => navigate("/home")}
          >
            Submit Files
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

export default RegisterFile;
