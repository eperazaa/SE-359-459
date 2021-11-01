import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

import CreateAccount from './components/CreateAccount';

function App() {
    return (
        <div className="App">
            <div>
                <CreateAccount />
            </div>
        </div>
    );
}

export default App;
