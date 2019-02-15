import React, { Component } from 'react'
import axios from 'axios'
import "./StringDB.css"

export class StringDB extends Component {
    constructor(props) {
        super(props);
        this.state = {
            strings: []
        }
    }

    componentDidMount() {
        axios.get('/api/allstrings')
            .then((res) => {
                console.log(res.data);
                const strings = res.data
                console.log({ strings })
                this.setState({ strings })
            }).catch((e) => {
                console.log(e)
            })
    }

    render() {
        return (
            <div className="StringDB">
                <table>
                    <tr>
                        <td>Name</td>
                        <td>Material</td>
                    </tr>
                    {this.state.strings.map( s => {
                        return(
                            <tr>
                                <td>{s.name}</td>
                                <td>{s.material}</td>
                            </tr>
                        )
                    })}
                </table>
            </div>
        )
    }
}

export default StringDB
