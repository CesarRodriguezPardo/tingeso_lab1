import './App.css'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Navbar from "./components/Navbar"
import Home from './components/Home';

import UserList from './components/UserList';
import UserProfile from './components/UserProfile';
import Login from './components/Login'
import Register from './components/Register'
import RegisterDocument from './components/RegisterDocument'
import LoanApplication from './components/LoanApplication'
import LoanCalculation from './components/LoanCalculation'
import LoanRequest from './components/LoanRequest'
import CreditList from './components/CreditList'
import AllCreditList from './components/AllCreditList'
import VerifiedUserList from './components/VerifiedUserList';
import SetData from './components/SetData';
import CreditDetail from './components/CreditDetail.jsx';

function App() {
  return (
      <Router>
          <div className="container">
          <Navbar></Navbar>
            <Routes>
              <Route path="/home" element={<Home/>} />
              <Route path="/user/UserList" element={<UserList/>} />
              <Route path="/user/UserProfile/:id" element={<UserProfile />} />
              <Route path="/login" element={<Login/>} />
              <Route path="/register" element={<Register/>} />
              <Route path="/registerdocument" element={<RegisterDocument/>} />
              <Route path="/loanapplication" element={<LoanApplication/>} />
              <Route path="/loancalculation" element={<LoanCalculation/>} />
              <Route path="/loanrequest" element={<LoanRequest/>} />
              <Route path="/creditlist" element={<CreditList/>} />
              <Route path="/allcreditlist" element={<AllCreditList/>} />
              <Route path="/user/verifieduserlist" element={<VerifiedUserList/>} />
              <Route path="/setdata/:id" element={<SetData/>} />
              <Route path="/creditdetail/:id" element={<CreditDetail/>} />

            </Routes>
          </div>
      </Router>
  );
}

export default App
