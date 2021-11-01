import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import LogOutButton from "../LogOutButton";

function Dashboard() {
    return (
        <div>
            <Container 
                fluid
                style={{
                    minHeight:"100vh",
                    background:"#282c34",
                    color: "#F6F6F6",
                    fontSize:"calc(10px + 1.5vmin)",
                }}>
                <Row className="pt-2">
                    <Col xs={2} md={2}/>
                    <Col xs={8} md={8}>
                    </Col>
                    <Col xs={2} md={2}>
                        <LogOutButton />
                    </Col>
                </Row>
                <h1>CleanSweep Portal</h1>
                <br />
                <h3>Welcome User</h3>
            </Container>
        </div>
    )
}

export default Dashboard;