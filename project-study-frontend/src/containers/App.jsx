import React, { Component } from 'react';
import Login from '../components/Login.jsx';
import Header from '../components/Header.jsx';
import Main from '../components/Main.jsx';
import SignIn from '../components/SignIn.jsx';
import CourseList from '../components/CourseList.jsx';
import CourseDetail from '../components/CourseDetail.jsx';
import { connect } from 'react-redux';
import './../css/App.css';
import 'bootstrap/dist/css/bootstrap.css';
import { BrowserRouter, Route } from 'react-router-dom';

class App extends Component {

  render() {
    console.log(this.props.user)
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
  return {user: state.user};
}

export default connect(mapStateToProps)(App);