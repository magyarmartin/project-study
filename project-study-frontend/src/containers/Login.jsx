import React, { Component } from 'react';
import { FormGroup, Input, Label, Button, FormFeedback } from 'reactstrap';
import { bindActionCreators } from 'redux';
import { Redirect } from 'react-router-dom';
import login from '../actions/auth.js';
import storeToken from '../actions/storeToken.js';
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
    this.props.auth({email: this.state.email, password: this.state.password});
  }

  render() {
    if(this.props.user.authenticated) {
      storeToken(this.props.user.token);
      return <Redirect to="/" push />
    }

    return (
      <div className="fullWidthHeight d-flex justify-content-center align-items-center">
        <form id="loginForm">
          <h1>Login</h1>
          {this.props.user.authenticated === false ? <FormFeedback invalid="true">Wrong username or password</FormFeedback> : ''}
          {this.props.user.error ? <FormFeedback invalid="true">Some error happend! Please try it later or contact an administrator!</FormFeedback> : ''}

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
  return bindActionCreators({
    auth: login,
    storeToken: storeToken
  }, dispatch);
}

function mapStateToProps(state) {
  return {user: state.user};
}

export default connect(mapStateToProps, mapDispatchToProps)(Login);
