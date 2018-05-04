import React, { Component } from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import Login from './Login.jsx';
import Header from './Header.jsx';
import Main from '../components/Main.jsx';
import UserDetail from './UserDetail.jsx';
import CourseList from '../components/CourseList.jsx';
import CourseDetail from '../components/CourseDetail.jsx';
import checkToken from '../actions/checkToken.js';
import './../css/App.css';
import 'bootstrap/dist/css/bootstrap.css';

class App extends Component {

  constructor(props) {
    super(props);
  }

  token() {
    this.props.checkToken({email: 'this.state.email', password: 'this.state.password'})
  }

  render() {
    if(!this.props.user.authenticated && !this.props.tokenChecked.checked && !this.props.tokenChecked.isFetching) {
      this.token();
    }

    return (
      <BrowserRouter>
        <div className="fullWidthHeight">
          <Header className="header"/>
          <div className="content">
            <Route path="/login" component={Login} />
            <Route path="/signin" render={routeProps => <UserDetail {...routeProps} signin={true}/>}/>
            <Route path="/modifyDetails" render={routeProps => <UserDetail {...routeProps} signin={false}/>}/>
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
  return bindActionCreators({checkToken: checkToken}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(App);