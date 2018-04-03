import React, { Component } from 'react';
import { connect } from 'react-redux';
import { FormGroup, Input, Label,  FormFeedback, Button } from 'reactstrap';
import { bindActionCreators } from 'redux';
import Validation from './../utils/Validation.js';
import registration from '../actions/registration.js';
import './../css/SignIn.css';

class SignIn extends Component {
  constructor(props) {
    super(props);

    this.state = {
      email: '',
      password: '',
      passwordConf: '',
      firstName: '',
      lastName: '',
      isTeacher: undefined,
      emailValid: true,
      passwordValid: true,
      passwordConfValid: true,
      firstNameValid: true,
      lastNameValid: true,
      isTeacherValid: true,
      emailError: '',
      passwordError: 'The password should be at least 6 character!',
      passwordConfError: 'The password confirmation should be equal the password!',
      firstNameError: 'The first name should be not empty!',
      lastNameError: 'The last name should be not empty!',
      isTeacherError: 'You sould select is you are a teacher or a student!'
    };
  }

  onFirstNameChange(event) {
    let firstName = event.target.value;
    this.setState({firstName: firstName});
    this.validateFirstName( firstName );
  }

  onLastNameChange(event) {
    let lastName = event.target.value;
    this.setState({lastName: lastName});
    this.validateLastName( lastName );
  }

  onEmailChange(event) {
    let email = event.target.value;
    this.setState({email: email});
    this.validateEmail( email );
  }

  onPasswordChange(event) {
    let password = event.target.value;
    this.setState({password: password});
    this.validatePassworld( password );
  }

  onPasswordConfChange(event) {
  	let passwordConf = event.target.value;
    this.setState({passwordConf: passwordConf});
    this.validatePassworldConf( passwordConf );
  }

  onCheckboxChange(event) {
  	if ( event.target.value === 'teacher' ) {
      this.setState({isTeacher: true});
  	} else {
  		this.setState({isTeacher: false});
  	}
    this.setState({isTeacherValid: true});
  }

  validateFirstName( firstName ) {
  	if ( Validation.isEmpty( firstName ) ) {
      this.setState({firstNameValid: false});
      return false;
    } else {
      this.setState({firstNameValid: true});
      return true;
    }
  }

  validateLastName( lastName ) {
	if ( Validation.isEmpty( lastName ) ) {
      this.setState({lastNameValid: false});
      return false;
    } else {
      this.setState({lastNameValid: true});
      return true;
    }
  }

  validateEmail( email ) {
  	if ( Validation.isEmpty(email) ) {
    	this.setState({emailValid: false});
      this.setState({emailError: 'The email should be not empty'});
    } else if ( !Validation.isValidEmail(email) ) {
		this.setState({emailValid: false});
      this.setState({emailError: 'This is not a valid email'});
    } else {
      this.setState({emailValid: true});
      return true;
    }
    return false;
  }

  validatePassworld( password ) {
  	if ( Validation.isShorterThan( password, 6 ) ) {
      this.setState({passwordValid: false});
      return false;
    } else {
      this.setState({passwordValid: true});
      return true;
    }
  }

  validatePassworldConf( passwordConf ) {
  	if ( !Validation.isTheSame( passwordConf, this.state.password ) ) {
      this.setState({passwordConfValid: false});
      return false;
    } else {
      this.setState({passwordConfValid: true});
      return true;
    }
  }

  validateAll() {
    let isTeacherValid = false;
    if ( this.state.isTeacher === undefined ) {
  		this.setState({isTeacherValid: false});
  	} else {
      this.setState({isTeacherValid: true});
      isTeacherValid = true;
    }
    let isFirstNameValid = this.validateFirstName( this.state.firstName );
    let isLastNameValid = this.validateLastName( this.state.lastName );
    let isEmailValid = this.validateEmail( this.state.email );
    let isPasswordValid = this.validatePassworld( this.state.password );
    let isPasswordConfValid = this.validatePassworldConf( this.state.passwordConf );

  	return isFirstNameValid
      && isLastNameValid
      && isEmailValid
      && isPasswordValid
      && isPasswordConfValid
      && isTeacherValid;
  }

  onSubmit(event) {
  	event.preventDefault();
    if(this.validateAll()) {
      registration({
        firstName: this.state.firstName,
        lastName: this.state.lastName,
        email: this.state.email,
        password: this.state.password,
        instructor: this.state.isTeacher
      });
      console.log('sent')
    } else {
      console.log('not')
    }
  }

  render() {
  	return (
  		<div className="fullWidthHeight d-flex justify-content-center align-items-center">
	        <form id="signInForm">
	          <h1>Sign in</h1>
            <FormGroup>
              <Label for="input_firstName">First name:</Label>
              <Input id="input_firstName" onChange={this.onFirstNameChange.bind(this)} invalid={!this.state.firstNameValid}/>
              {this.state.firstNameValid ? '' : <FormFeedback invalid="true">{this.state.firstNameError}</FormFeedback>}
            </FormGroup>
            <FormGroup>
              <Label for="input_lastname">Last name:</Label>
              <Input id="input_lastname" onChange={this.onLastNameChange.bind(this)} invalid={!this.state.lastNameValid}/>
              {this.state.lastNameValid ? '' : <FormFeedback invalid="true">{this.state.lastNameError}</FormFeedback>}
            </FormGroup>
            <FormGroup>
              <Label for="input_email">Email:</Label>
              <Input id="input_email" type="email" onChange={this.onEmailChange.bind(this)} invalid={!this.state.emailValid}/>
              {this.state.emailValid ? '' : <FormFeedback invalid="true">{this.state.emailError}</FormFeedback>}
            </FormGroup>
            <FormGroup>
              <Label for="input_pwd">Password:</Label>
              <Input id="input_pwd" type="password" onChange={this.onPasswordChange.bind(this)} invalid={!this.state.passwordValid}/>
              {this.state.passwordValid ? '' : <FormFeedback invalid="true">{this.state.passwordError}</FormFeedback>}
            </FormGroup>
            <FormGroup>
              <Label for="input_pwd_conf">Password confirmation:</Label>
              <Input id="input_pwd_conf" type="password" onChange={this.onPasswordConfChange.bind(this)} invalid={!this.state.passwordConfValid}/>
              {this.state.passwordConfValid ? '' : <FormFeedback invalid="true">{this.state.passwordConfError}</FormFeedback>}
            </FormGroup>
	          <FormGroup>
	            <label htmlFor="input_pwd_conf">Are you a teacher or a student?</label>
	            <div className="custom-control custom-radio">
				        <input type="radio" id="customRadio1" name="customRadio" className={`custom-control-input ${this.state.isTeacherValid ? '' : 'is-invalid'}`} value="teacher" onChange={this.onCheckboxChange.bind(this)}/>
				        <label className="custom-control-label" htmlFor="customRadio1">Teacher</label>
				      </div>
				      <div className="custom-control custom-radio">
				        <input type="radio" id="customRadio2" name="customRadio" className={`custom-control-input ${this.state.isTeacherValid ? '' : 'is-invalid'}`} value="student" onChange={this.onCheckboxChange.bind(this)}/>
				        <label className="custom-control-label" htmlFor="customRadio2">Student</label>
				      </div>
              {this.state.isTeacherValid ? '' : <FormFeedback invalid="true">{this.state.isTeacherError}</FormFeedback>}
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
  return bindActionCreators({ registration: registration }, dispatch);
}

export default connect(null, mapDispatchToProps)(SignIn);