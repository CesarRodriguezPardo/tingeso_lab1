import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import userService from "../services/user.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import SaveIcon from "@mui/icons-material/Save";
import Typography from "@mui/material/Typography";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";

const EditUserDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [rut, setRut] = useState("");
  const [dicom, setDicom] = useState(false);
  const [totalDebts, setTotalDebts] = useState("");
  const [independentJob, setIndependentJob] = useState(false);
  const [jobSeniority, setJobSeniority] = useState("");
  const [salary, setSalary] = useState("");
  const [independentEvaluate, setIndependentEvaluate] = useState(false);
  const [latePayment, setLatePayment] = useState(false);
  const [seniority, setSeniority] = useState("");
  const [balance, setBalance] = useState("");
  const [retiredCash, setRetiredCash] = useState("");
  const [periodicDeposit, setPeriodicDeposit] = useState("");
  const [recentlyRetiredCash, setRecentlyRetiredCash] = useState("");
  const [userName, setUserName] = useState("");

  useEffect(() => {
    userService.findById(id)
      .then(response => {
        const userRut = response.data.rut;
        const name = response.data.firstName;
        setRut(userRut);
        setUserName(name);
      })
      .catch(error => {
        console.error("Error al obtener el RUT del usuario:", error);
      });
  }, [id]);

  const saveUserDetails = async (e) => {
    e.preventDefault();
    try {
      await userService.setDicom(rut, dicom);
      await userService.setTotalDebts(rut, totalDebts);
      await userService.setIndependentJob(rut, independentJob);
      await userService.setJobSeniority(rut, jobSeniority);
      await userService.setSalary(rut, salary);
      await userService.setIndependentEvaluate(rut, independentEvaluate);
      await userService.setLatePayment(rut, latePayment);
      await userService.setSeniority(rut, seniority);
      await userService.setBalance(rut, balance);
      await userService.setRetiredCash(rut, retiredCash);
      await userService.setPeriodicDeposit(rut, periodicDeposit);
      await userService.setRecentlyRetiredCash(rut, recentlyRetiredCash);

      console.log("Datos del usuario actualizados exitosamente.");
      navigate("/user/verifieduserlist");
    } catch (error) {
      console.error("Error al actualizar los datos del usuario:", error);
    }
  };

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      component="form"
      onSubmit={saveUserDetails}
      sx={{
        backgroundColor: "white",
        padding: 3,
        borderRadius: 1,
        boxShadow: 2,
        width: '400px',
        margin: 'auto',
        marginTop: 4
      }}
    >
      <Typography variant="h6" component="h2" sx={{ marginBottom: 2, color: 'black' }}>
        User: {userName} (ID: {id})
      </Typography>
      <h3 style={{ color: 'black' }}>Set data</h3>
      
      {/* Radio Group for Dicom */}
      <FormControl component="fieldset" fullWidth>
        <Typography style={{ color: 'black' }}>Dicom</Typography>
        <RadioGroup
          row
          value={dicom ? "true" : "false"}
          onChange={(e) => setDicom(e.target.value === "true")}
        >
          <FormControlLabel value="true" control={<Radio />} label="True" labelPlacement="end" sx={{ color: 'black' }} />
          <FormControlLabel value="false" control={<Radio />} label="False" labelPlacement="end" sx={{ color: 'black' }} />
        </RadioGroup>
      </FormControl>

      {/* Input for Total Debts */}
      <FormControl fullWidth>
        <TextField label="Total Debts" type="number" value={totalDebts} onChange={(e) => setTotalDebts(e.target.value)} />
      </FormControl>

      {/* Radio Group for Independent Job */}
      <FormControl component="fieldset" fullWidth>
        <Typography style={{ color: 'black' }}>Independent Job</Typography>
        <RadioGroup
          row
          value={independentJob ? "true" : "false"}
          onChange={(e) => setIndependentJob(e.target.value === "true")}
        >
          <FormControlLabel value="true" control={<Radio />} label="True" labelPlacement="end" sx={{ color: 'black' }} />
          <FormControlLabel value="false" control={<Radio />} label="False" labelPlacement="end" sx={{ color: 'black' }} />
        </RadioGroup>
      </FormControl>

      {/* Input for Job Seniority */}
      <FormControl fullWidth>
        <TextField label="Job Seniority" value={jobSeniority} onChange={(e) => setJobSeniority(e.target.value)} />
      </FormControl>

      {/* Input for Salary */}
      <FormControl fullWidth>
        <TextField label="Salary" type="number" value={salary} onChange={(e) => setSalary(e.target.value)} />
      </FormControl>

      {/* Radio Group for Independent Evaluate */}
      <FormControl component="fieldset" fullWidth>
        <Typography style={{ color: 'black' }}>Independent Evaluate</Typography>
        <RadioGroup
          row
          value={independentEvaluate ? "true" : "false"}
          onChange={(e) => setIndependentEvaluate(e.target.value === "true")}
        >
          <FormControlLabel value="true" control={<Radio />} label="True" labelPlacement="end" sx={{ color: 'black' }} />
          <FormControlLabel value="false" control={<Radio />} label="False" labelPlacement="end" sx={{ color: 'black' }} />
        </RadioGroup>
      </FormControl>

      {/* Radio Group for Late Payment */}
      <FormControl component="fieldset" fullWidth>
        <Typography style={{ color: 'black' }}>Late Payment</Typography>
        <RadioGroup
          row
          value={latePayment ? "true" : "false"}
          onChange={(e) => setLatePayment(e.target.value === "true")}
        >
          <FormControlLabel value="true" control={<Radio />} label="True" labelPlacement="end" sx={{ color: 'black' }} />
          <FormControlLabel value="false" control={<Radio />} label="False" labelPlacement="end" sx={{ color: 'black' }} />
        </RadioGroup>
      </FormControl>

      {/* Input for Seniority */}
      <FormControl fullWidth>
        <TextField label="Seniority" value={seniority} onChange={(e) => setSeniority(e.target.value)} />
      </FormControl>

      {/* Input for Balance */}
      <FormControl fullWidth>
        <TextField label="Balance" type="number" value={balance} onChange={(e) => setBalance(e.target.value)} />
      </FormControl>

      
      {/* Input for Retired */}
      <FormControl fullWidth>
        <TextField label="Retired Cash" type="number" value={retiredCash} onChange={(e) => setRetiredCash(e.target.value)} />
      </FormControl>

      {/* Input for periodic */}
      <FormControl fullWidth>
        <TextField label="Periodic Deposit" type="number" value={periodicDeposit} onChange={(e) => setPeriodicDeposit(e.target.value)} />
      </FormControl>

      {/* Input for recently retired */}
      <FormControl fullWidth>
        <TextField label="Recently Retired" type="number" value={recentlyRetiredCash} onChange={(e) => setRecentlyRetiredCash(e.target.value)} />
      </FormControl>

      {/* Buttons to save or go back */}
      <Button variant="contained" color="info" type="submit" startIcon={<SaveIcon />}>
        Guardar
      </Button>
      <Button variant="text" color="primary" onClick={() => navigate("/user/verifieduserlist")}>
        Volver a la lista
      </Button>
    </Box>
  );
};

export default EditUserDetails;
