import React, { Component } from 'react'
import { Button } from 'react-bootstrap'
import {Link } from 'react-router-dom'
import axios from 'axios'
import './Returning.css'

export class Returning extends Component {
    constructor(props) {
        super(props)
        this.state = {
            customers: [],
        }
        console.log(this.props)
    }

    componentDidMount() {
        axios.get("/api/returning")
            .then((res) => {
                console.log(res.data);
                const customers = res.data
                console.log({ customers })
                this.setState({ customers })
            }).catch((e) => {
                console.log(e);
            })
    }

    buttonHandler = () => {
        axios.get("/api/clear")
            .then((res) => {
            console.log(res.data);
        }).catch((e) => {
            console.log(e)
        })
    }

    render() {
        return (
            <div className="Returning">
                <table>
                    <td>Name</td>
                    <td>Contact</td>
                    <td>E-mail</td>
                    <td>Strings(m/x)</td>
                    <td>Tensions</td>
                    <td>Due Date</td>
                    {this.state.customers.map(c => {
                        return (
                            <tr>
                                <td>{c.name}</td>
                                <td>{c.contact}</td>
                                <td><a href={"mailto:" + c.email}>{c.email}</a></td>
                                <td>{c.mainString}{" / "}{c.crossString}</td>
                                <td>{c.mainTension}{" lb / "}{c.crossTension}{" lb"}</td>
                                <td>{c.date2Return}</td>
                            </tr>
                        )
                    })}
                </table>
                <Link to="/returning">
                    <Button
                        type="submit"
                        onClick={this.buttonHandler}
                    >Clear
                </Button>
                </Link>
            </div>
        )
    }
}

export default Returning
