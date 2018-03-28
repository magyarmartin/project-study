import React, { Component } from 'react';
import { FormGroup, Input, Label, Button } from 'reactstrap';
import { bindActionCreators } from 'redux';
import auth from '../actions/auth.js';
import { connect } from 'react-redux';
import './../css/Login.css';

class Login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      authenticated: false,
      email: '',
      password: ''
    };
  }

  isAuthenticated() {
    return this.state.authenticated ? 'TRUE' : 'FALSE';
  }

  onEmailChange(event) {
    let email = event.target.value;
    this.setState({email: email});

    if ( email.length > 5 ) {
      this.setState({emailValid: true});
    }
  }

  onPasswordChange(event) {
    let pwd = event.target.value;
    this.setState({password: pwd});

    if ( pwd.length > 5 ) {
      this.setState({emailValid: true});
    }
  }

  onSubmit(event) {
    event.preventDefault();
    this.props.auth({email: this.state.email, password: this.state.password})
  }

  render() {
    return (
      <div className="fullWidthHeight d-flex justify-content-center align-items-center">
        <form id="loginForm">
          <h1>Login</h1>
          <FormGroup>
            <Label for="input_email">Email:</Label>
            <Input id="input_email" onChange={this.onEmailChange.bind(this)}/>
          </FormGroup>
          <FormGroup>
            <Label for="input_pwd">Password:</Label>
            <Input id="input_pwd" type="password" onChange={this.onPasswordChange.bind(this)}/>
          </FormGroup>
          <div className="form-group">
            <Button type="submit" onClick={this.onSubmit.bind(this)} color="primary">Submit</Button>
          </div>
        </form>
      </div>
    );
  }
}

function mapDispatchToProps(dispatch) {
  return bindActionCreators({ auth: auth }, dispatch);
}

export default connect(null, mapDispatchToProps)(Login);
