import React, { Component } from 'react';
import Login from './Login.jsx';
import Header from './Header.jsx';
import Main from './Main.jsx';
import SignIn from './SignIn.jsx';
import CourseList from './CourseList.jsx';
import CourseDetail from './CourseDetail.jsx';
import './../css/App.css';
import 'bootstrap/dist/css/bootstrap.css'
import { BrowserRouter, Route } from 'react-router-dom'

class App extends Component {

  render() {
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

export default App;
