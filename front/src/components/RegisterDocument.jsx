import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import DocumentService from '../services/document.service';
import UserService from '../services/user.service';
import { Paper, Grid, TextField, Button, Typography, CircularProgress, Alert } from '@mui/material';

const RegisterDocument = () => {
  const [files, setFiles] = useState({});
  const [message, setMessage] = useState({ text: '', type: 'info' });
  const [userId, setUserId] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUserId = async () => {
      const rut = localStorage.getItem('rut');
      if (!rut) {
        setMessage({ text: 'No RUT found. Please login again.', type: 'error' });
        navigate('/login');
        return;
      }

      try {
        setIsLoading(true);
        const response = await UserService.getIdByRut(rut);
        setUserId(response.data);
      } catch (error) {
        console.error('Error fetching user ID:', error);
        setMessage({ text: 'Error retrieving user ID. Please try again.', type: 'error' });
      } finally {
        setIsLoading(false);
      }
    };

    fetchUserId();
  }, [navigate]);

  const handleFileChange = (e) => {
    const { name, files } = e.target;
    setFiles((prevFiles) => ({ ...prevFiles, [name]: files[0] }));
  };

  const uploadFiles = async () => {
    if (!userId) {
      setMessage({ text: 'User ID not found. Please login again.', type: 'error' });
      return;
    }

    setIsLoading(true);
    setMessage({ text: '', type: 'info' });

    try {
      // Llamar al servicio para subir los documentos
      await DocumentService.uploadCustomerDocs(userId, files.file1, files.file2);

      // Verificar si los documentos se han subido correctamente
      const isSavingUploadedResponse = await DocumentService.isSavingUploaded(userId);
      const isWorksheetUploadedResponse = await DocumentService.isWorksheetUploaded(userId);

      if (isSavingUploadedResponse.data && isWorksheetUploadedResponse.data) {
        setMessage({ text: 'Documents uploaded successfully!', type: 'success' });
        // Redireccionar a /home
        navigate('/home');
      } else {
        setMessage({ text: 'Failed to upload documents. Please try again.', type: 'error' });
      }
    } catch (error) {
      console.error('Error uploading documents:', error);
      setMessage({ text: 'An error occurred during upload.', type: 'error' });
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Paper elevation={3} style={{ padding: '20px', maxWidth: '600px', margin: '20px auto' }}>
      <Typography variant="h6" gutterBottom>
        Upload Documents
      </Typography>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <TextField
            type="file"
            name="file1"
            onChange={handleFileChange}
            fullWidth
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            type="file"
            name="file2"
            onChange={handleFileChange}
            fullWidth
          />
        </Grid>
        <Grid item xs={12}>
  {isLoading ? (
    <CircularProgress />
  ) : (
    <>
      <Button 
        variant="contained" 
        color="primary" 
        onClick={uploadFiles} 
        sx={{ mr: 1 }} // Margen a la derecha
      >
        Upload
      </Button>

      <Button 
        variant="contained" 
        color="primary" 
        onClick={() => navigate('/home')}
        sx={{ ml: 1 }} // Margen a la izquierda
      >
        Upload without documents
      </Button>
    </>
  )}
</Grid>
      </Grid>
    </Paper>
  );
};

export default RegisterDocument;
