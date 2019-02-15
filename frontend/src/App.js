import React, { Component } from 'react';
import './App.css';
import Routes from './Routes';
import { Navbar } from "react-bootstrap";
import { BrowserRouter as Router } from 'react-router-dom';
import { Link } from 'react-router-dom';

class App extends Component {
  constructor(props) {
    super(props)
    this.state = {
      color: 'black',
      banner: 'hello',
      isOpen: true,
    }
    this.buttonHandler = this.buttonHandler.bind(this);
    console.log(this.props)
  }

  buttonHandler() {
    this.setState({
      isOpen: !this.state.isOpen,
    });
  }


  render() {
    return (
      <Router>
        <div className="App">
          <Navbar>
            <Link to="/">Home</Link>
            <Link to="/addstring">Add String</Link>
            <Link to="/addcustomer">Add Customer</Link>
            <Link to="/returning">Restring Due</Link>
            <Link to="/stringdatabase">String Database</Link>
          </Navbar>
          <Routes />
        </div>
      </Router>
    );
  }
}

const mapStateToProps = (state, ownState) => {
  return {
  }
}

const mapDispatchToProps = {}

export default App;
