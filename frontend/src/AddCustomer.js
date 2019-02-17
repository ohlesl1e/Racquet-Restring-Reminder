import React, { Component } from 'react'
import { FormGroup, FormControl, Form, Button } from "react-bootstrap";
import axios from 'axios'
import { Link } from 'react-router-dom'
import "./AddCustomer.css";

export class AddCustomer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            mainString: "",
            crossString: "",
            mainTension: "",
            crossTension: "",
            contact: "",
            email: "",
            strings: []
        }
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    }

    validateForm() {
        return this.state.name.length > 0 && this.state.contact.length > 0 && this.state.mainString.length > 0 && this.state.crossString.length > 0;
    }

    submitHandler = () => {
        axios({
            method: "POST",
            url: "/api/addcustomer",
            data: {
                name: this.state.name,
                contact: this.state.contact,
                email: this.state.email,
                mainString: this.state.mainString,
                crossString: this.state.crossString,
                mainTension: this.state.mainTension,
                crossTension: this.state.crossTension,
            }
        }).then(res => {
            console.log(this.state);
            console.log(res);
        }).catch((e) => {
            console.log(e);
        })
    }

    handleSubmit = event => {
        event.preventDefault()
    }

    componentDidMount() {
        axios.get("/api/allstrings")
            .then((res) => {
                console.log(res.data);
                const strings = res.data
                console.log({ strings })
                this.setState({ strings })
            }).catch((e) => {
                console.log(e);

            }).then(res => {
                console.log(this.state);
                console.log(res);
            }).catch((e) => {
                console.log(e);
            })
    }
    render() {
        return (
            <div className="AddCustomer">
                <form onSubmit={this.handleSubmit}>
                    <FormGroup className="FormGroup" controlId="name">
                        <Form.Label>Name: <br /></Form.Label>
                        <FormControl
                            placeholder="Roger Federer"
                            autoFocus
                            type="name"
                            value={this.state.name}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup className="FormGroup" controlId="contact">
                        <Form.Label>Phone: <br /></Form.Label>
                        <FormControl
                            placeholder="#########"
                            autoFocus
                            type="contact"
                            value={this.state.contact}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup className="FormGroup" controlId="email">
                        <Form.Label>E-mail: <br /></Form.Label>
                        <FormControl
                            placeholder="subzer0@gmail.com"
                            autoFocus
                            type="email"
                            value={this.state.email}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup className="FormGroup" controlId="mainString">
                        <Form.Label>Main: <br /></Form.Label>
                        <FormControl as="select" value={this.state.mainString} onChange={this.handleChange}>
                            <option>Choose...</option>
                            {this.state.strings.map(s => {
                                return (
                                    <option>{s.name}</option>
                                )
                            })}
                        </FormControl>
                    </FormGroup>
                    <FormGroup className="FormGroup" controlId="crossString">
                        <Form.Label>Cross: <br /></Form.Label>
                        <FormControl as="select" value={this.state.crossString} onChange={this.handleChange}>
                            <option>Choose...</option>
                            {this.state.strings.map(s => {
                                return (
                                    <option>{s.name}</option>
                                )
                            })}
                        </FormControl>
                    </FormGroup>
                    <FormGroup className="FormGroup" controlId="mainTension">
                        <Form.Label>Main Tension(lb): <br /></Form.Label>
                        <FormControl
                            autoFocus
                            type="tension"
                            value={this.state.mainTension}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup className="FormGroup" controlId="crossTension">
                        <Form.Label>Cross Tension(lb): <br /></Form.Label>
                        <FormControl
                            autoFocus
                            type="tension"
                            value={this.state.crossTension}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <Button
                        block
                        disabled={!this.validateForm()}
                        type="submit"
                        onClick={this.submitHandler}
                    >
                        Submit
                    </Button>
                </form>
            </div>
        )
    }
}

export default AddCustomer
