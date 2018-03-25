import React, { Component } from 'react';
import { FormGroup, Input, Label, Button } from 'reactstrap';
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
    console.log(this.state.email, this.state.password);
    if ( this.state.email.length < 5 ) {
      this.setState({emailValid: false});
    }
    if ( this.state.password.length < 5 ) {
      this.setState({pwdValid: false});
    }
    /*fetch('http://localhost:8080/projectstudy/api/auth', {
      body: JSON.stringify({email: this.state.email, password: this.state.password}), // must match 'Content-Type' header
      cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
      credentials: 'same-origin', // include, *omit
      headers: {
        'user-agent': 'Mozilla/4.0 MDN Example',
        'content-type': 'application/json'
      },
      method: 'POST', // *GET, PUT, DELETE, etc.
      mode: 'cors', // no-cors, *same-origin
      redirect: 'follow', // *manual, error
      referrer: 'no-referrer', // *client
    })
    .then(response => {
      if(response.ok) {
        this.setState({authenticated: true});
      }
    });*/
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

export default Login;
