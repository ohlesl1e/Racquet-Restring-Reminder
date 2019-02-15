import React from 'react';
import { Switch, Route } from 'react-router-dom';
import HomePage from './HomePage';
import AddCustomer from './AddCustomer';
import AddString from './AddString';
import StringDB from './StringDB';
import { Returning } from './Returning';

export default () =>
    <Switch>
        <Route path="/" exact component={HomePage} />
        <Route path="/addcustomer" exact component={AddCustomer} />
        <Route path="/addstring" exact component={AddString} />
        <Route path="/stringdatabase" exact component={StringDB} />
        <Route path="/returning" exact component={Returning} />
        <Route component={HomePage} />
    </Switch>
