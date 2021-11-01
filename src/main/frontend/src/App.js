import React from "react";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";

import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import CreateAccount from './components/CreateAccount';
import SignIn from './components/SignIn';
import RegisterDevice from './components/RegisterDevice';
import ContactUs from './components/ContactUs';

function App() {
    return (
        <Router>  
          {/* A <Switch> looks through its children <Route>s and
              renders the first one that matches the current URL. */}
          <Switch>
            <Route path="/create-account" exact>
                <CreateAccount />
            </Route>
            <Route path="/sign-in">
              <SignIn />
            </Route>
            <Route path="/register-device">
                <RegisterDevice />
            </Route>
            <Route path="/contact-us">
                <ContactUs />
            </Route>
          </Switch>
      </Router>
    );
}

export default App;
