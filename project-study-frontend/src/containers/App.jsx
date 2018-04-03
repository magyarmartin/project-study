import React, { Component } from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import Login from './Login.jsx';
import Header from '../components/Header.jsx';
import Main from '../components/Main.jsx';
import SignIn from './SignIn.jsx';
import CourseList from '../components/CourseList.jsx';
import CourseDetail from '../components/CourseDetail.jsx';
import checkToken from '../actions/checkToken.js';
import './../css/App.css';
import 'bootstrap/dist/css/bootstrap.css';

class App extends Component {

  render() {
    if(!this.props.user.authenticated && !this.props.tokenChecked) {
      console.log('check')
    }

    return (
      <BrowserRouter>
        <div className="fullWidthHeight">
          <Header className="header"/>
          <div className="content">
            <Route path="/login" component={Login} />
            <Route path="/signin" component={SignIn} />
            <Route path="/course/:courseId" component={CourseDetail} />
            <Route path="/courses" component={CourseList} />
            <Route exact={true} path="/" component={Main} />
          </div>
        </div>
      </BrowserRouter>
    );
  }
}

function mapStateToProps(state) {
  return {
    user: state.user,
    tokenChecked: state.tokenChecked
  };
}

function mapDispatchToProps(dispatch) {
  return bindActionCreators({checkToken: ''}, dispatch);
}

export default connect(mapStateToProps)(App);