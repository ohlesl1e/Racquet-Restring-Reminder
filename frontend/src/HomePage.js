import React, { Component } from 'react'
import axios from 'axios'
import './HomePage.css'

export class HomePage extends Component {
    constructor(props) {
        super(props)
        this.state = {
            customers: [],
        }
        console.log(this.props)
    }

    componentDidMount() {
        axios.get("/api/allcustomers")
            .then((res) => {
                console.log(res.data);
                const customers = res.data.customers
                console.log({ customers })
                this.setState({ customers })
            }).catch((e) => {
                console.log(e);

            })
    }

    render() {
        return (
            <div className="HomePage">
                <table>
                    <td>Name</td>
                    <td>Contact</td>
                    <td>Strings(m/x)</td>
                    <td>Tensions</td>
                    {this.state.customers.map(c => {
                        return (
                            <tr>
                                <td>{c.name}</td>
                                <td>{c.contact}</td>
                                <td>{c.mainString}{" / "}{c.crossString}</td>
                                <td>{c.mainTension}{" lb / "}{c.crossTension}{" lb"}</td>
                            </tr>
                        )
                    })}
                </table>
            </div>
        )
    }
}

export default HomePage
