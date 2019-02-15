import React, { Component } from 'react'
import axios from 'axios'
import { FormGroup, FormControl, Form, Button, Row, Col } from "react-bootstrap";
import { Link } from 'react-router-dom'
import "./AddString.css"

export class AddString extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            material: "",
            tensionLoss: "",
        }
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    }

    validateForm() {
        return this.state.name.length > 0 && this.state.material.length > 0 && this.state.tensionLoss.length > 0;
    }

    submitHandler = () => {
        axios({
            method: "POST",
            url: "/api/addstring",
            data: {
                name: this.state.name,
                material: this.state.material,
                tensionLoss: this.state.tensionLoss,
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

    render() {
        return (
            <div className="AddString">
                <form onSubmit={this.handleSubmit}>
                    <FormGroup className="FormGroup" controlId="name">
                        <Form.Label>Name: <br /></Form.Label>
                        <FormControl
                            autoFocus
                            type="name"
                            value={this.state.name}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup className="FormGroup" controlId="material">
                        <Form.Label>Material: <br /></Form.Label>
                        <FormControl as="select" value={this.state.material} onChange={this.handleChange}>
                            <option>Choose...</option>
                            <option>Polyester</option>
                            <option>Natural Gut</option>
                            <option>Synthetic Gut</option>
                            <option>Multifilament</option>
                        </FormControl>
                    </FormGroup>
                    <FormGroup className="FormGroup" controlId="tensionLoss">
                        <Form.Label>Tension Loss: <br /></Form.Label>
                        <FormControl
                            autoFocus
                            type="tensionloss"
                            value={this.state.tensionLoss}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <Link to="/">
                        <Button
                            block
                            disabled={!this.validateForm()}
                            type="submit"
                            onClick={this.submitHandler}
                        >
                            Submit
                            </Button>
                    </Link>
                </form>
            </div>
        )
    }
}

export default AddString
