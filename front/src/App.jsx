import './App.css'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Navbar from "./components/Navbar"
import Home from './components/Home';

import AccountRequest from './components/AccountRequest';
import CustomerProfile from './components/CustomerProfile';
import Login from './components/Login'
import Profile from './components/Profile'
import Register from './components/Register'
import RegisterDocument from './components/RegisterDocument'
import LoanApplication from './components/LoanApplication'
import LoanCalculation from './components/LoanCalculation'

function App() {
  return (
      <Router>
          <div className="container">
          <Navbar></Navbar>
            <Routes>
              <Route path="/home" element={<Home/>} />
              <Route path="/customer/accountrequest" element={<AccountRequest/>} />
              <Route path="/customer/customerprofile/:id" element={<CustomerProfile />} />
              <Route path="/login" element={<Login/>} />
              <Route path="/profile" element={<Profile/>} />
              <Route path="/register" element={<Register/>} />
              <Route path="/registerdocument" element={<RegisterDocument/>} />
              <Route path="/loanapplication" element={<LoanApplication/>} />
              <Route path="/loancalculation" element={<LoanCalculation/>} />
            </Routes>
          </div>
      </Router>
  );
}

export default App
