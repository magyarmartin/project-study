import React, { Component } from 'react';
import { connect } from 'react-redux';
import { FormGroup, Input, Label,  FormFeedback, Button, Alert, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import { Redirect } from 'react-router-dom';
import { bindActionCreators } from 'redux';
import registration from '../actions/registration.js';
import login from '../actions/auth.js';
import storeToken from '../actions/storeToken.js';
import { userDetailChanged, toggle } from '../actions/userDetailChanged.js';
import { modifyUserData, setModificationFlag } from '../actions/modifyUsedData.js';
import { deleteUser } from '../actions/deleteUser.js';
import { FIRSTNAME_CHANGED, LASTNAME_CHANGED, EMAIL_CHANGED, PASSWORD_CHANGED, PASSWORD_CONF_CHANGED, 
  IS_TEACHER_CHANGED, DESCRIPTION_CHANGED, SUBMIT} from  '../types/EventTypes';
import './../css/UserDetail.css';

class UserDetail extends Component {
  constructor(props) {
    super(props);

    if(!this.props.signin) {
      let user = this.props.user;
      this.props.userDetailChanged({type: FIRSTNAME_CHANGED, value: user.firstName})
      this.props.userDetailChanged({type: LASTNAME_CHANGED, value: user.lastName})
      this.props.userDetailChanged({type: EMAIL_CHANGED, value: user.email})
    }
  }

  onFirstNameChange(event) {
    let firstName = event.target.value;
    this.props.userDetailChanged({type: FIRSTNAME_CHANGED, value: firstName})
  }

  onLastNameChange(event) {
    let lastName = event.target.value;
    this.props.userDetailChanged({type: LASTNAME_CHANGED, value: lastName})
  }

  onEmailChange(event) {
    let email = event.target.value;
    this.props.userDetailChanged({type: EMAIL_CHANGED, value: email})
  }

  onPasswordChange(event) {
    let password = event.target.value;
    this.props.userDetailChanged({type: PASSWORD_CHANGED, value: password})
  }

  onPasswordConfChange(event) {
  	let passwordConf = event.target.value;
    this.props.userDetailChanged({type: PASSWORD_CONF_CHANGED, value: {passwordConf: passwordConf, password: this.props.userDetail.password}})
  }

  onCheckboxChange(event) {
    this.props.userDetailChanged({type: IS_TEACHER_CHANGED, value: event.target.value})
  }

  onDescriptionChange(event) {
    this.props.userDetailChanged({type: DESCRIPTION_CHANGED, value: event.target.value})
  }

  onSubmit(event) {
    event.preventDefault();
    let userDetail = this.props.userDetail;
    this.props.userDetailChanged({type: FIRSTNAME_CHANGED, value: userDetail.firstName})
    this.props.userDetailChanged({type: LASTNAME_CHANGED, value: userDetail.lastName})
    this.props.userDetailChanged({type: EMAIL_CHANGED, value: userDetail.email})
    if(this.props.signin) {
      this.props.userDetailChanged({type: PASSWORD_CHANGED, value: userDetail.password})
      this.props.userDetailChanged({type: PASSWORD_CONF_CHANGED, value: {passwordConf: userDetail.passwordConf, password: userDetail.password}})
    }
    this.props.userDetailChanged({type: IS_TEACHER_CHANGED, value: userDetail.type})
    this.props.userDetailChanged({type: DESCRIPTION_CHANGED, value: event.target.value})
    this.props.userDetailChanged({type: SUBMIT})
  }

  toggle(event) {
    event.preventDefault();
    this.props.toggle();
  }

  delete() {
    this.props.deleteUser(this.props.user.token);
  }

  componentWillReceiveProps(nextProps) {
    if(nextProps.signInData.completed && !nextProps.user.error && !nextProps.user.authenticated && !nextProps.user.isFetching) {
      nextProps.auth({email: this.props.userDetail.email, password: this.props.userDetail.password});
    } else if(nextProps.user.authenticated && this.props.userDetail.email === '') {
      let user = this.props.user;
      this.props.userDetailChanged({type: FIRSTNAME_CHANGED, value: user.firstName})
      this.props.userDetailChanged({type: LASTNAME_CHANGED, value: user.lastName})
      this.props.userDetailChanged({type: EMAIL_CHANGED, value: user.email})
    }
    if(nextProps.userDetail.submited
      && nextProps.userDetail.firstNameValid
      && nextProps.userDetail.lastNameValid
      && nextProps.userDetail.emailValid) {

        let data = {
          firstName: nextProps.userDetail.firstName,
          lastName: nextProps.userDetail.lastName,
          email: nextProps.userDetail.email,
          password: nextProps.userDetail.password,
          instructor: nextProps.userDetail.type === 'teacher',
          description: nextProps.userDetail.description
        }
        
        if(this.props.signin
          && nextProps.userDetail.passwordValid
          && nextProps.userDetail.passwordConfValid) {
            console.log('asd', nextProps.userDetail.submited)
          this.props.registration(data);
        } else {
          this.props.modifyData(data, nextProps.user.token);
        }
    }
    if(nextProps.user.changed) {
      setTimeout(() => {
        nextProps.setModificationFlag(false);
      }, 5000);
    }
  }

  render() {
    if(this.props.user.authenticated && this.props.signin) {
      return <Redirect to="/" push />
    }

    if(!this.props.user.authenticated && !this.props.signin && this.props.tokenChecked.checked) {
      return <Redirect to="/" push />
    }

  	return (
  		<div className="fullWidthHeight d-flex justify-content-center align-items-center">
	        <form id="signInForm">
            <h1>
              {this.props.signin ? 'Sign in' : 'Change user details'}
            </h1>
            {!this.props.signInData.error ? '' : <FormFeedback invalid="true">There wan an error during registration. Please try later.</FormFeedback>}
            {!this.props.signInData.invalid ? '' : <FormFeedback invalid="true">Your given informaions are invalid. Please fill the form again.</FormFeedback>}
            <Alert color="success" isOpen={this.props.user.changed}>
              Modification was successfull!
            </Alert>    
            <FormGroup>
              <Label for="input_firstName">First name:</Label>
              <Input id="input_firstName" onChange={this.onFirstNameChange.bind(this)} value={this.props.userDetail.firstName} invalid={!this.props.userDetail.firstNameValid}/>
              {this.props.userDetail.firstNameValid ? '' : <FormFeedback invalid="true">{this.props.userDetail.firstNameError}</FormFeedback>}
            </FormGroup>
            <FormGroup>
              <Label for="input_lastname">Last name:</Label>
              <Input id="input_lastname" onChange={this.onLastNameChange.bind(this)} value={this.props.userDetail.lastName} invalid={!this.props.userDetail.lastNameValid}/>
              {this.props.userDetail.lastNameValid ? '' : <FormFeedback invalid="true">{this.props.userDetail.lastNameError}</FormFeedback>}
            </FormGroup>
            <FormGroup>
              <Label for="input_email">Email:</Label>
              <Input id="input_email" type="email" onChange={this.onEmailChange.bind(this)} value={this.props.userDetail.email} 
                invalid={!this.props.userDetail.emailValid || this.props.signInData.exists}/>
              {this.props.userDetail.emailValid ? '' : <FormFeedback invalid="true">{this.props.userDetail.emailError}</FormFeedback>}
              {!this.props.signInData.exists ? '' : <FormFeedback invalid="true">This email is already in use.</FormFeedback>}
            </FormGroup>
            <FormGroup>
              <Label for="input_pwd">
                {this.props.signin ? 'Password:' : 'New password:'}
              </Label>
              <Input id="input_pwd" type="password" onChange={this.onPasswordChange.bind(this)} value={this.props.userDetail.password} invalid={!this.props.userDetail.passwordValid}/>
              {this.props.userDetail.passwordValid ? '' : <FormFeedback invalid="true">{this.props.userDetail.passwordError}</FormFeedback>}
            </FormGroup>
            <FormGroup>
              <Label for="input_pwd_conf">Password confirmation:</Label>
              <Input id="input_pwd_conf" type="password" onChange={this.onPasswordConfChange.bind(this)} value={this.props.userDetail.passwordConf} invalid={!this.props.userDetail.passwordConfValid}/>
              {this.props.userDetail.passwordConfValid ? '' : <FormFeedback invalid="true">{this.props.userDetail.passwordConfError}</FormFeedback>}
            </FormGroup>
            {this.props.signin ? 
              <FormGroup>
                <label htmlFor="input_pwd_conf">Are you a teacher or a student?</label>
                <div className="custom-control custom-radio">
                  <input type="radio" id="customRadio1" name="customRadio" 
                    className={`custom-control-input`} value="teacher" 
                    onChange={this.onCheckboxChange.bind(this)} checked={this.props.userDetail.type === "teacher"}/>
                  <label className="custom-control-label" htmlFor="customRadio1">Teacher</label>
                </div>
                <div className="custom-control custom-radio">
                  <input type="radio" id="customRadio2" name="customRadio" 
                    className={`custom-control-input`} value="student" 
                    onChange={this.onCheckboxChange.bind(this)} checked={this.props.userDetail.type === "student"}/>
                  <label className="custom-control-label" htmlFor="customRadio2">Student</label>
                </div>
              </FormGroup> 
            : ''}
            {this.props.userDetail.type === 'teacher' ?
            <FormGroup>
              <label htmlFor="input_description">Tell something about you</label>
              <Input type="textarea" name="input_description" id="input_description" value={this.props.userDetail.description} onChange={this.onDescriptionChange.bind(this)}/>
            </FormGroup> : ''}
	          <div className="form-group d-flex justify-content-between">
              <Button type="submit" onClick={this.onSubmit.bind(this)} color="primary">Submit</Button>
              <Button type="submit" onClick={this.toggle.bind(this)} color="primary">Delete profile</Button>
	          </div>
	        </form>
          <Modal isOpen={this.props.userDetail.modalOpen} toggle={this.toggle.bind(this)} backdrop={true}>
            <ModalHeader toggle={this.toggle.bind(this)}>Delete profile</ModalHeader>
            <ModalBody>
              Are you sure, you want to delete your profile?
            </ModalBody>
            <ModalFooter>
              <Button color="primary" onClick={this.delete.bind(this)}>Yes</Button>{' '}
              <Button color="secondary" onClick={this.toggle.bind(this)}>No</Button>
            </ModalFooter>
          </Modal>
      	</div>
  	);
  }
}

function mapDispatchToProps(dispatch) {
  return bindActionCreators({ 
    registration: registration,
    auth: login,
    storeToken: storeToken,
    userDetailChanged: userDetailChanged,
    modifyData: modifyUserData,
    setModificationFlag: setModificationFlag,
    toggle: toggle,
    deleteUser: deleteUser }, dispatch);
}

function mapStateToProps(state) {
  return {
    signInData: state.registration,
    user: state.user,
    userDetail: state.userDetail,
    tokenChecked: state.tokenChecked};
}

export default connect(mapStateToProps, mapDispatchToProps)(UserDetail);